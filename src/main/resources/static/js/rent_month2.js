let sortType = 'desc';

// rent/month/new 차량 정렬 화살표 클릭
function sortAvailableContent(index, isNumber) {
    let table = document.getElementsByClassName('table_available')

    sortType = (sortType == 'asc') ? 'desc' : 'asc';

    let checkSort = true;
    let rows = table[0].rows;

    while (checkSort) { // 현재와 다음만 비교하기때문에 위치변경되면 다시 정렬해준다.
        checkSort = false;

        for (let i = 1; i < (rows.length - 1); i++) {
            let fCell, sCell;

            if (isNumber) {
                fCell = parseInt(rows[i].cells[index].innerText.replace(/,/g, ''));
                sCell = parseInt(rows[i + 1].cells[index].innerText.replace(/,/g, ''));
            } else {
                fCell = rows[i].cells[index].innerText.toUpperCase();
                sCell = rows[i + 1].cells[index].innerText.toUpperCase();
            };

            let row = rows[i];

            // 오름차순<->내림차순
            if ((sortType == 'asc' && fCell > sCell) || (sortType == 'desc' && fCell < sCell)) {
                row.parentNode.insertBefore(row.nextSibling, row);
                checkSort = true;
            }
        }
    }
}

function sortExpectedContent(index, isNumber) {
    let table = document.getElementsByClassName('table_expected')

    sortType = (sortType == 'asc') ? 'desc' : 'asc';

    let checkSort = true;
    let rows = table[0].rows;

    while (checkSort) { // 현재와 다음만 비교하기때문에 위치변경되면 다시 정렬해준다.
        checkSort = false;

        for (let i = 1; i < (rows.length - 1); i++) {
            let fCell, sCell;

            if (isNumber) {
                fCell = parseInt(rows[i].cells[index].innerText.replace(/,/g, ''));
                sCell = parseInt(rows[i + 1].cells[index].innerText.replace(/,/g, ''));
            } else {
                fCell = rows[i].cells[index].innerText.toUpperCase();
                sCell = rows[i + 1].cells[index].innerText.toUpperCase();
            };

            let row = rows[i];

            // 오름차순<->내림차순
            if ((sortType == 'asc' && fCell > sCell) || (sortType == 'desc' && fCell < sCell)) {
                row.parentNode.insertBefore(row.nextSibling, row);
                checkSort = true;
            }
        }
    }
}

// rent/month/new 차량 정렬 시 화살표 아이콘
function displaySortImage(target) {
    let targetI = [...document.getElementsByTagName("i")].find(i => i.dataset.id === target);

    if (sortType === 'desc') {
        targetI.classList.remove('fa-arrow-up');
        targetI.classList.add('fa-arrow-down');
    } else if (sortType === 'asc') {
        targetI.classList.add('fa-arrow-up');
        targetI.classList.remove('fa-arrow-down');
    }
}

function sendData(){
    let carType = document.querySelector("input[name='carType']:checked").value;
    let rentTerm = document.querySelector("input[name='rentTerm']:checked").value;
    let kilometer = document.querySelector("input[name='kilometer']:checked").value;

    window.location = '/rent/month/lookup/' + carType + '/' + kilometer + '/' + rentTerm;
    // document.getElementById('select_wrapper').submit();
}


