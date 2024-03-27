// 내부 모듈

import customAxios from "../../axiosController";

const getProfile = async () => {
  return customAxios.get(`child`);
};

const getChildProfile = async (idx) => {
  return customAxios.get(`child/main/${idx}`);
};

const addProfile = async (profile) => {
  return customAxios.post("child", JSON.stringify(profile));
};

const deleteProfile = async (id) => {
  return customAxios.delete(`child/${id}`);
};

const editProfileImg = async (childId, profileImg) => {
  const headers = {
    "Content-Type": "multipart/form-data",
  };
  const formData = new FormData();
  formData.append("image", profileImg);
  return customAxios.put(`child/profile/${childId}`, formData, { headers });
};

export {
  getProfile,
  getChildProfile,
  addProfile,
  deleteProfile,
  editProfileImg,
};
