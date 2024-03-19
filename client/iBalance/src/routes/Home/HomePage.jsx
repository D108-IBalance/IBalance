/* eslint-disable */

// 외부 모듈

// 내부 모듈

import NavbarModule from "../../modules/Navbar/NavbarModule";
import classes from "./HomePage.module.css";
import ProfileView from "./ProfileView";
import TodayDiet from "./TodayDiet";
import { useEffect, useState } from "react";
import GrowthCard from "./GrowthCard";

const HomePage = () => {
  return (
    <>
      <div className={classes.gridSet}>
        <NavbarModule isClick={0}></NavbarModule>
        <div className={classes.container} style={{ width: "100%" }}>
          <ProfileView />
          <div className={classes.homeContentBack}>
            <TodayDiet></TodayDiet>
            <GrowthCard />
          </div>
        </div>
      </div>
    </>
  );
};

export default HomePage;
