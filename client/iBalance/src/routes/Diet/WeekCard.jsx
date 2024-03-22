// 내부 모듈
import classes from "./WeekCard.module.css";

const WeekCard = (props) => {
  const { weekList, isClick, setIsClick } = props;
  const handleAllClick = () => {
    setIsClick(0);
  };

  const handleCardClick = (index) => {
    setIsClick(index + 1);
  };
  return (
    <div className={classes.container}>
      <div className={classes.weekCardBox}>
        <div
          onClick={() => {
            handleAllClick();
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
                handleCardClick(idx);
              }}
              className={
                isClick === idx + 1 ? classes.weekCardClicked : classes.weekCard
              }>
              <p className={classes.dayFont}>{data.day}</p>
              <p className={classes.dateFont}>{data.date}</p>
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default WeekCard;
