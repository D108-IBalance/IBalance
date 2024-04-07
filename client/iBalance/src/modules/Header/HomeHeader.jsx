// 내부 모듈
import classes from "./HomeHeader.module.css";
import OffcanvasPage from "./OffcanvasPage";

const HomeHeader = () => {
  return (
    <>
      <div className={classes.headerBox}>
        <div className={classes.tempIcon}></div>
        <div className={classes.homeLogo}></div>
        <OffcanvasPage placement={"end"} name={"end"}></OffcanvasPage>
      </div>
    </>
  );
};

export default HomeHeader;
