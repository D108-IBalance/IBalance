// 내부 모듈
import classes from "./EmptyDiet.module.css";

const EmptyModal = (props) => {
  const { setIsEmptyDiet } = props;
  const { setIsModal } = props;
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
            <p>박서준</p>
            <p>11세</p>
            <p>130cm / 35kg</p>
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
              setIsEmptyDiet(false);
            }}>
            확인
          </div>
        </div>
      </div>
    </div>
  );
};

export default EmptyModal;
