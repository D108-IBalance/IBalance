import DiaryCard from "./DiaryCard";
import classes from "./DiaryCards.module.css";

const DiaryCards = (props) => {
  const { selectedDate, setPageStep } = props;
  return (
    <div className={classes.container}>
      <header>
        <p className={classes.dateDiary}>{`${selectedDate}`}</p>
      </header>
      <DiaryCard setPageStep={setPageStep}></DiaryCard>
      <DiaryCard setPageStep={setPageStep}></DiaryCard>
      <DiaryCard setPageStep={setPageStep}></DiaryCard>
    </div>
  );
};

export default DiaryCards;
