/* eslint-disable */

import { useNavigate } from "react-router-dom";
import classes from './LoginForm.module.css'
import {Container,  Row} from 'react-bootstrap';
import { useEffect } from "react";

const LoginForm = ()=>{

    const SocialLogin = (num) =>{
        let server = '';
        if ( num == 0 ){
            server = process.env.GOOGLE_AUTH_URL;
        }else if ( num == 1 ){
            server = process.env.KAKAO_AUTH_URL;
        }else {
            server = process.env.NAVER_AUTH_URL;
        }
        window.location.href = server;

    };

    return(
        <div className={classes.loginFormbox}>
            <Container className={classes.loginForm}>
                <Row className={classes.loginLogo}></Row>

                <Row className={classes.loginGoogle} onClick={SocialLogin(0)}></Row>
                <Row className={classes.loginKakao} onClick={SocialLogin(1)}></Row>
                <Row className={classes.loginNaver}onClick={SocialLogin(2)}></Row>
            </Container>
        </div>
    )
}

export default LoginForm