/* eslint-disable */

// 외부 모듈
import {Container,  Row} from 'react-bootstrap';
import { Link, useNavigate, Outlet } from 'react-router-dom';

// 내부 모듈
import classes from './EnterPage.module.css'

const EnterPage = ()=>{
    return(
        <>
            <div className={classes.loginBack}>
                <Outlet></Outlet>
            </div>
        </>
    )

}


export default EnterPage
