
// Date 객체 생성
const date = new Date();

const viewYear = date.getFullYear();
const viewMonth = date.getMonth();

// year-month 채우기
document.querySelector('.year-month').textContent = `${viewYear}년 ${viewMonth + 1}월`;


function doDisplay(){
    var con = document.getElementById("calendar_rental")
    if(con.style.display==='none'){
        con.style.display = 'block';
    } else {
        con.style.display = 'none';
    }
}
function doDisplay_rent_time(){
    var con = document.getElementById("calendar_rental_time")
    if(con.style.display==='none'){
        con.style.display = 'block';
    } else {
        con.style.display = 'none';
    }
}

function doDisplay_return(){
    var con = document.getElementById("calendar_return")
    if(con.style.display==='none'){
        con.style.display = 'block';
    } else {
        con.style.display = 'none';
    }
}

function doDisplay_return_time(){
    var con = document.getElementById("calendar_return_time")
    if(con.style.display==='none'){
        con.style.display = 'block';
    } else {
        con.style.display = 'none';
    }
}

// year-month 채우기
document.querySelector('.year-month-return').textContent = `${viewYear}년 ${viewMonth + 1}월`;


// show current date
let today = new Date();
let todayFull = today.toLocaleDateString();
let todayDay = '';
switch (today.getDay()) {
        case 0 :
            todayDay = '일';
            break;
        case 1 :
            todayDay = '월';
            break;
        case 2 :
            todayDay = '화';
            break;
        case 3 :
            todayDay = '수';
            break;
        case 4 :
            todayDay = '목';
            break;
        case 5 :
            todayDay = '금';
            break;
        case 6 :
            todayDay = '토';
            break;
}
document.getElementById('rent_date').innerText = `${todayFull}(${todayDay})`;


// 가격표
let obj;

let deposits;
let oneDay;
let fourDay;
let fiveDay;
let sevenDay;
let tenDay;
let fifteenDay;
let monthly;

function runIt() {
    fetch('/campingcar/getprice')
        .then(res => res.json())
        .then(result => {
            obj = result;
            deposits = obj['deposit'];
            monthly = obj['monthly'];
            oneDay = obj['onedays'];
            fourDay = obj['fourdays'];
            fiveDay = obj['fivedays'];
            sevenDay = obj['sevendays'];
            tenDay = obj['tendays'];
            fifteenDay = obj['fifteendays'];
        })
}
runIt();


// rent Date
let rentDateNum='';
const rentDate = (id) => {
    rentDateNum = id;
    // document.getElementById(id).id = 'selected-btn';
    console.log(id);
}

// return date
let returnDateNum='';
const returnDate = (id) => {
    returnDateNum = id;
    console.log(id)
}

// time select onclick
let rentTime = '';
const rentTimeSel = (id) => {
    console.log(id);
    rentTime = id;
}

// return time select onclick
let returnTime = '';
const returnTimeSel = (id) => {
    console.log(id);
    returnTime = id;
}


// calendar calculate
const calendarCal = (month1, day1, month2, day2) => {
    console.log(month1);
    /*
    switch (month1) {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
    }
    */
}

// price calculator
const calculateDate = () => {
    let date1 = rentDateNum.split('월');
    let date2 = returnDateNum.split('월');
    let month1 = date1[0];
    let day1 = date1[1][0];
    let month2 = date2[0];
    let day2 = date2[1][0];
    if ((month2 - month1) < 2) calendarCal(month1, day1, month2, day2);
    let monthDiffer = month2-month1;
    let dayDiffer = day2 - day1;
    let dateDiffer = 0;
    if(monthDiffer > 0) {
        if (dayDiffer > 0) dateDiffer = monthDiffer+1;
        else if (dateDiffer <= 0) dateDiffer = monthDiffer;
    } else if(monthDiffer == 0) dateDiffer = 1;

    // console.log(dateDiffer);
    console.log(obj);
    console.log(monthly);
    console.log(fifteenDay);
    document.getElementById('calResult').innerText = `${dateDiffer}달`;
    return dateDiffer;
}

// show total selection
let differ = '';
if (rentDateNum!='' && rentTime!='' && returnDateNum!='' && returnTime!='') {
    const target = document.getElementById('total_result');
    differ = calculateDate();
    target.innerText = `${differ}달`;
}



// phone number length limit
function handleOnInput(el, maxlength) {
    if(el.value.length > maxlength) el.value = el.value.substr(0, maxlength);
}

// check onchange of inputs
let customName = '';
let phoneNum = '';
const inputName = document.getElementById('input_name');
const inputPhoneNum = document.getElementById('input_number');
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


// Sending Data;
const postDate = () => {

    // customName != '' && phoneNum!='' &&
    if (rentDateNum!='' && rentTime!='' && returnDateNum!='' && returnTime!='') {

        alert('예약 창으로 넘어갑니다.')
        window.location.href = `/campingcar/reserve/${rentDateNum}/${rentTime}/${returnDateNum}/${returnTime}`

        /*
         let finalDate = {
             // 'name': customName,
             // 'phoneNum': phoneNum,
             'monthDiffer' : differ,
             'rentDate': rentDateNum,
             'rentTime':  rentTime,
             'returnDate': returnDateNum,
             'returnTime' : returnTime,
         }
         */

        /*
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
         */
    } else {
        alert('입력을 완료해주세요!')
    }
}

