const Greetings = () => {
  return (
    <div className="flex flex-col items-center justify-center w-[1200px] h-[700px] gap-20 text-white bg-gradient-to-t to-accent-light from-primary">
      <p className="font-bold text-7xl font-primary">¡Gracias!</p>
      <p className="text-2xl font-medium max-w-[650px] text-center">
        Queremos agradecerte por confiar en nosotros y unirte a nuestra
        comunidad. Estamos comprometidos con brindarte contenido valioso y
        relevante para tu negocio.
      </p>
      <p className="text-2xl font-medium max-w-[650px] text-center">
        Brevemente recibirás un correo con los resultados y podrás agendar una
        cita con un especialista
      </p>
    </div>
  );
};

export default Greetings;
