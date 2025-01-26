import {
  FaFacebookSquare,
  FaInstagramSquare,
  FaLinkedin,
} from "react-icons/fa";
import vocaltechIcon from "../../assets/icon_vocaltech.svg";
import vocaltechLogo from "../../assets/logo_letters_vocaltech.svg";
import { Link } from "react-router-dom";

const Footer = () => {
  // Función para volver al inicio
  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  return (
    <div className="bg-white h-[400px] flex justify-center items-center py-4">
      <div className="container flex flex-col justify-between h-full">
        <div className="flex items-center justify-around h-full gap-4">
          <div className="flex flex-col items-center gap-3 text-center">
            <h4 className="text-xl font-semibold">Nuestras redes</h4>
            <p className="max-w-[300px] text-primary/75">
              VocalTech le pone voz y tecnología a tu proyecto para lograr
              llevar tu idea al mercado
            </p>
            <div className="flex items-center gap-4 justify-evenly">
              <FaInstagramSquare className="w-10 h-10 cursor-pointer text-primary hover:text-accent" />
              <FaFacebookSquare className="w-10 h-10 cursor-pointer text-primary hover:text-accent" />
              <FaLinkedin className="w-10 h-10 cursor-pointer text-primary hover:text-accent" />
            </div>
          </div>
          <div className="flex flex-col items-center gap-4">
            <img
              src={vocaltechIcon}
              alt="logo"
              className="h-20 cursor-pointer"
              onClick={scrollToTop}
            />
            <img src={vocaltechLogo} alt="logo" className="h-10" />
          </div>
          <div className="flex flex-col items-center gap-3 text-center">
            <h4 className="text-xl font-semibold">
              Comunicarlo y hacerlo realidad!
            </h4>
            <p className="max-w-[300px] text-primary/75">
              Te invitamos a agendar una cita con nuestros especialistas para
              diseñar soluciones personalizadas. ¡Estamos aquí para ayudarte a
              crecer!
            </p>
            <Link
              to={"/diagnostico"}
              className="py-3 mt-5 font-semibold text-white transition-all rounded-lg shadow-2xl bg-accent-light hover:bg-accent px-7"
            >
              Quiero saber más
            </Link>
          </div>
        </div>
        <div className="w-full py-4 border-t border-primary/75">
          <p className="text-sm text-center text-primary/75">
            © 2021 All rights reserved. Privacy Policy
          </p>
        </div>
      </div>
    </div>
  );
};

export default Footer;
