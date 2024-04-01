// 내부 모듈
import classes from "./DiaryReview.module.css";
import example1 from "../../assets/diet/recipe1.png";
import example2 from "../../assets/diet/recipe2.png";
import example3 from "../../assets/diet/recipe3.png";
import { useState } from "react";

const DiaryReview = (props) => {
  const [current, setCurrent] = useState(0);
  const { selectedDate } = props;
  const hateFoods = [
    { food: "갈치", isHate: true },
    { food: "돼지고기", isHate: false },
    { food: "흰쌀", isHate: false },
    { food: "무", isHate: false },
    { food: "다시마", isHate: true },
    { food: "오징어", isHate: false },
    { food: "소고기", isHate: false },
    { food: "문어", isHate: false },
    { food: "고등어", isHate: true },
  ];
  const dietDataArr = [
    { img: example1, rate: 5, title: "수제 함박 스테이크" },
    { img: example2, rate: 3, title: "흰 쌀밥" },
    { img: example3, rate: 4, title: "미역국" },
  ];
  const onCurrentMove = (step) => {
    if (current === 0 && step < 0) {
      setCurrent(dietDataArr.length - 1);
      return;
    }
    if (current === 2 && step > 0) {
      setCurrent(0);
      return;
    }
    setCurrent(current + step);
  };
  return (
    <div className={classes.container}>
      <header
        className={classes.dietList}
        style={{ transform: `translateX(${current * -100}%)` }}>
        {dietDataArr.map((diet, id) => {
          return (
            <section key={id} className={classes.menuContainer}>
              <div
                src={example1}
                alt="menuImg"
                className={classes.menuImg}
                style={{
                  backgroundImage: `url(${diet.img})`,
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
                <p className={classes.menuTitle}>{diet.title}</p>
                <div className={classes.sideInfo}>
                  <p className={classes.menuCategory}>Main menu</p>
                  <p className={classes.rating}>
                    {[...new Array(diet.rate)].map((_, idx) => {
                      return (
                        <span key={idx} className={classes.star}>
                          ★
                        </span>
                      );
                    })}
                    {[...new Array(5 - diet.rate)].map((_, idx) => {
                      return (
                        <span key={idx} className={classes.star}>
                          ☆
                        </span>
                      );
                    })}
                  </p>
                  <span className={classes.menuList}>
                    메뉴 목록 : 수제 함박 스테이크, 돈까스, 흰 쌀밥, 뭘까
                    그다음은?
                  </span>
                </div>
              </div>
            </section>
          );
        })}
      </header>
      <hr />
      <section className={classes.diaryContainer}>
        <p className={classes.diaryTitle}>{selectedDate} - 아침 일기</p>
        <p className={classes.diaryContent}>
          동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리 나라 만세
          무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세.
        </p>
        <p className={classes.diaryWhen}>2024.04.21 작성</p>
      </section>
      <hr />
      <section className={classes.diaryContainer}>
        <p className={classes.diaryTitle}>아이 편식 재료</p>
        <div className={classes.foodContent}>
          {hateFoods.map((food, idx) => {
            return (
              <span
                className={food.isHate ? classes.hateFood : classes.notHate}
                key={idx}>
                {food.food}
              </span>
            );
          })}
        </div>
      </section>
    </div>
  );
};

export default DiaryReview;
