/* eslint-disable */

// 외부 모듈
import { Outlet } from "react-router-dom";
import { useState } from "react";

// 내부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";
import classes from "./RecipePage.module.css";
import Header from "../../modules/Header/Header";
import RecipeSummary from "./RecipeSummary.jsx";
import RecipeIngredients from "./RecipeIngredients.jsx";

const RecipePage = () => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <>
      <div className={classes.gridSet}>
        <Header />
        <NavbarModule isClick={1}></NavbarModule>
        <div className={classes.container}>
          <div
            className={
              isOpen === false
                ? classes.recipeContentBox
                : classes.recipeContentBoxOpen
            }>
            <RecipeIngredients></RecipeIngredients>
            <RecipeSummary setIsOpen={setIsOpen}></RecipeSummary>
            <RecipeSummary></RecipeSummary>
          </div>
          <Outlet context={{ isOpen, setIsOpen }}></Outlet>
        </div>
      </div>
    </>
  );
};

export default RecipePage;
