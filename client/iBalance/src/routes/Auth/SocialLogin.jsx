// 외부 모듈
import { useNavigate, useParams } from "react-router-dom";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import secureLocalStorage from "react-secure-storage";

// 내부 모듈
import Loading from "../../modules/Load/Load.jsx";
import { getToken } from "./ServerConnect.js";

const SocialLogin = () => {
  // redierct URL 파싱
  const dispatch = useDispatch();
  const params = new URL(window.location.href).searchParams;
  const code = params.get("code");
  const { provider } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    let login = async () => {
      try {
        const value = await getToken(code, provider);
        const accessToken = value.data.data.accessToken;
        secureLocalStorage.setItem("token", accessToken);
        navigate("/enter/profile");
      } catch (err) {
        console.log("Login error:", err);
      }
    };
    login();
  }, [code, dispatch, navigate, provider]); // 의존성 배열 축

  return <Loading step={2} />;
};

export default SocialLogin;
