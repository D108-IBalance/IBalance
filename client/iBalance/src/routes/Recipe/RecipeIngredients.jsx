import classes from "./RecipeIngredients.module.css";
const RecipeIngredients = (props) => {
  const { recipeIngre } = props;
  return (
    <div className={classes.container}>
      <div className={classes.titleBox}>
        <div className={classes.titleIcon}></div>
        <p>우리아이 편식 재료</p>
      </div>
      <div className={classes.contentBox}>
        {recipeIngre.map((item, idx) => {
          return (
            <div className={classes.itemType} key={idx}>
              {item.pickyMaterialName}
            </div>
          );
        })}
      </div>
    </div>
  );
};
export default RecipeIngredients;
