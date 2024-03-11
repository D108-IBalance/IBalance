/* eslint-disable */

import axios from "axios";

const getProfile = async () => {
  const headers = {
    // "Authorization" : `Bearer ${token}`,
  };
  return axios.get("http://localhost:8080/api/child", { headers });
};

const addProfile = async (profile) => {
  const headers = {
    // "Authorization" : `Bearer ${token}`,
    "Content-Type": `application/json`,
  };
  return axios.post(
    "http://localhost:8080/api/child",
    JSON.stringify(profile),
    {
      headers,
    },
  );
};

const deleteProfile = async (id) => {
  const headers = {
    // "Authorization" : `Bearer ${token}`,
    "Content-Type": `application/json`,
  };
  return axios.delete(`http://localhost:8080/api/child/${id}`, { headers });
};

export { getProfile, addProfile, deleteProfile };
