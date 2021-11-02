
// 할인 적용하기 -> 수정필요
function make_discount () {

    if (document.getElementById("reservation-detail-name").value == ""){
        alert('성함을 입력해주세요.')
        return
    }

    if (document.getElementById("reservation-detail-phone").value == ""){
        alert('전화번호를 입력해주세요.')
        return
    }


    var data = {
        name : $("#reservation-detail-name").val(),
        phoneNo : $("#reservation-detail-phone").val(),
        detail : $("#reservation-detail-details").val(),
        title : "간편상담신청",
        car_name : $("#reservation-detail-carname").val(),
        mileage : $("#reservation-detail-region").val(),
        option : $("#reservation-detail-resdate").val()
    };

    var checkbox = document.getElementById("agree")
    if(checkbox.checked) {
        $.ajax({
            type : 'POST',
            url : '/reservation/apply',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('예약이 완료되었습니다.');
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    } else{
        alert("개인정보 수집 및 이용에 동의해주세요.");
    }
}