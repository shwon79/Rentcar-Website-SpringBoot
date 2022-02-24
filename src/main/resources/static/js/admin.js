// sidebar
$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
        $(this).toggleClass('active');
    });
    const campingSubmenuBtn = document.getElementById('camping-submenu-btn');
    campingSubmenuBtn.addEventListener('click', () => {
        document.getElementById('campingcarSubmenu').classList.toggle('camping-open');
        if (document.getElementById('campingcarSubmenu').classList.contains('camping-open')) {
            document.getElementById('price-submenu-btn').style.top = '433px';
        } else {
            document.getElementById('price-submenu-btn').style.top = '190px';
        }
        campingSubmenuBtn.classList.toggle('active-span');
    });
    const priceSubmenuBtn = document.getElementById('price-submenu-btn');
    priceSubmenuBtn.addEventListener('click', () => {
        document.getElementById('priceSubMenu').classList.toggle('price-open');
        priceSubmenuBtn.classList.toggle('active-span');
    });
});

// 세부메뉴 클릭 시 sessionStorage에 저장
function setActiveMenu(type) {
    sessionStorage.setItem('activeMenu', type);
}

// 세부매뉴 기억하고 보여주기
function displayActiveMenu() {
    let menu = sessionStorage.getItem('activeMenu');
    const campingSubmenuBtn = document.getElementById('camping-submenu-btn');
    const campingcarSubmenu = document.getElementById('campingcarSubmenu');
    const priceSubmenuBtn = document.getElementById('price-submenu-btn');
    const priceSubMenu = document.getElementById('priceSubMenu');

    if (menu === 'campingCar') {
        campingSubmenuBtn.classList.add('active-span');
        campingcarSubmenu.classList.add('camping-open');
        priceSubmenuBtn.style.top = '433px';
    } else if (menu === 'rentCar') {
        priceSubmenuBtn.classList.add('active-span');
        priceSubMenu.classList.add('price-open');
    };
}
window.onload = displayActiveMenu();

//숫자 사이에 콤마 넣기
let number = document.getElementsByClassName("number");

function numberWithCommas() {
    for (let i = 0; i < number.length; i++) {
        if (number[i].innerText === '상담') {
            number[i].innerText = '상담';
        } else {
            const numberWithComma = parseInt(number[i].innerText).toLocaleString();
            number[i].innerText = numberWithComma;
        }
    }
}

$('.number').ready(numberWithCommas());

