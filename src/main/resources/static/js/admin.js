// sidebar
$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
        $(this).toggleClass('active');
    });
    const campingSubmenuBtn = document.getElementById('camping-submenu-btn');
    campingSubmenuBtn && campingSubmenuBtn.addEventListener('click', () => {
        document.getElementById('campingcarSubmenu').classList.toggle('camping-open');
        if (document.getElementById('campingcarSubmenu').classList.contains('camping-open')) {
            document.getElementById('price-submenu-btn').style.top = '433px';
        } else {
            document.getElementById('price-submenu-btn').style.top = '190px';
        }
        campingSubmenuBtn.classList.toggle('active-span');
    });
    const priceSubmenuBtn = document.getElementById('price-submenu-btn');
    priceSubmenuBtn && priceSubmenuBtn.addEventListener('click', () => {
        document.getElementById('priceSubMenu').classList.toggle('price-open');
        priceSubmenuBtn.classList.toggle('active-span');
    });
});

// 세부메뉴 클릭 시 sessionStorage에 저장
function setActiveMenu(type) {
    sessionStorage.setItem('activeMenu', type);
}

// 세부매뉴 기억하고 보여주기
function displayActiveMenu() {
    let menu = sessionStorage.getItem('activeMenu');
    const campingSubmenuBtn = document.getElementById('camping-submenu-btn');
    const campingcarSubmenu = document.getElementById('campingcarSubmenu');
    const priceSubmenuBtn = document.getElementById('price-submenu-btn');
    const priceSubMenu = document.getElementById('priceSubMenu');

    if (menu === 'campingCar') {
        campingSubmenuBtn.classList.add('active-span');
        campingcarSubmenu.classList.add('camping-open');
        priceSubmenuBtn.style.top = '433px';
    } else if (menu === 'rentCar') {
        priceSubmenuBtn.classList.add('active-span');
        priceSubMenu.classList.add('price-open');
    };
}
window.onload = displayActiveMenu();

//숫자 사이에 콤마 넣기
let number = document.getElementsByClassName("number");

