// 현재 작업중이 axios Interceptor로직, 테스트 코드 작성중

import store from "./store";
// import { setToken } from "./store";

import axios from "axios";

const customAxios = axios.create({
  baseURL: "https://j10d108.p.ssafy.io/api/",
  timeout: 2000,
  headers: {
    Authorization: `${store.getState().token}`,
    "Content-Type": "application/json",
  },
  withCredentials: true,
});
const root = JSON.parse(localStorage.getItem("persist:root"));
export const token = root["token"];
customAxios.interceptors.response.use(
  // 성공시 콜백
  (res) => {
    console.log("하하하하하핳");
    return res;
  },
  // 실패시 콜백
  (err) => {
    console.log("야야야야 이거 왜 되누?");
    return Promise.reject(err);
  },
);

export default customAxios;
