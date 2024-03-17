/* eslint-disable */

// 외부 모듈
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
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
    async () => {
      const value = await getToken(code, provider);
      // navigate("/home");
      // .then((res) => {
      //   const accessToken = res.data;
      //   console.log(res);
      // })
      // .catch((err) => {
      //   console.log(err);
      // });
    };
  });

  return (
    <>
      <Loading step={2}></Loading>

      <div>사이트 : {provider}</div>
      <div>인가코드 : {code}</div>
    </>
  );
};

export default SocialLogin;
