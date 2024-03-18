// 외부 모듈
import { useState } from "react";

// 내부 모듈
import classes from "./EmptyDiet.module.css";

const EmptyDiet = (props) => {
  let { setEmptyDiet } = props;
  let [isModal, setIsmodal] = useState(false);
  return (
    <>
      <div className={classes.emptyBox}>
        <div className={classes.emptyIcon}></div>
        <div
          className={classes.emptyBtn}
          onClick={() => {
            setIsmodal(true);
          }}>
          식단 받기
        </div>
      </div>
      {isModal === true ? (
        <EmptyModal
          setIsmodal={setIsmodal}
          setEmptyDiet={setEmptyDiet}></EmptyModal>
      ) : null}
    </>
  );
};

const EmptyModal = (props) => {
  let { setEmptyDiet } = props;
  let { setIsmodal } = props;
  return (
    <div
      className={classes.emptyModalBack}
      onClick={(e) => {
        e.target === e.currentTarget ? setIsmodal(false) : null;
      }}>
      <div className={classes.emptyModal}>
        <div className={classes.emptyModalImg}></div>
        <p>아이의 현재 성장상태를 확인해주세요.</p>
        <div className={classes.modalTextBox}>
          <div className={classes.modalTextImg}></div>
          <div className={classes.modalText}>
            <p>박서준</p>
            <p>11세</p>
            <p>130cm / 35kg</p>
          </div>
        </div>
        <div className={classes.btnList}>
          <div
            className={classes.cancleBtn}
            onClick={() => {
              setIsmodal(false);
            }}>
            취소
          </div>
          <div
            className={classes.confirmBtn}
            onClick={() => {
              setEmptyDiet(false);
            }}>
            확인
          </div>
        </div>
      </div>
    </div>
  );
};

export default EmptyDiet;
