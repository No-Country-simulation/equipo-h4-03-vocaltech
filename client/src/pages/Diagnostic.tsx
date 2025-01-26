import { Link } from "react-router-dom";
import TargetSection from "../components/targetSection/TargetSection";

const Diagnostic = () => {
  return (
    <div className="container flex flex-col items-center justify-center gap-20">
      <TargetSection />;
      <Link
        to={"/"}
        className="z-10 px-24 py-4 font-medium text-white transition-all rounded shadow-lg outline-none bg-accent-light hover:bg-accent"
      >
        Volver
      </Link>
    </div>
  );
};

export default Diagnostic;
