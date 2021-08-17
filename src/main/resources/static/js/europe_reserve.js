
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


// 클릭시 날짜 자동 생성
// let takeDate = document.getElementById('rent_date').name;
// document.getElementById('rent_date').innerText = `${todayFull}(${todayDay})`;


// 가격표
let obj, deposits;
let carType = document.getElementsByClassName('car_type')[0].id;
let season;
let priceList = [];
const runIt = () => {
    let target = document.getElementById(rentDateNum);
    season = target.title;
    if (season == '성수기') season = 1;
    else if (season == '비성수기') season = 0;

    fetch(`/${carType}/getprice/${season}`)
        .then(res => res.json())
        .then(result => {
            obj = result;
            // deposits = parseInt(obj['deposit']);
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
            // console.log(obj)
        })
}


// select rent date
let rentDateNum='';
const sendRentDate = (id, year, wDay) => {
    rentDateNum = id;
    let rentDateYear = year;
    let rentDateMonth = rentDateNum.split('월 ')[0];
    let rentDateDay = rentDateNum.split('월 ')[1].split('일')[0];
    let url = `/${carType}/sendrentdate`;

    // 요일 한글화 swtich
    let whichDay = '';
    switch (wDay) {
        case '0':
            whichDay = '일';
            break;
        case '1':
            whichDay = '월';
            break;
        case '2':
            whichDay = '화';
            break;
        case '3':
            whichDay = '수';
            break;
        case '4':
            whichDay = '목';
            break;
        case '5':
            whichDay = '금';
            break;
        case '6':
            whichDay = '토';
            break;
    }

    // 선택된 날짜 글자 넣기
    let putTarget = document.getElementById('rent_date');
    let putResult = year+'.'+id.split('월 ')[0]+'.'+id.split(' ')[1].split('일')[0]+'.('+whichDay+')';
    putTarget.innerText = putResult;

    // 그 날짜에 맞는 선택 가능 시간 데이터 받아오기
    fetch(url+'/'+rentDateYear+'/'+rentDateMonth+'/'+rentDateDay)
        .then(res => res.json())
        .then(result => {
            let theWrapper = document.getElementById('rent_time');
            let dayWrapper = document.getElementById('calendar_return')
            let hrTime = document.getElementById('hr_time');

            theWrapper.style.display = 'block';
            hrTime.style.display = 'block';
            dayWrapper.style.display = 'block';

            let allTime = ["10시", "11시", "12시", "13시", "14시", "15시", "16시", "17시"];
            for (const eachTime of allTime) {
                let timeId = document.getElementById(eachTime);
                if (result.includes(eachTime)) timeId.disabled = false;
                else timeId.disabled = true;
            }

            // let targetClasses = document.getElementsByClassName('each_rent_day');
            // let takeClass = [];
            // for (const targetClass of targetClasses) {
            //     if (targetClass.style.color == 'gray') takeClass.push(targetClass);
            // }
            //
            // console.log(takeClass);

            calculateDate();
            // console.log(clickedDate);
        })

    // 선택 불가능한 가까운 날짜 받아오기
    // fetch(`/${carType}/${rentDateYear}/${rentDateMonth}/${rentDateDay}`)
    //     .then(res => res.json())
    //     .then(result => {
    //       console.log(result);
    //
    //
    //     })

    runIt();
}


