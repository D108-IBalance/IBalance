import { useNavigate } from "react-router-dom";
import classes from "./TodayDiet.module.css";

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
        <div className={classes.dietIcon}></div>
        <span>오늘의 식단표</span>
      </div>
      <div className={classes.todayDietBox}>
        {userDiet.length == 0 ? (
          <>
            <div className={classes.emptyIcon}></div>
            <p>오늘의 식단을 추천받아보세요.</p>
          </>
        ) : userDiet.menuList ? (
          <div>{userDiet.menuList}</div>
        ) : null}
      </div>
    </section>
  );
};

export default TodayDiet;
