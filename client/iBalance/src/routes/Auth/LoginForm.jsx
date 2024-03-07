import { useNavigate } from "react-router-dom"
import classes from './LoginForm.module.css'
import {Container,  Row} from 'react-bootstrap';

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

export default LoginForm