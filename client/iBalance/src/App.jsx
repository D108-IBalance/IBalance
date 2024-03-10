/* eslint-disable */

// 외부 모듈
import { Route, Routes } from "react-router";
import "bootstrap/dist/css/bootstrap.min.css";

// 내부 모듈
import "./App.css";
import Error from "./routes/Error/Error";
import AddProfile from "./routes/Profile/AddProfile.jsx";
import "bootstrap/dist/css/bootstrap.min.css";
import LoginForm from "./routes/Auth/LoginForm.jsx";
import IntroPage from "./routes/Auth/IntroPage.jsx";
import EnterPage from "./routes/Auth/EnterPage.jsx";
import HomePage from "./routes/Home/HomePage.jsx";
import SocialLogin from "./routes/Auth/SocialLogin.jsx";
import RecipePage from "./routes/Recipe/RecipePage.jsx";
import DietPage from "./routes/Diet/DietPage.jsx";
import DiaryPage from "./routes/Diary/DiaryPage.jsx";
import Profile from "./routes/Profile/Profile.jsx";

const App = () => {
  return (
    <>
      <Routes>
        {/* 인트로 페이지 */}
        <Route path="/" element={<IntroPage></IntroPage>} />
        {/* 로그인 페이지 */}
        <Route path="/enter" element={<EnterPage></EnterPage>}>
          <Route path="login" element={<LoginForm></LoginForm>}>
            <Route
              path="callback/:id"
              element={<SocialLogin></SocialLogin>}></Route>
          </Route>
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
        <Route path="/diet" element={<DietPage />}></Route>
        {/* 일기장페이지 */}
        <Route path="/diary" element={<DiaryPage />}></Route>
        {/* 오류 페이지 */}
        <Route
          path="*"
          element={
            <>
              <Error />
            </>
          }
        />
      </Routes>
    </>
  );
};

export default App;
