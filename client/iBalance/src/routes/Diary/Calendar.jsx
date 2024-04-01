// 외부 모듈
import React, { useEffect } from "react";
import { useState } from "react";
import { useSelector } from "react-redux";

//내부 모듈
import classes from "./Calendar.module.css";
import selectImg from "../../assets/diary/img/select.svg";
import { getDietDates } from "./ServerConnect";

const Calendar = (props) => {
  const MONTHS = [
    "1",
    "2",
    "3",
    "4",
    "5",
    "6",
    "7",
    "8",
    "9",
    "10",
    "11",
    "12",
  ];
  const { setSelectedDate, setPageStep } = props;
  const WEEKS = ["일", "월", "화", "수", "목", "금", "토"];
  const today = new Date();
  const [year, setYear] = useState(today.getFullYear());
  const [month, setMonth] = useState(today.getMonth());
  const [weekArr, setWeekArr] = useState([]);
  const [currentClick, setCurrentClick] = useState(-1);
  const LAST_MONTH_IDX = 11;
  const FIRST_MONTH_IDX = 0;
  const IS_NEXT =
    month === LAST_MONTH_IDX && year === today.getFullYear() ? false : true;
  const IS_PREV = month === FIRST_MONTH_IDX && year === 2024 ? false : true;
  const CHILD_ID = useSelector((state) => state.childId);
  const movePrev = () => {
    if (month === FIRST_MONTH_IDX) {
      setYear(year - 1);
      setMonth(LAST_MONTH_IDX);
      return;
    }
    setMonth(month - 1);
  };
  const moveNext = () => {
    if (month === LAST_MONTH_IDX) {
      setYear(year + 1);
      setMonth(FIRST_MONTH_IDX);
      return;
    }
    setMonth(month + 1);
  };

  const onClickDay = (dateInfo, id) => {
    setSelectedDate(dateInfo);
    setPageStep(0);
    setCurrentClick(id);
  };

  useEffect(() => {
    const createPrevMonthDays = (days, firstWeekDay) => {
      for (let noDay = 0; noDay < firstWeekDay; noDay++) {
        let day = new Date(year, month, noDay - firstWeekDay + 1).getDate();
        let weekDay = new Date(year, month, noDay - firstWeekDay + 1).getDay();
        days.push({
          날짜: day,
          요일: weekDay,
          style: "disable",
          haveDiets: false,
        });
      }
    };
    const createCurrentMonthDays = (days, lastDay, dietDays) => {
      const dietDaysSet = new Set(dietDays);
      for (let day = 1; day <= lastDay; day++) {
        let weekDay = new Date(year, month, day).getDay();
        days.push({
          날짜: day,
          요일: weekDay,
          style: "able",
          haveDiets: dietDaysSet.has(day),
        });
      }
    };
    const getDayArr = (dietDays) => {
      let days = [];
      const firstWeekDay = new Date(year, month, 1).getDay();
      const lastDay = new Date(year, month + 1, 0).getDate();
      // 이전 달 정보
      createPrevMonthDays(days, firstWeekDay);
      // 이번 달 정보
      createCurrentMonthDays(days, lastDay, dietDays);
      return days;
    };
    const getWeekArr = (dayArr) => {
      const weeks = [];
      for (let i = 0; i < dayArr.length; i += 7) {
        weeks.push(dayArr.slice(i, i + 7));
      }
      return weeks;
    };
    const getDietInfo = async () => {
      let dietDays = [];
      const res = await getDietDates(CHILD_ID, year, month);
      if (res.data.status === 200) {
        dietDays = res.data.data.map((dietDate) => {
          const tempDate = new Date(dietDate[dietDate]);
          return tempDate.getDate();
        });
      }
      let dayArr = getDayArr(dietDays);
      const weeks = getWeekArr(dayArr);
      setWeekArr(weeks);
    };
    getDietInfo();
  }, [year, month, CHILD_ID]);
  return (
    <div className={classes.container}>
      <header className={classes.controller}>
        <span
          className={classes.nowDate}>{`${year}년 ${MONTHS[month]}월`}</span>
        <section>
          {IS_PREV ? (
            <img
              src={selectImg}
              alt="이전박스"
              onClick={movePrev}
              className={`${classes.controlBtn} ${classes.prevImg}`}
            />
          ) : null}
          {IS_NEXT ? (
            <img
              src={selectImg}
              alt="다음박스"
              onClick={moveNext}
              className={`${classes.controlBtn} ${classes.nextImg}`}
            />
          ) : null}
        </section>
      </header>
      <main>
        <table>
          <thead>
            <tr>
              {WEEKS.map((day, idx) => {
                return <th key={idx}>{day}</th>;
              })}
            </tr>
          </thead>
          <tbody>
            {weekArr.map((week, idx) => {
              return (
                <tr key={idx}>
                  {week.map((day, id) => {
                    return (
                      <td
                        key={id}
                        className={`${classes[day["style"]]}`}
                        onClick={() => {
                          day["haveDiets"]
                            ? onClickDay(
                                `${year}-${MONTHS[month]}-${day["날짜"]}`,
                                day["날짜"],
                              )
                            : null;
                        }}>
                        <span
                          className={
                            day["haveDiets"]
                              ? currentClick === day["날짜"]
                                ? classes.currentClick
                                : classes.canClick
                              : ""
                          }>
                          {day["날짜"]}
                        </span>
                      </td>
                    );
                  })}
                </tr>
              );
            })}
          </tbody>
        </table>
      </main>
    </div>
  );
};

export default React.memo(Calendar);
