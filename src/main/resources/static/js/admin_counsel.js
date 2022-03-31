// 상담 여러개 삭제 버튼_admin/counsel
function deleteMultiCounsel() {
    let selectedOptions = document.querySelectorAll('input[name="selected_counsel_reservation"]:checked');
    let idList = [];

    if (selectedOptions.length === 0) {
        alert('삭제할 내역을 선택해주세요.');
        return;
    };

    for (let i = 0; i < selectedOptions.length; i++) {
        idList.push(parseInt(selectedOptions[i].value));
    };

    const data = {
        idList: idList
    };

    let completeDeleteConfirm = confirm('삭제하시겠습니까?');
    if (completeDeleteConfirm) {
        $.ajax({
            type:'DELETE',
            url:'/admin/counsel/multiple',
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data: JSON.stringify(data),
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
};

// 유형 선택_admin/counsel/menu
function selectTitle() {
    let title = document.querySelector("input[name='title']:checked").value;
}

// 상담 삭제 버튼_admin/counsel/detail
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