// 월렌트실시간 상담요청
function make_monthly_rent_reservation (e) {

    if (document.getElementById("reservation-simple-name").value == ""){
        alert('성함을 입력해주세요.')
        return
    }

    if (document.getElementById("reservation-simple-phone").value == ""){
        alert('전화번호를 입력해주세요.')
        return
    }

    let rentTerm = document.getElementById('forPostRentTerm').innerText;
    let carDeposit = document.getElementById('forPostDeposit').innerText;
    let carOld = document.getElementById('forPostCarOld').innerText;
    let ageLimit = document.getElementById('selectAge1').value;
    let kilometer = document.getElementById('forPostKilometer').innerText;
    let carPrice = parseInt(document.getElementById('carPrice').innerText.replace(/,/g, ""));
    let reservationPhone = $("#reservation-simple-phone").val();
    let carTax = carPrice / 10;
    let carAmountTotal = carPrice + carTax;

    let regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

    if (ageLimit === 'upper26') {
        ageLimit = '만 26세 이상';
    } else if (ageLimit === 'upper21') {
        ageLimit = '만 21세 이상~만 26세 미만';
    }

    let data = {
        name : $("#reservation-simple-name").val(),
        phoneNo : reservationPhone,
        detail : $("#reservation-simple-details").val(),
        product: rentTerm,
        title: '월렌트실시간',
        category1: '',
        category2: '',
        car_name : document.getElementsByClassName("carName")[0].innerHTML,
        mileage: kilometer,
        deposit: carDeposit.toString(),
        option: '',
        price: carAmountTotal.toString(),
        age_limit: ageLimit,
        car_num : document.getElementsByClassName("carNo")[0].innerHTML,
        region: '',
        resDate: '',
        carAge: carOld
    };

    // console.log(data);

    let check1 = document.getElementById("agree1").checked;
    let check2 = document.getElementById("agree2").checked;
    let check3 = document.getElementById("agree3").checked;

    if (check1 != true || check2 != true || check3 != true) {
        alert("약관에 동의해주세요.");
    } else if (regPhone.test(reservationPhone) == false) {
        alert("연락처를 '010-1234-5678' 형식으로 입력해주세요.");
    } else {
            $.ajax({
                type : 'POST',
                url : '/reservation/apply',
                dataType : 'json',
                contentType : 'application/json; charset=utf-8',
                data : JSON.stringify(data)
            }).done(function () {
                alert('상담 신청이 완료되었습니다.');
                window.location.href = '/index';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
    }
}

//숫자 사이에 콤마 넣기 시작
const number = document.querySelectorAll(".number");

function numberWithCommas() {
    for (let i = 0; i < number.length; i++) {
        if (number[i].innerText === '상담') {
            number[i].innerText = '상담';
        } else {
            const num = parseFloat(number[i].innerText);
            const result = Math.floor(num/1000)*1000;
            const numberWithComma = result.toLocaleString();
            number[i].innerText = numberWithComma;
        }
    }
}

$('.number').ready(numberWithCommas());

// 한 줄 내에서 클릭 시 상세 페이지로 이동
$('.moveToDetail').click(function(e) {
    let dataIndex = e.currentTarget.dataset.index;

    let rentTerm = [...document.getElementsByClassName('rentTerm')].find(item => item.dataset.index == dataIndex).innerText;
    let rentId = [...document.getElementsByClassName('rentId')].find(item => item.dataset.index == dataIndex).innerText;
    let kilometer = [...document.getElementsByClassName('kilometer')].find(item => item.dataset.index == dataIndex).innerText;
    let discount = [...document.getElementsByClassName('discount')].find(item => item.dataset.index == dataIndex).innerText;

    window.location = '/rent/month/detail/' + rentId + '/' + rentTerm + '/' + kilometer  + '/' + discount;
});

//할인 가격 천원 단위로 반올림

const discountPrice = document.querySelectorAll('.discountStyle .discountPrice');

function roundNumber() {
    for (let i = 0; i < discountPrice.length; i++) {
        let textNum = parseInt(discountPrice[i].innerText);
        let roundedNumber = Math.round(textNum/1000)*1000;
        discountPrice[i].innerText = roundedNumber;
    }
}
$('.discountPrice').ready(roundNumber());


// 렌트 기간 선택하면 약정 주행거리 선택 보여주기
function displayNextOptions(e) {
    let monthKilometer = ["2000km", "2500km", "3000km", "4000km", "기타"];
    let yearKilometer = ["20000km", "30000km", "40000km", "기타"];
    let selectKilometer = document.getElementById('selectkilometer');
    let selectKilometer2 = document.getElementById('selectkilometer2');

    let displaySelect;

    if (e.value == "한달") {
        displaySelect = monthKilometer;
    } else if (e.value == "12개월" || e.value == "24개월") {
        displaySelect = yearKilometer;
    };

    selectKilometer.options.length = 0;
    selectKilometer2.options.length = 0;

    for (x in displaySelect) {
        let option = document.createElement('option');
        option.value = displaySelect[x];
        option.innerText = displaySelect[x];

        selectKilometer.appendChild(option);
        selectKilometer2.appendChild(option);
    };
}

// 상세페이지에서 렌트기간 및 약정 주행거리 변경 시 페이지 이동 - 데스크탑
function dataReset() {
    let rentTerm = document.getElementById('selectRentTerm').value;
    // let carIdx = document.getElementById('getCarIdx').innerText;
    let kilometer = document.getElementById('selectkilometer').value;
    let discount = document.getElementById('getDiscount').innerText;
    // let rentStatus = document.getElementById('getRentStatus').innerText;
    let rentId = document.getElementById('getRentId').innerText;

    // console.log(kilometer);

    if (rentTerm == '한달' && kilometer == '') {
        kilometer = '2000km';
    } else if (rentTerm == '12개월' && kilometer == '') {
        kilometer = '20000km';
    } else if (rentTerm == '24개월' && kilometer == '') {
        kilometer = '20000km';
    };

    window.location = '/rent/month/detail/' + rentId + '/' + rentTerm + '/' + kilometer  + '/' + discount;

    // window.location.href = '/rent/month/detail/'+ rentTerm + '/' + carIdx + '/' + rentIdx + '/' + kilometer + '/' + discount + '/' + rentStatus;
}
// 상세페이지에서 렌트기간 및 약정 주행거리 변경 시 페이지 이동 - 모바일
function dataReset2(isRentTermChanged) {
    let rentTerm = document.getElementById('selectRentTerm2').value;
    // let carIdx = document.getElementById('getCarIdx').innerText;
    let kilometer = document.getElementById('selectkilometer2').value;
    let discount = document.getElementById('getDiscount').innerText;
    // let rentStatus = document.getElementById('getRentStatus').innerText;
    let rentId = document.getElementById('getRentId').innerText;

    if (isRentTermChanged) {
        if (rentTerm == '한달') {
            kilometer = '2000km';
        } else if (rentTerm == '12개월' || rentTerm == '24개월') {
            kilometer = '20000km';
        }
    };

    window.location = '/rent/month/detail/' + rentId + '/' + rentTerm + '/' + kilometer  + '/' + discount;

    // window.location.href = '/rent/month/detail/'+ rentTerm + '/' + carIdx + '/' + rentIdx + '/' + kilometer + '/' + discount + '/' + rentStatus;
}

// 연령 바꾸면 가격 바꾸기
const originalText = document.getElementById('carPrice').innerText;
let displayPrice = document.querySelectorAll('#carPrice');
let ageLimit = document.getElementById('getAgeLimit').innerText;
let changedPrice;

function displayEditedPrice(e) {
    // console.log(originalText);
    if (originalText!='상담') {
        if (e.value == 'upper21') {
            let number = displayPrice[0].innerText.replace(/,/g, "");
            changedPrice = parseInt(number) + parseInt(ageLimit);
            displayPrice[0].innerText = changedPrice.toLocaleString();
            displayPrice[1].innerText = changedPrice.toLocaleString();
        } else if (e.value == 'upper26') {
            displayPrice[0].innerText = originalText;
            displayPrice[1].innerText = originalText;
        }
    }
}

// 데스크탑, 모바일 연령 맞추기
function sameAgeLimit(e) {
    // console.log(e.id);
    if (e.id === 'selectAge1') {
        document.getElementById('selectAge2').value = document.getElementById('selectAge1').value;
    } else if (e.id === 'selectAge2') {
        document.getElementById('selectAge1').value = document.getElementById('selectAge2').value;
    }
};

// 예약 신청하기 버튼 누르면 새창에 폼 띄우기
function openForm() {
    let carCategory = document.getElementById('forPostCarCategory').innerHTML;
    let carName = document.getElementById('forPostCarName').innerHTML;
    let carIdx = document.getElementById('forPostCarIdx').innerHTML;
    let carNo = document.getElementById('forPostCarNo').innerHTML;
    let carExteriorColor = document.getElementById('forPostCarExteriorColor').innerHTML;
    let carGubun = document.getElementById('forPostCarGubun').innerHTML;
    let carDisplacement = document.getElementById('forPostCarDisplacement').innerHTML;
    let carMileaget = document.getElementById('forPostCarMileaget').innerHTML;
    let carColor = document.getElementById('forPostCarColor').innerHTML;
    let carOld = document.getElementById('forPostCarOld').innerHTML;
    let carEngine = document.getElementById('forPostCarEngine').innerHTML;
    let carAttribute01 = document.getElementById('forPostCarAttribute01').innerHTML;
    let carPrice = document.getElementById('carPrice').innerHTML;
    let orderEnd = document.getElementById('forPostOrderEnd').innerHTML;
    let rentIdx = document.getElementById('forPostRentIdx').innerHTML;
    let carImageList = document.getElementById('forPostCarImageList').innerHTML;
    let discount = document.getElementById('forPostDiscount').innerHTML;
    let discountDescription = document.getElementById('forPostDiscountDescription').innerHTML;
    let costPerKm = document.getElementById('forPostCostPerKm').innerHTML;
    let credit = document.getElementById('forPostCredit').innerHTML;
    let carCode = document.getElementById('forPostCarCode').innerHTML;
    let kilometer = document.getElementById('forPostKilometer').innerText;
    let deposit = document.getElementById('forPostDeposit').innerText;
    let rentTerm = document.getElementById('forPostRentTerm').innerText;
    let ageLimit = document.getElementById('selectAge1').value;

    if (carPrice === '상담') {
        carPrice = -1;
    } else {
        carPrice = carPrice.replace(/,/g, "");
    }

    var mapForm = document.createElement("form");
    mapForm.target = "Map";
    mapForm.method = "POST"; // or "post" if appropriate
    mapForm.action = "/rent/month/detail/form/reservation";
    mapForm.style.display= "none";

    var mapAgeLimit = document.createElement("input");
    mapAgeLimit.type = "text";
    mapAgeLimit.name = "selectAge";
    mapAgeLimit.value = ageLimit;
    mapForm.appendChild(mapAgeLimit);

    var mapCarCategory = document.createElement("input");
    mapCarCategory.type = "text";
    mapCarCategory.name = "carCategory";
    mapCarCategory.value = carCategory;
    mapForm.appendChild(mapCarCategory);

    var mapCarName = document.createElement("input");
    mapCarName.type = "text";
    mapCarName.name = "carName";
    mapCarName.value = carName;
    mapForm.appendChild(mapCarName);

    var mapCarIdx = document.createElement("input");
    mapCarIdx.type = "text";
    mapCarIdx.name = "carIdx";
    mapCarIdx.value = carIdx;
    mapForm.appendChild(mapCarIdx);

    var mapCarNo = document.createElement("input");
    mapCarNo.type = "text";
    mapCarNo.name = "carNo";
    mapCarNo.value = carNo;
    mapForm.appendChild(mapCarNo);

    var mapCarExteriorColor = document.createElement("input");
    mapCarExteriorColor.type = "text";
    mapCarExteriorColor.name = "carExteriorColor";
    mapCarExteriorColor.value = carExteriorColor;
    mapForm.appendChild(mapCarExteriorColor);

    var mapCarGubun = document.createElement("input");
    mapCarGubun.type = "text";
    mapCarGubun.name = "carGubun";
    mapCarGubun.value = carGubun;
    mapForm.appendChild(mapCarGubun);

    var mapCarDisplacement = document.createElement("input");
    mapCarDisplacement.type = "text";
    mapCarDisplacement.name = "carDisplacement";
    mapCarDisplacement.value = carDisplacement;
    mapForm.appendChild(mapCarDisplacement);

    var mapCarMileaget = document.createElement("input");
    mapCarMileaget.type = "text";
    mapCarMileaget.name = "carMileaget";
    mapCarMileaget.value = carMileaget;
    mapForm.appendChild(mapCarMileaget);

    var mapCarColor = document.createElement("input");
    mapCarColor.type = "text";
    mapCarColor.name = "carColor";
    mapCarColor.value = carColor;
    mapForm.appendChild(mapCarColor);

    var mapCarOld = document.createElement("input");
    mapCarOld.type = "text";
    mapCarOld.name = "carOld";
    mapCarOld.value = carOld;
    mapForm.appendChild(mapCarOld);

    var mapCarEngine = document.createElement("input");
    mapCarEngine.type = "text";
    mapCarEngine.name = "carEngine";
    mapCarEngine.value = carEngine;
    mapForm.appendChild(mapCarEngine);

    var mapCarAttribute01 = document.createElement("input");
    mapCarAttribute01.type = "text";
    mapCarAttribute01.name = "carAttribute01";
    mapCarAttribute01.value = carAttribute01;
    mapForm.appendChild(mapCarAttribute01);

    var mapCarPrice = document.createElement("input");
    mapCarPrice.type = "text";
    mapCarPrice.name = "carPrice";
    mapCarPrice.value = carPrice;
    mapForm.appendChild(mapCarPrice);

    var mapOrderEnd = document.createElement("input");
    mapOrderEnd.type = "text";
    mapOrderEnd.name = "orderEnd";
    mapOrderEnd.value = orderEnd;
    mapForm.appendChild(mapOrderEnd);

    var mapRentIdx = document.createElement("input");
    mapRentIdx.type = "text";
    mapRentIdx.name = "rentIdx";
    mapRentIdx.value = rentIdx;
    mapForm.appendChild(mapRentIdx);

    var mapCarImageList = document.createElement("input");
    mapCarImageList.type = "text";
    mapCarImageList.name = "carImageList";
    mapCarImageList.value = carImageList;
    mapForm.appendChild(mapCarImageList);

    var mapDiscount = document.createElement("input");
    mapDiscount.type = "text";
    mapDiscount.name = "discount";
    mapDiscount.value = discount;
    mapForm.appendChild(mapDiscount);

    var mapDiscountDescription = document.createElement("input");
    mapDiscountDescription.type = "text";
    mapDiscountDescription.name = "discountDescription";
    mapDiscountDescription.value = discountDescription;
    mapForm.appendChild(mapDiscountDescription);

    var mapCostPerKm = document.createElement("input");
    mapCostPerKm.type = "text";
    mapCostPerKm.name = "costPerKm";
    mapCostPerKm.value = costPerKm;
    mapForm.appendChild(mapCostPerKm);

    var mapCredit = document.createElement("input");
    mapCredit.type = "text";
    mapCredit.name = "credit";
    mapCredit.value = credit;
    mapForm.appendChild(mapCredit);

    var mapCarCode = document.createElement("input");
    mapCarCode.type = "text";
    mapCarCode.name = "carCode";
    mapCarCode.value = carCode;
    mapForm.appendChild(mapCarCode);

    var mapKilometer = document.createElement("input");
    mapKilometer.type = "text";
    mapKilometer.name = "kilometer";
    mapKilometer.value = kilometer;
    mapForm.appendChild(mapKilometer);

    var mapDeposit = document.createElement("input");
    mapDeposit.type = "text";
    mapDeposit.name = "deposit";
    mapDeposit.value = deposit;
    mapForm.appendChild(mapDeposit);

    var mapRentTerm = document.createElement("input");
    mapRentTerm.type = "text";
    mapRentTerm.name = "rentTerm";
    mapRentTerm.value = rentTerm;
    mapForm.appendChild(mapRentTerm);

    document.body.appendChild(mapForm);

    map = window.open("", "Map", "status=0,title=0,height=1000,width=1000,scrollbars=1");

    if (map) {
        mapForm.submit();
    } else {
        alert('You must allow popups for this map to work.');
    }
}

// 견적서보기 버튼 누르면 새창에 폼 띄우기

function openOffer() {
    let carCategory = document.getElementById('forPostCarCategory').innerHTML;
    let carName = document.getElementById('forPostCarName').innerHTML;
    let carIdx = document.getElementById('forPostCarIdx').innerHTML;
    let carNo = document.getElementById('forPostCarNo').innerHTML;
    let carExteriorColor = document.getElementById('forPostCarExteriorColor').innerHTML;
    let carGubun = document.getElementById('forPostCarGubun').innerHTML;
    let carDisplacement = document.getElementById('forPostCarDisplacement').innerHTML;
    let carMileaget = document.getElementById('forPostCarMileaget').innerHTML;
    let carColor = document.getElementById('forPostCarColor').innerHTML;
    let carOld = document.getElementById('forPostCarOld').innerHTML;
    let carEngine = document.getElementById('forPostCarEngine').innerHTML;
    let carAttribute01 = document.getElementById('forPostCarAttribute01').innerHTML;
    let carPrice = document.getElementById('carPrice').innerHTML;
    let orderEnd = document.getElementById('forPostOrderEnd').innerHTML;
    let rentIdx = document.getElementById('forPostRentIdx').innerHTML;
    let carImageList = document.getElementById('forPostCarImageList').innerHTML;
    let discount = document.getElementById('forPostDiscount').innerHTML;
    let discountDescription = document.getElementById('forPostDiscountDescription').innerHTML;
    let costPerKm = document.getElementById('forPostCostPerKm').innerHTML;
    let credit = document.getElementById('forPostCredit').innerHTML;
    let carCode = document.getElementById('forPostCarCode').innerHTML;
    let kilometer = document.getElementById('forPostKilometer').innerText;
    let deposit = document.getElementById('forPostDeposit').innerText;
    let rentTerm = document.getElementById('forPostRentTerm').innerText;
    let selectAge = document.getElementById('selectAge1').value;

    if (carPrice === '상담') {
        carPrice = -1;
    } else {
        carPrice = carPrice.replace(/,/g, "");
    }

    var mapForm = document.createElement("form");
    mapForm.target = "Map";
    mapForm.method = "POST"; // or "post" if appropriate
    mapForm.action = "/rent/month/detail/form/estimate";
    mapForm.style.display= "none";

    var mapCarCategory = document.createElement("input");
    mapCarCategory.type = "text";
    mapCarCategory.name = "carCategory";
    mapCarCategory.value = carCategory;
    mapForm.appendChild(mapCarCategory);

    var mapCarName = document.createElement("input");
    mapCarName.type = "text";
    mapCarName.name = "carName";
    mapCarName.value = carName;
    mapForm.appendChild(mapCarName);

    var mapCarIdx = document.createElement("input");
    mapCarIdx.type = "text";
    mapCarIdx.name = "carIdx";
    mapCarIdx.value = carIdx;
    mapForm.appendChild(mapCarIdx);

    var mapCarNo = document.createElement("input");
    mapCarNo.type = "text";
    mapCarNo.name = "carNo";
    mapCarNo.value = carNo;
    mapForm.appendChild(mapCarNo);

    var mapCarExteriorColor = document.createElement("input");
    mapCarExteriorColor.type = "text";
    mapCarExteriorColor.name = "carExteriorColor";
    mapCarExteriorColor.value = carExteriorColor;
    mapForm.appendChild(mapCarExteriorColor);

    var mapCarGubun = document.createElement("input");
    mapCarGubun.type = "text";
    mapCarGubun.name = "carGubun";
    mapCarGubun.value = carGubun;
    mapForm.appendChild(mapCarGubun);

    var mapCarDisplacement = document.createElement("input");
    mapCarDisplacement.type = "text";
    mapCarDisplacement.name = "carDisplacement";
    mapCarDisplacement.value = carDisplacement;
    mapForm.appendChild(mapCarDisplacement);

    var mapCarMileaget = document.createElement("input");
    mapCarMileaget.type = "text";
    mapCarMileaget.name = "carMileaget";
    mapCarMileaget.value = carMileaget;
    mapForm.appendChild(mapCarMileaget);

    var mapCarColor = document.createElement("input");
    mapCarColor.type = "text";
    mapCarColor.name = "carColor";
    mapCarColor.value = carColor;
    mapForm.appendChild(mapCarColor);

    var mapCarOld = document.createElement("input");
    mapCarOld.type = "text";
    mapCarOld.name = "carOld";
    mapCarOld.value = carOld;
    mapForm.appendChild(mapCarOld);

    var mapCarEngine = document.createElement("input");
    mapCarEngine.type = "text";
    mapCarEngine.name = "carEngine";
    mapCarEngine.value = carEngine;
    mapForm.appendChild(mapCarEngine);

    var mapCarAttribute01 = document.createElement("input");
    mapCarAttribute01.type = "text";
    mapCarAttribute01.name = "carAttribute01";
    mapCarAttribute01.value = carAttribute01;
    mapForm.appendChild(mapCarAttribute01);

    var mapCarPrice = document.createElement("input");
    mapCarPrice.type = "text";
    mapCarPrice.name = "carPrice";
    mapCarPrice.value = carPrice;
    mapForm.appendChild(mapCarPrice);

    var mapOrderEnd = document.createElement("input");
    mapOrderEnd.type = "text";
    mapOrderEnd.name = "orderEnd";
    mapOrderEnd.value = orderEnd;
    mapForm.appendChild(mapOrderEnd);

    var mapRentIdx = document.createElement("input");
    mapRentIdx.type = "text";
    mapRentIdx.name = "rentIdx";
    mapRentIdx.value = rentIdx;
    mapForm.appendChild(mapRentIdx);

    var mapCarImageList = document.createElement("input");
    mapCarImageList.type = "text";
    mapCarImageList.name = "carImageList";
    mapCarImageList.value = carImageList;
    mapForm.appendChild(mapCarImageList);

    var mapDiscount = document.createElement("input");
    mapDiscount.type = "text";
    mapDiscount.name = "discount";
    mapDiscount.value = discount;
    mapForm.appendChild(mapDiscount);

    var mapDiscountDescription = document.createElement("input");
    mapDiscountDescription.type = "text";
    mapDiscountDescription.name = "discountDescription";
    mapDiscountDescription.value = discountDescription;
    mapForm.appendChild(mapDiscountDescription);

    var mapCostPerKm = document.createElement("input");
    mapCostPerKm.type = "text";
    mapCostPerKm.name = "costPerKm";
    mapCostPerKm.value = costPerKm;
    mapForm.appendChild(mapCostPerKm);

    var mapCredit = document.createElement("input");
    mapCredit.type = "text";
    mapCredit.name = "credit";
    mapCredit.value = credit;
    mapForm.appendChild(mapCredit);

    var mapCarCode = document.createElement("input");
    mapCarCode.type = "text";
    mapCarCode.name = "carCode";
    mapCarCode.value = carCode;
    mapForm.appendChild(mapCarCode);

    var mapKilometer = document.createElement("input");
    mapKilometer.type = "text";
    mapKilometer.name = "kilometer";
    mapKilometer.value = kilometer;
    mapForm.appendChild(mapKilometer);

    var mapDeposit = document.createElement("input");
    mapDeposit.type = "text";
    mapDeposit.name = "deposit";
    mapDeposit.value = deposit;
    mapForm.appendChild(mapDeposit);

    var mapRentTerm = document.createElement("input");
    mapRentTerm.type = "text";
    mapRentTerm.name = "rentTerm";
    mapRentTerm.value = rentTerm;
    mapForm.appendChild(mapRentTerm);

    var mapSelectAge = document.createElement("input");
    mapSelectAge.type = "text";
    mapSelectAge.name = "selectAge";
    mapSelectAge.value = selectAge;
    mapForm.appendChild(mapSelectAge);


    document.body.appendChild(mapForm);

    map = window.open("", "Map", "status=0,title=0,height=1000,width=1000,scrollbars=1");

    if (map) {
        mapForm.submit();
    } else {
        alert('You must allow popups for this map to work.');
    }
}