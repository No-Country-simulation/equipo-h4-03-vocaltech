import { useState } from "react";
import {
  IoIosArrowDroprightCircle,
  IoIosArrowDropleftCircle,
} from "react-icons/io";
import { cards } from "../utils/sliderData.ts";

const Slider = () => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const lenght = cards.length;

  const handlePrevSlide = () =>
    setCurrentIndex(currentIndex === 0 ? lenght - 1 : currentIndex - 1);

  const handleNextSlide = () =>
    setCurrentIndex(currentIndex === lenght - 1 ? 0 : currentIndex + 1);

  const getTranslateX = (index: number) => {
    const diff = index - currentIndex;

    // centro
    if (diff === 0) return "translate-x-0 scale-110 z-10 opacity-100 active";
    // derecha
    if (diff === 1 || diff === -cards.length + 1)
      return "translate-x-[120%] scale-90 z-10 opacity-70";
    // derecha
    if (diff === 2) return "translate-x-[230%] scale-90 z-10 opacity-0";
    // izquierda
    if (diff === -2 && diff === -cards.length + 3)
      return "-translate-x-[230%] scale-90 z-10 opacity-0";
    // derecha
    if (diff === -2) return "translate-x-[230%] scale-90 z-10 opacity-0";
    // derecha
    if (diff === -3) return "translate-x-[230%] scale-90 z-10 opacity-0";
    // izquierda
    if (diff === -1 || diff === cards.length - 1)
      return "-translate-x-[120%] scale-90 z-10 opacity-70";
    // izquierda
    if (diff === 3) return "-translate-x-[230%] scale-90 z-10 opacity-0";
    return "hidden";
  };

  // Auto Slide
  /* useEffect(() => {
    const autoSlide = setInterval(() => {
      handleNextSlide();
    }, 5000);

    return () => clearInterval(autoSlide);
  }, [currentIndex]); */

  const handleCurrentIndex = (index: number) => setCurrentIndex(index);

  return (
    <div className="flex items-center justify-between w-full h-[800px] overflow-hidden">
      <div className="z-20" onClick={handlePrevSlide}>
        <IoIosArrowDropleftCircle className="w-20 h-20 cursor-pointer text-accent-light hover:text-accent" />
      </div>
      <div className="relative flex items-center justify-center -mb-20">
        {cards.map((item, index) => {
          return (
            <div
              key={item.id}
              className={`absolute flex flex-col items-center transition-transform duration-500 ease-in-out cursor-pointer ${getTranslateX(
                index
              )}`}
              onClick={() => handleCurrentIndex(index)}
            >
              <img
                src={item.img}
                alt=""
                className="h-[230px] w-[230px] rounded-full border-4 border-white absolute top-0 transform -translate-y-1/2 object-cover object-center"
              />
              <div className="w-[400px] h-[490px] bg-white rounded-3xl shadow-lg flex items-center justify-center px-14 pb-10 text-center hover:bg-primary hover:text-white text-primary transition-colors duration-300 ease-in-out">
                <p className="self-end text-lg">{item.content}</p>
              </div>
            </div>
          );
        })}
      </div>
      <div className="z-20" onClick={handleNextSlide}>
        <IoIosArrowDroprightCircle className="w-20 h-20 cursor-pointer text-accent-light hover:text-accent" />
      </div>
    </div>
  );
};

export default Slider;
