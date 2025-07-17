// hooks/useNotificationSocket.js
import { useEffect, useRef } from "react";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import { toast } from "react-toastify";

export function useNotificationSocket(userId) {
  const stompClientRef = useRef(null);

  useEffect(() => {
    if (!userId) {
      console.log("No user ID provided, skipping WebSocket connection");
      return;
    }

    const connect = () => {
      try {
        console.log("Attempting to connect to WebSocket...");
        
const socket = new SockJS(`http://localhost:8080/ws-notify?userId=${userId}`);
        const stomp = Stomp.over(socket);
        
        // Enable debug logging
        stomp.debug = (str) => {
          console.log("STOMP: " + str);
        };

        stompClientRef.current = stomp;

        stomp.connect(
          {
          },
          (frame) => {
            console.log("Connected to WebSocket successfully:", frame);
            
            // Subscribe to user-specific notifications
            stomp.subscribe(`/user/queue/toast`, (message) => {
              console.log("Received toast message:", message.body);
              try {
                const notification = JSON.parse(message.body);
                console.log("Parsed notification:", notification);
                
                toast.info(notification.body, {
                  autoClose: 4000,
                  position: "top-right"
                });
              } catch (error) {
                console.error("Error parsing notification:", error);
              }
            });

            // Also subscribe to general topic for testing
            stomp.subscribe("/topic/toast", (message) => {
              console.log("Received broadcast message:", message.body);
              try {
                const notification = JSON.parse(message.body);
                toast.info(notification.body, {
                  autoClose: 4000,
                  position: "top-right"
                });
              } catch (error) {
                console.error("Error parsing broadcast notification:", error);
              }
            });
          },
          (error) => {
            console.error("WebSocket connection failed:", error);
            // Retry connection after 5 seconds
            setTimeout(connect, 5000);
          }
        );

        socket.onopen = () => {
          console.log("SockJS connection opened");
        };

        socket.onclose = () => {
          console.log("SockJS connection closed");
        };

        socket.onerror = (error) => {
          console.error("SockJS error:", error);
        };

      } catch (error) {
        console.error("Error creating WebSocket connection:", error);
      }
    };

    connect();

    return () => {
      if (stompClientRef.current && stompClientRef.current.connected) {
        console.log("Disconnecting WebSocket...");
        stompClientRef.current.disconnect();
      }
    };
  }, [userId]);

  return stompClientRef.current;
}

