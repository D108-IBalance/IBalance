// 내부 모듈
import classes from "./DietDetail.module.css";

const DietDetail = (props) => {
  const { setIsOpen, dietDetail } = props;

  return (
    <>
      <div className={classes.ModalBack}>
        <div className={classes.detailModal}>
          <div className={classes.ImgBox}>
            <div className={classes.titleBox}>
              <div
                className={classes.backIcon}
                onClick={() => {
                  setIsOpen(false);
                }}></div>
            </div>
            <div
              className={classes.menuImg}
              style={{
                backgroundImage: `url(${dietDetail.menuImgUrl})`,
              }}></div>
            {/* <img src={dietDetail.menuImgUrl} className={classes.menuImg}></img> */}
          </div>
          <div className={classes.nameBox}>
            <div className={classes.menuName}>{dietDetail.menuName}</div>
            <div className={classes.grayText}>{dietDetail.menuType} MENU</div>
          </div>

          <div className={classes.contentBox}>
            <div className={classes.orangeText}>영양 정보</div>
            <div>
              <div className={classes.flexRow}>
                <p>총 칼로리</p>
                <p className={classes.orangeText}>
                  {dietDetail.calorie}
                  <span className={classes.grayText}>kcal</span>
                </p>
              </div>

              <div className={classes.nutriBox}>
                <div className={classes.flexLine}>
                  <div className={classes.flexRow}>
                    <div className={classes.carboIcon}></div>
                    <p> 탄수화물</p>
                  </div>
                  <p className={classes.flexRow}>
                    {dietDetail.carbohydrate}
                    <span className={classes.gramText}>g</span>
                  </p>
                </div>

                <div className={classes.flexLine}>
                  <div className={classes.flexRow}>
                    <div className={classes.protein}></div>
                    <p> 단백질</p>
                  </div>
                  <p className={classes.flexRow}>
                    {dietDetail.protein}
                    <span className={classes.gramText}>g</span>
                  </p>
                </div>

                <div className={classes.flexLine}>
                  <div className={classes.flexRow}>
                    <div className={classes.fat}></div>
                    <p> 지방</p>
                  </div>
                  <p className={classes.flexRow}>
                    {dietDetail.fat}
                    <span className={classes.gramText}> g</span>
                  </p>
                </div>
              </div>
            </div>
            <div className={classes.contentBox}>
              <div className={classes.orangeText}>식재료</div>
              {dietDetail.materials.map((item, idx) => {
                return (
                  <div className={classes.ingreBox} key={idx}>
                    {item}
                  </div>
                );
              })}
              <div className={classes.flexLine}></div>
              <div className={classes.orangeText}>레시피</div>
              <div className={classes.recipeBox}>
                {dietDetail.recipe.map((item, idx) => {
                  return (
                    <div className={classes.recipe} key={idx}>
                      <p>{item}</p>
                    </div>
                  );
                })}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default DietDetail;
