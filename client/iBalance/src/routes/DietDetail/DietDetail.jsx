// 외부 모듈
import { useParams } from "react-router-dom";

// 내부 모듈
import classes from "./DietDetail.module.css";

const DietDetail = () => {
  const { menu } = useParams();
  return <div className={classes.container}>{menu}</div>;
};
export default DietDetail;
