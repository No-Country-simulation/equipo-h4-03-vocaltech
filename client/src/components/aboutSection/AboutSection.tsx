import { useState } from "react";
import { MdKeyboardArrowLeft, MdKeyboardArrowRight } from "react-icons/md";
import vosImg from "../../assets/about_1.png";
import nocountryImg from "../../assets/about_2.png";
import nocountryLogo from "../../assets/logo_nocountry.jpg";
import vosLogo from "../../assets/VosytuVoz_logo.svg";

const AboutSection = () => {
  const [imgNocountry, setImgNocountry] = useState(false);

  return (
    <div className="flex items-center justify-center h-dvh" id="about">
      <div className="container flex flex-col items-center justify-center gap-20">
        <h3 className="text-5xl font-bold text-accent font-primary">
          Quienes somos
        </h3>
        <div className="relative flex items-center justify-center w-full gap-20">
          <img
            src={vosLogo}
            alt=""
            className={`absolute -bottom-28 left-28 rounded-full w-[200px] h-[200px] object-cover object-center shadow-2xl duration-500 ease-in-out transition-opacity ${
              imgNocountry ? "opacity-0" : "opacity-100"
            }`}
          />
          <img
            src={nocountryLogo}
            alt=""
            className={`absolute -bottom-28 left-28 rounded-full w-[200px] h-[200px] object-cover object-center shadow-2xl duration-500 ease-in-out transition-opacity ${
              imgNocountry ? "opacity-100" : "opacity-0"
            }`}
          />
          <div className="flex flex-col items-center gap-5 text-lg text-center max-w-[600px]">
            <p>
              <span className="font-medium text-primary">Vocaltech</span> es una
              alianza estrategica entre Vos y tu voz y No country dos empresas
              líderes en sus respectivos campos.. Entendemos que el éxito no se
              logra con soluciones aisladas.
            </p>
            <p>
              <span className="font-medium text-primary">Unimos</span> nuestras
              fuerzas para ofrecerte un servicio completo que abarca desde la
              estrategia de comunicación hasta la construcción de tu Mvp.
            </p>
            <p>
              <span className="font-medium text-primary">Vos y tu voz</span> te
              ayudará a comunicar tu propuesta de valor de manera clara y
              convincente, mientras que{" "}
              <span className="font-medium text-primary">No Country</span> se
              encargará de desarrollar la tecnología que necesitas para
              materializar tu visión.
            </p>
          </div>
          <div className="relative w-[500px] h-[500px]">
            <img
              src={vosImg}
              alt=""
              className={`absolute inset-0 object-cover object-center w-full h-full rounded-t-lg shadow-2xl duration-500 ease-in-out transition-opacity ${
                imgNocountry ? "opacity-0" : "opacity-100"
              }`}
            />
            <img
              src={nocountryImg}
              alt=""
              className={`absolute inset-0 object-cover object-center w-full h-full rounded-t-lg shadow-2xl duration-500 ease-in-out transition-opacity ${
                imgNocountry ? "opacity-100" : "opacity-0"
              }`}
            />
            <button
              onClick={() => setImgNocountry(false)}
              className="bg-accent/50 w-[60px] h-[70px] absolute bottom-0 right-full rounded-l-xl flex items-start"
            >
              <MdKeyboardArrowLeft className="w-10 h-10 text-white" />
            </button>
            <button
              onClick={() => setImgNocountry(true)}
              className="bg-primary/50 w-[60px] h-[70px] absolute bottom-0 left-0 rounded-r-xl flex items-end justify-end"
            >
              <MdKeyboardArrowRight className="w-10 h-10 text-white" />
            </button>
          </div>
        </div>
        <p className="text-xl italic text-center text-secondary/75">
          “Un enfoque integral que te ahorra tiempo, dinero y esfuerzo.”
        </p>
      </div>
    </div>
  );
};

export default AboutSection;
