import customAxios from "../../axiosController";

const getInitDiet = async (CHILDID) => {
  return customAxios.get(`diet/${CHILDID}/init`);
};

export { getInitDiet };
