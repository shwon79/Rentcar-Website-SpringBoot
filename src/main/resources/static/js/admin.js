// 할인 적용하기

const make_discount = () => {
    if (document.getElementById("carNo").value == ""){
        alert('차량 번호를 입력해주세요.')
        return
    }

    if (document.getElementById("discount").value == ""){
        alert('할인 가격을 입력해주세요.')
        return
    }

    if (document.getElementById("discount").value > 0){
        alert('현재는 할인가만 적용가능합니다. 음수값으로 입력해주세요.')
        return
    }

    let carNo = $("#carNo").val().replace(/(\s*)/g,""); // 공백 제거

    const existingCarNo = document.querySelectorAll('.editCarNo');
    const existingCarNoList = [];
    for (i = 0; i < existingCarNo.length; i++) {
        existingCarNoList.push(existingCarNo[i].innerText);
    }
    console.log(existingCarNoList.indexOf(carNo));

    if (existingCarNoList.indexOf(carNo) == -1) {
        var data = {
            carNo : carNo,
            discount : $("#discount").val()
        };
        console.log(data);

        $.ajax({
            type:'POST',
            url:'/admin/discount',
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('할인 가격이 적용되었습니다.');
            window.location.href = '/admin/discount/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    } else {
        alert('이미 할인이 적용된 차량입니다. 아래 수정 기능을 이용하세요.');
        window.location.href = '/admin/discount/menu';
    }
}


// 할인 수정하기

$('.update-btn').click(function(e) {
    let carNo = e.target.dataset.index;
    let originalDiscount = document.querySelectorAll('.editDiscount');
    let editDiscount = prompt("수정할 할인가(%)를 입력하세요.", "");

    if (editDiscount > 0){
        alert('현재는 할인가만 적용가능합니다. 음수값으로 입력해주세요.')
        return
    }

    // 할인 퍼센트 입력 안해줬을 경우 -> 원래대로
    if (!editDiscount) {
        for (let i = 0; i < originalDiscount.length; i++) {
            if (originalDiscount[i].dataset.index === carNo) {
                editDiscount = originalDiscount[i].innerText;
            }
        }
    } else if (editDiscount == 0) {
        alert('0% 할인을 할 수 없습니다. 삭제 버튼을 이용해주세요.');
    } else {
        $.ajax({
            type:'GET',
            url:'/admin/discount/update/'+ carNo + '/' + editDiscount,
            dataType:'json',
            contentType : 'application/json; charset=utf-8'
        }).done(function (result) {
            if (result.result == 1) {
                alert('할인 가격이 수정되었습니다.');
            } else if (result.result == 0) {
                alert('할인 가격 수정에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/discount/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
})


// 할인 삭제하기

$('.delete-btn').click(function(e) {
    let carNo = e.target.dataset.index;
    console.log(carNo);

    if (confirm("할인 가격을 삭제하시겠습니까?")) {
        $.ajax({
            type:'GET',
            url:'/admin/discount/delete/'+ carNo,
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