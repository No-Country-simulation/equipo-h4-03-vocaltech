import { Outlet } from "react-router-dom";
//import vocaltechLogo from "../assets/logo_vocaltech.svg";
import vocaltechIcon from "../assets/icon_vocaltech.svg";
import vocaltechLetters from "../assets/logo_letters_vocaltech.svg";

const AppLayout = () => {
  return (
    <>
      <main className="relative flex items-center justify-center w-full min-h-screen py-20">
        {/* <img
          src={vocaltechLogo}
          alt="logo"
          className="absolute h-80 opacity-60"
        /> */}
        <div className="absolute flex flex-col items-center gap-10 opacity-60">
          <img src={vocaltechIcon} alt="logo" className="h-60" />
          <img src={vocaltechLetters} alt="logo" className="h-60" />
        </div>
        <Outlet />
      </main>
    </>
  );
};

export default AppLayout;
