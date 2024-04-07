// 외부 모듈
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";

// 내부 모듈

import NavbarModule from "../../modules/Navbar/NavbarModule";
import classes from "./HomePage.module.css";
import ProfileView from "./ProfileView";
import TodayDiet from "./TodayDiet";
import GrowthCard from "./GrowthCard";
import Load from "../../modules/Load/Load";
import { getUserInfo, getWeightChart, getHeightChart } from "./ServerConnect";

const HomePage = () => {
  const [userProfile, setUserProfile] = useState({});
  const [weightChartInfo, setWeightChartInfo] = useState({});
  const [heightChartInfo, setHeightChartInfo] = useState({});
  const [userDiet, setUserDiet] = useState([]);
  const [loadStep, setLoadStep] = useState(2);
  const childId = useSelector((state) => state.childId);
  useEffect(() => {
    const getHomeData = async () => {
      const res = await Promise.all([
        getHeightChart(0, childId),
        getWeightChart(0, childId),
        getUserInfo(childId),
      ]);
      setHeightChartInfo(res[0].data.data);
      setWeightChartInfo(res[1].data.data);
      setUserProfile(res[2].data.data.childMainResponse);
      setUserDiet(res[2].data.data.dietList);
      setLoadStep(1);
    };
    getHomeData();
  }, [childId]);
  return (
    <>
      <div className={classes.gridSet}>
        <NavbarModule isClick={0}></NavbarModule>
        <div className={classes.container} style={{ width: "100%" }}>
          <Load step={loadStep} />
          <ProfileView userProfile={userProfile} />
          <div className={classes.homeContentBack}>
            <TodayDiet userDiet={userDiet}></TodayDiet>
            <GrowthCard
              heightChartInfo={heightChartInfo}
              weightChartInfo={weightChartInfo}
            />
          </div>
        </div>
      </div>
    </>
  );
};

export default HomePage;
