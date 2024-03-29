const init = {
  status: 200,
  data: [
    {
      dietDay: 0,
      menuList: [
        [
          {
            menuId: "65fa83f63eb83d319efa85de",
            menuName: "쇠고기죽",
            menuType: "RICE",
          },
          {
            menuId: "65fa83bf3eb83d319efa85af",
            menuName: "갈비탕",
            menuType: "SOUP",
          },
          {
            menuId: "65fa83f63eb83d319efa8629",
            menuName: "동태찜",
            menuType: "SIDE",
          },
          {
            menuId: "65fa83f63eb83d319efa8609",
            menuName: "단호박찜",
            menuType: "SIDE",
          },
        ],
      ],
    },
    {
      dietDay: 1,
      menuList: [
        [
          {
            menuId: "65fa83f63eb83d319efa85e9",
            menuName: "단호박스프",
            menuType: "RICE",
          },
          {
            menuId: "65fa83bf3eb83d319efa85b9",
            menuName: "감자애호박국",
            menuType: "SOUP",
          },
          {
            menuId: "65fa83f63eb83d319efa8615",
            menuName: "깻잎찜",
            menuType: "SIDE",
          },
          {
            menuId: "65fa83f63eb83d319efa861d",
            menuName: "단호박오징어찜",
            menuType: "SIDE",
          },
        ],
      ],
    },
    {
      dietDay: 2,
      menuList: [
        [
          {
            menuId: "65fa83bf3eb83d319efa857d",
            menuName: "잡곡밥",
            menuType: "RICE",
          },
          {
            menuId: "65fa83bf3eb83d319efa85d6",
            menuName: "팽이버섯계란탕",
            menuType: "SOUP",
          },
          {
            menuId: "65fa84523eb83d319efa86b1",
            menuName: "숙주맛살무침",
            menuType: "SIDE",
          },
          {
            menuId: "65fa84203eb83d319efa868a",
            menuName: "청경채버섯무침",
            menuType: "SIDE",
          },
        ],
      ],
    },
    {
      dietDay: 3,
      menuList: [
        [
          {
            menuId: "65fa83bf3eb83d319efa858d",
            menuName: "옥수수밥",
            menuType: "RICE",
          },
          {
            menuId: "65fa83bf3eb83d319efa85a4",
            menuName: "감자국",
            menuType: "SOUP",
          },
          {
            menuId: "65fa83f63eb83d319efa8632",
            menuName: "애호박생선살찜",
            menuType: "SIDE",
          },
          {
            menuId: "65fa83f63eb83d319efa8638",
            menuName: "김구이",
            menuType: "SIDE",
          },
        ],
      ],
    },
    {
      dietDay: 4,
      menuList: [
        [
          {
            menuId: "65fa83f63eb83d319efa85ee",
            menuName: "양송이스프",
            menuType: "RICE",
          },
          {
            menuId: "65fa83bf3eb83d319efa85d0",
            menuName: "조랭이떡국",
            menuType: "SOUP",
          },
          {
            menuId: "65fa84933eb83d319efa8742",
            menuName: "삶은감자",
            menuType: "SIDE",
          },
          {
            menuId: "65fa84203eb83d319efa866f",
            menuName: "마늘쫑조림",
            menuType: "SIDE",
          },
        ],
      ],
    },
    {
      dietDay: 5,
      menuList: [
        [
          {
            menuId: "65fa83f63eb83d319efa85dd",
            menuName: "야채죽",
            menuType: "RICE",
          },
          {
            menuId: "65fa83bf3eb83d319efa85bb",
            menuName: "곰국",
            menuType: "SOUP",
          },
          {
            menuId: "65fa84203eb83d319efa8664",
            menuName: "고등어김치조림",
            menuType: "SIDE",
          },
          {
            menuId: "65fa84203eb83d319efa865c",
            menuName: "가자미조림",
            menuType: "SIDE",
          },
        ],
      ],
    },
    {
      dietDay: 6,
      menuList: [
        [
          {
            menuId: "65fa83bf3eb83d319efa85da",
            menuName: "닭죽",
            menuType: "RICE",
          },
          {
            menuId: "65fa83bf3eb83d319efa85c0",
            menuName: "닭곰탕",
            menuType: "SOUP",
          },
          {
            menuId: "65fa84ba3eb83d319efa8798",
            menuName: "참치계란말이",
            menuType: "SIDE",
          },
          {
            menuId: "65fa84203eb83d319efa8686",
            menuName: "숙주나물",
            menuType: "SIDE",
          },
        ],
      ],
    },
  ],
};

