/* eslint-disable */

import axios from "axios";

const getToken = async (code, provider) => {
  const headers = {
    "Content-Type": `application/json`,
  };
  return axios.post(
    `https://j10d108.p.ssafy.io/api/member/login/${provider}`,
    JSON.stringify(code),
    {
      headers,
    },
  );
};

export { getToken };
