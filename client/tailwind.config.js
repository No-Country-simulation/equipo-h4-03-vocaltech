/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: "#5E66F2",
        },
        secondary: {
          DEFAULT: "#1C29FF",
          light: "#6695F2",
        },
        accent: {
          DEFAULT: "#F2059F",
          light: "#EE5BBB",
        },
      },
      fontFamily: {
        primary: ["Poppins", "serif"],
        secondary: ["Roboto", "serif"],
      },
    },
  },
  plugins: [],
};
