import axios from "axios";

const getUserChart = (page) => {
  const TOKEN = "";
  const headers = {
    Authorization: `Bearer ${TOKEN}`,
  };
  const CHILD_ID = 1;
  const SIZE = 4;
  axios.get(
    `http://localhost:8080/api/growth/${CHILD_ID}?page=${page}&size=${SIZE}`,
    { headers },
  );
};

export { getUserChart };
