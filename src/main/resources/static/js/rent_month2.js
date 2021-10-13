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
            let url = '/campingcar/reserve';
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
                        window.location.href = '/rent/month/test';
                    } else if (result[0] == "0") alert('이용할 수 없는 날짜입니다.')
                })
            // .catch(err => console.error('Error: ', err))
        }
    } else if (reservationDetailName == '' || reservationDetailPhone=='' || reservationDetailDate=='' || reservationDetailTime=='' || addressKakao == '' || addressKakaoDetail == '' ) {
        alert('입력을 완료해주세요!')
    } else if (check1 != true || check2 != true || check3 != true || check4 != true) {
        alert('동의를 완료해주세요!')
    }
}

function getBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
}




// 이미지 서버에 올리기
function upload_image() {

    var file = document.querySelector('#files > input[type="file"]').files[0];
    getBase64(file).then(
        data => console.log(data)
    );

    // if (document.getElementById("reservation-detail-name").value == ""){
    //     alert('성함을 입력해주세요.')
    //     return
    // }
    //
    // if (document.getElementById("reservation-detail-phone").value == ""){
    //     alert('전화번호를 입력해주세요.')
    //     return
    // }
    //
    //
    // var data = {
    //     name : $("#reservation-detail-name").val(),
    //     phoneNo : $("#reservation-detail-phone").val(),
    //     detail : $("#reservation-detail-details").val(),
    //     title : "간편상담신청",
    //     car_name : $("#reservation-detail-carname").val(),
    //     mileage : $("#reservation-detail-region").val(),
    //     option : $("#reservation-detail-resdate").val()
    // };
    //
    // var checkbox = document.getElementById("agree")
    // if(checkbox.checked) {
    //     $.ajax({
    //         type : 'POST',
    //         url : '/reservation/apply',
    //         dataType : 'json',
    //         contentType : 'application/json; charset=utf-8',
    //         data : JSON.stringify(data)
    //     }).done(function () {
    //         alert('예약이 완료되었습니다.');
    //     }).fail(function (error) {
    //         alert(JSON.stringify(error));
    //     })
    // } else{
    //     alert("개인정보 수집 및 이용에 동의해주세요.");
    // }
}

//숫자 사이에 콤마 넣기
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// const numbers = document.querySelectorAll('.normNumber');
// const numbersLength = numbers.length;
//
// for(let i=0; i < numbersLength; i++) {
//     numberWithCommas(numbers[i].innerText);
// }
