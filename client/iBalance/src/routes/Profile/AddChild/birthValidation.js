const birthValidation = (year, month, day) => {
  // 년 월 일로 만나이 계산해서 3~8세까지만 통과
  let today = new Date();
  let currentYear = today.getFullYear();
  let currentMonth = today.getMonth() + 1;
  let currentDay = today.getDate();
  let age = currentYear - year;
  year = Number.parseInt(year);
  month = Number.parseInt(month);
  day = Number.parseInt(day);
  if (year === 0 || month <= 0 || month > 12 || day > 31 || day <= 0) {
    return false;
  }
  if (currentMonth < month) {
    age -= 1;
  } else if (currentMonth === month && currentDay < day) {
    age -= 1;
  }
  if (age === 0 && currentMonth - month < 6) {
    return false;
  } else {
    return 3 <= age && age <= 8 ? true : false;
  }
};

export default birthValidation;
