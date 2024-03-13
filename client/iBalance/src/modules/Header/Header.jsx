import classes from "./Header.module.css";

const Header = () => {
  return (
    <>
      <div className={classes.headerBox}>
        <div className={classes.tempIcon}></div>
        <div className={classes.homeLogo}></div>
        <div className={classes.toggleBtn}></div>
      </div>
    </>
  );
};

export default Header;
