// 외부 모듈
import { useEffect, useState } from "react";
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
                userDiet={userDiet}
                setUserDiet={setUserDiet}
                setSummaryInfo={setSummaryInfo}></DietListPage>
            )
          ) : (
            <DietSummary
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
