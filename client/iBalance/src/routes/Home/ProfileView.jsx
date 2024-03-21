import classes from "./ProfileView.module.css";
import profileImgfile from "../../assets/auth/img/default_profile1.png";
import HomeHeader from "../../modules/Header/HomeHeader";

const ProfileView = (props) => {
  const { userProfile } = props;
  let calcBmi = (userProfile.weight / Math.pow(userProfile.height, 2)) * 10000;
  const BMI = calcBmi.toString().slice(0, 5);
  return (
    <div className={classes.profileBox}>
      {/* <div className={classes.headerBox}>
        <div className={classes.tempIcon}></div>
        <div className={classes.homeLogo}></div>
        <div className={classes.toggleBtn}></div>
      </div> */}
      <HomeHeader></HomeHeader>
      <div className={classes.profileContentBox}>
        <div className={classes.profileImg}>
          <img src={profileImgfile} />
        </div>
        <div className={classes.profileContent}>
          <p className={classes.profileName}>
            {userProfile.name}
            <span>(만 11세)</span>
          </p>
          <div className={classes.growthBack}>
            <div className={classes.growthLine}></div>
            <div className={classes.profiletextBox}>
              <div>
                <p className={classes.profiletext}>신장</p>
                <p className={classes.profiledata}>{userProfile.height}cm</p>
              </div>
              <div>
                <p className={classes.profiletext}>몸무게</p>
                <p className={classes.profiledata}>{userProfile.weight}kg</p>
              </div>
              <div>
                <p className={classes.profiletext}>BMI</p>
                <p className={classes.profiledata}>{BMI}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfileView;
