// 보증금 숨기기 버튼
function hideDeposit() {
    const depositColumns = [...document.getElementsByClassName('depositColumn')];
    depositColumns.forEach(column => {
        column.classList.toggle('displayNone');
    });
}