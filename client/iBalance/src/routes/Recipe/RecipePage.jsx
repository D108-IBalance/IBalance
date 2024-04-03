/* eslint-disable */

// 외부 모듈
import { Outlet } from "react-router-dom";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";

// 내부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";
import classes from "./RecipePage.module.css";
import Header from "../../modules/Header/Header";
import RecipeSummary from "./RecipeSummary.jsx";
import RecipeIngredients from "./RecipeIngredients.jsx";
import RecipeDetail from "./RecipeDetail.jsx";
import RecipeMore from "./RecipeMore.jsx";
import { getPickyCate } from "./ServerConnect.js";

const RecipePage = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [isMore, setIsMore] = useState(false);
  const [moreRecipe, setMoreRecipe] = useState();
  const [recipeData, setRecipeData] = useState();
  const childId = useSelector((state) => state.childId);
  const [recipeIngre, setRecipeIngre] = useState();

  useEffect(() => {
    const getPickyData = async () => {
      const res = await Promise.all([getPickyCate(childId)]);
      const tempRecipes = res[0].data.data.filter(
        (ing) => ing.recipes.length > 0,
      );
      setRecipeIngre(tempRecipes);
    };
    getPickyData();
  }, []);

  return (
    <>
      <div className={classes.gridSet}>
        <Header />
        <NavbarModule isClick={1}></NavbarModule>
        <div className={classes.container}>
          {recipeIngre && recipeIngre.length ? (
            <div
              className={
                isOpen === false
                  ? classes.recipeContentBox
                  : classes.recipeContentBoxOpen
              }>
              <RecipeIngredients recipeIngre={recipeIngre}></RecipeIngredients>
              <RecipeSummary
                setMoreRecipe={setMoreRecipe}
                setRecipeData={setRecipeData}
                recipeIngre={recipeIngre}
                setIsOpen={setIsOpen}
                setIsMore={setIsMore}></RecipeSummary>
            </div>
          ) : (
            <div className={classes.recipeEmptyBox}>
              <div className={classes.recipeEmptyContent}>
                <div className={classes.recipeEmptyImg}></div>
                <p className={classes.recipeEmptyTitle}>
                  현재 편식하는 식재료가 없습니다.
                </p>
              </div>
            </div>
          )}
          {isMore ? (
            <RecipeMore
              moreRecipe={moreRecipe}
              setRecipeData={setRecipeData}
              setIsOpen={setIsOpen}
              setIsMore={setIsMore}></RecipeMore>
          ) : null}
          {isOpen ? (
            <RecipeDetail
              recipeData={{ recipeData }}
              setIsOpen={setIsOpen}></RecipeDetail>
          ) : null}
        </div>
      </div>
    </>
  );
};

export default RecipePage;
