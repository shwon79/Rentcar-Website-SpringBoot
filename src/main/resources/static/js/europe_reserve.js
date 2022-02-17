// 선택 옵션 보이게 하기
function doDisplay(param){
    let calendarRentalDate = document.getElementById("calendar_rental");
    let calendarRentalTime = document.getElementById('calendar_rental_time');
    let calendarRentalPeriod = document.getElementById('calendar_rental_period');
    let calendarRentalExtraTime = document.getElementById('calendar_rental_extra_time');

    switch (param) {
        case 'calendar':
            if (calendarRentalDate.style.display == 'none') {
                calendarRentalDate.style.display = 'block';
            } else {
                calendarRentalDate.style.display = 'none';
            };
            break;
        case 'time_options':
            if (calendarRentalTime.style.display == 'none') {
                calendarRentalTime.style.display = 'block';
            } else {
                calendarRentalTime.style.display = 'none';
            };
            break;
        case 'period_options':
            if (calendarRentalPeriod.style.display == 'none') {
                calendarRentalPeriod.style.display = 'block';
            } else {
                calendarRentalPeriod.style.display = 'none';
            };
            break;
        case 'extra_time_options':
            if (calendarRentalExtraTime.style.display == 'none') {
                calendarRentalExtraTime.style.display = 'block';
            } else {
                calendarRentalExtraTime.style.display = 'none';
            };
            break;
    }
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
    if (season === '성수기') season = 1;
    else if (season === '비성수기') season = 0;

    // 가격표 받아오기
    fetch(`/camping/calendar/${carType}/getprice/${season}`)
        .then(res => res.json())
        .then(result => {
            obj = result;
            priceList[1] = obj['onedays'];
            priceList[2] = parseFloat(obj['twodays']) * parseInt(obj['onedays']);
            priceList[3] = parseFloat(obj['threedays']) * parseInt(obj['onedays']);
            priceList[4] = parseFloat(obj['fourdays']) * parseInt(obj['onedays']);
            priceList[5] = parseFloat(obj['fivedays']) * parseInt(obj['onedays']);
            priceList[6] = parseFloat(obj['sixdays']) * parseInt(obj['onedays']);
            priceList[7] = parseFloat(obj['sevendays']) * parseInt(obj['onedays']);
            priceList[8] = parseFloat(obj['eightdays']) * parseInt(obj['onedays']);
            priceList[9] = parseFloat(obj['ninedays']) * parseInt(obj['onedays']);
            priceList[10] = parseFloat(obj['tendays']) * parseInt(obj['onedays']);
            priceList[11] = parseFloat(obj['elevendays']) * parseInt(obj['onedays']);
            priceList[12] = parseFloat(obj['twelvedays']) * parseInt(obj['onedays']);
            priceList[13] = parseFloat(obj['thirteendays']) * parseInt(obj['onedays']);
            priceList[14] = parseFloat(obj['fourteendays']) * parseInt(obj['onedays']);
            priceList[15] = parseFloat(obj['fifteendays']) * parseInt(obj['onedays']);
            priceList[16] = parseFloat(obj['sixteendays']) * parseInt(obj['onedays']);
            priceList[17] = parseFloat(obj['seventeendays']) * parseInt(obj['onedays']);
            priceList[18] = parseFloat(obj['eighteendays']) * parseInt(obj['onedays']);
            priceList[19] = parseFloat(obj['ninetinedays']) * parseInt(obj['onedays']);
            priceList[20] = parseFloat(obj['twentydays']) * parseInt(obj['onedays']);
            priceList[21] = parseFloat(obj['twentyonedays']) * parseInt(obj['onedays']);
            priceList[22] = parseFloat(obj['twentytwodays']) * parseInt(obj['onedays']);
            priceList[23] = parseFloat(obj['twentythreedays']) * parseInt(obj['onedays']);
            priceList[24] = parseFloat(obj['twentyfourdays']) * parseInt(obj['onedays']);
            priceList[25] = parseFloat(obj['twentyfivedays']) * parseInt(obj['onedays']);
            priceList[26] = parseFloat(obj['twentysixdays']) * parseInt(obj['onedays']);
            priceList[27] = parseFloat(obj['twentysevendays']) * parseInt(obj['onedays']);
            priceList[28] = parseFloat(obj['twentyeightdays']) * parseInt(obj['onedays']);
            priceList[29] = parseFloat(obj['twentyninedays']) * parseInt(obj['onedays']);
            priceList[30] = parseFloat(obj['thirtydays']) * parseInt(obj['onedays']);
        })
}


