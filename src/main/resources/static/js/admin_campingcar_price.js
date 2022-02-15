let twodays, threedays, fourdays, fivedays, sixdays, sevendays, eightdays, ninedays, tendays;
let elevendays, twelvedays, thirteendays, fourteendays, fifteendays, sixteendays, seventeendays, eighteendays, ninetinedays, twentydays;
let twentyonedays, twentytwodays, twentythreedays, twentyfourdays, twentyfivedays, twentysixdays, twentysevendays, twentyeightdays, twentyninedays, thirtydays;

function EditCampingcarPrice(carName, season) {
    const europeThreeHours = parseInt(document.getElementById('europe_three_hours').value.replace(/,/g, ""));
    const europeOffOneDay = parseInt(document.getElementById('europe_off_oneday').value.replace(/,/g, ""));
    const europeOnOneDay = parseInt(document.getElementById('europe_on_oneday').value.replace(/,/g, ""));
    const limousineThreeHours = parseInt(document.getElementById('limousine_three_hours').value.replace(/,/g, ""));
    const limousineOffOneDay = parseInt(document.getElementById('limousine_off_oneday').value.replace(/,/g, ""));
    const limousineOnOneDay = parseInt(document.getElementById('limousine_on_oneday').value.replace(/,/g, ""));
    const travelThreeHours = parseInt(document.getElementById('travel_three_hours').value.replace(/,/g, ""));
    const travelOffOneDay = parseInt(document.getElementById('travel_off_oneday').value.replace(/,/g, ""));
    const travelOnOneDay = parseInt(document.getElementById('travel_on_oneday').value.replace(/,/g, ""));
    let oneDay, threeHours;

    if (carName == 'europe') {
        threeHours = europeThreeHours;
        sessionStorage.setItem('campingPriceTab', carName);
        if (season === 0) {
            oneDay = europeOffOneDay;
            getData(0);
        } else {
            oneDay = europeOnOneDay;
            getData(1);
        }
    } else if (carName == 'limousine') {
        threeHours = limousineThreeHours;
        sessionStorage.setItem('campingPriceTab', carName);
        if (season === 0) {
            oneDay = limousineOffOneDay;
            getData(2);
        } else {
            oneDay = limousineOnOneDay;
            getData(3);
        }
    } else if (carName == 'travel') {
        threeHours = travelThreeHours;
        sessionStorage.setItem('campingPriceTab', carName);
        if (season === 0) {
            oneDay = travelOffOneDay;
            getData(4);
        } else {
            oneDay = travelOnOneDay;
            getData(5);
        }
    }

    let data = {
        season: season.toString(),
        deposit: '300000',
        threeHours: threeHours.toFixed(2),
        onedays: oneDay.toFixed(2),
        twodays: twodays.toFixed(2),
        threedays: threedays.toFixed(2),
        fourdays: fourdays.toFixed(2),
        fivedays: fivedays.toFixed(2),
        sixdays: sixdays.toFixed(2),
        sevendays: sevendays.toFixed(2),
        eightdays: eightdays.toFixed(2),
        ninedays: ninedays.toFixed(2),
        tendays: tendays.toFixed(2),
        elevendays: elevendays.toFixed(2),
        twelvedays: twelvedays.toFixed(2),
        thirteendays: thirteendays.toFixed(2),
        fourteendays: fourteendays.toFixed(2),
        fifteendays: fifteendays.toFixed(2),
        sixteendays: sixteendays.toFixed(2),
        seventeendays: seventeendays.toFixed(2),
        eighteendays: eighteendays.toFixed(2),
        ninetinedays: ninetinedays.toFixed(2),
        twentydays: twentydays.toFixed(2),
        twentyonedays: twentyonedays.toFixed(2),
        twentytwodays: twentytwodays.toFixed(2),
        twentythreedays: twentythreedays.toFixed(2),
        twentyfourdays: twentyfourdays.toFixed(2),
        twentyfivedays: twentyfivedays.toFixed(2),
        twentysixdays: twentysixdays.toFixed(2),
        twentysevendays: twentysevendays.toFixed(2),
        twentyeightdays: twentyeightdays.toFixed(2),
        twentyninedays: twentyninedays.toFixed(2),
        thirtydays: thirtydays.toFixed(2)
    }
    // console.log(data);

    if (confirm('가격을 수정하시겠습니까?')) {
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

function checkCampingPriceTab() {
    let tabStatus = sessionStorage.getItem('campingPriceTab');
    let navLinkList = document.getElementsByClassName('nav-link');
    let tabPaneList = document.getElementsByClassName('tab-pane');

    [...navLinkList].forEach((navLink) => {
        if (tabStatus && tabStatus === navLink.dataset.title) {
            navLink.classList.add('active');
            navLink.classList.add('show');
        } else {
            navLink.classList.remove('active');
            navLink.classList.remove('show');
        }
    });
    [...tabPaneList].forEach((tabpane) => {
        if (tabStatus && tabStatus === tabpane.id) {
            tabpane.classList.add('show');
            tabpane.classList.add('active');
        } else {
            tabpane.classList.remove('show');
            tabpane.classList.remove('active');
        }
    });

    if (!tabStatus) {
        navLinkList[0].classList.add('active');
        navLinkList[0].classList.add('show');
        tabPaneList[0].classList.add('active');
        tabPaneList[0].classList.add('show');
    }
}
window.onload = checkCampingPriceTab();