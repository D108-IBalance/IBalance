// 외부 모듈
import { useNavigate, useParams } from "react-router-dom";
import { useEffect } from "react";
import { useDispatch } from "react-redux";

// 내부 모듈
import Loading from "../../modules/Load/Load.jsx";
import { getToken } from "./ServerConnect.js";
import { setToken } from "../../store.js";

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
        console.log(value.data);
        const accessToken = value.data.data.accessToken;
        dispatch(setToken(accessToken));
        console.log("accessToken:", accessToken);
        navigate("/home");
      } catch (err) {
        console.log("Login error:", err);
      }
    };
    login();
  }, [code, dispatch, navigate, provider]); // 의존성 배열 축

  return <Loading step={2} />;
};

export default SocialLogin;
