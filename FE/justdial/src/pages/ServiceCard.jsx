import React, { useState, useEffect } from 'react';
import { MapPin, Star, Phone, Mail, Clock, Camera } from "lucide-react";

const ServiceCard = ({ service, onViewDetails }) => {
  const [images, setImages] = useState([]);
  const [loadingImages, setLoadingImages] = useState(true);
  const [currentImageIndex, setCurrentImageIndex] = useState(0);

  useEffect(() => {
    const fetchImages = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/services/getImages?serviceId=${service.id}`,
          {
            method: "GET",
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        );
        
        if (response.ok) {
          const imageData = await response.json();
          setImages(Array.isArray(imageData) ? imageData : []);
        }
      } catch (error) {
        console.error("Error fetching images:", error);
      } finally {
        setLoadingImages(false);
      }
    };

    if (service.id) {
      fetchImages();
    } else {
      setLoadingImages(false);
    }
  }, [service.id]);

  const nextImage = () => {
    if (images.length > 0) {
      setCurrentImageIndex((prev) => (prev + 1) % images.length);
    }
  };

  const prevImage = () => {
    if (images.length > 0) {
      setCurrentImageIndex((prev) => (prev - 1 + images.length) % images.length);
    }
  };

  const handleViewDetails = () => {
    if (onViewDetails) {
      onViewDetails(service);
    }
  };

  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300">
      {/* Image Section */}
      <div className="relative h-48 bg-gray-200">
        {loadingImages ? (
          <div className="flex items-center justify-center h-full">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-red-500"></div>
          </div>
        ) : images.length > 0 ? (
          <>
            <img
              src={images[currentImageIndex]}
              alt={service.serviceName || service.name || 'Service'}
              className="w-full h-full object-cover"
              onError={(e) => {
                // Fallback to placeholder image on error
                e.target.src = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDIwMCAyMDAiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjxyZWN0IHdpZHRoPSIyMDAiIGhlaWdodD0iMjAwIiBmaWxsPSIjRjNGNEY2Ii8+CjxwYXRoIGQ9Ik0xMDAgMTAwTDEyMCA4MEwxNDAgMTAwTDEyMCAxMjBMMTAwIDEwMFoiIGZpbGw9IiM5Q0EzQUYiLz4KPC9zdmc+';
              }}
            />
            {images.length > 1 && (
              <>
                <button
                  onClick={prevImage}
                  className="absolute left-2 top-1/2 transform -translate-y-1/2 bg-black bg-opacity-50 text-white p-1 rounded-full hover:bg-opacity-75 transition-opacity"
                  aria-label="Previous image"
                >
                  ←
                </button>
                <button
                  onClick={nextImage}
                  className="absolute right-2 top-1/2 transform -translate-y-1/2 bg-black bg-opacity-50 text-white p-1 rounded-full hover:bg-opacity-75 transition-opacity"
                  aria-label="Next image"
                >
                  →
                </button>
                <div className="absolute bottom-2 left-1/2 transform -translate-x-1/2 bg-black bg-opacity-50 text-white px-2 py-1 rounded-full text-xs">
                  {currentImageIndex + 1} / {images.length}
                </div>
              </>
            )}
          </>
        ) : (
          <div className="flex items-center justify-center h-full text-gray-400">
            <Camera className="h-12 w-12" />
          </div>
        )}
      </div>

      {/* Content Section */}
      <div className="p-4">
        <h3 className="text-lg font-semibold text-gray-800 mb-2 line-clamp-1">
          {service.serviceName || service.name || 'Service Name'}
        </h3>
        
        <p className="text-gray-600 text-sm mb-3 line-clamp-2">
          {service.description || 'Service description not available'}
        </p>

        <div className="space-y-2 text-sm text-gray-600">
          {service.address && (
            <div className="flex items-start">
              <MapPin className="h-4 w-4 mr-2 text-red-500 mt-0.5 flex-shrink-0" />
              <span className="line-clamp-2">{service.address}</span>
            </div>
          )}
          
          {service.phoneNumber && (
            <div className="flex items-center">
              <Phone className="h-4 w-4 mr-2 text-green-500 flex-shrink-0" />
              <a 
                href={`tel:${service.phoneNumber}`}
                className="hover:text-green-600 transition-colors"
              >
                {service.phoneNumber}
              </a>
            </div>
          )}
          
          {service.email && (
            <div className="flex items-center">
              <Mail className="h-4 w-4 mr-2 text-blue-500 flex-shrink-0" />
              <a 
                href={`mailto:${service.email}`}
                className="hover:text-blue-600 transition-colors line-clamp-1"
              >
                {service.email}
              </a>
            </div>
          )}
          
          {service.workingHours && (
            <div className="flex items-center">
              <Clock className="h-4 w-4 mr-2 text-purple-500 flex-shrink-0" />
              <span className="line-clamp-1">{service.workingHours}</span>
            </div>
          )}
        </div>

        {/* Rating Section */}
        {service.rating && (
          <div className="flex items-center mt-3 pt-3 border-t border-gray-100">
            <div className="flex items-center">
              <Star className="h-4 w-4 text-yellow-400 fill-current" />
              <span className="ml-1 text-sm font-medium">{service.rating}</span>
            </div>
            {service.reviewCount && (
              <span className="ml-2 text-sm text-gray-500">
                ({service.reviewCount} review{service.reviewCount !== 1 ? 's' : ''})
              </span>
            )}
          </div>
        )}

        {/* Action Button */}
        <button 
          onClick={handleViewDetails}
          className="w-full mt-4 bg-red-500 text-white py-2 px-4 rounded-lg hover:bg-red-600 transition duration-200 font-medium"
        >
          View Details
        </button>
      </div>
    </div>
  );
};

export default ServiceCard;