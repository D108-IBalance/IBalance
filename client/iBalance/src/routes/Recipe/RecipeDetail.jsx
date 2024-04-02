// 외부 모듈

// 내부 모듈
import classes from "./RecipeDetail.module.css";

const RecipeDetail = (props) => {
  const { setIsOpen, recipeData } = props;

  return (
    <>
      <div className={classes.ModalBack}>
        <div className={classes.detailModal}>
          {recipeData && (
            <div>
              <div className={classes.webVer}>
                <div className={classes.ImgBox}>
                  <div className={classes.titleBox}>
                    <div
                      className={classes.backIcon}
                      onClick={() => {
                        setIsOpen(false);
                      }}></div>
                  </div>
                  <img
                    src={recipeData.recipeData.recipeImgUrl}
                    className={classes.menuImg}></img>
                </div>
                <div className={classes.nameBox}>
                  <div className={classes.menuName}>
                    {recipeData.recipeData.recipeTitle}
                  </div>
                </div>
              </div>

              <div className={classes.contentBox}>
                <div className={classes.contentBox}>
                  <div className={classes.orangeText}>식재료</div>
                  {recipeData.recipeData.recipeMaterialList.map((item, idx) => {
                    return (
                      <div key={idx} className={classes.ingreBox}>
                        <div className={classes.ingre}>
                          {item["materialName"]}
                        </div>
                        <div>{item["materialOpecity"]}</div>
                      </div>
                    );
                  })}
                  <div className={classes.flexLine}></div>
                  <div className={classes.orangeText}>레시피</div>
                  {recipeData.recipeData.recipeSteps.map((item, idx) => {
                    return (
                      <div className={classes.recipeBox} key={idx}>
                        <img
                          src={item.recipeStepImg}
                          className={classes.recipeImg}
                          alt=""
                        />
                        <p>
                          {idx + 1}. {item.recipeStepContent}
                        </p>
                      </div>
                    );
                  })}
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </>
  );
};
export default RecipeDetail;
