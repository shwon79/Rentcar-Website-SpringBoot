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
            url: '/camping/review',
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
};

// 새로운 이미지 첨부
function editNewReviewImage() {
    let newFiles = document.getElementById('addImage').files;
    let reviewId = document.getElementById('reviewId').innerText;
    let formData = new FormData();

    if (newFiles.length == 0) {
        alert('새로운 이미지를 선택해주세요.')
    } else {
        formData.append('reviewId', reviewId);
        for (let i = 0; i < newFiles.length; i++) {
            formData.append('file', newFiles[i]);
        };

        if ('이미지를 추가하시겠습니까?') {
            $.ajax({
                enctype: 'multipart/form-data',
                cache: false,
                type: 'POST',
                url: '/camping/review/image',
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
};

// 리뷰 수정에서 이미지 하나씩 삭제
function deleteOneImage(imageId) {
    $.ajax({
        type:'DELETE',
        url:'/camping/review/image/'+ imageId,
        dataType:'text',
        contentType : 'application/json; charset=utf-8'
    }).done(function () {
        location.reload();
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
};

// 리뷰 수정에서 비디오 삭제
function deleteOneVideo(reviewId) {
    $.ajax({
        type:'DELETE',
        url:'/camping/review/video/'+ reviewId,
        dataType:'text',
        contentType : 'application/json; charset=utf-8'
    }).done(function () {
        location.reload();
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
};

// 리뷰 수정에서 비디오 추가
function editNewReviewVideo() {
    let existingVideo = document.getElementById('existing_video').innerText;

    let newFiles = document.getElementById('addVideo').files;

    let reviewId = document.getElementById('reviewId').innerText;
    let formData = new FormData();

    if (existingVideo != '') {
        alert('동영상은 최대 1개까지만 업로드 할 수 있습니다.');
    } else {
        if (newFiles.length == 0) {
            alert('새로 추가할 동영상을 선택해주세요.')
        } else {
            formData.append('file', newFiles[0]);

            formData.append('carName', '');
            formData.append('title', 0);
            formData.append('url', '');
            formData.append('isUploaded', '');
            formData.append('isMain', '');

            $.ajax({
                enctype: 'multipart/form-data',
                cache: false,
                type: 'PUT',
                url: '/camping/review/video/' + reviewId,
                processData:false,
                contentType: false,
                data: formData
            }).done(function () {
                alert('새로운 동영상이 추가 되었습니다.');
                location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        };
    }
};

// 리뷰 수정하기 기능
function editReview(reviewId) {
    let nickName = document.getElementById('editNickName').value;
    let password = document.getElementById('editPassword').value;
    let carName = document.getElementById('editCarName').value;
    let startDate = document.getElementById('editStartDate').value;
    let endDate = document.getElementById('editEndDate').value;
    let text = document.getElementById('editText').value;
    let carType = document.getElementById('carType').innerText;

    let data = {
        nickName: nickName,
        password: password,
        carName: carName,
        startDate: startDate,
        endDate: endDate,
        text: text
    };

    if (confirm('리뷰를 수정하시겠습니까?')) {
        $.ajax({
            type:'PUT',
            url:'/camping/review/text/' + reviewId,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('리뷰 수정이 완료되었습니다.');
            } else if (result.result == 0) {
                alert('리뷰 수정에 문제가 생겼습니다.');
            };
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
};

