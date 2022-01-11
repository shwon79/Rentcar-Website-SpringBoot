const europeCarNum = document.getElementById('europe_carNum').innerText;
const europeCarCode = document.getElementById('europe_carCode').innerText;
const europeYearModel = document.getElementById('europe_yearmodel').innerText;
const europeOffOneDay = parseInt(document.getElementById('europe_off_oneday').value.replace(/,/g, ""));
const europeOnOneDay = parseInt(document.getElementById('europe_on_oneday').value.replace(/,/g, ""));
const limousineCarNum = document.getElementById('limousine_carNum').innerText;
const limousineCarCode = document.getElementById('limousine_carCode').innerText;
const limousineYearModel = document.getElementById('limousine_yearmodel').innerText;
const limousineOffOneDay = parseInt(document.getElementById('limousine_off_oneday').value.replace(/,/g, ""));
const limousineOnOneDay = parseInt(document.getElementById('limousine_on_oneday').value.replace(/,/g, ""));
const travelCarNum = document.getElementById('travel_carNum').innerText;
const travelCarCode = document.getElementById('travel_carCode').innerText;
const travelYearModel = document.getElementById('travel_yearmodel').innerText;
const travelOffOneDay = parseInt(document.getElementById('travel_off_oneday').value.replace(/,/g, ""));
const travelOnOneDay = parseInt(document.getElementById('travel_on_oneday').value.replace(/,/g, ""));
let twodays, threedays, fourdays, fivedays, sixdays, sevendays, eightdays, ninedays, tendays;
let elevendays, twelvedays, thirteendays, fourteendays, fifteendays, sixteendays, seventeendays, eighteendays, ninetinedays, twentydays;
let twentyonedays, twentytwodays, twentythreedays, twentyfourdays, twentyfivedays, twentysixdays, twentysevendays, twentyeightdays, twentyninedays, thirtydays;


function EditCampingcarPrice(carName, season) {
    let carNum, carCode, yearModel, oneDay;

    if (carName.startsWith('europe')) {
        carNum = europeCarNum;
        carCode = europeCarCode;
        yearModel = europeYearModel;
        if (season === 0) {
            oneDay = europeOffOneDay;
            getData(0, 0);
        } else {
            oneDay = europeOnOneDay;
            getData(1, 0);
        }
    } else if (carName.startsWith('limousine')) {
        carNum = limousineCarNum;
        carCode = limousineCarCode;
        yearModel = limousineYearModel;
        if (season === 0) {
            oneDay = limousineOffOneDay;
            getData(0, 1);
        } else {
            oneDay = limousineOnOneDay;
            getData(1, 1);
        }
    } else if (carName.startsWith('travel')) {
        carNum = travelCarNum;
        carCode = travelCarCode;
        yearModel = travelYearModel;
        if (season === 0) {
            oneDay = travelOffOneDay;
            getData(0, 2);
        } else {
            oneDay = travelOnOneDay;
            getData(1, 2);
        }
    }

    // twoday부터는 곱할 거 1, 2(숫자로) 이렇게
    let data = {
        carName: carName,
        carNum: carNum,
        carCode: carCode,
        season: season.toString(),
        deposit: '300000',
        yearmodel: yearModel,
        onedays: oneDay,
        twodays: twodays,
        threedays: threedays,
        fourdays: fourdays,
        fivedays: fivedays,
        sixdays: sixdays,
        sevendays: sevendays,
        eightdays: eightdays,
        ninedays: ninedays,
        tendays: tendays,
        elevendays: elevendays,
        twelvedays: twelvedays,
        thirteendays: thirteendays,
        fourteendays: fourteendays,
        fifteendays: fifteendays,
        sixteendays: sixteendays,
        seventeendays: seventeendays,
        eighteendays: eighteendays,
        ninetinedays: ninetinedays,
        twentydays: twentydays,
        twentyonedays: twentyonedays,
        twentytwodays: twentytwodays,
        twentythreedays: twentythreedays,
        twentyfourdays: twentyfourdays,
        twentyfivedays: twentyfivedays,
        twentysixdays: twentysixdays,
        twentysevendays: twentysevendays,
        twentyeightdays: twentyeightdays,
        twentyninedays: twentyninedays,
        thirtydays: thirtydays
    }
    console.log(data);

    // sendingPriceData();
};

