// 외부 모듈
import { useState } from "react";

// 내부 모듈
import classes from "./DiaryWrite.module.css";
import prevIcon from "../../assets/diary/img/prevArrow.svg";

const DiaryWrite = (props) => {
  const { selectedDate, setPageStep } = props;
  const [selection, setSelection] = useState([
    {
      id: 0,
      title: "아침",
      content: "morning",
      checked: false,
    },
    {
      id: 1,
      title: "점심",
      content: "launch",
      checked: false,
    },
    {
      id: 2,
      title: "저녁",
      content: "dinner",
      checked: false,
    },
  ]);
  const [rating, setRating] = useState([false, false, false, false, false]);
  const [ingredients, setIngredients] = useState([
    { food: "돼지고기", isSelect: false },
    { food: "대추", isSelect: false },
    { food: "밀", isSelect: false },
    { food: "쌀", isSelect: false },
    { food: "히힣", isSelect: false },
    { food: "저저", isSelect: false },
    { food: "zz", isSelect: false },
  ]);
  const onSelect = (idx, isChecked) => {
    let prevSelection = selection.map((data, id) => {
      data["checked"] = idx === id && !isChecked;
      return data;
    });
    setSelection(prevSelection);
  };
  const onRating = (isStar, idx) => {
    let prevRating = rating.map((_, id) => id <= idx);
    setRating(prevRating);
  };
  const onBalance = (idx) => {
    let prevIngredients = [...ingredients];
    prevIngredients[idx]["isSelect"] = !prevIngredients[idx]["isSelect"];
    setIngredients(prevIngredients);
  };
  return (
    <div className={classes.container}>
      <header className={classes.headerContainer}>
        <img
          src={prevIcon}
          alt="prev"
          className={classes.backIcon}
          onClick={() => {
            setPageStep(0);
          }}
        />
        <p className={classes.title}>{selectedDate}</p>
      </header>
      <section className={classes.sectionContainer}>
        <header className={classes.selectBox}>
          {selection.map((data, idx) => {
            return (
              <div className={classes.selection} key={data.id}>
                <span className={classes.checkTitle}>{data.title}</span>
                <input
                  type="checkbox"
                  id={data.content}
                  className={classes[`${data.content}Input`]}
                  checked={data.checked}
                  onChange={() => {
                    onSelect(idx, data.checked);
                  }}
                />
                <label
                  htmlFor={data.content}
                  className={classes[data.content]}
                />
              </div>
            );
          })}
        </header>
        <textarea
          cols="40"
          rows="3"
          className={classes.review}
          maxLength={80}
          placeholder="우리 아이 식습관을 기록해주세요."
        />
        <article>
          {[1, 2, 3, 4].map((_, key) => {
            return (
              <div className={classes.menuBox} key={key}>
                <div className={classes.menuImg} />
                <div className={classes.menuInfo}>
                  <p className={classes.menuTitle}>메뉴명</p>
                  <p className={classes.ingredient}>히히 히히 히힣 </p>
                  <div className={classes.rating}>
                    {rating.map((isStar, idx) => {
                      return isStar ? (
                        <p
                          key={idx}
                          className={classes.star}
                          onClick={() => {
                            onRating(isStar, idx);
                          }}>
                          ★
                        </p>
                      ) : (
                        <p
                          key={idx}
                          className={classes.star}
                          onClick={() => {
                            onRating(isStar, idx);
                          }}>
                          ☆
                        </p>
                      );
                    })}
                  </div>
                </div>
              </div>
            );
          })}
        </article>
        <footer className={classes.footBox}>
          <div>
            <p className={classes.noBalaceTitle}>
              우리 아이가 편식하는 재료를 선택해주세요.
            </p>
            <div className={classes.noBalance}>
              {ingredients.map((ingredient, idx) => {
                return (
                  <div
                    onClick={() => {
                      onBalance(idx);
                    }}
                    className={
                      ingredient.isSelect
                        ? classes.noIngredientClicked
                        : classes.noIngredient
                    }
                    key={idx}>
                    {ingredient.food}
                  </div>
                );
              })}
            </div>
          </div>
          <button className={classes.btn}>일기 저장</button>
        </footer>
      </section>
    </div>
  );
};

export default DiaryWrite;
