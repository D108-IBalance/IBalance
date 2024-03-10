// 내부 모듈
import { useEffect, useState } from "react";
import classes from "./ChildHeight.module.css";

const ChildHeight = (props) => {
  // 최대 키
  const maxHeight = 150;

  let [current, setCurrent] = useState(0);
  let [animation, setAnimation] = useState("fadeIn");
  let { setStep, setProfileData, profileData } = props;
  let arr = [...new Array(maxHeight + 1)].map((_, idx) => {
    if (idx % 5 == 0) {
      return "long";
    }
    return "short";
  });
  let [go, setGo] = useState(0);
  let [height, setHeight] = useState("0");
  let [validate, setValidate] = useState(false);
  let changeIt = (e) => {
    e.currentTarget.value = e.currentTarget.value.replace(/[^0-9]/g, "");
    let temp = Number.parseInt(e.currentTarget.value);
    if (temp > maxHeight) {
      e.currentTarget.value = e.currentTarget.value.slice(0, 2);
    }
    setHeight(e.currentTarget.value);
    // 요소의 크기 고려한 픽셀식 4.25 Y축 방향으로 세로로 가야하기에 음수 값
    setGo(-4.25 * e.currentTarget.value);
    // .replace(/(\..*)\./g, "$1");
  };

  let nextStep = () => {
    let temp = Object.assign({}, profileData);
    temp.height = Number.parseInt(height);
    setProfileData(temp);
    setCurrent(1);
  };

  useEffect(() => {
    let timer;
    if (current === 1) {
      setAnimation("fadeOut");
      timer = setTimeout(() => {
        setStep(4);
      }, 500);
    }
    return () => {
      clearTimeout(timer);
    };
  }, [current, setStep]);

  useEffect(() => {
    let heightValdate = () => {
      if (height !== "0" && height !== "") {
        setValidate(true);
      } else {
        setValidate(false);
      }
    };
    heightValdate();
  }, [height]);

  return (
    <section className={`${classes.formBox} ${classes[animation]}`}>
      <p className={classes.title}>{profileData["name"]}님의</p>
      <p className={classes.title}>키를 알려주세요.</p>
      <p
        className={
          classes.description
        }>{`만 6세까지의 식단을 제공함으로 키는 최대 ${maxHeight}cm 까지 받고있습니다.`}</p>

      <div className={classes.heightForm}>
        <div
          style={{ transform: `translateY(${go}px)` }}
          className={classes.ruler}>
          {arr.map((content, idx) => {
            if (idx == height) {
              return (
                <div key={idx} className={classes[content + "Color"]}></div>
              );
            }
            return <div key={idx} className={classes[content]}></div>;
          })}
        </div>
        <div className={classes.inputBox}>
          <input
            type="text"
            className={classes.heightInput}
            maxLength={3}
            onChange={(e) => {
              changeIt(e);
            }}
          />
          <p className={classes.cm}>(CM)</p>
        </div>
      </div>

      {validate === true ? (
        <button
          className={classes.nextBtn}
          onClick={() => {
            nextStep();
          }}>
          다음
        </button>
      ) : (
        <button className={classes.noBtn} style={{ cursor: "auto" }} disabled>
          올바른 값을 입력해주세요.
        </button>
      )}
    </section>
  );
};

export default ChildHeight;
