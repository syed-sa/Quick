import React, { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import ServiceCard from '../components/sections/ServiceCard';
import { Camera } from 'lucide-react';
import api from '../components/auth/axios'; 
const ServicesPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [services, setServices] = useState([]);
  const [loading, setLoading] = useState(true);
  
  const category = searchParams.get('category') || '';
  const postalCode = searchParams.get('postalCode') || '';
  const area = searchParams.get('area') || '';


  useEffect(() => {
    const fetchServices = async () => {
      if (!category || !postalCode) {
        toast.error("Invalid search parameters");
        navigate('/');
        return;
      }
      const token = localStorage.getItem("token");

     try {
  const response = await api.get(
    "http://localhost:8080/api/services/getByCategory",
    {
      headers: {
        Authorization: `Bearer ${token}`, // ✅ Access token here
      },
      params: {
        postalCode,
        categoryName: category, // No need to manually encode — axios does it
      },
    }
  );
        if (response.status === 200) {
          const data = response.data;
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
    <div className="container mx-auto px-6 py-10">
      <div className="space-y-3">
        <button
          onClick={() => navigate('/')}
          className="text-red-500 hover:text-red-600 font-medium inline-flex items-center transition"
        >
          ← Back to Search
        </button>
        <h1 className="text-3xl font-semibold text-gray-800 capitalize">
          {category} Services
        </h1>
        <p className="text-gray-500 text-base">
          Found <span className="font-medium text-gray-700">{services.length}</span> result{services.length !== 1 ? 's' : ''} in <span className="font-medium text-gray-700">{area}</span> ({postalCode})
        </p>
      </div>
    </div>
  </div>

  {/* Services Grid */}
  <div className="container mx-auto px-6 py-10">
    {services.length === 0 ? (
      <div className="text-center py-20">
        <div className="text-gray-300 mb-6">
          <Camera className="h-16 w-16 mx-auto" />
        </div>
        <h3 className="text-2xl font-semibold text-gray-700 mb-2">
          No services found
        </h3>
        <p className="text-gray-500 mb-6">
          Try searching with different keywords or a nearby location.
        </p>
        <button
          onClick={() => navigate('/')}
          className="bg-red-500 text-white px-6 py-3 rounded-lg hover:bg-red-600 transition duration-200 text-sm font-medium"
        >
          🔍 Search Again
        </button>
      </div>
    ) : (
      <div className="grid gap-6 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
        {services.map((service, index) => (
          <ServiceCard key={service.id || index} service={service} />
        ))}
      </div>
    )}
  </div>
</main>

);

}
export default ServicesPage;