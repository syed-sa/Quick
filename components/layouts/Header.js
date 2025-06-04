import { useAuth } from "../../context/AuthContext";
import { Link } from "react-router-dom";
import { MapPin, User, Bell, Heart } from "lucide-react";
import { useState } from "react";

const Header = ({ selectedCity, setSelectedCity }) => {
  const { user, logout } = useAuth();
  const [dropdownOpen, setDropdownOpen] = useState(false);

  return (
    <header className="bg-white shadow-md">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between py-4">
          <Link to="/" className="text-2xl font-bold text-yellow-500">
            <span className="text-red-500">Just</span>Search
          </Link>

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

            <div className="flex items-center space-x-4 relative">
              {user ? (
                <div className="relative">
                  <button
                    onClick={() => setDropdownOpen(!dropdownOpen)}
                    className="flex items-center text-gray-600 hover:text-gray-900 focus:outline-none"
                  >
                    <User className="h-5 w-5 mr-1" />
                    <span>Hello, {user.email.split("@")[0]}</span>
                  </button>

                  {dropdownOpen && (
                    <div className="absolute right-0 mt-2 w-40 bg-white rounded-lg shadow-lg border z-50">
                      <Link
                        to="/profile"
                        className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                      >
                        Profile
                      </Link>
                      <button
                        onClick={logout}
                        className="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                      >
                        Logout
                      </button>
                    </div>
                  )}
                </div>
              ) : (
                <Link to="/auth" className="text-gray-600 hover:text-gray-900 flex items-center">
                  <User className="h-4 w-4 mr-1" />
                  <span>Login / Sign Up</span>
                </Link>
              )}

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
