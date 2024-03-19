// 외부 모듈
import { useNavigate, useParams } from "react-router-dom";
import { useEffect } from "react";

// 내부 모듈
import Loading from "../../modules/Load/Load.jsx";
import { getToken } from "./ServerConnect.js";

const SocialLogin = () => {
  // redierct URL 파싱
  const params = new URL(window.location.href).searchParams;
  const code = params.get("code");
  const { provider } = useParams();

  const navigate = useNavigate();

  useEffect(() => {
    let login = async () => {
      try {
        const value = await getToken(code, provider);
        console.log(value.data);
        const accessToken = value.data.data.accessToken;
        console.log("accessToken:", accessToken);
        navigate("/home");
      } catch (err) {
        console.log("Login error:", err);
      }
    };
    login();
  }, [code, navigate, provider]); // 의존성 배열 축

  return <Loading step={2} />;
};

export default SocialLogin;
