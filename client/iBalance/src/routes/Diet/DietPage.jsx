// 외부 모듈
import { useState } from "react";

// 내부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";
import classes from "./DietPage.module.css";
import EmptyDiet from "./EmptyDiet";
import DietListPage from "./DietListPage";
import Header from "../../modules/Header/Header";

const DietPage = () => {
  let [isEmptyDiet, setIsEmptyDiet] = useState(true);

  return (
    <>
      <div className={classes.gridSet}>
        <Header />
        <NavbarModule isClick={2} />
        <div className={classes.dietContentBox}>
          {isEmptyDiet ? (
            <EmptyDiet setIsEmptyDiet={setIsEmptyDiet}></EmptyDiet>
          ) : (
            <DietListPage></DietListPage>
          )}
        </div>
      </div>
    </>
  );
};

export default DietPage;
