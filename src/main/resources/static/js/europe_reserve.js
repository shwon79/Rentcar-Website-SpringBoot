
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


let today = new Date();
let todayFull = today.toLocaleDateString();
let todayDay = '';
switch (today.getDay()) {
        case 0 : todayDay = '일';
        case 1 : todayDay = '월';
        case 2 : todayDay = '화';
        case 3 : todayDay = '수';
        case 4 : todayDay = '목';
        case 5 : todayDay = '금';
        case 6 : todayDay = '토';
}

document.getElementById('rent_date').innerText = `${todayFull}(${todayDay})`;

/*
let rentDay = 0;
let returnDay = 0;
const rentDaySelected = () => {
    rentDay = 1;
}

const returnDaySelected = () => {
    returnDay = 1;
}
 */

let rentDateNum;
const rentDate = (id) => {
    rentDateNum = id;
    console.log(id);
}

let returnDateNum;
const returnDate = (id) => {
    returnDateNum = id;
    console.log(id)
}


const calculateDate = () => {
    let date1 = rentDateNum.split('/');
    let date2 = returnDateNum.split('/');
    let month1 = date1[0];
    let day1 = date1[1];
    let month2 = date2[0];
    let day2 = date2[1];
    let monthDiffer = month2-month1;
    let dayDiffer = day2 - day1;
    let dateDiffer = 0;
    if(monthDiffer > 0) {
        if (dayDiffer > 0) dateDiffer = monthDiffer+1;
        else if (dateDiffer <= 0) dateDiffer = monthDiffer;
    } else if(monthDiffer == 0) dateDiffer = 1;

    console.log(dateDiffer);
    document.getElementById('calResult').innerText = `${dateDiffer}달`;
}

// time select onclick
let rentTime = '';
const rentTimeSel = (id) => {
    console.log(id);
    rentTime = id;
}


let finalDate = {
    rentDate: rentDateNum,
    rentTime: rentTime,
    returnDate: returnDateNum,
}

const postDate = () => {
    console.log(finalDate);
    /*
    let url = '';
    fetch(url, {
        method: 'POST',
        body: JSON.stringify(finalDate),
        headers:{
            'Content-Type' : 'application/json'
        }
    }).then(res => res.json())
        .then(response => console.log('Success: ', JSON.stringify(response)))
        .catch(err => console.error('Error: ', err))

     */
}

