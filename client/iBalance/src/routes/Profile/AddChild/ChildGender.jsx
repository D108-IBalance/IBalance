/* eslint-disable */

//외부 모듈
import { useEffect, useState } from "react";

// 내부 모듈
import classes from "./ChildGender.module.css";
import femalePng from "../../../assets/profile/Img/female.png";
import malePng from "../../../assets/profile/Img/male.png";

const ChildGender = (props) => {
  let [current, setCurrent] = useState(0);
  let { setStep, setProfileData, profileData } = props;
  let [animation, setAnimation] = useState("fadeIn");
  let [gender, setGender] = useState("");
  let nextStep = () => {
    let data = Object.assign({}, profileData);
    data.gender = gender;
    setProfileData(data);
    setCurrent(1);
  };
  useEffect(() => {
    let timer;
    if (current === 1) {
      setAnimation("fadeOut");
      timer = setTimeout(() => {
        setStep(3);
      }, 500);
    }
    return () => {
      clearTimeout(timer);
    };
  }, [current]);
  return (
    <section className={`${classes.formBox} ${classes[animation]}`}>
      <p className={classes.title}>자녀의 성별을 선택해주세요.</p>
      <div className={classes.boxFlex}>
        {gender === "FEMALE" ? (
          <img src={femalePng} className={classes.genderPick} />
        ) : (
          <img
            src={femalePng}
            className={classes.genderImg}
            onClick={() => {
              setGender("FEMALE");
            }}
          />
        )}
        {gender === "MALE" ? (
          <img src={malePng} className={classes.genderPick} />
        ) : (
          <img
            src={malePng}
            className={classes.genderImg}
            onClick={() => {
              setGender("MALE");
            }}
          />
        )}
      </div>
      {gender === "" ? (
        <button className={classes.noBtn}>다음</button>
      ) : (
        <button
          className={classes.nextBtn}
          onClick={() => {
            nextStep();
          }}>
          다음
        </button>
      )}
    </section>
  );
};
export default ChildGender;
