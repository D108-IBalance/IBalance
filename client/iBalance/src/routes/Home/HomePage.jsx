/* eslint-disable */

// 외부 모듈
import axios from "axios";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";

// 내부 모듈

import NavbarModule from "../../modules/Navbar/NavbarModule";
import classes from "./HomePage.module.css";
import ProfileView from "./ProfileView";
import TodayDiet from "./TodayDiet";
import GrowthCard from "./GrowthCard";
import { getUserChart, getUserInfo } from "./ServerConnect";

const HomePage = () => {
  const childId = useSelector((state) => {
    return state.childId;
  });
  const token = useSelector((state) => {
    return state.token;
  });
  const [userProfile, setUserProfile] = useState({});
  const [userChart, setUserChart] = useState({});
  const [userDiet, setUserDiet] = useState({});
  useEffect(() => {
    const getHomeData = async () => {
      const res = await Promise.all([
        getUserChart(0, childId),
        getUserInfo(childId),
      ]);
      setUserChart(res[0].data.data);
      setUserProfile(res[1].data.data.childDetailResponse);
      setUserDiet(res[1].data.data.dietList);
    };
    getHomeData();
  }, []);
  return (
    <>
      <div className={classes.gridSet}>
        <NavbarModule isClick={0}></NavbarModule>
        <div className={classes.container} style={{ width: "100%" }}>
          <ProfileView userProfile={userProfile} />
          <div className={classes.homeContentBack}>
            <TodayDiet userDiet={userDiet}></TodayDiet>
            <GrowthCard userChart={userChart} />
          </div>
        </div>
      </div>
    </>
  );
};

export default HomePage;
