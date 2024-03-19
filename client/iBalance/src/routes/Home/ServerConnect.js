import axios from "axios";

const getUserChart = (page, TOKEN) => {
  const headers = {
    Authorization: `Bearer ${TOKEN}`,
  };
  const CHILD_ID = 1;
  const SIZE = 4;
  axios.get(
    `https://j10d108.p.ssafy.io/api/growth/${CHILD_ID}?page=${page}&size=${SIZE}`,
    { headers, withCredentials: true },
  );
};

export { getUserChart };
