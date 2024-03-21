// 외부 모듈
import { useEffect, useState } from "react";

// 내부 모듈
import classes from "./ChildBirth.module.css";
import birthValidation from "./birthValidation.js"; // 생년월일 모두 확인 이후 값 잘라서 return

const ChildBirth = (props) => {
  let [current, setCurrent] = useState(0);
  let { setStep, setProfileData, profileData } = props;
  let [animation, setAnimation] = useState("fadeIn");
  let [year, setYear] = useState(0);
  let [month, setMonth] = useState(0);
  let [day, setDay] = useState(0);
  let [warn, setWarn] = useState(false);
  let onNextStep = () => {
    let flag = birthValidation(year, month, day);
    if (flag === false) {
      setWarn(true);
    } else {
      setWarn(false);
      setBirth();
      setCurrent(1);
    }
  };
  let setBirth = () => {
    let data = Object.assign({}, profileData);
    let tempMonth = "" + month;
    let tempDay = "" + day;
    if (tempMonth.length < 2) tempMonth = "0" + tempMonth;
    if (tempDay.length < 2) tempDay = "0" + tempDay;
    data.birthDate = year + "-" + tempMonth + "-" + tempDay;
    setProfileData(data);
  };
  useEffect(() => {
    let timer = null;
    if (current === 1) {
      setAnimation("fadeOut");
      timer = setTimeout(() => {
        setStep(2);
      }, 500);
    }
    return () => {
      clearTimeout(timer);
    };
  }, [current, setStep]);
  return (
    <section className={`${classes[animation]} ${classes.formBox}`}>
      <p className={classes.title}>{profileData["name"]}님의</p>
      <p className={classes.title}>생년월일을 알려주세요.</p>

      <div className={classes.inputBox}>
        <input
          type="number"
          min={1000}
          max={9999}
          className={classes.numInput}
          placeholder="연도"
          onChange={(e) => {
            setYear(e.currentTarget.value);
          }}
        />
        <span className={classes.dot}>/</span>
        <input
          type="number"
          min={1}
          max={99}
          className={classes.numInput}
          placeholder="월"
          onChange={(e) => {
            setMonth(e.currentTarget.value);
          }}
        />
        <span className={classes.dot}>/</span>
        <input
          type="number"
          min={1}
          max={99}
          className={classes.numInput}
          placeholder="일"
          onChange={(e) => {
            setDay(e.currentTarget.value);
          }}
        />
      </div>
      <button
        className={classes.nextBtn}
        onClick={() => {
          onNextStep();
        }}>
        다음
      </button>
      {warn ? (
        <p className={classes.warn}>
          저희 서비스는 생후 6개월부터 만 6세까지의 식단을 제공합니다.
        </p>
      ) : (
        <></>
      )}
    </section>
  );
};

export default ChildBirth;
