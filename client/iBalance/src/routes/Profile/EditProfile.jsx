// 외부 모듈
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

// 내부 모듈
import classes from "./EditProfile.module.css";
import { getChildProfile, editProfileImg } from "./ServerConnect";
import { useEffect, useState } from "react";

const EditProfile = () => {
  const [uploadedImage, setUploadedImage] = useState("");
  const [userProfile, setUserProfile] = useState({});
  const navigate = useNavigate();
  const childId = useSelector((state) => state.childId);

  useEffect(() => {
    const getUserData = async () => {
      const res = await Promise.all([getChildProfile(childId)]);
      setUserProfile(res[0].data.data.childMainResponse);
      setUploadedImage(res[0].data.data.childMainResponse.imageUrl);
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
      const res = await editProfileImg(childId, file);
      if (res.request.status === 200) {
        setUploadedImage(imageUrl);
      }
    } catch (error) {
      alert("이미지 업데이트 중 에러가 발생하였습니다.");
    }
  };
  return (
    <main className={classes.container}>
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
          <div className={classes.dataBox}>{userProfile.name}</div>
        </div>
        <div className={classes.flexBet}>
          <p>자녀 생년월일</p>
          <div className={classes.dataBox}>{userProfile.birthDate}</div>
        </div>
        <div className={classes.flexBet}>
          <p>자녀 성별</p>
          <div className={classes.dataBox}>
            {userProfile.gender === "MALE" ? "남자" : "여자"}
          </div>
        </div>
        <div className={classes.flexBet}>
          <p>자녀 키</p>
          <div className={classes.dataBox}>{userProfile.height}cm</div>
        </div>
        <div className={classes.flexBet}>
          <p>자녀 몸무게</p>
          <div className={classes.dataBox}>{userProfile.weight}kg</div>
        </div>
        <div className={classes.flexBet}>
          <p>자녀 알레르기</p>
          <div className={classes.dataBox}>
            토마토, 우유, 대두, 계란, 고등어
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
          <div className={classes.confirmBtn}>확인</div>
        </div>
      </section>
    </main>
  );
};

export default EditProfile;
