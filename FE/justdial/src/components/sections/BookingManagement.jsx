import React, { useState, useEffect } from 'react';
import { 
  Calendar, 
  Clock, 
  User, 
  Phone, 
  Mail, 
  MapPin, 
  Check, 
  X, 
  Eye, 
  Filter,
  Search,
  ChevronDown,
  AlertCircle,
  CheckCircle,
  XCircle
} from 'lucide-react';

const BookingManagement = () => {
  const [bookings, setBookings] = useState([]);
  const [filteredBookings, setFilteredBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedBooking, setSelectedBooking] = useState(null);
  const [statusFilter, setStatusFilter] = useState('all');
  const [searchTerm, setSearchTerm] = useState('');
  const [processingId, setProcessingId] = useState(null);

  // Mock data for demonstration
  useEffect(() => {
    const mockBookings = [
      {
        id: 1,
        customerName: 'Rahul Sharma',
        customerPhone: '+91 9876543210',
        customerEmail: 'rahul.sharma@email.com',
        serviceName: 'Home Cleaning Service',
        date: '2025-01-15',
        fromTime: '10:00 AM',
        toTime: '12:00 PM',
        status: 'pending',
        bookedAt: '2025-01-10T14:30:00Z',
        customerAddress: '123 MG Road, Bangalore',
        notes: 'Please bring vacuum cleaner and cleaning supplies',
        serviceCategory: 'Home Services'
      },
      {
        id: 2,
        customerName: 'Priya Patel',
        customerPhone: '+91 9876543211',
        customerEmail: 'priya.patel@email.com',
        serviceName: 'AC Repair Service',
        date: '2025-01-16',
        fromTime: '2:00 PM',
        toTime: '4:00 PM',
        status: 'accepted',
        bookedAt: '2025-01-11T09:15:00Z',
        customerAddress: '456 Brigade Road, Bangalore',
        notes: 'AC not cooling properly, might need gas refill',
        serviceCategory: 'Appliance Repair'
      },
      {
        id: 3,
        customerName: 'Amit Kumar',
        customerPhone: '+91 9876543212',
        customerEmail: 'amit.kumar@email.com',
        serviceName: 'Plumbing Service',
        date: '2025-01-14',
        fromTime: '9:00 AM',
        toTime: '11:00 AM',
        status: 'rejected',
        bookedAt: '2025-01-12T16:45:00Z',
        customerAddress: '789 Koramangala, Bangalore',
        notes: 'Kitchen tap leaking, urgent repair needed',
        serviceCategory: 'Home Services'
      },
      {
        id: 4,
        customerName: 'Sneha Gupta',
        customerPhone: '+91 9876543213',
        customerEmail: 'sneha.gupta@email.com',
        serviceName: 'Electrical Work',
        date: '2025-01-17',
        fromTime: '11:00 AM',
        toTime: '1:00 PM',
        status: 'pending',
        bookedAt: '2025-01-13T11:20:00Z',
        customerAddress: '321 Indiranagar, Bangalore',
        notes: 'Power socket installation in bedroom',
        serviceCategory: 'Electrical Services'
      }
    ];

    setTimeout(() => {
      setBookings(mockBookings);
      setFilteredBookings(mockBookings);
      setLoading(false);
    }, 1000);
  }, []);

  // Filter bookings based on status and search term
  useEffect(() => {
    let filtered = bookings;

    if (statusFilter !== 'all') {
      filtered = filtered.filter(booking => booking.status === statusFilter);
    }

    if (searchTerm) {
      filtered = filtered.filter(booking => 
        booking.customerName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        booking.serviceName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        booking.customerPhone.includes(searchTerm)
      );
    }

    setFilteredBookings(filtered);
  }, [bookings, statusFilter, searchTerm]);

  const handleAccept = async (bookingId) => {
    setProcessingId(bookingId);
    
    // Simulate API call
    setTimeout(() => {
      setBookings(prev => 
        prev.map(booking => 
          booking.id === bookingId 
            ? { ...booking, status: 'accepted' }
            : booking
        )
      );
      setProcessingId(null);
    }, 1000);
  };

  const handleReject = async (bookingId) => {
    setProcessingId(bookingId);
    
    // Simulate API call
    setTimeout(() => {
      setBookings(prev => 
        prev.map(booking => 
          booking.id === bookingId 
            ? { ...booking, status: 'rejected' }
            : booking
        )
      );
      setProcessingId(null);
    }, 1000);
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'pending': return 'bg-yellow-100 text-yellow-800';
      case 'accepted': return 'bg-green-100 text-green-800';
      case 'rejected': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  const getStatusIcon = (status) => {
    switch (status) {
      case 'pending': return <AlertCircle className="h-4 w-4" />;
      case 'accepted': return <CheckCircle className="h-4 w-4" />;
      case 'rejected': return <XCircle className="h-4 w-4" />;
      case 'cancelled': return <XCircle className="h-4 w-4" />;
      default: return null;
    }
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-IN', {
      weekday: 'short',
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    });
  };

  const formatBookedAt = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-IN', {
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-red-600 mx-auto mb-4"></div>
          <p className="text-gray-600">Loading bookings...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="bg-white rounded-lg shadow-sm border border-gray-200 mb-6">
          <div className="px-6 py-4">
            <h1 className="text-2xl font-bold text-gray-900">Booking Management</h1>
            <p className="text-gray-600 mt-1">Manage your service bookings</p>
          </div>
        </div>

        {/* Filters */}
        <div className="bg-white rounded-lg shadow-sm border border-gray-200 mb-6">
          <div className="px-6 py-4">
            <div className="flex flex-col md:flex-row gap-4">
              {/* Search */}
              <div className="flex-1 relative">
                <Search className="absolute left-3 top-3 h-4 w-4 text-gray-400" />
                <input
                  type="text"
                  placeholder="Search by customer name, service, or phone..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-red-500 focus:border-red-500"
                />
              </div>

              {/* Status Filter */}
              <div className="relative">
                <Filter className="absolute left-3 top-3 h-4 w-4 text-gray-400" />
                <select
                  value={statusFilter}
                  onChange={(e) => setStatusFilter(e.target.value)}
                  className="pl-10 pr-8 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-red-500 focus:border-red-500 appearance-none bg-white"
                >
                  <option value="all">All Status</option>
                  <option value="pending">Pending</option>
                  <option value="accepted">Accepted</option>
                  <option value="rejected">Rejected</option>
                </select>
                <ChevronDown className="absolute right-3 top-3 h-4 w-4 text-gray-400" />
              </div>
            </div>
          </div>
        </div>

        {/* Bookings List */}
        <div className="space-y-4">
          {filteredBookings.length === 0 ? (
            <div className="bg-white rounded-lg shadow-sm border border-gray-200 py-12">
              <div className="text-center">
                <Calendar className="h-12 w-12 text-gray-400 mx-auto mb-4" />
                <p className="text-gray-600">No bookings found</p>
              </div>
            </div>
          ) : (
            filteredBookings.map(booking => (
              <div key={booking.id} className="bg-white rounded-lg shadow-sm border border-gray-200 hover:shadow-md transition-shadow">
                <div className="p-6">
                  <div className="flex items-start justify-between">
                    <div className="flex-1">
                      <div className="flex items-center mb-3">
                        <div className="bg-red-100 p-2 rounded-lg mr-4">
                          <User className="h-6 w-6 text-red-600" />
                        </div>
                        <div>
                          <h3 className="text-lg font-semibold text-gray-900">{booking.customerName}</h3>
                          <p className="text-sm text-gray-600">{booking.serviceName}</p>
                        </div>
                        <div className={`ml-4 px-3 py-1 rounded-full text-xs font-medium flex items-center gap-1 ${getStatusColor(booking.status)}`}>
                          {getStatusIcon(booking.status)}
                          {booking.status.toUpperCase()}
                        </div>
                      </div>

                      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-4">
                        <div className="flex items-center text-gray-600">
                          <Calendar className="h-4 w-4 mr-2 text-red-500" />
                          <span className="text-sm">{formatDate(booking.date)}</span>
                        </div>
                        <div className="flex items-center text-gray-600">
                          <Clock className="h-4 w-4 mr-2 text-red-500" />
                          <span className="text-sm">{booking.fromTime} - {booking.toTime}</span>
                        </div>
                        <div className="flex items-center text-gray-600">
                          <Phone className="h-4 w-4 mr-2 text-red-500" />
                          <span className="text-sm">{booking.customerPhone}</span>
                        </div>
                        <div className="flex items-center text-gray-600">
                          <Mail className="h-4 w-4 mr-2 text-red-500" />
                          <span className="text-sm">{booking.customerEmail}</span>
                        </div>
                      </div>

                      <div className="flex items-start text-gray-600 mb-4">
                        <MapPin className="h-4 w-4 mr-2 text-red-500 mt-0.5" />
                        <span className="text-sm">{booking.customerAddress}</span>
                      </div>

                      {booking.notes && (
                        <div className="bg-gray-50 p-3 rounded-lg mb-4">
                          <p className="text-sm text-gray-700"><strong>Notes:</strong> {booking.notes}</p>
                        </div>
                      )}

                      <div className="flex items-center justify-between">
                        <div className="text-xs text-gray-500">
                          Booked on {formatBookedAt(booking.bookedAt)}
                        </div>
                        
                        <div className="flex space-x-2">
                          <button
                            onClick={() => setSelectedBooking(booking)}
                            className="inline-flex items-center px-3 py-1 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-red-500"
                          >
                            <Eye className="h-4 w-4 mr-1" />
                            View
                          </button>
                          
                          {booking.status === 'pending' && (
                            <>
                              <button
                                onClick={() => handleAccept(booking.id)}
                                disabled={processingId === booking.id}
                                className="inline-flex items-center px-3 py-1 bg-green-600 text-white rounded-md text-sm font-medium hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 disabled:opacity-50"
                              >
                                <Check className="h-4 w-4 mr-1" />
                                {processingId === booking.id ? 'Processing...' : 'Accept'}
                              </button>
                              <button
                                onClick={() => handleReject(booking.id)}
                                disabled={processingId === booking.id}
                                className="inline-flex items-center px-3 py-1 bg-red-600 text-white rounded-md text-sm font-medium hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500 disabled:opacity-50"
                              >
                                <X className="h-4 w-4 mr-1" />
                                {processingId === booking.id ? 'Processing...' : 'Reject'}
                              </button>
                            </>
                          )}
                          {booking.status === 'accepted' && (
                            <>
                              <button
                                onClick={() => handleReject(booking.id)}
                                disabled={processingId === booking.id}
                                className="inline-flex items-center px-3 py-1 bg-red-600 text-white rounded-md text-sm font-medium hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500 disabled:opacity-50"
                              >
                                <X className="h-4 w-4 mr-1" />
                                {processingId === booking.id ? 'Processing...' : 'Cancel'}
                              </button>
                            </>
                          )}
                          
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            ))
          )}
        </div>

        {/* Booking Details Modal */}
        {selectedBooking && (
          <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
            <div className="bg-white rounded-2xl shadow-2xl w-full max-w-lg p-6 relative max-h-[90vh] overflow-y-auto">
              <button 
                onClick={() => setSelectedBooking(null)} 
                className="absolute top-3 right-4 text-gray-500 hover:text-red-600 text-xl"
              >
                &times;
              </button>
              
              <h2 className="text-xl font-bold mb-4">Booking Details</h2>
              
              <div className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Customer Name</label>
                  <p className="text-gray-900">{selectedBooking.customerName}</p>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Service</label>
                  <p className="text-gray-900">{selectedBooking.serviceName}</p>
                </div>
                
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Date</label>
                    <p className="text-gray-900">{formatDate(selectedBooking.date)}</p>
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Time</label>
                    <p className="text-gray-900">{selectedBooking.fromTime} - {selectedBooking.toTime}</p>
                  </div>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Phone</label>
                  <p className="text-gray-900">{selectedBooking.customerPhone}</p>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
                  <p className="text-gray-900">{selectedBooking.customerEmail}</p>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Address</label>
                  <p className="text-gray-900">{selectedBooking.customerAddress}</p>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Status</label>
                  <div className={`inline-flex items-center px-3 py-1 rounded-full text-sm font-medium ${getStatusColor(selectedBooking.status)}`}>
                    {getStatusIcon(selectedBooking.status)}
                    <span className="ml-1">{selectedBooking.status.toUpperCase()}</span>
                  </div>
                </div>
                
                {selectedBooking.notes && (
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Notes</label>
                    <p className="text-gray-900 bg-gray-50 p-3 rounded-lg">{selectedBooking.notes}</p>
                  </div>
                )}
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Booked At</label>
                  <p className="text-gray-900">{formatBookedAt(selectedBooking.bookedAt)}</p>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default BookingManagement;