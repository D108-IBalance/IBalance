// 외부 모듈
import { useEffect, useState } from "react";

// 내부 모듈
import classes from "./GrowthCard.module.css";

const Chart = (props) => {
  const Y_START = 30;
  const MAX_HEIGHT = 150;
  const DIVIDE_MAX = MAX_HEIGHT / 2;
  const { chart, isHeight, svgAni } = props;
  const [growthChart, setGrowthChart] = useState([]);
  const [clickStep, setClickStep] = useState(-1);
  const [avrChart, setAvrChart] = useState([
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
  ]);

  useEffect(() => {
    if (chart && Object.keys(chart).length > 0) {
      const userChart = chart.growthList.map((data, idx) => {
        return {
          id: idx,
          recordDate: data.recordDate,
          startDate: data.startDate,
          endDate: data.endDate,
          height: data.height,
          weight: data.weight,
        };
      });
      const averageChart = chart.growthList.map((data, idx) => {
        for (let avr of chart.averageList) {
          if (avr.month === data.month)
            return {
              id: idx,
              height: avr.averageHeight,
              weight: avr.averageWeight,
            };
        }
      });
      setGrowthChart(userChart.reverse());
      setAvrChart(averageChart.reverse());
      setClickStep(userChart.length - 1);
    }
  }, [chart]);
  const X_STEP = Math.round(180 / growthChart.length + 2);

  return (
    <svg
      viewBox="0 0 200 200"
      style={{
        transform: "scaleY(-1)",
        paddingLeft: "30px",
        paddingRight: "30px",
      }}>
      {clickStep >= 0 ? (
        <>
          <path
            d={`M0 ${avrChart[0][isHeight] + Y_START} ${avrChart.map(
              (data, idx) => {
                return `${X_STEP * (idx + 0.5)} ${data[isHeight] + Y_START} `;
              },
            )}`}
            fill="#fe724c"
            stroke="#fe724c"
            opacity={1}
            strokeWidth={0.5}
          />
          <path
            className={classes[svgAni]}
            d={`M0 ${growthChart[0][isHeight] + Y_START} ${growthChart.map(
              (data, idx) => {
                return `${X_STEP * (idx + 0.5)} ${data[isHeight] + Y_START} `;
              },
            )}`}
            fill="white"
            stroke="red"
            opacity={0.5}
            strokeWidth={2}
          />
          <line
            x1={0}
            y1={DIVIDE_MAX + Y_START}
            x2={MAX_HEIGHT + Y_START}
            y2={DIVIDE_MAX + Y_START}
            stroke="gray"
            opacity={0.2}
          />
          <line
            x1={0}
            y1={Y_START}
            x2={MAX_HEIGHT + Y_START}
            y2={Y_START}
            stroke="gray"
            opacity={0.5}
          />
          <line
            x1={X_STEP * (clickStep + 0.5)}
            x2={X_STEP * (clickStep + 0.5)}
            y1={Y_START}
            y2={growthChart[clickStep][isHeight] + 30}
            stroke="#fe724c"
            strokeWidth={2}
            strokeDasharray={4}
          />
          <rect
            x={X_STEP * (clickStep + 0.5) - 15}
            y={growthChart[clickStep][isHeight] + 35}
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
            y={-(growthChart[clickStep][isHeight] + 42)}
            fontSize={9}
            fontWeight="600">
            {growthChart[clickStep][isHeight] +
              `${isHeight === "height" ? "cm" : "kg"}`}
          </text>
          <text
            style={{ transform: "scaleY(-1)" }}
            x={X_STEP * (clickStep + 0.5) - 21}
            fill="black"
            y={-20}
            fontSize={8}
            fontWeight={500}>
            <tspan>{growthChart[clickStep]["startDate"]}</tspan>
            <tspan
              x={X_STEP * (clickStep + 0.5) - 3}
              y={-11}
              fontSize={13}
              fontWeight={"light"}>
              ~
            </tspan>
            <tspan x={X_STEP * (clickStep + 0.5) - 21} y={-4}>
              {growthChart[clickStep]["endDate"]}
            </tspan>
          </text>

          {growthChart.map((data, idx) => {
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
        </>
      ) : null}
    </svg>
  );
};

export default Chart;
