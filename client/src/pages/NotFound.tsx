import { TbFaceIdError } from "react-icons/tb";

const NotFound = () => {
  return (
    <div className="flex flex-col items-center justify-center gap-10">
      <h2 className="font-bold text-accent text-7xl">Ups algo salió mal!</h2>
      <TbFaceIdError className="text-red-700 text-9xl" />
    </div>
  );
};

export default NotFound;
