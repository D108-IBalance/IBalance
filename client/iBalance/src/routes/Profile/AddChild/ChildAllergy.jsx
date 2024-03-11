// 외부 모듈
import { useState, useEffect } from "react";

// 내부 모듈
import classes from "./ChildAllergy.module.css";
import allergy from "./allergy.js";
import { useNavigate } from "react-router-dom";

const ChildAllergy = (props) => {
  let { setProfileData, profileData } = props;
  let [animation, setAnimation] = useState("fadeIn");
  let [current, setCurrent] = useState(0);
  let [select, setSelect] = useState([]);
  let temp = [...new Array(allergy.length)].map(() => {
    return "0px 4px 4px 0px rgba(0,0,0,0.25)";
  });
  let [shadow, setShadow] = useState(temp);
  let navigate = useNavigate();
  let selectShadow = (idx) => {
    let tempShadow = [...shadow];
    let tempSelect = [...select];
    if (tempShadow[idx] === "0px 4px 4px 0px rgba(0,0,0,0.25)") {
      tempShadow[idx] = "0px 4px 4px 0px rgba(254, 114, 76, 0.8)";
      tempSelect.push(idx);
    } else {
      tempShadow[idx] = "0px 4px 4px 0px rgba(0,0,0,0.25)";
      tempSelect = tempSelect.filter((elem) => elem !== idx);
    }
    setShadow(tempShadow);
    setSelect(tempSelect);
  };
  let nextStep = () => {
    let temp = Object.assign({}, profileData);
    temp.haveAllergies = select;
    setProfileData(temp);
    //temp로 axios 요청하는 함수 필요
    setCurrent(1);
  };
  useEffect(() => {
    let timer;
    if (current === 1) {
      setAnimation("fadeOut");
      timer = setTimeout(() => {
        navigate("/enter/profile");
      }, 500);
    }
    return () => {
      clearTimeout(timer);
    };
  }, [current, navigate]);
  return (
    <section className={`${classes.formBox} ${classes[animation]}`}>
      <p className={classes.title}>{profileData["name"]}님의</p>
      <p className={classes.title}>알러지 정보를 알려주세요.</p>
      <p className={classes.description}>
        제공되는 식단에 해당 재료의 음식은 제외시켜 드리겠습니다.
      </p>
      <div className={classes.infoBox}>
        {allergy.map((data, idx) => {
          return (
            <div className={classes.ingredient} key={idx}>
              <div
                className={classes.img}
                style={{
                  backgroundImage: `url(${data["img"]})`,
                  boxShadow: `${shadow[idx]}`,
                }}
                onClick={() => {
                  selectShadow(idx);
                }}
              />
              <span className={classes.info}>{data["name"]}</span>
            </div>
          );
        })}
      </div>
      <button
        className={classes.nextBtn}
        onClick={() => {
          nextStep();
        }}>
        다음({select.length}/18)
      </button>
    </section>
  );
};

export default ChildAllergy;
