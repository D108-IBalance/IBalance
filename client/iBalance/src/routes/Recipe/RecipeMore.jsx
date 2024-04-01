// 내부 모듈
import { useNavigate } from "react-router-dom";
import classes from "./RecipeMore.module.css";
import sample1 from "../../assets/recipe/sample1.png";

const RecipeMore = () => {
  const navigate = useNavigate();
  return (
    <div className={classes.container}>
      <div className={classes.backBox}>
        <div
          className={classes.backIcon}
          onClick={() => {
            navigate(-1);
          }}></div>
      </div>
      <div className={classes.cardTitleBox}>
        <p className={classes.cardTitle}>
          <span>당근</span> 레시피
        </p>
      </div>
      <div className={classes.cardBox}>
        <div
          className={classes.recipeImgBox}
          onClick={() => {
            navigate("/recipe/item");
          }}>
          <img src={sample1} alt="" className={classes.recipeImg} />
          <div className={classes.imgTextBox}>
            <div className={classes.recipeName}>당근전</div>
          </div>
        </div>
        <div className={classes.recipeImgBox}>
          <img src={sample1} alt="" className={classes.recipeImg} />
          <div className={classes.imgTextBox}>
            <div className={classes.recipeName}>당근전</div>
          </div>
        </div>
        <div className={classes.recipeImgBox}>
          <img src={sample1} alt="" className={classes.recipeImg} />
          <div className={classes.imgTextBox}>
            <div className={classes.recipeName}>당근전</div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default RecipeMore;
