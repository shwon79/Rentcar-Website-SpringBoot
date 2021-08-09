
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
let obj, deposits, price2, price3, price4, price5, price6, price7, price8, price9, price10,
    price11, price12, price13, price14, price15, price16, price17, price18, price19, price20,
    price21, price22, price23, price24, price25, price26, price27, price28, price29, price30;


function runIt() {
    fetch('/campingcar/getprice')
        .then(res => res.json())
        .then(result => {
            obj = result;
            deposits = obj['deposit'];
            // oneDay = obj['onedays'];
            price2 = obj['twodays'];
            price3 = obj['threedays'];
            price4 = obj['fourdays'];
            price5 = obj['fivedays'];
            price6 = obj['sixdays'];
            price7 = obj['sevendays'];
            price8 = obj['eightdays'];
            price9 = obj['ninedays'];
            price10 = obj['tendays'];
            price11 = obj['elevendays'];
            price12 = obj['twelvedays'];
            price13 = obj['thirteendays'];
            price14 = obj['fourteendays'];
            price15 = obj['fifteendays'];
            price16 = obj['sixteendays'];
            price17 = obj['seventeendays'];
            price18 = obj['eighteendays'];
            price19 = obj['ninetinedays'];
            price20 = obj['twentydays'];
            price21 = obj['twentyonedays'];
            price22 = obj['twentytwodays'];
            price23 = obj['twentythreedays'];
            price24 = obj['twentyfourdays'];
            price25 = obj['twentyfivedays'];
            price26 = obj['twentysixdays'];
            price27 = obj['twentysevendays'];
            price28 = obj['twentyeightdays'];
            price29 = obj['twentyninedays'];
            price30 = obj['thirtydays'];
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
const rentTimeSel = (id) => {
    rentTime = id;
    console.log(rentTime);
}

// rent days select
var returnDateNum = '';
let daysSelect = () => {
    let daySelector = document.getElementById('days_select');
    let theVal = parseInt(daySelector.options[daySelector.selectedIndex].value);
    let temp = rentDateNum.split('월 ');
    let rentDateMon = parseInt(temp[0]);
    let returnDateMon = rentDateMon;
    let rentDateDay = parseInt(temp[1].split('일')[0]);
    console.log(rentDateDay);
    let returnDateDay = rentDateDay + theVal;
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
    returnDateNum = `${returnDateMon}월 ${returnDateDay}일`
}


// extra time select
let returnTime = '';
function timeSelect()  {
    let timeSelector = document.getElementById('extratime_select');
    let theVal = parseInt(timeSelector.options[timeSelector.selectedIndex].value);
    console.log(theVal);
    let temp = parseInt(rentTime.split('시')[0]) + theVal;
    returnTime = `${temp}시`;

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
    // console.log(monthly);
    // console.log(fifteenDay);
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

    } else {
        alert('입력을 완료해주세요!')
    }
}

