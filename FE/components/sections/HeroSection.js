import { Search, MapPin } from "lucide-react"

const HeroSection = ({ selectedCity }) => {
  return (
    <section className="bg-gradient-to-r from-yellow-400 to-red-500 py-12 md:py-20">
      <div className="container mx-auto px-4 text-center">
        <h1 className="text-3xl md:text-4xl font-bold text-white mb-6">Find the Best Services & Businesses Near You</h1>
        <div className="max-w-3xl mx-auto">
          <div className="flex flex-col md:flex-row bg-white rounded-lg shadow-lg overflow-hidden">
            <div className="flex items-center px-4 py-2 border-b md:border-b-0 md:border-r border-gray-200">
              <MapPin className="h-5 w-5 text-gray-500 mr-2" />
              <select className="bg-transparent border-none focus:outline-none text-gray-700 w-full">
                <option>{selectedCity}</option>
                <option>Bangalore</option>
                <option>Chennai</option>
              </select>
            </div>
            <div className="flex-grow flex items-center px-4 py-2">
              <Search className="h-5 w-5 text-gray-500 mr-2" />
              <input
                type="text"
                placeholder="Search for restaurants, hotels, doctors..."
                className="w-full bg-transparent border-none focus:outline-none text-gray-700"
              />
            </div>
            <button className="bg-red-500 text-white px-6 py-3 font-medium hover:bg-red-600 transition duration-200">
              Search
            </button>
          </div>
        </div>
      </div>
    </section>
  )
}

export default HeroSection
