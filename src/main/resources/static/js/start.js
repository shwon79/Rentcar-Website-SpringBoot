
cookiedata = document.cookie;

if ( cookiedata.indexOf("maindiv1=done") < 0 ){
    document.getElementById('divpop1').style.visibility = "visible";
}
else {
    document.getElementById('divpop1').style.visibility = "hidden";
}

if ( cookiedata.indexOf("maindiv2=done") < 0 ){
    document.getElementById('divpop2').style.visibility = "visible";
}
else {
    document.getElementById('divpop2').style.visibility = "hidden";
}

if ( cookiedata.indexOf("maindiv3=done") < 0 ){
    document.getElementById('divpop3').style.visibility = "visible";
}
else {
    document.getElementById('divpop3').style.visibility = "hidden";
}

