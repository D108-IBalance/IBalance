/* eslint-disable */

// 외부 모듈
import { Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";

// 내부 모듈
import classes from "./Profile.module.css";
import { getProfile, deleteProfile } from "./ServerConnect.js";
import settingImg from "../../assets/profile/Img/setting.svg";
import warningImg from "../../assets/profile/Img/warning.svg";

const Profile = () => {
  const navigate = useNavigate();
  const TOKEN = useSelector((state) => {
    return state.token;
  });
  const [profileList, setProfileList] = useState([]);
  const [isSetting, setIsSetting] = useState(false);
  const [deleteIdx, setDeleteIdx] = useState(-1);
  useEffect(() => {
    const getProfileList = async () => {
      try {
        let value = await getProfile(TOKEN);
        console.log(value);
      } catch (err) {
        console.log(err);
      }
    };
    getProfileList();
  }, []);

  const onDelete = async (deleteIdx) => {
    if (deleteIdx === -1) {
      setDeleteIdx(-1);
      return;
    }
    const { childId } = profileList[deleteIdx];
    const value = await deleteProfile(childId);
    if (value.status === "200") {
      setProfileList(profileList.filter((_, idx) => idx !== deleteIdx));
      setDeleteIdx(-1);
    } else {
      alert("에러발생");
    }
  };
  return (
    <main className={classes.container}>
      <img
        alt="setting"
        className={classes.setting}
        src={settingImg}
        onClick={() => {
          setIsSetting(!isSetting);
        }}
      />
      <article className={classes.profileWrap}>
        <div className={classes.logo} />
        <Container
          className={`${classes.profileForm} ${profileList >= 4 ? classes.formStart : classes.formCenter}`}>
          {profileList.map((profile, idx) => {
            return (
              <div
                key={idx}
                className={classes.profile}
                onClick={() => {
                  isSetting && setDeleteIdx(idx);
                }}>
                {isSetting ? (
                  <div className={classes.deleteBox}>
                    <div className={classes.deleteIcon} />
                  </div>
                ) : null}
                <div className={classes.profileIcon}></div>
                <p className={classes.profileName}>{profile.name}</p>
              </div>
            );
          })}
          {profileList.length < 6 && (
            <div
              className={classes.profile}
              onClick={() => navigate("/profile/add")}>
              <div className={classes.profilePlus} />
            </div>
          )}
        </Container>
      </article>
      {deleteIdx !== -1 && (
        <Modal
          deleteIdx={deleteIdx}
          profileList={profileList}
          setProfileList={setProfileList}
          onDelete={onDelete}
        />
      )}
    </main>
  );
};

const Modal = (props) => {
  let { deleteIdx, profileList, onDelete } = props;
  return (
    <div
      className={classes.modal}
      onClick={(e) => {
        if (e.target === e.currentTarget) {
          onDelete(deleteIdx);
        }
      }}>
      <div className={classes.modalContent}>
        <img src={warningImg} className={classes.warnIcon} />
        <p className={classes.modalDescription}>
          '{profileList[deleteIdx]["name"]}' 계정을 삭제하시겠습니까?
        </p>
        <div className={classes.btnBox}>
          <button
            className={classes.cancelBtn}
            onClick={() => {
              onDelete(-1);
            }}>
            취소
          </button>
          <button
            className={classes.confirmBtn}
            onClick={() => {
              onDelete(deleteIdx);
            }}>
            확인
          </button>
        </div>
      </div>
    </div>
  );
};

export default Profile;
