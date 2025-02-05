import ReactPlayer from "react-player";
import heroVideo from "../../assets/hero_video.mp4";
import { Link } from "react-router-dom";

const Hero = () => {
  return (
    <div className="relative h-screen bg-cover bg-center bg-[url('assets/hero_img4.jpg')]">
      <div className="absolute inset-0 flex items-center justify-center bg-black bg-opacity-75">
        <div className="container flex items-center justify-between">
          <div className="flex flex-col items-start gap-10">
            <p className="font-bold text-6xl max-w-[800px] bg-gradient-to-r from-primary to-accent text-transparent bg-clip-text font-primary pb-2">
              Impulsamos tu crecimiento con soluciones tecnológicas
            </p>
            <p className="text-2xl text-white max-w-[500px]">
              Con Vocaltech, transforma tu visión en un producto exitoso y
              alcanza tus objetivos comerciales.
            </p>
            <Link
              to={"/diagnostico"}
              className="py-3 mt-5 font-semibold text-white transition-all rounded-lg shadow-2xl bg-accent-light hover:bg-accent px-7"
            >
              Quiero saber más
            </Link>
          </div>
          <div className="flex items-center justify-center gap-20">
            <ReactPlayer
              url={heroVideo}
              playing={false}
              width="650px"
              height="400px"
              controls={true}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Hero;
