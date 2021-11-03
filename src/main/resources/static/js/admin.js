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

    var data = {
        carNo : $("#carNo").val(),
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
}


// 할인 수정하기

$('.update-btn').click(function(e) {
    let carNo = e.target.dataset.index;
    console.log(carNo);

    let editDiscount = prompt("수정할 할인가(%)를 입력하세요", "");

    var data = {
        carNo : carNo,
        discount : editDiscount
    };
    console.log(data);
    console.log(carNo);
    console.log(editDiscount);
    $.ajax({
        type:'GET',
        url:'/admin/discount/update/'+ carNo + '/' + editDiscount,
        dataType:'json',
        contentType : 'application/json; charset=utf-8'
    }).done(function () {
        alert('할인 가격이 수정되었습니다.');
        window.location.href = '/admin/discount/menu';
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
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
        }).done(function () {
            alert('할인 가격이 삭제되었습니다.');
            window.location.href = '/admin/discount/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
})