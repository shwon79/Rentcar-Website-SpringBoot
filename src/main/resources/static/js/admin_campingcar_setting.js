// 기존 저장 내용 로딩시에 보여주기
window.onload = function() {
    function getData(title, target) {
        $.ajax({
            type: "GET",
            url: `/admin/value/${title}`,
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
}

// 버튼 누르면 변경 내용 저장
const campingcarPopupEditBtn = document.getElementById('campingcarPopupEditBtn');

campingcarPopupEditBtn.addEventListener('click', () => {
    const popup2_contact = document.getElementById('popup2_contact').value;
    const popup2_subtitle = document.getElementById('popup2_subtitle').value;
    const popup2_title = document.getElementById('popup2_title').value;
    const popup2_slogan = document.getElementById('popup2_slogan').value;
    const popup2_box1_title = document.getElementById('popup2_box1_title').value;
    const popup2_box1_subtitle = document.getElementById('popup2_box1_subtitle').value;
    const popup2_box1_line1_title = document.getElementById('popup2_box1_line1_title').value;
    const popup2_box1_line1_red_text = document.getElementById('popup2_box1_line1_red_text').value;
    const popup2_box1_line1_text = document.getElementById('popup2_box1_line1_text').value;
    const popup2_box1_line2_title = document.getElementById('popup2_box1_line2_title').value;
    const popup2_box1_line2_red_text = document.getElementById('popup2_box1_line2_red_text').value;
    const popup2_box1_line2_text = document.getElementById('popup2_box1_line2_text').value;
    const popup2_box1_line3 = document.getElementById('popup2_box1_line3').value;
    const popup2_box1_line4 = document.getElementById('popup2_box1_line4').value;
    const popup2_box2_title = document.getElementById('popup2_box2_title').value;
    const popup2_box2_subtitle = document.getElementById('popup2_box2_subtitle').value;
    const popup2_box2_line1_title = document.getElementById('popup2_box2_line1_title').value;
    const popup2_box2_line1_red_text = document.getElementById('popup2_box2_line1_red_text').value;
    const popup2_box2_line1_text = document.getElementById('popup2_box2_line1_text').value;
    const popup2_box2_line2_title = document.getElementById('popup2_box2_line2_title').value;
    const popup2_box2_line2_red_text = document.getElementById('popup2_box2_line2_red_text').value;
    const popup2_box2_line2_text = document.getElementById('popup2_box2_line2_text').value;
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
            { "title" : "popup2_box1_title",
                "value" : popup2_box1_title },
            { "title" : "popup2_box1_subtitle",
                "value" : popup2_box1_subtitle },
            { "title" : "popup2_box1_line1_title",
                "value" : popup2_box1_line1_title },
            { "title" : "popup2_box1_line1_red_text",
                "value" : popup2_box1_line1_red_text },
            { "title" : "popup2_box1_line1_text",
                "value" : popup2_box1_line1_text },
            { "title" : "popup2_box1_line2_title",
                "value" : popup2_box1_line2_title },
            { "title" : "popup2_box1_line2_red_text",
                "value" : popup2_box1_line2_red_text },
            { "title" : "popup2_box1_line2_text",
                "value" : popup2_box1_line2_text },
            { "title" : "popup2_box1_line3",
                "value" : popup2_box1_line3 },
            { "title" : "popup2_box1_line4",
                "value" : popup2_box1_line4 },
            { "title" : "popup2_box2_title",
                "value" : popup2_box2_title },
            { "title" : "popup2_box2_subtitle",
                "value" : popup2_box2_subtitle },
            { "title" : "popup2_box2_line1_title",
                "value" : popup2_box2_line1_title },
            { "title" : "popup2_box2_line1_red_text",
                "value" : popup2_box2_line1_red_text },
            { "title" : "popup2_box2_line1_text",
                "value" : popup2_box2_line1_text },
            { "title" : "popup2_box2_line2_title",
                "value" : popup2_box2_line2_title },
            { "title" : "popup2_box2_line2_red_text",
                "value" : popup2_box2_line2_red_text },
            { "title" : "popup2_box2_line2_text",
                "value" : popup2_box2_line2_text },
            { "title" : "popup2_box2_line3",
                "value" : popup2_box2_line3 },
            { "title" : "popup2_box2_line4",
                "value" : popup2_box2_line4 },
            { "title" : "popup2_button",
                "value" : popup2_button }
        ]
    };
    // console.log(data);

    $.ajax({
        type:'POST',
        url:'/admin/value',
        dataType:'json',
        contentType : 'application/json; charset=utf-8',
        data : JSON.stringify(data)
    }).done(function (result) {
        if (result.result == 1) {
            alert('완료되었습니다.');
            window.location.href = '/admin/setting/menu';
        } else {
            alert('문제가 생겼습니다.');
        };
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
});