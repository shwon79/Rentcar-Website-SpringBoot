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

// function closeWinThree() {
//
//     if ( document.pop_form3.chkbox.checked ) {
//         setCookieAt00( "maindiv3", "done" , 1 );
//         console.log('if pass')
//     }
//
//     document.getElementById('divpop3').style.visibility = "hidden";
// }
//
//
// function closeWinFour() {
//
//     if ( document.pop_form4.chkbox.checked ) {
//         setCookieAt00( "maindiv4", "done" , 1 );
//         console.log('if pass')
//     }
//
//     document.getElementById('divpop4').style.visibility = "hidden";
// }

// 팝업 내용 불러오기
$(window).load(function () {
    if (document.getElementById('popup2_contact')) {
        function getData(title, target) {
            $.ajax({
                type: "GET",
                url: `/admin/value/${title}`,
                dataType: "json",
                cache: false,
                success: function(data){
                    let targetEle = document.getElementById(target);
                    targetEle.innerText = data.value;
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    console.log("Status: " + textStatus);
                }
            });
        }

        getData('popup2_contact', 'popup2_contact');
        getData('popup2_subtitle', 'popup2_subtitle');
        getData('popup2_title', 'popup2_title');
        getData('popup2_slogan', 'popup2_slogan');
        getData('popup2_box1_title', 'popup2_box1_title');
        getData('popup2_box1_subtitle', 'popup2_box1_subtitle');
        getData('popup2_box1_line1_title', 'popup2_box1_line1_title');
        getData('popup2_box1_line1_red_text', 'popup2_box1_line1_red_text');
        getData('popup2_box1_line1_text', 'popup2_box1_line1_text');
        getData('popup2_box1_line2_title', 'popup2_box1_line2_title');
        getData('popup2_box1_line2_red_text', 'popup2_box1_line2_red_text');
        getData('popup2_box1_line2_text', 'popup2_box1_line2_text');
        getData('popup2_box1_line3', 'popup2_box1_line3');
        getData('popup2_box1_line4', 'popup2_box1_line4');
        getData('popup2_box2_subtitle', 'popup2_box2_subtitle');
        getData('popup2_box2_title', 'popup2_box2_title');
        getData('popup2_box2_line1_title', 'popup2_box2_line1_title');
        getData('popup2_box2_line1_red_text', 'popup2_box2_line1_red_text');
        getData('popup2_box2_line1_text', 'popup2_box2_line1_text');
        getData('popup2_box2_line2_title', 'popup2_box2_line2_title');
        getData('popup2_box2_line2_red_text', 'popup2_box2_line2_red_text');
        getData('popup2_box2_line2_text', 'popup2_box2_line2_text');
        getData('popup2_box2_line3', 'popup2_box2_line3');
        getData('popup2_box2_line4', 'popup2_box2_line4');
        getData('popup2_button', 'popup2_button');
    };
});