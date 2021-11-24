let sortType = 'asc';

function sortAvailableContent(index) {
    let table = document.getElementsByClassName('table_available')

    sortType = (sortType == 'asc') ? 'desc' : 'asc';

    let checkSort = true;
    let rows = table[0].rows;

    while (checkSort) { // 현재와 다음만 비교하기때문에 위치변경되면 다시 정렬해준다.
        checkSort = false;

        for (let i = 1; i < (rows.length - 1); i++) {
            let fCell = rows[i].cells[index].innerText.toUpperCase();
            let sCell = rows[i + 1].cells[index].innerText.toUpperCase();

            let row = rows[i];

            // 오름차순<->내림차순
            if ((sortType == 'asc' && fCell > sCell) || (sortType == 'desc' && fCell < sCell)) {
                row.parentNode.insertBefore(row.nextSibling, row);
                checkSort = true;
            }
        }
    }
}

function sortExpectedContent(index) {
    let table = document.getElementsByClassName('table_expected')

    sortType = (sortType == 'asc') ? 'desc' : 'asc';

    let checkSort = true;
    let rows = table[0].rows;

    while (checkSort) { // 현재와 다음만 비교하기때문에 위치변경되면 다시 정렬해준다.
        checkSort = false;

        for (let i = 1; i < (rows.length - 1); i++) {
            let fCell = rows[i].cells[index].innerText.toUpperCase();
            let sCell = rows[i + 1].cells[index].innerText.toUpperCase();

            let row = rows[i];

            // 오름차순<->내림차순
            if ((sortType == 'asc' && fCell > sCell) || (sortType == 'desc' && fCell < sCell)) {
                row.parentNode.insertBefore(row.nextSibling, row);
                checkSort = true;
            }
        }
    }
}


function sendData(){
    document.getElementById('select_wrapper').submit();
}


// 월렌트실시간 상담요청
function make_monthly_rent_reservation () {

    if (document.getElementById("reservation-simple-name").value == ""){
        alert('성함을 입력해주세요.')
        return
    }

    if (document.getElementById("reservation-simple-phone").value == ""){
        alert('전화번호를 입력해주세요.')
        return
    }


    var data = {
        name : $("#reservation-simple-name").val(),
        phoneNo : $("#reservation-simple-phone").val(),
        detail : $("#reservation-simple-details").val(),
        title : "월렌트실시간",
        car_name : document.getElementsByClassName("carName")[0].innerHTML,
        mileage : document.getElementsByClassName("carNo")[0].innerHTML,
        option : document.getElementsByClassName("carOld")[0].innerHTML
    };

    var checkbox = document.getElementById("agree")
    if(checkbox.checked) {
        $.ajax({
            type : 'POST',
            url : '/reservation/apply',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('예약 신청이 완료되었습니다.');
            window.location.href = '/index';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    } else{
        alert("개인정보 수집 및 이용에 동의해주세요.");
    }
}

//숫자 사이에 콤마 넣기 시작
const number = document.querySelectorAll(".number");

function numberWithCommas() {
    // console.log(number);
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

// 한 줄 내에서 클릭 시 상세 페이지로 이동
// $('#mainTable_tbody').


// '차량금액의 30% 보증금 지불'일 경우 보증금 표기 하지 않기
const credit = document.getElementById('reservation-detail-credit');
let originalDepositText = document.getElementById('carDeposit');
const realDeposit = originalDepositText.innerText;
const wonText = document.getElementById('displayNone');
let newDepositText = '차량가격의 30% 이상(상담문의)';

function displayDeposit() {
    if (credit.value == '차량 금액의 30% 이상 보증금') {
        originalDepositText.innerText = newDepositText;
        wonText.style.display = 'none';
    } else {
        originalDepositText.innerText = realDeposit.toLocaleString();
        wonText.style.display = 'inline-block';
    }
}


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

// 상세페이지에서 렌트기간 및 약정 주행거리 변경 시 페이지 이동
function dataReset() {
    let rentTerm = document.getElementById('selectRentTerm').value;
    let carIdx = document.getElementById('getCarIdx').innerText;
    let kilometer = document.getElementById('selectkilometer').value;
    let discount = document.getElementById('getDiscount').innerText;
    let rentStatus = document.getElementById('getRentStatus').innerText;
    let rentIdx = document.getElementById('getrentIdx').innerText;

    window.location.href = '/rent/month/detail/'+ rentTerm + '/' + carIdx + '/' + rentIdx + '/' + kilometer + '/' + discount + '/' + rentStatus;
}

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
    let carPrice = document.getElementById('forPostCarPrice').innerHTML;
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

    var mapForm = document.createElement("form");
    mapForm.target = "Map";
    mapForm.method = "POST"; // or "post" if appropriate
    mapForm.action = "/rent/month/detail/form/reservation";
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

    document.body.appendChild(mapForm);

    map = window.open("", "Map", "status=0,title=0,height=1000,width=1000,scrollbars=1");

    if (map) {
        mapForm.submit();
    } else {
        alert('You must allow popups for this map to work.');
    }
}

// 예약 신청하기 버튼 누르면 새창에 폼 띄우기

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
    let carPrice = document.getElementById('forPostCarPrice').innerHTML;
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

    document.body.appendChild(mapForm);

    map = window.open("", "Map", "status=0,title=0,height=1000,width=1000,scrollbars=1");

    if (map) {
        mapForm.submit();
    } else {
        alert('You must allow popups for this map to work.');
    }
}