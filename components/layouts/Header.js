"use client";
import { Link } from "react-router-dom";
import { useState } from "react";
import { MapPin, Menu, X, User, Bell, Heart } from "lucide-react";
import AuthForm from "./../sections/AuthForm";
const Header = ({ selectedCity, setSelectedCity }) => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <header className="bg-white shadow-md">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between py-4">
          {/* Logo */}
          {/* Logo */}
          <div className="flex items-center">
            <Link
              to="/"
              className="text-2xl font-bold text-yellow-500 hover:cursor-pointer"
            >
              <span className="text-red-500">Just</span>Search
            </Link>
          </div>

          {/* Desktop Navigation */}
          <div className="hidden md:flex items-center space-x-6">
            <div className="flex items-center text-gray-600">
              <MapPin className="h-4 w-4 mr-1" />
              <select
                className="bg-transparent border-none focus:outline-none text-sm"
                value={selectedCity}
                onChange={(e) => setSelectedCity(e.target.value)}
              >
                <option>Bangalore</option>
                <option>Chennai</option>
              </select>
            </div>
            <div className="flex items-center space-x-4">
              <Link
                to="/auth"
                className="text-gray-600 hover:text-gray-900 flex items-center"
              >
                <User className="h-4 w-4 mr-1" />
                <span>Login / Sign Up</span>
              </Link>
              <a href="#" className="text-gray-600 hover:text-gray-900">
                <Bell className="h-5 w-5" />
              </a>

              <Link to="/favourites" className="text-gray-600 hover:text-gray-900">
                <Heart className="h-5 w-5" />
              </Link>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;
