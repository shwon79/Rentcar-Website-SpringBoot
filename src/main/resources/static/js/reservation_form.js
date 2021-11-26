//숫자 사이에 콤마 넣기
let number = document.getElementsByClassName("number");
document.getElementById("pickup-address").style.display = 'none';
document.getElementById("pickup-address-detail").style.display = 'none';

function numberWithCommas() {
    for (let i = 0; i < number.length; i++) {
        if (number[i].innerText === '상담') {
            number[i].innerText = '상담';
        } else {
            const numberWithComma = parseInt(number[i].innerText).toLocaleString();
            number[i].innerText = numberWithComma;
        }
    }
}

$('.number').ready(numberWithCommas());

// Display return date
function displayReturnDate() {
    let startDate = document.getElementById('reservation-detail-date').value;
    let startTime = document.getElementById('reservation-detail-time').value;
    let rentTerm = document.getElementById('getRentTerm').innerText;
    let displayReturnDate = document.getElementById('reservation-detail-date_return');
    let displayReturnTime = document.getElementById('reservation-detail-time_return');
    let returnYear;
    let returnMonth;
    let returnDate;

    startDate = new Date(startDate);

    if (rentTerm == "한달") {
        startDate.setMonth(startDate.getMonth() + 1);
    } else if (rentTerm == "12개월") {
        startDate.setMonth(startDate.getMonth() + 12);
    } else if (rentTerm == "24개월") {
        startDate.setMonth(startDate.getMonth() + 24);
    }

    returnYear = startDate.getFullYear();
    returnMonth = startDate.getMonth() + 1;
    returnDate = startDate.getDate();

    let returnMonthToString = returnMonth.toString();
    let returnDateToString = returnDate.toString();

    if (returnMonthToString.length == 1) {
        returnMonth = "0" + returnMonthToString;
    }
    if (returnDateToString.length == 1) {
        returnDate = "0" + returnDateToString;
    }

    let returnDateString = returnYear + '-' + returnMonth + '-' + returnDate;

    displayReturnDate.value = returnDateString;
    displayReturnTime.value = startTime;
}

function changeSelect(){
    var selectList = document.getElementById("reservation-detail-pickup")
    if(selectList.options[selectList.selectedIndex].value == "배차 신청"){
        document.getElementById("pickup-address").style.display = '';
        document.getElementById("pickup-address-detail").style.display = '';
    } else {
        document.getElementById("pickup-address").style.display = 'none';
        document.getElementById("pickup-address-detail").style.display = 'none';
    }
}


// Sending Data;
const reserveMonthlyRent = () => {

    // 예약 정보 받기
    const carNo = document.getElementById('carNo').innerText;
    const kilometer = document.getElementById('kilometer').innerText;
    const reservationName = document.getElementById('reservation-detail-name').value;
    let reservationPhone = document.getElementById('reservation-detail-phone').value;
    let reservationAge = document.getElementById('reservation-detail-age').value;
    const reservationDate = document.getElementById('reservation-detail-date').value;
    const reservationTime = document.getElementById('reservation-detail-time').value;
    const reservationGuarantee = document.getElementById('reservation-detail-credit').value;
    const reservationDetails = document.getElementById('reservation-detail-details').value;
    const address = document.getElementById('address_kakao').value;
    const addressDetail = document.getElementById('address_kakao_detail').value;
    const carPrice = document.getElementById('carPrice').innerText;
    let carTax = document.getElementById('carTax').innerText;
    let carAmountTotal = document.getElementById('carAmountTotal').innerText;
    const carDeposit = document.getElementById('carDeposit').innerText;
    const rentTerm = document.getElementById('getRentTerm').innerText;
    const costPerKm = document.getElementById('getCostPerKm').innerText;
    const returnDate = document.getElementById('reservation-detail-date_return').value;
    const returnTime = document.getElementById('reservation-detail-time_return').value;
    const carCode = document.getElementById('getCarCode').innerText;
    const pickupPlace = document.getElementById('reservation-detail-pickup').value;
    let reservationStatus = 0;

    let check1 = document.getElementById('check_info').checked;
    let regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
    let phoneWithoutDash = /^\d{11}$/;
    let regAge = /[0-9]{2}([0]\d|[1][0-2])([0][1-9]|[1-2]\d|[3][0-1])/;

    if (carTax!='상담') {
        carTax = parseInt(carTax)*0.1;
    }
    if (carAmountTotal!='상담') {
        carAmountTotal = parseInt(carAmountTotal)*1.1;
    }

    if (phoneWithoutDash.test(reservationPhone) == true) {
        reservationPhone = reservationPhone.substr(0, 3) + "-" + reservationPhone.substr(3, 4) + "-" + reservationPhone.substr(7,4);
    };

    if (reservationName == '' || reservationPhone == '' || reservationAge == '' || reservationDate == '' || reservationTime == '' || reservationGuarantee == '' || address == '' || addressDetail == '') {
        alert('입력을 완료해주세요!')
    } else if (check1 != true) {
        alert('동의를 완료해주세요!')
    } else if (returnDate == '' || returnTime == '') {
        alert('대여 날짜 및 시간을 선택해주세요.')
    } else if (regPhone.test(reservationPhone) == false) {
        alert("연락처를 '010-1234-5678' 형식으로 입력해주세요.");
    } else if (regAge.test(reservationAge) == false) {
        alert("생년월일을 '680101'와 같이 주민등록번호 앞자리 6자리의 형태로 작성해주세요.");
    } else if (reservationName != '' && reservationPhone != '' && reservationAge != '' && reservationDate != '' && reservationTime != '' && reservationGuarantee != '' && address != '' && addressDetail != '' && check1) {
        var data = {
            carNo: carNo,
            kilometer: kilometer,
            reservationName: reservationName,
            reservationPhone: reservationPhone,
            reservationAge: reservationAge,
            reservationDate: reservationDate,
            reservationTime: reservationTime,
            reservationGuarantee: reservationGuarantee,
            reservationDetails: reservationDetails,
            address: address,
            addressDetail: addressDetail,
            carPrice: carPrice,
            carTax: carTax,
            carAmountTotal: carAmountTotal,
            carDeposit: carDeposit,
            reservationStatus : reservationStatus,
            rentTerm: rentTerm,
            costPerKm: costPerKm,
            carCode: carCode,
            pickupPlace: pickupPlace
        }

        console.log(data);

        let reserveConfirm = confirm('예약 신청을 완료하시겠습니까?');

        if (reserveConfirm) {
            $.ajax({
                type:'POST',
                url:'/rent/month/moren/reservation',
                dataType:'json',
                contentType : 'application/json; charset=utf-8',
                data : JSON.stringify(data)
            }).done(function (result) {
                if (result.result == 1) {
                    alert('예약이 완료되었습니다.');
                } else {
                    alert('예약에 문제가 생겼습니다.');
                };
                window.location.href = '/rent/month/new';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        };
    }
};