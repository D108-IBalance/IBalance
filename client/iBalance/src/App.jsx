/* eslint-disable */

// 외부 모듈
import { Route,Routes } from "react-router";
import LoginPage from './routes/Auth/LoginPage.jsx';
import IntroPage from './routes/Auth/IntroPage.jsx';

// 내부 모듈
import  "./App.css";
import Error from "./routes/Error/Error";

const App = ()=>{
  return (
    <>
      <Routes>
        {/* 인트로 페이지 */}
        <Route path="/" element={<IntroPage></IntroPage>}/> 
        {/* 로그인 페이지 */}
        <Route path="/login" element={<LoginPage></LoginPage>}/>
        {/* 오류 페이지 */}
        <Route path="*" element={<><Error/></>}/>
      </Routes>
    </>
  )
}

export default App
