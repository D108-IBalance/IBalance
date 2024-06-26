import { useEffect, useState } from "react";
import classes from "./ChildName.module.css";

const ChildName = (props) => {
  const [current, setCurrent] = useState(0);
  const { setStep, setProfileData, profileData } = props;
  const [animation, setAnimation] = useState("fadeIn");
  const [text, setText] = useState("");
  const onNextStep = () => {
    if (text.length === 0) return;
    setAnimation("fadeOut");
    let data = Object.assign({}, profileData);
    data.name = text;
    setProfileData(data);
    setCurrent(1);
  };
  const onType = (e) => {
    if (e.currentTarget.value.length > 10) {
      e.currentTarget.value = e.currentTarget.value.slice(0, 10);
    }
    setText(e.currentTarget.value);
  };

  useEffect(() => {
    let timer = null;
    if (current === 1) {
      timer = setTimeout(() => {
        setStep(1);
      }, 500);
    }
    return () => {
      clearTimeout(timer);
    };
  }, [current, setStep]);

  return (
    <section className={`${classes[animation]} ${classes.formBox}`}>
      <p className={classes.title}>자녀 이름을 입력해주세요.</p>
      <div className={classes.nameBox}>
        <input
          type="text"
          className={classes.nameInput}
          placeholder="10글자 내로 입력하세요."
          onChange={(e) => {
            onType(e);
          }}
        />
      </div>
      {text.length > 0 ? (
        <button
          className={classes.nextBtn}
          onClick={() => {
            onNextStep();
          }}>
          다음
        </button>
      ) : (
        <button className={classes.noBtn} disabled>
          다음
        </button>
      )}
    </section>
  );
};

export default ChildName;
