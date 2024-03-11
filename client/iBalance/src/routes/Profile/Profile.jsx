/* eslint-disable */

// 외부 모듈
import { Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

// 내부 모듈
import classes from "./Profile.module.css";
import { getProfile, deleteProfile } from "./ServerConnect.js";
import settingImg from "../../assets/profile/Img/setting.svg";
import warningImg from "../../assets/profile/Img/warning.svg";

const Profile = () => {
  const navigate = useNavigate();

  // 이후 프로필 리스트 추가할 변수 ajax 통신 필요
  let [profileList, setProfileList] = useState([]);
  let [isSetting, setIsSetting] = useState(false);
  let [deleteIdx, setDeleteIdx] = useState(-1);
  // 길이 정보를 통해 레이아웃( 4개 이상일 시 가운데 정렬 풂) 및 프로필 추가 버튼 여부 결정할 것
  let length = profileList.length;
  let justify = classes.formCenter; // default값은 가운데 정렬

  useEffect(() => {
    (async () => {
      let value = await getProfile();
      // 최대 프로필 수 6개이기에 만약 추가될 사항이 있으면 숫자 변경 가능
      if (value.data.length < 6) {
        value.data.push(null);
      }
      setProfileList(value.data);
    })();
  }, []);

  if (length >= 4) {
    // 길이가 3개 이상일 시 ==> 밑에 레이아웃이 추가 됨, 이 경우 justfy-content : start로 값 변경
    justify = classes.formStart;
  }
  return (
    <main className={classes.container}>
      <img
        className={classes.setting}
        src={settingImg}
        onClick={() => {
          setIsSetting(!isSetting);
        }}
      />
      <article className={classes.profileWrap}>
        <div className={classes.logo} />
        <Container className={`${classes.profileForm} ${justify}`}>
          {
            // 유저의 프로필 갯수만큼 반복
            profileList.map((content, idx) => {
              return (
                // 6개 미만일 시 프로필 추가버튼을 렌더해야함 즉, 마지막에 넣은 null값이 프로필 추가 버튼
                content === null ? (
                  <div className={classes.profile} key={idx}>
                    <div
                      className={classes.profilePlus}
                      onClick={(e) => {
                        e.preventDefault();
                        navigate("/profile/add");
                      }}></div>
                  </div>
                ) : // 유저 프로필 정보
                isSetting ? (
                  // 프로필 삭제
                  <div
                    className={classes.profile}
                    key={idx}
                    onClick={() => {
                      setDeleteIdx(idx);
                    }}>
                    <div className={classes.deleteBox}>
                      <div className={classes.deleteIcon} />
                    </div>
                    <div className={classes.profileIcon}></div>
                    <p className={classes.profileName}>{content.name}</p>
                  </div>
                ) : (
                  // 프로필 조회
                  <div className={classes.profile} key={idx}>
                    <div className={classes.profileIcon}></div>
                    <p className={classes.profileName}>{content.name}</p>
                  </div>
                )
              );
            })
          }
          {deleteIdx !== -1 ? (
            <Modal
              setDeleteIdx={setDeleteIdx}
              profileList={profileList}
              setProfileList={setDeleteIdx}
              deleteIdx={deleteIdx}></Modal>
          ) : null}
        </Container>
      </article>
    </main>
  );
};

const Modal = (props) => {
  let { deleteIdx, setProfileList, profileList, setDeleteIdx } = props;
  let deleteIt = async () => {
    let temp = profileList.map((content, idx) => {
      if (idx !== deleteIdx) {
        return content;
      }
    });
    let value = await deleteProfile(profileList[deleteIdx]["childId"]);
    if (value.status == "200") {
      setProfileList(temp);
      setDeleteIdx(-1);
    } else {
      alert("에러");
    }
  };
  return (
    <div
      className={classes.modal}
      onClick={(e) => {
        if (e.target === e.currentTarget) {
          setDeleteIdx(-1);
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
              setDeleteIdx(-1);
            }}>
            취소
          </button>
          <button
            className={classes.confirmBtn}
            onClick={() => {
              deleteIt();
            }}>
            확인
          </button>
        </div>
      </div>
    </div>
  );
};

export default Profile;
