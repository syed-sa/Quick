import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Mail, Phone, Globe, MapPin } from 'lucide-react';

const ServiceDetailPage = () => {
  const { state } = useLocation();
  const navigate = useNavigate();

  const service = state?.service;

  if (!service) {
    return (
      <div className="min-h-screen flex flex-col items-center justify-center text-center px-4">
        <h2 className="text-xl font-semibold text-gray-700 mb-2">No Service Data</h2>
        <p className="text-gray-500 mb-4">Please go back and select a service again.</p>
        <button
          onClick={() => navigate('/')}
          className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
        >
          Back to Search
        </button>
      </div>
    );
  }

  return (
    <main className="max-w-4xl mx-auto py-8 px-4">
      <button
        onClick={() => navigate(-1)}
        className="mb-4 text-red-500 hover:text-red-600 font-medium"
      >
        ← Back
      </button>

      <div className="bg-white shadow-md rounded-lg p-6">
        <h1 className="text-2xl font-bold text-gray-800 mb-2">{service.companyName}</h1>
        <p className="text-gray-600 mb-4">{service.address}</p>

        <div className="space-y-3 text-sm text-gray-700">
          <div className="flex items-center">
            <MapPin className="w-4 h-4 mr-2 text-red-500" />
            {service.city} - {service.postalCode}
          </div>
          {service.phone && (
            <div className="flex items-center">
              <Phone className="w-4 h-4 mr-2 text-green-500" />
              <a href={`tel:${service.phone}`} className="hover:underline">{service.phone}</a>
            </div>
          )}
          {service.email && (
            <div className="flex items-center">
              <Mail className="w-4 h-4 mr-2 text-blue-500" />
              <a href={`mailto:${service.email}`} className="hover:underline">{service.email}</a>
            </div>
          )}
          {service.website && (
            <div className="flex items-center">
              <Globe className="w-4 h-4 mr-2 text-purple-500" />
              <a href={service.website} target="_blank" rel="noreferrer" className="hover:underline">
                {service.website}
              </a>
            </div>
          )}
        </div>
      </div>
    </main>
  );
};

export default ServiceDetailPage;
