import { Search, MapPin } from "lucide-react";
import LocationAutocomplete from "../auth/LocationAutocomplete"; // Import the component
import { useState } from "react";

const HeroSection = ({ selectedCity }) => {
  const [selectedLocation, setSelectedLocation] = useState(null);

  const handleLocationSelect = ({ postalCode, area }) => {
    console.log("Selected Postal Code:", postalCode);
    console.log("Selected Area:", area);
    setSelectedLocation({ postalCode, area });
    // Optionally, you can auto-search or pass this to parent component
  };

  return (
    <section className="bg-gradient-to-r from-yellow-400 to-red-500 py-12 md:py-20">
      <div className="container mx-auto px-4 text-center">
        <h1 className="text-3xl md:text-4xl font-bold text-white mb-6">
          Find the Best Services & Businesses Near You
        </h1>
        <div className="max-w-3xl mx-auto">
          <div className="flex flex-col md:flex-row bg-white rounded-lg shadow-lg">
            <div className="flex items-center px-4 py-2 border-b md:border-b-0 md:border-r border-gray-200">
              <MapPin className="h-5 w-5 text-gray-500 mr-2" />
               <LocationAutocomplete onSelect={handleLocationSelect} />
            </div>

                        <div className="flex-1 relative">
                <div className="flex items-center bg-gray-50 rounded-xl px-4 py-3">
                  <Search className="h-5 w-5 text-gray-500 mr-3" />
                  <input
                    type="text"
                    placeholder="Search for services, restaurants, shops..."
                    className="w-full bg-transparent outline-none text-gray-700 placeholder-gray-500"
                  />
                </div>
              </div>

            <button
              className="bg-red-500 text-white px-6 py-3 font-medium hover:bg-red-600 transition duration-200"
              onClick={() => {
                if (selectedLocation) {
                  // You can call your service-search API here using postalCode
                  console.log("Search using:", selectedLocation);
                } else {
                  alert("Please select a location.");
                }
              }}
            >
              Search
            </button>
          </div>
        </div>
      </div>
    </section>
  );
};

export default HeroSection;


  // {/* Search Input */}
              // <div className="flex-1 relative">
              //   <div className="flex items-center bg-gray-50 rounded-xl px-4 py-3">
              //     <Search className="h-5 w-5 text-gray-500 mr-3" />
              //     <input
              //       type="text"
              //       value={searchQuery}
              //       onChange={(e) => setSearchQuery(e.target.value)}
              //       onKeyPress={handleKeyPress}
              //       placeholder="Search for services, restaurants, shops..."
              //       className="w-full bg-transparent outline-none text-gray-700 placeholder-gray-500"
              //       disabled={isSearching}
              //     />
              //   </div>
              // </div>

  //             {/* Search Button */}
  //             <button
  //               onClick={handleSearch}
  //               disabled={isSearching}
  //               className="bg-gradient-to-r from-red-500 to-red-600 text-white px-8 py-3 rounded-xl font-semibold hover:from-red-600 hover:to-red-700 transition-all duration-200 transform hover:scale-[1.02] disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none flex items-center justify-center min-w-[120px]"
  //             >
  //               {isSearching ? (
  //                 <>
  //                   <Loader2 className="h-5 w-5 mr-2 animate-spin" />
  //                   Searching...
  //                 </>
  //               ) : (
  //                 <>
  //                   <Search className="h-5 w-5 mr-2" />
  //                   Search
  //                 </>
  //               )}
  //             </button>
  //           </div>
  //         </div>