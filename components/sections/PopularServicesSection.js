const PopularServicesSection = () => {
  const popularServices = [
    { name: "AC Repair", image: "/images/ac-repair.jpg" },
    { name: "Plumbers", image: "/images/plumber.jpg" },
    { name: "Electricians", image: "/images/elelctrician.png" },
    { name: "House Cleaning", image: "/images/cleaning.jpg" },
    { name: "Car service", image: "/images/car-service.png" },
    { name: "Packers & Movers", image: "/images/packers-movers.png" },
  ]

  return (
    <section className="py-12">
      <div className="container mx-auto px-2">
        <h2 className="text-2xl font-bold text-gray-800 mb-8 text-center">Popular Services</h2>
        <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-8">
          {popularServices.map((service, index) => (
            <a
              key={index}
              href="#"
              className="flex flex-col items-center p-4 bg-white rounded-lg shadow-sm hover:shadow-md transition duration-200"
            >
              <img
                src={service.image || "/placeholder.svg"}
                alt={service.name}
                className="w-20 h-20 mb-3 rounded-full"
              />
              <span className="text-sm font-medium text-gray-700">{service.name}</span>
            </a>
          ))}
        </div>
      </div>
    </section>
  )
}

export default PopularServicesSection
