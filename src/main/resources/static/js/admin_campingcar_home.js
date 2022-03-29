// 내용 순서 변경 버튼 클릭_admin/campingcar/home/menu
function displayChangeIndexBox() {
    const btn = document.getElementsByClassName('change-index-btn');
    const changeIndexBox = document.getElementById('changeIndexBox');

    btn[0].classList.toggle('opened');
    changeIndexBox.classList.toggle('opened');
}

// 내용 순서 변경_admin/campingcar/home/menu
function saveChangedRowIndex() {
    console.log(`Not yet`);
}

// 이미지 순서 변경_admin/campingcar/home/detail
function saveChangedImageIndex() {

}