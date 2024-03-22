// 내부 모듈
import { useEffect, useState } from "react";
import classes from "./ChildHeight.module.css";

const ChildHeight = (props) => {
  // 최대 키
  const MAX_HEIGHT = 150;

  const [current, setCurrent] = useState(0);
  const [animation, setAnimation] = useState("fadeIn");
  const { setStep, setProfileData, profileData } = props;
  const [go, setGo] = useState(0);
  const [height, setHeight] = useState("0");
  const [validate, setValidate] = useState(false);
  let arr = [...new Array(MAX_HEIGHT + 1)].map((_, idx) => {
    if (idx % 5 == 0) {
      return "long";
    }
    return "short";
  });

  let onChangeIt = (e) => {
    e.currentTarget.value = e.currentTarget.value
      .replace(/[^0-9.]/g, "")
      .replace(/(\..*)\./g, "$1");
    let temp = Number.parseFloat(e.currentTarget.value);
    if (temp > MAX_HEIGHT) {
      e.currentTarget.value = e.currentTarget.value.slice(0, 2);
    }
    setHeight(e.currentTarget.value);
    // 요소의 크기 고려한 픽셀식 4.25 Y축 방향으로 세로로 가야하기에 음수 값
    setGo(-4.25 * e.currentTarget.value);
    // .replace(/(\..*)\./g, "$1");
  };

  let onNextStep = () => {
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
        }>{`만 6세까지의 식단을 제공함으로 키는 최대 ${MAX_HEIGHT}cm 까지 받고있습니다.`}</p>

      <div className={classes.heightForm}>
        <div
          style={{ transform: `translateY(${go}px)` }}
          className={classes.ruler}>
          {arr.map((content, idx) => {
            if (idx == Math.round(height)) {
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
            maxLength={5}
            onChange={(e) => {
              onChangeIt(e);
            }}
          />
          <p className={classes.cm}>(CM)</p>
        </div>
      </div>

      {validate === true ? (
        <button
          className={classes.nextBtn}
          onClick={() => {
            onNextStep();
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
