const dayDetail = (id) => {
    // 윗 부분
    const restBtn = document.getElementsByClassName('able_day')
    const targetBtn = document.getElementById(id);
    for (const restBtnElement of restBtn) {
        restBtnElement.style.background = 'aliceblue'
    }
    targetBtn.style.background = 'rgb(32 151 255)';

    // 아래 부분
    const restDetails = document.getElementsByClassName('detail_wrapper');
    for (const restDetail of restDetails) {
        restDetail.style.display = 'none'
    }

    const target = document.getElementById(id.split('day')[0]);
    target.style.display = 'flex'

}