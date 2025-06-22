import React, { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import ServiceCard from '../components/sections/ServiceCard';
import { Camera } from 'lucide-react';
const ServicesPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [services, setServices] = useState([]);
  const [loading, setLoading] = useState(true);
  
  const category = searchParams.get('category') || '';
  const postalCode = searchParams.get('postalCode') || '';
  const area = searchParams.get('area') || '';
const handleViewDetails = (service) => {
  navigate(`/service/${service.id}`, { state: { service } });
};

  useEffect(() => {
    const fetchServices = async () => {
      if (!category || !postalCode) {
        toast.error("Invalid search parameters");
        navigate('/');
        return;
      }

      try {
        const response = await fetch(
          `http://localhost:8080/api/services/getByCategory?postalCode=${postalCode}&categoryName=${encodeURIComponent(category)}`,
          {
            method: "GET",
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        );
        
        if (response.ok) {
          const data = await response.json();
          setServices(data);
        } else {
          toast.error("Failed to fetch services");
        }
      } catch (error) {
        console.error("Error fetching services:", error);
        toast.error("Error loading services. Please try again.");
      } finally {
        setLoading(false);
      }
    };

    fetchServices();
  }, [category, postalCode, navigate]);

  if (loading) {
    return (
      <main className="flex-grow">
        <div className="min-h-screen flex items-center justify-center">
          <div className="text-center">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-red-500 mx-auto mb-4"></div>
            <p className="text-gray-600">Loading services...</p>
          </div>
        </div>
      </main>
    );
  }

  return (
    <main className="flex-grow bg-gray-50">
      {/* Header */}
      <div className="bg-white shadow-sm border-b">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div>
              <button
                onClick={() => navigate('/')}
                className="text-red-500 hover:text-red-600 font-medium mb-2 flex items-center"
              >
                ← Back to Search
              </button>
              <h1 className="text-2xl font-bold text-gray-800 capitalize">
                {category} Services
              </h1>
              <p className="text-gray-600">
                Found {services.length} result{services.length !== 1 ? 's' : ''} in {area} ({postalCode})
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* Services Grid */}
      <div className="container mx-auto px-4 py-8">
        {services.length === 0 ? (
          <div className="text-center py-12">
            <div className="text-gray-400 mb-4">
              <Camera className="h-16 w-16 mx-auto" />
            </div>
            <h3 className="text-xl font-medium text-gray-600 mb-2">
              No services found
            </h3>
            <p className="text-gray-500 mb-4">
              Try searching with different keywords or location
            </p>
            <button
              onClick={() => navigate('/')}
              className="bg-red-500 text-white px-6 py-2 rounded-lg hover:bg-red-600 transition duration-200"
            >
              Search Again
            </button>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {services.map((service, index) => (
              <ServiceCard key={service.id || index} 
              service={service} 
              onViewDetails={handleViewDetails} />
            ))}
          </div>
        )}
      </div>
    </main>
  );
};

export default ServicesPage;