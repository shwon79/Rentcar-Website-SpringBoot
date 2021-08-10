
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
// document.querySelector('.year-month-return').textContent = `${viewYear}년 ${viewMonth + 1}월`;


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
let obj, deposits;
let priceList = [];
function runIt() {
    fetch('/campingcar/getprice')
        .then(res => res.json())
        .then(result => {
            obj = result;
            deposits = obj['deposit'];
            priceList[1] = obj['onedays'];
            priceList[2] = obj['twodays'];
            priceList[3] = obj['threedays'];
            priceList[4] = obj['fourdays'];
            priceList[5] = obj['fivedays'];
            priceList[6] = obj['sixdays'];
            priceList[7] = obj['sevendays'];
            priceList[8] = obj['eightdays'];
            priceList[9] = obj['ninedays'];
            priceList[10] = obj['tendays'];
            priceList[11] = obj['elevendays'];
            priceList[12] = obj['twelvedays'];
            priceList[13] = obj['thirteendays'];
            priceList[14] = obj['fourteendays'];
            priceList[15] = obj['fifteendays'];
            priceList[16] = obj['sixteendays'];
            priceList[17] = obj['seventeendays'];
            priceList[18] = obj['eighteendays'];
            priceList[19] = obj['ninetinedays'];
            priceList[20] = obj['twentydays'];
            priceList[21] = obj['twentyonedays'];
            priceList[22] = obj['twentytwodays'];
            priceList[23] = obj['twentythreedays'];
            priceList[24] = obj['twentyfourdays'];
            priceList[25] = obj['twentyfivedays'];
            priceList[26] = obj['twentysixdays'];
            priceList[27] = obj['twentysevendays'];
            priceList[28] = obj['twentyeightdays'];
            priceList[29] = obj['twentyninedays'];
            priceList[30] = obj['thirtydays'];
        })
}
runIt();


// rent Date
let rentDateNum='';
const rentDate = (id) => {
    rentDateNum = id;
    // document.getElementById(id).id = 'selected-btn';
    console.log(rentDateNum);
}

// time select onclick
let rentTime = '';
let returnTime = '';
const rentTimeSel = (id) => {
    rentTime = id;
    returnTime = rentTime;
    let option1 = document.getElementById('1h');
    let option2 = document.getElementById('2h');
    let option3 = document.getElementById('3h');
    let option4 = document.getElementById('4h');
    let option5 = document.getElementById('5h');
    let option6 = document.getElementById('6h');
    let option7 = document.getElementById('7h');

    // disable 추가시간
    if (rentTime == '10시') {
        option1.disabled = false;
        option2.disabled = false;
        option3.disabled = false;
        option4.disabled = false;
        option5.disabled = false;
        option6.disabled = false;
        option7.disabled = false;
    }
    else if (rentTime == '11시') {
        option1.disabled = false;
        option2.disabled = false;
        option3.disabled = false;
        option4.disabled = false;
        option5.disabled = false;
        option6.disabled = false;
        option7.disabled = true;
    }
    else if (rentTime == '12시') {
        option1.disabled = false;
        option2.disabled = false;
        option3.disabled = false;
        option4.disabled = false;
        option5.disabled = false;
        option6.disabled = true;
        option7.disabled = true;
    }
    else if (rentTime == '13시') {
        option1.disabled = false;
        option2.disabled = false;
        option3.disabled = false;
        option4.disabled = false;
        option5.disabled = true;
        option6.disabled = true;
        option7.disabled = true;
    }
    else if (rentTime == '14시') {
        option1.disabled = false;
        option2.disabled = false;
        option3.disabled = false;
        option4.disabled = true;
        option5.disabled = true;
        option6.disabled = true;
        option7.disabled = true;
    }
    else if (rentTime == '15시') {
        option1.disabled = false;
        option2.disabled = false;
        option3.disabled = true;
        option4.disabled = true;
        option5.disabled = true;
        option6.disabled = true;
        option7.disabled = true;
    }
    else if (rentTime == '16시') {
        option1.disabled = false;
        option2.disabled = true;
        option3.disabled = true;
        option4.disabled = true;
        option5.disabled = true;
        option6.disabled = true;
        option7.disabled = true;
    }
    else if (rentTime == '17시') {
        option1.disabled = true;
        option2.disabled = true;
        option3.disabled = true;
        option4.disabled = true;
        option5.disabled = true;
        option6.disabled = true;
        option7.disabled = true;
    }

    console.log(rentTime);
}

