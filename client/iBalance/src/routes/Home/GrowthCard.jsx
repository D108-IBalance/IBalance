/* eslint-disable */

// 내부 모듈
import { useEffect, useRef, useState } from "react";
import classes from "./GrowthCard.module.css";

const GrowthCard = () => {
  const [toggle, setToggle] = useState(false);
  const COMPONENT_INFO = [
    {
      title: "우리 아이 성장 곡선",
      class: "total",
    },
    {
      title: "우리 아이 키 성장 곡선",
      class: "height",
    },
    {
      title: "우리 아이 몸무게 곡선",
      class: "weight",
    },
  ];

  return (
    <>
      {/* 3개의 컴포넌트를 띄워놓고 미디어 쿼리로 해당 사항을 display none으로 제어 */}
      {COMPONENT_INFO.map((data, idx) => {
        return (
          <div
            className={`${classes[data.class]} ${classes.cardBody}`}
            key={idx}>
            <div className={classes.category}>
              <div className={classes.cardTitle}>
                <div className={classes.cardIcon} />
                <span>{data.title}</span>
              </div>
              {idx === 0 ? (
                <div className={classes.toggle}>
                  <span
                    className={!toggle ? classes.bold : null}
                    onClick={() => {
                      setToggle(!toggle);
                    }}>
                    키
                  </span>
                  <span> /</span>
                  <span
                    className={toggle ? classes.bold : null}
                    onClick={() => {
                      setToggle(!toggle);
                    }}>
                    몸무게
                  </span>
                </div>
              ) : null}
            </div>
            <div className={classes.card}>
              <Chart></Chart>
            </div>
          </div>
        );
      })}
    </>
  );
};

const Chart = (props) => {
  let chartInfo = [
    {
      recordDate: "2024-03-13",
      startDate: "2024-03-10",
      endDate: "2024-03-16",
      height: 10,
      weight: 36.8,
    },
    {
      recordDate: "2024-03-13",
      startDate: "2024-03-10",
      endDate: "2024-03-16",
      height: 138.0,
      weight: 36.5,
    },
    {
      recordDate: "2024-03-11",
      startDate: "2024-03-10",
      endDate: "2024-03-16",
      height: 137.0,
      weight: 36.0,
    },
    {
      recordDate: "2024-03-11",
      startDate: "2024-03-10",
      endDate: "2024-03-16",
      height: 135.0,
      weight: 35.0,
    },
  ];
  chartInfo = chartInfo.reverse();
  const [clickStep, setClickStep] = useState(3);
  const X_STEP = Math.round(180 / chartInfo.length + 2);
  return (
    <svg
      viewBox="0 0 180 180"
      style={{
        transform: "scaleY(-1)",
        paddingLeft: "30px",
        paddingRight: "30px",
      }}>
      <path />
      {chartInfo.map((data, idx) => {
        return (
          <circle
            key={idx}
            cx={X_STEP * (idx + 0.5)}
            cy={data["height"]}
            fill="white"
            stroke={clickStep === idx ? "#f75c31" : "#f7ae9a"}
            strokeWidth={2}
            r={3}
            onClick={() => {
              setClickStep(idx);
            }}
          />
        );
      })}
    </svg>
  );
};

export default GrowthCard;
