import DiaryCard from "./DiaryCard";
import classes from "./DiaryCards.module.css";

const DiaryCards = (props) => {
  const { selectedDate } = props;
  return (
    <div className={classes.container}>
      <header>
        <p className={classes.dateDiary}>{`${selectedDate}`}</p>
      </header>
      <DiaryCard></DiaryCard>
      <DiaryCard></DiaryCard>
      <DiaryCard></DiaryCard>
    </div>
  );
};

export default DiaryCards;
