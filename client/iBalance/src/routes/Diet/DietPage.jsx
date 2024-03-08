/* eslint-disable */

// 외부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";

// 내부 모듈
import classes from "./DietPage.module.css";

const DietPage = () => {
  return (
    <>
      <div className={classes.gridSet}>
        <NavbarModule isClick={2}></NavbarModule>
        <div className={classes.dietContentBox}>this is Diet</div>
      </div>
    </>
  );
};

export default DietPage;
