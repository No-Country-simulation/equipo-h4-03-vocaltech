import { Controller, useForm } from "react-hook-form";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import Greetings from "../modals/Greetings";

const FormEmprendedor = () => {
  const MySwal = withReactContent(Swal);

  // Hook useForm
  const { handleSubmit, control, register, watch, reset } = useForm<FormData>();

  // Logica del POST y Logueo de usuario
  interface FormData {
    nombreCompleto: string;
    email: string;
    telefono?: string;
    tipoEmprendimiento: string;
    especificarEmprendimiento?: string;
    tipoNecesidad: string;
    especificarNecesidad?: string;
    tipoNecesidadArea: string;
    especificarNecesidadArea?: string;
    audio: FileList;
    mvp: "si" | "no";
    tipoServicio?: string;
    especificarServicio?: string;
    talentoIT: "si" | "no";
  }

  const onSubmit = (data: FormData) => {
    console.log("Form Data: ", data);

    MySwal.fire({
      html: <Greetings />,
      showConfirmButton: false,
      width: "fit-content",
      scrollbarPadding: false,
    });

    reset();
  };

  const tipoEmprendimiento = watch("tipoEmprendimiento");
  const tipoNecesidad = watch("tipoNecesidad");
  const tipoNecesidadArea = watch("tipoNecesidadArea");
  const tipoServicio = watch("tipoServicio");

  return (
    <form className="w-[750px] flex flex-col gap-10">
      {/* Datos personales */}
      <div className="flex flex-col w-full gap-6 p-10 border-2 rounded-lg shadow-xl backdrop-blur-sm bg-secondary-light/50 border-primary/30">
        {/* Full name */}
        <p className="self-center mb-5 text-3xl font-semibold">
          Formulario Emprendedor
        </p>
        <div className="flex flex-col gap-1">
          <label htmlFor="nombreCompleto" className="block text-sm font-medium">
            Nombre completo
          </label>
          <input
            type="text"
            id="nombreCompleto"
            {...register("nombreCompleto", { required: true })}
            className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
          />
        </div>
        {/* Email and Tel */}
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
            <label htmlFor="telefono" className="block text-sm font-medium">
              Número de télefono (opcional)
            </label>
            <input
              type="text"
              id="telefono"
              {...register("telefono")}
              className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
            />
          </div>
        </div>
      </div>
      {/* Datos para Vos y tu Voz */}
      <div className="flex flex-col w-full gap-6 p-10 border-2 rounded-lg shadow-xl backdrop-blur-sm bg-secondary-light/50 border-primary/30">
        {/* Tipo emprendimiento */}
        <div className="flex flex-col gap-1">
          <label className="block text-sm font-medium">
            ¿Qué tipo de emprendimiento tenés?
          </label>
          <Controller
            name="tipoEmprendimiento"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <select
                {...field}
                className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
              >
                <option value="">Selecciona una opción</option>
                <option value="Startup">Startup</option>
                <option value="Negocio pequeño/medio">
                  Negocio pequeño/medio
                </option>
                <option value="Freelance">Freelance</option>
                <option value="Otro">Otro</option>
              </select>
            )}
          />
          {tipoEmprendimiento === "Otro" && (
            <input
              type="text"
              placeholder="Especificar"
              {...register("especificarEmprendimiento")}
              className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
            />
          )}
        </div>
        {/* Que mejorar */}
        <div className="flex flex-col gap-1">
          <label className="block text-sm font-medium">
            ¿Qué es lo que más te gustaría mejorar en tu comunicación?
          </label>
          <Controller
            name="tipoNecesidad"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <select
                {...field}
                className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
              >
                <option value="">Selecciona una opción</option>
                <option value="Pitch de Ventas">Pitch de Ventas</option>
                <option value="Comunicación con Clientes">
                  Comunicación con Clientes
                </option>
                <option value="Storytelling">Storytelling</option>
                <option value="Liderazgo personal">Liderazgo personal</option>
                <option value="Otro">Otro</option>
              </select>
            )}
          />
          {tipoNecesidad === "Otro" && (
            <input
              type="text"
              placeholder="Especificar"
              {...register("especificarNecesidad")}
              className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
            />
          )}
        </div>
        {/* Que areas mejorar */}
        <div className="flex flex-col gap-1">
          <label className="block text-sm font-medium">
            ¿En qué área necesitás más ayuda?
          </label>
          <Controller
            name="tipoNecesidadArea"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <select
                {...field}
                className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
              >
                <option value="">Selecciona una opción</option>
                <option value="Oratoria">Oratoria</option>
                <option value="Técnicas vocales">Técnicas vocales</option>
                <option value="Liderazgo vocal">Liderazgo vocal</option>
                <option value="Comunicación efectiva">
                  Comunicación efectiva
                </option>
                <option value="Otro">Otro</option>
              </select>
            )}
          />
          {tipoNecesidadArea === "Otro" && (
            <input
              type="text"
              placeholder="Especificar"
              {...register("especificarNecesidadArea")}
              className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
            />
          )}
        </div>
        {/* Subir audio */}
        <div className="flex flex-col gap-1">
          <label htmlFor="audio" className="block text-sm font-medium">
            Subir un archivo de audio
          </label>
          <input
            type="file"
            id="audio"
            accept="audio/*"
            {...register("audio")}
            className="w-full text-sm cursor-pointer text-secondary file:mr-4 file:py-3 file:px-5 file:rounded-lg file:border-0 file:text-sm file:font-semibold file:bg-accent-light file:text-white hover:file:bg-accent file:cursor-pointer"
          />
        </div>
      </div>
      {/* Datos para No Country */}
      <div className="flex flex-col w-full gap-6 p-10 border-2 rounded-lg shadow-xl backdrop-blur-sm bg-secondary-light/50 border-primary/30">
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
                {...register("mvp")}
                className="w-4 h-4 outline-none accent-accent-light"
              />
              <span className="ml-2 text-sm">Sí</span>
            </label>
            <label className="flex items-center">
              <input
                type="radio"
                value="no"
                {...register("mvp")}
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
            name="tipoServicio"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <select
                {...field}
                className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
              >
                <option value="">Selecciona una opción</option>
                <option value="App móvil">App móvil</option>
                <option value="Plataforma web">Plataforma web</option>
                <option value="Producto físico">Producto físico</option>
                <option value="Otro">Otro</option>
              </select>
            )}
          />
          {tipoServicio === "Otro" && (
            <input
              type="text"
              placeholder="Especificar"
              {...register("especificarServicio")}
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
                {...register("talentoIT")}
                className="w-4 h-4 outline-none accent-accent-light"
              />
              <span className="ml-2 text-sm">Sí</span>
            </label>
            <label className="flex items-center">
              <input
                type="radio"
                value="no"
                {...register("talentoIT")}
                className="w-4 h-4 outline-none accent-accent-light"
              />
              <span className="ml-2 text-sm">No</span>
            </label>
          </div>
        </div>
      </div>
      <button
        type="submit"
        onClick={handleSubmit(onSubmit)}
        className="self-start px-24 py-4 text-white transition-all rounded shadow-md outline-none bg-accent-light hover:bg-accent"
      >
        Enviar
      </button>
    </form>
  );
};

export default FormEmprendedor;
