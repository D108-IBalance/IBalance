// 외부 모듈
import { Route, Routes } from "react-router";
import "bootstrap/dist/css/bootstrap.min.css";
import { useEffect } from "react";

// 내부 모듈
import "./App.css";
import Error from "./routes/Error/Error";
import AddProfile from "./routes/Profile/AddProfile.jsx";
import LoginForm from "./routes/Auth/LoginForm.jsx";
import IntroPage from "./routes/Auth/IntroPage.jsx";
import EnterPage from "./routes/Auth/EnterPage.jsx";
import HomePage from "./routes/Home/HomePage.jsx";
import SocialLogin from "./routes/Auth/SocialLogin.jsx";
import RecipePage from "./routes/Recipe/RecipePage.jsx";
import DietPage from "./routes/Diet/DietPage.jsx";
import DiaryPage from "./routes/Diary/DiaryPage.jsx";
import Profile from "./routes/Profile/Profile.jsx";
import DietDetail from "./routes/DietDetail/DietDetail.jsx";

const App = () => {
  const setScreenSize = () => {
    const VH = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${VH}px`);
  };
  useEffect(() => {
    setScreenSize();
    window.addEventListener("resize", setScreenSize);
    return () => {
      window.removeEventListener("resize", setScreenSize);
    };
  }, []);
  return (
    <>
      <Routes>
        {/* 인트로 페이지 */}
        <Route path="/" element={<IntroPage></IntroPage>} />
        {/* 로그인 페이지 */}
        <Route path="/enter" element={<EnterPage></EnterPage>}>
          <Route path="login" element={<LoginForm></LoginForm>}></Route>
        </Route>
        {/* 프로필 페이지 */}
        <Route path="/enter" element={<EnterPage></EnterPage>}>
          <Route path="profile" element={<Profile />} />
        </Route>
        <Route path="/profile/add" element={<AddProfile></AddProfile>}></Route>
        {/* 홈페이지 */}
        <Route path="/home" element={<HomePage />}></Route>
        {/* 레시피페이지 */}
        <Route path="/recipe" element={<RecipePage />}></Route>
        {/* 식단페이지 */}
        <Route path="/diet" element={<DietPage />}>
          <Route path="detail" element={<DietDetail></DietDetail>}></Route>
        </Route>
        {/* 일기장페이지 */}
        <Route path="/diary" element={<DiaryPage />}></Route>
        {/* 소셜 로그인 페이지 */}
        <Route
          path="/auth/social/:provider"
          element={<SocialLogin></SocialLogin>}></Route>
        {/* 오류 페이지 */}
        <Route path="*" element={<Error />} />
      </Routes>
    </>
  );
};

export default App;
