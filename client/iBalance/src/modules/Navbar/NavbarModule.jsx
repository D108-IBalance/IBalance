// 외부 모듈
import React, { useState } from "react";
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
  const [hoverFlag, setHoverFlag] = useState([false, false, false, false]);
  const [colors, setColors] = useState([
    { color: "#FFB8A5" },
    { color: "#FFB8A5" },
    { color: "#FFB8A5" },
    { color: "#FFB8A5" },
  ]);
  const ICON = icons.map(
    (icon, idx) => `${icon}Icon${isClick === idx ? "Active" : ""}`,
  );
  const ICONHOVER = icons.map((icon) => icon + "IconHover");
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
                style={isClick == idx ? { color: "#FF5D30" } : colors[idx]}
                onMouseOver={() => {
                  setColors((prev) => {
                    let arr = [...prev];
                    arr[idx] = { color: "#FF5D30" };
                    return arr;
                  });
                  setHoverFlag((prev) => {
                    let arr = [...prev];
                    arr[idx] = true;
                    return arr;
                  });
                }}
                onMouseLeave={() => {
                  setColors((prev) => {
                    let arr = [...prev];
                    arr[idx] = { color: "#FFB8A5" };
                    return arr;
                  });
                  setHoverFlag((prev) => {
                    let arr = [...prev];
                    arr[idx] = false;
                    return arr;
                  });
                }}
                onClick={() => {
                  movePage(idx);
                }}>
                <p
                  className={
                    !hoverFlag[idx] ? classes[icon] : classes[ICONHOVER[idx]]
                  }></p>
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
                onMouseOver={() => {
                  setHoverFlag((prev) => {
                    let arr = [...prev];
                    arr[idx] = true;
                    return arr;
                  });
                }}
                onMouseLeave={() => {
                  setHoverFlag((prev) => {
                    let arr = [...prev];
                    arr[idx] = false;
                    return arr;
                  });
                }}
                className={
                  !hoverFlag[idx] ? classes[icon] : classes[ICONHOVER[idx]]
                }
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