// select rent date
let rentDateNum='';
let rentDateYear='';
let rentDateMonth='';
let rentDateDay='';
let dateId = '';
let reserveTime = '';

function sendRentDate(id, year, wDay, getdateId) {


    const selectedDate = document.getElementById('selected_date');
    let resultSelectedDate = document.getElementById('display_result_start_date');
    let calendarRentalDate = document.getElementById("calendar_rental");
    let calendarRentalTime = document.getElementById('calendar_rental_time');


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

    selectedDate.innerText = year + '년 ' + id + '(' + whichDay + ')';
    calendarRentalDate.style.display = 'none';
    resultSelectedDate.innerText = id;
    rentDateNum = id;
    calendarRentalTime.style.display = 'grid';

    document.getElementById('selected_time').innerText = '시간을 선택해주세요';
    document.getElementById('display_result_start_time').innerText = '시간';
    document.getElementById('selected_period').innerText = '렌트일자를 선택해주세요';
    document.getElementById('selected_extra_time').innerText = '추가시간을 선택해주세요';
    document.getElementById('display_result_end_date').innerText = '반납 날짜';
    document.getElementById('display_result_end_time').innerText = '반납 시간';
    document.getElementById('calendar_rental_extra_time').style.display = 'none';
    dateId = getdateId;


    // 선택 가능한 시간 받아오기
    fetch(`/camping/calendar/${carType}_reserve/time_list/${dateId}`)
        .then(res => res.json())
        .then(result => {
            let options = document.getElementsByName('time_option');
            options.forEach(option => {
                option.style.pointerEvents = 'auto';
                option.style.backgroundColor = 'white';
            })

            for (i=0; i < result.length; i++) {
                if (result[i]=='1') {
                    options[i].style.pointerEvents = 'none';
                    options[i].style.backgroundColor = 'dimgray';
                }
            }
        })

    // reserveTime='10시';

}
// const sendRentDate = (id, year, wDay) => {
//     console.log(id);
//     console.log(year);
//     console.log(wDay);
//     // rentDateNum = id;
//     // rentDateYear = year;
//     // rentDateMonth = rentDateNum.split('월 ')[0];
//     // rentDateDay = rentDateNum.split('월 ')[1].split('일')[0];
//     // let url = `/${carType}/sendrentdate`;
//     //
//     // let targetTimes = document.getElementsByName('time1');
//     // for (const eachTime of targetTimes) {
//     //     eachTime.checked = false;
//     // }
//     //
//     // rentTime='';
//     // returnTime='';
//     // returnDateNum='';
//     // extraTimeNum=0;
//     //
//     // // 요일 한글화 swtich
//     // let whichDay = '';
//     // switch (wDay) {
//     //     case '0':
//     //         whichDay = '일';
//     //         break;
//     //     case '1':
//     //         whichDay = '월';
//     //         break;
//     //     case '2':
//     //         whichDay = '화';
//     //         break;
//     //     case '3':
//     //         whichDay = '수';
//     //         break;
//     //     case '4':
//     //         whichDay = '목';
//     //         break;
//     //     case '5':
//     //         whichDay = '금';
//     //         break;
//     //     case '6':
//     //         whichDay = '토';
//     //         break;
//     // }
//     //
//     // // 선택된 날짜 글자 넣기
//     // let putTarget = document.getElementById('rent_date');
//     // let putResult = year+'.'+id.split('월 ')[0]+'.'+id.split(' ')[1].split('일')[0]+'.('+whichDay+')';
//     // putTarget.innerText = putResult;
//     //
//     // // 그 날짜에 맞는 선택 가능 시간 데이터 받아오기
//     // fetch(url+'/'+rentDateYear+'/'+rentDateMonth+'/'+rentDateDay)
//     //     .then(res => res.json())
//     //     .then(result => {
//     //
//     //         // 대여 시간 선택 띄우기
//     //         let theWrapper = document.getElementById('rent_time');
//     //         let hrTime = document.getElementById('hr_time');
//     //
//     //         theWrapper.style.display = 'block';
//     //         hrTime.style.display = 'block';
//     //
//     //         // 대여 시작 날짜 & 시간 선택되면 일권 고르기
//     //         if (rentDateNum != '' && rentTime != '') {
//     //             let targetDays = document.getElementById('calendar_return');
//     //             targetDays.style.display = 'block';
//     //         }
//     //
//     //         let allTime = ["10시", "11시", "12시", "13시", "14시", "15시", "16시", "17시"];
//     //         for (const eachTime of allTime) {
//     //             let timeId = document.getElementById(eachTime);
//     //             if (result.includes(eachTime)) {
//     //                 timeId.disabled = false;
//     //                 timeId.className = 'able_time'
//     //
//     //             }
//     //             else {
//     //                 timeId.disabled = true;
//     //                 timeId.className = 'disabled_time';
//     //             }
//     //         }
//     //
//     //         calculateDate();
//     //
//     //     })
//     //
//     // let availableDays = 0;
//     //
//     // // 선택 불가능한 가까운 날짜 받아오기
//     // fetch(`/${carType}/getrentdate/${rentDateYear}/${rentDateMonth}/${rentDateDay}`)
//     //     .then(res => res.json())
//     //     .then(result => {
//     //         // 현재 남아있는 options 없애기
//     //         let targetSelect = document.getElementById('days_select');
//     //         targetSelect.options.length = 0;
//     //         availableDays = result[0];
//     //         makeOptions(availableDays)
//     //     })
//     //
//     // runIt();
// }


