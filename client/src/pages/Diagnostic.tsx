import { useState } from "react";
import { ImCheckmark2 } from "react-icons/im";
import FormEmprendedor from "../components/forms/FormEmprendedor";
import FormEmpresa from "../components/forms/FormEmpresa";

const Diagnostic = () => {
  const [active, setActive] = useState<"empresa" | "emprendedor">(
    "emprendedor"
  );
  const handleButtonClick = (button: "empresa" | "emprendedor") => {
    setActive(button);
  };

  return (
    <div className="container flex flex-col items-center justify-center mt-40 mb-20 gap-28">
      <div className="flex items-center gap-20">
        <div className="flex items-center justify-center w-[800px] h-[550px] bg-primary/10 rounded-2xl shadow-xl">
          <div className="flex flex-col items-start justify-center gap-8">
            <p className="text-5xl font-semibold text-accent-light font-primary">
              Desbloqueá tu potencial
            </p>
            <p className="text-2xl max-w-[580px]">
              Te invitamos a agendar una cita con nuestros especialistas para
              diseñar soluciones personalizadas.
            </p>
            <p className="text-2xl font-semibold text-primary">
              ¡Estamos acá para ayudarte a crecer!
            </p>
          </div>
        </div>
        <div className="flex items-start justify-center gap-20 w-[650px] h-[550px] flex-col">
          <p className="text-xl font-medium">
            Este formulario de diagnóstico está diseñado para ayudarte a
            identificar las áreas de mejora en tu negocio y proporcionarte
            recomendaciones personalizadas para alcanzar tus objetivos.
          </p>
          <div className="flex flex-col gap-4 text-xl font-medium">
            <p>Al completar este formulario podrás</p>
            <div className="flex gap-2">
              <ImCheckmark2 className="text-primary" />
              <p className="font-bold">
                Identificar tus fortalezas y debilidades
              </p>
            </div>
            <div className="flex gap-2">
              <ImCheckmark2 className="text-primary" />
              <p className="font-bold">
                Descubrir oportunidades de crecimiento y mejora
              </p>
            </div>
            <div className="flex gap-2">
              <ImCheckmark2 className="text-primary" />
              <p className="font-bold">
                Recibir recomendaciones personalizadas para impulsar tu negocio
              </p>
            </div>
          </div>
          <p className="text-3xl font-semibold text-primary font-primary">
            ¡Completá este formulario y comenzá a transformar tu negocio hoy
            mismo!
          </p>
        </div>
      </div>
      {/* Formulario con renderizado condicional */}
      <div className="flex flex-col items-center gap-5">
        <div className="flex items-center self-start gap-4">
          <button
            className={`text-2xl font-semibold p-3  ${
              active === "emprendedor"
                ? "shadow border bg-accent-light text-white rounded-lg"
                : "text-black"
            }`}
            onClick={() => handleButtonClick("emprendedor")}
          >
            Emprendedor
          </button>
          <button
            className={`text-2xl font-semibold p-3 ${
              active === "empresa"
                ? " shadow border bg-accent-light text-white rounded-lg"
                : "text-black"
            }`}
            onClick={() => handleButtonClick("empresa")}
          >
            Empresa
          </button>
        </div>
        {active === "emprendedor" ? <FormEmprendedor /> : <FormEmpresa />}
      </div>
    </div>
  );
};

export default Diagnostic;
