// 외부 모듈
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";

// 내부 모듈
import Loading from "../../modules/Load/Load.jsx";
import { getToken } from "./ServerConnect.js";
import { setToken } from "../../store.js";
import LoginAlert from "./LoginAlert.jsx";

const SocialLogin = () => {
  const dispatch = useDispatch();
  const { provider } = useParams();
  const navigate = useNavigate();
  const [alert, setAlert] = useState(0);

  useEffect(() => {
    // URL 파싱
    const params = new URL(window.location.href).searchParams;
    const code = params.get("code");
    let timer = null;
    let login = async () => {
      try {
        const value = await getToken(code, provider);
        alert(code, provider);
        const accessToken = value.data.data.accessToken;
        dispatch(setToken(accessToken));
        timer = setTimeout(() => {
          navigate("/enter/profile");
        }, 1000);
      } catch (err) {
        setAlert(1);
      }
    };

    login();
    return () => {
      clearTimeout(timer);
    };
  }, [dispatch, navigate, provider]);

  useEffect(() => {
    if (alert === 2) {
      navigate("/");
    }
  }, [alert, navigate]);

  return (
    <>
      <Loading step={2} />
      {alert === 1 ? <LoginAlert setAlert={setAlert} /> : null}
    </>
  );
};

export default SocialLogin;
