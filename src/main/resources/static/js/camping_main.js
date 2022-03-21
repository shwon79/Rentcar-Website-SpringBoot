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