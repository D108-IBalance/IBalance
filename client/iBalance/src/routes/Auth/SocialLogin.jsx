/* eslint-disable */

// 외부 모듈
import { useParams } from "react-router-dom";
import Loading from "../../modules/Load/Load.jsx";
import axios from "axios";
import { useEffect } from "react";

const SocialLogin = () => {
  let params = new URL(window.location.href).searchParams;
  let code = params.get("code");
  let provider = params.get("provider");
  useEffect;
  return (
    <>
      <Loading></Loading>
    </>
  );
};

export default SocialLogin;
