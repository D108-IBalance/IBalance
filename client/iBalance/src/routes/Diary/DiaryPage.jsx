/* eslint-disable */

// 외부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";

// 내부 모듈
import classes from "./DiaryPage.module.css";
const DiaryPage = () => {
  return (
    <>
      <div className={classes.gridSet}>
        <NavbarModule isClick={3}></NavbarModule>
        <div className={classes.diaryContentBox}>this is Diary</div>
      </div>
    </>
  );
};

export default DiaryPage;
