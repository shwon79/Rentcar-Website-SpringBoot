const campingcarPopupEditBtn = document.getElementById('campingcarPopupEditBtn');

campingcarPopupEditBtn.addEventListener('click', () => {
    const contact = document.getElementById('popup_contact').value;

    let data = {
        "title" : "",
        "value" : "",
        "valuesList": [
            { "title" : "contact", "value" : contact }
        ]
    };
    console.log(data);

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