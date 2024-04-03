import { useNavigate } from "react-router-dom";
import classes from "./TodayDiet.module.css";
import spoon from "../../assets/home/diet_icon1.png";
import { useState } from "react";

const TodayDiet = (props) => {
  const { userDiet } = props;
  const [menuStep, setMenuStep] = useState(0);
  const prevStep = () => {
    setMenuStep((prev) => (prev === 0 ? userDiet.length - 1 : prev - 1));
  };
  const nextStep = () => {
    setMenuStep((prev) => (prev === userDiet.length - 1 ? 0 : prev + 1));
  };
  const navigate = useNavigate();
  return (
    <section className={classes.dietBox}>
      <div className={classes.dietTitle}>
        <div style={{ display: "flex" }}>
          <div className={classes.dietIcon} />
          <span>오늘의 식단표</span>
        </div>
        {userDiet.length > 1 ? (
          <div className={classes.direction}>
            <div className={classes.prev} onClick={prevStep} />
            <div className={classes.next} onClick={nextStep} />
          </div>
        ) : null}
      </div>

      {userDiet.length === 0 ? (
        <div
          className={classes.noDietBox}
          onClick={() => {
            navigate("/diet");
          }}>
          <div className={classes.emptyIcon} />
          <p>오늘의 식단을 추천받아보세요.</p>
        </div>
      ) : (
        <div
          className={classes.todayDietBox}
          onClick={() => {
            navigate("/diet");
          }}>
          <img className={classes.spoonIcon} src={spoon} />
          <div className={classes.separate} />
          <div>
            {userDiet[menuStep].menuList.map((menu, idx) => {
              return (
                <div key={idx} className={classes.menuText}>
                  {menu.menuName}
                </div>
              );
            })}
          </div>
        </div>
      )}
    </section>
  );
};

export default TodayDiet;