const add = {
  status: 200,
  data: [
    {
      menuId: "65fa83f63eb83d319efa85ea",
      menuName: "당근죽",
      menuType: "RICE",
    },
    {
      menuId: "65fa83bf3eb83d319efa85a2",
      menuName: "달걀국",
      menuType: "SOUP",
    },
    {
      menuId: "65fa84203eb83d319efa8683",
      menuName: "시금치나물",
      menuType: "SIDE",
    },
    {
      menuId: "65fa83f63eb83d319efa860b",
      menuName: "돈사태찜",
      menuType: "SIDE",
    },
  ],
};
const dietDetail = {
  status: 200,
  data: [
    {
      menuId: "65fa83f63eb83d319efa85de",
      menuName: "쇠고기죽",
      menuImgUrl:
        "https://lh3.googleusercontent.com/proxy/xbFupek3WsR-4o2kz-XusgwvZ_9C5A8GPL8VQI2oBvGTWdLLNV_624Gky3LgHr_77dGQVRlhgL7rP39sNhSY7n-Huv1bXA",
      menuType: "RICE",
      calorie: 69,
      carbohydrate: 11.8,
      protein: 2.5,
      fat: 1.2,
      materials: [
        "참기름",
        "소고기",
        "재래간장",
        "대파",
        "마늘",
        "쌀",
        "돼지고기",
        "멥쌀",
        "논벼",
      ],
      recipe: [
        "① 쌀을 불린 후, 쇠고기를 볶아 쌀과 함께 넣어 약한불로 장시간 끓인다.",
        "② 국간장, 소금, 마늘, 파, 참기름으로 간을 한다.",
      ],
      need: "쌀, 멥쌀, 논벼, 백미 국내산, 일반형, 일품 15g, 쇠고기, 수입우, 등심 7.5g, 간장, 재래간장 2g, 소금, 식염 0.2g, 파, 대파 1g, 마늘, 구근, 생것 0.5g, 참기름 0.2g, ",
    },
    {
      menuId: "65fa83bf3eb83d319efa85af",
      menuName: "갈비탕",
      menuImgUrl:
        "https://static.wtable.co.kr/image/production/service/recipe/776/5446a21d-56e9-4725-b43a-50ca938566fd.jpg",
      menuType: "SOUP",
      calorie: 133,
      carbohydrate: 5.1,
      protein: 10.8,
      fat: 7.4,
      materials: [
        "전분",
        "소고기",
        "재래간장",
        "대파",
        "돼지고기",
        "당면",
        "조선무",
      ],
      recipe: [
        "① 갈비는 핏물을 충분히 빼준다.",
        "② 적당량의 물에 갈비를 넣고 끓인다.",
        "③ 무와 불린 당면, 파를 넣고 끓인다.",
      ],
      need: "쇠고기, 수입우, 등심 60g, 무, 조선무, 뿌리 12g, 파, 대파 1.5g, 전분, 당면 5g, 간장, 재래간장 2g, ",
    },
    {
      menuId: "65fa83f63eb83d319efa8629",
      menuName: "동태찜",
      menuImgUrl:
        "https://lh6.googleusercontent.com/proxy/4yKmE0lid4u-D1mZTdeZHmXs2Hl2yCINDsao9hSJ07biHTADJ4kI7EqDV-H1iF2iMhtjf81gQXI4A8LsGhFWVx1OJsCHsZmC-n1m",
      menuType: "SIDE",
      calorie: 30,
      carbohydrate: 1.4,
      protein: 4.4,
      fat: 0.7,
      materials: [
        "후추",
        "참기름",
        "명태",
        "마늘",
        "설탕",
        "고추가루",
        "백설탕",
        "조선무",
      ],
      recipe: [
        "① 동태는 먹기 좋게 뼈를 제거하고 포를 떠서 적당한 크기로 토막낸다.",
        "② 무는 한 입 크기로 깍둑썰기한다.",
        "③ 진간장, 고추가루, 설탕, 깨소금, 참기름, 후추가루, 마늘을 섞어 양념장을 만든다.",
        "④ 냄비에 무를 깔고 동태를 올리고 양념장을 골고루 끼얹어 푹 찐다.",
      ],
      need: "명태, 냉동품 동태 25g, 무, 조선무, 뿌리 10g, 고추가루 0.3g, 마늘, 구근, 생것 1g, 간장, 왜간장 2g, 설탕, 백설탕 0.5g, 참기름 0.5g, 깨소금 0.1g, 후추, 분말 0.01g, ",
    },
    {
      menuId: "65fa83f63eb83d319efa8609",
      menuName: "단호박찜",
      menuImgUrl:
        "https://shop.biumfood.com/upload/1596782496image_product156.jpg",
      menuType: "SIDE",
      calorie: 14,
      carbohydrate: 3.3,
      protein: 0.8,
      fat: 0.1,
      materials: ["당호박", "호박"],
      recipe: ["① 단호박을 적당한 크기로 썰어 찜통에서 찐다."],
      need: "호박, 당호박 50g, ",
    },
  ],
};
export { init, add, dietDetail };
