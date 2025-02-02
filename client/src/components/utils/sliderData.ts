import userImg1 from "../../assets/user_1.png";
import userImg2 from "../../assets/user_2.png";
import userImg3 from "../../assets/user_3.png";
import userImg4 from "../../assets/user_4.png";
import userImg5 from "../../assets/user_5.png";

interface Card {
  id: number;
  img: string;
  content: string;
}

export const cards: Card[] = [
  {
    id: 1,
    img: userImg1,
    content:
      "Trabajar con Vocaltech ha sido una experiencia muy enriquecedora. Me ayudaron a mejorar mi comunicación dentro de la empresa, lo cual ha tenido un impacto positivo en mi trabajo en equipo. Pero lo que más valoro es que me ayudaron a ordenar mis ideas y a encontrar claridad en mi visión, lo que me ha dado mucha más confianza para seguir creciendo profesionalmente.",
  },
  {
    id: 2,
    img: userImg2,
    content:
      "¡Menos mal que encontré a Vocaltech! Estaba hecho un lío, con mil ideas en la cabeza pero sin saber cómo bajarlas a tierra. Gracias a ellos, pude comunicarme mejor con mi equipo y con mis clientes, y lo más importante, logré ordenar mis ideas para que mi empresa por fin empiece a tomar forma. ¡Ahora sí vamos con todo!",
  },
  {
    id: 3,
    img: userImg3,
    content:
      "¡Estoy feliz con los resultados que he obtenido con Vocaltech! Como emprendedor, a veces es difícil transmitir tu visión a otros, pero ellos me ayudaron a comunicarme de una manera mucho más clara y efectiva. Gracias a su apoyo, mis ideas finalmente están tomando forma y mi proyecto está avanzando a pasos agigantados. ¡Recomendadísimos!",
  },
  {
    id: 4,
    img: userImg4,
    content:
      "Trabajar con Vocaltech ha sido una experiencia muy enriquecedora. Me ayudaron a mejorar mi comunicación dentro de la empresa, lo cual ha tenido un impacto positivo en mi trabajo en equipo. Pero lo que más valoro es que me ayudaron a ordenar mis ideas y a encontrar claridad en mi visión, lo que me ha dado mucha más confianza para seguir creciendo profesionalmente.",
  },
  {
    id: 5,
    img: userImg5,
    content:
      "¡Menos mal que encontré a Vocaltech! Estaba hecho un lío, con mil ideas en la cabeza pero sin saber cómo bajarlas a tierra. Gracias a ellos, pude comunicarme mejor con mi equipo y con mis clientes, y lo más importante, logré ordenar mis ideas para que mi empresa por fin empiece a tomar forma. ¡Ahora sí vamos con todo!",
  },
];
