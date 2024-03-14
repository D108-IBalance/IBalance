/* eslint-disable */

// 외부 모듈
import Slider from "react-slick";

// 내부 모듈
import classes from "./DayDiet.module.css";
import "./sliderDay/slick-theme.css";
import "./sliderDay/slick.css";

const DayDiet = (props) => {
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
          slidesToShow: 1,
          slidesToScroll: 1,
          dots: false,
        },
      },
    ],
  };
  const { day } = props;
  const { diets } = props;
  const { addDietCard } = props;
  // console.log(diets);
  return (
    <div className={classes.dayDietBox}>
      <div className={classes.dayTitleBox}>
        <p className={classes.dayTitle}>{day}</p>
        <p className={classes.addTitle} onClick={() => addDietCard()}>
          식단 추가
        </p>
      </div>
      {/* <Slider {...settings} style={{ display: "flex", flexDirection: "row" }}> */}
      {diets.map((diet, index) => {
        return (
          <div className={classes.dayCard} key={index}>
            <div className={classes.cardIcon}></div>
            <div className={classes.cardLine}></div>
            <div className={classes.dietContentBox}>
              <div>
                <p>{diet.riceMenu}</p>
                <p>{diet.mainMenu}</p>
                <p>{diet.sideMenu}</p>
                <p>{diet.soupMenu}</p>
              </div>
            </div>
          </div>
        );
      })}
      {/* </Slider> */}
    </div>
  );
};

export default DayDiet;
