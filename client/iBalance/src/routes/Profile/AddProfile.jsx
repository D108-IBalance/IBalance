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
  const [profileData, setProfileData] = useState({});
  const [step, setStep] = useState(0);
  const sendProps = {
    setStep,
    setProfileData,
    profileData,
  };
  const component = [
    <ChildName {...sendProps} />,
    <ChildBirth {...sendProps} />,
    <ChildGender {...sendProps} />,
    <ChildHeight {...sendProps} />,
    <ChildWeight {...sendProps} />,
    <ChildAllergy {...sendProps} />,
  ];

  return <div className={classes.container}>{component[step]}</div>;
};

export default AddProfile;
