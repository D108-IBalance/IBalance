import customAxios from "../../axiosController";

const getUserInfo = async (CHILDID) => {
  return customAxios.get(`child/main/${CHILDID}`);
};

const getRecommendedDiet = async (CHILDID) => {
  const date = new Date();
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const formMonth = month.toString().padStart(2, "0");
  const formDay = day.toString().padStart(2, "0");
  const form = `${year}-${formMonth}-${formDay}`;
  return customAxios.get(`diet/${CHILDID}?today=${form}`);
};

const getInitDiet = async (CHILDID) => {
  return customAxios.get(`diet/${CHILDID}/init`);
};

const addTempDiet = async (CHILDID, dayId) => {
  return customAxios.get(`diet/${CHILDID}?dietDay=${dayId}`);
};

export { getUserInfo, getRecommendedDiet, getInitDiet, addTempDiet };
