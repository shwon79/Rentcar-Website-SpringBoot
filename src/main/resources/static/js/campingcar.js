// camping/calendar/cartype_reserve/year/month 예약 기능을 제외한 나머지 기능들

// 데스크탑에서 큰 이미지 아래에 있는 작은 이미지 hover시 크게 보이기
const smallBoxList = [...document.getElementsByClassName('small_image_box')];
smallBoxList.forEach((box) => {
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

// 비밀번호 확인
function checkValidPassword(carType, id, btnType) {
    const password = [...document.getElementsByClassName('password')].find(ele => ele.dataset.id == id).innerText;
    const passwordInput = [...document.getElementsByClassName(`password_input_for_${btnType}`)].find(ele => ele.dataset.id == id).value;

    if (passwordInput === password) {
        if (btnType == 'edit') {
            location.href = '/camping/review/modification/' + carType + '/' + id;
        } else if (btnType == 'delete') {
            deleteReview(id);
        };
    } else {
        alert('비밀번호가 틀렸습니다.');
    };
};

// 리뷰 수정하기 버튼 or 리뷰 삭제하기 버튼 클릭
function clickEditOrDeleteReview(id, type) {
    let targetForm = [...document.getElementsByClassName(`enter_password_form_for_${type}`)].find(ele => ele.dataset.id == id);
    targetForm.style.display = 'block';
};

// 리뷰 삭제하기 버튼
function deleteReview(id) {
    if (confirm('리뷰를 삭제하시겠습니까?')) {
        $.ajax({
            type:'DELETE',
            url:'/camping/review/'+ id,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
        }).done(function (result) {
            if (result.result == 1) {
                alert('리뷰가 삭제 되었습니다.');
            } else if (result.result == 0) {
                alert('리뷰 삭제에 문제가 생겼습니다.');
            };
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
};

// 리뷰 클릭하면 크게 보여지도록
let oneReview = document.getElementsByClassName('one_review');
oneReview && [...oneReview].forEach((review) => {
    review.addEventListener('click', event => {
        if (!event.target.classList.contains('video_part') && !event.target.classList.contains('video_real')) {
            const reviewImageOpen = [...document.getElementsByClassName('review_image_open')];
            const oneReviewClose = [...document.getElementsByClassName('one_review_close')];

            let targetImageBox = reviewImageOpen.find(box => box.dataset.title == event.currentTarget.dataset.id);
            targetImageBox && targetImageBox.classList.toggle('active');

            let targetReview = oneReviewClose.find(review => review.dataset.title == event.currentTarget.dataset.id);
            targetReview && targetReview.classList.toggle('opened');
        };
        changeBtnText(review.dataset.id, false);
    });
});

// 자세히 보기 버튼
function changeBtnText(reviewId, boolean) {
    reviewId = reviewId.toString();
    const targetReview = [...document.getElementsByClassName('one_review_close')].find((review) => review.dataset.title === reviewId);
    const targetOpenBtn = [...document.getElementsByClassName('see_more_btn_open')].find((btn) => btn.dataset.id === reviewId);
    const targetCloseBtn = [...document.getElementsByClassName('see_more_btn_close')].find((btn) => btn.dataset.id === reviewId);

    if (boolean) {
        if (targetReview && targetReview.classList.contains('opened')) {
            targetOpenBtn.style.display = 'block';
            targetCloseBtn.style.display = 'none';
        } else {
            targetOpenBtn.style.display = 'none';
            targetCloseBtn.style.display = 'block';
        }
    } else {
        if (targetReview && targetReview.classList.contains('opened')) {
            if (targetOpenBtn && targetCloseBtn) {
                targetOpenBtn.style.display = 'none';
                targetCloseBtn.style.display = 'block';
            };
        } else {
            if (targetOpenBtn && targetCloseBtn) {
                targetOpenBtn.style.display = 'block';
                targetCloseBtn.style.display = 'none';
            };
        }
    }
};

// 캠핑카 상담신청하기
function campingcar_rent_reservation () {
    if (document.getElementById("reservation-detail-name").value == ""){
        alert('성함을 입력해주세요.')
        return
    };

    if (document.getElementById("reservation-detail-phone").value == ""){
        alert('전화번호를 입력해주세요.')
        return
    };

    let data = {
        name : $("#reservation-detail-name").val(),
        phoneNo : $("#reservation-detail-phone").val(),
        detail : $("#reservation-detail-details").val(),
        title : "캠핑카렌트",
        product :document.getElementById("rentProduct").getAttribute("value")
    };

    // console.log(data);

    let checkbox = document.getElementById("agree")

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
};

// 화면 맨 위로 올라가기 버튼
const mybutton = document.getElementById('toTheTopBtn');
window.onscroll = function() {
    scrollFunction()
};

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