/* eslint-disable */

// 외부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule";
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

// 내부 모듈
import classes from './DiaryPage.module.css';
const DiaryPage = ()=>{
    return(
        <>
            <Row>
                <NavbarModule isClick={3}></NavbarModule>
                <Col>
                    this is Diary
                </Col>
            </Row>
        </>
    )
}

export default DiaryPage