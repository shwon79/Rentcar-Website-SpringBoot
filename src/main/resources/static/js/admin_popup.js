// 기존 저장 내용 로딩시에 보여주기
window.onload = function() {
    getEditedData('popup1_contact', 'popup1_contact');
    getEditedData('popup1_box1_line1_title', 'popup1_box1_line1_title');
    getEditedData('popup1_box1_line1_text1', 'popup1_box1_line1_text1');
    getEditedData('popup1_box1_line1_price', 'popup1_box1_line1_price');
    getEditedData('popup1_box1_line1_text2', 'popup1_box1_line1_text2');
    getEditedData('popup1_box1_line2_title', 'popup1_box1_line2_title');
    getEditedData('popup1_box1_line2_text1', 'popup1_box1_line2_text1');
    getEditedData('popup1_box1_line2_price', 'popup1_box1_line2_price');
    getEditedData('popup1_box1_line2_text2', 'popup1_box1_line2_text2');
    getEditedData('popup1_box1_line3_title', 'popup1_box1_line3_title');
    getEditedData('popup1_box1_line3_text1', 'popup1_box1_line3_text1');
    getEditedData('popup1_box1_line3_price', 'popup1_box1_line3_price');
    getEditedData('popup1_box1_line3_text2', 'popup1_box1_line3_text2');
    getEditedData('popup1_box1_line4_title', 'popup1_box1_line4_title');
    getEditedData('popup1_box1_line4_text1', 'popup1_box1_line4_text1');
    getEditedData('popup1_box1_line4_price', 'popup1_box1_line4_price');
    getEditedData('popup1_box1_line4_text2', 'popup1_box1_line4_text2');
    getEditedData('popup1_box1_line5_title', 'popup1_box1_line5_title');
    getEditedData('popup1_box1_line5_text1', 'popup1_box1_line5_text1');
    getEditedData('popup1_box1_line5_price', 'popup1_box1_line5_price');
    getEditedData('popup1_box1_line5_text2', 'popup1_box1_line5_text2');
    getEditedData('popup1_box1_line6_title', 'popup1_box1_line6_title');
    getEditedData('popup1_box1_line6_text1', 'popup1_box1_line6_text1');
    getEditedData('popup1_box1_line6_price', 'popup1_box1_line6_price');
    getEditedData('popup1_box1_line6_text2', 'popup1_box1_line6_text2');

    getEditedData('popup1_box2_line1_title', 'popup1_box1_line1_title');
    getEditedData('popup1_box2_line1_text1', 'popup1_box1_line1_text1');
    getEditedData('popup1_box2_line1_price1', 'popup1_box1_line1_price1');
    getEditedData('popup1_box2_line1_text2', 'popup1_box1_line1_text2');
    getEditedData('popup1_box2_line1_price2', 'popup1_box1_line1_price2');
    getEditedData('popup1_box2_line1_text3', 'popup1_box1_line1_text3');
    getEditedData('popup1_box2_line2_title', 'popup1_box1_line2_title');
    getEditedData('popup1_box2_line2_text1', 'popup1_box1_line2_text1');
    getEditedData('popup1_box2_line2_price1', 'popup1_box1_line2_price1');
    getEditedData('popup1_box2_line2_text2', 'popup1_box1_line2_text2');
    getEditedData('popup1_box2_line2_price2', 'popup1_box1_line2_price2');
    getEditedData('popup1_box2_line2_text3', 'popup1_box1_line2_text3');
    getEditedData('popup1_box2_line3_title', 'popup1_box1_line3_title');
    getEditedData('popup1_box2_line3_text1', 'popup1_box1_line3_text1');
    getEditedData('popup1_box2_line3_price1', 'popup1_box1_line3_price1');
    getEditedData('popup1_box2_line3_text2', 'popup1_box1_line3_text2');
    getEditedData('popup1_box2_line3_price2', 'popup1_box1_line3_price2');
    getEditedData('popup1_box2_line3_text3', 'popup1_box1_line3_text3');

    getEditedData('popup1_box2_bottom_line1', 'popup1_box1_bottom_line1');
    getEditedData('popup1_box2_bottom_line2_title', 'popup1_box1_bottom_line2_title');
    getEditedData('popup1_box2_bottom_line2_text1', 'popup1_box1_bottom_line2_text1');
    getEditedData('popup1_box2_bottom_line2_text2', 'popup1_box1_bottom_line2_text2');
    getEditedData('popup1_box2_bottom_line3_title', 'popup1_box1_bottom_line3_title');
    getEditedData('popup1_box2_bottom_line3_text1', 'popup1_box1_bottom_line3_text1');
    getEditedData('popup1_box2_bottom_line3_text2', 'popup1_box1_bottom_line3_text2');

    getEditedData('popup1_button', 'popup1_button');

    getEditedData('popup2_contact', 'popup2_contact');
    getEditedData('popup2_subtitle', 'popup2_subtitle');
    getEditedData('popup2_title', 'popup2_title');
    getEditedData('popup2_slogan', 'popup2_slogan');
    getEditedData('popup2_box1_subtitle', 'popup2_box1_subtitle');
    getEditedData('popup2_box1_line3', 'popup2_box1_line3');
    getEditedData('popup2_box1_line4', 'popup2_box1_line4');
    getEditedData('popup2_box2_subtitle', 'popup2_box2_subtitle');
    getEditedData('popup2_box2_line3', 'popup2_box2_line3');
    getEditedData('popup2_box2_line4', 'popup2_box2_line4');
    getEditedData('popup2_button', 'popup2_button');
    getCampingcarInfoData('europe', 'popup2_box1_title','popup2_box1_line1_price', 'popup2_box1_line2_price');
    getCampingcarInfoData('limousine', 'popup2_box2_title','popup2_box2_line1_price', 'popup2_box2_line2_price');

    function getEditedData(title, target) {
        $.ajax({
            type: "GET",
            url: `/index/popup/value/${title}`,
            dataType: "json",
            cache: false,
            success: function(data){
                let targetEle = document.getElementById(target);
                targetEle.value = data.value;
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
}

// 버튼 누르면 변경 내용 저장
const campingcarPopupEditBtn = document.getElementById('campingcarInfoEditBtn');

campingcarPopupEditBtn.addEventListener('click', () => {
    const popup1_contact = document.getElementById('popup1_contact').value;
    const popup1_box1_line1_title = document.getElementById('popup1_box1_line1_title').value;
    const popup1_box1_line1_text1 = document.getElementById('popup1_box1_line1_text1').value;
    const popup1_box1_line1_price = document.getElementById('popup1_box1_line1_price').value;
    const popup1_box1_line1_text2 = document.getElementById('popup1_box1_line1_text2').value;
    const popup1_box1_line2_title = document.getElementById('popup1_box1_line2_title').value;
    const popup1_box1_line2_text1 = document.getElementById('popup1_box1_line2_text1').value;
    const popup1_box1_line2_price = document.getElementById('popup1_box1_line2_price').value;
    const popup1_box1_line2_text2 = document.getElementById('popup1_box1_line2_text2').value;
    const popup1_box1_line3_title = document.getElementById('popup1_box1_line3_title').value;
    const popup1_box1_line3_text1 = document.getElementById('popup1_box1_line3_text1').value;
    const popup1_box1_line3_price = document.getElementById('popup1_box1_line3_price').value;
    const popup1_box1_line3_text2 = document.getElementById('popup1_box1_line3_text2').value;
    const popup1_box1_line4_title = document.getElementById('popup1_box1_line4_title').value;
    const popup1_box1_line4_text1 = document.getElementById('popup1_box1_line4_text1').value;
    const popup1_box1_line4_price = document.getElementById('popup1_box1_line4_price').value;
    const popup1_box1_line4_text2 = document.getElementById('popup1_box1_line4_text2').value;
    const popup1_box1_line5_title = document.getElementById('popup1_box1_line5_title').value;
    const popup1_box1_line5_text1 = document.getElementById('popup1_box1_line5_text1').value;
    const popup1_box1_line5_price = document.getElementById('popup1_box1_line5_price').value;
    const popup1_box1_line5_text2 = document.getElementById('popup1_box1_line5_text2').value;
    const popup1_box1_line6_title = document.getElementById('popup1_box1_line6_title').value;
    const popup1_box1_line6_text1 = document.getElementById('popup1_box1_line6_text1').value;
    const popup1_box1_line6_price = document.getElementById('popup1_box1_line6_price').value;
    const popup1_box1_line6_text2 = document.getElementById('popup1_box1_line6_text2').value;

    const popup1_box2_line1_title = document.getElementById('popup1_box2_line1_title').value;
    const popup1_box2_line1_text1 = document.getElementById('popup1_box2_line1_text1').value;
    const popup1_box2_line1_price1 = document.getElementById('popup1_box2_line1_price1').value;
    const popup1_box2_line1_text2 = document.getElementById('popup1_box2_line1_text2').value;
    const popup1_box2_line1_price2 = document.getElementById('popup1_box2_line1_price2').value;
    const popup1_box2_line1_text3 = document.getElementById('popup1_box2_line1_text3').value;
    const popup1_box2_line2_title = document.getElementById('popup1_box2_line2_title').value;
    const popup1_box2_line2_text1 = document.getElementById('popup1_box2_line2_text1').value;
    const popup1_box2_line2_price1 = document.getElementById('popup1_box2_line2_price1').value;
    const popup1_box2_line2_text2 = document.getElementById('popup1_box2_line2_text2').value;
    const popup1_box2_line2_price2 = document.getElementById('popup1_box2_line2_price2').value;
    const popup1_box2_line2_text3 = document.getElementById('popup1_box2_line2_text3').value;
    const popup1_box2_line3_title = document.getElementById('popup1_box2_line3_title').value;
    const popup1_box2_line3_text1 = document.getElementById('popup1_box2_line3_text1').value;
    const popup1_box2_line3_price1 = document.getElementById('popup1_box2_line3_price1').value;
    const popup1_box2_line3_text2 = document.getElementById('popup1_box2_line3_text2').value;
    const popup1_box2_line3_price2 = document.getElementById('popup1_box2_line3_price2').value;
    const popup1_box2_line3_text3 = document.getElementById('popup1_box2_line3_text3').value;

    const popup1_box2_bottom_line1 = document.getElementById('popup1_box2_bottom_line1').value;
    const popup1_box2_bottom_line2_title = document.getElementById('popup1_box2_bottom_line2_title').value;
    const popup1_box2_bottom_line2_text1 = document.getElementById('popup1_box2_bottom_line2_text1').value;
    const popup1_box2_bottom_line2_text2 = document.getElementById('popup1_box2_bottom_line2_text2').value;
    const popup1_box2_bottom_line3_title = document.getElementById('popup1_box2_bottom_line3_title').value;
    const popup1_box2_bottom_line3_text1 = document.getElementById('popup1_box2_bottom_line3_text1').value;
    const popup1_box2_bottom_line3_text2 = document.getElementById('popup1_box2_bottom_line3_text2').value;

    const popup1_button = document.getElementById('popup1_button').value;

    const popup2_contact = document.getElementById('popup2_contact').value;
    const popup2_subtitle = document.getElementById('popup2_subtitle').value;
    const popup2_title = document.getElementById('popup2_title').value;
    const popup2_slogan = document.getElementById('popup2_slogan').value;
    const popup2_box1_subtitle = document.getElementById('popup2_box1_subtitle').value;
    const popup2_box1_line3 = document.getElementById('popup2_box1_line3').value;
    const popup2_box1_line4 = document.getElementById('popup2_box1_line4').value;
    const popup2_box2_subtitle = document.getElementById('popup2_box2_subtitle').value;
    const popup2_box2_line3 = document.getElementById('popup2_box2_line3').value;
    const popup2_box2_line4 = document.getElementById('popup2_box2_line4').value;
    const popup2_button = document.getElementById('popup2_button').value;

    let data = {
        "valuesList": [
            { "title" : "popup1_contact", "value" : popup1_contact },
            { "title" : "popup1_box1_line1_title", "value" : popup1_box1_line1_title },
            { "title" : "popup1_box1_line1_text1", "value" : popup1_box1_line1_text1 },
            { "title" : "popup1_box1_line1_price", "value" : popup1_box1_line1_price },
            { "title" : "popup1_box1_line1_text2", "value" : popup1_box1_line1_text2 },
            { "title" : "popup1_box1_line2_title", "value" : popup1_box1_line2_title },
            { "title" : "popup1_box1_line2_text1", "value" : popup1_box1_line2_text1 },
            { "title" : "popup1_box1_line2_price", "value" : popup1_box1_line2_price },
            { "title" : "popup1_box1_line2_text2", "value" : popup1_box1_line2_text2 },
            { "title" : "popup1_box1_line3_title", "value" : popup1_box1_line3_title },
            { "title" : "popup1_box1_line3_text1", "value" : popup1_box1_line3_text1 },
            { "title" : "popup1_box1_line3_price", "value" : popup1_box1_line3_price },
            { "title" : "popup1_box1_line3_text2", "value" : popup1_box1_line3_text2 },
            { "title" : "popup1_box1_line4_title", "value" : popup1_box1_line4_title },
            { "title" : "popup1_box1_line4_text1", "value" : popup1_box1_line4_text1 },
            { "title" : "popup1_box1_line4_price", "value" : popup1_box1_line4_price },
            { "title" : "popup1_box1_line4_text2", "value" : popup1_box1_line4_text2 },
            { "title" : "popup1_box1_line5_title", "value" : popup1_box1_line5_title },
            { "title" : "popup1_box1_line5_text1", "value" : popup1_box1_line5_text1 },
            { "title" : "popup1_box1_line5_price", "value" : popup1_box1_line5_price },
            { "title" : "popup1_box1_line5_text2", "value" : popup1_box1_line5_text2 },
            { "title" : "popup1_box1_line6_title", "value" : popup1_box1_line6_title },
            { "title" : "popup1_box1_line6_text1", "value" : popup1_box1_line6_text1 },
            { "title" : "popup1_box1_line6_price", "value" : popup1_box1_line6_price },
            { "title" : "popup1_box1_line6_text2", "value" : popup1_box1_line6_text2 },
            { "title" : "popup1_box2_line1_title", "value" : popup1_box2_line1_title },
            { "title" : "popup1_box2_line1_text1", "value" : popup1_box2_line1_text1 },
            { "title" : "popup1_box2_line1_price1", "value" : popup1_box2_line1_price1 },
            { "title" : "popup1_box2_line1_text2", "value" : popup1_box2_line1_text2 },
            { "title" : "popup1_box2_line1_price2", "value" : popup1_box2_line1_price2 },
            { "title" : "popup1_box2_line1_text3", "value" : popup1_box2_line1_text3 },
            { "title" : "popup1_box2_line2_title", "value" : popup1_box2_line2_title },
            { "title" : "popup1_box2_line2_text1", "value" : popup1_box2_line2_text1 },
            { "title" : "popup1_box2_line2_price1", "value" : popup1_box2_line2_price1 },
            { "title" : "popup1_box2_line2_text2", "value" : popup1_box2_line2_text2 },
            { "title" : "popup1_box2_line2_price2", "value" : popup1_box2_line2_price2 },
            { "title" : "popup1_box2_line2_text3", "value" : popup1_box2_line2_text3 },
            { "title" : "popup1_box2_line3_title", "value" : popup1_box2_line3_title },
            { "title" : "popup1_box2_line3_text1", "value" : popup1_box2_line3_text1 },
            { "title" : "popup1_box2_line3_price1", "value" : popup1_box2_line3_price1 },
            { "title" : "popup1_box2_line3_text2", "value" : popup1_box2_line3_text2 },
            { "title" : "popup1_box2_line3_price2", "value" : popup1_box2_line3_price2 },
            { "title" : "popup1_box2_line3_text3", "value" : popup1_box2_line3_text3 },
            { "title" : "popup1_box2_bottom_line1", "value" : popup1_box2_bottom_line1 },
            { "title" : "popup1_box2_bottom_line2_title", "value" : popup1_box2_bottom_line2_title },
            { "title" : "popup1_box2_bottom_line2_text1", "value" : popup1_box2_bottom_line2_text1 },
            { "title" : "popup1_box2_bottom_line2_text2", "value" : popup1_box2_bottom_line2_text2 },
            { "title" : "popup1_box2_bottom_line3_title", "value" : popup1_box2_bottom_line3_title },
            { "title" : "popup1_box2_bottom_line3_text1", "value" : popup1_box2_bottom_line3_text1 },
            { "title" : "popup1_box2_bottom_line3_text2", "value" : popup1_box2_bottom_line3_text2 },
            { "title" : "popup1_button", "value" : popup1_button },
            { "title" : "popup2_contact", "value" : popup2_contact },
            { "title" : "popup2_subtitle", "value" : popup2_subtitle },
            { "title" : "popup2_title", "value" : popup2_title },
            { "title" : "popup2_slogan", "value" : popup2_slogan },
            { "title" : "popup2_box1_subtitle", "value" : popup2_box1_subtitle },
            { "title" : "popup2_box1_line3", "value" : popup2_box1_line3 },
            { "title" : "popup2_box1_line4", "value" : popup2_box1_line4 },
            { "title" : "popup2_box2_subtitle", "value" : popup2_box2_subtitle },
            { "title" : "popup2_box2_line3", "value" : popup2_box2_line3 },
            { "title" : "popup2_box2_line4", "value" : popup2_box2_line4 },
            { "title" : "popup2_button", "value" : popup2_button }
        ]
    };
    // console.log(data);

    if (confirm('팝업 내용을 수정하시겠습니까?')) {
        $.ajax({
            type:'POST',
            url:'/admin/popup/value',
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('완료되었습니다.');
                window.location.href = '/admin/popup/menu';
            } else {
                alert('문제가 생겼습니다.');
            };
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };
});