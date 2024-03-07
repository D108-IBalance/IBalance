/* eslint-disable */

// 외부 모듈
import {Container,  Row} from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';

// 내부 모듈
import classes from '../../assets/auth/LoginPage.module.css'

const LoginPage = ()=>{
    return(
        <>
            <div className={classes.loginBack}>
                <LoginForm></LoginForm>
            </div>
        </>
    )

}

const LoginForm = ()=>{
    let navigate = useNavigate()
    return(
        <div className={classes.loginFormbox}>
            <Container className={classes.loginForm}>
                <Row className={classes.loginLogo}></Row>

                <Row className={classes.loginGoogle} onClick={()=>{ navigate('/home')}}></Row>
                <Row className={classes.loginKakao}></Row>
                <Row className={classes.loginNaver}></Row>
            </Container>
        </div>
    )
}

export default LoginPage
