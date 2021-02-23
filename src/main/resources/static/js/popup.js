function setCookie(name, value, expiredays) {
    var todayDate = new Date();
    todayDate.setDate(todayDate.getDate() + expiredays);
    document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}

function closeWin1() {
    if (document.notice_form1.chkbox1.checked) {
        setCookie("maindiv", "done", 1);
    }
    document.all['divpop1'].style.visibility = "hidden";
}

function closeWin2() {
    if (document.notice_form2.chkbox2.checked) {
        setCookie("maindiv", "done", 1);
    }
    document.all['divpop2'].style.visibility = "hidden";
}

cookiedata = document.cookie;
if (cookiedata.indexOf("maindiv=done") < 0) {
    document.all['divpop'].style.visibility = "visible";
} else {
    document.all['divpop'].style.visibility = "hidden";
}