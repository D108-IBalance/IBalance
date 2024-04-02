// 내부 모듈
import classes from "./RecipeSummary.module.css";
import Carousel from "../../modules/Carousel/Carousel";

const RecipeSummary = (props) => {
  const { setIsOpen, setIsMore } = props;
  return (
    <>
      <div className={classes.container}>
        <div className={classes.cardTitleBox}>
          <p className={classes.cardTitle}>
            <span>당근</span> 레시피
          </p>
          <p
            className={classes.moreBtn}
            onClick={() => {
              setIsMore(true);
            }}>
            더 보기
          </p>
        </div>
        <div className={classes.cardContent}>
          <div className={classes.cardBox}>
            <Carousel setIsOpen={setIsOpen}></Carousel>
          </div>
        </div>
      </div>
    </>
  );
};
export default RecipeSummary;
