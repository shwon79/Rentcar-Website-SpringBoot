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

// 월렌트실시간 상담요청
function make_monthly_rent_reservation (e) {
    let reservationPhone = $("#reservation-simple-phone").val();


    if (document.getElementById("reservation-simple-name").value == ""){
        alert('성함을 입력해주세요.')
        return
    }

    if (document.getElementById("reservation-simple-phone").value == ""){
        alert('전화번호를 입력해주세요.')
        return
    }


    let regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;


    let data = {
        name : $("#reservation-simple-name").val(),
        phoneNo : reservationPhone,
        detail : $("#reservation-simple-details").val(),
        product: '장기렌트',
        title: '누구나간편',
        category1: '',
        category2: '',
        car_name : '',
        mileage: '',
        deposit: '',
        option: '',
        price: '',
        age_limit: '',
        car_num : '',
        region: '',
        resDate: '',
        carAge: ''
    };

    // console.log(data);

    let check1 = document.getElementById("agree1").checked;
    let check2 = document.getElementById("agree2").checked;
    let check3 = document.getElementById("agree3").checked;

    if (check1 != true || check2 != true || check3 != true) {
        alert("약관에 동의해주세요.");
    } else if (regPhone.test(reservationPhone) == false) {
        alert("연락처를 '010-1234-5678' 형식으로 입력해주세요.");
    } else {
        $.ajax({
            type : 'POST',
            url : '/reservation/apply',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('상담 신청이 완료되었습니다.');
            window.location.href = '/index';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
}