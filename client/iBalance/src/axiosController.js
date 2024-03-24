// 현재 작업중이 axios Interceptor로직, 테스트 코드 작성중

import store from "./store";
import { setToken } from "./store";

import axios from "axios";

const customAxios = axios.create({
  baseURL: "https://j10d108.p.ssafy.io/api/",
  timeout: 2000,
  headers: {
    Authorization: `${-1}`,
    "Content-Type": "application/json",
  },
  withCredentials: true,
});
// `${store.getState().token}`
const root = JSON.parse(localStorage.getItem("persist:root"));
export const token = root["token"];
customAxios.interceptors.response.use(
  // 성공시 콜백
  (res) => res,
  // 실패시 콜백
  async (err) => {
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
