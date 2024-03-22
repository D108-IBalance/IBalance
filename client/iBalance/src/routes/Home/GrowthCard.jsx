// 외부 모듈
import { useEffect, useMemo, useState } from "react";
import { useSelector } from "react-redux";

// 내부 모듈
import classes from "./GrowthCard.module.css";
import prevArrow from "../../assets/home/prevArrow.svg";
import nextArrow from "../../assets/home/nextArrow.svg";
import { getUserChart } from "./ServerConnect";
import Chart from "./Chart.jsx";

const GrowthCard = (props) => {
  const { chartInfo } = props;
  const [toggle, setToggle] = useState(false);
  const [heightChart, setHeightChart] = useState({});
  const [heightLast, setHeightLast] = useState(true);
  const [heightPage, setHeightPage] = useState(0);
  const [weightChart, setWeightChart] = useState({});
  const [weightLast, setWeightLast] = useState(true);
  const [weightPage, setWeightPage] = useState(0);
  const CHILD_ID = useSelector((state) => state.childId);
  const getChart = async (isHeight, step) => {
    const page = isHeight === "height" ? heightPage + step : weightPage + step;
    let value = await getUserChart(page, CHILD_ID);
    if (isHeight === "height") {
      setHeightChart(value.data.data);
      setHeightLast(value.data.data.last);
      setHeightPage(page);
    } else {
      setWeightChart(value.data.data);
      setWeightLast(value.data.data.last);
      setWeightPage(page);
    }
  };

  useEffect(() => {
    if (chartInfo) {
      setHeightChart(Object.assign(chartInfo));
      setHeightLast(chartInfo.last);
      setWeightChart(Object.assign(chartInfo));
      setWeightLast(chartInfo.last);
    }
  }, [chartInfo]);

  const COMPONENT_INFO = useMemo(() => {
    return [
      {
        title: "우리 아이 성장 곡선",
        class: "total",
        isLast: toggle ? weightLast : heightLast,
        page: toggle ? weightPage : heightPage,
        chart: toggle ? weightChart : heightChart,
        isHeight: toggle ? "weight" : "height",
      },
      {
        title: "우리 아이 키 성장 곡선",
        class: "height",
        isLast: heightLast,
        page: heightPage,
        chart: heightChart,
        isHeight: "height",
      },
      {
        title: "우리 아이 몸무게 곡선",
        class: "weight",
        isLast: weightLast,
        page: weightPage,
        chart: weightChart,
        isHeight: "weight",
      },
    ];
  }, [
    heightChart,
    heightLast,
    heightPage,
    weightChart,
    weightLast,
    weightPage,
    toggle,
  ]);

  return (
    <>
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
                  {!data.isLast ? (
                    <img
                      src={prevArrow}
                      alt="prev"
                      className={classes.prev}
                      onClick={() => {
                        getChart(data.isHeight, 1);
                      }}
                    />
                  ) : null}
                  {data.page > 0 ? (
                    <img
                      src={nextArrow}
                      alt="next"
                      className={classes.next}
                      onClick={() => {
                        getChart(data.isHeight, -1);
                      }}
                    />
                  ) : null}
                </div>
              </div>
              <Chart chart={data.chart} isHeight={data.isHeight}></Chart>
            </div>
          </div>
        );
      })}
    </>
  );
};

export default GrowthCard;
