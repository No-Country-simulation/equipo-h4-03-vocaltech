import { useState } from "react";
import { services } from "../utils/servicesData.ts";

const OurServices = () => {
  const [seeMore, setSeeMore] = useState(false);

  return (
    <div
      className="flex items-center justify-center min-h-screen bg-gray-100 p-28"
      id="services"
    >
      <div className="container flex flex-col items-center h-full gap-20 justify-evenly">
        <h3 className="mb-10 text-5xl font-bold text-accent font-primary">
          Estos son nuestros servicios
        </h3>
        <div
          className={`flex items-center justify-evenly w-[1200px] flex-wrap gap-14 overflow-hidden pb-8 transition-all duration-500 ease-in-out ${
            seeMore ? "h-[1000px]" : "h-[650px]"
          }`}
        >
          {services.map((service) => {
            const Icon = service.icon;
            return (
              <div
                key={service.id}
                className="flex flex-col items-center h-[280px] text-center transition-colors duration-300 ease-in-out bg-white shadow-xl justify-evenly group p-7 w-[330px] rounded-2xl hover:bg-primary"
              >
                <Icon className="w-12 h-12 text-primary group-hover:text-white" />
                <p className="text-xl font-semibold text-primary group-hover:text-white font-primary">
                  {service.title}
                </p>
                <p className="text-sm group-hover:text-white">
                  {service.content}
                </p>
              </div>
            );
          })}
        </div>
        <button
          onClick={() => {
            setSeeMore(!seeMore);
          }}
          className="py-3 -mt-5 font-semibold text-white transition-all rounded-lg shadow-2xl bg-accent-light hover:bg-accent px-7"
        >
          Quiero ver más
        </button>
      </div>
    </div>
  );
};

export default OurServices;
