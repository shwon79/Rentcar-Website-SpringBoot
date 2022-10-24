// camping/calendar/cartype_reserve/year/month 예약과 관련된 모든 기능들

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
};


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
    };

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
        });
    // reserveTime='10시';
}

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
};

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
        });
}
const rentTimeSel = (id) => {
    rentTime = id;
    returnTime = rentTime;

    if (rentTime != '' && rentDateNum != '') {
        let targetDays = document.getElementById('calendar_return')
        targetDays.style.display = 'block'
        calculateDate();
    };

    if (rentTime !='' && rentDateNum != '' && returnDateNum != '') {
        getAvailableExtra();
        calculateDate();
    };
};

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
};

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
};

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
};

// extra time select
let extraTimeNum = 0;
const timeSelect = () => {
    let timeSelector = document.getElementById('extratime_select');
    let theVal = parseInt(timeSelector.options[timeSelector.selectedIndex].value);
    extraTimeNum = theVal
    let temp = parseInt(rentTime.split('시')[0]) + theVal;
    returnTime = `${temp}시`;
    calculateDate();
};

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
};

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
    };

    if (!rentDateNum.endsWith('주세요') && !rentTime.endsWith('주세요')&& returnDateNum!='' && returnTime!='' && extraTime.value!='') {
        alert('예약 창으로 넘어갑니다.')
        window.location.href = `/camping/calendar/${carType}_reserve/reservation/${rentDateNum}/${rentTime}/${returnDateNum}/${returnTime}/${useDay}/${totalPrice}/${extraTime}`
    } else {
        alert('입력을 완료해주세요!')
    };
};

// 대여기간, 성수기/비성수기 선택시 가격 보여주기
let offObj, peakObj, resultPrice;
let offList = [];
let peakList = [];

