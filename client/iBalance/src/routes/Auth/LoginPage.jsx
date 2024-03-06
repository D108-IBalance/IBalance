// 외부 모듈
import {Container,  Row} from 'react-bootstrap';

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
    return(
        <div className={classes.loginFormbox}>
            <Container className={classes.loginForm}>
                <Row  className={classes.loginLogo}></Row>
                <Row className={classes.loginGoogle} ></Row>
                <Row className={classes.loginKakao}></Row>
                <Row className={classes.loginNaver}></Row>
            </Container>
        </div>
    )
}

export default LoginPage
