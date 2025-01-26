import { createSlice } from "@reduxjs/toolkit";
import { RootState } from "../store.ts";

interface Rol {
  email: string;
  rol: string;
}

interface InitialState {
  token: string;
  user: User;
}

interface User {
  id: number | null;
  nombre: string;
  apellido: string;
  picture: string;
  nombreCompleto: string;
  usuario: Rol;
}

// Estado inicial del usuario
const emptyUser: InitialState = {
  token: "",
  user: {
    id: 1,
    nombre: "Fabio",
    apellido: "A.",
    picture: "",
    nombreCompleto: "Fabio A.",
    usuario: {
      email: "FabioA@gmail.com",
      rol: "user",
    },
  },
};

// Slice de autenticación
export const authSlice = createSlice({
  name: "auth",
  initialState: localStorage.getItem("userInfo")
    ? JSON.parse(localStorage.getItem("userInfo") || "{}")
    : emptyUser,
  reducers: {
    setUserData: (state, { payload }) => {
      state.user = payload;
      localStorage.setItem("userInfo", JSON.stringify(state));
      return state;
    },
    setUserToken: (state, { payload }) => {
      state.token = payload;
      return state;
    },
  },
});

export const { setUserData, setUserToken } = authSlice.actions;

export default authSlice.reducer;

export const selectCurrentUser = (state: RootState) => state.auth.user;
export const selectTokenUser = (state: RootState) => state.auth.token;
