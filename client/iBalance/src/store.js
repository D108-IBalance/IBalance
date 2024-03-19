import { configureStore, createSlice } from "@reduxjs/toolkit";

const token = createSlice({
  name: "token",
  initialState: "",
  reducers: {
    setToken: (state, newToken) => newToken.payload,
  },
});

export const { setToken } = token.actions;

export default configureStore({
  reducer: {
    token: token.reducer,
  },
});
