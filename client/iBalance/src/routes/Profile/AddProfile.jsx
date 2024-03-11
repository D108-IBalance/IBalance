/* eslint-disable */

// 내부 모듈
import { useEffect, useState } from "react";
import classes from "./AddProfile.module.css";
import ChildName from "./AddChild/ChildName";
import ChildBirth from "./AddChild/ChildBirth";
import ChildGender from "./AddChild/ChildGender";
import ChildHeight from "./AddChild/ChildHeight";
import ChildWeight from "./AddChild/ChildWeight";
import ChildAllergy from "./AddChild/ChildAllergy";

const AddProfile = () => {
  let [profileData, setProfileData] = useState({});
  let [step, setStep] = useState(0);
  let component;
  switch (step) {
    case 0:
      component = (
        <ChildName
          setStep={setStep}
          setProfileData={setProfileData}
          profileData={profileData}></ChildName>
      );
      break;
    case 1:
      component = (
        <ChildBirth
          setStep={setStep}
          setProfileData={setProfileData}
          profileData={profileData}></ChildBirth>
      );
      break;
    case 2:
      component = (
        <ChildGender
          setStep={setStep}
          setProfileData={setProfileData}
          profileData={profileData}></ChildGender>
      );
      break;
    case 3:
      component = (
        <ChildHeight
          setStep={setStep}
          setProfileData={setProfileData}
          profileData={profileData}></ChildHeight>
      );
      break;
    case 4:
      component = (
        <ChildWeight
          setStep={setStep}
          setProfileData={setProfileData}
          profileData={profileData}></ChildWeight>
      );
      break;
    case 5:
      component = (
        <ChildAllergy
          setStep={setStep}
          setProfileData={setProfileData}
          profileData={profileData}></ChildAllergy>
      );
      break;
    case 6:
      console.log(profileData);
      component = (
        <div>
          <h1>일단 여기</h1>
        </div>
      );
  }

  return <div className={classes.container}>{component}</div>;
};

export default AddProfile;
