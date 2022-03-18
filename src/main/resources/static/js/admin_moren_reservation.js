// 모렌으로 데이터 전달, 예약하기
$('.moren-reservation-btn').click(function(e) {
    let id = e.target.name || e.target.dataset.index;

    // 예약 정보 받기
    let carNo = [...document.getElementsByClassName('carNo')].find(ele => ele.dataset.index === e.target.dataset.index).innerText;
    let carName = [...document.getElementsByClassName('carName')].find(ele => ele.dataset.index === e.target.dataset.index).innerText;
    let reservationNameElem = [...document.getElementsByClassName('reservationName')].find(ele => ele.dataset.index === e.target.dataset.index);
    let reservationPhoneElem = [...document.getElementsByClassName('reservationPhone')].find(ele => ele.dataset.index === e.target.dataset.index);
    let reservationDateElem = [...document.getElementsByClassName('reservationDate')].find(ele => ele.dataset.index === e.target.dataset.index);
    let reservationTimeElem = [...document.getElementsByClassName('reservationTime')].find(ele => ele.dataset.index === e.target.dataset.index);
    let addressElem = [...document.getElementsByClassName('reservationAddress')].find(ele => ele.dataset.index === e.target.dataset.index);
    let addressDetailElem = [...document.getElementsByClassName('reservationAddressDetail')].find(ele => ele.dataset.index === e.target.dataset.index);
    let rentTermElem = [...document.getElementsByClassName('rentTerm')].find(ele => ele.dataset.index === e.target.dataset.index);
    let costPerKm = [...document.getElementsByClassName('costPerKm')].find(ele => ele.dataset.index === e.target.dataset.index).innerText;
    let carAmountTotalList = document.getElementsByClassName('carAmountTotal');
    let carPriceList = document.getElementsByClassName('carPrice');
    let carTaxList = document.getElementsByClassName('carTax');
    let carDepositList = document.getElementsByClassName('carDeposit');
    let reservationDetailsElem = [...document.getElementsByClassName('reservationDetails')].find(ele => ele.dataset.index === e.target.dataset.index);
    let kilometerElem = [...document.getElementsByClassName('kilometer')].find(ele => ele.dataset.index === e.target.dataset.index);
    let reservationAgeElem = [...document.getElementsByClassName('reservationAge')].find(ele => ele.dataset.index === e.target.dataset.index);
    let reservationGuarantee = [...document.getElementsByClassName('reservationGuarantee')].find(ele => ele.dataset.index === e.target.dataset.index).innerText;
    let carCode = [...document.getElementsByClassName('carCode')].find(ele => ele.dataset.index === e.target.dataset.index).innerText;
    let pickupPlaceElem = [...document.getElementsByClassName('pickupPlace')].find(ele => ele.dataset.index === e.target.dataset.index);
    let reservationStatusElem = [...document.getElementsByClassName('reservationStatus')].find(ele => ele.dataset.index === e.target.dataset.index);
    let orderCodeElem = [...document.getElementsByClassName('orderCode')].find(ele => ele.dataset.index === e.target.dataset.index);


    let reservationName, reservationPhone, reservationDate, reservationTime, address, addressDetail, rentTerm, reservationStatus, orderCode;
    let carAmountTotal, carDeposit,reservationDetails, kilometer, reservationAge, pickupPlace, carPrice, carTax;

    reservationName = reservationNameElem.value || reservationNameElem.innerText;
    reservationPhone = reservationPhoneElem.value || reservationPhoneElem.innerText;
    reservationDate = reservationDateElem.value || reservationDateElem.innerText;
    reservationTime = reservationTimeElem.value || reservationTimeElem.innerText;
    address = addressElem.value || addressElem.innerText;
    addressDetail = addressDetailElem.value || addressDetailElem.innerText;
    rentTerm = rentTermElem.value || rentTermElem.innerText;
    reservationDetails = reservationDetailsElem.value || reservationDetailsElem.innerText;
    kilometer = kilometerElem.value || kilometerElem.innerText;
    reservationAge = reservationAgeElem.value || reservationAgeElem.innerText;
    pickupPlace = pickupPlaceElem.value || pickupPlaceElem.innerText;
    reservationStatus = reservationStatusElem.value || reservationStatusElem.innerText;
    orderCode = orderCodeElem.value || orderCodeElem.innerText;

    for (i=0; i < carAmountTotalList.length; i++) {
        if (e.target.dataset.index == carAmountTotalList[i].dataset.index) {
            let carAmountTotalString = carAmountTotalList[i].value || carAmountTotalList[i].innerText;
            carAmountTotal = carAmountTotalString.replace(/,/g, "");
        }
    };
    for (i=0; i < carPriceList.length; i++) {
        if (e.target.dataset.index == carPriceList[i].dataset.index) {
            carPrice = (carPriceList[i].innerText).replace(/,/g, "");
        }
    };
    for (i=0; i < carTaxList.length; i++) {
        if (e.target.dataset.index == carTaxList[i].dataset.index) {
            carTax = (carTaxList[i].innerText).replace(/,/g, "");
        }
    };
    for (i=0; i < carDepositList.length; i++) {
        if (e.target.dataset.index == carDepositList[i].dataset.index) {
            carDeposit = carDepositList[i].value || carDepositList[i].innerText;
            if (carDeposit == '상담' || carDeposit == '차량가격의 30%(상담문의)') {
                carDeposit = '0';
            } else {
                carDeposit = carDeposit.replace(/,/g, "");
            }
        }
    };


    var data = {
        carName: carName,
        carNo: carNo,
        kilometer: kilometer,
        reservationName: reservationName,
        reservationPhone: reservationPhone,
        reservationAge: reservationAge,
        reservationDate: reservationDate,
        reservationTime: reservationTime,
        reservationDetails: reservationDetails,
        reservationGuarantee: reservationGuarantee,
        address: address,
        addressDetail: addressDetail,
        carPrice: carPrice,
        carTax: carTax,
        carAmountTotal: carAmountTotal,
        carDeposit: carDeposit,
        rentTerm: rentTerm,
        costPerKm: costPerKm,
        carCode: carCode,
        reservationStatus: reservationStatus,
        pickupPlace: pickupPlace,
        orderCode: orderCode
    }

    // console.log(data);

    if (e.target.dataset.behavior === 'confirm') {
        if (confirm('예약을 확정하시겠습니까?')) {
            data.reservationStatus = '1';
            console.log(data);
            connectMoren();
        }
    } else if (e.target.dataset.behavior === 'cancel') {
        if (confirm('예약 확정을 취소하시겠습니까?')) {
            data.reservationStatus = '0';
            console.log(data);
            connectMoren();
        }
    } else if (e.target.dataset.behavior === 'edit') {
        if (confirm('예약 내용을 수정하시겠습니까?')) {
            console.log(data);
            connectMoren();
        }
    }

    function connectMoren() {
        $.ajax({
            type:'PUT',
            url:'/moren/reservation/apply/' + id,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('처리되었습니다.');
            } else {
                alert('처리에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/moren/reservation/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
});

// detail에서 렌트 기간 선택하면 약정 주행거리 선택 보여주기
function displayNextOptions(e) {
    let monthKilometer = ["2000km", "2500km", "3000km", "4000km", "기타"];
    let yearKilometer = ["20000km", "30000km", "40000km", "기타"];
    let selectKilometer = document.getElementById('selectKilometer');

    let displaySelect;

    if (e.value == "한달") {
        displaySelect = monthKilometer;
    } else if (e.value == "12개월" || e.value == "24개월") {
        displaySelect = yearKilometer;
    };

    selectKilometer.options.length = 0;

    for (x in displaySelect) {
        let option = document.createElement('option');
        option.value = displaySelect[x];
        option.innerText = displaySelect[x];
        selectKilometer.appendChild(option);
    };
}

// detail에서 총 렌트료 수정하면 공급가, 부가세 자동 변경
function changePrice(e) {
    let carPrice = document.getElementById('displayCarPrice');
    let carTax = document.getElementById('displayCarTax');
    let input = parseInt(e.value);

    if (e.value == '') {
    } else {
        if (isNaN(input) == true) {
            alert('숫자로만 작성해주세요!');
        } else {
            let calCarPrice = Math.round((input/11*10)).toLocaleString();
            let calCarTax = Math.round((input/11)).toLocaleString();
            carPrice.innerText = calCarPrice;
            carTax.innerText = calCarTax;
        }
    }
};