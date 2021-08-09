


// phone number length limit
function handleOnInput(el, maxlength) {
    if(el.value.length > maxlength) el.value = el.value.substr(0, maxlength);
}

// must items
const rentDateNum = '';
const rentTime = '';
const returnDateNum = '';
const returnTime = '';
const depositPrice = '';
const totalPrice = '';


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
            'rent_date': rentDateNum,
            'rent_time':  rentTime,
            'return_date': returnDateNum,
            'return_time' : returnTime,
            'agree': 1,
            'deposit':depositPrice,
            'depositor': depositName,
            'detail': customDemand,
            'name': customName,
            'phone': phoneNum,
            'total': totalPrice,
            'reservation': 1,
        }

        console.log(JSON.stringify(finalDate));

        let url = '/campingcar/reserve';
        fetch(url, {
            method: 'POST',
            body: JSON.stringify(finalDate),
            headers:{
                'Content-Type' : 'application/json'
            }
        }).then(res => res.json())
            .then(response => console.log('Success: ', JSON.stringify(response)))
            .then(()=>alert('예약이 완료되었습니다!'))
            .catch(err => console.error('Error: ', err))

    } else {
        alert('입력을 완료해주세요!')
    }
}

