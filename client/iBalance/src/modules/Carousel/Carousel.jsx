// 외부 모듈
import { useEffect, useRef } from "react";

// 내부 모듈
import classes from "./Carousel.module.css";
import { getPickySolutionDetail } from "../../routes/Recipe/ServerConnect";

const Carousel = (props) => {
  const { setIsOpen, recipeList, pickyMaterialName, setRecipeData } = props;

  const slider = useRef(null);
  const inner = useRef(null);
  let pressed = false;
  let startX = 0;
  let x = 0;

  const onSliderDown = (e) => {
    inner.current.style.pointerEvents = "auto";
    pressed = true;
    startX = e.nativeEvent.offsetX - inner.current.offsetLeft;
    slider.current.style.cursor = "grabbing";
  };
  const onSliderEnter = () => {
    slider.current.style.cursor = "grab";
  };
  const onSliderUp = () => {
    slider.current.style.cursor = "grab";
    pressed = false;
  };
  const onSliderMove = (e) => {
    if (!pressed) return;
    e.preventDefault();
    inner.current.style.pointerEvents = "none";
    x = e.nativeEvent.offsetX;
    inner.current.style.left = `${x - startX}px`;
    checkRange();
  };
  const checkRange = () => {
    let outSide = slider.current.getBoundingClientRect();
    let inSide = inner.current.getBoundingClientRect();
    if (parseInt(inner.current.style.left) > 0) {
      inner.current.style.left = "0px";
    } else if (inSide.right < outSide.right) {
      inner.current.style.left = `-${inSide.width - outSide.width}px`;
    }
  };

  const onTest = async (recipeId) => {
    const res = await getPickySolutionDetail(pickyMaterialName, recipeId);
    setRecipeData(res.data.data);
    setIsOpen(true);
  };

  useEffect(() => {
    const changePressed = () => {
      pressed = false;
    };
    window.addEventListener("mouseup", changePressed);
    return () => {
      window.removeEventListener("mouseup", changePressed);
    };
  }, []);
  return (
    <div className={classes.container}>
      <div
        className={classes.slider}
        ref={slider}
        onMouseDown={onSliderDown}
        onMouseEnter={onSliderEnter}
        onMouseUp={onSliderUp}
        onMouseMove={onSliderMove}>
        <div className={classes.inner} ref={inner}>
          {recipeList &&
            recipeList.map((item, idx) => {
              return (
                <div
                  key={idx}
                  className={classes.item}
                  onMouseUp={() => {
                    onTest(item.recipeId);
                  }}>
                  <div className={classes.recipeImgBox}>
                    <img
                      src={item.recipeImgUrl}
                      alt=""
                      className={classes.recipeImg}
                    />
                    <div className={classes.imgTextBox}>
                      <div className={classes.recipeName}>
                        {item.recipeTitle}
                      </div>
                    </div>
                  </div>
                </div>
              );
            })}
        </div>
      </div>
      <div></div>
    </div>
  );
};

export default Carousel;
