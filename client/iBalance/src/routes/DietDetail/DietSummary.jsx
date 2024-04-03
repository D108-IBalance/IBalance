// 외부 모듈
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";

// 내부 모듈
import classes from "./DietSummary.module.css";
import DietDetail from "./DietDetail";
import {
  getInitDietDetail,
  changeMenuOfTempDiet,
  getDietDetail,
} from "../Diet/ServerConnect";

const DietSummary = (props) => {
  const {
    setSummaryInfo,
    summaryInfo,
    selectDate,
    isSave,
    setUserDiet,
    dietId,
    setLoadStep,
    setBgColor,
  } = props;
  const [isOpen, setIsOpen] = useState(false);
  const childId = useSelector((state) => state.childId);
  const [dietSummary, setDietSummary] = useState(null);
  const [dietDetail, setDietDetail] = useState(null);

  useEffect(() => {
    const getDietDetailData = async () => {
      const res = await getInitDietDetail(
        childId,
        summaryInfo.dietDay,
        summaryInfo.sequence,
      );
      // const res = dummyDetail.data;
      setDietSummary(res.data.data);
    };
    const getDetailData = async () => {
      const res = await getDietDetail(dietId);
      setDietSummary(res.data.data);
    };
    if (Object.keys(summaryInfo).length > 0 && isSave === false) {
      getDietDetailData();
    } else if (dietId && isSave === true) {
      getDetailData();
    }
  }, [summaryInfo, isSave, dietId]);

  const refreshMenu = async (prevMenuId) => {
    setBgColor("rgba(0,0,0,0.7)");
    setLoadStep(2);
    try {
      const res = await changeMenuOfTempDiet(
        childId,
        summaryInfo.dietDay,
        summaryInfo.sequence,
        prevMenuId,
      );
      if (res.data.status === 200) {
        const nextDietSummary = dietSummary.map((menu) => {
          if (menu.menuId == prevMenuId) return res.data.data;
          return menu;
        });
        setDietSummary(nextDietSummary);
        setUserDiet((prev) => {
          let prevTemp = JSON.parse(JSON.stringify(prev));
          prevTemp[summaryInfo.dietDay].menuList[summaryInfo.sequence] =
            nextDietSummary;

          return prevTemp;
        });
      }
    } finally {
      setLoadStep(1);
      setBgColor("#fff");
    }
  };

  return (
    <div className={classes.gridSet}>
      <div className={classes.container}>
        <div className={classes.leftBox}>
          <div className={classes.titleBox}>
            <div
              className={classes.backIcon}
              onClick={() => {
                setSummaryInfo({});
              }}></div>
            <div className={classes.titleTextBox}>
              <div className={classes.titleIcon}></div>
              <div className={classes.titleText}>{selectDate}</div>
            </div>
            <div className={classes.tempIcon}></div>
          </div>
          <div className={classes.contentBox}>
            {dietSummary &&
              dietSummary.map((menu, idx) => {
                return (
                  <div
                    key={idx}
                    className={classes.card}
                    style={{ zIndex: idx }}
                    onClick={(e) => {
                      if (e.currentTarget !== e.target) return;
                      setIsOpen(true);
                      setDietDetail(menu);
                      // setSelectDate("");
                    }}>
                    {!isSave ? (
                      <div
                        className={classes.settingIcon}
                        onClick={() => {
                          refreshMenu(menu.menuId);
                        }}
                      />
                    ) : null}
                    <div
                      className={classes.leftCard}
                      onClick={() => {
                        setIsOpen(true);
                        setDietDetail(menu);
                      }}>
                      <div className={classes.cardImgBox}>
                        <div
                          style={{ backgroundImage: `url(${menu.menuImgUrl})` }}
                          className={classes.cardImg}></div>
                      </div>

                      <div className={classes.cardContent}>
                        <p className={classes.menuName}>{menu.menuName}</p>
                        <p className={classes.menuKcal}>{menu.calorie}kcal</p>
                        <p className={classes.menuIngre}>
                          {menu.materials.map((food, idx) => {
                            return idx === menu.materials.length - 1
                              ? `${food}`
                              : `${food}, `;
                          })}
                        </p>
                      </div>
                    </div>
                  </div>
                );
              })}
          </div>
        </div>
      </div>
      {isOpen ? (
        <DietDetail setIsOpen={setIsOpen} dietDetail={dietDetail}></DietDetail>
      ) : null}
    </div>
  );
};
export default DietSummary;
