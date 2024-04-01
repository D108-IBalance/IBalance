// 외부 모듈
import { useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";

// 내부 모듈
import classes from "./Carousel.module.css";
import sample1 from "../../assets/recipe/sample1.png";
import sample2 from "../../assets/recipe/sample2.png";
import sample3 from "../../assets/recipe/sample3.png";
import sample4 from "../../assets/recipe/sample4.png";
import sample5 from "../../assets/recipe/sample5.png";

const Carousel = (props) => {
  const { setIsOpen } = props;
  const naviate = useNavigate();
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
  const onTest = () => {
    setIsOpen(true);
    naviate("/recipe/item");
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
          <div
            className={classes.item}
            onMouseUp={() => {
              onTest();
            }}>
            <div className={classes.recipeImgBox}>
              <img src={sample1} alt="" className={classes.recipeImg} />
              <div className={classes.imgTextBox}>
                <div className={classes.recipeKcal}>Total : 192Kcal</div>
                <div className={classes.recipeName}>당근전</div>
              </div>
            </div>
          </div>
          <div className={classes.item}>
            <div className={classes.recipeImgBox}>
              <img src={sample2} alt="" className={classes.recipeImg} />
            </div>
            <div className={classes.imgText}></div>
          </div>
          <div className={classes.item}>
            <div className={classes.recipeImgBox}>
              <img src={sample3} alt="" className={classes.recipeImg} />
            </div>
            <div className={classes.imgText}></div>
          </div>
          <div className={classes.item}>
            <div className={classes.recipeImgBox}>
              <img src={sample4} alt="" className={classes.recipeImg} />
            </div>
            <div className={classes.imgText}></div>
          </div>
          <div className={classes.item}>
            <div className={classes.recipeImgBox}>
              <img src={sample5} alt="" className={classes.recipeImg} />
            </div>
            <div className={classes.imgText}></div>
          </div>
        </div>
      </div>
      <div></div>
    </div>
  );
};

export default Carousel;
