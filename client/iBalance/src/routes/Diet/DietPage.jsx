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

  return (
    <div>
      <Header></Header>
      {emptyDiet === true ? (
        <div className={classes.gridSet}>
          <NavbarModule isClick={2}></NavbarModule>
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
