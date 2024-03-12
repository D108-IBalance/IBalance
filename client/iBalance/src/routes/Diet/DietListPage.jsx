/* eslint-disable */

// 외부 모듈
import { useEffect, useState } from "react";

// 내부 모듈
import classes from "./DietListPage.module.css";
import Load from "../../modules/Load/Load";

const DietListPage = () => {
  const TODAY = new Date();
  const DATE = TODAY.getDate(); //날짜
  const DAY = TODAY.getDay(); //요일
  const WEEKDAY = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];

  let weekList = [...new Array(7)].map((_, idx) => {
    return { date: DATE + idx, day: WEEKDAY[(DAY + idx) % 7] };
  });

  let [step, setStep] = useState(2);
  const timeout = () => {
    setTimeout(() => {
      setStep(1);
    }, 2000);
  };

  useEffect(() => {
    timeout();
    return () => {
      // clear 해줌
      clearTimeout(timeout);
    };
  });
  return (
    <div>
      <Load step={step}></Load>
      <div>{step === 1 ? <WeekCard weekList={weekList}></WeekCard> : null}</div>
    </div>
  );
};
const WeekCard = (props) => {
  let { weekList } = props;
  let [isClick, setIsclick] = useState([0, 0, 0, 0, 0, 0, 0, 0]);
  return (
    <div style={{ width: "100vw", overflow: "hidden" }}>
      <div className={classes.weekCardBox}>
        <div
          className={
            classes.weekCard
            // isClick[7] === 0 ? classes.weekCard : classes.weekCardClicked
          }>
          <p className={classes.dayFont}>All</p>
          <p className={classes.dateFont}>Day</p>
        </div>
        {weekList.map((data, key) => {
          return (
            <div key={key}>
              {data["date"]}날짜 {data["day"]}
            </div>
          );
        })}
      </div>
    </div>
  );
};
export default DietListPage;