function numberWithCommas() {
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

//모렌 예약 신청 아예 삭제
$('.moren-completely-delete-btn').click(function() {
    let completeDeleteConfirm = confirm('삭제를 하시면 현재 admin 페이지에 반영이 되며, 프라임클럽 사이트에는 반영되지 않습니다. 예약 신청 목록에서 삭제 하시겠습니까?');
    let selectedOptions = document.querySelectorAll('input[name="selected_moren_reservation"]:checked');
    let id;

    if (completeDeleteConfirm) {
        for (i=0; i < selectedOptions.length; i++) {
            id = selectedOptions[i].value;
            // console.log(id);

            $.ajax({
                type:'DELETE',
                url:'/moren/reservation/'+ id,
                dataType:'json',
                contentType : 'application/json; charset=utf-8',
            }).done(function (result) {
                if (result.result == 1) {
                    alert('삭제 되었습니다.');
                } else if (result.result == 0) {
                    alert('삭제에 문제가 생겼습니다.');
                };
                window.location.href = '/admin/moren/reservation/menu';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        }
    }
});

//캠핑카 예약 신청 아예 삭제
$('.camping-completely-delete-btn').click(function() {
    let completeDeleteConfirm = confirm('삭제를 하시면 현재 admin 페이지에 반영이 되며, 프라임클럽 사이트에는 반영되지 않습니다. 예약 신청 목록에서 삭제 하시겠습니까?');
    let selectedOptions = document.querySelectorAll('input[name="selected_camping_reservation"]:checked');
    let id;

    if (completeDeleteConfirm) {
        for (i=0; i < selectedOptions.length; i++) {
            id = selectedOptions[i].value;
            // console.log(id);

            $.ajax({
                type:'DELETE',
                url:'/admin/campingcar/reservation/'+ id,
                dataType:'json',
                contentType : 'application/json; charset=utf-8',
            }).done(function (result) {
                if (result.result == 1) {
                    alert('삭제 되었습니다.');
                } else if (result.result == 0) {
                    alert('삭제에 문제가 생겼습니다.');
                };
                window.location.href = '/admin/campingcar/menu';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        }
    }
});

// 캠핑카 예약 확정, 확정 취소, 삭제 버튼_디테일 페이지
function setCampingReserve(behavior) {
    let carType = document.getElementById('carType').value.toLowerCase();
    let rentDate = document.getElementById('rentDate').value;
    let rentTime = document.getElementById('rentTime').value;
    let returnDate = document.getElementById('returnDate').value;
    let returnTime = document.getElementById('returnTime').value;
    let day = document.getElementById('day').innerText;
    let extraTime = document.getElementById('extraTime').innerText;
    let deposit = document.getElementById('deposit').value;
    let total = document.getElementById('total').value;
    let totalHalf = document.getElementById('totalHalf').value;
    let name = document.getElementById('name').value;
    let phone = document.getElementById('phone').value;
    let depositor = document.getElementById('depositor').value;
    let detail = document.getElementById('detail').value;
    let agree = parseInt(document.getElementById('agree').innerText);
    let reservation = document.getElementById('campingReservation').innerText;
    let id = document.getElementById('campingReservationId').innerText;
    let orderCode = document.getElementById('orderCode').innerText;
    let regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

    deposit = parseInt(deposit.replace(/,/g, ""));
    total = parseInt(total.replace(/,/g, ""));
    totalHalf = parseInt(totalHalf.replace(/,/g, ""));

    if (behavior === 'confirm') {
        reservation = 1;
    } else if (behavior === 'delete') {
        reservation = 0;
    } else if (behavior === 'edit') {
        if (reservation == '예약 O') {
            reservation = 1;
        } else if (reservation == '예약 X') {
            reservation = 0;
        };
    };

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

    let data = {
        carType: carType,
        rentDate: rentDate,
        rentTime: rentTime,
        returnDate: returnDate,
        returnTime: returnTime,
        day: day,
        extraTime: extraTime,
        deposit: deposit,
        total: total,
        totalHalf: totalHalf,
        name: name,
        phone: phone,
        depositor: depositor,
        detail: detail,
        agree: agree,
        reservation: reservation,
        orderCode: orderCode
    }

    // console.log(data);

    if (regPhone.test(phone) === false) {
        alert("연락처를 '010-1234-5678'의 형태로 작성해주세요.");
    } else {
        if (behavior === 'confirm') {
            if (confirm('예약을 확정하시겠습니까?')) {
                setCampingData();
            }
        } else if (behavior === 'delete') {
            if (confirm('예약 확정을 취소하시겠습니까?')) {
                setCampingData();
            }
        } else if (behavior === 'edit') {
            if (confirm('예약 내용을 수정하시겠습니까?')) {
                setCampingData();
            }
        }
    }

    function setCampingData() {
        $.ajax({
            type:'PUT',
            url:'/admin/campingcar/reservation/' + id,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('처리되었습니다.');
            } else if (result.result == 0) {
                alert('처리에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/campingcar/detail/' + id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
};

function displayExtraCampingData() {
    let rentDate = document.getElementById('rentDate').value;
    let rentTime = parseInt(document.getElementById('rentTime').value);
    let returnDate = document.getElementById('returnDate').value;
    let returnTime = parseInt(document.getElementById('returnTime').value);
    let day = document.getElementById('day');
    let extraTime = document.getElementById('extraTime');

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

// 캠핑카 예약 확정 버튼_메뉴 페이지
function setReservationOnMenu(event, behavior) {
    let carTypeList = document.getElementsByClassName('carTypeList');
    let rentDateList = document.getElementsByClassName('rentDateList');
    let rentTimeList = document.getElementsByClassName('rentTimeList');
    let returnDateList = document.getElementsByClassName('returnDateList');
    let returnTimeList = document.getElementsByClassName('returnTimeList');
    let dayList = document.getElementsByClassName('dayList');
    let extraTimeList = document.getElementsByClassName('extraTimeList');
    let depositList = document.getElementsByClassName('depositList');
    let totalList = document.getElementsByClassName('totalList');
    let totalHalfList = document.getElementsByClassName('totalHalfList');
    let nameList = document.getElementsByClassName('nameList');
    let phoneList = document.getElementsByClassName('phoneList');
    let depositorList = document.getElementsByClassName('depositorList');
    let detailList = document.getElementsByClassName('detailList');
    let agreeList = document.getElementsByClassName('agreeList');
    let idList = document.getElementsByClassName('campingReservationIdList');
    let orderCodeList = document.getElementsByClassName('orderCodeList');
    let carType, rentDate, rentTime, returnDate, returnTime, day, extraTime, deposit, total, totalHalf, name, phone, depositor, detail, agree, reservation, orderCode;
    let targetIndex = event.dataset.index;

    carType = [...carTypeList].find(carType => carType.dataset.index === targetIndex).innerText;
    rentDate = [...rentDateList].find(rentDate => rentDate.dataset.index === targetIndex).innerText;
    rentTime = [...rentTimeList].find(rentTime => rentTime.dataset.index === targetIndex).innerText;
    returnDate = [...returnDateList].find(returnDate => returnDate.dataset.index === targetIndex).innerText;
    returnTime = [...returnTimeList].find(returnTime => returnTime.dataset.index === targetIndex).innerText;
    day = [...dayList].find(day => day.dataset.index === targetIndex).innerText;
    extraTime = parseInt([...extraTimeList].find(extraTime => extraTime.dataset.index === targetIndex).innerText);
    deposit = parseInt([...depositList].find(deposit => deposit.dataset.index === targetIndex).innerText);
    total = parseInt([...totalList].find(total => total.dataset.index === targetIndex).innerText);
    totalHalf = parseInt([...totalHalfList].find(totalHalf => totalHalf.dataset.index === targetIndex).innerText);
    name = [...nameList].find(name => name.dataset.index === targetIndex).innerText;
    phone = [...phoneList].find(phone => phone.dataset.index === targetIndex).innerText;
    depositor = [...depositorList].find(depositor => depositor.dataset.index === targetIndex).innerText;
    detail = [...detailList].find(detail => detail.dataset.index === targetIndex).innerText;
    agree = parseInt([...agreeList].find(agree => agree.dataset.index === targetIndex).innerText);
    id = [...idList].find(id => id.dataset.index === targetIndex).innerText;
    orderCode = [...orderCodeList].find(orderCode => orderCode.dataset.index === targetIndex).innerText;

    if (behavior === 'confirm') {
        reservation = 1;
    } else if (behavior === 'delete') {
        reservation = 0;
    };

    let data = {
        carType: carType,
        rentDate: rentDate,
        rentTime: rentTime,
        returnDate: returnDate,
        returnTime: returnTime,
        day: day,
        extraTime: extraTime,
        deposit: deposit,
        total: total,
        totalHalf: totalHalf,
        name: name,
        phone: phone,
        depositor: depositor,
        detail: detail,
        agree: agree,
        reservation: reservation,
        orderCode: orderCode
    }

    // console.log(data);

    if (behavior == 'confirm') {
        if (confirm('해당 예약을 확정하시겠습니까?')) {
            sendingData();
        }
    } else if (behavior == 'delete') {
        if (confirm('확정된 예약을 취소하시겠습니까?')) {
            sendingData();
        }
    }

    function sendingData() {
        $.ajax({
            type:'PUT',
            url:'/admin/campingcar/reservation/' + id,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('처리되었습니다.');
            } else if (result.result == 0) {
                alert('처리에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/campingcar/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };
};