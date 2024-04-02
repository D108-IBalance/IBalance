// 외부 모듈
import { useEffect, useMemo, useState } from "react";
import { useSelector } from "react-redux";

// 내부 모듈
import classes from "./GrowthCard.module.css";
import prevArrow from "../../assets/home/prevArrow.svg";
import nextArrow from "../../assets/home/nextArrow.svg";
import { getHeightChart, getWeightChart } from "./ServerConnect";
import Chart from "./Chart.jsx";

const GrowthCard = (props) => {
  const { heightChartInfo, weightChartInfo } = props;
  const [svgAni, setSvgAni] = useState("svgAni");
  const [toggle, setToggle] = useState(false);
  const [heightChart, setHeightChart] = useState({});
  const [heightLast, setHeightLast] = useState(true);
  const [heightPage, setHeightPage] = useState(0);
  const [weightChart, setWeightChart] = useState({});
  const [weightLast, setWeightLast] = useState(true);
  const [weightPage, setWeightPage] = useState(0);
  const CHILD_ID = useSelector((state) => state.childId);
  const getChart = async (isHeight, step) => {
    setSvgAni("");
    const page = isHeight === "height" ? heightPage + step : weightPage + step;
    if (isHeight === "height") {
      const value = await getHeightChart(page, CHILD_ID);
      setHeightChart(value.data.data);
      setHeightLast(value.data.data.last);
      setHeightPage(page);
    } else {
      const value = await getWeightChart(page, CHILD_ID);
      setWeightChart(value.data.data);
      setWeightLast(value.data.data.last);
      setWeightPage(page);
    }
    setSvgAni("svgAni");
  };
  let timer = null;
  const onToggle = () => {
    setSvgAni("");
    setToggle(!toggle);
    timer = setTimeout(() => {
      setSvgAni("svgAni");
    }, 50);
  };

  useEffect(() => {
    if (heightChartInfo && weightChartInfo) {
      setHeightChart(Object.assign(heightChartInfo));
      setHeightLast(heightChartInfo.last);
      setWeightChart(Object.assign(weightChartInfo));
      setWeightLast(weightChartInfo.last);
    }
  }, [heightChartInfo, weightChartInfo]);
  useEffect(() => {
    return () => {
      clearTimeout(timer);
    };
  }, [timer]);

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
                      onToggle();
                    }}>
                    키
                  </span>
                  <span> /</span>
                  <span
                    className={toggle ? classes.bold : null}
                    onClick={() => {
                      onToggle();
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
                    <p>우리 아이 성장 곡선</p>
                  </div>
                  <div className={classes.legend}>
                    <div className={classes.notSelectIcon} />
                    <p>평균 성장 곡선</p>
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
              <Chart
                chart={data.chart}
                isHeight={data.isHeight}
                svgAni={svgAni}></Chart>
            </div>
          </div>
        );
      })}
    </>
  );
};

export default GrowthCard;
