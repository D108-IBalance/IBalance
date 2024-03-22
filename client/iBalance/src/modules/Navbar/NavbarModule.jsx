// 외부 모듈
import React from "react";
import { useNavigate } from "react-router-dom";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";

// 내부 모듈
import classes from "./NavbarModule.module.css";

// props로 넘겨줘야 할 사항 : isClick (이 컴포넌트가 호출된 위치가 어느 아이콘이 불이 들어와야하는지)
const NavbarModule = (props) => {
  const { isClick } = props;
  let navigate = useNavigate();
  const LINK = ["/home", "/recipe", "/diet", "/diary"];
  const icons = ["home", "recipe", "diet", "diary"];
  const ICON = icons.map(
    (icon, idx) => `${icon}Icon${isClick === idx ? "Active" : ""}`,
  );
  let movePage = (idx) => {
    navigate(LINK[idx]);
  };

  return (
    <>
      <Navbar className={classes.navBox} style={{ backgroundColor: "white" }}>
        {/* 모바일 */}
        <Nav className={classes.iconBox}>
          {ICON.map((icon, idx) => {
            return (
              <Nav.Link
                key={idx}
                className={classes[icon]}
                onClick={() => {
                  movePage(idx);
                }}></Nav.Link>
            );
          })}
        </Nav>

        {/* 데스크톱 전체화면 */}
        <Nav
          className={classes.texticonBox}
          style={{ flexDirection: "column" }}>
          <div className={classes.logoBox}></div>
          {ICON.map((icon, idx) => {
            return (
              <Nav.Link
                key={idx}
                className={classes.IconBox}
                style={
                  isClick == idx ? { color: "#FF5D30" } : { color: "#FFB8A5" }
                }
                onClick={() => {
                  movePage(idx);
                }}>
                <p className={classes[icon]}></p>
                <span>{icons[idx]}</span>
              </Nav.Link>
            );
          })}
        </Nav>

        {/* 데스크톱 축소화면 */}

        <Nav
          className={classes.smallDesktop}
          style={{ flexDirection: "column" }}>
          {ICON.map((icon, idx) => {
            return (
              <Nav.Link
                key={idx}
                className={classes[icon]}
                style={{ margin: "30px" }}
                onClick={() => {
                  movePage(idx);
                }}></Nav.Link>
            );
          })}
        </Nav>
      </Navbar>
    </>
  );
};

export default React.memo(NavbarModule);
