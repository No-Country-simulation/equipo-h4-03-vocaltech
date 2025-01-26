import { Link } from "react-router-dom";
import logoVocaltech from "../../assets/logo_vocaltech.svg";

const Navbar = () => {
  // Función para desplazarse a una sección
  const scrollToSection = (id: string) => {
    const element = document.getElementById(id); // Obtenemos el elemento por ID
    if (element) {
      element.scrollIntoView({ behavior: "smooth" });
    }
  };

  // Función para volver al inicio
  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  return (
    <div className="fixed z-30 flex items-center justify-center w-full text-lg bg-white shadow-xl text-black/75">
      <div className="container flex items-center justify-between p-6">
        <Link to={"/"} className="flex items-center" onClick={scrollToTop}>
          <img src={logoVocaltech} alt="logo" className="h-10" />
        </Link>
        <div className="flex items-center gap-10 font-medium">
          <button
            onClick={() => scrollToSection("about")}
            className="hover:text-primary"
          >
            Quienes somos
          </button>
          <button
            onClick={() => scrollToSection("services")}
            className="hover:text-primary"
          >
            Servicios
          </button>
          <button
            onClick={() => scrollToSection("testimonial")}
            className="hover:text-primary"
          >
            Testimonios
          </button>
          <Link to="/" className="hover:text-primary">
            Te podemos ayudar
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
