// 외부 모듈
import { useEffect, useState } from "react";

// 내부 모듈
import classes from "./ProfileView.module.css";
import HomeHeader from "../../modules/Header/HomeHeader";

const ProfileView = (props) => {
  const { userProfile } = props;
  const [currentAge, setCurrentAge] = useState("");
  const [BMI, setBMI] = useState("");

  useEffect(() => {
    const getCurrentAge = () => {
      const STANDARD_MONTH = 36;
      const USER_BIRTH = userProfile.birthDate;
      const birthDate = new Date(USER_BIRTH);
      const currentDate = new Date();
      let month = (currentDate.getFullYear() - birthDate.getFullYear()) * 12;
      month += currentDate.getMonth() - birthDate.getMonth();
      month = currentDate.getDate() < birthDate.getDate() ? month - 1 : month;
      if (month > STANDARD_MONTH) return `만 ${(month / 12).toFixed()}세`;
      else return `${month}개월`;
    };
    if (userProfile) {
      setCurrentAge(getCurrentAge());
      let calcBmi =
        (userProfile.weight / Math.pow(userProfile.height, 2)) * 10000;
      setBMI(calcBmi.toString().slice(0, 5));
    }
  }, [userProfile]);
  return (
    <>
      <main className={classes.profileBox}>
        <HomeHeader></HomeHeader>
        <div className={classes.profileContentBox}>
          <div className={classes.profileImg}>
            <img src={userProfile?.imageUrl} />
          </div>
          <section className={classes.profileContent}>
            <p className={classes.profileName}>
              {userProfile ? userProfile.name : ""}
              <span>({currentAge})</span>
            </p>
            <div className={classes.growthBack}>
              <div className={classes.growthLine}></div>
              <div className={classes.profiletextBox}>
                <div>
                  <p className={classes.profiletext}>신장</p>
                  <p className={classes.profiledata}>
                    {userProfile ? `${userProfile.height}cm` : ""}
                  </p>
                </div>
                <div>
                  <p className={classes.profiletext}>몸무게</p>
                  <p className={classes.profiledata}>
                    {userProfile ? `${userProfile.weight}kg` : ""}
                  </p>
                </div>
                <div>
                  <p className={classes.profiletext}>BMI</p>
                  <p className={classes.profiledata}>{BMI}</p>
                </div>
              </div>
            </div>
          </section>
        </div>
      </main>
    </>
  );
};

export default ProfileView;
