// 외부 모듈
import { useEffect, useState } from "react";

// 내부 모듈
import classes from "./DiaryReview.module.css";
import { readDiary } from "./ServerConnect";
import { useSelector } from "react-redux";

const DiaryReview = (props) => {
  const [current, setCurrent] = useState(0);
  const { dietId, setPageStep } = props;
  const [year, setYear] = useState("");
  const [month, setMonth] = useState("");
  const [date, setDate] = useState("");
  const [pickys, setpickys] = useState([]);

  const [mealTime, setMealTime] = useState("");
  const [diaryContent, setDiaryContent] = useState("");
  const childId = useSelector((state) => state.childId);
  const [dietDataArr, setDietDataArr] = useState([]);

  const onCurrentMove = (step) => {
    if (current === 0 && step < 0) {
      setCurrent(dietDataArr.length - 1);
      return;
    }
    if (current === 3 && step > 0) {
      setCurrent(0);
      return;
    }
    setCurrent(current + step);
  };
  useEffect(() => {
    const korMeal = { BREAKFAST: "아침", LUNCH: "점심", DINNER: "저녁" };
    const read = async () => {
      const res = await readDiary(childId, dietId);
      const DATE = new Date(res.data.data.date);
      setDietDataArr(res.data.data.diaryMenuList);
      setDiaryContent(res.data.data.content);
      setYear(DATE.getFullYear());
      setMonth(DATE.getMonth() + 1);
      setDate(DATE.getDate());
      setMealTime(korMeal[res.data.data.mealTime]);
      setpickys(res.data.data.materials);
    };
    if (dietId && childId) {
      read();
    }
  }, [dietId, childId]);
  return (
    <div className={classes.container}>
      <div style={{ position: "relative" }}>
        <header
          className={classes.dietList}
          style={{ transform: `translateX(${current * -100}%)` }}>
          {dietDataArr.length > 0 &&
            dietDataArr.map((diet, id) => {
              return (
                <section key={id} className={classes.menuContainer}>
                  <div
                    className={classes.menuImg}
                    style={{
                      backgroundImage: `url(${diet.menuImgUrl})`,
                    }}>
                    <div
                      className={classes.prevBtn}
                      onClick={() => {
                        onCurrentMove(-1);
                      }}
                    />
                    <div
                      className={classes.nextBtn}
                      onClick={() => {
                        onCurrentMove(+1);
                      }}
                    />
                  </div>
                  <div className={classes.menuInfo}>
                    <p className={classes.menuTitle}>{diet.menuName}</p>
                    <div className={classes.sideInfo}>
                      <p className={classes.menuCategory}>Main menu</p>
                      <p className={classes.rating}>
                        {[...new Array(diet.score)].map((_, idx) => {
                          return (
                            <span key={idx} className={classes.star}>
                              ★
                            </span>
                          );
                        })}
                        {[...new Array(5 - diet.score)].map((_, idx) => {
                          return (
                            <span key={idx} className={classes.star}>
                              ☆
                            </span>
                          );
                        })}
                      </p>
                      <span className={classes.menuList}>
                        메뉴 목록 :{" "}
                        {diet.materials?.map((matieral, key) => {
                          return (
                            <span key={key}>
                              {key === diet.materials.length - 1
                                ? `${matieral}`
                                : `${matieral}, `}
                            </span>
                          );
                        })}
                      </span>
                    </div>
                  </div>
                </section>
              );
            })}
        </header>
        <div
          className={classes.backIcon}
          onClick={() => {
            setPageStep(0);
          }}
        />
      </div>
      <hr />
      <section className={classes.diaryContainer}>
        <p className={classes.diaryTitle}>
          {`${year}년 ${month}월 ${date}일 - `} {mealTime}
        </p>
        <p className={classes.diaryContent}>{diaryContent}</p>
        <p className={classes.diaryWhen}>{`${year}. ${month}. ${date} 작성`}</p>
      </section>
      <hr />
      <section className={classes.diaryContainer}>
        <p className={classes.diaryTitle}>아이 편식 재료</p>
        <div className={classes.foodContent}>
          {pickys.map((food, idx) => {
            return (
              <span
                className={food.picky ? classes.hateFood : classes.notHate}
                key={idx}>
                {food.material}
              </span>
            );
          })}
        </div>
      </section>
    </div>
  );
};

export default DiaryReview;
