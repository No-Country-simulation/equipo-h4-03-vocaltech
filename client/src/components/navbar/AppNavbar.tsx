import { Link } from "react-router-dom";
import logoVocaltech from "../../assets/logo_vocaltech.svg";
import { FaArrowLeft } from "react-icons/fa";

const AppNavbar = () => {
  return (
    <div className="fixed z-30 flex items-center justify-center w-full text-lg bg-white shadow-xl text-black/75">
      <div className="container flex items-center justify-between p-5">
        <Link to={"/"} className="flex items-center">
          <img src={logoVocaltech} alt="logo" className="h-10" />
        </Link>
        <Link
          to={"/"}
          className="flex items-center gap-3 py-3 font-semibold text-white transition-all rounded-lg shadow-2xl bg-accent-light hover:bg-accent px-7"
        >
          <FaArrowLeft />
          Volver
        </Link>
      </div>
    </div>
  );
};

export default AppNavbar;
