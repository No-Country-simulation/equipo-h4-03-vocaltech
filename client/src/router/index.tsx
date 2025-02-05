import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import AppLayout from "../layout/AppLayout";
import Dashboard from "../pages/Dashboard";
import Diagnostic from "../pages/Diagnostic";
import Home from "../pages/Home";
import Login from "../pages/Login";
import NotFound from "../pages/NotFound";

export const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      {/* Rutas publicas */}
      <Route index path="/" element={<Home />} />
      {/* Rutas User */}

      <Route path="/" element={<AppLayout />} errorElement={<NotFound />}>
        <Route path="*" element={<NotFound />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/login" element={<Login />} />
        <Route path="/diagnostico" element={<Diagnostic />} />
      </Route>
    </>
  )
);
