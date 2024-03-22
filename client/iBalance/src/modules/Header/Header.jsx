// 내부 모듈
import classes from "./Header.module.css";
import OffcanvasPage from "./OffcanvasPage";

const Header = () => {
  return (
    <>
      <div className={classes.headerBox}>
        <div className={classes.tempIcon}></div>
        <div className={classes.homeLogo}></div>
        <div className={classes.toggleBtn}>
          <OffcanvasPage placement={"end"} name={"end"}></OffcanvasPage>
        </div>
      </div>
    </>
  );
};

export default Header;
