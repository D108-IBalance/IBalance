//내부 모듈
import classes from "./Calendar.module.css";

const Calendar = (props) => {
  const { month, year, setDate } = props;
  console.log(month, year, setDate);

  return (
    <div className={classes.container}>
      <p>January 01</p>
    </div>
  );
};

export default Calendar;
