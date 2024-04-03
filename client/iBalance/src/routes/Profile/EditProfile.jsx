// 외부 모듈
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";

// 내부 모듈
import classes from "./EditProfile.module.css";
import { getChildProfile, editProfileImg, editProfile } from "./ServerConnect";
import allergy from "./AddChild/allergy";
import ChildHeight from "./AddChild/ChildHeight";
import ChildWeight from "./AddChild/ChildWeight";
import ChildAllergy from "./AddChild/ChildAllergy";

const EditProfile = () => {
  const [uploadedImage, setUploadedImage] = useState("");
  const [userProfile, setUserProfile] = useState({});
  const [profileData, setProfileData] = useState({});
  const [step, setStep] = useState(0);

  const navigate = useNavigate();
  const childId = useSelector((state) => state.childId);
  const token = useSelector((state) => state.token);
  const sendProps = {
    setStep,
    setProfileData,
    profileData,
    step,
  };
  const component = [
    <ChildHeight {...sendProps} key="ChildHeight" />,
    <ChildWeight {...sendProps} key="ChildWeight" />,
    <ChildAllergy {...sendProps} key="ChildAllergy" />,
  ];
  useEffect(() => {
    const getUserData = async () => {
      const res = await getChildProfile(childId);
      setUserProfile(res.data.data);
      let tempProfile = {};
      tempProfile["name"] = res.data.data.name;
      tempProfile["height"] = res.data.data.height;
      tempProfile["weight"] = res.data.data.weight;
      tempProfile["haveAllergies"] = res.data.data.allergies;
      setProfileData(tempProfile);
      setUploadedImage(res.data.data.imageUrl);
    };
    getUserData();
  }, [childId]);

  const handleFileChange = async (e) => {
    if (e.target.files.length === 0) return;
    const file = e.target.files[0];
    if (!file) {
      return;
    }
    // 파일 크기 검증
    const maxFileSize = 100 * 1024 * 1024; // 100MB in bytes
    if (file.size > maxFileSize) {
      alert("파일 크기가 너무 큽니다. 100MB 이하의 파일을 업로드해주세요.");
      return;
    }

    // 파일 타입 검증
    if (!file.type.startsWith("image/")) {
      alert("이미지 파일만 업로드 가능합니다.");
      return;
    }

    const imageUrl = URL.createObjectURL(file);

    try {
      // 파일 업로드를 시도합니다.
      const res = await editProfileImg(childId, file, token);
      if (res.request.status === 200) {
        setUploadedImage(imageUrl);
      }
    } catch (error) {
      alert("이미지 업데이트 중 에러가 발생하였습니다.");
    }
  };
  const changeProfile = async () => {
    await editProfile(childId, profileData);
    navigate(-1);
  };
  return (
    <main className={classes.container}>
      {step === 0 ? (
        <section className={classes.formBox}>
          <div
            className={classes.backIcon}
            onClick={() => {
              navigate(-1);
            }}></div>
          <div className={classes.imgBox}>
            <div className={classes.settingLine}>
              <img
                src={uploadedImage}
                alt="Profile"
                className={classes.profileImg}
              />{" "}
              <div className={classes.settingIconBox}>
                <input
                  style={{ display: "none" }}
                  id="file"
                  type="file"
                  accept="image/*"
                  onChange={handleFileChange}
                />{" "}
                <label className={classes.settingIcon} htmlFor="file"></label>
              </div>
            </div>
          </div>
          <div className={classes.flexBet}>
            <p>자녀 이름</p>
            <div
              className={classes.dataBox}
              style={{ color: "#9796a1", cursor: "auto" }}>
              {userProfile.name}
            </div>
          </div>
          <div className={classes.flexBet}>
            <p>자녀 생년월일</p>
            <div
              className={classes.dataBox}
              style={{ color: "#9796a1", cursor: "auto" }}>
              {userProfile.birthDate}
            </div>
          </div>
          <div className={classes.flexBet}>
            <p>자녀 성별</p>
            <div
              className={classes.dataBox}
              style={{ color: "#9796a1", cursor: "auto" }}>
              {userProfile.gender === "MALE" ? "남자" : "여자"}
            </div>
          </div>
          <div
            className={classes.flexBet}
            style={{ cursor: "pointer" }}
            onClick={() => {
              setStep(1);
            }}>
            <p>자녀 키</p>
            <div className={classes.dataBox}>{profileData.height}cm</div>
          </div>
          <div
            className={classes.flexBet}
            style={{ cursor: "pointer" }}
            onClick={() => {
              setStep(2);
            }}>
            <p>자녀 몸무게</p>
            <div className={classes.dataBox}>{profileData.weight}kg</div>
          </div>
          <div
            className={classes.flexBet}
            style={{ cursor: "pointer" }}
            onClick={() => {
              setStep(3);
            }}>
            <p>자녀 알레르기</p>
            <div className={classes.dataBox}>
              {profileData.haveAllergies &&
              profileData.haveAllergies.length > 0 ? (
                profileData.haveAllergies.map((food, idx) => {
                  return profileData.haveAllergies.length - 1 !== idx ? (
                    <span key={idx}>{allergy[food - 1].name} ,</span>
                  ) : (
                    <span key={idx}>{allergy[food - 1].name}</span>
                  );
                })
              ) : (
                <span>-</span>
              )}
            </div>
          </div>
          <div className={classes.btnList}>
            <div
              className={classes.cancleBtn}
              onClick={() => {
                navigate(-1);
              }}>
              취소
            </div>
            <div
              className={classes.confirmBtn}
              onClick={() => {
                changeProfile();
              }}>
              확인
            </div>
          </div>
        </section>
      ) : (
        component[step - 1]
      )}
    </main>
  );
};

export default EditProfile;
