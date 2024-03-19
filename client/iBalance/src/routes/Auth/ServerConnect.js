// 외부 모듈
import axios from "axios";

// 내부 모듈
import servers from "../../server.jsx";

// accessToken 받기 위한 API 요청
const getToken = async (code, provider) => {
  const headers = {
    "Content-Type": "application/json",
  };
  const data = {
    code: code,
    url: `${servers}/auth/social/${provider}`,
  };
  try {
    const response = await axios.post(
      `https://j10d108.p.ssafy.io/api/member/login/${provider}`,
      data, // 직접 객체를 전달
      {
        headers,
        withCredentials: true, // 쿠키에 refresh token 저장하기 위해 필요
      },
    );
    return response;
  } catch (error) {
    console.error("getToken error: error");
    throw error; // 오류를 다시 throw하여 호출자가 처리할 수 있도록 함
  }
};

export { getToken };
