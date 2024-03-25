// 내부 모듈
import classes from "./LoginAlert.module.css";
import warningImg from "../../assets/auth/img/warning.svg";

const LoginAlert = (props) => {
  const { setAlert } = props;
  return (
    <section className={classes.modalBack}>
      <div className={classes.container}>
        <img src={warningImg} alt="warn" className={classes.warnImg} />
        <p className={classes.warnText}>로그인에 오류가 발생하였습니다.</p>
        <p
          className={classes.warnConfirm}
          onClick={() => {
            setAlert(2);
          }}>
          확인
        </p>
      </div>
    </section>
  );
};

export default LoginAlert;
