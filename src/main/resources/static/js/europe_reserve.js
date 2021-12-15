
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
        con.style.display = 'grid';
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

function toggleImg(id) {
    let before = document.getElementById("headerImg").src;
    let tmp = document.getElementById(id).src;
    document.getElementById("headerImg").src = tmp;
    document.getElementById(id).src = before;
}

// year-month 채우기
// document.querySelector('.year-month-return').textContent = `${viewYear}년 ${viewMonth + 1}월`;


// 클릭시 날짜 자동 생성
// let takeDate = document.getElementById('rent_date').name;
// document.getElementById('rent_date').innerText = `${todayFull}(${todayDay})`;


// 성수기 or 비성수기 가격표 확인
let obj, deposits;
let carType = document.getElementsByClassName('car_type')[0].id;
let season;
let priceList = [];
const runIt = () => {
    let target = document.getElementById(rentDateNum);
    season = target.title;
    if (season == '성수기') season = 1;
    else if (season == '비성수기') season = 0;

    // 가격표 받아오기
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
let rentDateYear='';
let rentDateMonth='';
let rentDateDay='';

const sendRentDate = (id, year, wDay) => {
    rentDateNum = id;
    rentDateYear = year;
    rentDateMonth = rentDateNum.split('월 ')[0];
    rentDateDay = rentDateNum.split('월 ')[1].split('일')[0];
    let url = `/${carType}/sendrentdate`;

    let targetTimes = document.getElementsByName('time1');
    for (const eachTime of targetTimes) {
        eachTime.checked = false;
    }

    rentTime='';
    returnTime='';
    returnDateNum='';
    extraTimeNum=0;

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

            // 대여 시간 선택 띄우기
            let theWrapper = document.getElementById('rent_time');
            let hrTime = document.getElementById('hr_time');

            theWrapper.style.display = 'block';
            hrTime.style.display = 'block';

            // 대여 시작 날짜 & 시간 선택되면 일권 고르기
            if (rentDateNum != '' && rentTime != '') {
                let targetDays = document.getElementById('calendar_return');
                targetDays.style.display = 'block';
            }

            let allTime = ["10시", "11시", "12시", "13시", "14시", "15시", "16시", "17시"];
            for (const eachTime of allTime) {
                let timeId = document.getElementById(eachTime);
                if (result.includes(eachTime)) {
                    timeId.disabled = false;
                    timeId.className = 'able_time'

                }
                else {
                    timeId.disabled = true;
                    timeId.className = 'disabled_time';
                }
            }

            calculateDate();

        })

    let availableDays = 0;

    // 선택 불가능한 가까운 날짜 받아오기
    fetch(`/${carType}/getrentdate/${rentDateYear}/${rentDateMonth}/${rentDateDay}`)
        .then(res => res.json())
        .then(result => {
            // 현재 남아있는 options 없애기
            let targetSelect = document.getElementById('days_select');
            targetSelect.options.length = 0;
            availableDays = result[0];
            makeOptions(availableDays)
        })

    runIt();
}


// make 일권 options on selection

const makeOptions = (days) => {
    for (let i = 0; i<=days; i++) {
        let targetSelect = document.getElementById('days_select');
        let createdOpt = document.createElement("option");
        if (i>30) break;
        if (i == 30) {
            createdOpt.text = '한달권';
            createdOpt.value = i;
        } else if(i == 0) {
            createdOpt.text = '==렌트 일자 선택(2일이상)==';
            createdOpt.value = 0;

        }
        else if (i == 1){
            createdOpt.text = i +'일권(선택 불가)';
            createdOpt.disabled = true;
        } else {
            createdOpt.text = i +'일권';
            createdOpt.value = i;
        }

    targetSelect.appendChild(createdOpt);

    }
}


// time select onclick
let rentTime = '';
let returnTime = '';
const rentTimeSel = (id) => {
    rentTime = id;
    returnTime = rentTime;

    if (rentTime != '' && rentDateNum != '') {
        let targetDays = document.getElementById('calendar_return')
        targetDays.style.display = 'block'

        calculateDate();
    }

    if (rentTime !='' && rentDateNum != '' && returnDateNum != '') {
        getAvailableExtra();
        calculateDate();
    }

}

// price calculator
let price = 0;
let totalPrice = 0;
let extraFee = 0;
const calculateDate = () => {
    if (returnDateNum != '' && rentDateNum != '' && rentTime != '' && returnTime != '') {
        const optionWrapper = document.getElementById('calResultWrapper')
        optionWrapper.style.display = 'block'
        let showSelections1 = document.getElementById('sel_option_output1_1');
        let showSelections2 = document.getElementById('sel_option_output1_2');
        let showSelections3 = document.getElementById('sel_option_output2_1');
        let showSelections4 = document.getElementById('sel_option_output2_2');

        showSelections1.innerText = rentDateNum
        showSelections2.innerText = rentTime
        showSelections3.innerText = returnDateNum
        showSelections4.innerText = returnTime

        let targetWhole = document.getElementById('calResult');
        let targetFee = document.getElementById('calRentFee');
        let targetExtra = document.getElementById('calExtraFee');
        let targetVat = document.getElementById('calVat');


        // 추가시간 포함 총 렌트료

        price = parseInt(priceList[useDayNum]);


        if (season == 1) extraFee = (65000*extraTimeNum);
        else if (season == 0) {
            if (carType == 'liomousine') extraFee = 40000*extraTimeNum;
            else extraFee = 50000*extraTimeNum;
        }

        // 총 결제금액(VAT포함)
        totalPrice = parseInt((price+extraFee)*1.1);
        // 가격 넣어주기
        targetVat.innerText = ((totalPrice)/11).toLocaleString()+'원'  // vat 부가가치세
        targetFee.innerText = price.toLocaleString()+'원'
        targetExtra.innerText = extraFee.toLocaleString()+'원'
        targetWhole.innerText = totalPrice.toLocaleString()+'원'
    }
}


