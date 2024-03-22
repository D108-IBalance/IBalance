// 외부 모듈

import { useNavigate } from "react-router-dom";

// 내부 모듈

import classes from "./Error.module.css";
import boyImg from "../../assets/error/Img/boy.png";

const Error = () => {
  const navigate = useNavigate();
  return (
    <div className={classes.container}>
      <img src={boyImg} alt="" />
      <span className={classes.font}>404 Not Found</span>
      <button
        className={classes.mainButton}
        onClick={() => {
          navigate("/");
        }}>
        Go Main
      </button>
    </div>
  );
};

export default Error;
