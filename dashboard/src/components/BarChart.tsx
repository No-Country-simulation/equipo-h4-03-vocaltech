// src/components/BarChart.tsx
import { useEffect, useRef } from "react";
import Chart from "chart.js/auto";

export default function BarChart() {
  const chartRef = useRef<HTMLCanvasElement>(null);

  useEffect(() => {
    if (chartRef.current) {
      const ctx = chartRef.current.getContext("2d");
      if (ctx) {
        new Chart(ctx, {
          type: "bar",
          data: {
            labels: ["Enero", "Febrero", "Marzo", "Abril", "Mayo"],
            datasets: [
              {
                label: "Leads",
                data: [12, 19, 3, 5, 2],
                backgroundColor: "#5E66F2",
              },
            ],
          },
        });
      }
    }
  }, []);

  return <canvas ref={chartRef}></canvas>;
}
