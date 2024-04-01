import classes from "./RecipeIngredients.module.css";
const RecipeIngredients = () => {
  const ingreList = ["당근", "시금치", "양파"];
  return (
    <div className={classes.container}>
      <div className={classes.titleBox}>
        <div className={classes.titleIcon}></div>
        <p>우리아이 편식 재료</p>
      </div>
      <div className={classes.contentBox}>
        {ingreList.map((item, idx) => {
          return (
            <div className={classes.itemType} key={idx}>
              {item}
            </div>
          );
        })}
      </div>
    </div>
  );
};
export default RecipeIngredients;
