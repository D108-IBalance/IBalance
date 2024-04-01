// 외부 모듈
import { useState } from "react";

// 내부 모듈
import classes from "./EmptyDiet.module.css";
import EmptyModal from "./EmptyModal";

const EmptyDiet = (props) => {
  const { setUserDiet } = props;
  const [isModal, setIsModal] = useState(false);
  return (
    <>
      <div className={classes.emptyBox}>
        <div className={classes.emptyIcon}></div>
        <div
          className={classes.emptyBtn}
          onClick={() => {
            setIsModal(true);
          }}>
          식단 받기
        </div>
      </div>
      {isModal && (
        <EmptyModal
          setIsModal={setIsModal}
          setUserDiet={setUserDiet}></EmptyModal>
      )}
    </>
  );
};

export default EmptyDiet;
