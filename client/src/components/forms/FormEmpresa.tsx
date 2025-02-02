import { Controller, useForm } from "react-hook-form";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import Greetings from "../modals/Greetings";

const FormEmpresa = () => {
  const MySwal = withReactContent(Swal);

  // Hook useForm
  const { handleSubmit, control, register, watch, reset } = useForm<FormData>();

  // Logica del POST y Logueo de usuario
  interface FormData {
    nombreEmpresa: string;
    sectorEmpresa: string;
    nombreContactoEmpresa: string;
    email: string;
    telefono?: string;
    tamanioEmpresa: string;
    objetivoEmpresa: string;
    quePrograma: string;
    audio: FileList;
    mvp: "si" | "no";
    etapaMVP?: string;
    talentoIT: "si" | "no";
    tipoPerfilIT?: string;
    especificarTipoPerfilIT?: string;
    recibirRecomendaciones: "si" | "no";
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

  const tipoPerfilIT = watch("tipoPerfilIT");

  return (
    <form className="w-[750px] flex flex-col gap-10">
      {/* Datos personales */}
      <div className="flex flex-col w-full gap-6 p-10 border-2 rounded-lg shadow-xl backdrop-blur-sm bg-secondary-light/50 border-primary/30">
        <p className="self-center mb-5 text-3xl font-semibold">
          Formulario Empresa
        </p>
        {/* Nombre de la empresa */}
        <div className="flex flex-col gap-1">
          <label htmlFor="nombreEmpresa" className="block text-sm font-medium">
            Nombre de la Empresa
          </label>
          <input
            type="text"
            id="nombreEmpresa"
            {...register("nombreEmpresa", { required: true })}
            className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
          />
        </div>
        {/* Sector */}
        <div className="flex flex-col gap-1">
          <label htmlFor="sectorEmpresa" className="block text-sm font-medium">
            Sector de la Empresa
          </label>
          <input
            type="text"
            id="sectorEmpresa"
            {...register("sectorEmpresa", { required: true })}
            className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
          />
        </div>
        {/* Tamaño de la empresa */}
        <div className="flex flex-col gap-1">
          <label className="block text-sm font-medium">
            Tamaño de la empresa
          </label>
          <Controller
            name="tamanioEmpresa"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <select
                {...field}
                className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
              >
                <option value="">Selecciona una opción</option>
                <option value="Chico">Menos de 10 empleados</option>
                <option value="Mediano">Entre 10 y 50 empleados</option>
                <option value="Grande">Más de 50 empleados</option>
              </select>
            )}
          />
        </div>
        {/* Nombre responsable RRHH */}
        <div className="flex flex-col gap-1">
          <label
            htmlFor="nombreContactoEmpresa"
            className="block text-sm font-medium"
          >
            Nombre del contacto (Responsable de la comunicación o RRHH)
          </label>
          <input
            type="text"
            id="nombreContactoEmpresa"
            {...register("nombreContactoEmpresa", { required: true })}
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
        {/* Objetivo de la empresa */}
        <div className="flex flex-col gap-1">
          <label className="block text-sm font-medium">
            ¿Cuál es el objetivo principal de la capacitación para tu empresa?
          </label>
          <Controller
            name="objetivoEmpresa"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <select
                {...field}
                className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
              >
                <option value="">Selecciona una opción</option>
                <option value="Comunicacion interna">
                  Mejorar la comunicación interna
                </option>
                <option value="Comunicacion externa">
                  Mejorar la comunicación externa
                </option>
                <option value="Liderazgo">
                  Mejorar el liderazgo y habilidades de los ejecutivos
                </option>
                <option value="Oratoria">
                  Mejorar las presentaciones y oratoria
                </option>
                <option value="Fortalecer">
                  Fortalecer la voz de la empresa
                </option>
              </select>
            )}
          />
        </div>
        {/* Que programa */}
        <div className="flex flex-col gap-1">
          <label className="block text-sm font-medium">
            ¿Qué tipo de programa te gustaría implementar?
          </label>
          <Controller
            name="quePrograma"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <select
                {...field}
                className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
              >
                <option value="">Selecciona una opción</option>
                <option value="Capacitacion">
                  Capacitación grupal para equipos
                </option>
                <option value="Entrenamiento">
                  Entrenamiento personalizado para ejecutivos
                </option>
                <option value="Coaching">
                  Coaching individual para líderes
                </option>
                <option value="Charlas">Charlas inspiradoras</option>
              </select>
            )}
          />
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
            ¿Está tu empresa interesada en desarrollar un MVP para un nuevo
            proyecto o servicio?
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
        {/* Etapa del MVP */}
        <div className="flex flex-col gap-1">
          <label className="block text-sm font-medium">
            ¿En qué etapa se encuentra el desarrollo de tu MVP?
          </label>
          <Controller
            name="etapaMVP"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <select
                {...field}
                className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
              >
                <option value="">Selecciona una opción</option>
                <option value="Idea inicial">Idea inicial</option>
                <option value="Desarrollo en curso">Desarrollo en curso</option>
                <option value="Ya se encuentra testeado">
                  Ya se encuentra testeado
                </option>
                <option value="No tengo un MVP definido">
                  No tengo un MVP definido
                </option>
              </select>
            )}
          />
        </div>
        {/* Talento junior */}
        <div className="flex flex-col gap-1">
          <span className="block text-sm font-medium">
            ¿Estás buscando incorporar talento IT junior a tu equipo de
            desarrollo?
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
        {/* Tipo de perfil IT */}
        <div className="flex flex-col gap-1">
          <label className="block text-sm font-medium">
            ¿Qué tipo de perfil IT necesitas para tu empresa?
          </label>
          <Controller
            name="tipoPerfilIT"
            control={control}
            defaultValue=""
            render={({ field }) => (
              <select
                {...field}
                className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
              >
                <option value="">Selecciona una opción</option>
                <option value="Desarrolladores Frontend">
                  Desarrolladores Frontend
                </option>
                <option value="Desarrolladores Backend">
                  Desarrolladores Backend
                </option>
                <option value="Desarrolladores Full-stack">
                  Desarrolladores Full-stack
                </option>
                <option value="QA/Testers">QA/Testers</option>
                <option value="Otro">Otro</option>
              </select>
            )}
          />
          {tipoPerfilIT === "Otro" && (
            <input
              type="text"
              placeholder="Especificar"
              {...register("especificarTipoPerfilIT")}
              className="w-full p-2 mt-1 rounded-md shadow-sm outline-none focus:ring-primary focus:ring-2"
            />
          )}
        </div>
        {/* Recibir recomendaciones */}
        <div className="flex flex-col gap-1">
          <span className="block text-sm font-medium">
            ¿Te gustaría recibir recomendaciones sobre posibles programas o
            servicios para mejorar tu comunicación y/o desarrollo de producto?
          </span>
          <div className="flex items-center mt-2 space-x-4">
            <label className="flex items-center">
              <input
                type="radio"
                value="si"
                {...register("recibirRecomendaciones")}
                className="w-4 h-4 outline-none accent-accent-light"
              />
              <span className="ml-2 text-sm">Sí</span>
            </label>
            <label className="flex items-center">
              <input
                type="radio"
                value="no"
                {...register("recibirRecomendaciones")}
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

export default FormEmpresa;