// make 일권 options on selection

const makeOptions = (days) => {
    for (let i = 0; i<=days; i++) {
        let targetSelect = document.getElementById('days_select');
        let createdOpt = document.createElement("option");
        if (i>30) break;
        if (i == 30) {
            createdOpt.text = '한달권';
            createdOpt.value = '한달';
        } else if(i == 0) {
            createdOpt.text = '=렌트 일자 선택=';
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
function sendRentTime(id) {
    const selectedTime = document.getElementById('selected_time');
    const resultSelectedTime = document.getElementById('display_result_start_time');
    let calendarRentalPeriod = document.getElementById('calendar_rental_period');

    let calendarRentalTime = document.getElementById('calendar_rental_time');

    selectedTime.innerText = id;
    calendarRentalTime.style.display = 'none';
    resultSelectedTime.innerText = id;

    calendarRentalPeriod.style.display = 'block';
    document.getElementById('selected_period').innerText = '렌트일자를 선택해주세요';
    document.getElementById('selected_extra_time').innerText = '추가시간을 선택해주세요';

    reserveTime = id;

    let availableDays = 0;

    //선택 불가능한 가까운 날짜 받아오기
    fetch(`/camping/calendar/possible/${carType}/${dateId}/${reserveTime}/29`)
        .then(res => res.json())
        .then(result => {
            // 현재 남아있는 options 없애기
            let targetSelect = document.getElementById('days_select');
            targetSelect.options.length = 0;
            availableDays = result.possible_days;
            // console.log(availableDays);
            makeOptions(availableDays)
        })
}
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
let param = '';
const daysSelect = () => {
    let select = document.getElementById('days_select');
    let selectedPeriod = document.getElementById('selected_period');
    let calendarRentalPeriod = document.getElementById('calendar_rental_period');
    let resultSelectedEndDate = document.getElementById('display_result_end_date');
    let resultSelectedStartDate = document.getElementById('selected_date').innerText;
    let fullReturnDate = document.getElementById('fullReturnDate');

    let plus = parseInt(select.value);

    if (select.value === '한달') {
        selectedPeriod.innerText = select.value;
        param = 30;
    } else if (select.value === 0) {
        selectedPeriod.innerText = '렌트일자를 선택해주세요';
    } else {
        param = plus;
        selectedPeriod.innerText = select.value + '일';
    }
    calendarRentalPeriod.style.display = 'none';

    let splitString = resultSelectedStartDate.split(' ');
    let year = parseInt(splitString[0]);
    let month = parseInt(splitString[1]);
    let day = parseInt(splitString[2]);

    let startDate = new Date(year, month-1, day);

    let calYear, calMonth, calDate;
    if (select.value === '한달') {
        startDate.setMonth(startDate.getMonth() + 1);
        calYear = startDate.getFullYear();
        calMonth = startDate.getMonth() + 1;
        calDate = day;
    } else {
        startDate.setDate(startDate.getDate() + plus);
        calYear = startDate.getFullYear();
        calMonth = startDate.getMonth() + 1;
        calDate = startDate.getDate();
    }

    fullReturnDate.innerText = calYear + '년 ' + calMonth + '월 ' + calDate + '일';
    resultSelectedEndDate.innerText = calMonth + '월 ' + calDate + '일';
    document.getElementById('calendar_rental_extra_time').style.display = 'block';
    document.getElementById('selected_extra_time').innerText = '추가시간을 선택해주세요';

    //추가시간 가능 여부 받아오기
    fetch(`/camping/calendar/possible/${carType}/${dateId}/${reserveTime}/${select.value}`)
        .then(res => res.json())
        .then(result => {
            let targetSelect = document.getElementById('extra_time_select');

            if(result.extra_time_flg == 0) {
                targetSelect.options.length = 0;
                let opt = document.createElement('option');
                opt.text = '=추가 시간 선택=';
                opt.value = '';
                let opt1 = document.createElement('option');
                opt1.text = '추가 시간 X';
                opt1.value = '0';
                targetSelect.appendChild(opt);
                targetSelect.appendChild(opt1);
            } else {
                targetSelect.options.length = 0;
                let opt = document.createElement('option');
                opt.text = '=추가 시간 선택=';
                opt.value = '';
                let opt1 = document.createElement('option');
                opt1.text = '추가 시간 X';
                opt1.value = '0';
                let opt2 = document.createElement('option');
                opt2.text = '3시간';
                opt2.value = '3';
                targetSelect.appendChild(opt);
                targetSelect.appendChild(opt1);
                targetSelect.appendChild(opt2);
            }
        })
    // runIt();
    //
    // setTimeout(function() {
    //     displayCampingPrice(param);
    // }, 200);
}

function displayCampingPrice(param) {
    let rentPrice = document.getElementById('rentPrice');
    let rentVAT = document.getElementById('rentVAT');
    let rentFullPrice = document.getElementById('rentFullPrice');
    let extraTimePrice = document.getElementById('extraTimePrice');
    let selectedExtraTime = document.getElementById('extra_time_select');

    let originPrice = parseInt(priceList[param]);

    if (selectedExtraTime.value == '3') {
        if (carType == 'europe') {
            extraTimePrice.innerText = '110,000 원';
        } else if (carType == 'limousine' || carType == 'travel') {
            extraTimePrice.innerText = '90,000 원';
        }
    } else {
        extraTimePrice.innerText = '- 원';
    }

    if (Math.floor((originPrice/11*10)).toLocaleString() == NaN) {
        rentPrice.innerText ='';
    } else {
        rentPrice.innerText = Math.floor((originPrice/11*10)).toLocaleString() + ' 원';
    }

    if (Math.floor((originPrice/11)).toLocaleString() == NaN) {
        rentVAT.innerText ='';
    } else {
        rentVAT.innerText = Math.floor((originPrice/11)).toLocaleString() + ' 원';
    }

    if (originPrice.toLocaleString() == NaN) {
        rentFullPrice.innerText ='';
    } else {
        if (selectedExtraTime.value == '3') {
            if (carType == 'europe') {
                rentFullPrice.innerText = (originPrice + 110000).toLocaleString() + ' 원';
            } else if (carType == 'limousine' || carType == 'travel') {
                rentFullPrice.innerText = (originPrice + 90000).toLocaleString() + ' 원';
            }
        } else {
            rentFullPrice.innerText = originPrice.toLocaleString() + ' 원';
        }
    }
}

//추가 시간 선택시 표기
function extraTimeSelect() {
    let resultSelectedEndTime = document.getElementById('display_result_end_time');
    let resultStartTime = document.getElementById('display_result_start_time');

    let displayExtraTime = document.getElementById('selected_extra_time');
    let selectedExtraTime = document.getElementById('extra_time_select');

    if (selectedExtraTime.value === '') {
        displayExtraTime.innerText = '추가 시간 X';
        resultSelectedEndTime.innerText = resultStartTime.innerText;
    } else if (selectedExtraTime.value === '0') {
        displayExtraTime.innerText = '추가 시간 X';
        resultSelectedEndTime.innerText = resultStartTime.innerText;
    } else if (selectedExtraTime.value === '3') {
        displayExtraTime.innerText = '3시간';
        let res = parseInt(resultStartTime.innerText) + 3;
        resultSelectedEndTime.innerText = res + '시';
    } else if (selectedExtraTime.value === 0) {
        displayExtraTime.innerText = '추가 시간을 선택하세요.';
        resultSelectedEndTime.innerText = '';
    }

    document.getElementById('calendar_rental_extra_time').style.display = 'none';


    runIt();

    setTimeout(function() {
        displayCampingPrice(param);
    }, 200);
}
// const daysSelect = () => {
//     let daySelector = document.getElementById('days_select');
//     let theVal = parseInt(daySelector.options[daySelector.selectedIndex].value);
//
//     // 날짜 계산  && rentTime != ''
//     if (rentDateNum != '') {
//         let temp = rentDateNum.split('월 ');
//         rentDateMonth = parseInt(temp[0]);
//         let returnDateMon = rentDateMonth;
//         rentDateDay = parseInt(temp[1].split('일')[0]);
//         let returnDateDay = rentDateDay + theVal;
//         useDay = daySelector.options[daySelector.selectedIndex].innerText;
//         useDayNum = parseInt(daySelector.options[daySelector.selectedIndex].value);
//
//
//         if (returnDateDay < 29) ;
//         else if (useDayNum == 30) {  // 한달권 선택시
//             returnDateMon = rentDateMonth + 1;
//             returnDateDay = rentDateDay;
//         }
//         else { // 한달권 제외 다른 달로 넘어갈 시
//             if (rentDateMonth <= 7) {
//                 if (rentDateMonth == 2) {
//                     if (returnDateDay > 29) {
//                         returnDateMon = rentDateMonth + 1;
//                         returnDateDay -= 28;
//                     }
//                 } else if (rentDateMonth%2 == 1) {
//                     if (returnDateDay > 31) {
//                         returnDateMon = rentDateMonth + 1;
//                         returnDateDay -= 31;
//                     }
//                 } else if (rentDateMonth%2 == 0) {
//                     if (returnDateDay > 30) {
//                         returnDateMon = rentDateMonth + 1;
//                         returnDateDay -= 30;
//                     }
//                 }
//             } else if (rentDateMonth > 7) {
//                 if (rentDateMonth%2 == 0) {
//                     if (returnDateDay > 31) {
//                         returnDateMon = rentDateMonth + 1;
//                         returnDateDay -= 31;
//                     }
//                 } else if (rentDateMonth%2 == 1) {
//                     if (returnDateDay > 30) {
//                         returnDateMon = rentDateMonth + 1;
//                         returnDateDay -= 30;
//                     }
//                 }
//             }
//
//         }
//
//
//         returnDateNum = `${returnDateMon}월 ${returnDateDay}일`;
//
//         getAvailableExtra();
//
//
//     }
//     calculateDate();
// }

// let extraTimeTarget = document.getElementById('extra_time_sel')
// const getAvailableExtra = () => {
//     fetch(`/${carType}/getextratime/${rentDateYear}/${rentDateMonth}/${rentDateDay}/${useDayNum}/${rentTime}`)
//         .then(res => res.json())
//         .then(result => {
//             // 현재 남아있는 options 없애기
//             let targetSelect = document.getElementById('extratime_select');
//             targetSelect.options.length = 0;
//             let availableExtra = result[0];
//             makeExtraOptions(availableExtra)
//             extraTimeTarget.style.display = 'block'
//         })
// }



// extra time option maker
const makeExtraOptions = (times) => {
    for (let i = 0; i<=times; i++) {
        let targetSelect = document.getElementById('extratime_select');
        let createdOpt = document.createElement("option");

        if(i == 0) {
            createdOpt.text = '==추가 시간 선택==';
            createdOpt.value = '';
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
// const seasonTarget = document.getElementById('rent_date');
// let condition1 = parseInt(seasonTarget.title);
// if (condition1 > 0) doIt();


// // df
// runIt();




// Sending Data;

const postDate = () => {
    rentDateNum = document.getElementById('selected_date').innerText;
    rentTime = document.getElementById('selected_time').innerText;
    returnDateNum = document.getElementById('fullReturnDate').innerText;
    returnTime = '';
    useDay = document.getElementById('selected_period').innerText;
    let extraTime = document.getElementById('extra_time_select');

    let totalPriceString = document.getElementById('rentFullPrice').innerText.replace(/,/g, "");
    totalPrice = totalPriceString.substr(0, totalPriceString.length - 1);

    if (extraTime.value != '') {
        if (extraTime.value ==='0') {
            extraTime = 0;
            returnTime = rentTime;
        } else if (extraTime.value ==='3') {
            extraTime = 1;
            let calculateTime = parseInt(rentTime) + 3;
            returnTime = calculateTime + '시';
        }
    }

    if (!rentDateNum.endsWith('주세요') && !rentTime.endsWith('주세요')&& returnDateNum!='' && returnTime!='' && extraTime.value!='') {
        alert('예약 창으로 넘어갑니다.')
        window.location.href = `/camping/calendar/${carType}_reserve/reservation/${rentDateNum}/${rentTime}/${returnDateNum}/${returnTime}/${useDay}/${totalPrice}/${extraTime}`

    } else {
        alert('입력을 완료해주세요!')
    }
}

// 옵션 선택 다 해야 아래 결과 보여주기
// function displayResultWrapper() {
//     let resultWrapper = document.getElementsByClassName('result_wrapper')[0];
//     let startDate = document.getElementById('selected_date').innerText;
//     let startTime = $(".time_options input[type='radio']:checked")[0];
//     let rentPeriod = document.getElementById('selected_period').innerText;
//
//     if (startTime === undefined) {
//         startTime = '';
//     } else {
//         startTime = startTime.id;
//     }
//
//     let validStartDate = startDate.startsWith('20') ? true : false;
//     let validStartTime = startTime != '' ? true : false;
//     let validRentPeriod;
//
//     if (rentPeriod.endsWith('일') || rentPeriod === '한달') {
//         validRentPeriod = true;
//     } else {
//         validRentPeriod = false;
//     }
//
//     if (validStartDate & validStartTime & validRentPeriod) {
//         resultWrapper.style.display = 'block';
//     } else {
//         resultWrapper.style.display = 'none';
//     }
// }

// 대여기간, 성수기/비성수기 선택시 가격 보여주기
let offObj, peakObj, resultPrice;
let offList = [];
let peakList = [];

function displayPrice() {
    let displayPriceDay = document.getElementById('displayPriceDay').value;
    let displayPricePeak = document.getElementById('displayPricePeak').value;
    let displayPriceExtraTime = document.getElementById('displayPriceExtraTime');
    let priceInfo = document.getElementById('priceInfo');

    if (displayPriceDay != '' && displayPricePeak != '') {
        if (displayPricePeak == '0') {
            if (displayPriceExtraTime && displayPriceExtraTime.value == '1') {
                priceInfo.innerText = (offList[displayPriceDay] + offList[0]).toLocaleString();
            } else {
                priceInfo.innerText = offList[displayPriceDay].toLocaleString();
            }
        } else if (displayPricePeak == '1') {
            if (displayPriceExtraTime && displayPriceExtraTime.value == '1') {
                priceInfo.innerText = (peakList[displayPriceDay] + offList[0]).toLocaleString();
            } else {
                priceInfo.innerText = peakList[displayPriceDay].toLocaleString();
            }
        }
    } else {
        priceInfo.innerText = '';
    }
};

// 페이지 로딩 시 가장 싼 가격 보여주면서 모든 가격 받아오기
function getPriceData(carType) {
    let cheapestPrice = document.getElementById('cheapestPrice');
    let displayPriceExtraTime = document.getElementById('displayPriceExtraTime');

    // 비성수기 가격
    fetch(`/camping/calendar/` + carType + `/getprice/0`)
        .then(res => res.json())
        .then(result => {
            offObj = result;
            offList[0] = offObj['threeHours'];
            offList[1] = offObj['onedays'];
            offList[2] = parseFloat(offObj['twodays']) * parseInt(offObj['onedays']);
            offList[3] = parseFloat(offObj['threedays']) * parseInt(offObj['onedays']);
            offList[4] = parseFloat(offObj['fourdays']) * parseInt(offObj['onedays']);
            offList[5] = parseFloat(offObj['fivedays']) * parseInt(offObj['onedays']);
            offList[6] = parseFloat(offObj['sixdays']) * parseInt(offObj['onedays']);
            offList[7] = parseFloat(offObj['sevendays']) * parseInt(offObj['onedays']);
            offList[8] = parseFloat(offObj['eightdays']) * parseInt(offObj['onedays']);
            offList[9] = parseFloat(offObj['ninedays']) * parseInt(offObj['onedays']);
            offList[10] = parseFloat(offObj['tendays']) * parseInt(offObj['onedays']);
            offList[11] = parseFloat(offObj['elevendays']) * parseInt(offObj['onedays']);
            offList[12] = parseFloat(offObj['twelvedays']) * parseInt(offObj['onedays']);
            offList[13] = parseFloat(offObj['thirteendays']) * parseInt(offObj['onedays']);
            offList[14] = parseFloat(offObj['fourteendays']) * parseInt(offObj['onedays']);
            offList[15] = parseFloat(offObj['fifteendays']) * parseInt(offObj['onedays']);
            offList[16] = parseFloat(offObj['sixteendays']) * parseInt(offObj['onedays']);
            offList[17] = parseFloat(offObj['seventeendays']) * parseInt(offObj['onedays']);
            offList[18] = parseFloat(offObj['eighteendays']) * parseInt(offObj['onedays']);
            offList[19] = parseFloat(offObj['ninetinedays']) * parseInt(offObj['onedays']);
            offList[20] = parseFloat(offObj['twentydays']) * parseInt(offObj['onedays']);
            offList[21] = parseFloat(offObj['twentyonedays']) * parseInt(offObj['onedays']);
            offList[22] = parseFloat(offObj['twentytwodays']) * parseInt(offObj['onedays']);
            offList[23] = parseFloat(offObj['twentythreedays']) * parseInt(offObj['onedays']);
            offList[24] = parseFloat(offObj['twentyfourdays']) * parseInt(offObj['onedays']);
            offList[25] = parseFloat(offObj['twentyfivedays']) * parseInt(offObj['onedays']);
            offList[26] = parseFloat(offObj['twentysixdays']) * parseInt(offObj['onedays']);
            offList[27] = parseFloat(offObj['twentysevendays']) * parseInt(offObj['onedays']);
            offList[28] = parseFloat(offObj['twentyeightdays']) * parseInt(offObj['onedays']);
            offList[29] = parseFloat(offObj['twentyninedays']) * parseInt(offObj['onedays']);
            offList[30] = parseFloat(offObj['thirtydays']) * parseInt(offObj['onedays']);

            cheapestPrice.innerText = parseInt(offList[1]).toLocaleString();
            if (displayPriceExtraTime) {
                makeExtraTimeOptions(carType, offList[0]);
            };
        })

    // 성수기 가격
    fetch(`/camping/calendar/` + carType + `/getprice/1`)
        .then(res => res.json())
        .then(result => {
            peakObj = result;
            peakList[0] = peakObj['threeHours'];
            peakList[1] = peakObj['onedays'];
            peakList[2] = parseFloat(peakObj['twodays']) * parseInt(peakObj['onedays']);
            peakList[3] = parseFloat(peakObj['threedays']) * parseInt(peakObj['onedays']);
            peakList[4] = parseFloat(peakObj['fourdays']) * parseInt(peakObj['onedays']);
            peakList[5] = parseFloat(peakObj['fivedays']) * parseInt(peakObj['onedays']);
            peakList[6] = parseFloat(peakObj['sixdays']) * parseInt(peakObj['onedays']);
            peakList[7] = parseFloat(peakObj['sevendays']) * parseInt(peakObj['onedays']);
            peakList[8] = parseFloat(peakObj['eightdays']) * parseInt(peakObj['onedays']);
            peakList[9] = parseFloat(peakObj['ninedays']) * parseInt(peakObj['onedays']);
            peakList[10] = parseFloat(peakObj['tendays']) * parseInt(peakObj['onedays']);
            peakList[11] = parseFloat(peakObj['elevendays']) * parseInt(peakObj['onedays']);
            peakList[12] = parseFloat(peakObj['twelvedays']) * parseInt(peakObj['onedays']);
            peakList[13] = parseFloat(peakObj['thirteendays']) * parseInt(peakObj['onedays']);
            peakList[14] = parseFloat(peakObj['fourteendays']) * parseInt(peakObj['onedays']);
            peakList[15] = parseFloat(peakObj['fifteendays']) * parseInt(peakObj['onedays']);
            peakList[16] = parseFloat(peakObj['sixteendays']) * parseInt(peakObj['onedays']);
            peakList[17] = parseFloat(peakObj['seventeendays']) * parseInt(peakObj['onedays']);
            peakList[18] = parseFloat(peakObj['eighteendays']) * parseInt(peakObj['onedays']);
            peakList[19] = parseFloat(peakObj['ninetinedays']) * parseInt(peakObj['onedays']);
            peakList[20] = parseFloat(peakObj['twentydays']) * parseInt(peakObj['onedays']);
            peakList[21] = parseFloat(peakObj['twentyonedays']) * parseInt(peakObj['onedays']);
            peakList[22] = parseFloat(peakObj['twentytwodays']) * parseInt(peakObj['onedays']);
            peakList[23] = parseFloat(peakObj['twentythreedays']) * parseInt(peakObj['onedays']);
            peakList[24] = parseFloat(peakObj['twentyfourdays']) * parseInt(peakObj['onedays']);
            peakList[25] = parseFloat(peakObj['twentyfivedays']) * parseInt(peakObj['onedays']);
            peakList[26] = parseFloat(peakObj['twentysixdays']) * parseInt(peakObj['onedays']);
            peakList[27] = parseFloat(peakObj['twentysevendays']) * parseInt(peakObj['onedays']);
            peakList[28] = parseFloat(peakObj['twentyeightdays']) * parseInt(peakObj['onedays']);
            peakList[29] = parseFloat(peakObj['twentyninedays']) * parseInt(peakObj['onedays']);
            peakList[30] = parseFloat(peakObj['thirtydays']) * parseInt(peakObj['onedays']);
        })
};

// 추가요금 옵션 만들기
function makeExtraTimeOptions(carType, price) {
    const displayPriceExtraTime = document.getElementById('displayPriceExtraTime');

    let option1 = document.createElement('option');
    option1.value = '1';
    option1.text = '3시간권 (+' + price.toLocaleString() + '원)';

    displayPriceExtraTime.appendChild(option1);
}

// //지난 달로 못가게 화살표 없애기
// const hiddenOnlythisMonth = document.getElementById('hiddenOnlythisMonth');
// const curMonth = new Date();
// let thisMonthOnCalendar = hiddenOnlythisMonth.dataset.index;
// if (curMonth.getMonth()+1 == thisMonthOnCalendar) {
//     hiddenOnlythisMonth.style.display = "none";
// }
//
//
// // 6월 달력까지만 보이게
// const hiddenFromJuly = document.getElementById('hiddenFromJuly');
// if (hiddenFromJuly.dataset.index == '6') {
//     hiddenFromJuly.style.display = 'none';
// }

// 리뷰쓰기 버튼 누르면 리뷰 폼 보이기
function displayReviewBox() {
    document.getElementById('openReviewBtn').classList.toggle('active');
    document.getElementById('writeReviewBox').classList.toggle('active');
}

// 리뷰 등록하기 버튼
function submitReview() {
    let reviewName = document.getElementById('reviewName').value;
    let reviewPassword = document.getElementById('reviewPassword').value;
    let reviewCarType = document.getElementById('reviewCarType').value;
    let reviewRentStartDate = document.getElementById('reviewRentStartDate').value;
    let reviewRentEndDate = document.getElementById('reviewRentEndDate').value;
    let reviewText = document.getElementById('reviewText').value;
    let reviewImage = document.getElementById('reviewImage').files;
    let reviewVideo = document.getElementById('reviewVideo').files;

    // convert file object to list
    // reviewImage = Object.values(reviewImage);
    // console.log(reviewImage);
    // image 첨부 10개 미만일 경우 null 값 추가
    // if (reviewImage.length !== 10) {
    //     do {
    //         reviewImage.push(null);
    //     } while (reviewImage.length < 10)
    // }
    console.log(reviewImage[0]);
    console.log(reviewVideo);

    // let data = {
    //     'carName': reviewCarType,
    //     'text': reviewText,
    //     'nickName': reviewName,
    //     'startDate': reviewRentStartDate,
    //     'endDate': reviewRentEndDate,
    //     'file': reviewImage,
    //     'password': reviewPassword
    // };
    //
    // console.log(data);

    let formDataWrapper = new FormData();


    if (reviewVideo.length > 1) {
        // 동영상 최대 갯수 1개
        alert('동영상 첨부는 최대 1개까지 가능합니다.');
    } else if (reviewImage.length > 10) {
        // 사진 최대 갯수 10개
        alert('이미지 첨부는 최대 10장까지 가능합니다.');
    } else if (reviewName === '' || reviewPassword === '' || reviewCarType === '' || reviewRentStartDate === '' || reviewRentEndDate === '' || reviewText === '') {
        alert('필수 입력 내용을 빠짐없이 작성해주세요.');
    } else if (reviewName !== '' && reviewPassword !== '' && reviewCarType !== '' && reviewRentStartDate !== '' && reviewRentEndDate !== '' && reviewText !== '') {
        formDataWrapper.append('carName', reviewCarType);
        formDataWrapper.append('text', reviewText);
        formDataWrapper.append('nickName', reviewName);
        formDataWrapper.append('startDate', reviewRentStartDate);
        formDataWrapper.append('endDate', reviewRentEndDate);
        for (let i = 0; i < reviewImage.length; i++) {
            formDataWrapper.append('file', reviewImage[i]);
        }
        formDataWrapper.append('video', reviewVideo[0]);
        formDataWrapper.append('password', reviewPassword);
        if (confirm('리뷰를 등록 하시겠습니까?')) {
            postReview(formDataWrapper);
        };
    };

    function postReview(data) {
        $.ajax({
            enctype: 'multipart/form-data',
            cache: false,
            type: 'POST',
            url: '/camping/calendar/review',
            processData:false,
            contentType: false,
            data: data
        }).done(function () {
            alert('리뷰가 등록되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };
}

// 데스크탑에서 리뷰 클릭하면 크게 보여지도록
function openReview(id) {
    const reviewImageOpen = [...document.getElementsByClassName('review_image_open')];
    const oneReviewClose = [...document.getElementsByClassName('one_review_close')];

    let targetImageBox = reviewImageOpen.find(box => box.dataset.title == id);
    targetImageBox.classList.toggle('active');

    let targetReview = oneReviewClose.find(review => review.dataset.title == id);
    targetReview.classList.toggle('opened');
}