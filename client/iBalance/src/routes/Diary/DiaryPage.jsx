/* eslint-disable */

// 외부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";
import Calendar from "./Calendar";

// 내부 모듈
import classes from "./DiaryPage.module.css";
const DiaryPage = () => {
  return (
    <>
      <div className={classes.gridSet}>
        <NavbarModule isClick={3}></NavbarModule>
        <div className={classes.diaryContentBox}>
          <Calendar></Calendar>
          <div className={classes.next}></div>
        </div>
      </div>
    </>
  );
};

export default DiaryPage;
