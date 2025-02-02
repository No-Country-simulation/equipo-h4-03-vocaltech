import { BiSolidUserVoice } from "react-icons/bi";
import {
  FaBuilding,
  FaChalkboardTeacher,
  FaCheckSquare,
  FaMedal,
  FaRegUser,
} from "react-icons/fa";
import { GiWallet } from "react-icons/gi";
import { MdBarChart } from "react-icons/md";
import { RiChatVoiceAiLine } from "react-icons/ri";

interface Card {
  id: number;
  icon: React.ElementType;
  title: string;
  content: string;
}

export const services: Card[] = [
  {
    id: 1,
    icon: FaCheckSquare,
    title: "Desarrollamos tu MVP en 5 semanas",
    content:
      "Organiza tu Hackathon y obtén MVPs funcionales, documentados y testeados.",
  },
  {
    id: 2,
    icon: FaChalkboardTeacher,
    title: "Entrenamiento personalizado",
    content:
      "Individual o grupal. Sesiones a medida para perfeccionar tus habilidades vocales.",
  },
  {
    id: 3,
    icon: FaBuilding,
    title: "Capacitación para empresas",
    content:
      "Programas de formación diseñados para mejorar la comunicación interna y externa.",
  },
  {
    id: 4,
    icon: FaMedal,
    title: "Evidenciando talento junior",
    content:
      "Les ofrecemos a las empresas talento IT con habilidades comprobadas.",
  },
  {
    id: 5,
    icon: BiSolidUserVoice,
    title: "Liderar a través de la voz",
    content:
      "Curso para empresas y profesionales. Transformá tu comunicación y liderazgo a través del poder de tu voz.",
  },
  {
    id: 6,
    icon: RiChatVoiceAiLine,
    title: "Charlas inspiradoras",
    content:
      "Conferencias dinámicas y motivacionales que destacan el poder de la voz como herramienta de liderazgo.",
  },
  {
    id: 7,
    icon: MdBarChart,
    title: "Fortalecer la voz de la empresa",
    content:
      "Programa: Empresa Familiar. Fortalecé la cohesión y la comunicación dentro de tu empresa familiar.",
  },
  {
    id: 8,
    icon: FaRegUser,
    title: "Coaching individual",
    content:
      "Para emprendedores. Desarrolla una comunicación efectiva que impulse tu negocio.",
  },
  {
    id: 9,
    icon: GiWallet,
    title: "Reducción de riesgos",
    content:
      "Reduce significativamente los costos, y riesgos asociados con la creacion de MVPs",
  },
];
