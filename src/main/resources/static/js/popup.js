
function setCookie( name, value, expiredays ) {

    var todayDate = new Date();
    todayDate.setDate( todayDate.getDate() + expiredays );
    document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
    console.log(todayDate.getDate())
}

// 00:00 시 기준 쿠키 설정하기
// expiredays 의 새벽  00:00:00 까지 쿠키 설정
function setCookieAt00( name, value, expiredays ) {
    var todayDate = new Date();
    todayDate = new Date(parseInt(todayDate.getTime() / 86400000) * 86400000 + 54000000);
    if ( todayDate > new Date() )
    {
        expiredays = expiredays - 1;
    }
    todayDate.setDate( todayDate.getDate() + expiredays );
    document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
    console.log(document.cookie)
}


function closeWinOne() {

    if ( document.pop_form1.chkbox.checked ) {
        setCookieAt00( "maindiv1", "done" , 1 );
        console.log('if pass')
    }

    document.getElementById('divpop1').style.visibility = "hidden";
}

function closeWinTwo() {

    if ( document.pop_form2.chkbox.checked ) {
        setCookieAt00( "maindiv2", "done" , 1 );
        console.log('if pass')
    }

    document.getElementById('divpop2').style.visibility = "hidden";
}

function closeWinThree() {

    if ( document.pop_form3.chkbox.checked ) {
        setCookieAt00( "maindiv3", "done" , 1 );
        console.log('if pass')
    }

    document.getElementById('divpop3').style.visibility = "hidden";
}


function closeWinFour() {

    if ( document.pop_form4.chkbox.checked ) {
        setCookieAt00( "maindiv4", "done" , 1 );
        console.log('if pass')
    }

    document.getElementById('divpop4').style.visibility = "hidden";
}