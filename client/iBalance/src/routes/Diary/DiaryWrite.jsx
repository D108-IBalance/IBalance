// 외부 모듈
import { useEffect, useState } from "react";

// 내부 모듈
import classes from "./DiaryWrite.module.css";
import prevIcon from "../../assets/diary/img/prevArrow.svg";
import { getDiaryDetail, writeDiary } from "./ServerConnect";
import { useSelector } from "react-redux";

const DiaryWrite = (props) => {
  const { selectedDate, setPageStep, dietId, setDietId } = props;
  const chlidId = useSelector((state) => state.chlidId);
  const [diaryInfo, setDiaryInfo] = useState({});
  const [content, setContent] = useState("");
  const [menuRate, setMenuRate] = useState([]);
  const [mealTime, setMealTime] = useState("");
  const [pickyIdList, setPickyIdList] = useState([]);
  const [writeValidation, setWriteValidation] = useState(false);
  const [rateValidation, setRateValidation] = useState(false);
  const [selection, setSelection] = useState([
    {
      id: 0,
      title: "아침",
      content: "BREAKFAST",
      checked: false,
    },
    {
      id: 1,
      title: "점심",
      content: "lunch",
      checked: false,
    },
    {
      id: 2,
      title: "저녁",
      content: "dinner",
      checked: false,
    },
  ]);
  const [ingredients, setIngredients] = useState([]);
  const onSelect = (idx) => {
    switch (idx) {
      case 0:
        setMealTime("BREAKFAST");
        break;
      case 1:
        setMealTime("LUNCH");
        break;
      case 2:
        setMealTime("DINNER");
        break;
    }
    setSelection((prev) => {
      return prev.map((data, id) => {
        data["checked"] = idx === id;
        return data;
      });
    });
  };
  const onRating = (key, idx) => {
    setMenuRate((prev) => {
      const updatedRate = [...prev];
      updatedRate[key] = { ...updatedRate[key], rate: idx + 1 };
      return updatedRate;
    });
  };
  const onBalance = (idx) => {
    setIngredients((prev) => {
      return prev.map((item, index) => {
        index === idx ? { ...item, picky: !item.picky } : item;
      });
    });
    setPickyIdList((prev) => {
      const newPicks = prev.includes(idx)
        ? prev.filter((id) => id !== idx)
        : [...prev, idx];
      return newPicks;
    });
  };
  const writeContent = (e) => {
    const tempContent = e.currentTarget.value;
    setContent(tempContent);
    if (tempContent.length === 0) {
      setWriteValidation(false);
    }
  };
  const goBack = () => {
    setPageStep(0);
    setDietId(0);
  };
  const saveDiary = async () => {
    let diaryData = { dietId, content, menuRate, pickyIdList, mealTime };
    const res = await writeDiary(chlidId, diaryData);
    console.log(res);
  };
  useEffect(() => {
    if (menuRate) {
      let flag = true;
      for (let menu of menuRate) {
        if (menu.rate === 0) {
          flag = false;
          break;
        }
      }
      setRateValidation(flag);
    }
  }, [menuRate]);
  useEffect(() => {
    const getDiaryInfo = async () => {
      const res = await getDiaryDetail(dietId);
      if (res.data.status === 200) {
        const info = res.data.data;
        setDiaryInfo(info);

        const tempRatings = info.menu.map((item) => {
          let rating = { menuId: item.menuId, rate: 0 };
          return rating;
        });
        setIngredients(info.materials);
        setMenuRate(tempRatings);
      }
    };
    if (dietId) {
      getDiaryInfo();
    }
  }, [dietId]);
  return (
    <div className={classes.container}>
      <header className={classes.headerContainer}>
        <img
          src={prevIcon}
          alt="prev"
          className={classes.backIcon}
          onClick={() => {
            goBack();
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
                    onSelect(idx);
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
          onChange={(e) => {
            writeContent(e);
          }}
          maxLength={80}
          placeholder="우리 아이 식습관을 기록해주세요."
        />
        <article>
          {diaryInfo &&
            diaryInfo.menu.map((menu, key) => {
              return (
                <div className={classes.menuBox} key={key}>
                  <div className={classes.menuImg} />
                  <div className={classes.menuInfo}>
                    <p className={classes.menuTitle}>{menu.menuName}</p>
                    <p className={classes.ingredient}>{menu.materials} </p>
                    <div className={classes.rating}>
                      {[...new Array(5)].map((_, idx) => {
                        return idx < menuRate[key].rate ? (
                          <p
                            key={idx}
                            className={classes.star}
                            onClick={() => {
                              onRating(key, idx);
                            }}>
                            ★
                          </p>
                        ) : (
                          <p
                            key={idx}
                            className={classes.star}
                            onClick={() => {
                              onRating(key, idx);
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
              {ingredients &&
                ingredients.map((ingredient, idx) => {
                  return (
                    <div
                      onClick={() => {
                        onBalance(idx);
                      }}
                      className={
                        ingredient.picky
                          ? classes.noIngredientClicked
                          : classes.noIngredient
                      }
                      key={idx}>
                      {ingredient.material}
                    </div>
                  );
                })}
            </div>
          </div>
          {rateValidation && writeValidation && mealTime ? (
            <button
              className={classes.btn}
              onClick={() => {
                saveDiary();
              }}>
              일기 저장
            </button>
          ) : (
            <button className={classes.noBtn} disabled>
              일기 작성을 완료 해 주세요.
            </button>
          )}
        </footer>
      </section>
    </div>
  );
};

export default DiaryWrite;