// 모렌으로 데이터 전달, 예약하기
$('.moren-reservation-btn').click(function(e) {
    // 예약 정보 받기
    let carNoList = document.getElementsByClassName('carNo');
    let carNameList = document.getElementsByClassName('carName');
    let reservationNameList = document.getElementsByClassName('reservationName');
    let reservationPhoneList = document.getElementsByClassName('reservationPhone');
    let reservationDateList = document.getElementsByClassName('reservationDate');
    let reservationTimeList = document.getElementsByClassName('reservationTime');
    let addressList = document.getElementsByClassName('reservationAddress');
    let addressDetailList = document.getElementsByClassName('reservationAddressDetail');
    let rentTermList = document.getElementsByClassName('rentTerm');
    let costPerKmList = document.getElementsByClassName('costPerKm');
    let carAmountTotalList = document.getElementsByClassName('carAmountTotal');
    let carPriceList = document.getElementsByClassName('carPrice');
    let carTaxList = document.getElementsByClassName('carTax');
    let carDepositList = document.getElementsByClassName('carDeposit');
    let reservationDetailsList = document.getElementsByClassName('reservationDetails');
    let kilometerList = document.getElementsByClassName('kilometer');
    let reservationAgeList = document.getElementsByClassName('reservationAge');
    let reservationGuaranteeList = document.getElementsByClassName('reservationGuarantee');
    let carCodeList = document.getElementsByClassName('carCode');
    let pickupPlaceList = document.getElementsByClassName('pickupPlace');
    let reservationStatusList = document.getElementsByClassName('reservationStatus');
    let orderCodeList = document.getElementsByClassName('orderCode');

    let id = e.target.name || e.target.dataset.index;
    let carNo, carName, reservationName, reservationPhone, reservationDate, reservationTime, address, addressDetail, rentTerm, costPerKm, reservationStatus, orderCode;
    let carAmountTotal, carDeposit,reservationDetails, kilometer, reservationAge, reservationGuarantee, carCode, pickupPlace, carPrice, carTax;

    for (i=0; i < carNoList.length; i++) {
        if (e.target.dataset.index == carNoList[i].dataset.index) {
            carNo = carNoList[i].innerText;
        }
    };
    for (i=0; i < carNameList.length; i++) {
        if (e.target.dataset.index == carNameList[i].dataset.index) {
            carName = carNameList[i].innerText;
        }
    };
    for (i=0; i < reservationNameList.length; i++) {
        if (e.target.dataset.index == reservationNameList[i].dataset.index) {
            reservationName = reservationNameList[i].value || reservationNameList[i].innerText;
        }
    };
    for (i=0; i < reservationPhoneList.length; i++) {
        if (e.target.dataset.index == reservationPhoneList[i].dataset.index) {
            reservationPhone = reservationPhoneList[i].value || reservationPhoneList[i].innerText;
        }
    };
    for (i=0; i < reservationDateList.length; i++) {
        if (e.target.dataset.index == reservationDateList[i].dataset.index) {
            reservationDate = reservationDateList[i].value || reservationDateList[i].innerText;
        }
    };
    for (i=0; i < reservationTimeList.length; i++) {
        if (e.target.dataset.index == reservationTimeList[i].dataset.index) {
            reservationTime = reservationTimeList[i].value || reservationTimeList[i].innerText;
        }
    };
    for (i=0; i < addressList.length; i++) {
        if (e.target.dataset.index == addressList[i].dataset.index) {
            address = addressList[i].value || addressList[i].innerText;
        }
    };
    for (i=0; i < addressDetailList.length; i++) {
        if (e.target.dataset.index == addressDetailList[i].dataset.index) {
            addressDetail = addressDetailList[i].value || addressDetailList[i].innerText;
        }
    };
    for (i=0; i < rentTermList.length; i++) {
        if (e.target.dataset.index == rentTermList[i].dataset.index) {
            rentTerm = rentTermList[i].value || rentTermList[i].innerText;
        }
    };
    for (i=0; i < costPerKmList.length; i++) {
        if (e.target.dataset.index == costPerKmList[i].dataset.index) {
            costPerKm = costPerKmList[i].innerText;
        }
    };
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
    for (i=0; i < reservationGuaranteeList.length; i++) {
        if (e.target.dataset.index == reservationGuaranteeList[i].dataset.index) {
            reservationGuarantee = reservationGuaranteeList[i].innerText;
        }
    };
    for (i=0; i < reservationDetailsList.length; i++) {
        if (e.target.dataset.index == reservationDetailsList[i].dataset.index) {
            reservationDetails = reservationDetailsList[i].value || reservationDetailsList[i].innerText;
        }
    };
    for (i=0; i < kilometerList.length; i++) {
        if (e.target.dataset.index == kilometerList[i].dataset.index) {
            kilometer = kilometerList[i].value || kilometerList[i].innerText;
        }
    };
    for (i=0; i < reservationAgeList.length; i++) {
        if (e.target.dataset.index == reservationAgeList[i].dataset.index) {
            reservationAge = reservationAgeList[i].value || reservationAgeList[i].innerText;
        }
    };
    for (i=0; i < carCodeList.length; i++) {
        if (e.target.dataset.index == carCodeList[i].dataset.index) {
            carCode = carCodeList[i].innerText;
        }
    };
    for (i=0; i < pickupPlaceList.length; i++) {
        if (e.target.dataset.index == pickupPlaceList[i].dataset.index) {
            pickupPlace = pickupPlaceList[i].value || pickupPlaceList[i].innerText;
        }
    };
    for (i=0; i < reservationStatusList.length; i++) {
        if (e.target.dataset.index == reservationStatusList[i].dataset.index) {
            reservationStatus = reservationStatusList[i].value || reservationStatusList[i].innerText;
        }
    };
    for (i=0; i < orderCodeList.length; i++) {
        if (e.target.dataset.index == orderCodeList[i].dataset.index) {
            orderCode = orderCodeList[i].value || orderCodeList[i].innerText;
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
})

// 렌트 기간 선택하면 약정 주행거리 선택 보여주기
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

//총 렌트료 수정하면 공급가, 부가세 자동 변경
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

//모렌 예약 신청 아예 삭제
$('.moren-completely-delete-btn').click(function() {
    let completeDeleteConfirm = confirm('삭제를 하시면 현재 admin 페이지에 반영이 되며, 프라임클럽 사이트에는 반영되지 않습니다. 예약 신청 목록에서 삭제 하시겠습니까?');
    let selectedOptions = document.querySelectorAll('input[name="selected_moren_reservation"]:checked');
    let id;

    if (completeDeleteConfirm) {
        for (i=0; i < selectedOptions.length; i++) {
            id = selectedOptions[i].value;
            // console.log(id);

            $.ajax({
                type:'DELETE',
                url:'/moren/reservation/'+ id,
                dataType:'json',
                contentType : 'application/json; charset=utf-8',
            }).done(function (result) {
                if (result.result == 1) {
                    alert('삭제 되었습니다.');
                } else if (result.result == 0) {
                    alert('삭제에 문제가 생겼습니다.');
                };
                window.location.href = '/admin/moren/reservation/menu';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        }
    }
});

//캠핑카 예약 신청 아예 삭제
$('.camping-completely-delete-btn').click(function() {
    let completeDeleteConfirm = confirm('삭제를 하시면 현재 admin 페이지에 반영이 되며, 프라임클럽 사이트에는 반영되지 않습니다. 예약 신청 목록에서 삭제 하시겠습니까?');
    let selectedOptions = document.querySelectorAll('input[name="selected_camping_reservation"]:checked');
    let id;

    if (completeDeleteConfirm) {
        for (i=0; i < selectedOptions.length; i++) {
            id = selectedOptions[i].value;
            // console.log(id);

            $.ajax({
                type:'DELETE',
                url:'/admin/campingcar/reservation/'+ id,
                dataType:'json',
                contentType : 'application/json; charset=utf-8',
            }).done(function (result) {
                if (result.result == 1) {
                    alert('삭제 되었습니다.');
                } else if (result.result == 0) {
                    alert('삭제에 문제가 생겼습니다.');
                };
                window.location.href = '/admin/campingcar/menu';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        }
    }
});

// 캠핑카 예약 확정, 확정 취소, 삭제 버튼_디테일 페이지
function setCampingReserve(behavior) {
    let carType = document.getElementById('carType').value.toLowerCase();
    let rentDate = document.getElementById('rentDate').value;
    let rentTime = document.getElementById('rentTime').value;
    let returnDate = document.getElementById('returnDate').value;
    let returnTime = document.getElementById('returnTime').value;
    let day = document.getElementById('day').innerText;
    let extraTime = document.getElementById('extraTime').innerText;
    let deposit = document.getElementById('deposit').value;
    let total = document.getElementById('total').value;
    let totalHalf = document.getElementById('totalHalf').value;
    let name = document.getElementById('name').value;
    let phone = document.getElementById('phone').value;
    let depositor = document.getElementById('depositor').value;
    let detail = document.getElementById('detail').value;
    let agree = parseInt(document.getElementById('agree').innerText);
    let reservation = document.getElementById('campingReservation').innerText;
    let id = document.getElementById('campingReservationId').innerText;
    let orderCode = document.getElementById('orderCode').innerText;
    let regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

    deposit = parseInt(deposit.replace(/,/g, ""));
    total = parseInt(total.replace(/,/g, ""));
    totalHalf = parseInt(totalHalf.replace(/,/g, ""));

    if (behavior === 'confirm') {
        reservation = 1;
    } else if (behavior === 'delete') {
        reservation = 0;
    } else if (behavior === 'edit') {
        if (reservation == '예약 O') {
            reservation = 1;
        } else if (reservation == '예약 X') {
            reservation = 0;
        };
    };

    let rentYear = parseInt(rentDate.split('-')[0]);
    let rentMonth = parseInt(rentDate.split('-')[1]);
    let rentDay = parseInt(rentDate.split('-')[2]);

    rentDate = rentYear + '-' + rentMonth + '-' + rentDay;

    let returnYear = parseInt(returnDate.split('-')[0]);
    let returnMonth = parseInt(returnDate.split('-')[1]);
    let returnDay = parseInt(returnDate.split('-')[2]);

    returnDate = returnYear + '-' + returnMonth + '-' + returnDay;

    if (extraTime == '추가 시간 없음') {
        extraTime = 0;
    } else if (extraTime == '3시간') {
        extraTime = 1;
    };

    let data = {
        carType: carType,
        rentDate: rentDate,
        rentTime: rentTime,
        returnDate: returnDate,
        returnTime: returnTime,
        day: day,
        extraTime: extraTime,
        deposit: deposit,
        total: total,
        totalHalf: totalHalf,
        name: name,
        phone: phone,
        depositor: depositor,
        detail: detail,
        agree: agree,
        reservation: reservation,
        orderCode: orderCode
    }

    // console.log(data);

    if (regPhone.test(phone) === false) {
        alert("연락처를 '010-1234-5678'의 형태로 작성해주세요.");
    } else {
        if (behavior === 'confirm') {
            if (confirm('예약을 확정하시겠습니까?')) {
                setCampingData();
            }
        } else if (behavior === 'delete') {
            if (confirm('예약 확정을 취소하시겠습니까?')) {
                setCampingData();
            }
        } else if (behavior === 'edit') {
            if (confirm('예약 내용을 수정하시겠습니까?')) {
                setCampingData();
            }
        }
    }

    function setCampingData() {
        $.ajax({
            type:'PUT',
            url:'/admin/campingcar/reservation/' + id,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('처리되었습니다.');
            } else if (result.result == 0) {
                alert('처리에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/campingcar/detail/' + id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
};

function displayExtraCampingData() {
    let rentDate = document.getElementById('rentDate').value;
    let rentTime = parseInt(document.getElementById('rentTime').value);
    let returnDate = document.getElementById('returnDate').value;
    let returnTime = parseInt(document.getElementById('returnTime').value);
    let day = document.getElementById('day');
    let extraTime = document.getElementById('extraTime');

    let changedDate = new Date(rentDate);
    let changedReturn = new Date(returnDate);
    let currDay = 24 * 60 * 60 * 1000;// 차이를 일 단위로 환산
    let diffDate = ((changedReturn - changedDate)/currDay);
    let diffTime = returnTime - rentTime;

    if (diffTime <= 3 && diffTime > 0) {
        extraTime.innerText = '3시간';
    } else if (diffTime == 0 || diffTime < 0) {
        extraTime.innerText = '추가 시간 없음';
    } else if (diffTime > 3) {
        extraTime.innerText = '추가 시간 없음';
        diffDate = diffDate + 1;
    }

    day.innerText = diffDate + '일';

}

// 캠핑카 예약 확정 버튼_메뉴 페이지
function setReservationOnMenu(event, behavior) {
    let carTypeList = document.getElementsByClassName('carTypeList');
    let rentDateList = document.getElementsByClassName('rentDateList');
    let rentTimeList = document.getElementsByClassName('rentTimeList');
    let returnDateList = document.getElementsByClassName('returnDateList');
    let returnTimeList = document.getElementsByClassName('returnTimeList');
    let dayList = document.getElementsByClassName('dayList');
    let extraTimeList = document.getElementsByClassName('extraTimeList');
    let depositList = document.getElementsByClassName('depositList');
    let totalList = document.getElementsByClassName('totalList');
    let totalHalfList = document.getElementsByClassName('totalHalfList');
    let nameList = document.getElementsByClassName('nameList');
    let phoneList = document.getElementsByClassName('phoneList');
    let depositorList = document.getElementsByClassName('depositorList');
    let detailList = document.getElementsByClassName('detailList');
    let agreeList = document.getElementsByClassName('agreeList');
    let idList = document.getElementsByClassName('campingReservationIdList');
    let orderCodeList = document.getElementsByClassName('orderCodeList');
    let carType, rentDate, rentTime, returnDate, returnTime, day, extraTime, deposit, total, totalHalf, name, phone, depositor, detail, agree, reservation, orderCode;
    let targetIndex = event.dataset.index;

    carType = [...carTypeList].find(carType => carType.dataset.index === targetIndex).innerText;
    rentDate = [...rentDateList].find(rentDate => rentDate.dataset.index === targetIndex).innerText;
    rentTime = [...rentTimeList].find(rentTime => rentTime.dataset.index === targetIndex).innerText;
    returnDate = [...returnDateList].find(returnDate => returnDate.dataset.index === targetIndex).innerText;
    returnTime = [...returnTimeList].find(returnTime => returnTime.dataset.index === targetIndex).innerText;
    day = [...dayList].find(day => day.dataset.index === targetIndex).innerText;
    extraTime = parseInt([...extraTimeList].find(extraTime => extraTime.dataset.index === targetIndex).innerText);
    deposit = parseInt([...depositList].find(deposit => deposit.dataset.index === targetIndex).innerText);
    total = parseInt([...totalList].find(total => total.dataset.index === targetIndex).innerText);
    totalHalf = parseInt([...totalHalfList].find(totalHalf => totalHalf.dataset.index === targetIndex).innerText);
    name = [...nameList].find(name => name.dataset.index === targetIndex).innerText;
    phone = [...phoneList].find(phone => phone.dataset.index === targetIndex).innerText;
    depositor = [...depositorList].find(depositor => depositor.dataset.index === targetIndex).innerText;
    detail = [...detailList].find(detail => detail.dataset.index === targetIndex).innerText;
    agree = parseInt([...agreeList].find(agree => agree.dataset.index === targetIndex).innerText);
    id = [...idList].find(id => id.dataset.index === targetIndex).innerText;
    orderCode = [...orderCodeList].find(orderCode => orderCode.dataset.index === targetIndex).innerText;

    if (behavior === 'confirm') {
        reservation = 1;
    } else if (behavior === 'delete') {
        reservation = 0;
    };

    let data = {
        carType: carType,
        rentDate: rentDate,
        rentTime: rentTime,
        returnDate: returnDate,
        returnTime: returnTime,
        day: day,
        extraTime: extraTime,
        deposit: deposit,
        total: total,
        totalHalf: totalHalf,
        name: name,
        phone: phone,
        depositor: depositor,
        detail: detail,
        agree: agree,
        reservation: reservation,
        orderCode: orderCode
    }

    // console.log(data);

    if (behavior == 'confirm') {
        if (confirm('해당 예약을 확정하시겠습니까?')) {
            sendingData();
        }
    } else if (behavior == 'delete') {
        if (confirm('확정된 예약을 취소하시겠습니까?')) {
            sendingData();
        }
    }

    function sendingData() {
        $.ajax({
            type:'PUT',
            url:'/admin/campingcar/reservation/' + id,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('처리되었습니다.');
            } else if (result.result == 0) {
                alert('처리에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/campingcar/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };
};