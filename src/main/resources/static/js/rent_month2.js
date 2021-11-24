let sortType = 'asc';
let image_url;

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




function ajaxFileUpload() {
    var form = jQuery("ajaxFrom")[0];
    var formData = new FormData(form);
    formData.append("message", "ajax로 파일 전송하기");

    var fileValue = $("#ajaxFile").val().split("\\");
    var fileName = fileValue[fileValue.length-1].replace(/(.png|.jpg|.jpeg|.gif)$/, ''); // 파일명
    console.log("fileName : "+fileName);

    formData.append("key", "03b260a78d187a2dd3086b7fe1e70e80");
    formData.append("image", jQuery("#ajaxFile")[0].files[0]);
    formData.append("name", fileName);

    console.log("파일 데이터");
    console.dir(jQuery("#ajaxFile")[0].files[0]);
    console.log("-------------");
    console.log(jQuery("#ajaxFile")[0].files[0]);

    jQuery.ajax({
        url : "https://api.imgbb.com/1/upload"
        , type : "POST"
        , processData : false
        ,contentType : false
        , data : formData
        , success:function(imgbbReturn) {

            if(imgbbReturn.success==true){

                var imgbb = imgbbReturn.data;
                console.log("imgbb.image.url : " +imgbb.image.url);
                image_url = imgbb.image.url;
            }

        }
    });
}


// Sending Data;
const reserveMonthlyRent = () => {

    // 예약 정보 받기
    const carNo = document.getElementById('carNo').innerText;
    const kilometer = document.getElementById('kilometer').innerText;
    const reservationName = document.getElementById('reservation-detail-name').value;
    const reservationPhone = document.getElementById('reservation-detail-phone').value;
    const reservationAge = document.getElementById('reservation-detail-age').value;
    const reservationDate = document.getElementById('reservation-detail-date').value;
    const reservationTime = document.getElementById('reservation-detail-time').value;
    const reservationGuarantee = document.getElementById('reservation-detail-guarantee').value;
    // const reservationLicense = image_url;
    const reservationDetails = document.getElementById('reservation-detail-details').value;
    const address = document.getElementById('address_kakao').value;
    const addressDetail = document.getElementById('address_kakao_detail').value;
    const carPrice = document.getElementById('carPrice').innerText;
    const carTax = document.getElementById('carTax').innerText;
    const carAmountTotal = document.getElementById('carAmountTotal').innerText;
    const carDeposit = document.getElementById('carDeposit').innerText;
    // ftp://itscar@itscar.cafe24.com/tomcat/webapps/manager/images/add.gif

    let check1 = document.getElementById('check_info').checked;
    console.log(check1);

    if (reservationName != '' && reservationPhone != '' && reservationAge != '' && reservationDate != '' && reservationTime != '' && reservationGuarantee != '' && address != '' && addressDetail != '' && check1) {
        var data = {
            carNo: carNo,
            kilometer: kilometer,
            reservationName: reservationName,
            reservationPhone: reservationPhone,
            reservationAge: reservationAge,
            reservationDate: reservationDate,
            reservationTime: reservationTime,
            reservationGuarantee: reservationGuarantee,
            reservationDetails: reservationDetails,
            address: address,
            addressDetail: addressDetail,
            carPrice: carPrice,
            carTax: carTax,
            carAmountTotal: carAmountTotal,
            carDeposit: carDeposit
        }

        console.log(data);

        let reserveConfirm = confirm('예약을 완료하시겠습니까?');

        if (reserveConfirm) {
            $.ajax({
                type:'POST',
                url:'/rent/month/moren/reservation',
                dataType:'json',
                contentType : 'application/json; charset=utf-8',
                data : JSON.stringify(data)
            }).done(function (result) {
                if (result.result == 1) {
                    alert('예약이 완료되었습니다.');
                } else {
                    alert('예약에 문제가 생겼습니다.');
                };
                // window.location.href = '/admin/discount/menu';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        };
    } else if (reservationName == '' || reservationPhone == '' || reservationAge == '' || reservationDate == '' || reservationTime == '' || reservationGuarantee == '' || address == '' || addressDetail == '') {
            alert('입력을 완료해주세요!')
    } else if (check1 != true) {
            alert('동의를 완료해주세요!')
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