// 몇 일권 select
var returnDateNum = '';
let useDay = ''
let useDayNum = 0;
const daysSelect = () => {
    let daySelector = document.getElementById('days_select');
    let theVal = parseInt(daySelector.options[daySelector.selectedIndex].value);
    let temp = rentDateNum.split('월 ');
    let rentDateMon = parseInt(temp[0]);
    let returnDateMon = rentDateMon;
    let rentDateDay = parseInt(temp[1].split('일')[0]);
    let returnDateDay = rentDateDay + theVal;
    console.log(returnDateDay);
    useDay = daySelector.options[daySelector.selectedIndex].innerText;
    useDayNum = parseInt(daySelector.options[daySelector.selectedIndex].value);
    if (returnDateDay < 29) ;
    else {
        switch (rentDateMon) {
            case 1:
                if (returnDateDay > 31) {
                    returnDateMon = rentDateMon + 1;
                    returnDateDay = rentDateDay - 31;
                }
                break;
            case 2:
                if (returnDateDay > 29) {
                    returnDateMon = rentDateMon + 1;
                    returnDateDay = rentDateDay - 28;
                }
                break;
            case 3:
                if (returnDateDay > 31) {
                    returnDateMon = rentDateMon + 1;
                    returnDateDay = rentDateDay - 31;
                }
                break;
            case 4:
                if (returnDateDay > 30) {
                    returnDateMon = rentDateMon + 1;
                    returnDateDay = rentDateDay - 30;
                }
                break;
            case 5:
                if (returnDateDay > 31) {
                    returnDateMon = rentDateMon + 1;
                    returnDateDay = rentDateDay - 31;
                }
                break;
            case 6:
                if (returnDateDay > 30) {
                    returnDateMon = rentDateMon + 1;
                    returnDateDay = rentDateDay - 30;
                }
                break;
            case 7:
                if (returnDateDay > 31) {
                    returnDateMon = rentDateMon + 1;
                    returnDateDay = rentDateDay - 31;
                }
                break;
            case 8:
                if (returnDateDay > 31) {
                    returnDateMon = rentDateMon + 1;
                    returnDateDay = rentDateDay - 31;
                }
                break;
            case 9:
                if (returnDateDay > 30) {
                    returnDateMon = rentDateMon + 1;
                    returnDateDay = rentDateDay - 30;
                }
                break;
            case 10:
                if (returnDateDay > 31) {
                    returnDateMon = rentDateMon + 1;
                    returnDateDay = rentDateDay - 31;
                }
                break;
            case 11:
                if (returnDateDay > 30) {
                    returnDateMon = rentDateMon + 1;
                    returnDateDay = rentDateDay - 30;
                }
                break;
            case 12:
                if (returnDateDay > 31) {
                    returnDateMon = rentDateMon + 1;
                    returnDateDay = rentDateDay - 31;
                }
                break;
        }
    }
    returnDateNum = `${returnDateMon}월 ${returnDateDay}일`;
    console.log(returnDateNum);
}


// extra time select
let extraTimeNum = 0;
function timeSelect()  {
    let timeSelector = document.getElementById('extratime_select');
    let theVal = parseInt(timeSelector.options[timeSelector.selectedIndex].value);
    extraTimeNum = theVal
    console.log(theVal);
    let temp = parseInt(rentTime.split('시')[0]) + theVal;
    returnTime = `${temp}시`;

}


// price calculator
const calculateDate = () => {
    console.log(priceList);
    console.log(useDayNum);
    let showData = document.getElementById('calResult');
    let price = parseInt(priceList[useDayNum]) + (40000*extraTimeNum);
    showData.innerText = `${price}원`
}



// phone number length limit
function handleOnInput(el, maxlength) {
    if(el.value.length > maxlength) el.value = el.value.substr(0, maxlength);
}


// Sending Data;
const postDate = () => {

    // customName != '' && phoneNum!='' &&
    if (rentDateNum!='' && rentTime!='' && returnDateNum!='' && returnTime!='') {

        alert('예약 창으로 넘어갑니다.')
        window.location.href = `/campingcar/reserve/${rentDateNum}/${rentTime}/${returnDateNum}/${returnTime}`

    } else {
        alert('입력을 완료해주세요!')
    }
}

