import customAxios from "../../axiosController";

const getUserChart = async (page, CHILDID) => {
  const SIZE = 4;
  return customAxios.get(`child/growth/${CHILDID}?page=${page}&size=${SIZE}`);
};

const getUserInfo = async (idx) => {
  const date = new Date();
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const formMonth = month.toString().padStart(2, "0");
  const formDay = day.toString().padStart(2, "0");
  const form = `${year}-${formMonth}-${formDay}`;
  return customAxios.get(`child/main/${idx}?date=${form}`);
};

const getWeightChart = async (page, CHILDID) => {
  const SIZE = 4;
  return customAxios.get(`child/weight/${CHILDID}?page=${page}&size=${SIZE}`);
};

const getHeightChart = async (page, CHILDID) => {
  const SIZE = 4;
  return customAxios.get(`child/height/${CHILDID}?page=${page}&size=${SIZE}`);
};

export { getUserChart, getUserInfo, getWeightChart, getHeightChart };