function displayPrice() {
    let displayPriceDay = document.getElementById('displayPriceDay').value;
    let displayPricePeak = document.getElementById('displayPricePeak').value;
    let displayPriceExtraTime = document.getElementById('displayPriceExtraTime');
    let priceInfo = document.getElementById('priceInfo');

    console.log(displayPriceDay)
    console.log(displayPricePeak)
    console.log(displayPriceExtraTime)
    console.log(priceInfo)

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
    };
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
            offList[2] = Math.floor(parseFloat(offObj['twodays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[3] = Math.floor(parseFloat(offObj['threedays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[4] = Math.floor(parseFloat(offObj['fourdays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[5] = Math.floor(parseFloat(offObj['fivedays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[6] = Math.floor(parseFloat(offObj['sixdays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[7] = Math.floor(parseFloat(offObj['sevendays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[8] = Math.floor(parseFloat(offObj['eightdays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[9] = Math.floor(parseFloat(offObj['ninedays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[10] = Math.floor(parseFloat(offObj['tendays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[11] = Math.floor(parseFloat(offObj['elevendays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[12] = Math.floor(parseFloat(offObj['twelvedays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[13] = Math.floor(parseFloat(offObj['thirteendays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[14] = Math.floor(parseFloat(offObj['fourteendays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[15] = Math.floor(parseFloat(offObj['fifteendays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[16] = Math.floor(parseFloat(offObj['sixteendays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[17] = Math.floor(parseFloat(offObj['seventeendays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[18] = Math.floor(parseFloat(offObj['eighteendays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[19] = Math.floor(parseFloat(offObj['ninetinedays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[20] = Math.floor(parseFloat(offObj['twentydays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[21] = Math.floor(parseFloat(offObj['twentyonedays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[22] = Math.floor(parseFloat(offObj['twentytwodays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[23] = Math.floor(parseFloat(offObj['twentythreedays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[24] = Math.floor(parseFloat(offObj['twentyfourdays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[25] = Math.floor(parseFloat(offObj['twentyfivedays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[26] = Math.floor(parseFloat(offObj['twentysixdays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[27] = Math.floor(parseFloat(offObj['twentysevendays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[28] = Math.floor(parseFloat(offObj['twentyeightdays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[29] = Math.floor(parseFloat(offObj['twentyninedays']) * parseInt(offObj['onedays']) / 1000) * 1000;
            offList[30] = Math.floor(parseFloat(offObj['thirtydays']) * parseInt(offObj['onedays']) / 1000) * 1000;

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
            peakList[2] = Math.floor(parseFloat(peakObj['twodays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[3] = Math.floor(parseFloat(peakObj['threedays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[4] = Math.floor(parseFloat(peakObj['fourdays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[5] = Math.floor(parseFloat(peakObj['fivedays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[6] = Math.floor(parseFloat(peakObj['sixdays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[7] = Math.floor(parseFloat(peakObj['sevendays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[8] = Math.floor(parseFloat(peakObj['eightdays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[9] = Math.floor(parseFloat(peakObj['ninedays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[10] = Math.floor(parseFloat(peakObj['tendays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[11] = Math.floor(parseFloat(peakObj['elevendays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[12] = Math.floor(parseFloat(peakObj['twelvedays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[13] = Math.floor(parseFloat(peakObj['thirteendays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[14] = Math.floor(parseFloat(peakObj['fourteendays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[15] = Math.floor(parseFloat(peakObj['fifteendays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[16] = Math.floor(parseFloat(peakObj['sixteendays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[17] = Math.floor(parseFloat(peakObj['seventeendays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[18] = Math.floor(parseFloat(peakObj['eighteendays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[19] = Math.floor(parseFloat(peakObj['ninetinedays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[20] = Math.floor(parseFloat(peakObj['twentydays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[21] = Math.floor(parseFloat(peakObj['twentyonedays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[22] = Math.floor(parseFloat(peakObj['twentytwodays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[23] = Math.floor(parseFloat(peakObj['twentythreedays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[24] = Math.floor(parseFloat(peakObj['twentyfourdays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[25] = Math.floor(parseFloat(peakObj['twentyfivedays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[26] = Math.floor(parseFloat(peakObj['twentysixdays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[27] = Math.floor(parseFloat(peakObj['twentysevendays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[28] = Math.floor(parseFloat(peakObj['twentyeightdays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[29] = Math.floor(parseFloat(peakObj['twentyninedays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
            peakList[30] = Math.floor(parseFloat(peakObj['thirtydays']) * parseInt(peakObj['onedays']) / 1000) * 1000;
        })
};

// 추가요금 옵션 만들기
function makeExtraTimeOptions(carType, price) {
    const displayPriceExtraTime = document.getElementById('displayPriceExtraTime');

    let option1 = document.createElement('option');
    option1.value = '1';
    option1.text = '3시간권 (+' + price.toLocaleString() + '원)';

    displayPriceExtraTime.appendChild(option1);
};

const clickedDay = (e) => {
    const restBtn = document.getElementsByClassName('able_radio');

    for (const restBtnElement of restBtn) {
        if (restBtnElement.classList.contains('double_circle')) {
            restBtnElement.style.backgroundColor = 'royalblue';
            restBtnElement.style.boxShadow = 'inset 0px 0px 0px 5px white';
            restBtnElement.style.border = '2px solid royalblue';
            restBtnElement.style.color = 'white';
        } else {
            restBtnElement.style.boxShadow = 'none';
            restBtnElement.style.border = 'none';
            restBtnElement.style.backgroundColor = 'royalblue';
            restBtnElement.style.color = 'white';
        }
    };
    e.style.border = '2px solid royalblue';
    e.style.color = 'royalblue';
    e.style.backgroundColor = 'white';
}

// 모바일 예약하기 버튼 누르면 달력 뜨기
function seeMobileReserve(behavior) {
    const rightColumn = document.getElementById('right_column');
    if (behavior === 'open') {
        rightColumn.classList.add('onMobile');
        localStorage.setItem('modalStatus', behavior);
    } else if (behavior === 'close') {
        rightColumn.classList.remove('onMobile');
        modalStatus = behavior;
        localStorage.setItem('modalStatus', behavior);
    }
}

function checkModal() {
    let modalStatus = localStorage.getItem('modalStatus');
    const rightColumn = document.getElementById('right_column');

    if (modalStatus === 'open') {
        rightColumn.classList.add('onMobile');
    } else {
        rightColumn.classList.remove('onMobile');
    }
}
// window.onload = checkModal();