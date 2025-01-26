import { useState } from "react";
import { Link } from "react-router-dom";
import { useLoginUserMutation } from "../store/api/apiSlice";

const Login = () => {
  const [loginUser] = useLoginUserMutation();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const formData = { email: email, password: password };

    loginUser(formData);
  };

  return (
    <div className="container z-10 grid place-items-center">
      <div className="flex flex-row-reverse w-[900px] min-h-[600px] shadow-lg rounded overflow-hidden">
        <div className="relative w-1/2 bg-[url('assets/banner_img.jpg')] bg-cover bg-center rounded-s"></div>
        <div className="flex flex-col items-center justify-center w-1/2 gap-6 bg-white">
          <h2 className="text-4xl font-semibold text-primary">
            Iniciar sesión
          </h2>
          <form onSubmit={handleSubmit} className="flex flex-col items-center">
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={handleEmailChange}
              className="w-64 px-4 py-2 mb-4 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary"
            />
            <input
              type="password"
              placeholder="Contraseña"
              value={password}
              onChange={handlePasswordChange}
              className="w-64 px-4 py-2 mb-4 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary"
            />
            <button
              type="submit"
              className="px-5 py-3 text-white transition-all rounded shadow-md outline-none bg-accent-light hover:bg-accent"
            >
              Iniciar sesión
            </button>
            <p className="mt-5">
              No tienes una cuenta?{" "}
              <Link to="/" className="font-semibold text-primary">
                Volver
              </Link>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;
