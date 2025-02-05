// src/components/Campaigns.tsx
import { useState, useEffect } from "react";

interface Campaign {
  id: number;
  name: string;
  description: string;
  targetEmails: string[];
}

export default function Campaigns() {
  const [campaigns, setCampaigns] = useState<Campaign[]>([]);
  const [newCampaign, setNewCampaign] = useState<Omit<Campaign, "id">>({
    name: "",
    description: "",
    targetEmails: [],
  });

  useEffect(() => {
    // Simulación de carga de campañas desde la API
    const fetchCampaigns = async () => {
      const token = localStorage.getItem("token");
      const response = await fetch("https://tu-api.com/campaigns", {
        headers: { Authorization: `Bearer ${token}` },
      });
      const data: Campaign[] = await response.json();
      setCampaigns(data);
    };
    fetchCampaigns();
  }, []);

  const handleCreateCampaign = async () => {
    const token = localStorage.getItem("token");
    const response = await fetch("https://tu-api.com/campaigns", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(newCampaign),
    });
    const data: Campaign = await response.json();
    setCampaigns([...campaigns, data]);
    setNewCampaign({ name: "", description: "", targetEmails: [] });
  };

  return (
    <div className="space-y-4">
      <h2 className="text-2xl font-bold">Campañas de Marketing</h2>

      {/* Formulario para crear una nueva campaña */}
      <div className="bg-white p-4 rounded shadow">
        <h3 className="text-lg font-semibold">Crear Nueva Campaña</h3>
        <input
          type="text"
          placeholder="Nombre"
          value={newCampaign.name}
          onChange={(e) =>
            setNewCampaign({ ...newCampaign, name: e.target.value })
          }
          className="w-full p-2 border rounded mb-2"
        />
        <textarea
          placeholder="Descripción"
          value={newCampaign.description}
          onChange={(e) =>
            setNewCampaign({ ...newCampaign, description: e.target.value })
          }
          className="w-full p-2 border rounded mb-2"
        />
        <button
          onClick={handleCreateCampaign}
          className="bg-primary text-white p-2 rounded"
        >
          Crear Campaña
        </button>
      </div>

      {/* Lista de campañas */}
      <div className="bg-white p-4 rounded shadow">
        <h3 className="text-lg font-semibold">Campañas Existentes</h3>
        <ul>
          {campaigns.map((campaign) => (
            <li key={campaign.id} className="border-b py-2">
              <h4 className="font-bold">{campaign.name}</h4>
              <p>{campaign.description}</p>
              <p>
                <strong>Emails:</strong> {campaign.targetEmails.join(", ")}
              </p>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}
