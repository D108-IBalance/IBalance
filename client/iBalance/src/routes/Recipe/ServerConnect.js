import customAxios from "../../axiosController";

const getPickyCate = async (childId) => {
  return customAxios.get(`picky/${childId}?limit=MONTHLY`);
};

const getPickySolutionList = async (childId, material, offset, example) => {
  return customAxios.get(
    `picky/solution/${childId}?material=${material}&offset=${offset}&lastId=${example}`,
  );
};

const getPickySolutionDetail = async (childId, menuId) => {
  return customAxios.get(`picky/${childId}/${menuId}`);
};

export { getPickyCate, getPickySolutionList, getPickySolutionDetail };
