// src/components/BookingCard.jsx
import React, { useState } from 'react';
import { toast } from 'react-toastify';
import api from '../auth/axios';

const BookingCard = ({ userId, serviceId, onClose }) => {
  const [date, setDate] = useState('');
  const [fromTime, setFromTime] = useState('');
  const [toTime, setToTime] = useState('');
  const [loading, setLoading] = useState(false);

  const timeOptions = Array.from({ length: 24 }, (_, i) => `${i.toString().padStart(2, '0')}:00`);

  const handleSubmit = async () => {
    if (!date || !fromTime || !toTime) {
      toast.error('Please select all fields.');
      return;
    }
    setLoading(true);
    try {
      const bookingDetails = { userId, serviceId, timeSlot: `${fromTime} - ${toTime}` };
      const token = localStorage.getItem('token');
      console.log("Booking payload", JSON.stringify(bookingDetails));
     const res = await api.post('bookservice/RequestBooking', bookingDetails, {
       headers: {
         Authorization: `Bearer ${token}`,
         'Content-Type': 'application/json'
       },
});
      if (res.status === 200 || res.status === 201) {
        toast.success('Booking successful!');
        onClose();
      } else toast.error('Booking failed.');
    } catch (e) {
      toast.error('Something went wrong.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div className="bg-white rounded-2xl shadow-2xl w-full max-w-md p-6 relative">
        <button onClick={onClose} className="absolute top-3 right-4 text-gray-500 hover:text-red-600 text-xl">&times;</button>
        <h2 className="text-xl font-bold mb-4">Book Service</h2>
        <label className="block mb-1">Date</label>
        <input type="date" value={date} onChange={e => setDate(e.target.value)} className="w-full border p-2 rounded-lg mb-4"/>
        <div className="flex gap-4 mb-4">
          <div className="flex-1">
            <label className="block mb-1">From</label>
            <select value={fromTime} onChange={e => setFromTime(e.target.value)} className="w-full border p-2 rounded-lg">
              <option value="">--</option>
              {timeOptions.map(t => <option key={t} value={t}>{t}</option>)}
            </select>
          </div>
          <div className="flex-1">
            <label className="block mb-1">To</label>
            <select value={toTime} onChange={e => setToTime(e.target.value)} className="w-full border p-2 rounded-lg">
              <option value="">--</option>
              {timeOptions.map(t => <option key={t} value={t}>{t}</option>)}
            </select>
          </div>
        </div>
        <button onClick={handleSubmit}
          disabled={loading}
          className="w-full bg-red-600 text-white py-2 rounded-lg hover:bg-red-700 disabled:opacity-50">
          {loading ? 'Booking...' : 'Submit Booking'}
        </button>
      </div>
    </div>
  );
};

export default BookingCard;
