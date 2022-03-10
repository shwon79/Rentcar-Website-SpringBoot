const dayDetail = (id) => {
    // 윗 부분
    const restBtn = document.getElementsByClassName('able_day')
    const targetBtn = document.getElementById(id);
    for (const restBtnElement of restBtn) {
        if (restBtnElement.classList.contains('double_circle')) {
            restBtnElement.style.backgroundColor = 'royalblue';
            restBtnElement.style.boxShadow = 'inset 0px 0px 0px 5px white';
            restBtnElement.style.border = '2px solid royalblue';
            restBtnElement.style.color = 'white';
        } else {
            restBtnElement.style.boxShadow = 'none';
            restBtnElement.style.border = 'none';
            restBtnElement.style.backgroundColor = 'royalblue';
            restBtnElement.style.color = 'white';
        }
    };
    targetBtn.style.border = '2px solid royalblue';
    targetBtn.style.color = 'royalblue';
    targetBtn.style.backgroundColor = 'white';

    // 아래 부분
    const restDetails = document.getElementsByClassName('detail_wrapper');
    for (const restDetail of restDetails) {
        restDetail.style.display = 'none'
    }

    const target = document.getElementById(id.split('day')[0]);
    target.style.display = 'flex'

}

//지난 달로 못가게 화살표 없애기
const hiddenOnlythisMonth = document.getElementById('hiddenOnlythisMonth');
const curMonth = new Date();
let thisMonthOnCalendar = hiddenOnlythisMonth.dataset.index;
if (curMonth.getMonth()+1 == thisMonthOnCalendar) {
    hiddenOnlythisMonth.style.display = "none";
}

// 6월 달력까지만 보이게
const hiddenFromJuly = document.getElementById('hiddenFromJuly');
if (hiddenFromJuly.dataset.index == '6') {
    hiddenFromJuly.style.display = 'none';
}