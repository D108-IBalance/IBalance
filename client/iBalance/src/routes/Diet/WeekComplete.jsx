import { useEffect, useState } from "react";
import classes from "./DayDiet.module.css";

const WeekComplete = (props) => {
  const { weekDate, menu } = props;
  const [weekTitle, setWeekTitle] = useState("");
  useEffect(() => {
    const WEEK_DAY = ["일", "월", "화", "수", "목", "금", "토"];
    if (Object.keys(menu).length > 0) {
      const date = new Date(menu.dietDate);
      const month = date.getMonth() + 1;
      const dates = date.getDate();
      const day = date.getDay();
      setWeekTitle(`${month}월 ${dates}일 (${WEEK_DAY[day]})`);
    }
  }, [weekDate, menu]);
  return (
    <>
      <div className={classes.dayDietBox}>
        <div className={classes.dayTitleBox}>
          <p className={classes.dayTitle}>{weekTitle}</p>
        </div>
      </div>
    </>
  );
};

export default WeekComplete;
