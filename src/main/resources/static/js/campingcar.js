// 캠핑카 상담요청
function campingcar_rent_reservation () {

    if (document.getElementById("reservation-detail-name").value == ""){
        alert('성함을 입력해주세요.')
        return
    }

    if (document.getElementById("reservation-detail-phone").value == ""){
        alert('전화번호를 입력해주세요.')
        return
    }


    var data = {
        name : $("#reservation-detail-name").val(),
        phoneNo : $("#reservation-detail-phone").val(),
        detail : $("#reservation-detail-details").val(),
        title : "캠핑카렌트",
        product :document.getElementById("rentProduct").getAttribute("value")
    };

    console.log(data);

    var checkbox = document.getElementById("agree")
    if(checkbox.checked) {
        $.ajax({
            type : 'POST',
            url : '/reservation/apply',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (res) {
            alert('예약 신청이 완료되었습니다.');
            window.location.href = '/europe';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    } else{
        alert("개인정보 수집 및 이용에 동의해주세요.");
    }
}

const clickedDay = (e) => {
    const restBtn = document.getElementsByClassName('able_radio');

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
    e.style.border = '2px solid royalblue';
    e.style.color = 'royalblue';
    e.style.backgroundColor = 'white';
}

// 모바일 예약하기 버튼 누르면 달력 뜨기
function seeMobileReserve(behavior) {
    const rightColumn = document.getElementById('right_column');
    if (behavior === 'open') {
        rightColumn.classList.add('onMobile');
        localStorage.setItem('modalStatus', behavior);
    } else if (behavior === 'close') {
        rightColumn.classList.remove('onMobile');
        modalStatus = behavior;
        localStorage.setItem('modalStatus', behavior);
    }
}

function checkModal() {
    let modalStatus = localStorage.getItem('modalStatus');
    const rightColumn = document.getElementById('right_column');

    if (modalStatus === 'open') {
        rightColumn.classList.add('onMobile');
    } else {
        rightColumn.classList.remove('onMobile');
    }
}

// window.onload = checkModal();

let smallBox = document.getElementsByClassName('small_image_box');
[...smallBox].forEach((box) => {
    box.addEventListener('mouseover', function() {
        let bigImages = document.getElementsByClassName('item main-item');
        let selectedImage = [...bigImages].filter(image => image.dataset.title === box.dataset.title);
        [...bigImages].forEach((image) => {
            if (image === selectedImage[0]) {
                image.classList.add('active');
            } else {
                image.classList.remove('active');
            };
        });
    });
});

// To the top button
const mybutton = document.getElementById('toTheTopBtn');
window.onscroll = function() {scrollFunction()};

function scrollFunction() {
    if (document.body.scrollTop > 300 || document.documentElement.scrollTop > 300) {
        mybutton.style.display = "block";
    } else {
        mybutton.style.display = "none";
    }
};

function topFunction() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
};