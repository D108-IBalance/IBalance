// 외부 모듈
import { useState, useEffect } from "react";

// 내부 모듈
import classes from "./ChildWeight.module.css";

const ChildWeight = (props) => {
  // 최대 몸무게 100Kg
  const MAX_WEIGHT = 100;
  const { setStep, setProfileData, profileData } = props;
  const [animation, setAnimation] = useState("fadeIn");
  const [current, setCurrent] = useState(0);
  const [weight, setWeight] = useState(0);
  const [go, setGo] = useState(MAX_WEIGHT * 4.1);
  const [validate, setValidate] = useState(false);
  let arr = [...new Array(MAX_WEIGHT + 1)].map((_, idx) => {
    if (idx % 5 == 0) {
      return "long";
    }
    return "short";
  });
  let onChangeIt = (e) => {
    e.currentTarget.value = e.currentTarget.value.replace(/[^0-9]/g, "");
    let temp = Number.parseInt(e.currentTarget.value);
    if (temp > MAX_WEIGHT) {
      e.currentTarget.value = e.currentTarget.value.slice(0, 2);
    }
    setWeight(e.currentTarget.value);
    // 요소의 크기 고려한 픽셀식 4.25 Y축 방향으로 세로로 가야하기에 음수 값
    setGo(MAX_WEIGHT * 4.1 - 8.2 * e.currentTarget.value);
    // .replace(/(\..*)\./g, "$1");
  };
  let onNextStep = () => {
    let temp = Object.assign({}, profileData);
    temp.weight = Number.parseInt(weight);
    setProfileData(temp);
    setCurrent(1);
  };
  useEffect(() => {
    let weightValidate = () => {
      if (weight !== "0" && weight !== "") {
        setValidate(true);
      } else {
        setValidate(false);
      }
    };
    weightValidate();
  }, [weight]);
  useEffect(() => {
    let timer = null;
    if (current === 1) {
      setAnimation("fadeOut");
      timer = setTimeout(() => {
        setStep(5);
      }, 500);
    }
    return () => {
      clearTimeout(timer);
    };
  }, [current, setStep]);
  return (
    <section className={`${classes.formBox} ${classes[animation]}`}>
      <p className={classes.title}>{profileData["name"]}님의</p>
      <p className={classes.title}>몸무게를 알려주세요.</p>
      <p
        className={
          classes.description
        }>{`만 6세까지의 식단을 제공함으로 최대 ${MAX_WEIGHT}kg 까지 받고있습니다.`}</p>
      <div className={classes.weightForm}>
        <div className={classes.inputBox}>
          <input
            type="text"
            className={classes.weightInput}
            maxLength={3}
            onChange={(e) => {
              onChangeIt(e);
            }}
          />
          <p className={classes.kg}>(KG)</p>
        </div>
        <div
          style={{ transform: `translateX(${go}px)` }}
          className={classes.ruler}>
          {arr.map((content, idx) => {
            if (idx == weight) {
              return (
                <div key={idx} className={classes[content + "Color"]}></div>
              );
            }
            return <div key={idx} className={classes[content]}></div>;
          })}
        </div>
      </div>
      {validate ? (
        <button
          className={classes.nextBtn}
          onClick={() => {
            onNextStep();
          }}>
          다음
        </button>
      ) : (
        <button className={classes.noBtn} style={{ cursor: "auto" }}>
          올바른 값을 입력해주세요.
        </button>
      )}
    </section>
  );
};

export default ChildWeight;
