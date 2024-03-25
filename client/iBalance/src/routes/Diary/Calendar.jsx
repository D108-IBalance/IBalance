// 외부 모듈
import React from "react";
import { useCallback, useMemo, useState } from "react";

//내부 모듈
import classes from "./Calendar.module.css";
import selectImg from "../../assets/diary/img/select.svg";

const Calendar = (props) => {
  const MONTHS = [
    "1월",
    "2월",
    "3월",
    "4월",
    "5월",
    "6월",
    "7월",
    "8월",
    "9월",
    "10월",
    "11월",
    "12월",
  ];
  const { setSelectedDate, setPageStep } = props;
  const WEEKS = ["일", "월", "화", "수", "목", "금", "토"];
  const today = new Date();
  const [year, setYear] = useState(today.getFullYear());
  const [month, setMonth] = useState(today.getMonth());
  const LAST_MONTH_IDX = 11;
  const FIRST_MONTH_IDX = 0;
  const IS_NEXT =
    month === LAST_MONTH_IDX && year === today.getFullYear() ? false : true;
  const IS_PREV = month === FIRST_MONTH_IDX && year === 2024 ? false : true;
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

  const onClickDay = (dateInfo) => {
    setSelectedDate(dateInfo);
    setPageStep(0);
  };

  const createPrevMonthDays = useCallback(
    (days, firstWeekDay) => {
      for (let noDay = 0; noDay < firstWeekDay; noDay++) {
        let day = new Date(year, month, noDay - firstWeekDay + 1).getDate();
        let weekDay = new Date(year, month, noDay - firstWeekDay + 1).getDay();
        days.push({ 날짜: day, 요일: weekDay, style: "disable" });
      }
    },
    [year, month],
  );
  const createCurrentMonthDays = useCallback(
    (days, lastDay) => {
      for (let day = 1; day <= lastDay; day++) {
        let weekDay = new Date(year, month, day).getDay();
        days.push({ 날짜: day, 요일: weekDay, style: "able" });
      }
    },
    [year, month],
  );

  const dayArr = useMemo(() => {
    let days = [];
    const firstWeekDay = new Date(year, month, 1).getDay();
    const lastDay = new Date(year, month + 1, 0).getDate();
    // 이전 달 정보
    createPrevMonthDays(days, firstWeekDay);
    // 이번 달 정보
    createCurrentMonthDays(days, lastDay);
    return days;
  }, [month, year, createCurrentMonthDays, createPrevMonthDays]);

  const weekArr = useMemo(() => {
    const weeks = [];
    for (let i = 0; i < dayArr.length; i += 7) {
      weeks.push(dayArr.slice(i, i + 7));
    }
    return weeks;
  }, [dayArr]);
  return (
    <div className={classes.container}>
      <header className={classes.controller}>
        <span className={classes.nowDate}>{`${year}년 ${MONTHS[month]}`}</span>
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
                        className={classes[day["style"]]}
                        onClick={() => {
                          onClickDay(
                            `${year}년 ${MONTHS[month]} ${day["날짜"]}일 식단`,
                          );
                        }}>
                        {day["날짜"]}
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
