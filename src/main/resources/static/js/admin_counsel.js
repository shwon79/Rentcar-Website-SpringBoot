// 상담 삭제 버튼
function deleteCounsel(id) {
    let completeDeleteConfirm = confirm('삭제하시겠습니까?');

    if (completeDeleteConfirm) {
        $.ajax({
            type:'DELETE',
            url:'/admin/counsel/'+ id,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
        }).done(function (result) {
            if (result.result == 1) {
                alert('삭제 되었습니다.');
            } else if (result.result == 0) {
                alert('삭제에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/counsel/menu?page=0&size=20';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}