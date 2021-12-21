// 렌트 날짜 표기하기
let originalRentStartDate = document.getElementById('original_rent_start_date').innerText;
let displayRentStartDate = document.getElementById('display_result_start_date');

displayRentStartDate.innerText = originalRentStartDate.split('(')[0];

// 최종 결제 금액 표기하기
let totalPrice = document.getElementById('totalPrice').innerText;
let displayPrice = document.getElementById('rentPrice');
let displayVAT = document.getElementById('rentVAT');
let displayTotalPrice = document.getElementById('rentFullPrice');
let displayPrepayPrice = document.getElementById('prepayPrice');

displayPrice.innerText = Math.floor((parseInt(totalPrice/11*10))).toLocaleString() + ' 원';
displayVAT.innerText = Math.floor((parseInt(totalPrice/11))).toLocaleString() + ' 원';
displayTotalPrice.innerText = parseInt(totalPrice).toLocaleString() + ' 원';
displayPrepayPrice.innerText = Math.floor(parseInt(totalPrice/2)).toLocaleString() + ' 원';

// phone number length limit
function handleOnInput(el, maxlength) {
    if(el.value.length > maxlength) el.value = el.value.substr(0, maxlength);
}

/*
// 보증금 값 가져오기
let deposits = 0;
const getData = () => {
    fetch(`/${carType}/getprice`)
        .then(res => res.json())
        .then(result => {
            deposits = result['deposit'];
        })
}
getData();
 */


// must items
// let rentDate = document.getElementById('rent_date');
// let returnDate = document.getElementById('return_date');
// let rentTimeTarget = document.getElementById('rent_time');
// let returnTimeTarget = document.getElementById('return_time');


// const rentDateNum = document.getElementById('rent_date').innerText;
// const rentTime = document.getElementById('rent_time').innerText;
// const returnDateNum = document.getElementById('return_date').innerText;
// const returnTime = document.getElementById('return_time').innerText;
// // const totalPrice = parseInt(document.getElementById('total_price').className);
// const extraFee = parseInt(document.getElementById('extra_fee').className);
//
// let totalHalf = parseInt(totalPrice/2);
// const useDay = document.getElementById('use_day').innerText;
// const extraTime = document.getElementById('extra_time').innerText;


// check onchange of inputs
// let customName = '';
// let phoneNum = '';
// let depositName = '';
// let customDemand = '';
// const inputName = document.getElementById('input_name');
// const inputPhoneNum = document.getElementById('input_number');
// const inputDeposit = document.getElementById('input_deposit');
// const inputDemand = document.getElementById('input_demand');
// inputName.addEventListener('change',
//     function () {
//         customName = inputName.value;
//     })
// inputPhoneNum.addEventListener('change',
//     function () {
//         phoneNum = inputPhoneNum.value;
//     })
// inputDeposit.addEventListener('change',
//     function () {
//         depositName = inputDeposit.value;
//     })
// inputDemand.addEventListener('change',
//     function () {
//         customDemand = inputDemand.value;
//         // console.log(totalPrice)
//         // console.log(totalHalf)
//
//     })

// 요청사항 높이 자동 조절
const autoGrow = (texts) => {
    texts.style.height = "1px";
    texts.style.height = (12 + texts.scrollHeight) + "px";
}

