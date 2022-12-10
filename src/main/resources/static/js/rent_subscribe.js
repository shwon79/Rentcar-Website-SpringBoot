
// 상담신청하기_rent/subscribe main, detail
function make_subscribe_rent_reservation(e) {
    let reservationPhone = $("#reservation-simple-phone").val();

    if (document.getElementById("reservation-simple-name").value == ""){
        alert('성함을 입력해주세요.')
        return
    };

    if (document.getElementById("reservation-simple-phone").value == ""){
        alert('전화번호를 입력해주세요.')
        return
    };

    let regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
    const carName = document.getElementById('car_name');
    const carNum = document.getElementById('car_num');
    const carPrice = document.getElementById('car_price');
    const carDeposit = document.getElementById('car_deposit');
    const carColor = document.getElementById('car_color');
    const carYearModel = document.getElementById('car_year_model');
    const contractPeriod = document.getElementById('contract_period');
    const contractKm = document.getElementById('contract_km');
    const contractMaintenance = document.getElementById('contract_maintenance');
    const carFuel = document.getElementById('car_fuel');
    let newOld = document.getElementById('new_old');
    let product, title;

    if (newOld) {
        product = newOld && '구독' + newOld.innerText + '장기렌트';
    } else {
        product = '자동차 구독';
    };

    title = '구독상담신청';

    let data = {
        name : $("#reservation-simple-name").val(),
        phoneNo : reservationPhone,
        detail : $("#reservation-simple-details").val(),
        product: product,
        title: title,
        category1: carColor && carColor.innerText || '',
        category2: carFuel && carFuel.innerText || '',
        car_name : carName && carName.innerText || '',
        mileage: contractKm && contractKm.innerText || '',
        deposit: carDeposit && carDeposit.innerText || '',
        option: contractMaintenance && contractMaintenance.innerText || '',
        price: carPrice && carPrice.innerText || '',
        age_limit: '',
        car_num : carNum && carNum.innerText || '',
        region: '',
        resDate: contractPeriod && contractPeriod.innerText || '',
        carAge: carYearModel && carYearModel.innerText || '',
    };

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
            window.location.href = '/rent/subscribe';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };
};