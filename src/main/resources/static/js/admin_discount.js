// 할인 적용하기
const make_discount = () => {
    if (document.getElementById("carNo").value == ""){
        alert('차량 번호를 입력해주세요.')
        return
    };

    if (document.getElementById("carName").value == ""){
        alert('차량 이름을 입력해주세요.')
        return
    };

    if (document.getElementById("discount").value == ""){
        alert('할인 가격을 입력해주세요.')
        return
    };

    if (document.getElementById("discount-description").value == ""){
        alert('할인 설명을 입력해주세요.')
        return
    };

    let carNo = $("#carNo").val().replace(/(\s*)/g,""); // 공백 제거

    var data = {
        carNo : carNo,
        carName : $("#carName").val(),
        discount : $("#discount").val(),
        description: $("#discount-description").val()
    };
    // console.log(data);

    $.ajax({
        type:'POST',
        url:'/admin/discount',
        dataType:'json',
        contentType : 'application/json; charset=utf-8',
        data : JSON.stringify(data)
    }).done(function (result) {
        if (result.result == 1) {
            alert('할인 가격이 적용되었습니다.');
        } else if (result.result == 0) {
            alert('할인 가격이 적용에 문제가 생겼습니다.');
        };
        window.location.href = '/admin/discount/menu';
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
};

// 차량이름, 할인률, 할인 설명 수정하기
function discountUpdate(target, event) {
    let discountId = event.dataset.index;
    let promptText;

    switch (target) {
        case 'carName':
            promptText = '차량이름';
            break;
        case 'price':
            promptText = '할인 가격';
            break;
        case 'description':
            promptText = '할인 설명';
            break;
    };

    let editedData = prompt(`수정할 ${promptText}을/를 입력하세요.`, '');

    if (target === 'price' && editedData == 0) {
        alert('0% 할인을 할 수 없습니다. 삭제 버튼을 이용해주세요.');
    } else if (editedData) {
        $.ajax({
            type: 'PUT',
            url: '/admin/discount/' + target + '/' + discountId + '/' + editedData,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (result) {
            if (result.result == 1) {
                alert(`${promptText} 수정이 완료되었습니다.`);
            } else if (result.result == 0) {
                alert(`${promptText} 수정에 문제가 생겼습니다.`);
            };
            window.location.href = '/admin/discount/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
};


// 할인 삭제하기
$('.delete-btn').click(function(e) {
    let discountId = e.target.dataset.index;

    if (confirm("할인 가격을 삭제하시겠습니까?")) {
        $.ajax({
            type:'DELETE',
            url:'/admin/discount/'+ discountId,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
        }).done(function (result) {
            if (result.result == 1) {
                alert('할인 가격이 삭제되었습니다.');
            } else if (result.result == 0) {
                alert('할인 가격 삭제에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/discount/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
})