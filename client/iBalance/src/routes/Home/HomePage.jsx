/* eslint-disable */

// 외부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

// 내부 모듈
import classes from './HomePage.module.css';

const HomePage = ()=>{
    return(
        <>
            <Row>
                <NavbarModule isClick={0}></NavbarModule>
                <Col className={classes.homeContentBack}>
                    {/* 자녀 프로필 */}
                    <Row>
                        <div>자녀 프로필</div>
                    </Row>
                    {/* 오늘의 식단표 */}
                    <Row>
                        <TodayDiet/>
                    </Row>
                    {/* 우리아이 성장곡선 */}
                    <Row>
                        <div>우리아이 성장곡선</div>
                    </Row>
                </Col>
            </Row>
        </>
    )
}

const TodayDiet = ()=>{
    return(
        <>
            <div>
                <div></div>
                <span>오늘의 식단표</span>
            </div>
            <div className={classes.todayDietBox}></div>
        </>
    )
}

export default HomePage