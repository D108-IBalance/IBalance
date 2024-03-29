// 외부 모듈
import { useNavigate } from "react-router-dom";
import { useEffect, useMemo } from "react";
import { add } from "./dummy.js";
import { useSelector } from "react-redux";

import { addTempDiet } from "./ServerConnect";

// 내부 모듈
import classes from "./DayDiet.module.css";

const DayDiet = (props) => {
  const childId = useSelector((state) => state.childId);
  const navigate = useNavigate();
  const { diets, day, setUserDiet, dayIdx, setSummaryInfo, setSelectDate } =
    props;
  // "식단 추가" 버튼의 표시 여부를 메모이제이션
  // const showAddDiet = useMemo(() => {
  //   return dietList.length <= 2 && !saveDiet;
  // }, [dietList.length, saveDiet]);

  const addDietCard = async () => {
    // const res = await addTempDiet(childId, diets.dietDay);
    const res = add.data;
    setUserDiet((prev) => {
      let nextDiets = JSON.parse(JSON.stringify(prev));
      let nextDiet = nextDiets[0];
      nextDiet.map((data, idx) => {
        if (idx === dayIdx) {
          data.menuList.push(res);
        }
        return data;
      });
      return nextDiets;
    });
  };
  const goSummary = (sequence) => {
    setSummaryInfo({ dietDay: diets.dietDay, sequence: sequence });
    setSelectDate(day);
  };
  return (
    <div className={classes.dayDietBox}>
      <div className={classes.dayTitleBox}>
        <p className={classes.dayTitle}>{day}</p>

        <p className={classes.addTitle} onClick={() => addDietCard()}>
          식단 추가
        </p>
      </div>
      <div className={classes.dayCardBox}>
        {Array.from(diets.menuList).map((diet, idx) => {
          return (
            <div
              key={idx}
              className={classes.dayCard}
              onClick={() => {
                goSummary(idx);
              }}>
              <div className={classes.cardIcon}></div>
              <div className={classes.cardLine}></div>
              <div className={classes.dietContentBox}>
                <div>
                  {diet.map((menu, id) => {
                    return <p key={id}>{menu.menuName}</p>;
                  })}
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
