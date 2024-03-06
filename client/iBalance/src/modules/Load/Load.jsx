/* eslint-disable */

// 외부 모듈
import { useRef } from "react";
import Lottie from 'react-lottie';
import Icon from "../../assets/load/json/Lottie-load.json";

// 내부 모듈
import classes from './Load.module.css';

/* 
    props값 step을 통해 상태 업데이트 가능
    key(step)
        - 0 : default(none)
        - 1 : (off -> on)
        - 2 : (on/none->off)
*/
const Load = (props)=>{ 
    let { step } = props;
    let isShow = false;
    let fade = "";

    switch(step) {
        case 1: // on -> off 애니메이션 실행
            isShow = true;
            fade = "fadeOut";
            break;
        case 2: // off -> on
            isShow = true;
            fade = "fadeOut";
            break;
        default: // 디폴트값 명시적 작성
            isShow = false;
    }

    return(
        <>
        {
            isShow?( //isShow값에 따른 랜더 결정 default 제외 모두 랜더
                <div id="spinner">
                    <Spinner fade={fade}></Spinner>
                </div>
            ):(
                <></>
            )
        }
        </>
    )
}


const Spinner = (props)=>{
    const defaultOptions = { // lottie Options
        loop: true,
        autoplay: true,
        animationData: Icon,
        rendererSettings: {
          preserveAspectRatio: "xMidYMid slice"
        },
      };

    let {fade} = props // 애니메이션 클래스

    return(
        <div className={classes.container + ` ${classes[fade]}`} >
            <Lottie options={defaultOptions} height={100} width={100}/>
            <span className={classes.loadText}>Loading...</span>
        </div>
    )
}

export default Load