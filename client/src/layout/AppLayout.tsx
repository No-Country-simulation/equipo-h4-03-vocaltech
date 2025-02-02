import { Outlet } from "react-router-dom";
import AppNavbar from "../components/navbar/AppNavbar";
import AppFooter from "../components/footer/AppFooter";

const AppLayout = () => {
  return (
    <>
      <AppNavbar />
      <main className="flex items-center justify-center w-full min-h-screen">
        <Outlet />
      </main>
      <AppFooter />
    </>
  );
};

export default AppLayout;
