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
        sessionStorage.setItem('campingSettingTab', carName);
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
    carCode = document.getElementsByClassName('carCode')[index].innerText;
    carNum = document.getElementsByClassName('carNum')[index].value;
    yearmodel = document.getElementsByClassName('yearmodel')[index].value;
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

function checkCampingSettingTab() {
    let tabStatus = sessionStorage.getItem('campingSettingTab');
    let navLinkList = document.getElementsByClassName('nav-link');
    let tabPaneList = document.getElementsByClassName('tab-pane');

    [...navLinkList].forEach((navLink) => {
        if (tabStatus && tabStatus === navLink.dataset.title) {
            navLink.classList.add('active');
            navLink.classList.add('show');
        } else {
            navLink.classList.remove('active');
            navLink.classList.remove('show');
        }
    });
    [...tabPaneList].forEach((tabpane) => {
        if (tabStatus && tabStatus === tabpane.id) {
            tabpane.classList.add('show');
            tabpane.classList.add('active');
        } else {
            tabpane.classList.remove('show');
            tabpane.classList.remove('active');
        }
    })

    if (!tabStatus) {
        navLinkList[0].classList.add('active');
        navLinkList[0].classList.add('show');
        tabPaneList[0].classList.add('active');
        tabPaneList[0].classList.add('show');
    }
}
window.onload = checkCampingSettingTab();