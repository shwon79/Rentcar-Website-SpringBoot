function changes(fr, detailedSelect) {
    console.log(fr)
    $.ajax({
        type: 'POST',
        url: '/monthly/' + fr,
        contentType: "application/json; charset=UTF-8",
        dataType: 'json',
        success: function (result) {
            console.log(result)
            for (i = 0; i < detailedSelect.length; i++) {
                detailedSelect.options[0] = null;
            }
            //포문을 이용하여 두번째(test2)셀렉트 박스에 값을 뿌려줍니당)
            for (i = 0; i < result.length; i++) {
                detailedSelect.options[i] = new Option(result[i], i);
            }
        }
    }).done(function () {
        alert('찾기 완료');
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}