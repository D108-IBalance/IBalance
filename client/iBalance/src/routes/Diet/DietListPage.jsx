// 외부 모듈
import { useEffect, useMemo, useState } from "react";
import { Outlet } from "react-router-dom";

// 내부 모듈
import classes from "./DietListPage.module.css";
import Load from "../../modules/Load/Load";
import WeekCard from "./WeekCard";
import DayDiet from "./DayDiet";
import SaveModalPage from "./SaveModalPage";

const DietListPage = (props) => {
  const {
    userDiet,
    setUserDiet,
    setSummaryInfo,
    weekListKo,
    setSelectDate,
    isSave,
    setIsSave,
    setDietId,
    setBgColor,
    setLoadStep,
    setSaveAlert,
  } = props;
  // 식단 받을 오늘부터 일주일치 날짜리스트 생성

  const weekList = useMemo(() => {
    const WEEKDAY = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];
    return [...new Array(7)].map((_, idx) => {
      const newDate = new Date();
      newDate.setDate(newDate.getDate() + idx);
      return { date: newDate.getDate(), day: WEEKDAY[newDate.getDay()] };
    });
  }, []); // 초기 마운트 시에만 실행

  // 식단 페이지를 보여주기 전 로딩페이지 (2초간 유지)
  const [step, setStep] = useState(2);

  useEffect(() => {
    const timeout = setTimeout(() => {
      setStep(1);
    }, 2000);

    return () => {
      // clear 해줌
      clearTimeout(timeout);
    };
  }, []);
  // 카드 클릭 시 카드 색 변환
  const [isClick, setIsClick] = useState(0);

  const [dietData, setDietData] = useState([]);
  useEffect(() => {
    if (userDiet.length > 0) {
      setDietData(userDiet);
    }
  }, [userDiet]); // userDiet이 변경될 때마다 실행됩니다.

  //식단 저장 상태 관리
  const [saveModal, setSaveModal] = useState(false);

  return (
    <>
      <div className={classes.container}>
        <Load step={step}></Load>
        {step === 1 ? (
          <div>
            <WeekCard
              weekList={weekList}
              isClick={isClick}
              setIsClick={setIsClick}></WeekCard>

            <div className={classes.DietListBack}>
              {dietData.length > 0
                ? dietData.map((menu, idx) => {
                    return isClick === 0 ||
                      Number.parseInt(isClick) === Number.parseInt(idx) + 1 ? (
                      <div key={idx}>
                        <DayDiet
                          setSelectDate={setSelectDate}
                          setDietId={setDietId}
                          day={weekListKo[idx]}
                          diets={menu}
                          setUserDiet={setUserDiet}
                          dayIdx={idx}
                          setSummaryInfo={setSummaryInfo}
                          setBgColor={setBgColor}
                          setLoadStep={setLoadStep}
                          isSave={isSave}></DayDiet>
                      </div>
                    ) : null;
                  })
                : null}
            </div>
            {isSave === false ? (
              <div
                className={classes.saveBtn}
                onClick={() => {
                  setSaveModal(true);
                }}>
                식단 저장
              </div>
            ) : null}
            {saveModal === true && isSave === false ? (
              <SaveModalPage
                setSaveModal={setSaveModal}
                setIsSave={setIsSave}
                setUserDiet={setUserDiet}
                setSaveAlert={setSaveAlert}></SaveModalPage>
            ) : null}
          </div>
        ) : null}
      </div>
      <Outlet></Outlet>
    </>
  );
};

export default DietListPage;
