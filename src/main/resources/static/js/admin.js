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

// 할인 적용하기

const make_discount = () => {
    if (document.getElementById("carNo").value == ""){
        alert('차량 번호를 입력해주세요.')
        return
    }

    if (document.getElementById("discount").value == ""){
        alert('할인 가격을 입력해주세요.')
        return
    }

    if (document.getElementById("discount-description").value == ""){
        alert('할인 설명을 입력해주세요.')
        return
    }

    let carNo = $("#carNo").val().replace(/(\s*)/g,""); // 공백 제거

    var data = {
        carNo : carNo,
        discount : $("#discount").val(),
        description: $("#discount-description").val()
    };
    // console.log(data);

    $.ajax({
        type:'POST',
        url:'/admin/discount',
        dataType:'json',
        contentType : 'application/json; charset=utf-8',
        data : JSON.stringify(data)
    }).done(function (result) {
        if (result.result == 1) {
            alert('할인 가격이 적용되었습니다.');
        } else if (result.result == 0) {
            alert('할인 가격이 적용에 문제가 생겼습니다.');
        };
        window.location.href = '/admin/discount/menu';
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}


// 할인 가격 수정하기

$('.money-update-btn').click(function(e) {
    let carNo = e.target.dataset.index;
    let originalDiscount = document.querySelectorAll('.editDiscount');
    let editDiscount = prompt("수정할 할인가(%)를 입력하세요.", "");

    // 할인 퍼센트 입력 안해줬을 경우 -> 원래대로
    if (!editDiscount) {
        for (let i = 0; i < originalDiscount.length; i++) {
            if (originalDiscount[i].dataset.index === carNo) {
                editDiscount = originalDiscount[i].innerText;
            }
        }
    } else if (editDiscount == 0) {
        alert('0% 할인을 할 수 없습니다. 삭제 버튼을 이용해주세요.');
    } else {
        $.ajax({
            type:'PUT',
            url:'/admin/discount/'+ carNo + '/' + editDiscount,
            dataType:'json',
            contentType : 'application/json; charset=utf-8'
        }).done(function (result) {
            if (result.result == 1) {
                alert('할인 가격이 수정되었습니다.');
            } else if (result.result == 0) {
                alert('할인 가격 수정에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/discount/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
})

// 할인 설명 수정하기

$('.description-update-btn').click(function(e) {
    let carNo = e.target.dataset.index;
    let originalDescription = document.querySelectorAll('.discountDescription');
    let editDescription = prompt("수정할 할인 설명을 입력하세요.", "");

    // 할인 설명 입력 안해줬을 경우 -> 원래대로
    if (!editDescription) {
        for (let i = 0; i < originalDescription.length; i++) {
            if (originalDescription[i].dataset.index === carNo) {
                editDescription = originalDescription[i].innerText;
            }
        }
    } else {
        $.ajax({
            type:'PUT',
            url:'/admin/description/'+ carNo + '/' + editDescription,
            dataType:'json',
            contentType : 'application/json; charset=utf-8'
        }).done(function (result) {
            if (result.result == 1) {
                alert('할인 설명이 수정되었습니다.');
            } else if (result.result == 0) {
                alert('할인 설명 수정에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/discount/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
})


// 할인 삭제하기

$('.delete-btn').click(function(e) {
    let carNo = e.target.dataset.index;
    console.log(carNo);

    if (confirm("할인 가격을 삭제하시겠습니까?")) {
        $.ajax({
            type:'GET',
            url:'/admin/discount/delete/'+ carNo,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
        }).done(function (result) {
            if (result.result == 1) {
                alert('할인 가격이 삭제되었습니다.');
            } else if (result.result == 0) {
                alert('할인 가격 삭제에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/discount/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
})



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

    let id = e.target.name || e.target.dataset.index;
    let carNo, carName, reservationName, reservationPhone, reservationDate, reservationTime, address, addressDetail, rentTerm, costPerKm, reservationStatus;
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
        pickupPlace: pickupPlace
    }

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
$('.reservation-completely-delete-btn').click(function(e) {
    let reservationId = e.target.dataset.index;
    let completeDeleteConfirm = confirm('예약 신청 목록에서 삭제 하시겠습니까?');

    if (completeDeleteConfirm) {
        $.ajax({
            type:'DELETE',
            url:'/moren/reservation/'+ reservationId,
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
});

// setting에서 캠핑카 내용 수정 버튼
let saveBtn = document.getElementById('saveBtn');
saveBtn.addEventListener('click', () => {
    let editedCamperPrice = document.getElementById('editedCamperPrice');
    let editedRentPolicy = document.getElementById('editedRentPolicy');
    let editedRentInsurance = document.getElementById('editedRentInsurance');
    let editedDriverLicense = document.getElementById('editedDriverLicense');
    let editedRentRule = document.getElementById('editedRentRule');
    let editedRefundPolicy = document.getElementById('editedRefundPolicy');
    let editedEuropeBasicOption = document.getElementById('editedEuropeBasicOption');
    let editedEuropeFacility = document.getElementById('editedEuropeFacility');
    let editedLimousineBasicOption = document.getElementById('editedLimousineBasicOption');
    let editedLimousineFacility = document.getElementById('editedLimousineFacility');
    let editedTravelBasicOption = document.getElementById('editedTravelBasicOption');
    let editedTravelFacility = document.getElementById('editedTravelFacility');

    let data = {
        camper_price: editedCamperPrice.value,
        rent_policy: editedRentPolicy.value,
        rent_insurance: editedRentInsurance.value,
        driver_license: editedDriverLicense.value,
        rent_rule: editedRentRule.value,
        refund_policy: editedRefundPolicy.value,
        europe_basic_option: editedEuropeBasicOption.value,
        limousine_basic_option: editedLimousineBasicOption.value,
        travel_basic_option: editedTravelBasicOption.value,
        europe_facility: editedEuropeFacility.value,
        limousine_facility: editedLimousineFacility.value,
        travel_facility: editedTravelFacility.value
    }
    // console.log(data);

    $.ajax({
        type:'POST',
        url:'/admin/setting',
        dataType:'json',
        contentType : 'application/json; charset=utf-8',
        data : JSON.stringify(data)
    }).done(function (result) {
        if (result.result == 1) {
            alert('완료되었습니다.');
            window.location.href = '/admin/setting/menu';
        } else {
            alert('문제가 생겼습니다.');
        };
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
})

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
    let total = document.getElementById('total').innerText;
    let totalHalf = document.getElementById('totalHalf').innerText;
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

    console.log(data);

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
            window.location.href = '/admin/campingcar/menu';
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
function setReservationOnMenu(event) {
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
    let reservationList = document.getElementsByClassName('campingReservationList');
    let idList = document.getElementsByClassName('campingReservationIdList');
    let orderCodeList = document.getElementsByClassName('orderCodeList');
    let carType, rentDate, rentTime, returnDate, returnTime, day, extraTime, deposit, total, totalHalf, name, phone, depositor, detail, agree, reservation, orderCode;
    let targetIndex = event.dataset.index;

    for (i=0; i < carTypeList.length; i++) {
        if (targetIndex == carTypeList[i].dataset.index) {
            carType = carTypeList[i].innerText;
        };
    };

    for (i=0; i < rentDateList.length; i++) {
        if (targetIndex == rentDateList[i].dataset.index) {
            rentDate = rentDateList[i].innerText;
        };
    };

    for (i=0; i < rentTimeList.length; i++) {
        if (targetIndex == rentTimeList[i].dataset.index) {
            rentTime = rentTimeList[i].innerText;
        };
    };

    for (i=0; i < returnDateList.length; i++) {
        if (targetIndex == returnDateList[i].dataset.index) {
            returnDate = returnDateList[i].innerText;
        };
    };

    for (i=0; i < returnTimeList.length; i++) {
        if (targetIndex == returnTimeList[i].dataset.index) {
            returnTime = returnTimeList[i].innerText;
        };
    };

    for (i=0; i < dayList.length; i++) {
        if (targetIndex == dayList[i].dataset.index) {
            day = dayList[i].innerText;
        };
    };

    for (i=0; i < extraTimeList.length; i++) {
        if (targetIndex == extraTimeList[i].dataset.index) {
            extraTime = parseInt(extraTimeList[i].innerText);
        };
    };

    for (i=0; i < depositList.length; i++) {
        if (targetIndex == depositList[i].dataset.index) {
            deposit = parseInt(depositList[i].innerText);
        };
    };

    for (i=0; i < totalList.length; i++) {
        if (targetIndex == totalList[i].dataset.index) {
            total = parseInt(totalList[i].innerText);
        };
    };

    for (i=0; i < totalHalfList.length; i++) {
        if (targetIndex == totalHalfList[i].dataset.index) {
            totalHalf = parseInt(totalHalfList[i].innerText);
        };
    };

    for (i=0; i < nameList.length; i++) {
        if (targetIndex == nameList[i].dataset.index) {
            name = nameList[i].innerText;
        };
    };

    for (i=0; i < phoneList.length; i++) {
        if (targetIndex == phoneList[i].dataset.index) {
            phone = phoneList[i].innerText;
        };
    };
    for (i=0; i < depositorList.length; i++) {
        if (targetIndex == depositorList[i].dataset.index) {
            depositor = depositorList[i].innerText;
        };
    };
    for (i=0; i < detailList.length; i++) {
        if (targetIndex == detailList[i].dataset.index) {
            detail = detailList[i].innerText;
        };
    };
    for (i=0; i < agreeList.length; i++) {
        if (targetIndex == agreeList[i].dataset.index) {
            agree = parseInt(agreeList[i].innerText);
        };
    };
    for (i=0; i < idList.length; i++) {
        if (targetIndex == idList[i].dataset.index) {
            id = idList[i].innerText;
        };
    };
    for (i=0; i < orderCodeList.length; i++) {
        if (orderCode == orderCodeList[i].dataset.index) {
            orderCode = orderCodeList[i].innerText;
        };
    };
    reservation = 1;


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