import axios from "axios";
import secureLocalStorage from "react-secure-storage";

const getUserChart = async (page, idx) => {
  const TOKEN = secureLocalStorage.getItem("token");
  const SIZE = 4;
  const headers = {
    Authorization: `${TOKEN}`,
  };
  return axios.get(
    `https://j10d108.p.ssafy.io/api/child/growth/${idx}?page=${page}&size=${SIZE}`,
    { headers },
  );
};

const getUserInfo = (idx) => {
  const TOKEN = secureLocalStorage.getItem("token");
  const headers = {
    Authorization: `${TOKEN}`,
  };
  const date = new Date();
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const formMonth = month.toString().padStart(2, "0");
  const formDay = day.toString().padStart(2, "0");
  const form = `${year}-${formMonth}-${formDay}`;
  return axios.get(
    `https://j10d108.p.ssafy.io/api/child/main/${idx}?date=${form}`,
    { headers },
  );
};

export { getUserChart, getUserInfo };
