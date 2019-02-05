function formatDay() {
    let d = new Date(),
        currentYear = d.getFullYear(),
        currentMonth = '' + d.getMonth() + 1,
        currentDay = '' + d.getDate();
    if (currentDay.length < 2) currentDay = '0' + currentDay;
    return [currentYear, currentMonth, currentDay].join('-');
}