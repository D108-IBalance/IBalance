/* eslint-disable */

// 외부 모듈
import { useNavigate } from "react-router-dom"
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { useState } from "react";

// 내부 모듈
import classes from './NavbarModule.module.css';


const NavbarModule = (props)=>{
    let {isClick} = props;
    let navigate = useNavigate();
    let arr = [0,0,0,0];
    arr[isClick] = 1;
    console.log(arr)
    return(
      <>
        <Navbar  className={classes.navBox} style={{backgroundColor : 'white'}}> 
        <Container>
          <Nav className={classes.iconBox}>
            <Nav.Link className={arr[0] == 1 ? classes.homeIconActive : classes.homeIcon} active onClick={()=>{ navigate('/home')}}></Nav.Link>
            <Nav.Link className={arr[1] == 1 ? classes.recipeIconActive : classes.recipeIcon} onClick={()=>{ navigate('/recipe')}}></Nav.Link>
            <Nav.Link className={arr[2] == 1 ? classes.dietIconActive : classes.dietIcon} onClick={()=>{ navigate('/diet')}}></Nav.Link>
            <Nav.Link className={arr[3] == 1 ? classes.diaryIconActive : classes.diaryIcon} onClick={()=>{ navigate('/diary')}}></Nav.Link>
          </Nav>
          <Nav className={classes.texticonBox}>
            <Nav.Link className={classes.IconBox} style={arr[0] == 1 ? { color: '#FF5D30' }:{ color: '#FFB8A5' }} active onClick={()=>{ navigate('/home')}}>
              <p className={arr[0] == 1 ? classes.homeIconActive : classes.homeIcon}></p>&nbsp; Home</Nav.Link>
            <Nav.Link className={classes.IconBox} style={arr[1] == 1 ? { color: '#FF5D30' }:{ color: '#FFB8A5' }} onClick={()=>{ navigate('/recipe')}}>
              <p className={arr[1] == 1 ? classes.recipeIconActive : classes.recipeIcon}></p>&nbsp;Recipe</Nav.Link>
            <Nav.Link className={classes.IconBox} style={arr[2] == 1 ? { color: '#FF5D30' }:{ color: '#FFB8A5' }} onClick={()=>{ navigate('/diet')}}>
              <p className={arr[2] == 1 ? classes.dietIconActive : classes.dietIcon}></p>&nbsp;Diet</Nav.Link>
            <Nav.Link className={classes.IconBox} style={arr[3] == 1 ? { color: '#FF5D30' }:{ color: '#FFB8A5' }} onClick={()=>{ navigate('/diary')}}>
              <p className={arr[3] == 1 ? classes.diaryIconActive : classes.diaryIcon}></p>&nbsp;Diary</Nav.Link>
          </Nav>
        </Container>
      </Navbar>
      </>
    )
}

export default NavbarModule