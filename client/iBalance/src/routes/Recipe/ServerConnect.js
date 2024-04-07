import customAxios from "../../axiosController";

const getPickyCate = async (childId) => {
  return customAxios.get(`picky/${childId}?limit=MONTHLY`);
};

const getPickySolutionList = async (childId, material, offset, example) => {
  return customAxios.get(
    `picky/solution/${childId}?material=${material}&offset=${offset}&lastId=${example}`,
  );
};

const getPickySolutionDetail = async (material, recipeId) => {
  return customAxios.get(`picky/detail/${material}/${recipeId}`);
};

export { getPickyCate, getPickySolutionList, getPickySolutionDetail };
