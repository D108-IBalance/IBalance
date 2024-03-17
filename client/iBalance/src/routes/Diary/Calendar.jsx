//내부 모듈
import classes from "./Calendar.module.css";
import selectImg from "../../assets/diary/img/select.svg";
import { useCallback, useMemo, useState } from "react";
const Calendar = () => {
  const MONTHS = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
  ];
  const WEEKS = ["Su", "Mo", "Tu", "Wed", "Th", "Fr", "Sa"];
  const today = new Date();
  const [year, setYear] = useState(today.getFullYear());
  const [month, setMonth] = useState(today.getMonth());
  // 달력의 범위 변경 시 하위 IS_NEXT 및 IS_PREV 변경 시 변경 가능
  // 현재 년도보다 높은 년도일 시 False
  const IS_NEXT = month === 11 && year === today.getFullYear() ? false : true;
  // 서비스 시작 2024년도
  const IS_PREV = month === 0 && year === 2024 ? false : true;
  const movePrev = () => {
    if (month === 0) {
      setYear(year - 1);
      setMonth(11);
      return;
    }
    setMonth(month - 1);
  };
  const moveNext = () => {
    if (month === 11) {
      setYear(year + 1);
      setMonth(0);
      return;
    }
    setMonth(month + 1);
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
  // month값은 0부터 시작 12는 다음달 1월을 의미
  return (
    <div className={classes.container}>
      <header className={classes.controller}>
        <span className={classes.nowDate}>{`${MONTHS[month]} ${year}`}</span>
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
                      <td key={id} className={classes[day["style"]]}>
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

export default Calendar;
