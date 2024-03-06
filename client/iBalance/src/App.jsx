/* eslint-disable */

// 외부 모듈
import { Route,Routes } from "react-router";


// 내부 모듈
import  "./App.css";
import Error from "./routes/Error/Error";

const App = ()=>{
  return (
    <>
      <Routes>
        <Route path="/404" element={<><Error/></>}/>
      </Routes>
    </>
  )
}

export default App
