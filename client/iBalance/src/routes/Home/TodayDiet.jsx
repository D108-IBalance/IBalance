import classes from "./TodayDiet.module.css";

const TodayDiet = () => {
  return (
    <section className={classes.dietBox}>
      <div className={classes.dietTitle}>
        <div className={classes.dietIcon}></div>
        <span>오늘의 식단표</span>
      </div>
      <div className={classes.todayDietBox}>
        <div className={classes.emptyIcon}></div>
        <p>오늘의 식단을 추천받아보세요.</p>
      </div>
    </section>
  );
};

export default TodayDiet;
