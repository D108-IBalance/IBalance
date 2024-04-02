// 외부 모듈
import { useSelector } from "react-redux";
import { useEffect, useState, useRef } from "react";

// 내부 모듈
import classes from "./RecipeMore.module.css";
import {
  getPickyCate,
  getPickySolutionList,
  getPickySolutionDetail,
} from "./ServerConnect.js";

const RecipeMore = (props) => {
  const childId = useSelector((state) => state.childId);
  const targetRef = useRef(null);
  const [isLoading, setIsloading] = useState(true);

  const { setIsOpen, setIsMore, moreRecipe, setRecipeData } = props;
  const [recipeList, setRecipeList] = useState(null);
  const updateList = async () => {
    setIsloading(true);
    const value = await getPickySolutionList(
      childId,
      moreRecipe,
      8,
      recipeList[recipeList.length - 1].recipeId,
    );
    if (value.data.status === 200) {
      setRecipeList((prev) => [...prev, ...value.data.data]);
      setIsloading(false);
    }
  };
  const io = new IntersectionObserver(
    (entris) => {
      entris.forEach((entry) => {
        if (entry.isIntersecting && !isLoading) {
          io.unobserve(entry.target);
          updateList();
        }
      });
    },
    { threshold: 0.9 },
  );
  useEffect(() => {
    const getPickyData = async () => {
      const res = await Promise.all([
        getPickyCate(childId),
        getPickySolutionList(childId, moreRecipe, 8, ""),
      ]);
      setRecipeList(res[1].data.data);
      setIsloading(false);
    };
    getPickyData();
    return () => {
      if (targetRef.current) {
        io.unobserve(targetRef.current);
      }
    };
  }, []);

  const onTest = async (recipeId) => {
    const res = await getPickySolutionDetail(moreRecipe, recipeId);
    setRecipeData(res.data.data);
    setIsOpen(true);
  };
  useEffect(() => {
    if (isLoading === false) {
      io.observe(targetRef.current);
    }
  }, [isLoading]);

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
        <div className={classes.cardTitleIcon}></div>
        <p className={classes.cardTitle}>
          <span>{moreRecipe}</span> 레시피
        </p>
      </div>
      <div className={classes.cardBox}>
        {recipeList &&
          recipeList.map((recipe, idx) => {
            return (
              <div
                key={idx}
                className={classes.recipeImgBox}
                ref={idx === recipeList.length - 1 ? targetRef : null}
                onClick={() => {
                  setIsOpen(true);
                  onTest(recipe.recipeId);
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
