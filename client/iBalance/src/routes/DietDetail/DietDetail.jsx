// 외부 모듈
import { useNavigate, useOutletContext, useParams } from "react-router-dom";

// 내부 모듈
import classes from "./DietDetail.module.css";

const DietDetail = () => {
  const { setIsOpen } = useOutletContext();
  const { menu } = useParams();
  const navigate = useNavigate();
  return (
    <>
      <div className={classes.ModalBack}>
        {menu}
        <div className={classes.detailModal}>
          <div
            onClick={() => {
              navigate("/detail");
              setIsOpen(false);
            }}>
            닫기
          </div>
        </div>
      </div>
      ;
    </>
  );
};
export default DietDetail;
