// src/components/ReservaCitas.tsx
export default function ReservaCitas() {
  return (
    <div className="p-6 bg-white rounded-lg shadow-md">
      <iframe
        src="https://cal.com/vocaltech-cba" // Reemplaza con tu nombre de usuario de Cal.com
        style={{ width: "100%", height: "600px", border: "none" }}
        title="Reserva de citas"
      />
    </div>
  );
}
