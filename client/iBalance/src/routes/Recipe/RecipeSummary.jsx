// 내부 모듈
import classes from "./RecipeSummary.module.css";
import Carousel from "../../modules/Carousel/Carousel";

const RecipeSummary = (props) => {
  const { setIsOpen, setIsMore, recipeIngre, setRecipeData, setMoreRecipe } =
    props;
  return (
    <>
      {recipeIngre &&
        recipeIngre.map((ingre, idx) => {
          return (
            <div className={classes.container} key={idx}>
              <div className={classes.cardTitleBox}>
                <p className={classes.cardTitle}>
                  <span>{ingre.pickyMaterialName}</span> 레시피
                </p>
                <p
                  className={classes.moreBtn}
                  onClick={() => {
                    setIsMore(true);
                    setMoreRecipe(ingre.pickyMaterialName);
                  }}>
                  더 보기
                </p>
              </div>
              <div className={classes.cardContent}>
                <div className={classes.cardBox}>
                  <Carousel
                    setRecipeData={setRecipeData}
                    pickyMaterialName={ingre.pickyMaterialName}
                    setIsOpen={setIsOpen}
                    recipeList={ingre.recipes}></Carousel>
                </div>
              </div>
            </div>
          );
        })}
    </>
  );
};
export default RecipeSummary;
