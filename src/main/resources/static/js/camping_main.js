
function check_camping_all_box(){
    const total_agree =  document.getElementById("agree4");
    const agrees = document.getElementsByClassName("agree");

    if(total_agree.checked == true) {
        for (var i = 0; i < agrees.length; i++) {
            var item = agrees.item(i);
            item.checked = true;
        }
    } else {
        for (var i = 0; i < agrees.length; i++) {
            var item = agrees.item(i);
            item.checked = false;
        }
    }
}

// 캠핑카 렌트 목록에서 가장 저렴한 가격 보여주기_camping/main
function displayCheapestPrice() {
    let euroObj, limoObj, travelObj;

    let europePrice = document.getElementById('europePrice');
    let limousinePrice = document.getElementById('limousinePrice');
    let travelPrice = document.getElementById('travelPrice');

    fetch(`/camping/calendar/europe/getprice/0`)
        .then(res => res.json())
        .then(result => {
            euroObj = result;
            europePrice.innerText = euroObj['onedays'].toLocaleString();
        });

    fetch(`/camping/calendar/limousine/getprice/0`)
        .then(res => res.json())
        .then(result => {
            limoObj = result;
            limousinePrice.innerText = limoObj['onedays'].toLocaleString();
        });

    fetch(`/camping/calendar/travel/getprice/0`)
        .then(res => res.json())
        .then(result => {
            travelObj = result;
            travelPrice.innerText = travelObj['onedays'].toLocaleString();
        });
};

// 위로 올라가는 버튼
const mybutton = document.getElementById('toTheTopBtn');
window.onscroll = function() {
    scrollFunction()
};

function scrollFunction() {
    if (document.body.scrollTop > 300 || document.documentElement.scrollTop > 300) {
        mybutton.style.display = "block";
    } else {
        mybutton.style.display = "none";
    }
};

function topFunction() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
};

function make_camping_main_reservation() {
    if (document.getElementById("reservation-simple-name").value === ""){
        alert('성함을 입력해주세요.')
        return
    }

    if (document.getElementById("reservation-simple-phone").value === ""){
        alert('전화번호를 입력해주세요.')
        return
    }

    const agree1 = document.getElementById('agree1').checked;
    const agree2 = document.getElementById('agree2').checked;
    const agree3 = document.getElementById('agree3').checked;

    let data = {
        name : $("#reservation-simple-name").val(),
        phoneNo : $("#reservation-simple-phone").val(),
        detail : $("#reservation-simple-details").val(),
        title : '캠핑카메인상담신청',
        product : '',
        category1 : '',
        category2 : '',
        car_name : '',
        mileage : '',
        deposit : '',
        age_limit : '',
        option : '',
        price : ''
    };

    // console.log(data);

    if (agree1 && agree2 && agree3) {
        $.ajax({
            type : 'POST',
            url : '/reservation/apply',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('상담 신청이 완료되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    } else {
        alert("개인정보 수집 및 이용에 동의해주세요.");
    };
}