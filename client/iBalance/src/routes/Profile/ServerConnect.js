/* eslint-disable */

import secureLocalStorage from "react-secure-storage";

import axios from "axios";

const getProfile = async () => {
  const TOKEN = secureLocalStorage.getItem("token");
  const headers = {
    Authorization: `${TOKEN}`,
  };
  return axios.get("https://j10d108.p.ssafy.io/api/child", { headers });
};

const addProfile = async (profile) => {
  const TOKEN = secureLocalStorage.getItem("token");
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

const deleteProfile = async (id) => {
  const TOKEN = secureLocalStorage.getItem("token");
  const headers = {
    Authorization: `${TOKEN}`,
  };
  return axios.delete(`https://j10d108.p.ssafy.io/api/child/${id}`, {
    headers,
  });
};

export { getProfile, addProfile, deleteProfile };
