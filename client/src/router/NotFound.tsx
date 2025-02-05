import { useNavigate } from "react-router-dom";

const NotFound = () => {
  const navigate = useNavigate();

  const backToPage = () => {
    navigate(-1);
  };

  return (
    <div>
      <button onClick={backToPage}>Volver</button>
      <div className="text-3xl font-bold text-red-700">
        <h2>OCURRIO UN ERROR INESPERADO!</h2>
      </div>
    </div>
  );
};

export default NotFound;