function sendingPriceData() {
    $.ajax({
        type:'PUT',
        url:'/admin/campingcar/price/' + carType,
        dataType:'json',
        contentType : 'application/json; charset=utf-8',
        data : JSON.stringify(data)
    }).done(function (result) {
        if (result.result == 1) {
            alert('처리되었습니다.');
        } else {
            alert('처리에 문제가 생겼습니다.');
        };
        window.location.href = '/admin/campingcar/price/menu';
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
};

function getData(season, index) {
    // index는 europe은 1, limousine은 2, travel은 3
    if (season === 0) {
        twodays = parseFloat(document.getElementsByClassName('offPercent2')[index].value);
        threedays = parseFloat(document.getElementsByClassName('offPercent3')[index].value);
        fourdays = parseFloat(document.getElementsByClassName('offPercent4')[index].value);
        fivedays = parseFloat(document.getElementsByClassName('offPercent5')[index].value);
        sixdays = parseFloat(document.getElementsByClassName('offPercent6')[index].value);
        sevendays = parseFloat(document.getElementsByClassName('offPercent7')[index].value);
        eightdays = parseFloat(document.getElementsByClassName('offPercent8')[index].value);
        ninedays = parseFloat(document.getElementsByClassName('offPercent9')[index].value);
        tendays = parseFloat(document.getElementsByClassName('offPercent10')[index].value);
        elevendays = parseFloat(document.getElementsByClassName('offPercent11')[index].value);
        twelvedays = parseFloat(document.getElementsByClassName('offPercent12')[index].value);
        thirteendays = parseFloat(document.getElementsByClassName('offPercent13')[index].value);
        fourteendays = parseFloat(document.getElementsByClassName('offPercent14')[index].value);
        fifteendays = parseFloat(document.getElementsByClassName('offPercent15')[index].value);
        sixteendays = parseFloat(document.getElementsByClassName('offPercent16')[index].value);
        seventeendays = parseFloat(document.getElementsByClassName('offPercent17')[index].value);
        eighteendays = parseFloat(document.getElementsByClassName('offPercent18')[index].value);
        ninetinedays = parseFloat(document.getElementsByClassName('offPercent19')[index].value);
        twentydays = parseFloat(document.getElementsByClassName('offPercent20')[index].value);
        twentyonedays = parseFloat(document.getElementsByClassName('offPercent21')[index].value);
        twentytwodays = parseFloat(document.getElementsByClassName('offPercent22')[index].value);
        twentythreedays = parseFloat(document.getElementsByClassName('offPercent23')[index].value);
        twentyfourdays = parseFloat(document.getElementsByClassName('offPercent24')[index].value);
        twentyfivedays = parseFloat(document.getElementsByClassName('offPercent25')[index].value);
        twentysixdays = parseFloat(document.getElementsByClassName('offPercent26')[index].value);
        twentysevendays = parseFloat(document.getElementsByClassName('offPercent27')[index].value);
        twentyeightdays = parseFloat(document.getElementsByClassName('offPercent28')[index].value);
        twentyninedays = parseFloat(document.getElementsByClassName('offPercent29')[index].value);
        thirtydays = parseFloat(document.getElementsByClassName('offPercent30')[index].value);
    } else if (season === 1) {
        twodays = parseFloat(document.getElementsByClassName('onPercent2')[index].value);
        threedays = parseFloat(document.getElementsByClassName('onPercent3')[index].value);
        fourdays = parseFloat(document.getElementsByClassName('onPercent4')[index].value);
        fivedays = parseFloat(document.getElementsByClassName('onPercent5')[index].value);
        sixdays = parseFloat(document.getElementsByClassName('onPercent6')[index].value);
        sevendays = parseFloat(document.getElementsByClassName('onPercent7')[index].value);
        eightdays = parseFloat(document.getElementsByClassName('onPercent8')[index].value);
        ninedays = parseFloat(document.getElementsByClassName('onPercent9')[index].value);
        tendays = parseFloat(document.getElementsByClassName('onPercent10')[index].value);
        elevendays = parseFloat(document.getElementsByClassName('onPercent11')[index].value);
        twelvedays = parseFloat(document.getElementsByClassName('onPercent12')[index].value);
        thirteendays = parseFloat(document.getElementsByClassName('onPercent13')[index].value);
        fourteendays = parseFloat(document.getElementsByClassName('onPercent14')[index].value);
        fifteendays = parseFloat(document.getElementsByClassName('onPercent15')[index].value);
        sixteendays = parseFloat(document.getElementsByClassName('onPercent16')[index].value);
        seventeendays = parseFloat(document.getElementsByClassName('onPercent17')[index].value);
        eighteendays = parseFloat(document.getElementsByClassName('onPercent18')[index].value);
        ninetinedays = parseFloat(document.getElementsByClassName('onPercent19')[index].value);
        twentydays = parseFloat(document.getElementsByClassName('onPercent20')[index].value);
        twentyonedays = parseFloat(document.getElementsByClassName('onPercent21')[index].value);
        twentytwodays = parseFloat(document.getElementsByClassName('onPercent22')[index].value);
        twentythreedays = parseFloat(document.getElementsByClassName('onPercent23')[index].value);
        twentyfourdays = parseFloat(document.getElementsByClassName('onPercent24')[index].value);
        twentyfivedays = parseFloat(document.getElementsByClassName('onPercent25')[index].value);
        twentysixdays = parseFloat(document.getElementsByClassName('onPercent26')[index].value);
        twentysevendays = parseFloat(document.getElementsByClassName('onPercent27')[index].value);
        twentyeightdays = parseFloat(document.getElementsByClassName('onPercent28')[index].value);
        twentyninedays = parseFloat(document.getElementsByClassName('onPercent29')[index].value);
        thirtydays = parseFloat(document.getElementsByClassName('onPercent30')[index].value);
    }
}