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
};

// 세부메뉴 기억하고 보여주기
function displayActiveMenu() {
    let menu = sessionStorage.getItem('activeMenu');
    const campingSubmenuBtn = document.getElementById('camping-submenu-btn');
    const campingcarSubmenu = document.getElementById('campingcarSubmenu');
    const priceSubmenuBtn = document.getElementById('price-submenu-btn');
    const priceSubMenu = document.getElementById('priceSubMenu');

    if (menu === 'campingCar') {
        campingSubmenuBtn && campingSubmenuBtn.classList.add('active-span');
        campingcarSubmenu && campingcarSubmenu.classList.add('camping-open');
        priceSubmenuBtn.style.top = '433px';
    } else if (menu === 'rentCar') {
        priceSubmenuBtn && priceSubmenuBtn.classList.add('active-span');
        priceSubMenu && priceSubMenu.classList.add('price-open');
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
};

$('.number').ready(numberWithCommas());