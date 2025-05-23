const CategoriesSection = () => {
  const categories = [
    { name: "Restaurants", icon: "🍽️" },
    { name: "Hotels", icon: "🏨" },
    { name: "Doctors", icon: "👨‍⚕️" },
    { name: "Real Estate", icon: "🏢" },
    { name: "Travel", icon: "✈️" },
    { name: "Auto Services", icon: "🚗" },
    { name: "Home Services", icon: "🔧" },
    { name: "Education", icon: "🎓" },
  ]

  return (
    <section className="py-12 bg-gray-50">
      <div className="container mx-auto px-4">
        <h2 className="text-2xl font-bold text-gray-800 mb-8 text-center">Browse Categories</h2>
        <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-8 gap-4">
          {categories.map((category, index) => (
            <a
              key={index}
              href="#"
              className="flex flex-col items-center p-4 bg-white rounded-lg shadow-sm hover:shadow-md transition duration-200"
            >
              <div className="text-3xl mb-2">{category.icon}</div>
              <span className="text-sm font-medium text-gray-700">{category.name}</span>
            </a>
          ))}
        </div>
      </div>
    </section>
  )
}

export default CategoriesSection