// time select onclick
let rentTime = '';
let returnTime = '';
const rentTimeSel = (id) => {
    rentTime = id;
    returnTime = rentTime;

    // let option1 = document.getElementById('1h');
    // let option2 = document.getElementById('2h');
    // let option3 = document.getElementById('3h');
    // let option4 = document.getElementById('4h');
    // let option5 = document.getElementById('5h');
    // let option6 = document.getElementById('6h');
    // let option7 = document.getElementById('7h');

    // disable 추가시간
    // if (rentTime == '10시') {
    //     option1.disabled = false;
    //     option2.disabled = false;
    //     option3.disabled = false;
    //     option4.disabled = false;
    //     option5.disabled = false;
    //     option6.disabled = false;
    //     option7.disabled = false;
    // }
    // else if (rentTime == '11시') {
    //     option1.disabled = false;
    //     option2.disabled = false;
    //     option3.disabled = false;
    //     option4.disabled = false;
    //     option5.disabled = false;
    //     option6.disabled = false;
    //     option7.disabled = true;
    // }
    // else if (rentTime == '12시') {
    //     option1.disabled = false;
    //     option2.disabled = false;
    //     option3.disabled = false;
    //     option4.disabled = false;
    //     option5.disabled = false;
    //     option6.disabled = true;
    //     option7.disabled = true;
    // }
    // else if (rentTime == '13시') {
    //     option1.disabled = false;
    //     option2.disabled = false;
    //     option3.disabled = false;
    //     option4.disabled = false;
    //     option5.disabled = true;
    //     option6.disabled = true;
    //     option7.disabled = true;
    // }
    // else if (rentTime == '14시') {
    //     option1.disabled = false;
    //     option2.disabled = false;
    //     option3.disabled = false;
    //     option4.disabled = true;
    //     option5.disabled = true;
    //     option6.disabled = true;
    //     option7.disabled = true;
    // }
    // else if (rentTime == '15시') {
    //     option1.disabled = false;
    //     option2.disabled = false;
    //     option3.disabled = true;
    //     option4.disabled = true;
    //     option5.disabled = true;
    //     option6.disabled = true;
    //     option7.disabled = true;
    // }
    // else if (rentTime == '16시') {
    //     option1.disabled = false;
    //     option2.disabled = true;
    //     option3.disabled = true;
    //     option4.disabled = true;
    //     option5.disabled = true;
    //     option6.disabled = true;
    //     option7.disabled = true;
    // }
    // else if (rentTime == '17시') {
    //     option1.disabled = true;
    //     option2.disabled = true;
    //     option3.disabled = true;
    //     option4.disabled = true;
    //     option5.disabled = true;
    //     option6.disabled = true;
    //     option7.disabled = true;
    // }

    calculateDate();

}

// price calculator
let price = 0;
let totalPrice = 0;
const calculateDate = () => {
    if (returnDateNum != '' && rentDateNum != '' && rentTime != '' && returnTime != '') {
        const optionWrapper = document.getElementById('calResultWrapper')
        optionWrapper.style.display = 'block'
        let showSelections = document.getElementById('selOption');
        showSelections.innerText = `${rentDateNum} ${rentTime}  ➔  ${returnDateNum} ${returnTime}`
        let targetWhole = document.getElementById('calResult');
        let targetFee = document.getElementById('calRentFee');
        let targetDeposit = document.getElementById('calDeposit');
        // 추가시간 포함 총 렌트료
        price = parseInt(priceList[useDayNum]) + (40000*extraTimeNum);
        // 총 결제금액(VAT포함)
        totalPrice = parseInt(price*1.1);
        // 가격 넣어주기
        targetDeposit.innerText = parseInt(totalPrice/11).toLocaleString()+'원'  // 보증금 = 선결제금액
        targetFee.innerText = price.toLocaleString()+'원'
        targetWhole.innerText = totalPrice.toLocaleString()+'원'
    }
}


// 몇 일권 select
var returnDateNum = '';
let useDay = ''
let useDayNum = 0;
const daysSelect = () => {
    let daySelector = document.getElementById('days_select');
    let theVal = parseInt(daySelector.options[daySelector.selectedIndex].value);

    // 날짜 계산  && rentTime != ''
    if (rentDateNum != '') {
        let temp = rentDateNum.split('월 ');
        let rentDateMon = parseInt(temp[0]);
        let returnDateMon = rentDateMon;
        let rentDateDay = parseInt(temp[1].split('일')[0]);
        let returnDateDay = rentDateDay + theVal;
        useDay = daySelector.options[daySelector.selectedIndex].innerText;
        useDayNum = parseInt(daySelector.options[daySelector.selectedIndex].value);
        // console.log(rentDateMon, rentDateDay)
        // console.log(useDay, useDayNum)

        if (returnDateDay < 29) ;
        else if (useDayNum == 30) {  // 한달권 선택시
            returnDateMon = rentDateMon + 1;
            returnDateDay = rentDateDay;
        }
        else {
            switch (rentDateMon) {
                case 1:
                    if (returnDateDay > 31) {
                        returnDateMon = rentDateMon + 1;
                        returnDateDay -= 31;
                    }
                    break;
                case 2:
                    if (returnDateDay > 29) {
                        returnDateMon = rentDateMon + 1;
                        returnDateDay -= 28;
                    }
                    break;
                case 3:
                    if (returnDateDay > 31) {
                        returnDateMon = rentDateMon + 1;
                        returnDateDay -= 31;
                    }
                    break;
                case 4:
                    if (returnDateDay > 30) {
                        returnDateMon = rentDateMon + 1;
                        returnDateDay -= 30;
                    }
                    break;
                case 5:
                    if (returnDateDay > 31) {
                        returnDateMon = rentDateMon + 1;
                        returnDateDay -= 31;
                    }
                    break;
                case 6:
                    if (returnDateDay > 30) {
                        returnDateMon = rentDateMon + 1;
                        returnDateDay -= 30;
                    }
                    break;
                case 7:
                    if (returnDateDay > 31) {
                        returnDateMon = rentDateMon + 1;
                        returnDateDay -= 31;
                    }
                    break;
                case 8:
                    if (returnDateDay > 31) {
                        returnDateMon = rentDateMon + 1;
                        returnDateDay -= 31;
                    }
                    break;
                case 9:
                    if (returnDateDay > 30) {
                        returnDateMon = rentDateMon + 1;
                        returnDateDay -= 30;
                    }
                    break;
                case 10:
                    if (returnDateDay > 31) {
                        returnDateMon = rentDateMon + 1;
                        returnDateDay -= 31;
                    }
                    break;
                case 11:
                    if (returnDateDay > 30) {
                        returnDateMon = rentDateMon + 1;
                        returnDateDay -= 30;
                    }
                    break;
                case 12:
                    if (returnDateDay > 31) {
                        returnDateMon =  1;
                        returnDateDay -= 31;
                    }
                    break;
            }
        }

        returnDateNum = `${returnDateMon}월 ${returnDateDay}일`;
        // console.log(returnDateNum)

    }
    calculateDate();
}


