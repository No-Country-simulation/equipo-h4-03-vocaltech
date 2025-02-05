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
    <div className="bg-cover bg-center bg-[url('assets/hero_img4.jpg')] h-[400px] flex justify-center items-center relative">
      <div className="absolute inset-0 flex items-center justify-center bg-black bg-opacity-75">
        <div className="container flex flex-col justify-between h-full py-4 text-white">
          <div className="flex items-center justify-around h-full gap-4">
            <div className="flex flex-col items-center gap-4 text-center">
              <h4 className="text-xl font-semibold font-primary">
                Nuestras redes
              </h4>
              <p className="max-w-[300px] text-sm">
                VocalTech le pone voz y tecnología a tu proyecto para lograr
                llevar tu idea al mercado
              </p>
              <div className="flex items-center gap-4 mt-5 justify-evenly">
                <FaInstagramSquare className="w-10 h-10 text-white cursor-pointer hover:text-primary" />
                <FaFacebookSquare className="w-10 h-10 text-white cursor-pointer hover:text-primary" />
                <FaLinkedin className="w-10 h-10 text-white cursor-pointer hover:text-primary" />
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
              <h4 className="text-xl font-semibold font-primary">
                Comunicarlo y hacerlo realidad!
              </h4>
              <p className="max-w-[300px] text-sm">
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
          <div className="w-full py-4 border-t">
            <p className="text-sm text-center">
              © 2021 All rights reserved. Privacy Policy
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Footer;
