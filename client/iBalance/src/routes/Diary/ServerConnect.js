import customAxios from "../../axiosController";

const getDietDates = async (childId, year, month) => {
  return customAxios.get(
    `diary/calendar/${childId}?year=${year}&month=${month}`,
  );
};

const getDietDiary = async (childId, date) => {
  return customAxios.get(`diary/${childId}?date=${date}`);
};

const getDiaryDetail = async (dietId) => {
  return customAxios.get(`diary/detail/${dietId}`);
};

const writeDiary = async (childId, dietInfo) => {
  return customAxios.post(`diary/${childId}`, JSON.stringify(dietInfo));
};

const readDiary = async (childId, dietId) => {
  return customAxios.get(`diary/${childId}/detail/${dietId}`);
};

export { getDietDates, getDietDiary, getDiaryDetail, writeDiary, readDiary };