// extra time select
let extraTimeNum = 0;
// const timeSelect = () => {
//     let timeSelector = document.getElementById('extratime_select');
//     let theVal = parseInt(timeSelector.options[timeSelector.selectedIndex].value);
//     extraTimeNum = theVal
//     let temp = parseInt(rentTime.split('시')[0]) + theVal;
//     returnTime = `${temp}시`;
//     calculateDate();
// }

let clickedDate = ''

const doIt = () => {
    // 미리 넣어둔 값 가져오기
    let clickedOne = document.getElementById('rent_date');
    let clickedWhole = clickedOne.className;
    // 필요한 값들 할당
    let clickedYear = clickedWhole.split('년')[0];
    clickedDate = clickedWhole.split('년')[1].split('요일')[0];
    let clickedWDay = clickedWhole.split('요일')[1];
    // sendRentDate 실행해서 시작 날짜 기입해주기
    sendRentDate(clickedDate, clickedYear, clickedWDay);
    // 라디오 버튼 클릭 만들기
    let target = document.getElementById(clickedDate);
    target.checked = true;

}



// 만약 클릭한 곳이 아니라 달력으로 넘어갈시 날짜 표시 X
const seasonTarget = document.getElementById('rent_date');
let condition1 = parseInt(seasonTarget.title);
if (condition1 > 0) doIt();


// // df
// runIt();




// Sending Data;
const postDateEurope = () => {

    // customName != '' && phoneNum!='' &&
    if (rentDateNum!='' && rentTime!='' && returnDateNum!='' && returnTime!='') {

        alert('예약 창으로 넘어갑니다.')
        window.location.href = `/europe_reserve/${rentDateNum}/${rentTime}/${returnDateNum}/${returnTime}/${useDay}/${extraTimeNum}/${totalPrice}`

    } else {
        alert('입력을 완료해주세요!')
    }
}

const postDateLimousine = () => {

    // customName != '' && phoneNum!='' &&
    if (rentDateNum!='' && rentTime!='' && returnDateNum!='' && returnTime!='') {

        alert('예약 창으로 넘어갑니다.')
        window.location.href = `/liomousine_reserve/${rentDateNum}/${rentTime}/${returnDateNum}/${returnTime}/${useDay}/${extraTimeNum}/${totalPrice}`

    } else {
        alert('입력을 완료해주세요!')
    }
}

const postDateTravel = () => {

    // customName != '' && phoneNum!='' &&
    if (rentDateNum!='' && rentTime!='' && returnDateNum!='' && returnTime!='') {

        alert('예약 창으로 넘어갑니다.')
        window.location.href = `/${carType}_reserve/${rentDateNum}/${rentTime}/${returnDateNum}/${returnTime}/${useDay}/${extraTimeNum}/${totalPrice}`

    } else {
        alert('입력을 완료해주세요!')
    }
}


