// src/components/QRGenerator.tsx
import { useState } from "react";
import QRCode from "qrcode";

export default function QRGenerator() {
  const [url, setUrl] = useState<string>("");
  const [qrCode, setQRCode] = useState<string>("");
  const [error, setError] = useState<string>("");
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);

  // Función para validar si el texto es una URL válida
  const isValidUrl = (url: string) => {
    const urlPattern = /^https?:\/\/\S+/i; // Regex para validar URLs que comienzan con http:// o https://
    return urlPattern.test(url);
  };

  const generateQRCode = async () => {
    if (!url.trim()) {
      setError("Por favor, introduce una URL válida.");
      return;
    }

    if (!isValidUrl(url)) {
      setError(
        "La URL ingresada no es válida. Asegúrate de que comience con http:// o https://."
      );
      return;
    }

    try {
      const qrCodeData = await QRCode.toDataURL(url);
      setQRCode(qrCodeData);
      setError(""); // Limpiar el mensaje de error si hay éxito
      setIsModalOpen(true); // Abrir el modal
    } catch (error) {
      console.error("Error generating QR code:", error);
      setError("Hubo un error al generar el código QR. Inténtalo de nuevo.");
    }
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="space-y-4">
      <h2 className="text-2xl font-bold">Generador de Códigos QR</h2>
      <input
        type="text"
        placeholder="Introduce la URL (debe comenzar con http:// o https://)"
        value={url}
        onChange={(e) => {
          setUrl(e.target.value);
          setError(""); // Limpiar el mensaje de error al cambiar el texto
        }}
        className={`w-full p-2 border rounded ${
          error ? "border-red-500" : "border-gray-300"
        }`}
      />
      {error && <p className="text-red-500 text-sm">{error}</p>}
      <button
        onClick={generateQRCode}
        className="bg-blue-600 text-white p-2 rounded hover:bg-accent transition-colors"
      >
        Generar QR
      </button>

      {/* Modal para mostrar el código QR */}
      {isModalOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
          <div className="bg-white rounded-lg p-6 max-w-sm w-full">
            <h3 className="text-xl font-bold mb-4">Código QR Generado</h3>
            <img
              src={qrCode}
              alt="Generated QR Code"
              className="mx-auto mb-4"
            />
            <button
              onClick={closeModal}
              className="bg-blue-600 text-white p-2 rounded w-full hover:bg-accent transition-colors"
            >
              Cerrar
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
