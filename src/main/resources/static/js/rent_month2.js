let sortType = 'asc';

function sortContent(index) {
    // let table = document.getElementsByTagName('table');
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



// Sending Data;
const reserveMonthlyRent = () => {


    // 예약 정보 받기
    const reservationDetailName = document.getElementById('reservation-detail-name').value;
    const reservationDetailPhone = document.getElementById('reservation-detail-phone').value;
    const reservationDetailDate = document.getElementById('reservation-detail-date').value;
    const reservationDetailTime = document.getElementById('reservation-detail-time').value;
    const reservationDetailDetails = document.getElementById('reservation-detail-details').value;

    const addressKakao = document.getElementById('address_kakao').value;
    const addressKakaoDetail = document.getElementById('address_kakao_detail').value;
    // ftp://itscar@itscar.cafe24.com/tomcat/webapps/manager/images/add.gif

    console.log(reservationDetailName)
    console.log(reservationDetailPhone)
    console.log(reservationDetailDate)
    console.log(reservationDetailTime)
    console.log(reservationDetailDetails)
    console.log(addressKakao)
    console.log(addressKakaoDetail)

    let check1 = document.getElementById('check_info').checked;



    if (reservationDetailName != '' && reservationDetailPhone!='' && reservationDetailDate!='' && reservationDetailTime != '' &&  addressKakao != '' && addressKakaoDetail != '') {
        let finalDate = {
            'carType': carType,
            'rentDate': rentDateNum,
            'rentTime':  rentTime,
            'returnDate': returnDateNum,
            'returnTime' : returnTime,
            'extraTime' : extraTime,
            'agree': 1,
            'deposit':totalHalf,
            'depositor': depositName,
            'detail': customDemand,
            'name': customName,
            'phone': phoneNum,
            'total': totalPrice,
            'totalHalf': totalHalf,
            'reservation': 0,
            'day': useDay,
        }

        console.log(finalDate);

        let reserveConfirm = confirm('예약을 완료하시겠습니까?');
        if (reserveConfirm) {
            let url = '/reserve/campingcar';
            fetch(url, {
                method: 'POST',
                headers:{
                    'Content-Type' : 'application/json'
                },
                body: JSON.stringify(finalDate),
            }).then(response => response.json())
                .then(result => {
                    if (result[0] == "1") {
                        alert('차량 예약 대기 신청이 완료되었습니다.')
                        window.location.href = '/rent/month/new';
                    } else if (result[0] == "0") alert('차량 예약 대기 신청을 실패하였습니다.')
                })
            // .catch(err => console.error('Error: ', err))
        }
    } else if (reservationDetailName == '' || reservationDetailPhone=='' || reservationDetailDate=='' || reservationDetailTime=='' || addressKakao == '' || addressKakaoDetail == '' ) {
        alert('입력을 완료해주세요!')
    } else if (check1 != true || check2 != true || check3 != true || check4 != true) {
        alert('동의를 완료해주세요!')
    }
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
                console.log("imgbb.image.url : " +imgbb.image.url);
            }

        }
    });
}

//숫자 사이에 콤마 넣기 시작
const number = document.querySelectorAll(".number");

function numberWithCommas() {
    // console.log(number);
    for (let i = 0; i < number.length; i++) {
        const numberWithComma = parseInt(number[i].innerText).toLocaleString();
        number[i].innerText = numberWithComma;
    }
}

number.addEventListener('load', numberWithCommas());

//숫자 사이에 콤마 넣기 끝

// 두 지점 사이 거리 시작
