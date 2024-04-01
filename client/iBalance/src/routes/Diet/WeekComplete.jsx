import { useEffect, useState } from "react";
import classes from "./DayDiet.module.css";

const WeekComplete = (props) => {
  const { weekDate, menu, setDietId, setSummaryInfo, setSelectDate } = props;
  const [weekTitle, setWeekTitle] = useState("");
  const goSummary = (idx) => {
    setDietId(menu.dietList[idx].dietId);
    setSummaryInfo({ dietDay: "", sequence: "" });
    setSelectDate(weekTitle);
  };
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
    <div className={classes.dayDietBox}>
      <div className={classes.dayTitleBox}>
        <p className={classes.dayTitle}>{weekTitle}</p>
      </div>

      <div className={classes.dayCardBox}>
        {Object.keys(menu).length > 0 &&
          menu?.dietList.map((dietList, idx) => {
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
                    {dietList?.menuList.map((menu, id) => {
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

export default WeekComplete;
