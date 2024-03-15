/* eslint-disable */

// 외부 모듈
import { Container, Col, Row } from "react-bootstrap";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

// 내부 모듈
import classes from "./IntroPage.module.css";

const IntroPage = () => {
  // 인트로 화면 상태 값 저장
  const [intro, setIntro] = useState(true);
  const navigate = useNavigate();

  // 2초 뒤 로그인 페이지로 넘어가는 함수
  const timeout = () => {
    setTimeout(() => {
      navigate("/enter/login");
      // 인트로 상태값 false로 변경
      setIntro(false);
    }, 2000);
  };

  // 컴포넌트 렌더링이 완료되면, timeout 함수 실행
  useEffect(() => {
    timeout();
    return () => {
      // 실행이 완료되면 clear 해줌
      clearTimeout(timeout);
    };
  });

  return (
    <div className={classes.introBack}>
      {/* 인트로 상태값이 true 일때만 intro컴포넌트 보여주기 */}
      {intro === true ? (
        <div>
          <div className={classes.introImg}></div>
          <div className={classes.introLogo}></div>
        </div>
      ) : null}
    </div>
  );
};

export default IntroPage;
