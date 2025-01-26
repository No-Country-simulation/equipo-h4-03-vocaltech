//import codeIcon from "../../assets/code_icon.svg";
import { FaLightbulb, FaMedal, FaShieldAlt } from "react-icons/fa";

const ChoosingSection = () => {
  return (
    <div className="flex items-center justify-center h-[600px] bg-gray-100">
      <div className="container flex flex-col items-center justify-center gap-10">
        <h3 className="text-5xl font-bold text-accent font-primary">
          Por qué elegirnos
        </h3>
        <p className="text-center font-secondary max-w-[800px] text-xl">
          En <span className="font-medium text-primary">Vocaltech</span> te
          ofrecemos una solución integral que combina la comunicación efectiva
          con el desarrollo tecnológico de vanguardia.
        </p>
        <div className="flex flex-wrap items-center w-full gap-10 py-10 justify-evenly">
          <div className="flex items-start gap-5">
            <FaLightbulb className="w-12 h-12 fill-primary" />
            <div className="flex flex-col items-start gap-5 py-3 border-l-2 pl-7 w-80 border-primary">
              <p className="text-2xl font-bold text-primary">Inovación</p>
              <p className="">
                Somos una alianza innovadora entre dos marcas “Vos y tu Voz” y
                “No Country”
              </p>
            </div>
          </div>
          <div className="flex items-start gap-5">
            <FaShieldAlt className="w-12 h-12 fill-primary" />
            <div className="flex flex-col items-start gap-5 py-3 border-l-2 pl-7 w-80 border-primary">
              <p className="text-2xl font-bold text-primary">Confiabilidad</p>
              <p className="">
                Ambas empresas lideres en el desarrollo profesional y personal
              </p>
            </div>
          </div>
          <div className="flex items-start gap-5">
            <FaMedal className="w-12 h-12 fill-primary" />
            <div className="flex flex-col items-start gap-5 py-3 border-l-2 pl-7 w-80 border-primary">
              <p className="text-2xl font-bold text-primary">Experiencia</p>
              <p className="">
                Juntas combinamos nuestra experiencia y recursos para ofrecer
                soluciones efectivas
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChoosingSection;
