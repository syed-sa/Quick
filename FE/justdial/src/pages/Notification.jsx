import { useState } from "react";
import { Bell, Check, X, Clock, Calendar, Settings, Mail, Smartphone } from "lucide-react";
import api from "../components/auth/axios";
import { formatDistanceToNow } from 'date-fns';

import { useEffect } from "react";
const NotificationPage = () => {
  const [activeTab, setActiveTab] = useState("all");
  const [notifications, setNotifications] = useState([
    {
      id: 1,
      type: "booking_request",
      title: "New Booking Request",
      message: "John Doe has requested a booking for AC Repair service on Dec 15, 2024",
      timestamp: "2 hours ago",
      isRead: false,
      priority: "high"
    },
    {
      id: 2,
      type: "booking_accepted",
      title: "Booking Confirmed",
      message: "Your booking for Plumbing service has been confirmed by ABC Services",
      timestamp: "5 hours ago",
      isRead: true,
      priority: "medium"
    },
    {
      id: 3,
      type: "booking_rejected",
      title: "Booking Declined",
      message: "Your booking request for Electrical work has been declined. Please try another service provider.",
      timestamp: "1 day ago",
      isRead: false,
      priority: "medium"
    },
    {
      id: 4,
      type: "reminder",
      title: "Upcoming Service",
      message: "Reminder: Your AC Repair service is scheduled for tomorrow at 2:00 PM",
      timestamp: "2 days ago",
      isRead: true,
      priority: "low"
    }
  ]);

  const [emailNotifications, setEmailNotifications] = useState(true);
  const [pushNotifications, setPushNotifications] = useState(true);
  useEffect(() => {
    const getNotification = async () => {
      try {
        const userId = localStorage.getItem("userId");
        const res = await api.get(`notifications/${userId}`);
        if (res.status === 200) {
          setNotifications(res.data);
        }
      } catch (error) {
        console.error("Error fetching notifications:", error);
      }
    };

    getNotification();
  }, []); 

  const getNotificationIcon = (type) => {
    switch (type) {
      case "booking_request":
        return <Calendar className="h-5 w-5 text-blue-500" />;
      case "booking_accepted":
        return <Check className="h-5 w-5 text-green-500" />;
      case "booking_rejected":
        return <X className="h-5 w-5 text-red-500" />;
      case "reminder":
        return <Clock className="h-5 w-5 text-yellow-500" />;
      default:
        return <Bell className="h-5 w-5 text-gray-500" />;
    }
  };

  const getPriorityColor = (priority) => {
    switch (priority) {
      case "high":
        return "border-l-red-500";
      case "medium":
        return "border-l-yellow-500";
      case "low":
        return "border-l-green-500";
      default:
        return "border-l-gray-300";
    }
  };

  const markAsRead = (id) => {
    setNotifications(notifications.map(notif => 
      notif.id === id ? { ...notif, isRead: true } : notif
    ));
  };

  const markAllAsRead = () => {
    setNotifications(notifications.map(notif => ({ ...notif, isRead: true })));
  };

  const filteredNotifications = notifications.filter(notif => {
    if (activeTab === "all") return true;
    if (activeTab === "unread") return !notif.isRead;
    return notif.type === activeTab;
  });

  const unreadCount = notifications.filter(notif => !notif.isRead).length;

  return (
    <div className="max-w-4xl mx-auto p-4 md:p-6">
      {/* Header */}
      <div className="mb-6">
        <div className="flex items-center justify-between mb-4">
          <h1 className="text-2xl font-bold text-gray-800 flex items-center">
            <Bell className="h-6 w-6 mr-2" />
            Notifications
            {unreadCount > 0 && (
              <span className="ml-2 bg-red-500 text-white text-xs rounded-full px-2 py-1">
                {unreadCount}
              </span>
            )}
          </h1>
          <button
            onClick={markAllAsRead}
            className="text-sm text-blue-600 hover:text-blue-800"
          >
            Mark all as read
          </button>
        </div>

        {/* Tabs */}
        <div className="flex space-x-1 bg-gray-100 p-1 rounded-lg">
          {[
            { key: "all", label: "All" },
            { key: "unread", label: "Unread" },
          ].map(tab => (
            <button
              key={tab.key}
              onClick={() => setActiveTab(tab.key)}
              className={`px-4 py-2 text-sm font-medium rounded-md transition-colors ${
                activeTab === tab.key
                  ? "bg-white text-blue-600 shadow-sm"
                  : "text-gray-600 hover:text-gray-900"
              }`}
            >
              {tab.label}
            </button>
          ))}
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Notifications List */}
        <div className="lg:col-span-2">
          {filteredNotifications.length === 0 ? (
            <div className="text-center py-12">
              <Bell className="h-12 w-12 text-gray-400 mx-auto mb-4" />
              <p className="text-gray-500">No notifications found</p>
            </div>
          ) : (
            <div className="space-y-3">
              {filteredNotifications.map(notification => (
                <div
                  key={notification.id}
                  className={`bg-white rounded-lg shadow-sm border-l-4 ${getPriorityColor(notification.priority)} p-4 hover:shadow-md transition-shadow ${
                    !notification.isRead ? "bg-blue-50" : ""
                  }`}
                >
                  <div className="flex items-start justify-between">
                    <div className="flex items-start space-x-3">
                      {getNotificationIcon(notification.notificationType)}
                      <div className="flex-1 min-w-0">
                        <h3 className={`text-sm font-medium ${
                          !notification.isRead ? "text-gray-900" : "text-gray-700"
                        }`}>
                          {notification.title}
                        </h3>
                        <p className="text-sm text-gray-600 mt-1">
                          {notification.message}
                        </p>
                       <p className="text-xs text-gray-500 mt-2">
  {notification.timestamp && !isNaN(new Date(notification.timestamp)) ? (
    formatDistanceToNow(new Date(notification.timestamp), { addSuffix: true })
  ) : (
    "Invalid date"
  )}
</p>

                      </div>
                    </div>
                    {!notification.isRead && (
                      <button
                        onClick={() => markAsRead(notification.id)}
                        className="text-blue-600 hover:text-blue-800 text-sm"
                      >
                        Mark as read
                      </button>
                    )}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* Settings Panel */}
        <div className="bg-white rounded-lg shadow-sm border p-4">
          <h2 className="text-lg font-semibold text-gray-800 mb-4 flex items-center">
            <Settings className="h-5 w-5 mr-2" />
            Notification Settings
          </h2>
          
          <div className="space-y-4">
            <div className="flex items-center justify-between">
              <div className="flex items-center space-x-2">
                <Mail className="h-4 w-4 text-gray-500" />
                <span className="text-sm text-gray-700">Email Notifications</span>
              </div>
              <button
                onClick={() => setEmailNotifications(!emailNotifications)}
                className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${
                  emailNotifications ? "bg-blue-600" : "bg-gray-200"
                }`}
              >
                <span
                  className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform ${
                    emailNotifications ? "translate-x-6" : "translate-x-1"
                  }`}
                />
              </button>
            </div>

            <div className="flex items-center justify-between">
              <div className="flex items-center space-x-2">
                <Smartphone className="h-4 w-4 text-gray-500" />
                <span className="text-sm text-gray-700">Push Notifications</span>
              </div>
              <button
                onClick={() => setPushNotifications(!pushNotifications)}
                className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${
                  pushNotifications ? "bg-blue-600" : "bg-gray-200"
                }`}
              >
                <span
                  className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform ${
                    pushNotifications ? "translate-x-6" : "translate-x-1"
                  }`}
                />
              </button>
            </div>
          </div>

          <div className="mt-6 pt-4 border-t">
            <h3 className="text-sm font-medium text-gray-700 mb-3">Notification Types</h3>
            <div className="space-y-2 text-xs text-gray-600">
              <div className="flex items-center space-x-2">
                <div className="w-2 h-2 bg-blue-500 rounded-full"></div>
                <span>Booking Requests</span>
              </div>
              <div className="flex items-center space-x-2">
                <div className="w-2 h-2 bg-green-500 rounded-full"></div>
                <span>Booking Confirmations</span>
              </div>
              <div className="flex items-center space-x-2">
                <div className="w-2 h-2 bg-red-500 rounded-full"></div>
                <span>Booking Rejections</span>
              </div>
              <div className="flex items-center space-x-2">
                <div className="w-2 h-2 bg-yellow-500 rounded-full"></div>
                <span>Reminders</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default NotificationPage;