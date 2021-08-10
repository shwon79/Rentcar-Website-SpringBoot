


// phone number length limit
function handleOnInput(el, maxlength) {
    if(el.value.length > maxlength) el.value = el.value.substr(0, maxlength);
}


let rentDate = document.getElementById('rent_date').innerText.split(' ');
let returnDate = document.getElementById('return_date').innerText.split(' ');


// must items
const rentDateNum = rentDate[0];
const rentTime = rentDate[1];
const returnDateNum = returnDate[0];
const returnTime = returnDate[1];
const depositPrice = 1;
const totalPrice = 41564;


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





// Sending Data;
const reserveDone = () => {

    if (customName != '' && phoneNum!='' && depositName!='') {
        let finalDate = {
            'rentDate': rentDateNum,
            'rentTime':  rentTime,
            'returnDate': returnDateNum,
            'returnTime' : returnTime,
            'agree': 1,
            'deposit':depositPrice,
            'depositor': depositName,
            'detail': customDemand,
            'name': customName,
            'phone': phoneNum,
            'total': totalPrice,
            'reservation': 1,
            'day': '1일권',
        }

        console.log(JSON.stringify(finalDate));

        let url = '/campingcar/reserve';
        fetch(url, {
            method: 'POST',
            headers:{
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(finalDate),

        }).then(console.log)
            /*
            .then(response => console.log('Success: ', JSON.stringify(response)))
            .then(()=> {
                alert('예약이 완료되었습니다!');
                window.location.href = '/europe';
            })
            .catch(err => console.error('Error: ', err))
             */
    } else {
        alert('입력을 완료해주세요!')
    }
}

