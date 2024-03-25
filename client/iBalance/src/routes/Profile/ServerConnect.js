// 외부 모듈

import axios from "axios";

// 내부 모듈

// import customAxios, { token } from "../../axiosController";

const getProfile = async (TOKEN) => {
  const headers = {
    Authorization: `${TOKEN}`,
  };
  return axios.get("https://j10d108.p.ssafy.io/api/child", { headers });
};

const addProfile = async (TOKEN, profile) => {
  const headers = {
    Authorization: `${TOKEN}`,
    "Content-Type": `application/json`,
  };
  return axios.post(
    "https://j10d108.p.ssafy.io/api/child",
    JSON.stringify(profile),
    {
      headers,
    },
  );
};

const deleteProfile = async (TOKEN, id) => {
  const headers = {
    Authorization: `${TOKEN}`,
  };
  return axios.delete(`https://j10d108.p.ssafy.io/api/child/${id}`, {
    headers,
  });
};

// const getTestProfile = async () => {
//   return customAxios.get("child");
// };

export { getProfile, addProfile, deleteProfile };
