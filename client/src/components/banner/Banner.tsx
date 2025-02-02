import { useState } from "react";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import NewsletterModal from "../modals/NewsletterModal";

const Banner = () => {
  const MySwal = withReactContent(Swal);
  const [email, setEmail] = useState("");

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    console.log(email);
    setEmail("");

    MySwal.fire({
      html: <NewsletterModal />,
      showConfirmButton: false,
      width: "fit-content",
      scrollbarPadding: false,
    });
  };

  return (
    <div className="h-[400px] bg-gradient-to-r from-primary to-secondary-light flex items-center justify-center">
      <div className="container flex items-center justify-between">
        <div className="flex flex-col items-center justify-center gap-6 text-center text-white">
          <p className="text-5xl font-bold font-primary">
            ¿Listo para impulsar tu negocio?
          </p>
          <p className="text-2xl max-w-[600px]">
            Comenzá tu cambio ahora con nuestro diagnóstico. Registrate y recibí
            una guía totalmente gratuita
          </p>
        </div>
        <form
          className="flex flex-col items-start gap-5 max-w-[500px]"
          onSubmit={handleSubmit}
        >
          <p className="mb-5 text-2xl font-bold text-white font-primary">
            ¡No te pierdas de nuestras novedades!
          </p>
          <input
            type="email"
            required
            placeholder="Email"
            value={email}
            onChange={handleEmailChange}
            className="w-full p-2 rounded-md"
          />
          <button
            type="submit"
            className="self-end py-3 font-semibold text-white transition-all rounded-lg shadow-2xl bg-accent-light hover:bg-accent px-7"
          >
            Quiero más información
          </button>
        </form>
      </div>
    </div>
  );
};

export default Banner;
