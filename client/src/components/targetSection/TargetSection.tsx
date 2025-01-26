import { useNavigate } from "react-router-dom";
import img1 from "../../assets/target_1.png";
import img2 from "../../assets/target_2.png";

const TargetSection = () => {
  const navigate = useNavigate();

  return (
    <div className="flex items-center justify-around w-full">
      <div className="relative">
        <div
          className="absolute inset-0 flex items-center justify-center cursor-pointer bg-gradient-to-b from-secondary/80 to-accent/50 rounded-3xl"
          onClick={() => {
            navigate("/diagnostico/empresa");
          }}
        >
          <p className="text-6xl font-semibold text-white font-secondary">
            Empresa
          </p>
        </div>
        <img src={img1} alt="" />
      </div>
      <div className="relative">
        <div
          className="absolute inset-0 flex items-center justify-center cursor-pointer bg-gradient-to-b from-secondary/80 to-accent/50 rounded-3xl"
          onClick={() => {
            navigate("/diagnostico/emprendedor");
          }}
        >
          <p className="text-6xl font-semibold text-white font-secondary">
            Emprendedor
          </p>
        </div>
        <img src={img2} alt="" />
      </div>
    </div>
  );
};

export default TargetSection;
