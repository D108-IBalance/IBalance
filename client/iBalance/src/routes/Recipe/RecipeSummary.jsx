// 외부 모듈
import { useNavigate } from "react-router-dom";

// 내부 모듈
import classes from "./RecipeSummary.module.css";
import Carousel from "../../modules/Carousel/Carousel";

const RecipeSummary = (props) => {
  const navigate = useNavigate();
  const { setIsOpen } = props;
  return (
    <>
      <div className={classes.container}>
        <div className={classes.cardTitleBox}>
          <p className={classes.cardTitle}>
            <span>당근</span> 레시피
          </p>
          <p
            onClick={() => {
              navigate("/recipe/more");
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
