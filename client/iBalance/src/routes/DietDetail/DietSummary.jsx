// 외부 모듈
import { useNavigate } from "react-router-dom";

// 내부 모듈
import classes from "./DietSummary.module.css";
import sample1 from "../../assets/diet/sample1.png";
import sample2 from "../../assets/diet/sample2.png";
import sample3 from "../../assets/diet/sample3.png";
import sample4 from "../../assets/diet/sample4.png";

const DietSummary = () => {
  const navigate = useNavigate();
  const dietList = [
    {
      name: "현미밥",
      kcal: "321kcal",
      ingredient: ["현미", "흰쌀"],
      img: sample1,
    },
    {
      name: "수제함박 스테이크",
      kcal: "561kcal",
      ingredient: [
        "돼지고기",
        "빵가루",
        "우스터소스",
        "후추가루",
        "토마토케찹",
        "버터",
      ],
      img: sample2,
    },
    {
      name: "어묵볶음",
      kcal: "137kcal",
      ingredient: [
        "어묵",
        "양파",
        "당근",
        "간장",
        "대파",
        "고추가루",
        "다진마늘",
        "설탕",
      ],
      img: sample3,
    },
    {
      name: "두부 계란탕",
      kcal: "83kcal",
      ingredient: [
        "두부",
        "계란",
        "감자",
        "대파",
        "소금",
        "멸치",
        "간장",
        "소금",
      ],
      img: sample4,
    },
  ];
  return (
    <>
      <div className={classes.container}>
        <div className={classes.titleBox}>
          <div
            className={classes.backIcon}
            onClick={() => {
              navigate(-1);
            }}></div>
          <div className={classes.titleTextBox}>
            <div className={classes.titleIcon}></div>
            <div className={classes.titleText}>2024.03.04 (월)</div>
          </div>
          <div className={classes.tempIcon}></div>
        </div>
        <div className={classes.contentBox}>
          {dietList.map((menu, idx) => {
            return (
              <div
                key={idx}
                className={classes.card}
                style={{ zIndex: idx }}
                onClick={() => {
                  navigate(`/detail/${menu.name}`);
                }}>
                <img src={menu.img} className={classes.cardImg}></img>
                <div className={classes.cardContent}>
                  <p className={classes.menuName}>{menu.name}</p>
                  <p className={classes.menuKcal}>{menu.kcal}</p>
                  <p className={classes.menuIngre}>
                    {menu.ingredient.map((food, idx) => {
                      return idx === menu.ingredient.length - 1
                        ? `${food}`
                        : `${food}, `;
                    })}
                  </p>
                </div>
                <div className={classes.resetIcon}></div>
              </div>
            );
          })}
        </div>
      </div>
    </>
  );
};
export default DietSummary;
