// 내부 모듈
import { useNavigate } from "react-router-dom";
import classes from "./RecipeMore.module.css";

const RecipeMore = () => {
  const navigate = useNavigate();
  return (
    <div className={classes.container}>
      <div className={classes.titleBox}>
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
        <div className={classes.card}></div>
        <div className={classes.card}></div>
        <div className={classes.card}></div>
        <div className={classes.card}></div>
      </div>
    </div>
  );
};
export default RecipeMore;
