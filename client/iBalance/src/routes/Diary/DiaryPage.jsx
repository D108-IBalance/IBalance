/* eslint-disable */

// 외부 모듈
import { useState } from "react";
// 내부 모듈
import classes from "./DiaryPage.module.css";
import NavbarModule from "../../modules/Navbar/NavbarModule";
import Calendar from "./Calendar";
import DiaryCards from "./DiaryCards";
import DiaryWrite from "./DiaryWrite";
import Header from "../../modules/Header/Header";
import notSelectIcon from "../../assets/diary/img/notselect.svg";

const DiaryPage = () => {
  const [selectedDate, setSelectedDate] = useState("");
  const [pageStep, setPageStep] = useState(0);
  const cardComponent =
    pageStep === 0 ? (
      <DiaryCards
        selectedDate={selectedDate}
        pageStep={pageStep}
        setPageStep={setPageStep}
      />
    ) : (
      <DiaryWrite selectedDate={selectedDate} />
    );
  return (
    <>
      <div className={classes.gridSet}>
        <Header />

        <NavbarModule isClick={3}></NavbarModule>
        <div className={classes.diaryContentBox}>
          <Calendar
            setSelectedDate={setSelectedDate}
            setPageStep={setPageStep}></Calendar>
          <div className={classes.next}>
            {selectedDate === "" ? (
              <div className={classes.notSelectBox}>
                <img src={notSelectIcon} alt="선택되지않은아이콘" />
                <p className={classes.notSelectText}>
                  일기를 확인할 날짜를 선택해주세요
                </p>
              </div>
            ) : (
              cardComponent
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default DiaryPage;
