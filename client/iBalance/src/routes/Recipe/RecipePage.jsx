/* eslint-disable */

// 외부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";

// 내부 모듈
import classes from "./RecipePage.module.css";

const RecipePage = () => {
  return (
    <>
      <div className={classes.gridSet}>
        <NavbarModule isClick={1}></NavbarModule>
        <div className={classes.recipeContentBox}>this is Recipe</div>
      </div>
    </>
  );
};

export default RecipePage;
