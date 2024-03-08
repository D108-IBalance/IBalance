/* eslint-disable */

// 외부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule"
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

// 내부 모듈
import classes from './DietPage.module.css';

const DietPage = ()=>{
    return(
        <>
            <Row>
                <NavbarModule isClick={2}></NavbarModule>
                <Col>
                    this is Diet
                </Col>
            </Row>
        </>
    )
}

export default DietPage