// Sending Data;
function reserveDone() {
    let name, phoneNum, depositName, demand;
    const carType = document.getElementById('carType').innerText;
    const useDay = document.getElementById('useDay').innerText;
    let rentStartDate = document.getElementById('display_result_start_date').innerText;
    let rentStartTime = document.getElementById('display_result_start_time').innerText;
    let rentEndDate = document.getElementById('display_result_end_date').innerText;
    let rentEndTime = document.getElementById('display_result_end_time').innerText;
    let fullPrice = document.getElementById('rentFullPrice').innerText;
    let halfPrice = document.getElementById('prepayPrice').innerText;
    let deposit = document.getElementById('deposit').innerText;
    let inputName = document.getElementById('input_name');
    let inputPhoneNum = document.getElementById('input_phone');
    let inputDeposit = document.getElementById('input_deposit_name');
    let inputDemand = document.getElementById('input_demand');

    let agreement = document.getElementById('check_agreement').checked;
    let phoneWithoutDash = /^\d{11}$/;
    let regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

    name = inputName.value;
    phoneNum = inputPhoneNum.value;
    depositName = inputDeposit.value;
    demand = inputDemand.value;
    fullPrice = fullPrice.split(' ')[0].replace(/,/g, "");
    halfPrice = halfPrice.split(' ')[0].replace(/,/g, "");
    deposit = deposit.split(' ')[0].replace(/,/g, "");

    // 연락처 01011112222 로 작성했을 경우, 010-1111-2222로 처리
    if (phoneWithoutDash.test(phoneNum) == true) {
        phoneNum = phoneNum.substr(0, 3) + "-" + phoneNum.substr(3, 4) + "-" + phoneNum.substr(7,4);
    }

    if (name == '' || phoneNum=='' || depositName=='') {
        alert('예약자 정보 입력을 완료해주세요!')
    } else if (agreement != true) {
        alert('약관에 동의해주세요!')
    } else if (regPhone.test(phoneNum) == false) {
        alert("연락처를 '010-1234-5678' 형식으로 입력해주세요.");
    } else if (name != '' && phoneNum != '' && depositName != '' && agreement == true) {
        let data = {
            carType: carType,
            rentDate: rentStartDate,
            rentTime: rentStartTime,
            returnDate: rentEndDate,
            returnTime: rentEndTime,
            // extraTime : extraTime,
            agree: 1,
            depositor: depositName,
            detail: demand,
            name: name,
            phone: phoneNum,
            total: fullPrice,
            totalHalf: halfPrice,
            deposit: deposit,
            reservation: 0,
            day: useDay
        }
        console.log(data);

        // let reserveConfirm = confirm('예약을 완료하시겠습니까?');
        //
        // if (reserveConfirm) {
        //     let url = '/campingcar/reserve';
        //     fetch(url, {
        //         method: 'POST',
        //         headers: {
        //             'Content-Type': 'application/json'
        //         },
        //         body: JSON.stringify(data),
        //     })
        //         .then(response => response.json())
        //         .then(result => {
        //             if (result[0] == "1") {
        //                 alert('캠핑카 예약 대기 신청이 완료되었습니다.');
        //                 let now = new Date();
        //                 let year = now.getFullYear();
        //                 let month = now.getMonth() + 1;
        //                 window.location.href = '/camping/calendar' + year + '/' + month;
        //             } else if (result[0] == "0") alert('이용할 수 없는 날짜입니다.')
        //         })
        // }
    }
}
// const reserveDone = () => {
//
//
//     // if (customName != '' && phoneNum!='' && depositName!='' && check1 == true ) {
//     //     let finalDate = {
//     //         'carType': carType,
//     //         'rentDate': rentDateNum,
//     //         'rentTime':  rentTime,
//     //         'returnDate': returnDateNum,
//     //         'returnTime' : returnTime,
//     //         'extraTime' : extraTime,
//     //         'agree': 1,
//     //         'deposit':totalHalf,
//     //         'depositor': depositName,
//     //         'detail': customDemand,
//     //         'name': customName,
//     //         'phone': phoneNum,
//     //         'total': totalPrice,
//     //         'totalHalf': totalHalf,
//     //         'reservation': 0,
//     //         'day': useDay,
//     //     }
//     //
//     //     console.log(finalDate);
//     //
//     //     // console.log(finalDate);
//     //     let reserveConfirm = confirm('예약을 완료하시겠습니까?');
//     //     if (reserveConfirm) {
//     //         let url = '/campingcar/reserve';
//     //         fetch(url, {
//     //             method: 'POST',
//     //             headers:{
//     //                 'Content-Type' : 'application/json'
//     //             },
//     //             body: JSON.stringify(finalDate),
//     //         }).then(response => response.json())
//     //             .then(result => {
//     //                 if (result[0] == "1") {
//     //                     alert('캠핑카 예약 대기 신청이 완료되었습니다.')
//     //                     window.location.href = '/europe';
//     //                 } else if (result[0] == "0") alert('이용할 수 없는 날짜입니다.')
//     //             })
//     //             // .catch(err => console.error('Error: ', err))
//     //     }
//     // } else if (customName == '' || phoneNum=='' || depositName=='') {
//     //     alert('입력을 완료해주세요!')
//     // } else if (check1 != true || check2 != true || check3 != true || check4 != true) {
//     //     alert('동의를 완료해주세요!')
//     // }
// }

