// 외부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";
import { useState } from "react";

// 내부 모듈
import classes from "./DietPage.module.css";
import EmptyDiet from "./EmptyDiet";
import DietListPage from "./DietListPage";
import Header from "../../modules/Header/Header";

const DietPage = () => {
  let [emptyDiet, setEmptyDiet] = useState(true);

  return (
    <>
      <div className={classes.gridSet}>
        <Header></Header>
        <NavbarModule isClick={2}></NavbarModule>
        <div className={classes.dietContentBox}>
          {emptyDiet === true ? (
            <EmptyDiet setEmptyDiet={setEmptyDiet}></EmptyDiet>
          ) : (
            <DietListPage></DietListPage>
          )}
        </div>
      </div>
    </>
  );
};

export default DietPage;
