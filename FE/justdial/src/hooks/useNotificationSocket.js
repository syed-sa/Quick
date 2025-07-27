// hooks/useNotificationSocket.js
import { useEffect, useRef } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { toast } from "react-toastify";

export function useNotificationSocket() {
  const stompClientRef = useRef(null);

  useEffect(() => {
    const token = localStorage.getItem("token");

    const stompClient = new Client({
      webSocketFactory: () =>
        new SockJS(`http://localhost:8080/ws-notify?token=${token}`), 
      reconnectDelay: 5000,
      debug: (str) => console.log(str),
    });

    stompClient.onConnect = (frame) => {
      console.log("Connected to WebSocket:", frame);

      stompClient.subscribe(`/user/queue/toast`, (message) => {
        console.log("Received toast message:", message.body);
        try {
          const notification = JSON.parse(message.body);
          console.log(" Parsed notification:", notification);

          toast.info(notification.body, {
            autoClose: 4000,
            position: "top-right",
          });
        } catch (error) {
          console.error("Error parsing notification:", error);
        }
      });
    };

    stompClient.onStompError = (frame) => {
      console.error("STOMP Error:", frame.headers["message"]);
    };

    stompClient.onWebSocketError = (error) => {
      console.error("WebSocket Error:", error);
    };

    stompClient.activate(); // instead of stomp.connect()

    stompClientRef.current = stompClient;

    return () => {
      if (stompClientRef.current && stompClientRef.current.active) {
        console.log("🔌 Disconnecting WebSocket...");
        stompClientRef.current.deactivate();
      }
    };
  }, []);

  return stompClientRef.current;
}
