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


// function getBase64(file) {
//     var reader = new FileReader();
//     reader.readAsDataURL(file);
//     reader.onload = function () {
//         console.log(reader.result);
//     };
//     reader.onerror = function (error) {
//         console.log('Error: ', error);
//     };
// }


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

    console.log(atob(file));

    getBase64(file)
        .then(data => {
            // var data_post = {
            //     key: "03b260a78d187a2dd3086b7fe1e70e80",
            //     image: data
            // };
            // console.log(JSON.stringify(data_post))

            // $.ajax({
            //     type: 'POST',
            //     url: 'https://api.imgbb.com/1/upload',
            //     dataType: 'json',
            //     processData: false,
            //     contentType: 'application/json; charset=utf-8',
            //     data: JSON.stringify(data_post)
            // }).done(function () {
            //     alert('POST 성공.');
            // }).fail(function (error) {
            //     alert(JSON.stringify(error));
            // })

            // var url = "https://api.imgbb.com/1/upload";
            var url = "https://api.imgbb.com/1/upload?expiration=600&key=03b260a78d187a2dd3086b7fe1e70e80&image=" + data;
            console.log(url);


            var xhr = new XMLHttpRequest();
            xhr.open("POST", url);

            // xhr.setRequestHeader("Content-Length", "0");

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    console.log(xhr.status);
                    console.log(xhr.responseText);
                }};

            xhr.send();

        }
    );
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
