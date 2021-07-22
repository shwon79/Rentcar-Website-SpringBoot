
// Date 객체 생성
const date = new Date();

const viewYear = date.getFullYear();
const viewMonth = date.getMonth();

// year-month 채우기
document.querySelector('.year-month').textContent = `${viewYear}년 ${viewMonth + 1}월`;


function doDisplay(){
    var con = document.getElementById("calendar_rental")
    if(con.style.display==='none'){
        con.style.display = 'block';
    } else {
        con.style.display = 'none';
    }
}
function doDisplay_rent_time(){
    var con = document.getElementById("calendar_rental_time")
    if(con.style.display==='none'){
        con.style.display = 'block';
    } else {
        con.style.display = 'none';
    }
}

function doDisplay_return(){
    var con = document.getElementById("calendar_return")
    if(con.style.display==='none'){
        con.style.display = 'block';
    } else {
        con.style.display = 'none';
    }
}

function doDisplay_return_time(){
    var con = document.getElementById("calendar_return_time")
    if(con.style.display==='none'){
        con.style.display = 'block';
    } else {
        con.style.display = 'none';
    }
}

// year-month 채우기
document.querySelector('.year-month-return').textContent = `${viewYear}년 ${viewMonth + 1}월`;