// 외부 모듈
import { useNavigate } from "react-router-dom";
import { useEffect, useMemo, useState } from "react";
import { add } from "./dummy.js";
import { useSelector } from "react-redux";

import { addTempDiet } from "./ServerConnect";

// 내부 모듈
import classes from "./DayDiet.module.css";

const DayDiet = (props) => {
  const childId = useSelector((state) => state.childId);
  const {
    diets,
    day,
    setUserDiet,
    dayIdx,
    setSummaryInfo,
    setSelectDate,
    isSave,
  } = props;

  const addDietCard = async () => {
    console.log(childId, diets.dietDay);
    const res = await addTempDiet(childId, diets.dietDay);
    console.log(res);
    setUserDiet((prev) => {
      let nextDiets = JSON.parse(JSON.stringify(prev));
      let nextDiet = nextDiets;
      nextDiet.map((data, idx) => {
        if (idx === dayIdx) {
          data.menuList.push(res.data.data);
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

        {diets.menuList.length < 3 && !isSave ? (
          <p className={classes.addTitle} onClick={() => addDietCard()}>
            식단 추가
          </p>
        ) : null}
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
