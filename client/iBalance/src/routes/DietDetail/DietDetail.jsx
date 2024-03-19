// 외부 모듈
import { useNavigate, useOutletContext } from "react-router-dom";
import sampleMenu from "../../assets/diet/sampleImg.png";
import recipe1 from "../../assets/diet/recipe1.png";
import recipe2 from "../../assets/diet/recipe2.png";
import recipe3 from "../../assets/diet/recipe3.png";

// 내부 모듈
import classes from "./DietDetail.module.css";

const DietDetail = () => {
  const { setIsOpen } = useOutletContext();
  const navigate = useNavigate();
  const menuList = {
    foodId: 0,
    foodKind: "Main menu",
    name: "수제 함박 스테이크",
    kcal: "561",
    ingredient: [
      "돼지고기",
      "빵가루",
      "우스터소스",
      "후추가루",
      "토마토케찹",
      "버터",
    ],
    nutrients: { carbo: "42.55", fat: "26.42", protein: "36.76" },
    img: sampleMenu,
    recipe: [
      {
        recipeImg: recipe1,
        recipeText:
          "양파는 곱게다져 후라이팬에 식용유를 둘러 소금,후추간으로 볶아서 식혀 주세요. 볼에 재료를 모두 넣고 양손으로 잘 치대어 주세요.",
      },
      {
        recipeImg: recipe2,
        recipeText:
          "프라이팬에 식용유를 두르고 양면 모두 노릇하게 구워 주세요. 버터를 제외한 소스의 재료를 볼에 넣고 잘 섞어주고, 프라이팬에 버터를 녹인 후 잘 섞은 소스의 재료를 모두 넣고 한번 끓여 주세요.",
      },
      {
        recipeImg: recipe3,
        recipeText:
          "그릇에 고기를 올리고 소스를 부운 후에 새싹을 올려주세요.냉장고에 있는 채소들과 곁들여 주면 더 좋아요",
      },
    ],
  };
  return (
    <>
      <div className={classes.ModalBack}>
        <div className={classes.detailModal}>
          <div className={classes.webVer}>
            <div className={classes.ImgBox}>
              <div className={classes.titleBox}>
                <div
                  className={classes.backIcon}
                  onClick={() => {
                    navigate("/detail");
                    setIsOpen(false);
                  }}></div>
              </div>
              <img src={menuList.img} className={classes.menuImg}></img>
              <div className={classes.likeBtn}></div>
            </div>
            <div className={classes.nameBox}>
              <div className={classes.menuName}>{menuList.name}</div>
              <div className={classes.grayText}>{menuList.foodKind}</div>
            </div>
          </div>

          <div className={classes.contentBox}>
            <div className={classes.orangeText}>영양 정보</div>
            <div>
              <div className={classes.flexRow}>
                <p>총 칼로리</p>
                <p className={classes.orangeText}>
                  {menuList.kcal}
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
                    {menuList.nutrients.carbo}
                    <span className={classes.gramText}>g</span>
                  </p>
                </div>

                <div className={classes.flexLine}>
                  <div className={classes.flexRow}>
                    <div className={classes.protein}></div>
                    <p> 단백질</p>
                  </div>
                  <p className={classes.flexRow}>
                    {menuList.nutrients.protein}
                    <span className={classes.gramText}>g</span>
                  </p>
                </div>

                <div className={classes.flexLine}>
                  <div className={classes.flexRow}>
                    <div className={classes.fat}></div>
                    <p> 지방</p>
                  </div>
                  <p className={classes.flexRow}>
                    {menuList.nutrients.fat}
                    <span className={classes.gramText}> g</span>
                  </p>
                </div>
              </div>
            </div>
            <div className={classes.contentBox}>
              <div className={classes.orangeText}>식재료</div>
              {menuList.ingredient.map((item, idx) => {
                return (
                  <div className={classes.ingreBox} key={idx}>
                    {item}
                  </div>
                );
              })}
              <div className={classes.flexLine}></div>
              <div className={classes.orangeText}>레시피</div>
              {menuList.recipe.map((item, idx) => {
                return (
                  <div className={classes.recipeBox} key={idx}>
                    <img
                      src={item.recipeImg}
                      className={classes.recipeImg}
                      alt=""
                    />
                    <p>
                      {idx + 1}. {item.recipeText}
                    </p>
                  </div>
                );
              })}
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default DietDetail;
