// 외부 모듈
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";

// 내부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";
import classes from "./DietPage.module.css";
import EmptyDiet from "./EmptyDiet";
import DietListPage from "./DietListPage";
import Header from "../../modules/Header/Header";
import { getInitDiet } from "./ServerConnect";

const DietPage = () => {
  const [dietData, setDietData] = useState([]);
  const childId = useSelector((state) => state.childId);

  const [isEmptyDiet, setIsEmptyDiet] = useState(true);

  useEffect(() => {
    const getDietData = async () => {
      const res = await Promise.all([getInitDiet(childId)]);
      setDietData(res[0].data.data);
      console.log(res[0].data.data);
    };
    getDietData();
  }, [childId]);

  return (
    <>
      <div className={classes.gridSet}>
        <Header />
        <NavbarModule isClick={2} />
        <div className={classes.dietContentBox}>
          {isEmptyDiet ? (
            <EmptyDiet setIsEmptyDiet={setIsEmptyDiet}></EmptyDiet>
          ) : (
            <DietListPage dietData={dietData}></DietListPage>
          )}
        </div>
      </div>
    </>
  );
};

export default DietPage;
