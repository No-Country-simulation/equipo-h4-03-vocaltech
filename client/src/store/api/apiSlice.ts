import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const apiSlice = createApi({
  reducerPath: "api",
  baseQuery: fetchBaseQuery({
    baseUrl: "https://vocaltech-production.up.railway.app/",
    credentials: "include",
    // Comprueba si existe un token y lo envia en el header
    /* prepareHeaders: (headers, { getState }) => {
      const token = getState().authToken.token;
      if (token) {
        headers.set('authorization', `Bearer ${token}`);
      }
      return headers;
    }, */
  }),
  endpoints: (builder) => ({
    // Login de un usuario
    loginUser: builder.mutation({
      query: (userData) => ({
        url: "api/auth/login",
        method: "POST",
        body: userData,
      }),
    }),
    // Logout
    logoutUser: builder.query({
      query: () => "api/auth/logout",
    }),
  }),
});

export const { useLoginUserMutation, useLazyLogoutUserQuery } = apiSlice;
