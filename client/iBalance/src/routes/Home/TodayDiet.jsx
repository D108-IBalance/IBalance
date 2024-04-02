import { useNavigate } from "react-router-dom";
import classes from "./TodayDiet.module.css";
import spoon from "../../assets/home/diet_icon1.png";

const TodayDiet = (props) => {
  const { userDiet } = props;

  const navigate = useNavigate();
  return (
    <section
      className={classes.dietBox}
      onClick={() => {
        navigate("/diet");
      }}>
      <div className={classes.dietTitle}>
        <div className={classes.dietIcon} />
        <span>오늘의 식단표</span>
      </div>

      {userDiet.length === 0 ? (
        <div className={classes.noDietBox}>
          <div className={classes.emptyIcon} />
          <p>오늘의 식단을 추천받아보세요.</p>
        </div>
      ) : (
        <div className={classes.todayDietBox}>
          <img className={classes.spoonIcon} src={spoon} />
          <div className={classes.separate} />
          <div>
            {userDiet[0].menuList.map((menu, idx) => {
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
