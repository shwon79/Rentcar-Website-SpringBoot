


// phone number length limit
function handleOnInput(el, maxlength) {
    if(el.value.length > maxlength) el.value = el.value.substr(0, maxlength);
}


let rentDate = document.getElementById('rent_date').innerText.split(' ');
let returnDate = document.getElementById('return_date').innerText.split(' ');

let deposits = 0;
const getData = () => {
    fetch('/campingcar/getprice')
        .then(res => res.json())
        .then(result => {
            deposits = result['deposit'];
            console.log(result);
        })
}
getData();


// must items
const rentDateNum = rentDate[0]+' '+rentDate[1];
const rentTime = rentDate[2];
const returnDateNum = returnDate[0]+' '+returnDate[1];
const returnTime = returnDate[2];
const totalPrice = parseInt(document.getElementById('total_price').innerText.split('원')[0]);
const totalHalf = totalPrice/2;
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
        console.log(inputName.value);
    })
inputPhoneNum.addEventListener('change',
    function () {
        phoneNum = inputPhoneNum.value;
        console.log(inputPhoneNum.value);
    })
inputDeposit.addEventListener('change',
    function () {
        depositName = inputDeposit.value;
        console.log(inputDeposit.value);
    })
inputDemand.addEventListener('change',
    function () {
        customDemand = inputDemand.value;
        console.log(inputDemand.value);
    })

// 요청사항 높이 자동 조절
const autoGrow = (texts) => {
    texts.style.height = "1px";
    texts.style.height = (12 + texts.scrollHeight) + "px";
}


// Sending Data;
const reserveDone = () => {
    let check1 = document.getElementById('check_info').checked;
    let check2 = document.getElementById('check_rent').checked;
    let check3 = document.getElementById('check_last').checked;

    if (customName != '' && phoneNum!='' && depositName!='' && check1 == true && check2 == true && check3 == true) {
        let finalDate = {
            'rentDate': rentDateNum,
            'rentTime':  rentTime,
            'returnDate': returnDateNum,
            'returnTime' : returnTime,
            'extraTime' : extraTime,
            'agree': 1,
            'deposit':deposits,
            'depositor': depositName,
            'detail': customDemand,
            'name': customName,
            'phone': phoneNum,
            'total': totalPrice,
            'totalHalf': totalHalf,
            'reservation': 1,
            'day': useDay,
        }

        console.log(JSON.stringify(finalDate));

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
                console.log(response);
                return response
            })
                .then(result => {
                    console.log(result);
                    alert('예약이 완료되었습니다!');
                    window.location.href = '/europe';
                })
                // .catch(err => console.error('Error: ', err))
        }
    } else if (customName == '' || phoneNum=='' || depositName=='') {
        alert('입력을 완료해주세요!')
    } else if (check1 != true || check2 != true || check3 != true) {
        alert('동의를 완료해주세요!')
    }
}

