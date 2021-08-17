
// 상품 이름 넣기
let carType = document.getElementsByClassName('car_type')[0].id
let target_car_name = document.getElementById(carType)
target_car_name.innerText = `${carType.toUpperCase()} VIP CAMPER`

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
let rentDate = document.getElementById('rent_date').innerText.split(' ');
let returnDate = document.getElementById('return_date').innerText.split(' ');

const rentDateNum = rentDate[0]+' '+rentDate[1];
const rentTime = rentDate[2];
const returnDateNum = returnDate[0]+' '+returnDate[1];
const returnTime = returnDate[2];
const totalPrice = parseInt(document.getElementById('total_price').className);

let totalHalf = parseInt(totalPrice/2);
const useDay = document.getElementById('use_day').innerText;
const extraTime = document.getElementById('extra_time').innerText;


// check onchange of inputs
let customName = '';
let phoneNum = '';
let depositName = '';
let customDemand = '';
const inputName = document.getElementById('input_name');
const inputPhoneNum = document.getElementById('input_number');
const inputDeposit = document.getElementById('input_deposit');
const inputDemand = document.getElementById('input_demand');
inputName.addEventListener('change',
    function () {
        customName = inputName.value;
    })
inputPhoneNum.addEventListener('change',
    function () {
        phoneNum = inputPhoneNum.value;
    })
inputDeposit.addEventListener('change',
    function () {
        depositName = inputDeposit.value;
    })
inputDemand.addEventListener('change',
    function () {
        customDemand = inputDemand.value;
        // console.log(totalPrice)
        // console.log(totalHalf)

    })

// 요청사항 높이 자동 조절
const autoGrow = (texts) => {
    texts.style.height = "1px";
    texts.style.height = (12 + texts.scrollHeight) + "px";
}


// 결제금액 넣기
let totalTarget = document.getElementById('total_price');
totalTarget.innerText = totalPrice.toLocaleString()+'원'; // 콤마 넣어주기
let halfTarget = document.getElementById('half_price');
halfTarget.innerText = totalHalf.toLocaleString()+'원'; // 콤마 넣어주기
let totalFeeTarget = document.getElementById('total_fee');
totalFeeTarget.innerText = (parseInt((totalPrice/11)*10)).toLocaleString()+'원';
// let depositsTarget = document.getElementById('deposits');
// depositsTarget.innerText = totalHalf.toLocaleString()+'원';


// Sending Data;
const reserveDone = () => {
    let check1 = document.getElementById('check_info').checked;
    let check2 = document.getElementById('check_rent').checked;
    let check3 = document.getElementById('check_last').checked;
    let check4 = document.getElementById('check_insure').checked;

    if (customName != '' && phoneNum!='' && depositName!='' && check1 == true && check2 == true && check3 == true && check4 == true) {
        let finalDate = {
            'carType': carType,
            'rentDate': rentDateNum,
            'rentTime':  rentTime,
            'returnDate': returnDateNum,
            'returnTime' : returnTime,
            'extraTime' : extraTime,
            'agree': 1,
            'deposit':totalHalf,
            'depositor': depositName,
            'detail': customDemand,
            'name': customName,
            'phone': phoneNum,
            'total': totalPrice,
            'totalHalf': totalHalf,
            'reservation': 0,
            'day': useDay,
        }

        // console.log(finalDate);
        let reserveConfirm = confirm('예약을 완료하시겠습니까?');
        if (reserveConfirm) {
            let url = '/campingcar/reserve';
            fetch(url, {
                method: 'POST',
                headers:{
                    'Content-Type' : 'application/json'
                },
                body: JSON.stringify(finalDate),
            }).then(response => {
                return response
            })
                .then(result => {
                    alert('예약 대기신청이 완료되었습니다.');
                    window.location.href = '/europe';
                })
                // .catch(err => console.error('Error: ', err))
        }
    } else if (customName == '' || phoneNum=='' || depositName=='') {
        alert('입력을 완료해주세요!')
    } else if (check1 != true || check2 != true || check3 != true || check4 != true) {
        alert('동의를 완료해주세요!')
    }
}

