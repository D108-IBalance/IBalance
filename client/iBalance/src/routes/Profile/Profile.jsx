/* eslint-disable */

// 외부 모듈
import { Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { initializeApp } from "firebase/app"
import { getMessaging, getToken, onMessage } from "firebase/messaging"

// 내부 모듈
import classes from "./Profile.module.css";
import { getProfile, deleteProfile } from "./ServerConnect.js";
import settingImg from "../../assets/profile/Img/setting.svg";
import warningImg from "../../assets/profile/Img/warning.svg";
import { setChildId } from "../../store.js";
import customAxios from "../../axiosController";

const Profile = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const token = useSelector((state) => state.token);
  const [profileList, setProfileList] = useState([]);
  const [isSetting, setIsSetting] = useState(false);
  const [deleteIdx, setDeleteIdx] = useState(-1);

  const app = initializeApp({
    apiKey: import.meta.env.VITE_APP_FCM_API_KEY,
    authDomain: import.meta.env.VITE_APP_FCM_AUTH_DOMAIN,
    projectId: import.meta.env.VITE_APP_FCM_PROJECT_ID,
    storageBucket: import.meta.env.VITE_APP_FCM_STORAGE_BUCKET,
    messagingSenderId: import.meta.env.VITE_APP_FCM_MESSAGING_SENDER_ID,
    appId: import.meta.env.VITE_APP_FCM_APP_ID,
    measurementId: import.meta.env.VITE_APP_FCM_MEASUREMENT_ID
  });

  const messaging = getMessaging();

  function requestPermission() {
    console.log('Requesting permission...');
    Notification.requestPermission().then((permission) => {
      if (permission === 'granted') {
        console.log('Notification permission granted.');
        getToken(messaging, { vapidKey: import.meta.env.VITE_APP_FCM_VAPID_KEY })
        .then((currentToken) => {
          if (currentToken) {
            customAxios.post('fcm',{token : currentToken})
            .then(res => {})
            .catch(err => console.log(err));
          } else {
            console.log('No registration token available. Request permission to generate one.');
          }
        }).catch((err) => {
          console.log('An error occurred while retrieving token. ', err);
        });
        onMessage(messaging, (payload) => {
          console.log('Message received. ', payload);
        });
      }
      else {
        console.log('Notification permission not granted.');
      }
    })
  }

  const goToHome = (idx) => {
    dispatch(setChildId(profileList[idx].childId));
    navigate("/home");
  };
  const onDelete = async (idx) => {
    if (idx === -1) {
      setDeleteIdx(-1);
      return;
    }
    const { childId } = profileList[deleteIdx];
    const value = await deleteProfile(childId);
    if (value.status == "200") {
      setProfileList(profileList.filter((_, idx) => idx !== deleteIdx));
      setDeleteIdx(-1);
    } else {
      alert("에러발생");
    }
  };

  useEffect(() => {
    requestPermission();
    const getProfileList = async () => {
      let value = await getProfile();
      setProfileList(value.data.data);
    };
    getProfileList();
  }, []);

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
          className={`${classes.profileForm} ${profileList.length >= 3 ? classes.formStart : classes.formCenter}`}>
          {profileList.map((profile, idx) => {
            return (
              <div
                key={idx}
                className={classes.profile}
                onClick={() => {
                  isSetting && setDeleteIdx(idx);
                  !isSetting && goToHome(idx);
                }}>
                {isSetting ? (
                  <div className={classes.deleteBox}>
                    <div className={classes.deleteIcon} />
                  </div>
                ) : null}
                <img
                  src={profile.imageUrl}
                  className={
                    !isSetting ? classes.profileIcon : classes.profileDeleteIcon
                  }
                />
                <p className={classes.profileName}>{profile.name}</p>
              </div>
            );
          })}
          {profileList.length < 6 && !isSetting && (
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
