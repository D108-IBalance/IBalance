import { useEffect, useState } from "react";
import classes from "./LoginForm.module.css";
import { Container, Row } from "react-bootstrap";

import { logout } from "./ServerConnect";
import { useSelector } from "react-redux";

const LoginForm = () => {
  const SocialLogin = async (num) => {
    let server = "";
    if (num == 0) {
      // 구글 로그인
      server = import.meta.env.VITE_APP_GOOGLE_AUTH_URL;
    } else if (num == 1) {
      // 카카오 로그인
      server = import.meta.env.VITE_APP_KAKAO_AUTH_URL;
    } else {
      // 네이버 로그인
      server = import.meta.env.VITE_APP_NAVER_AUTH_URL;
    }
    window.location.href = server;
  };

  return (
    <div className={classes.loginFormbox}>
      <Container className={classes.loginForm}>
        <Row className={classes.loginLogo}></Row>

        <Row
          className={classes.loginGoogle}
          onClick={() => {
            SocialLogin(0);
          }}></Row>
        <Row
          className={classes.loginKakao}
          onClick={() => {
            SocialLogin(1);
          }}></Row>
        <Row
          className={classes.loginNaver}
          onClick={() => {
            SocialLogin(2);
          }}></Row>
      </Container>
    </div>
  );
};

export default LoginForm;
