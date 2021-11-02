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
        type:'post',
        url:'/admin/discount',
        dataType:'json',
        contentType : 'application/json; charset=utf-8',
        data : JSON.stringify(data)
    }).done(function (result) {
        alert('할인 가격이 적용되었습니다.');
        console.log(result);
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}


// 할인 수정하기

const update_discount = () => {

    // var data = {
    //     carNo : $("#carNo").val(),
    //     discount : $("#discount").val()
    // };
    // console.log(data);
    //
    // $.ajax({
    //     type:'post',
    //     url:'/admin/discount',
    //     dataType:'json',
    //     contentType : 'application/json; charset=utf-8',
    //     data : JSON.stringify(data)
    // }).done(function (result) {
    //     alert('할인 가격이 적용되었습니다.');
    //     console.log(result);
    // }).fail(function (error) {
    //     alert(JSON.stringify(error));
    // })
}


// 할인 삭제하기

const delete_discount = () => {
    // var data = {
    //     carNo : $(".editCarNo").innerText,
    //     discount : $(".editDiscount").innerText
    // };
    // console.log(data);

    // $.ajax({
    //     type:'get',
    //     url:'/admin/discount/delete/'+${carNo},
    //     dataType:'json',
    //     contentType : 'application/json; charset=utf-8',
    //     data : JSON.stringify(data)
    // }).done(function (result) {
    //     alert('할인 가격이 적용되었습니다.');
    //     console.log(result);
    // }).fail(function (error) {
    //     alert(JSON.stringify(error));
    // })
}