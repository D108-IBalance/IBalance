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
import DietComplete from "./DietComplete";
import { getRecommendedDiet } from "./ServerConnect";
import Load from "../../modules/Load/Load";
import SaveAlert from "./SaveAlert";

const DietPage = () => {
  const childId = useSelector((state) => state.childId);
  const [saveAlert, setSaveAlert] = useState(null);
  const [isCreate, setIsCreate] = useState(false);
  const [bgColor, setBgColor] = useState("#fff");
  const [userDiet, setUserDiet] = useState([]);
  const [loadStep, setLoadStep] = useState(2);
  const [summaryInfo, setSummaryInfo] = useState({});
  const [selectDate, setSelectDate] = useState("");
  const [dietId, setDietId] = useState();
  const [isSave, setIsSave] = useState(false);
  // false : 초기 식단 조회 상태 ( 식단 저장 버튼 O )
  // true : 최종 식단 조회 상태 ( 식단 저장 버튼 X )
  useEffect(() => {
    let timer = null;
    if (saveAlert === true) {
      timer = setTimeout(() => {
        setSaveAlert(false);
      }, 1000);
    }
    return () => {
      clearTimeout(timer);
    };
  }, [saveAlert]);
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
      const res = await getRecommendedDiet(childId);
      setLoadStep(1);
      if (res.data.data[0].dietList.length === 0) {
        setIsSave(false);
        setUserDiet([]);
      } else {
        setIsSave(true);
        setUserDiet(res.data.data);
      }
    };
    getDietData();
  }, [childId]);
  return (
    <>
      {saveAlert ? <SaveAlert /> : null}
      <Load step={loadStep} bgColor={bgColor} isCreate={isCreate} />
      <div className={classes.gridSet}>
        <Header />
        <NavbarModule isClick={2} />
        <div className={classes.dietContentBox}>
          {Object.keys(summaryInfo).length === 0 ? (
            <EmptyOrList
              setDietId={setDietId}
              weekListKo={weekListKo}
              setSelectDate={setSelectDate}
              userDiet={userDiet}
              setUserDiet={setUserDiet}
              setSummaryInfo={setSummaryInfo}
              isSave={isSave}
              setIsSave={setIsSave}
              setLoadStep={setLoadStep}
              setBgColor={setBgColor}
              setIsCreate={setIsCreate}
              setSaveAlert={setSaveAlert}
            />
          ) : (
            <DietSummary
              dietId={dietId}
              weekListKo={weekListKo}
              selectDate={selectDate}
              setSelectDate={setSelectDate}
              setSummaryInfo={setSummaryInfo}
              summaryInfo={summaryInfo}
              isSave={isSave}
              setUserDiet={setUserDiet}
            />
          )}
        </div>
      </div>
    </>
  );
};

const ListComponent = (props) => {
  const {
    userDiet,
    setDietId,
    weekListKo,
    setSelectDate,
    setUserDiet,
    setSummaryInfo,
    isSave,
    setIsSave,
    setLoadStep,
    setBgColor,
    setSaveAlert,
  } = props;
  return (
    <>
      {isSave ? (
        <DietComplete
          userDiet={userDiet}
          setSummaryInfo={setSummaryInfo}
          setDietId={setDietId}
          setSelectDate={setSelectDate}
        />
      ) : (
        <DietListPage
          setDietId={setDietId}
          weekListKo={weekListKo}
          setSelectDate={setSelectDate}
          userDiet={userDiet}
          setUserDiet={setUserDiet}
          setSummaryInfo={setSummaryInfo}
          isSave={isSave}
          setIsSave={setIsSave}
          setBgColor={setBgColor}
          setLoadStep={setLoadStep}
          setSaveAlert={setSaveAlert}></DietListPage>
      )}
    </>
  );
};

const EmptyOrList = (props) => {
  const {
    userDiet,
    setDietId,
    weekListKo,
    setSelectDate,
    setUserDiet,
    setSummaryInfo,
    isSave,
    setIsSave,
    setLoadStep,
    setBgColor,
    setIsCreate,
    setSaveAlert,
  } = props;
  return (
    <>
      {userDiet.length === 0 ? (
        <EmptyDiet
          setUserDiet={setUserDiet}
          isSave={isSave}
          setLoadStep={setLoadStep}
          setIsCreate={setIsCreate}></EmptyDiet>
      ) : (
        <ListComponent
          setDietId={setDietId}
          weekListKo={weekListKo}
          setSelectDate={setSelectDate}
          userDiet={userDiet}
          setUserDiet={setUserDiet}
          setSummaryInfo={setSummaryInfo}
          isSave={isSave}
          setIsSave={setIsSave}
          setLoadStep={setLoadStep}
          setBgColor={setBgColor}
          setSaveAlert={setSaveAlert}
        />
      )}
    </>
  );
};

export default DietPage;
