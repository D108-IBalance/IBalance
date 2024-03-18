// 내부 모듈
import classes from "./DayDiet.module.css";

const DayDiet = (props) => {
  const { day, diets, addDietCard, saveDiet } = props;
  return (
    <div className={classes.dayDietBox}>
      <div className={classes.dayTitleBox}>
        <p className={classes.dayTitle}>{day}</p>
        {diets.length <= 2 && saveDiet === false ? (
          <p className={classes.addTitle} onClick={() => addDietCard()}>
            식단 추가
          </p>
        ) : null}
      </div>
      <div className={classes.dayCardBox}>
        {diets.map((diet, index) => {
          return (
            // <div >
            <div className={classes.dayCard} key={index}>
              <div className={classes.cardIcon}></div>
              <div className={classes.cardLine}></div>
              <div className={classes.dietContentBox}>
                <div>
                  <p>{diet.riceMenu}</p>
                  <p>{diet.mainMenu}</p>
                  <p>{diet.sideMenu}</p>
                  <p>{diet.soupMenu}</p>
                </div>
              </div>
            </div>
            // </div>
          );
        })}
      </div>
    </div>
  );
};

export default DayDiet;
