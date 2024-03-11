/* eslint-disable */

// 내부 모듈
import classes from "./EmptyDiet.module.css";

const EmptyDiet = () => {
  return (
    <div className={classes.emptyBox}>
      <div className={classes.emptyIcon}></div>
      <div className={classes.emptyBtn}>식단 받기</div>
    </div>
  );
};

export default EmptyDiet;
