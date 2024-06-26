// 외부 모듈
import Lottie from "react-lottie";
import Icon from "../../assets/load/json/Lottie-load.json";
import dietIcon from "../../assets/load/json/getDietLottie.json";

// 내부 모듈
import classes from "./Load.module.css";

/* 
    props값 step을 통해 상태 업데이트 가능
    key(step)
        - 0 : default(none)
        - 1 : (on -> off) off지만 dom에 상태는 on, display만 none 처리
        - 2 : (off/none -> on)
*/
const Load = (props) => {
  const { step, bgColor, isCreate } = props;
  let isShow = false;
  let fade = "";

  switch (step) {
    case 1: // on -> off 애니메이션 실행
      isShow = true;
      fade = "fadeOut";
      break;
    case 2: // off -> on
      isShow = true;
      fade = "";
      // fade = "fadeOut";
      break;
    default: // 디폴트값 명시적 작성
      isShow = false;
  }
  return (
    <>
      {isShow ? ( //isShow값에 따른 랜더 결정 default 제외 모두 랜더
        <div id="spinner">
          <Spinner fade={fade} bgColor={bgColor} isCreate={isCreate}></Spinner>
        </div>
      ) : (
        <></>
      )}
    </>
  );
};

const Spinner = (props) => {
  const { fade, bgColor, isCreate } = props; // 애니메이션 클래스
  const defaultOptions = {
    // lottie Options
    loop: true,
    autoplay: true,
    animationData: isCreate ? dietIcon : Icon,
    rendererSettings: {
      preserveAspectRatio: "xMidYMid slice",
    },
  };
  return (
    <div
      className={classes.container + ` ${classes[fade]}`}
      style={bgColor && { backgroundColor: `${bgColor}` }}>
      <Lottie
        options={defaultOptions}
        height={isCreate ? 300 : 100}
        width={isCreate ? 300 : 100}
      />
      <span className={classes.loadText}>
        {isCreate ? <>Create Weekly Diets...</> : <>Loading...</>}
      </span>
    </div>
  );
};

export default Load;
