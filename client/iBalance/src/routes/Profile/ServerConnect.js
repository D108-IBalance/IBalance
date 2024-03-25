// 내부 모듈

import customAxios from "../../axiosController";

const getProfile = async () => {
  return customAxios.get("child");
};

const addProfile = async (profile) => {
  return customAxios.post("child", JSON.stringify(profile));
};

const deleteProfile = async (id) => {
  return customAxios.delete(`child/${id}`);
};

// const getTestProfile = async () => {
//   return customAxios.get("child");
// };

export { getProfile, addProfile, deleteProfile };
