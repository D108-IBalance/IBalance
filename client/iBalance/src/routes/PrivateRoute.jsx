// 외부 모듈
import { useEffect, useState } from "react";
import { Navigate, Outlet, useLocation } from "react-router-dom";

const PrivateRoute = () => {
  const location = useLocation();
  const [isValide, setValide] = useState(true);
  useEffect(() => {
    const validate = () => {
      const persist = localStorage.getItem("persist:root");
      if (persist) {
        const state = JSON.parse(persist);
        const token = state.token;
        console.log(token);
        if (token && token !== '""') {
          setValide(true);
          return;
        }
      }
      setValide(false);
    };
    validate();
  }, [location]);
  return isValide ? <Outlet></Outlet> : <Navigate to="/enter/login" replace />;
};
export default PrivateRoute;
