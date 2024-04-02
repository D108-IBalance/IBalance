// 외부 모듈
import Offcanvas from "react-bootstrap/Offcanvas";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

// 내부 모듈
import classes from "./OffcanvasPage.module.css";
import { getChildProfile } from "../../routes/Profile/ServerConnect";
import { logout } from "../../routes/Auth/ServerConnect";

const OffcanvasPage = ({ ...props }) => {
  const navigate = useNavigate();
  const [show, setShow] = useState(false);
  const [fcmAni, setFcmAni] = useState("");
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const [childInfo, setChildInfo] = useState(null);
  const childId = useSelector((state) => state.childId);
  const logOutSite = async () => {
    await logout();
    localStorage.removeItem("persist:root");
    navigate("/");
  };
  useEffect(() => {
    const getInfo = async () => {
      let value = await getChildProfile(childId);
      setChildInfo(value.data.data);
    };
    getInfo();
  }, []);
  const fcmDown = () => {
    setFcmAni("fcmDown");
  };
  const fcmUp = () => {
    setFcmAni("fcmUp");
  };

  return (
    <>
      <div
        onClick={() => {
          handleShow();
          // fcmDown();
        }}
        className={classes.toggleBtn}></div>
      <div className={`${classes.fcmContainer} ${classes[fcmAni]}`}>
        <div className={classes.fcmMsg}>
          <div className={classes.fcmIcon} />
          <p className={classes.fcmContent}>
            <span style={{ fontWeight: "bold" }}>부수환</span> 님에 대한 식단
            업데이트가 필요합니다.
          </p>
        </div>
      </div>
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
              <img
                className={classes.img}
                src={childInfo && childInfo.imageUrl}
              />
            </div>
            <p className={classes.name}>{childInfo && childInfo.name}</p>
            <p className={classes.loginForm}>{childInfo && childInfo.gender}</p>
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
            <div className={classes.logout} onClick={logOutSite}>
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
