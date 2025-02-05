// src/components/DataTable.tsx
import { useEffect, useState } from "react";
import {
  useReactTable,
  getCoreRowModel,
  flexRender,
} from "@tanstack/react-table";
import type { ColumnDef } from "@tanstack/react-table"; // Importación de solo tipo
import api from "../lib/api";

interface Data {
  id: string;
  name: string;
  email: string;
  phone: string;
  subscribed: boolean;
  recipientType: string;
  createdAt: string;
  moreInfo: boolean;
}

export default function DataTable() {
  const [leads, setLeads] = useState<Data[]>([
    {
      id: "uuid",
      name: "Juan Pérez",
      email: "juan@example.com",
      phone: "123456789",
      subscribed: true,
      recipientType: "Lead",
      createdAt: "2023-10-01",
      moreInfo: false,
    },
    {
      id: "UUID2",
      name: "María Gómez",
      email: "maria@example.com",
      phone: "987654321",
      subscribed: false,
      recipientType: "Lead",
      createdAt: "2023-10-02",
      moreInfo: true,
    },
  ]);

  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchLeads = async () => {
      try {
        const response = await api.get("/recipients");
        setLeads(response.data);
      } catch (error) {
        console.error("Error fetching leads:", error);
      } finally {
        setIsLoading(false); // Finaliza la carga
      }
    };
    fetchLeads();
  }, []);

  const columns: ColumnDef<Data>[] = [
    {
      header: "Nombre",
      accessorKey: "name",
    },
    {
      header: "Suscripción",
      accessorKey: "subscribed",
      cell: ({ getValue }) => (
        <span
          className={`inline-flex px-2 py-1 rounded-full text-xs font-semibold ${
            getValue()
              ? "bg-green-100 text-green-800"
              : "bg-red-100 text-red-800"
          }`}
        >
          {getValue() ? "Activo" : "Inactivo"}
        </span>
      ),
    },
    {
      header: "Tipo de Lead",
      accessorKey: "recipientType",
    },
    {
      header: "Fecha",
      accessorKey: "createdAt",
    },
    {
      header: "Más información",
      accessorKey: "moreInfo",
      cell: ({ getValue }) => (
        <span
          className={`inline-flex px-2 py-1 rounded-full text-xs font-semibold ${
            getValue()
              ? "bg-green-100 text-green-800"
              : "bg-red-100 text-red-800"
          }`}
        >
          {getValue() ? "Sí" : "No"}
        </span>
      ),
    },
  ];

  const table = useReactTable({
    data: leads,
    columns,
    getCoreRowModel: getCoreRowModel(),
  });

  if (isLoading) {
    return <div className="text-center py-4">Cargando...</div>;
  }

  return (
    <div className="shadow-lg rounded-lg overflow-hidden">
      <table className="w-full">
        <thead className="bg-gray-50">
          {table.getHeaderGroups().map((headerGroup) => (
            <tr key={headerGroup.id}>
              {headerGroup.headers.map((header) => (
                <th
                  key={header.id}
                  className="p-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  {flexRender(
                    header.column.columnDef.header,
                    header.getContext()
                  )}
                </th>
              ))}
            </tr>
          ))}
        </thead>
        <tbody className="bg-white divide-y divide-gray-200">
          {table.getRowModel().rows.map((row) => (
            <tr key={row.id} className="hover:bg-gray-50 transition-colors">
              {row.getVisibleCells().map((cell) => (
                <td key={cell.id} className="p-3 text-sm text-gray-900">
                  {flexRender(cell.column.columnDef.cell, cell.getContext())}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
