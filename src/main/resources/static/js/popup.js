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

// 팝업 내용 불러오기
$(window).load(function () {
    if (document.getElementById('popup2_contact')) {
        function getData(title, target) {
            $.ajax({
                type: "GET",
                url: `/index/popup/value/${title}`,
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
        };

        function getCampingcarInfoData(carType, targetCarName, targetPrice1, targetPrice2) {
            fetch(`/camping/calendar/${carType}/getprice/0`)
                .then(res => res.json())
                .then(result => {
                    const carName = document.getElementById(targetCarName);
                    const target1Ele = document.getElementById(targetPrice1);
                    const target2Ele = document.getElementById(targetPrice2);

                    carName.innerText = result['carName'].toUpperCase();

                    let price = result['onedays'].toString().substr(0, 3);

                    if (price.charAt(price.length -1) === '0') {
                        target1Ele.innerText = price.substr(0, 2);
                        target2Ele.innerText = price.substr(0, 2);
                    } else {
                        target1Ele.innerText = (parseInt(price) / 10).toString();
                        target2Ele.innerText = (parseInt(price) / 10).toString();
                    }
                })
        };

        getData('popup1_contact', 'popup1_contact');

        getData('popup1_banner1', 'popup1_banner1');
        getData('popup1_banner2', 'popup1_banner2');

        getData('popup1_box1_line1_title', 'popup1_box1_line1_title');
        getData('popup1_box1_line1_text1', 'popup1_box1_line1_text1');
        getData('popup1_box1_line1_price', 'popup1_box1_line1_price');
        getData('popup1_box1_line1_text2', 'popup1_box1_line1_text2');

        getData('popup1_box1_line2_title', 'popup1_box1_line2_title');
        getData('popup1_box1_line2_text1', 'popup1_box1_line2_text1');
        getData('popup1_box1_line2_price', 'popup1_box1_line2_price');
        getData('popup1_box1_line2_text2', 'popup1_box1_line2_text2');

        getData('popup1_box1_line3_title', 'popup1_box1_line3_title');
        getData('popup1_box1_line3_text1', 'popup1_box1_line3_text1');
        getData('popup1_box1_line3_price', 'popup1_box1_line3_price');
        getData('popup1_box1_line3_text2', 'popup1_box1_line3_text2');

        getData('popup1_box1_line4_title', 'popup1_box1_line4_title');
        getData('popup1_box1_line4_text1', 'popup1_box1_line4_text1');
        getData('popup1_box1_line4_price', 'popup1_box1_line4_price');
        getData('popup1_box1_line4_text2', 'popup1_box1_line4_text2');
        getData('popup1_box1_line4_text3', 'popup1_box1_line4_text3');

        getData('popup1_box1_line5_title', 'popup1_box1_line5_title');
        getData('popup1_box1_line5_text1', 'popup1_box1_line5_text1');
        getData('popup1_box1_line5_price', 'popup1_box1_line5_price');
        getData('popup1_box1_line5_text2', 'popup1_box1_line5_text2');
        // getData('popup1_box1_line5_text3', 'popup1_box1_line5_text3');

        getData('popup1_box1_line6_title', 'popup1_box1_line6_title');
        getData('popup1_box1_line6_text1', 'popup1_box1_line6_text1');
        getData('popup1_box1_line6_price', 'popup1_box1_line6_price');

        getData('popup1_box1_line7_title', 'popup1_box1_line7_title');
        getData('popup1_box1_line7_text1', 'popup1_box1_line7_text1');
        getData('popup1_box1_line7_price', 'popup1_box1_line7_price');
        getData('popup1_box1_line7_text2', 'popup1_box1_line7_text2');

        getData('popup1_box1_line8_title', 'popup1_box1_line8_title');
        getData('popup1_box1_line8_text1', 'popup1_box1_line8_text1');
        getData('popup1_box1_line8_price', 'popup1_box1_line8_price');
        getData('popup1_box1_line8_text2', 'popup1_box1_line8_text2');

        getData('popup1_box2_line1_title', 'popup1_box2_line1_title');
        getData('popup1_box2_line1_text1', 'popup1_box2_line1_text1');
        getData('popup1_box2_line1_price1', 'popup1_box2_line1_price1');
        getData('popup1_box2_line1_text2', 'popup1_box2_line1_text2');
        getData('popup1_box2_line1_price2', 'popup1_box2_line1_price2');
        getData('popup1_box2_line1_text3', 'popup1_box2_line1_text3');
        getData('popup1_box2_line2_title', 'popup1_box2_line2_title');
        getData('popup1_box2_line2_text1', 'popup1_box2_line2_text1');
        getData('popup1_box2_line2_price1', 'popup1_box2_line2_price1');
        getData('popup1_box2_line2_text2', 'popup1_box2_line2_text2');
        getData('popup1_box2_line2_price2', 'popup1_box2_line2_price2');
        getData('popup1_box2_line2_text3', 'popup1_box2_line2_text3');
        getData('popup1_box2_line3_title', 'popup1_box2_line3_title');
        getData('popup1_box2_line3_text1', 'popup1_box2_line3_text1');
        getData('popup1_box2_line3_price1', 'popup1_box2_line3_price1');
        getData('popup1_box2_line3_text2', 'popup1_box2_line3_text2');
        getData('popup1_box2_line3_price2', 'popup1_box2_line3_price2');
        getData('popup1_box2_line3_text3', 'popup1_box2_line3_text3');

        getData('popup1_box2_bottom_line1', 'popup1_box2_bottom_line1');
        getData('popup1_box2_bottom_line2_title', 'popup1_box2_bottom_line2_title');
        getData('popup1_box2_bottom_line2_text1', 'popup1_box2_bottom_line2_text1');
        getData('popup1_box2_bottom_line2_text2', 'popup1_box2_bottom_line2_text2');
        getData('popup1_box2_bottom_line3_title', 'popup1_box2_bottom_line3_title');
        getData('popup1_box2_bottom_line3_text1', 'popup1_box2_bottom_line3_text1');
        getData('popup1_box2_bottom_line3_text2', 'popup1_box2_bottom_line3_text2');

        getData('popup1_button', 'popup1_button');
        getData('popup2_contact', 'popup2_contact');
        getData('popup2_subtitle', 'popup2_subtitle');
        getData('popup2_title', 'popup2_title');
        getData('popup2_slogan', 'popup2_slogan');
        getData('popup2_box1_title', 'popup2_box1_title');
        getData('popup2_box1_subtitle', 'popup2_box1_subtitle');
        getData('popup2_box2_title', 'popup2_box2_title');
        getData('popup2_box2_subtitle', 'popup2_box2_subtitle');
        getData('popup2_promotion', 'popup2_promotion');
        getData('popup2_box3_line1_title', 'popup2_box3_line1_title');
        getData('popup2_box3_line1_price', 'popup2_box3_line1_price');
        getData('popup2_box3_line2_title', 'popup2_box3_line2_title');
        getData('popup2_box3_line2_price', 'popup2_box3_line2_price');
        getData('popup2_box3_line3_title', 'popup2_box3_line3_title');
        getData('popup2_box3_line3_price', 'popup2_box3_line3_price');
        getData('popup2_box4_line1_title', 'popup2_box4_line1_title');
        getData('popup2_box4_line1_price', 'popup2_box4_line1_price');
        getData('popup2_box4_line2_title', 'popup2_box4_line2_title');
        getData('popup2_box4_line2_price', 'popup2_box4_line2_price');
        getData('popup2_box4_line3_title', 'popup2_box4_line3_title');
        getData('popup2_box4_line3_price', 'popup2_box4_line3_price');
        getData('popup2_button', 'popup2_button');
        getCampingcarInfoData('europe', 'popup2_box1_title','popup2_box1_line1_price', 'popup2_box1_line2_price');
        getCampingcarInfoData('limousine', 'popup2_box2_title','popup2_box2_line1_price', 'popup2_box2_line2_price');

    };
});