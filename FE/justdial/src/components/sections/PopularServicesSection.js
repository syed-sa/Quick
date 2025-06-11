const PopularServicesSection = () => {
  const popularServices = [
    { name: "AC Repair", image: "/images/ac-repair.jpg" },
    { name: "Plumbers", image: "/images/plumber.jpg" },
    { name: "Electricians", image: "/images/elelctrician.png" },
    { name: "House Cleaning", image: "/images/cleaning.jpg" },
    { name: "Car Service", image: "/images/car-service.png" },
    { name: "Packers & Movers", image: "/images/packers-movers.png" },
  ];

  return (
    <section className="bg-gradient-to-br from-yellow-50 to-red-50 py-14 rounded-t-3xl">
      <div className="container mx-auto px-4">
        <div className="text-center mb-10">
          <h2 className="text-3xl font-bold text-gray-800 mb-8 text-center">Popular Services</h2>
          <p className="text-gray-600 text-sm mt-2">Trusted by thousands of users</p>
        </div>

        <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-6">
          {popularServices.map((service, index) => (
            <a
              key={index}
              href="#"
              className="group flex flex-col items-center bg-white p-5 rounded-xl shadow-sm hover:shadow-lg transition-all duration-200 hover:scale-[1.03]"
            >
              <div className="w-20 h-20 rounded-full overflow-hidden border border-gray-200 shadow-sm mb-3">
                <img
                  src={service.image || "/placeholder.svg"}
                  alt={service.name}
                  className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105"
                />
              </div>
              <span className="text-sm font-semibold text-gray-700 text-center">{service.name}</span>
            </a>
          ))}
        </div>
      </div>
    </section>
  );
};

export default PopularServicesSection;
