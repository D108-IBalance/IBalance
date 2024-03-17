/* eslint-disable */

// 외부 모듈
import { useState } from "react";
// 내부 모듈
import classes from "./DiaryPage.module.css";
import NavbarModule from "../../modules/Navbar/NavbarModule";
import Calendar from "./Calendar";
import DiaryCards from "./DiaryCards";
const DiaryPage = () => {
  const [selectedDate, setSelectedDate] = useState({});
  return (
    <>
      <div className={classes.gridSet}>
        <NavbarModule isClick={3}></NavbarModule>
        <div className={classes.diaryContentBox} style={{ width: "100%" }}>
          <Calendar setSelectedDate={setSelectedDate}></Calendar>
          <div className={classes.next}>
            <DiaryCards selectedDate={"2024 January 04"} />
          </div>
        </div>
      </div>
    </>
  );
};

export default DiaryPage;
