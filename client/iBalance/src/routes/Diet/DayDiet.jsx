// 외부 모듈
import { useNavigate } from "react-router-dom";
import { useMemo } from "react";

// 내부 모듈
import classes from "./DayDiet.module.css";

const DayDiet = (props) => {
  const navigate = useNavigate();
  const { day, diets, addDietCard, saveDiet } = props;

  // "식단 추가" 버튼의 표시 여부를 메모이제이션
  const showAddDiet = useMemo(() => {
    return diets.length <= 2 && !saveDiet;
  }, [diets.length, saveDiet]);

  return (
    <div className={classes.dayDietBox}>
      <div className={classes.dayTitleBox}>
        <p className={classes.dayTitle}>{day}</p>
        {showAddDiet ? (
          <p className={classes.addTitle} onClick={() => addDietCard()}>
            식단 추가
          </p>
        ) : null}
      </div>
      <div className={classes.dayCardBox}>
        {diets.map((diet, index) => {
          return (
            <div
              className={classes.dayCard}
              key={index}
              onClick={() => {
                navigate("/detail");
              }}>
              <div className={classes.cardIcon}></div>
              <div className={classes.cardLine}></div>
              <div className={classes.dietContentBox}>
                <div>
                  <p>{diet.riceMenu}</p>
                  <p>{diet.mainMenu}</p>
                  <p>{diet.sideMenu}</p>
                  <p>{diet.soupMenu}</p>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default DayDiet;
