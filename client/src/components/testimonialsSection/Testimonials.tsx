import Slider from "../slider/Slider";
import bgImg from "../../assets/bg_img.png";

const Testimonials = () => {
  return (
    <div
      className="relative flex items-center justify-center py-32 min-h-dvh"
      id="testimonial"
    >
      <img
        src={bgImg}
        alt=""
        className="absolute inset-0 object-cover object-center h-full -z-10 opacity-20"
      />
      <div className="container flex flex-col items-center gap-20">
        <h3 className="text-5xl font-bold text-accent font-primary">
          Que dicen nuestros clientes
        </h3>
        <Slider />
      </div>
    </div>
  );
};

export default Testimonials;