// 몇 일권 select
let returnDateNum = '';
let useDay = ''
let useDayNum = 0;
const daysSelect = () => {
    let daySelector = document.getElementById('days_select');
    let theVal = parseInt(daySelector.options[daySelector.selectedIndex].value);

    // 날짜 계산  && rentTime != ''
    if (rentDateNum != '') {
        let temp = rentDateNum.split('월 ');
        rentDateMonth = parseInt(temp[0]);
        let returnDateMon = rentDateMonth;
        rentDateDay = parseInt(temp[1].split('일')[0]);
        let returnDateDay = rentDateDay + theVal;
        useDay = daySelector.options[daySelector.selectedIndex].innerText;
        useDayNum = parseInt(daySelector.options[daySelector.selectedIndex].value);


        if (returnDateDay < 29) ;
        else if (useDayNum == 30) {  // 한달권 선택시
            returnDateMon = rentDateMonth + 1;
            returnDateDay = rentDateDay;
        }
        else { // 한달권 제외 다른 달로 넘어갈 시
            if (rentDateMonth <= 7) {
                if (rentDateMonth == 2) {
                    if (returnDateDay > 29) {
                        returnDateMon = rentDateMonth + 1;
                        returnDateDay -= 28;
                    }
                } else if (rentDateMonth%2 == 1) {
                    if (returnDateDay > 31) {
                        returnDateMon = rentDateMonth + 1;
                        returnDateDay -= 31;
                    }
                } else if (rentDateMonth%2 == 0) {
                    if (returnDateDay > 30) {
                        returnDateMon = rentDateMonth + 1;
                        returnDateDay -= 30;
                    }
                }
            } else if (rentDateMonth > 7) {
                if (rentDateMonth%2 == 0) {
                    if (returnDateDay > 31) {
                        returnDateMon = rentDateMonth + 1;
                        returnDateDay -= 31;
                    }
                } else if (rentDateMonth%2 == 1) {
                    if (returnDateDay > 30) {
                        returnDateMon = rentDateMonth + 1;
                        returnDateDay -= 30;
                    }
                }
            }

        }


        returnDateNum = `${returnDateMon}월 ${returnDateDay}일`;

        getAvailableExtra();


    }
    calculateDate();
}

let extraTimeTarget = document.getElementById('extra_time_sel')
const getAvailableExtra = () => {
    fetch(`/${carType}/getextratime/${rentDateYear}/${rentDateMonth}/${rentDateDay}/${useDayNum}/${rentTime}`)
        .then(res => res.json())
        .then(result => {
            // 현재 남아있는 options 없애기
            let targetSelect = document.getElementById('extratime_select');
            targetSelect.options.length = 0;
            let availableExtra = result[0];
            makeExtraOptions(availableExtra)
            extraTimeTarget.style.display = 'block'
        })
}



// extra time option maker
const makeExtraOptions = (times) => {
    for (let i = 0; i<=times; i++) {
        let targetSelect = document.getElementById('extratime_select');
        let createdOpt = document.createElement("option");

        if(i == 0) {
            createdOpt.text = '==추가 시간 선택==';
            createdOpt.value = 0;
        }
        else {
            createdOpt.text = '+'+ i +'시간';
            createdOpt.value = i;
        }
        targetSelect.appendChild(createdOpt);
    }
}


// extra time select
let extraTimeNum = 0;
const timeSelect = () => {
    let timeSelector = document.getElementById('extratime_select');
    let theVal = parseInt(timeSelector.options[timeSelector.selectedIndex].value);
    extraTimeNum = theVal
    let temp = parseInt(rentTime.split('시')[0]) + theVal;
    returnTime = `${temp}시`;
    calculateDate();
}


// 선택한 날짜 자동 선택
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

const postDate = () => {

    // customName != '' && phoneNum!='' &&
    if (rentDateNum!='' && rentTime!='' && returnDateNum!='' && returnTime!='') {

        alert('예약 창으로 넘어갑니다.')
        window.location.href = `/${carType}_reserve/${rentDateNum}/${rentTime}/${returnDateNum}/${returnTime}/${useDay}/${extraTimeNum}/${totalPrice}/${extraFee}`

    } else {
        alert('입력을 완료해주세요!')
    }
}


