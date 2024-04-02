import { useEffect, useState, useMemo } from "react";

import WeekCard from "./WeekCard";
import classes from "./DietListPage.module.css";
import WeekComplete from "./WeekComplete";

const DietComplete = (props) => {
  const { userDiet, setSummaryInfo, setDietId, setSelectDate } = props;
  const [isClick, setIsClick] = useState(0);
  const [weekDiets, setWeekDiets] = useState([]);
  const weekList = useMemo(() => {
    if (weekDiets.length > 0) {
      const WEEKDAY = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];
      return [...new Array(weekDiets.length)].map((_, idx) => {
        const newDate = new Date();
        newDate.setDate(newDate.getDate() + idx);
        return { date: newDate.getDate(), day: WEEKDAY[newDate.getDay()] };
      });
    }
  }, [weekDiets]); // 초기 마운트 시에만 실행
  useEffect(() => {
    if (userDiet.length > 0) {
      const tempWeekDiets = userDiet.filter((dayDiet) => {
        if (dayDiet.dietList.length > 0) {
          return { ...dayDiet, dietDate: dayDiet.dietDate };
        }
      });
      setWeekDiets(tempWeekDiets);
    }
  }, [userDiet]);

  return (
    <div className={classes.container}>
      <WeekCard weekList={weekList} isClick={isClick} setIsClick={setIsClick} />
      <div className={classes.DietListBack}>
        {weekDiets.length > 0
          ? weekDiets.map((menu, idx) => {
              return isClick === 0 ||
                Number.parseInt(isClick) === Number.parseInt(idx) + 1 ? (
                <div key={idx}>
                  <WeekComplete
                    weekDate={weekList[idx]}
                    menu={menu}
                    setSummaryInfo={setSummaryInfo}
                    setDietId={setDietId}
                    setSelectDate={setSelectDate}
                  />
                </div>
              ) : null;
            })
          : null}
      </div>
    </div>
  );
};

export default DietComplete;
