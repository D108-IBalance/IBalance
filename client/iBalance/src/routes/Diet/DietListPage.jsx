/* eslint-disable */

// 외부 모듈
import { useEffect, useRef, useState } from "react";

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
  // 유저가 접속 시 해당 화면이 모바일인지 혹은 데스크탑인지 점검
  const IS_MOBILE = window.matchMedia(
    "(hover: none) and (pointer: coarse)",
  ).matches;
  let { weekList } = props;
  let [isClick, setIsclick] = useState(0);
  // 드래그 중인지 여부를 체크
  let [isDrag, setIsDrag] = useState(false);
  // 이전 마우스 드래그 좌표
  let [prevPoint, setPrevPoint] = useState(0);
  let [trans, setTrans] = useState(0);
  let onDragStart = (e) => {
    let pointX = null;
    pointX = e.changedTouches[0]["screenX"];
    setPrevPoint(pointX);
    setIsDrag(true);
  };

  let onDragging = (e) => {
    if (!isDrag) return;
    let pointX = null;
    pointX = e.changedTouches[0]["screenX"];
    if (prevPoint < pointX && trans !== 0) {
      console.log("gg");
      setTrans(trans + 50);
    }
    if (prevPoint > pointX && trans !== -100) {
      setTrans(trans - 50);
    }
    setPrevPoint(pointX);
    setIsDrag(false);
  };

  let onDragEnd = () => {};

  return (
    <div style={{ width: "100vw", overflow: "hidden" }}>
      <div
        style={{ transform: `translateX(${trans}%)` }}
        className={classes.weekCardBox}
        onTouchStart={onDragStart}
        onTouchMove={onDragging}
        onTouchEnd={onDragEnd}>
        <div
          className={isClick === 0 ? classes.weekCardClicked : classes.weekCard}
          onClick={(e) => {
            setIsclick(0);
          }}>
          <p className={classes.dayFont}>All</p>
          <p className={classes.dateFont}>Day</p>
        </div>
        {weekList.map((data, key) => {
          return (
            <div
              key={key}
              className={
                isClick === key + 1 ? classes.weekCardClicked : classes.weekCard
              }
              onClick={() => {
                setIsclick(key + 1);
              }}>
              <p className={classes.dayFont}>{data["day"]}</p>
              <p className={classes.dateFont}>{data["date"]}</p>
            </div>
          );
        })}
      </div>
    </div>
  );
};
export default DietListPage;
