/* eslint-disable */

// 외부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";

// 내부 모듈
import classes from "./DietPage.module.css";
import EmptyDiet from "./EmptyDiet";
import DietListPage from "./DietListPage";
import Header from "../../modules/Header/Header";
import { useEffect, useState } from "react";

const DietPage = (props) => {
  let [emptyDiet, setEmptyDiet] = useState(true);
  let { displaySize } = props;
  useEffect(() => {
    // console.log(displaySize);
  }, [displaySize]);
  return (
    <div>
      <Header></Header>
      <NavbarModule isClick={2}></NavbarModule>
      {emptyDiet === true ? (
        <div className={classes.gridSet}>
          <div className={classes.dietContentBox}>
            <EmptyDiet setEmptyDiet={setEmptyDiet}></EmptyDiet>
          </div>
        </div>
      ) : (
        <DietListPage></DietListPage>
      )}
    </div>
  );
};

export default DietPage;
