//내부 모듈
import classes from "./SaveModalPage.module.css";

const SaveModalPage = (props) => {
  const { setSaveModal, setSaveDiet } = props;
  const closeModal = () => {
    setSaveModal(false);
  };

  const confirmAndCloseModal = () => {
    setSaveDiet(true);
    setSaveModal(false);
  };

  return (
    <div
      className={classes.ModalBack}
      onClick={(e) => {
        if (e.target === e.currentTarget) closeModal();
      }}>
      <div className={classes.ModalBox}>
        <p>일주일 식단을 저장하시겠습니까?</p>
        <div className={classes.btnList}>
          <div
            className={classes.cancelBtn}
            onClick={() => {
              closeModal();
            }}>
            취소
          </div>
          <div
            className={classes.confirmBtn}
            onClick={() => {
              confirmAndCloseModal();
            }}>
            확인
          </div>
        </div>
      </div>
    </div>
  );
};

export default SaveModalPage;
