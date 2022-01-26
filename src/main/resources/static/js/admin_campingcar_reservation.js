// 대여 날짜 및 시간, 반납 날짜 및 시간 선택시 자동으로 일권, 추가시간 보여주기
function displayExtraCampingData() {
    let rentDate = document.getElementById('rentDate').value;
    let rentTime = parseInt(document.getElementById('rentTime').value);
    let returnDate = document.getElementById('returnDate').value;
    let returnTime = parseInt(document.getElementById('returnTime').value);
    let day = document.getElementById('day');
    let extraTime = document.getElementById('extraTime');

    if (rentDate != '' && rentTime != '' && returnDate != '' && returnTime != '') {
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
}

// 등록 버튼
function reserveCampingcar() {
    let carType = document.getElementById('carType').value;
    let day = document.getElementById('day').innerText;
    let deposit = document.getElementById('deposit').value;
    let depositor = document.getElementById('depositor').value;
    let detail = document.getElementById('detail').value;
    let name = document.getElementById('name').value;
    let phone = document.getElementById('phone').value;
    let rentDate = document.getElementById('rentDate').value;
    let rentTime = document.getElementById('rentTime').value;
    let returnDate = document.getElementById('returnDate').value;
    let returnTime = document.getElementById('returnTime').value;
    let total = document.getElementById('total').value;
    let totalHalf = document.getElementById('totalHalf').value;
    let extraTime = document.getElementById('extraTime').innerText;
    let regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
    let phoneWithoutDash = /^\d{11}$/;

    deposit = parseInt(deposit.replace(/,/g, ""));
    total = parseInt(total.replace(/,/g, ""));
    totalHalf = parseInt(totalHalf.replace(/,/g, ""));

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

    if (phoneWithoutDash.test(phone) == true) {
        phone = phone.substr(0, 3) + "-" + phone.substr(3, 4) + "-" + phone.substr(7,4);
    };

    if (carType == '' || deposit == '' || depositor == '' || name == '' || phone == '' || rentDate == '' || rentTime == '' || returnDate == ''|| returnTime == ''|| total == ''|| totalHalf == '') {
        alert('입력을 완료해주세요!');
    }  else if (regPhone.test(phone) == false) {
        alert("올바른 연락처를 입력해주세요.");
    }  else if (carType != '' && deposit != '' && depositor != '' && name != '' && phone != '' && rentDate != '' && rentTime != '' && returnDate != '' && returnTime != '' && total != '' && totalHalf != '') {
        let data = {
            agree: 1,
            carType: carType,
            day: day,
            deposit: deposit,
            depositor: depositor,
            detail: detail,
            name: name,
            phone: phone,
            rentDate: rentDate,
            rentTime: rentTime,
            reservation: 0,
            returnDate: returnDate,
            returnTime: returnTime,
            total: total,
            totalHalf: totalHalf,
            extraTime: extraTime,
            orderCode: ''
        };

        console.log(data);

        if (confirm('캠핑카 예약을 등록하시겠습니까?')) {
            $.ajax({
                type: 'POST',
                url: '/admin/campingcar/reservation',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function (result) {
                if (result.result == 1) {
                    alert('등록이 완료되었습니다.');
                } else {
                    alert('등록에 문제가 생겼습니다.');
                };
                window.location.href = '/admin/campingcar/menu';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        };
    }
}