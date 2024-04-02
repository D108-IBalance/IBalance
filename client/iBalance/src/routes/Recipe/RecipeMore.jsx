// 외부 모듈
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";

// 내부 모듈
import classes from "./RecipeMore.module.css";
import { getPickyCate, getPickySolutionList } from "./ServerConnect.js";

const RecipeMore = (props) => {
  const childId = useSelector((state) => state.childId);

  const { setIsOpen, setIsMore } = props;
  const [recipeList, setRecipeList] = useState(null);

  useEffect(() => {
    const getPickyData = async () => {
      const res = await Promise.all([
        getPickyCate(childId),
        getPickySolutionList(childId, "당근", 10, ""),
      ]);
      setRecipeList(res[1].data.data);
      // console.log(res[0]);
      // console.log(res[1]);
    };
    getPickyData();
  }, []);
  return (
    <div className={classes.container}>
      <div className={classes.backBox}>
        <div
          className={classes.backIcon}
          onClick={() => {
            setIsMore(false);
          }}></div>
      </div>
      <div className={classes.cardTitleBox}>
        <p className={classes.cardTitle}>
          <span>당근</span> 레시피
        </p>
      </div>
      <div className={classes.cardBox}>
        {recipeList &&
          recipeList.map((recipe, idx) => {
            return (
              <div
                key={idx}
                className={classes.recipeImgBox}
                onClick={() => {
                  setIsOpen(true);
                }}>
                <img
                  src={recipe.recipeImgUrl}
                  alt=""
                  className={classes.recipeImg}
                />
                <div className={classes.imgTextBox}>
                  <div className={classes.recipeName}>{recipe.recipeTitle}</div>
                </div>
              </div>
            );
          })}
      </div>
    </div>
  );
};
export default RecipeMore;
