// 외부 모듈
import { useEffect, useState } from "react";

// 내부 모듈
import DiaryCard from "./DiaryCard";
import classes from "./DiaryCards.module.css";
import { getDietDiary } from "./ServerConnect";
import { useSelector } from "react-redux";

const DiaryCards = (props) => {
  const { selectedDate, setPageStep, setDietId } = props;
  const [dietTitle, setDietTitle] = useState("");
  const [diets, setDiets] = useState([]);
  const childId = useSelector((state) => state.childId);
  useEffect(() => {
    const getDiary = async () => {
      const date = new Date(selectedDate);
      let year = date.getFullYear();
      let month = "" + (date.getMonth() + 1);
      let day = "" + date.getDate();
      if (month.length < 2) month = "0" + month;
      if (day.length < 2) day = "0" + day;
      const res = await getDietDiary(childId, `${year}-${month}-${day}`);
      setDiets(res.data.data);
    };
    if (selectedDate) {
      const date = new Date(selectedDate);
      setDietTitle(
        `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일 식단`,
      );
      getDiary();
    }
  }, [selectedDate, childId]);
  return (
    <div className={classes.container}>
      <header>
        <p className={classes.dateDiary}>{`${dietTitle}`}</p>
      </header>
      {diets.length > 0 &&
        diets.map((diet, idx) => {
          return (
            <DiaryCard
              setPageStep={setPageStep}
              diet={diet}
              setDietId={setDietId}
              key={idx}
            />
          );
        })}
    </div>
  );
};

export default DiaryCards;
