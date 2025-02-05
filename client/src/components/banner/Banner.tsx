import { useState } from "react";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import NewsletterModal from "../modals/NewsletterModal";
import axios from "axios";

const Banner = () => {
  const MySwal = withReactContent(Swal);
  const [formData, setFormData] = useState({ name: "", email: "" });
  const [loading, setLoading] = useState(false);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setLoading(true);

    try {
      await axios.post(
        //"http://localhost:8090/api/leads",
        "https://vocaltech-production.up.railway.app/api/leads",
        {
          name: formData.name,
          email: formData.email,
        }
      );

      setFormData({ name: "", email: "" });

      MySwal.fire({
        html: <NewsletterModal />,
        showConfirmButton: false,
        width: "fit-content",
        scrollbarPadding: false,
      });
    } catch (error) {
      console.error("Error al enviar el formulario", error);
      MySwal.fire({
        title: "Error",
        text: "Hubo un problema al registrar tu email. Inténtalo nuevamente.",
        icon: "error",
      });
    } finally {
      setLoading(false);
    }
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
            type="text"
            name="name"
            required
            placeholder="Nombre"
            value={formData.name}
            onChange={handleChange}
            className="w-full p-2 rounded-md"
          />
          <input
            type="email"
            name="email"
            required
            placeholder="Email"
            value={formData.email}
            onChange={handleChange}
            className="w-full p-2 rounded-md"
          />
          <button
            type="submit"
            className="self-end py-3 font-semibold text-white transition-all rounded-lg shadow-2xl bg-accent-light hover:bg-accent px-7"
            disabled={loading}
          >
            {loading ? "Enviando..." : "Quiero más información"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default Banner;
