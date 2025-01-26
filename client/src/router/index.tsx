import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import AppLayout from "../layout/AppLayout";
import Dashboard from "../pages/Dashboard";
import FormEmprendedor from "../pages/FormEmprendedor";
import FormEmpresa from "../pages/FormEmpresa";
import Home from "../pages/Home";
import Login from "../pages/Login";
import NotFound from "./NotFound";
import Diagnostic from "../pages/Diagnostic";

export const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      {/* Rutas publicas */}
      <Route index path="/" element={<Home />} />
      <Route path="*" element={<NotFound />} />
      {/* Rutas User */}

      <Route path="/" element={<AppLayout />} errorElement={<NotFound />}>
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/login" element={<Login />} />
        <Route path="/diagnostico" element={<Diagnostic />} />
        <Route path="/diagnostico/empresa" element={<FormEmpresa />} />
        <Route path="/diagnostico/emprendedor" element={<FormEmprendedor />} />
      </Route>
    </>
  )
);
