import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useLoginUserMutation } from "../store/api/apiSlice";
import { FcGoogle } from "react-icons/fc";

const Login = () => {
  const navigate = useNavigate();
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
    setEmail("");
    setPassword("");
    navigate("/dashboard");
  };

  return (
    <div className="flex items-center justify-center w-full h-screen mt-5">
      <div className="flex flex-row-reverse w-[1200px] min-h-[700px] shadow-lg rounded overflow-hidden">
        <div className="relative w-1/2 bg-[url('assets/login_img.png')] bg-cover bg-center rounded-s"></div>
        <div className="flex flex-col items-center justify-center w-1/2 bg-white gap-14">
          <div className="flex flex-col items-center gap-3">
            <h2 className="text-5xl font-semibold text-primary font-primary">
              Bienvenido
            </h2>
            <p className="text-xl">Ingresar a tu cuenta</p>
          </div>
          <form
            onSubmit={handleSubmit}
            className="flex flex-col items-center gap-10 w-96"
          >
            <div className="flex flex-col w-full gap-1">
              <label htmlFor="email" className="font-medium">
                Email
              </label>
              <input
                name="email"
                type="email"
                placeholder="example@gmail.com"
                required
                value={email}
                onChange={handleEmailChange}
                className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary"
              />
            </div>
            <div className="flex flex-col w-full gap-1">
              <label htmlFor="password" className="font-medium">
                Contraseña
              </label>
              <input
                name="password"
                type="password"
                placeholder="* * * * * * * *"
                required
                value={password}
                onChange={handlePasswordChange}
                className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary"
              />
            </div>
            <div className="flex flex-col items-center w-full gap-3">
              <button
                type="submit"
                className="py-3 text-white transition-all rounded shadow-md outline-none px-7 bg-accent-light hover:bg-accent"
              >
                Ingresar
              </button>
              <div className="flex items-center gap-2">
                <p>O ingresar con</p>
                <FcGoogle className="text-3xl" />
              </div>
            </div>
            <p className="">
              ¿No tiene una cuenta?{" "}
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
