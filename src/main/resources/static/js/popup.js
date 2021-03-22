
function setCookie( name, value, expiredays ) {

    var todayDate = new Date();
    todayDate.setDate( todayDate.getDate() + expiredays );
    document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"

}

function closeWinOne() {

    if ( document.pop_form1.chkbox.checked ) {
        setCookie( "maindiv1", "done" , 1 );
        console.log('if pass')
    }

    document.getElementById('divpop1').style.visibility = "hidden";
}

function closeWinTwo() {

    if ( document.pop_form2.chkbox.checked ) {
        setCookie( "maindiv2", "done" , 1 );
        console.log('if pass')
    }

    document.getElementById('divpop2').style.visibility = "hidden";
}

function closeWinThree() {

    if ( document.pop_form3.chkbox.checked ) {
        setCookie( "maindiv3", "done" , 1 );
        console.log('if pass')
    }

    document.getElementById('divpop3').style.visibility = "hidden";
}


