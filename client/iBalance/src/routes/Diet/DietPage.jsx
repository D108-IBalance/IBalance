// 외부 모듈
import { useEffect, useMemo, useState } from "react";
import { useSelector } from "react-redux";

// 내부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";
import classes from "./DietPage.module.css";
import EmptyDiet from "./EmptyDiet";
import DietListPage from "./DietListPage";
import Header from "../../modules/Header/Header";
import DietSummary from "../DietDetail/DietSummary";
import { getInitDiet, getRecommendedDiet } from "./ServerConnect";

const DietPage = () => {
  const childId = useSelector((state) => state.childId);
  const [userDiet, setUserDiet] = useState([]);
  const [summaryInfo, setSummaryInfo] = useState({});
  const [selectDate, setSelectDate] = useState("");

  const weekListKo = useMemo(() => {
    const arrDayStr = ["일", "월", "화", "수", "목", "금", "토"];
    return [...new Array(7)].map((_, idx) => {
      const newDate = new Date();
      newDate.setDate(newDate.getDate() + idx);
      const month = newDate.getMonth() + 1; // JavaScript에서 월은 0부터 시작하므로 +1
      const date = newDate.getDate();
      const dayOfWeek = arrDayStr[newDate.getDay()];
      return `${month}월 ${date}일 (${dayOfWeek})`;
    });
  }, []); // 초기 마운트 시에만 실행

  useEffect(() => {
    const getDietData = async () => {
      const res = await Promise.all([
        getInitDiet(childId),
        getRecommendedDiet(childId),
      ]);
      setUserDiet(res[1].data.data);
    };
    // getDietData();
  }, [childId]);

  return (
    <>
      <div className={classes.gridSet}>
        <Header />
        <NavbarModule isClick={2} />
        <div className={classes.dietContentBox}>
          {Object.keys(summaryInfo).length === 0 ? (
            userDiet.length === 0 ? (
              <EmptyDiet setUserDiet={setUserDiet}></EmptyDiet>
            ) : (
              <DietListPage
                weekListKo={weekListKo}
                setSelectDate={setSelectDate}
                userDiet={userDiet}
                setUserDiet={setUserDiet}
                setSummaryInfo={setSummaryInfo}></DietListPage>
            )
          ) : (
            <DietSummary
              weekListKo={weekListKo}
              selectDate={selectDate}
              setSelectDate={setSelectDate}
              setSummaryInfo={setSummaryInfo}
              summaryInfo={summaryInfo}
            />
          )}
        </div>
      </div>
    </>
  );
};

export default DietPage;
