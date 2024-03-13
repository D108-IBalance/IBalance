/* eslint-disable */

// 외부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";

// 내부 모듈
import classes from "./HomePage.module.css";
import profileImgfile from "../../assets/auth/img/default_profile1.png";
import { useEffect, useState } from "react";

const HomePage = (props) => {
  let { displaySize } = props;

  return (
    <>
      <div className={classes.gridSet}>
        <NavbarModule isClick={0}></NavbarModule>
        <div style={{ width: "75%" }}>
          <ProfileView />
          <div className={classes.homeContentBack}>
            <TodayDiet />
            <TodayDiet />
            <TodayDiet />
            {/* <div>우라아이 성장곡선</div> */}
            {/* <div className={classes.growthBox}></div> */}
          </div>
        </div>
      </div>
    </>
  );
};

const ProfileView = () => {
  return (
    <div className={classes.profileBox}>
      <div className={classes.headerBox}>
        <div className={classes.tempIcon}></div>
        <div className={classes.homeLogo}></div>
        <div className={classes.toggleBtn}></div>
      </div>
      <div className={classes.profileContentBox}>
        <div className={classes.profileImg}>
          <img src={profileImgfile} alt="" />
        </div>
        <div className={classes.profileContent}>
          <p className={classes.profileName}>
            박서준<span>(만 11세)</span>
          </p>
          <div className={classes.growthBack}>
            <div className={classes.growthLine}></div>
            <div className={classes.profiletextBox}>
              <div>
                <p className={classes.profiletext}>신장</p>
                <p className={classes.profiledata}>135cm</p>
                <div className={classes.normal}></div>
              </div>
              <div>
                <p className={classes.profiletext}>몸무게</p>
                <p className={classes.profiledata}>30kg</p>
                <div className={classes.normal}></div>
              </div>
              <div>
                <p className={classes.profiletext}>BMI</p>
                <p className={classes.profiledata}>20.71</p>
                <div className={classes.normal}></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const TodayDiet = () => {
  return (
    <div className={classes.dietBox}>
      <div className={classes.dietTitle}>
        <div className={classes.dietIcon}></div>
        <span>오늘의 식단표</span>
      </div>
      <div className={classes.todayDietBox}>
        <div className={classes.emptyIcon}></div>
        <p>오늘의 식단을 추천받아보세요.</p>
      </div>
    </div>
  );
};

export default HomePage;
