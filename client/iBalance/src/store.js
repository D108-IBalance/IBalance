import { configureStore, createSlice } from "@reduxjs/toolkit";
import { combineReducers } from "@reduxjs/toolkit";
import storage from "redux-persist/lib/storage";
import {
  persistReducer,
  FLUSH,
  REHYDRATE,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER,
} from "redux-persist";

const token = createSlice({
  name: "token",
  initialState: "",
  reducers: {
    setToken: (state, newToken) => newToken.payload,
  },
});

const childId = createSlice({
  name: "childId",
  initialState: "",
  reducers: {
    setChildId: (state, id) => id.payload,
  },
});
const reducers = combineReducers({
  token: token.reducer,
  childId: childId.reducer,
});
const persistConfig = {
  key: "root",
  storage,
  whitelist: ["token", "childId"],
};
const persist = persistReducer(persistConfig, reducers);

export const { setToken } = token.actions;
export const { setChildId } = childId.actions;

export default configureStore({
  reducer: persist,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoreActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
      },
    }),
});
