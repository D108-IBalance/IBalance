//내부 모듈
import classes from "./SaveModalPage.module.css";

const SaveModalPage = (props) => {
  const { setSaveModal, setSaveDiet } = props;
  return (
    <div
      className={classes.ModalBack}
      onClick={(e) => {
        e.target === e.currentTarget ? setSaveModal(false) : null;
      }}>
      <div className={classes.ModalBox}>
        <p>일주일 식단을 저장하시겠습니까?</p>
        <div className={classes.btnList}>
          <div
            className={classes.cancleBtn}
            onClick={() => {
              setSaveModal(false);
            }}>
            취소
          </div>
          <div
            className={classes.confirmBtn}
            onClick={() => {
              setSaveModal(false);
              setSaveDiet(true);
            }}>
            확인
          </div>
        </div>
      </div>
    </div>
  );
};

export default SaveModalPage;
