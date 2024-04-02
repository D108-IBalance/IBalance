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
  const parentRef = useRef(null);
  const [isLoading, setIsloading] = useState(true);

  const { setIsOpen, setIsMore, moreRecipe, setRecipeData } = props;
  const [recipeList, setRecipeList] = useState(null);

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
  }, []);

  const onTest = async (recipeId) => {
    const res = await getPickySolutionDetail(moreRecipe, recipeId);
    setRecipeData(res.data.data);
    setIsOpen(true);
  };

  useEffect(() => {
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          console.log(entry.target);
          if (entry.isIntersecting && !isLoading) {
            console.log("hi");
            //호출
            const getData = async () => {
              setIsloading(true);
              const res = await getPickySolutionList(
                childId,
                moreRecipe,
                8,
                recipeList[`${recipeList.length - 1}`].recipeId,
              );
              setRecipeList((prev) => [...prev, ...res.data.data]);
              setIsloading(false);
            };
            getData();
          }
        });
      },
      { threshold: 0.1 },
    );
    if (parentRef.current) {
      observer.observe(parentRef.current);
    }
    return () => {
      if (parentRef.current) {
        observer.unobserve(parentRef.current);
      }
    };
  }, [parentRef, isLoading]);

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
      <div className={classes.cardBox} ref={parentRef}>
        {recipeList &&
          recipeList.map((recipe, idx) => {
            return (
              <div
                key={idx}
                className={classes.recipeImgBox}
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
      <div className={classes.loadingBtn}></div>
    </div>
  );
};
export default RecipeMore;
