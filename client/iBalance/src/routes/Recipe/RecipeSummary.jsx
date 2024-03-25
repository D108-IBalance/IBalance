// 내부 모듈

import { useNavigate } from "react-router-dom";
import classes from "./RecipeSummary.module.css";

const RecipeSummary = (props) => {
  const navigate = useNavigate();
  const { setIsOpen } = props;
  return (
    <>
      <div className={classes.container}>
        <div className={classes.cardTitleBox}>
          <p className={classes.cardTitle}>
            <span>당근</span> 레시피
          </p>
          <p
            onClick={() => {
              navigate("/recipe/more");
            }}>
            더 보기
          </p>
        </div>
        <div className={classes.cardContent}>
          <div className={classes.cardBox}>
            <div
              className={classes.card}
              onClick={() => {
                setIsOpen(true);
                navigate(`/recipe/item`);
              }}></div>
            <div className={classes.card}></div>
            <div className={classes.card}></div>
            <div className={classes.card}></div>
          </div>
        </div>
      </div>
    </>
  );
};
export default RecipeSummary;
