// setting에서 캠핑카 내용 수정 버튼
let fuel, gearBox, license, personnel, camperPrice, rentPolicy, rentInsurance, driverLicense;
let rentRule, refundPolicy, basicOption, facility, carNum, carCode, yearmodel;
function editCampingcarSetting(carName, index) {
    getData(index);

    let data = {
        basic_option: basicOption,
        facility: facility,
        carNum: carNum,
        carCode: carCode,
        yearmodel: yearmodel,
        fuel: fuel,
        gearBox: gearBox,
        license: license,
        personnel: personnel,
        camper_price: camperPrice,
        rent_policy: rentPolicy,
        rent_insurance: rentInsurance,
        rent_rule: rentRule,
        refund_policy: refundPolicy,
        driver_license: driverLicense
    }

    // console.log(data);
    // console.log(carName);
    if (confirm('캠핑카 내용을 수정하시겠습니까?')) {
        console.log(data);
        sendSettingData();
    };

    function sendSettingData() {
        $.ajax({
            type:'PUT',
            url:'/admin/campingcar/setting/' + carName,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('처리되었습니다.');
            } else if (result.result == 0) {
                alert('처리에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/campingcar/setting/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };
}

function getData(index) {
    carNum = document.getElementsByClassName('carNum')[index].innerText;
    carCode = document.getElementsByClassName('carCode')[index].innerText;
    yearmodel = document.getElementsByClassName('yearmodel')[index].innerText;
    fuel = document.getElementsByClassName('fuel')[index].value;
    gearBox = document.getElementsByClassName('gearBox')[index].value;
    license = document.getElementsByClassName('license')[index].value;
    personnel = document.getElementsByClassName('personnel')[index].value;
    camperPrice = document.getElementsByClassName('camperPrice')[index].value;
    rentPolicy = document.getElementsByClassName('rentPolicy')[index].value;
    rentInsurance = document.getElementsByClassName('rentInsurance')[index].value;
    driverLicense = document.getElementsByClassName('driverLicense')[index].value;
    rentRule = document.getElementsByClassName('rentRule')[index].value;
    refundPolicy = document.getElementsByClassName('refundPolicy')[index].value;
    basicOption = document.getElementsByClassName('basicOption')[index].value;
    facility = document.getElementsByClassName('facility')[index].value;

}