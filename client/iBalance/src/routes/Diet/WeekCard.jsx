/* eslint-disable */

//외부 모듈
import { useState } from "react";
import Slider from "react-slick";

// 내부 모듈
import classes from "./WeekCard.module.css";
import "./sliderWeek/slick-theme.css";
import "./sliderWeek/slick.css";

const WeekCard = (props) => {
  let { weekList } = props;
  let { isClick } = props;
  let { setIsclick } = props;
  const settings = {
    dots: false,
    infinite: false,
    speed: 500,
    slidesToShow: 4,
    slidesToScroll: 3,
    initialSlide: 0,
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 7,
          slidesToScroll: 3,
          infinite: true,
        },
      },

      {
        breakpoint: 576,
        settings: {
          slidesToShow: 4,
          slidesToScroll: 4,
          dots: false,
        },
      },
    ],
  };

  return (
    <div style={{ width: "100vw", overflow: "hidden", paddingLeft: "10px" }}>
      {/* <div className={`slider - container`}> */}
      <Slider {...settings}>
        <div>
          <div
            onClick={(e) => {
              setIsclick(0);
            }}
            className={
              isClick === 0 ? classes.weekCardClicked : classes.weekCard
            }>
            <p className={classes.dayFont}>All</p>
            <p className={classes.dateFont}>Day</p>
          </div>
        </div>
        {weekList.map((data, key) => {
          return (
            <div key={key}>
              <div
                onClick={() => {
                  setIsclick(key + 1);
                }}
                className={
                  isClick === key + 1
                    ? classes.weekCardClicked
                    : classes.weekCard
                }>
                <p className={classes.dayFont}>{data["day"]}</p>
                <p className={classes.dateFont}>{data["date"]}</p>
              </div>
            </div>
          );
        })}
      </Slider>
    </div>
  );
};

export default WeekCard;
