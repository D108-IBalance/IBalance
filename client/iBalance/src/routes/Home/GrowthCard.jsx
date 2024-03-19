/* eslint-disable */

// 내부 모듈
import { useCallback, useEffect, useState } from "react";
import classes from "./GrowthCard.module.css";
import prevArrow from "../../assets/home/prevArrow.svg";
import nextArrow from "../../assets/home/nextArrow.svg";
import { getUserChart } from "./ServerConnect";
import { useSelector } from "react-redux";

const GrowthCard = () => {
  const [toggle, setToggle] = useState(false);
  const [heightPage, setHeightPage] = useState(0);
  const [weightPage, setWeightPage] = useState(0);
  const TOKEN = useSelector((state) => {
    return state.token;
  });
  let heightChart = {};
  let weightChart = {};

  useEffect(() => {
    const getHeightChart = async () => {
      try {
        const value = await getUserChart(heightPage, TOKEN);
        heightChart = value.data;
        console.log(heightChart);
      } catch {
        // alert("에러");
      }
    };
    getHeightChart();
  }, [heightPage]);

  useEffect(() => {
    const getWeightChart = async () => {
      try {
        const value = await getUserChart(heightPage, TOKEN);
        weightChart = value.data;
        console.log(weightChart);
      } catch {
        // alert("에러");
      }
    };
    getWeightChart();
  }, [weightPage]);

  let isHeightPrev = true;
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
              <div className={classes.chartMeta}>
                <div className={classes.legends}>
                  <div className={classes.legend}>
                    <div className={classes.selectIcon} />
                    <p>선택된 요소</p>
                  </div>
                  <div className={classes.legend}>
                    <div className={classes.notSelectIcon} />
                    <p>선택되지 않은 요소</p>
                  </div>
                </div>
                <div className={classes.controller}>
                  <img src={prevArrow} alt="prev" className={classes.prev} />
                  <img src={nextArrow} alt="next" className={classes.next} />
                </div>
              </div>
              <Chart
                isHeight={
                  (idx === 0 && !toggle) || idx === 1 ? "height" : "weight"
                }></Chart>
            </div>
          </div>
        );
      })}
    </>
  );
};

const Chart = (props) => {
  const Y_START = 30;
  const MAX_HEIGHT = 150;
  const DIVIDE_MAX = MAX_HEIGHT / 2;
  let { isHeight } = props;
  let chartInfo = [
    {
      recordDate: "2024-03-13",
      startDate: "2024-03-10",
      endDate: "2024-03-16",
      height: 30,
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
      height: 90.0,
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
  const [clickStep, setClickStep] = useState(3);
  const X_STEP = Math.round(180 / chartInfo.length + 2);
  chartInfo = chartInfo.reverse();

  return (
    <svg
      viewBox="0 0 200 200"
      style={{
        transform: "scaleY(-1)",
        paddingLeft: "30px",
        paddingRight: "30px",
      }}>
      <path
        className={classes.svgAni}
        d={`M0 ${chartInfo[0][isHeight] + Y_START} ${chartInfo.map(
          (data, idx) => {
            return `${X_STEP * (idx + 0.5)} ${data[isHeight] + Y_START} `;
          },
        )}`}
        fill="white"
        stroke="#fe724c"
        opacity={0.5}
        strokeWidth={2.5}
      />
      <line
        x1={0}
        y1={DIVIDE_MAX + Y_START}
        x2={MAX_HEIGHT + Y_START}
        y2={DIVIDE_MAX + Y_START}
        stroke="gray"
        opacity={0.5}
      />
      <line
        x1={0}
        y1={Y_START}
        x2={MAX_HEIGHT}
        y2={Y_START}
        stroke="gray"
        opacity={0.5}
      />
      <line
        x1={X_STEP * (clickStep + 0.5)}
        x2={X_STEP * (clickStep + 0.5)}
        y1={Y_START}
        y2={chartInfo[clickStep][isHeight] + 30}
        stroke="#fe724c"
        strokeWidth={2}
        strokeDasharray={4}
      />
      <rect
        x={X_STEP * (clickStep + 0.5) - 15}
        y={chartInfo[clickStep][isHeight] + 35}
        fill="black"
        opacity={0.8}
        rx={5}
        width={35}
        height={20}
      />
      <text
        style={{ transform: "scaleY(-1)" }}
        x={X_STEP * (clickStep + 0.5) - 12}
        fill="white"
        y={-(chartInfo[clickStep][isHeight] + 42)}
        fontSize={9}
        fontWeight="600">
        {chartInfo[clickStep][isHeight] +
          `${isHeight === "height" ? "cm" : "kg"}`}
      </text>
      <text
        style={{ transform: "scaleY(-1)" }}
        x={X_STEP * (clickStep + 0.5) - 21}
        fill="black"
        y={-20}
        fontSize={8}
        fontWeight={500}>
        <tspan>{chartInfo[clickStep]["startDate"]}</tspan>
        <tspan
          x={X_STEP * (clickStep + 0.5) - 3}
          y={-11}
          fontSize={13}
          fontWeight={"light"}>
          ~
        </tspan>
        <tspan x={X_STEP * (clickStep + 0.5) - 21} y={-4}>
          {chartInfo[clickStep]["endDate"]}
        </tspan>
      </text>

      {chartInfo.map((data, idx) => {
        return (
          <circle
            key={idx}
            cx={X_STEP * (idx + 0.5)}
            cy={data[isHeight] + 30}
            fill="white"
            stroke={clickStep === idx ? "#f75c31" : "#f7ae9a"}
            strokeWidth={2}
            r={3}
            cursor="pointer"
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
