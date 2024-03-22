// 외부 모듈
import Offcanvas from "react-bootstrap/Offcanvas";

// 내부 모듈
import classes from "./OffcanvasPage.module.css";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const OffcanvasPage = ({ ...props }) => {
  const navigate = useNavigate();
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <>
      <div onClick={handleShow} className={classes.toggleBtn}></div>
      <Offcanvas
        show={show}
        onHide={handleClose}
        {...props}
        // responsive="sm"
        style={{ width: "80%" }}>
        <Offcanvas.Header closeButton></Offcanvas.Header>
        <Offcanvas.Body>
          <div className={classes.profileBox}>
            <div className={classes.imgBox}>
              <div className={classes.img}></div>
            </div>
            <p className={classes.name}>박서준</p>
            <p className={classes.loginForm}>카카오 로그인</p>
          </div>
          <div className={classes.contentBox}>
            <div>
              <div
                className={classes.switchBox}
                onClick={() => navigate("/enter/profile")}>
                <div className={classes.switchIcon}></div>
                <p>자녀 변경</p>
              </div>
              <div
                className={classes.editBox}
                onClick={() => {
                  navigate("/profile/edit");
                }}>
                <div className={classes.editIcon}></div>
                <p>프로필 정보 수정</p>
              </div>
            </div>
            <div className={classes.logout}>
              <div className={classes.logoutIcon}></div>
              <p>Log Out</p>
            </div>
          </div>
        </Offcanvas.Body>
      </Offcanvas>
    </>
  );
};

export default OffcanvasPage;
