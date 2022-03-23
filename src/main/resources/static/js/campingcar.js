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

// 리뷰쓰기 버튼 누르면 리뷰 폼 보이기
function displayReviewBox() {
    document.getElementById('openReviewBtn').classList.toggle('active');
    document.getElementById('writeReviewBox').classList.toggle('active');
};

// 리뷰 등록하기 버튼
function submitReview() {
    let reviewName = document.getElementById('reviewName').value;
    let reviewPassword = document.getElementById('reviewPassword').value;
    let reviewCarType = document.getElementById('reviewCarType').value;
    let reviewRentStartDate = document.getElementById('reviewRentStartDate').value;
    let reviewRentEndDate = document.getElementById('reviewRentEndDate').value;
    let reviewText = document.getElementById('reviewText').value;
    let reviewImage = document.getElementById('reviewImage').files;
    let reviewVideo = document.getElementById('reviewVideo').files;

    let formDataWrapper = new FormData();

    if (reviewVideo.length > 1) {
        // 동영상 최대 갯수 1개
        alert('동영상 첨부는 최대 1개까지 가능합니다.');
    } else if (reviewImage.length > 10) {
        // 사진 최대 갯수 10개
        alert('이미지 첨부는 최대 10장까지 가능합니다.');
    } else if (reviewName === '' || reviewPassword === '' || reviewCarType === '' || reviewRentStartDate === '' || reviewRentEndDate === '' || reviewText === '') {
        alert('필수 입력 내용을 빠짐없이 작성해주세요.');
    } else if (reviewName !== '' && reviewPassword !== '' && reviewCarType !== '' && reviewRentStartDate !== '' && reviewRentEndDate !== '' && reviewText !== '') {
        formDataWrapper.append('carName', reviewCarType);
        formDataWrapper.append('text', reviewText);
        formDataWrapper.append('nickName', reviewName);
        formDataWrapper.append('startDate', reviewRentStartDate);
        formDataWrapper.append('endDate', reviewRentEndDate);
        for (let i = 0; i < reviewImage.length; i++) {
            formDataWrapper.append('file', reviewImage[i]);
        }
        formDataWrapper.append('video', reviewVideo[0]);
        formDataWrapper.append('password', reviewPassword);
        if (confirm('리뷰를 등록 하시겠습니까?')) {
            postReview(formDataWrapper);
        };
    };

    function postReview(data) {
        $.ajax({
            enctype: 'multipart/form-data',
            cache: false,
            type: 'POST',
            url: '/camping/calendar/review',
            processData:false,
            contentType: false,
            data: data
        }).done(function () {
            alert('리뷰가 등록되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };
}

// 비밀번호 확인
function checkPassword(id) {
    const password = [...document.getElementsByClassName('password')].find(ele => ele.dataset.id == id).innerText;
    let enterPassword = prompt(`비밀번호를 입력하세요.`, '');

    if (enterPassword === password) {
        return true;
    } else {
        return false;
    };
};

// 리뷰 수정하기 창 띄우기
function displayReviewEditBox(type) {
    let modal = document.getElementById('reviewEditModal');
    // let span = document.getElementsByClassName("modal_close_btn")[0];

    if (type == 'open') {
        modal.style.display = "block";
    } else if (type == 'close') {
        modal.style.display = "none";
        sessionStorage.setItem('camping_review_edit_open', '0');
        sessionStorage.setItem('review_id', '0');
    };
};

// 리뷰 수정하기 창에 기존 입력 값 보여주기
function displayExistingValue(id) {
    let nickName = [...document.getElementsByClassName('nickName')].find(ele => ele.dataset.id == id).innerText;
    let password = [...document.getElementsByClassName('password')].find(ele => ele.dataset.id == id).innerText;
    let carName = [...document.getElementsByClassName('carName')].find(ele => ele.dataset.id == id).innerText;
    let startDate = [...document.getElementsByClassName('startDate')].find(ele => ele.dataset.id == id).innerText;
    let endDate = [...document.getElementsByClassName('endDate')].find(ele => ele.dataset.id == id).innerText;
    let text = [...document.getElementsByClassName('text')].find(ele => ele.dataset.id == id).innerText;
    let imageUrlList = [...document.getElementsByClassName('imgUrl')].filter(ele => ele.dataset.id == id);
    let selectedImageUrlList = [];

    for(let i = 0; i < imageUrlList.length; i++) {
        selectedImageUrlList.push(imageUrlList[i]);
    };

    document.getElementById('editNickName').value = nickName;
    document.getElementById('editPassword').value = password;
    document.getElementById('editCarName').value = carName;
    document.getElementById('editStartDate').value = startDate;
    document.getElementById('editEndDate').value = endDate;
    document.getElementById('editText').value = text;
    document.getElementById('reviewIdForEdit').innerText = id;

    document.getElementsByClassName('existing-image-wrapper')[0].innerHTML = '';
    document.getElementsByClassName('existing-image-wrapper')[0].style.marginBottom = '20px';

    if (selectedImageUrlList.length == 0) {
        document.getElementsByClassName('existing-image-wrapper')[0].innerHTML = `
         <p style="text-align: center; width: 100%; font-size: 14px;">없음</p>
        `;

        document.getElementsByClassName('existing-image-wrapper')[0].style.border = '1px solid lightgray';
        document.getElementsByClassName('existing-image-wrapper')[0].style.padding = '10px';
    } else {
        for(let i = 0; i < selectedImageUrlList.length; i++) {
            let imageDiv = document.createElement('div');
            imageDiv.classList.add('reviewEditImageDiv');

            let button = document.createElement('button');
            let buttonText = document.createTextNode('X');
            button.appendChild(buttonText);
            button.classList.add('delete_image_button');
            button.onclick = function clickedDelete() {
                deleteOneImage(selectedImageUrlList[i].dataset.imageId, selectedImageUrlList[i].dataset.id);
            };

            imageDiv.append(button);

            let image = document.createElement('img');
            image.src = `${selectedImageUrlList[i].src}`;
            imageDiv.append(image);

            document.getElementsByClassName('existing-image-wrapper')[0].append(imageDiv);
            document.getElementsByClassName('existing-image-wrapper')[0].style.border = 'none';
            document.getElementsByClassName('existing-image-wrapper')[0].style.padding = '0px';
        }
    }


}

// 새로운 이미지 첨부
function editNewReviewImage() {
    let newFiles = document.getElementById('addImage').files;
    let reviewId = document.getElementById('reviewIdForEdit').innerText;
    let formData = new FormData();

    if (newFiles.length == 0) {
        alert('새로운 이미지를 선택해주세요.')
    } else {
        formData.append('reviewId', reviewId);
        for (let i = 0; i < newFiles.length; i++) {
            formData.append('file', newFiles[i]);
        };

        sessionStorage.setItem('camping_review_edit_open', '1');
        sessionStorage.setItem('review_id', reviewId);

        if ('이미지를 추가하시겠습니까?') {
            $.ajax({
                enctype: 'multipart/form-data',
                cache: false,
                type: 'POST',
                url: '/camping/calendar/review/image',
                processData:false,
                contentType: false,
                data: formData
            }).done(function () {
                alert('새로운 이미지가 추가 되었습니다.');
                location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        };
    };

}

// 리뷰 수정에서 이미지 하나씩 삭제
function deleteOneImage(imageId, reviewId) {
    if (confirm('이 이미지를 삭제하시겠습니까?')) {

        sessionStorage.setItem('camping_review_edit_open', '1');
        sessionStorage.setItem('review_id', reviewId);

        $.ajax({
            type:'DELETE',
            url:'/camping/calendar/review/image/'+ imageId,
            dataType:'text',
            contentType : 'application/json; charset=utf-8'
        }).done(function () {
            alert('삭제가 완료되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    };
};

// 리뷰 수정하기 버튼
function editReview(id) {
    let resultCheckPassword = checkPassword(id);

    if (resultCheckPassword) {
        displayReviewEditBox('open');
        displayExistingValue(id);
    } else {
        alert('비밀번호가 틀렸습니다.');
   };
};

// 리뷰 삭제하기 버튼
function deleteReview(id) {
    let resultCheckPassword = checkPassword(id);

    if (resultCheckPassword) {

    } else {
        alert('비밀번호가 틀렸습니다.');
    };

        // $.ajax({
    //     type:'DELETE',
    //     url:'/admin/rentcar/price/'+ id,
    //     dataType:'json',
    //     contentType : 'application/json; charset=utf-8',
    // }).done(function (result) {
    //     if (result.result == 1) {
    //         alert('삭제 되었습니다.');
    //     } else if (result.result == 0) {
    //         alert('삭제에 문제가 생겼습니다.');
    //     };
    //     location.reload();
    // }).fail(function (error) {
    //     alert(JSON.stringify(error));
    // })
}

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