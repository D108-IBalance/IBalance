/* eslint-disable */

import axios from "axios";

const getProfile = async (TOKEN) => {
  console.log(TOKEN);
  const headers = {
    Authorization: `Bearer ${TOKEN}`,
  };
  return axios.get("https://j10d108.p.ssafy.io/api/child", {
    headers,
  });
};

const addProfile = async (profile, TOKEN) => {
  const headers = {
    Authorization: `Bearer ${TOKEN}`,
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

const deleteProfile = async (id, TOKEN) => {
  const headers = {
    Authorization: `Bearer ${TOKEN}`,
    "Content-Type": `application/json`,
  };
  return axios.delete(`http://localhost:8080/api/child/${id}`, { headers });
};

export { getProfile, addProfile, deleteProfile };
