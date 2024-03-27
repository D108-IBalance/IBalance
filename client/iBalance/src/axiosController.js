// 외부 모듈
import axios from "axios";

// 내부 모듈
import store from "./store";
import { setToken } from "./store";

const customAxios = axios.create({
  baseURL: "https://j10d108.p.ssafy.io/api/",
  timeout: 2000,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

customAxios.interceptors.request.use((config) => {
  const token = store.getState().token;
  if (token) {
    config.headers.Authorization = token;
  }
  return config;
});

customAxios.interceptors.response.use(
  // 성공시 콜백
  (res) => res,
  // 실패시 콜백
  async (err) => {
    console.log(err);
    if (err.response.status === 401) {
      try {
        let value = await axios.post(
          "https://j10d108.p.ssafy.io/api/member/issue/access-token",
          {},
          { withCredentials: true },
        );
        const newToken = value.data.data.accessToken;
        store.dispatch(setToken(newToken));
        err.config.headers.Authorization = newToken;
        customAxios.defaults.headers.common.Authorization = newToken;
        customAxios.defaults.headers.Authorization = newToken;
        return axios(err.config);
      } catch (newErr) {
        console.log(newErr);
      }
    }
    return Promise.reject(err);
  },
);

export default customAxios;
