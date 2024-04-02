// 내부 모듈
import { useEffect, useState } from "react";
import classes from "./EmptyDiet.module.css";
import { getUserInfo, getInitDiet } from "./ServerConnect";
import { useSelector } from "react-redux";

const EmptyModal = (props) => {
  const { setIsModal, setUserDiet, setLoadStep, setIsCreate } = props;
  const [userProfile, setUserProfile] = useState({});
  const childId = useSelector((state) => state.childId);
  useEffect(() => {
    const getUserData = async () => {
      const res = await getUserInfo(childId);
      setUserProfile(res.data.data.childMainResponse);
    };
    getUserData();
  }, []);

  const dietHandler = async () => {
    setIsCreate(true);
    setLoadStep(2);
    const res = await getInitDiet(childId);
    setUserDiet(res.data.data);
    setLoadStep(1);
    setIsCreate(false);
  };
  return (
    <div
      className={classes.emptyModalBack}
      onClick={(e) => {
        e.target === e.currentTarget ? setIsModal(false) : null;
      }}>
      <div className={classes.emptyModal}>
        <div className={classes.emptyModalImg}></div>
        <p>아이의 현재 성장상태를 확인해주세요.</p>
        <div className={classes.modalTextBox}>
          <div className={classes.modalTextImg}></div>
          <div className={classes.modalText}>
            <p>{userProfile?.name}</p>
            <p>{userProfile?.birthDate}</p>
            <p>
              {userProfile?.height}cm / {userProfile?.weight}kg
            </p>
          </div>
        </div>
        <div className={classes.btnList}>
          <div
            className={classes.cancleBtn}
            onClick={() => {
              setIsModal(false);
            }}>
            취소
          </div>
          <div
            className={classes.confirmBtn}
            onClick={() => {
              dietHandler();
              setIsModal(false);
            }}>
            확인
          </div>
        </div>
      </div>
    </div>
  );
};

export default EmptyModal;
