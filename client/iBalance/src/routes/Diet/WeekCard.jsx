// 내부 모듈
import classes from "./WeekCard.module.css";

const WeekCard = (props) => {
  const { weekList, isClick, setIsclick } = props;

  return (
    <div className={classes.container}>
      <div className={classes.weekCardBox}>
        <div
          onClick={() => {
            setIsclick(0);
          }}
          className={
            isClick === 0 ? classes.weekCardClicked : classes.weekCard
          }>
          <p className={classes.dayFont}>All</p>
          <p className={classes.dateFont}>Day</p>
        </div>
      </div>
      {weekList.map((data, idx) => {
        return (
          <div key={idx} className={classes.weekCardBox}>
            <div
              onClick={() => {
                setIsclick(idx + 1);
              }}
              className={
                isClick === idx + 1 ? classes.weekCardClicked : classes.weekCard
              }>
              <p className={classes.dayFont}>{data["day"]}</p>
              <p className={classes.dateFont}>{data["date"]}</p>
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default WeekCard;
