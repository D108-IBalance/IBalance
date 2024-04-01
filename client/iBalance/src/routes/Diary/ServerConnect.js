import customAxios from "../../axiosController";

const getDietDates = (childId, year, month) => {
  return customAxios.get(
    `diary/calendar/${childId}?year=${year}&month=${month}`,
  );
};

export { getDietDates };
