const Banner = () => {
  return (
    <div className="h-[350px] bg-cover bg-center bg-[url('assets/banner_img.jpg')] relative">
      <div className="absolute inset-0 flex items-center justify-center bg-black/75">
        <div className="container flex items-center justify-between">
          <div className="flex flex-col items-start justify-center gap-6">
            <p className="text-3xl font-bold text-white">
              ¿Listo para Transformar tu Comunicación y Tecnología?
            </p>
            <p className="text-white text-lg max-w-[500px]">
              Comienza tu cambio ahora con nuestro diagnóstico gratuito
              Suscríbete a nuestro newsletter y recibe una guía totalmente
              gratis en PDF!
            </p>
          </div>
          <div className="flex flex-col items-start gap-5 max-w-[500px]">
            <p className="mb-5 text-3xl font-bold text-white">
              Suscríbete a nuestro newsletter
            </p>
            <input
              type="email"
              className="w-full p-2 rounded-md"
              placeholder="Email"
            />
            <button className="self-end py-3 font-semibold text-white transition-all rounded-lg shadow-2xl bg-accent-light hover:bg-accent px-7">
              Quiero saber más
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Banner;
