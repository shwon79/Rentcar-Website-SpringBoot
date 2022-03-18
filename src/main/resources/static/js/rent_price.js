// 보증금 숨기기 버튼
function hideDeposit() {
    const depositColumns = [...document.getElementsByClassName('depositColumn')];
    depositColumns.forEach(column => {
        column.classList.toggle('displayNone');
    });
}

function make_reservation_price () {
    if (document.getElementById("reservation-detail-name").value == ""){
        alert('성함을 입력해주세요.')
        return
    };

    if (document.getElementById("reservation-detail-phone").value == ""){
        alert('전화번호를 입력해주세요.')
        return
    };

    var data = {
        name : $("#reservation-detail-name").val(),
        phoneNo : $("#reservation-detail-phone").val(),
        detail : $("#reservation-detail-details").val(),
        title : "한눈에 상담신청",
        car_name : $("#reservation-detail-carname").val(),
        region : $("#reservation-detail-region").val(),
        resDate : $("#reservation-detail-resdate").val()
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
            alert('상담신청이 완료되었습니다.');
            window.location.href = '/price/month/경형';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    } else{
        alert("개인정보 수집 및 이용에 동의해주세요.");
    }
}