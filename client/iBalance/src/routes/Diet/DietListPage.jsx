// 외부 모듈
import { useCallback, useEffect, useMemo, useState } from "react";
import { Outlet } from "react-router-dom";

// 내부 모듈
import classes from "./DietListPage.module.css";
import Load from "../../modules/Load/Load";
import WeekCard from "./WeekCard";
import DayDiet from "./DayDiet";
import SaveModalPage from "./SaveModalPage";

const DietListPage = () => {
  // 식단 받을 오늘부터 일주일치 날짜리스트 생성
  const WEEKDAY = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];

  const weekList = useMemo(() => {
    return [...new Array(7)].map((_, idx) => {
      const newDate = new Date();
      newDate.setDate(newDate.getDate() + idx);
      return { date: newDate.getDate(), day: WEEKDAY[newDate.getDay()] };
    });
  }, []); // 초기 마운트 시에만 실행

  const arrDayStr = ["일", "월", "화", "수", "목", "금", "토"];

  const weekListKo = useMemo(() => {
    return [...new Array(7)].map((_, idx) => {
      const newDate = new Date();
      newDate.setDate(newDate.getDate() + idx);
      const month = newDate.getMonth() + 1; // JavaScript에서 월은 0부터 시작하므로 +1
      const date = newDate.getDate();
      const dayOfWeek = arrDayStr[newDate.getDay()];
      return `${month}월 ${date}일 (${dayOfWeek})`;
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

  // 기존 7일치 식단 생성
  const initialDietData = weekListKo.map(() => [
    {
      riceMenu: "현미밥",
      mainMenu: "수제 함박 스테이크",
      sideMenu: "어묵볶음",
      soupMenu: "두부 계란탕",
    },
  ]);
  // 식단 상태 변경을 위한 설정
  const [dietData, setDietData] = useState(initialDietData);
  // console.log(dietData);

  //식단 추가시
  const addDietCard = useCallback(
    (dayIndex) => {
      const newDietData = [...dietData];
      newDietData[dayIndex].push({
        riceMenu: "치즈밥",
        mainMenu: "갈치구이",
        sideMenu: "김계란말이",
        soupMenu: "오징어무국",
      });
      setDietData(newDietData);
    },
    [dietData],
  );

  //식단 저장 상태 관리
  const [saveDiet, setSaveDiet] = useState(false);
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
              {weekListKo
                .filter((_, idx) => isClick === 0 || isClick === idx + 1)
                .map((day, idx) => (
                  <div key={idx}>
                    <DayDiet
                      day={day}
                      diets={dietData[idx]}
                      saveDiet={saveDiet}
                      addDietCard={() => addDietCard(idx)}></DayDiet>
                  </div>
                ))}
            </div>
            {saveDiet === false ? (
              <div
                className={classes.saveBtn}
                onClick={() => {
                  setSaveModal(true);
                }}>
                식단 저장
              </div>
            ) : null}
            {saveModal === true && saveDiet === false ? (
              <SaveModalPage
                setSaveModal={setSaveModal}
                setSaveDiet={setSaveDiet}></SaveModalPage>
            ) : null}
          </div>
        ) : null}
      </div>
      <Outlet></Outlet>
    </>
  );
};

export default DietListPage;
