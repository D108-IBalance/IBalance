import classes from "./DiaryCards.module.css";
import spoonIcon from "../../assets/diary/img/diet_icon_white.png";
import addIcon from "../../assets/diary/img/add_diet_icon.png";
import { useEffect, useState } from "react";

const DiaryCard = (props) => {
  const { setPageStep, diet, setDietId } = props;
  const [cardTitle, setCardTitle] = useState("");
  const goToDiary = () => {
    setDietId(diet.dietId);
    diet.mealTime === "NONE" ? setPageStep(1) : setPageStep(2);
  };
  useEffect(() => {
    if (diet) {
      diet.mealTime === "NONE"
        ? setCardTitle("제목 없음")
        : setCardTitle(diet.mealTime);
    }
  }, [diet]);
  return (
    <section className={classes.card}>
      <div
        className={classes.cardContentBox}
        onClick={() => {
          goToDiary();
        }}>
        <img src={spoonIcon} alt="수저아이콘" className={classes.spoonIcon} />
        <div className={classes.line} />
        <main className={classes.cardContent}>
          <p className={classes.cardTitle}>{cardTitle}</p>
          {diet["menuList"].map((food, idx) => {
            return (
              <p key={idx} className={classes.cardFood}>
                {food}
              </p>
            );
          })}
          {diet.mealTime !== "NONE" ? null : (
            <img src={addIcon} className={classes.addDiary} />
          )}
        </main>
      </div>
    </section>
  );
};

export default DiaryCard;
