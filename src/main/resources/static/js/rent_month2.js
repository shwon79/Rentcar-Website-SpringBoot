let sortType = 'asc';

function sortContent(index) {
    let table = document.getElementsByClassName('table_expected')

    sortType = (sortType == 'asc') ? 'desc' : 'asc';

    let checkSort = true;
    let rows = table[0].rows;

    while (checkSort) { // 현재와 다음만 비교하기때문에 위치변경되면 다시 정렬해준다.
        checkSort = false;

        for (let i = 1; i < (rows.length - 1); i++) {
            let fCell = rows[i].cells[index].innerText.toUpperCase();
            let sCell = rows[i + 1].cells[index].innerText.toUpperCase();

            let row = rows[i];

            // 오름차순<->내림차순
            if ((sortType == 'asc' && fCell > sCell) || (sortType == 'desc' && fCell < sCell)) {
                row.parentNode.insertBefore(row.nextSibling, row);
                checkSort = true;
            }
        }
    }
}

function sendData(){
    document.getElementById('select_wrapper').submit();
}


// 월렌트실시간 상담요청
function make_monthly_rent_reservation () {

    if (document.getElementById("reservation-simple-name").value == ""){
        alert('성함을 입력해주세요.')
        return
    }

    if (document.getElementById("reservation-simple-phone").value == ""){
        alert('전화번호를 입력해주세요.')
        return
    }


    var data = {
        name : $("#reservation-simple-name").val(),
        phoneNo : $("#reservation-simple-phone").val(),
        detail : $("#reservation-simple-details").val(),
        title : "월렌트실시간",
        car_name : document.getElementsByClassName("carName")[0].innerHTML,
        mileage : document.getElementsByClassName("carNo")[0].innerHTML,
        option : document.getElementsByClassName("carOld")[0].innerHTML
    };

    var checkbox = document.getElementById("agree")
    if(checkbox.checked) {
        $.ajax({
            type : 'POST',
            url : '/reservation/apply',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('예약 신청이 완료되었습니다.');
            window.location.href = '/index';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    } else{
        alert("개인정보 수집 및 이용에 동의해주세요.");
    }
}

//숫자 사이에 콤마 넣기 시작
const number = document.querySelectorAll(".number");

function numberWithCommas() {
    // console.log(number);
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


// '차량금액의 30% 보증금 지불'일 경우 보증금 표기 하지 않기
const credit = document.getElementById('reservation-detail-credit');
let originalDepositText = document.getElementById('carDeposit');
const realDeposit = originalDepositText.innerText;
const wonText = document.getElementById('displayNone');
let newDepositText = '차량가격의 30%(상담문의)';

function displayDeposit() {
    if (credit.value == '차량 금액의 30% 보증금') {
        originalDepositText.innerText = newDepositText;
        wonText.style.display = 'none';
    } else {
        originalDepositText.innerText = realDeposit.toLocaleString();
        wonText.style.display = 'inline-block';
    }
}


// Sending Data;
const reserveMonthlyRent = () => {

    // 예약 정보 받기
    const carNo = document.getElementById('carNo').innerText;
    const kilometer = document.getElementById('kilometer').innerText;
    const reservationName = document.getElementById('reservation-detail-name').value;
    let reservationPhone = document.getElementById('reservation-detail-phone').value;
    const reservationAge = document.getElementById('reservation-detail-age').value;
    const reservationDate = document.getElementById('reservation-detail-date').value;
    const reservationTime = document.getElementById('reservation-detail-time').value;
    const reservationGuarantee = document.getElementById('reservation-detail-credit').value;
    const reservationDetails = document.getElementById('reservation-detail-details').value;
    const address = document.getElementById('address_kakao').value;
    const addressDetail = document.getElementById('address_kakao_detail').value;
    const carPrice = document.getElementById('carPrice').innerText;
    const carTax = document.getElementById('carTax').innerText;
    const carAmountTotal = document.getElementById('carAmountTotal').innerText;
    const carDeposit = document.getElementById('carDeposit').innerText;
    const rentTerm = document.getElementById('getRentTerm').innerText;
    const costPerKm = document.getElementById('getCostPerKm').innerText;
    const returnDate = document.getElementById('reservation-detail-date_return').value;
    const returnTime = document.getElementById('reservation-detail-time_return').value;
    const reservationStatus = 0;

    let check1 = document.getElementById('check_info').checked;
    let regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
    let phoneWithoutDash = /^\d{11}$/;

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
            costPerKm: costPerKm
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
}


//할인 가격 천원 단위로 반올림

const discountPrice = document.querySelectorAll('.discountStyle .discountPrice');

function roundNumber() {
    for (let i = 0; i < discountPrice.length; i++) {
        let textNum = parseInt(discountPrice[i].innerText);
        let roundedNumber = Math.round(textNum/1000)*1000;
        discountPrice[i].innerText = roundedNumber;
    }
}
$('.discountPrice').ready(roundNumber());


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

    let returnDateString = returnYear + '-' + returnMonth + '-' + returnDate;

    displayReturnDate.value = returnDateString;
    displayReturnTime.value = startTime;
}

// 렌트 기간 선택하면 약정 주행거리 선택 보여주기
// function displaySelectKilometer(e) {
//     let monthKilometer = ["2000km", "2500km", "3000km", "4000km", "기타"];
//     let yearKilometer = ["20000km", "30000km", "40000km", "기타"];
//     let selectKilometer = document.getElementById('selectKilometer');
//
//     if (e.value == "한달") {
//         let displaySelect = monthKilometer;
//     } else if (e.value == "12개월" || e.value == "24개월") {
//         let displaySelect = yearKilometer;
//     };
//
//     // selectKilometer.option.length = 0;
//
//     for (x in displaySelect) {
//         let option = document.createElement('option');
//         option.value = displaySelect[x];
//         selectKilometer.appendChild(option);
//     };
// }
