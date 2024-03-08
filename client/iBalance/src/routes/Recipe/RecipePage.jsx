/* eslint-disable */

// 외부 모듈
import NavbarModule from "../../modules/Navbar/NavbarModule"
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

// 내부 모듈
import classes from './RecipePage.module.css';

const RecipePage = ()=>{
    return(
        <>
            <Row>
                <NavbarModule isClick={1}></NavbarModule>
                <Col>
                    this is Recipe
                </Col>
            </Row>
        </>
    )
}

export default RecipePage