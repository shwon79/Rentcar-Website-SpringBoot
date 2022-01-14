const europeCarNum = document.getElementById('europe_carNum').innerText;
const europeCarCode = document.getElementById('europe_carCode').innerText;
const europeYearModel = document.getElementById('europe_yearmodel').innerText;
const limousineCarNum = document.getElementById('limousine_carNum').innerText;
const limousineCarCode = document.getElementById('limousine_carCode').innerText;
const limousineYearModel = document.getElementById('limousine_yearmodel').innerText;
const travelCarNum = document.getElementById('travel_carNum').innerText;
const travelCarCode = document.getElementById('travel_carCode').innerText;
const travelYearModel = document.getElementById('travel_yearmodel').innerText;
let twodays, threedays, fourdays, fivedays, sixdays, sevendays, eightdays, ninedays, tendays;
let elevendays, twelvedays, thirteendays, fourteendays, fifteendays, sixteendays, seventeendays, eighteendays, ninetinedays, twentydays;
let twentyonedays, twentytwodays, twentythreedays, twentyfourdays, twentyfivedays, twentysixdays, twentysevendays, twentyeightdays, twentyninedays, thirtydays;

function EditCampingcarPrice(carName, season) {
    const europeOffOneDay = parseInt(document.getElementById('europe_off_oneday').value.replace(/,/g, ""));
    const europeOnOneDay = parseInt(document.getElementById('europe_on_oneday').value.replace(/,/g, ""));
    const limousineOffOneDay = parseInt(document.getElementById('limousine_off_oneday').value.replace(/,/g, ""));
    const limousineOnOneDay = parseInt(document.getElementById('limousine_on_oneday').value.replace(/,/g, ""));
    const travelOffOneDay = parseInt(document.getElementById('travel_off_oneday').value.replace(/,/g, ""));
    const travelOnOneDay = parseInt(document.getElementById('travel_on_oneday').value.replace(/,/g, ""));
    let carNum, carCode, yearModel, oneDay;

    if (carName.startsWith('europe')) {
        carNum = europeCarNum;
        carCode = europeCarCode;
        yearModel = europeYearModel;
        if (season === 0) {
            oneDay = europeOffOneDay;
            getData(0);
        } else {
            oneDay = europeOnOneDay;
            getData(1);
        }
    } else if (carName.startsWith('limousine')) {
        carNum = limousineCarNum;
        carCode = limousineCarCode;
        yearModel = limousineYearModel;
        if (season === 0) {
            oneDay = limousineOffOneDay;
            getData(2);
        } else {
            oneDay = limousineOnOneDay;
            getData(3);
        }
    } else if (carName.startsWith('travel')) {
        carNum = travelCarNum;
        carCode = travelCarCode;
        yearModel = travelYearModel;
        if (season === 0) {
            oneDay = travelOffOneDay;
            getData(4);
        } else {
            oneDay = travelOnOneDay;
            getData(5);
        }
    }

    let data = {
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

    if (confirm('가격을 수정하시겠습니까?')) {
        console.log(data);
        sendingPriceData();
    };

    function sendingPriceData() {
        $.ajax({
            type:'PUT',
            url:'/admin/campingcar/price/by/' + carName,
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
};

function getData(index) {
    // index는
    // europe 비수기 0, europe 성수기 1, limousine 비수기 2, limousine 성수기 3, travel 비수기 4, travel 성수기 5
    twodays = parseFloat(document.getElementsByClassName('percent2')[index].value);
    threedays = parseFloat(document.getElementsByClassName('percent3')[index].value);
    fourdays = parseFloat(document.getElementsByClassName('percent4')[index].value);
    fivedays = parseFloat(document.getElementsByClassName('percent5')[index].value);
    sixdays = parseFloat(document.getElementsByClassName('percent6')[index].value);
    sevendays = parseFloat(document.getElementsByClassName('percent7')[index].value);
    eightdays = parseFloat(document.getElementsByClassName('percent8')[index].value);
    ninedays = parseFloat(document.getElementsByClassName('percent9')[index].value);
    tendays = parseFloat(document.getElementsByClassName('percent10')[index].value);
    elevendays = parseFloat(document.getElementsByClassName('percent11')[index].value);
    twelvedays = parseFloat(document.getElementsByClassName('percent12')[index].value);
    thirteendays = parseFloat(document.getElementsByClassName('percent13')[index].value);
    fourteendays = parseFloat(document.getElementsByClassName('percent14')[index].value);
    fifteendays = parseFloat(document.getElementsByClassName('percent15')[index].value);
    sixteendays = parseFloat(document.getElementsByClassName('percent16')[index].value);
    seventeendays = parseFloat(document.getElementsByClassName('percent17')[index].value);
    eighteendays = parseFloat(document.getElementsByClassName('percent18')[index].value);
    ninetinedays = parseFloat(document.getElementsByClassName('percent19')[index].value);
    twentydays = parseFloat(document.getElementsByClassName('percent20')[index].value);
    twentyonedays = parseFloat(document.getElementsByClassName('percent21')[index].value);
    twentytwodays = parseFloat(document.getElementsByClassName('percent22')[index].value);
    twentythreedays = parseFloat(document.getElementsByClassName('percent23')[index].value);
    twentyfourdays = parseFloat(document.getElementsByClassName('percent24')[index].value);
    twentyfivedays = parseFloat(document.getElementsByClassName('percent25')[index].value);
    twentysixdays = parseFloat(document.getElementsByClassName('percent26')[index].value);
    twentysevendays = parseFloat(document.getElementsByClassName('percent27')[index].value);
    twentyeightdays = parseFloat(document.getElementsByClassName('percent28')[index].value);
    twentyninedays = parseFloat(document.getElementsByClassName('percent29')[index].value);
    thirtydays = parseFloat(document.getElementsByClassName('percent30')[index].value);
}