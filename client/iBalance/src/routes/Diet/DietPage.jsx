/* eslint-disable */

// 외부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";

// 내부 모듈
import classes from "./DietPage.module.css";
import EmptyDiet from "./EmptyDiet";
import Header from "../../modules/Header/Header";
import { useEffect, useState } from "react";

const DietPage = (props) => {
  let [emptyDiet, setEmptyDiet] = useState(true);
  let { displaySize } = props;
  useEffect(() => {
    console.log(displaySize);
  }, [displaySize]);
  return (
    <div>
      <Header></Header>
      <div className={classes.gridSet}>
        <NavbarModule isClick={2}></NavbarModule>
        <div className={classes.dietContentBox}>
          {emptyDiet === true ? <EmptyDiet></EmptyDiet> : null}
        </div>
      </div>
    </div>
  );
};

export default DietPage;
