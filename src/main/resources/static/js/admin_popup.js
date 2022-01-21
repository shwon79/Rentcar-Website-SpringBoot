// 기존 저장 내용 로딩시에 보여주기
window.onload = function() {
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
            url: `/admin/popup/value/${title}`,
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
            { "title" : "popup2_contact",
                "value" : popup2_contact },
            { "title" : "popup2_subtitle",
                "value" : popup2_subtitle },
            { "title" : "popup2_title",
                "value" : popup2_title },
            { "title" : "popup2_slogan",
                "value" : popup2_slogan },
            { "title" : "popup2_box1_subtitle",
                "value" : popup2_box1_subtitle },
            { "title" : "popup2_box1_line3",
                "value" : popup2_box1_line3 },
            { "title" : "popup2_box1_line4",
                "value" : popup2_box1_line4 },
            { "title" : "popup2_box2_subtitle",
                "value" : popup2_box2_subtitle },
            { "title" : "popup2_box2_line3",
                "value" : popup2_box2_line3 },
            { "title" : "popup2_box2_line4",
                "value" : popup2_box2_line4 },
            { "title" : "popup2_button",
                "value" : popup2_button }
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