import { Controller, useForm } from "react-hook-form";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import Greetings from "../modals/Greetings";
import axios from "axios";

const FormEmprendedor = () => {
  const MySwal = withReactContent(Swal);

  // Hook useForm
  const { handleSubmit, control, register, watch, reset } = useForm<FormData>();

  // Definición de la interfaz FormData
  interface FormData {
    name: string;
    email: string;
    phone?: string;
    type: string;
    description?: string;
    MVP: "si" | "no";
    productToDevelop: string;
    hireJunior: "si" | "no";
    moreInfo: "si" | "no";
    products: string[];
    audioFile: FileList;
  }

  // Función para manejar el envío del formulario
  const onSubmit = async (data: FormData) => {
    const formData = new FormData();

    formData.append("name", data.name);
    formData.append("email", data.email);
    if (data.phone) formData.append("phone", data.phone);
    formData.append("type", data.type);
    if (data.description) formData.append("description", data.description);
    formData.append("MVP", data.MVP === "si" ? "true" : "false");
    formData.append("productToDevelop", data.productToDevelop);
    formData.append("hireJunior", data.hireJunior === "si" ? "true" : "false");
    formData.append("moreInfo", data.moreInfo === "si" ? "true" : "false");
    data.products.forEach((product) => formData.append("products", product));
    if (data.audioFile && data.audioFile[0]) {
      formData.append("audioFile", data.audioFile[0]);
    }

    try {
      const response = await axios.post(
        //"http://localhost:8090/api/entrepreneurs",
        "https://vocaltech-production.up.railway.app/api/entrepreneurs",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      console.log("Response:", response.data);

      MySwal.fire({
        html: <Greetings />,
        showConfirmButton: false,
        width: "fit-content",
        scrollbarPadding: false,
      });

      reset();
    } catch (error) {
      console.error("Error submitting form:", error);
    }
  };

  const type = watch("type");
  const productToDevelop = watch("productToDevelop");

  return (
    <form
      className="w-[750px] flex flex-col gap-10"
      onSubmit={handleSubmit(onSubmit)}
    >
      {/* Datos personales */}
      <div className="flex flex-col w-full gap-6 p-10 border-2 rounded-lg shadow-xl backdrop-blur-sm bg-secondary-light/50 border-primary/30">
        <p className="self-center mb-5 text-3xl font-semibold">
          Formulario Emprendedor
        </p>
        <div className="flex flex-col gap-1">
          <label htmlFor="name" className="block text-sm font-medium">
            Nombre completo
          </label>
          <input
            type="text"
            id="name"
            {...register("name", { required: true })}
            className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
          />
        </div>
        <div className="flex items-center justify-between gap-4">
          <div className="flex flex-col w-full gap-1">
            <label htmlFor="email" className="block text-sm font-medium">
              Email
            </label>
            <input
              type="email"
              id="email"
              {...register("email", { required: true })}
              className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
            />
          </div>
          <div className="flex flex-col w-full gap-1">
            <label htmlFor="phone" className="block text-sm font-medium">
              Número de teléfono (opcional)
            </label>
            <input
              type="text"
              id="phone"
              {...register("phone")}
              className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
            />
          </div>
        </div>
      </div>

      {/* Tipo de emprendimiento */}
      <div className="flex flex-col w-full gap-6 p-10 border-2 rounded-lg shadow-xl backdrop-blur-sm bg-secondary-light/50 border-primary/30">
        <div className="flex flex-col gap-1">
          <label className="block text-sm font-medium">
            ¿Qué tipo de emprendimiento tenés?
          </label>
          <Controller
            name="type"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <select
                {...field}
                className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
              >
                <option value="">Selecciona una opción</option>
                <option value="STARTUP">Startup</option>
                <option value="SMALL_BUSINESS">Negocio pequeño/medio</option>
                <option value="FREELANCE">Freelance</option>
                <option value="OTHER">Otro</option>
              </select>
            )}
          />
          {type === "Otro" && (
            <input
              type="text"
              placeholder="Especificar"
              {...register("description")}
              className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
            />
          )}
        </div>

        {/* Desarrollar MVP */}
        <div className="flex flex-col gap-1">
          <label className="block text-sm font-medium">
            ¿Estás interesado/a en desarrollar un MVP para tu emprendimiento?
          </label>
          <div className="flex items-center mt-2 space-x-4">
            <label className="flex items-center">
              <input
                type="radio"
                value="si"
                {...register("MVP")}
                className="w-4 h-4 outline-none accent-accent-light"
              />
              <span className="ml-2 text-sm">Sí</span>
            </label>
            <label className="flex items-center">
              <input
                type="radio"
                value="no"
                {...register("MVP")}
                className="w-4 h-4 outline-none accent-accent-light"
              />
              <span className="ml-2 text-sm">No</span>
            </label>
          </div>
        </div>

        {/* Tipo de producto */}
        <div className="flex flex-col gap-1">
          <label className="block text-sm font-medium">
            ¿Qué tipo de producto o servicio estás buscando desarrollar?
          </label>
          <Controller
            name="productToDevelop"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <select
                {...field}
                className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
              >
                <option value="">Selecciona una opción</option>
                <option value="MOBILE_APP">App móvil</option>
                <option value="WEB_PLATFORM">Plataforma web</option>
                <option value="PHYSICAL_PRODUCT">Producto físico</option>
                <option value="OTHER">Otro</option>
              </select>
            )}
          />
          {productToDevelop === "Otro" && (
            <input
              type="text"
              placeholder="Especificar"
              {...register("description")}
              className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
            />
          )}
        </div>

        {/* Talento junior */}
        <div className="flex flex-col gap-1">
          <span className="block text-sm font-medium">
            ¿Estás buscando incorporar talento IT junior para tu equipo o
            proyecto?
          </span>
          <div className="flex items-center mt-2 space-x-4">
            <label className="flex items-center">
              <input
                type="radio"
                value="si"
                {...register("hireJunior")}
                className="w-4 h-4 outline-none accent-accent-light"
              />
              <span className="ml-2 text-sm">Sí</span>
            </label>
            <label className="flex items-center">
              <input
                type="radio"
                value="no"
                {...register("hireJunior")}
                className="w-4 h-4 outline-none accent-accent-light"
              />
              <span className="ml-2 text-sm">No</span>
            </label>
          </div>
        </div>

        {/* Más información */}
        <div className="flex flex-col gap-1">
          <span className="block text-sm font-medium">
            ¿Deseas recibir más información?
          </span>
          <div className="flex items-center mt-2 space-x-4">
            <label className="flex items-center">
              <input
                type="radio"
                value="si"
                {...register("moreInfo")}
                className="w-4 h-4 outline-none accent-accent-light"
              />
              <span className="ml-2 text-sm">Sí</span>
            </label>
            <label className="flex items-center">
              <input
                type="radio"
                value="no"
                {...register("moreInfo")}
                className="w-4 h-4 outline-none accent-accent-light"
              />
              <span className="ml-2 text-sm">No</span>
            </label>
          </div>
        </div>

        {/* Subir audio */}
        <div className="flex flex-col gap-1">
          <label htmlFor="audioFile" className="block text-sm font-medium">
            Subir un archivo de audio
          </label>
          <input
            type="file"
            id="audioFile"
            accept="audio/*"
            {...register("audioFile")}
            className="w-full text-sm cursor-pointer text-secondary file:mr-4 file:py-3 file:px-5 file:rounded-lg file:border-0 file:text-sm file:font-semibold file:bg-accent-light file:text-white hover:file:bg-accent file:cursor-pointer"
          />
        </div>
      </div>

      {/* Sección de selección de productos/servicios */}
      <div className="flex flex-col w-full gap-6 p-10 border-2 rounded-lg shadow-xl backdrop-blur-sm bg-secondary-light/50 border-primary/30">
        <p className="text-xl font-semibold">
          Selecciona los servicios o productos que te interesan:
        </p>
        <div className="grid grid-cols-2 gap-4">
          {[
            "Liderar a través de la voz",
            "Entrenamiento personalizado",
            "Coaching individual",
            "Pitch de ventas",
            "Storytelling",
            "Técnicas vocales",
            "MVP",
            "Contratación de talento IT junior",
          ].map((item) => (
            <label key={item} className="flex items-center gap-2">
              <input
                type="checkbox"
                value={item}
                {...register("products")}
                className="w-4 h-4"
              />
              <span>{item}</span>
            </label>
          ))}
        </div>
      </div>

      {/* Botón de envío */}
      <button
        type="submit"
        className="self-start px-24 py-4 text-white transition-all rounded shadow-md outline-none bg-accent-light hover:bg-accent"
      >
        Enviar
      </button>
    </form>
  );
};

export default FormEmprendedor;
