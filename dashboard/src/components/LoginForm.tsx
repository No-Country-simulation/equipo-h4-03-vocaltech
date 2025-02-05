// src/components/LoginForm.tsx
import { useState } from "react";
import api from "../lib/api";
import { AxiosError } from "axios";

export default function LoginForm() {
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Formulario enviado");

    // Validaciones del formulario
    if (!email || !password) {
      setError("Por favor, completa todos los campos.");
      return;
    }

    setIsLoading(true);
    setError(null);

    try {
      console.log("Enviando solicitud a la API...");
      const response = await api.post("/auth/login", { email, password });
      console.log("Respuesta de la API:", response.data);
      const data = response.data;

      if (data.token) {
        localStorage.setItem("token", data.token); // Guardar el token en localStorage
        setEmail(""); // Limpiar el campo de email
        setPassword(""); // Limpiar el campo de password
        window.location.href = "/dashboard"; // Redirigir al dashboard
      } else {
        setError("No se recibió un token válido.");
      }
    } catch (error) {
      if (error instanceof AxiosError) {
        if (error.response?.status === 401) {
          setError("Credenciales incorrectas.");
        } else {
          setError("Ocurrió un error al iniciar sesión. Inténtalo de nuevo.");
        }
      } else {
        setError("Ocurrió un error inesperado. Inténtalo de nuevo.");
      }
      console.error("Error en el login:", error);
    } finally {
      setIsLoading(false); // Finalizar carga
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-accent"
          required
        />
      </div>
      <div>
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-accent"
          required
        />
      </div>
      {error && <p className="text-red-500">{error}</p>}
      <button
        type="submit"
        className="w-full p-2 bg-primary text-white rounded hover:bg-accent transition-colors disabled:opacity-50"
        disabled={isLoading}
      >
        {isLoading ? "Cargando..." : "Login"}
      </button>
    </form>
  );
}
