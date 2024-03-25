// 외부 모듈
import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

// 내부 모듈
import classes from "./ChildAllergy.module.css";
import allergy from "./allergy.js";
import { addProfile } from "../ServerConnect.js";

const ChildAllergy = (props) => {
  const TOKEN = useSelector((state) => {
    return state.token;
  });
  const { setProfileData, profileData } = props;
  const [animation, setAnimation] = useState("fadeIn");
  const [current, setCurrent] = useState(0);
  const [select, setSelect] = useState([]);
  const navigate = useNavigate();
  const tempShadow = [...new Array(allergy.length)].map(() => {
    return "0px 4px 4px 0px rgba(0,0,0,0.25)";
  });
  const [shadow, setShadow] = useState(tempShadow);
  let selectShadow = (idx, id) => {
    let tempShadow = [...shadow];
    let tempSelect = [...select];
    if (tempShadow[idx] === "0px 4px 4px 0px rgba(0,0,0,0.25)") {
      tempShadow[idx] = "0px 4px 4px 0px rgba(254, 114, 76, 0.8)";
      tempSelect.push(id);
    } else {
      tempShadow[idx] = "0px 4px 4px 0px rgba(0,0,0,0.25)";
      tempSelect = tempSelect.filter((elem) => elem !== id);
    }
    setShadow(tempShadow);
    setSelect(tempSelect);
  };
  let onNextStep = async () => {
    let temp = Object.assign({}, profileData);
    temp.haveAllergies = select;
    setProfileData(temp);
    try {
      await addProfile(temp, TOKEN);
      setCurrent(1);
    } catch (err) {
      console.log(err);
    }
  };
  useEffect(() => {
    let timer = null;
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
                  selectShadow(idx, data.id);
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
          onNextStep();
        }}>
        다음({select.length}/18)
      </button>
    </section>
  );
};

export default ChildAllergy;
