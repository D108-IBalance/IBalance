import classes from "./DiaryCards.module.css";
import spoonIcon from "../../assets/diary/img/diet_icon_white.png";
import addIcon from "../../assets/diary/img/add_diet_icon.png";
const DiaryCard = (props) => {
  const { setPageStep } = props;
  const DIET = {
    title: "제목1",
    food: ["돈까스", "수제 함박 스테이크", "어묵 볶음", "두부 계란 탕"],
  };
  const IS_WRITE = false;
  return (
    <section className={classes.card}>
      <div className={classes.cardContentBox}>
        <img src={spoonIcon} alt="수저아이콘" className={classes.spoonIcon} />
        <div className={classes.line} />
        <main className={classes.cardContent}>
          <p className={classes.cardTitle}>{DIET["title"]}</p>
          {DIET["food"].map((food, idx) => {
            return (
              <p key={idx} className={classes.cardFood}>
                {food}
              </p>
            );
          })}
          {IS_WRITE ? null : (
            <img
              src={addIcon}
              className={classes.addDiary}
              onClick={() => {
                setPageStep(1);
              }}
            />
          )}
        </main>
      </div>
    </section>
  );
};

export default DiaryCard;
