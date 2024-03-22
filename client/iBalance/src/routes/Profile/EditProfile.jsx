// 외부 모듈
import { useNavigate } from "react-router-dom";

// 내부 모듈
import classes from "./EditProfile.module.css";
import profileImg from "../../assets/profile/Img/default_image.png";

const EditProfile = () => {
  const navigate = useNavigate();
  return (
    <main className={classes.container}>
      <section className={classes.formBox}>
        <div
          className={classes.backIcon}
          onClick={() => {
            navigate(-1);
          }}></div>
        <div className={classes.imgBox}>
          <div className={classes.settingLine}>
            <img src={profileImg} className={classes.profileImg} />
            <div className={classes.settingIconBox}>
              <div className={classes.settingIcon}></div>
            </div>
          </div>
        </div>
        <div className={classes.flexBet}>
          <p>자녀 이름</p>
          <div className={classes.dataBox}>박서준</div>
        </div>
        <div className={classes.flexBet}>
          <p>자녀 생년월일</p>
          <div className={classes.dataBox}>2020 / 03 / 30</div>
        </div>
        <div className={classes.flexBet}>
          <p>자녀 성별</p>
          <div className={classes.dataBox}>남자</div>
        </div>
        <div className={classes.flexBet}>
          <p>자녀 키</p>
          <div className={classes.dataBox}>130cm</div>
        </div>
        <div className={classes.flexBet}>
          <p>자녀 몸무게</p>
          <div className={classes.dataBox}>35kg</div>
        </div>
        <div className={classes.flexBet}>
          <p>자녀 알레르기</p>
          <div className={classes.dataBox}>
            토마토, 우유, 대두, 계란, 고등어
          </div>
        </div>
        <div className={classes.btnList}>
          <div
            className={classes.cancleBtn}
            onClick={() => {
              navigate(-1);
            }}>
            취소
          </div>
          <div className={classes.confirmBtn}>확인</div>
        </div>
      </section>
    </main>
  );
};

export default EditProfile;
