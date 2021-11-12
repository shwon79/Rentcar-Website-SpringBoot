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

    if (document.getElementById("discount-description").value == ""){
        alert('할인 설명을 입력해주세요.')
        return
    }

    let carNo = $("#carNo").val().replace(/(\s*)/g,""); // 공백 제거

    var data = {
        carNo : carNo,
        discount : $("#discount").val(),
        description: $("#discount-description").val()
    };
    console.log(data);

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
}


// 할인 가격 수정하기

$('.money-update-btn').click(function(e) {
    let carNo = e.target.dataset.index;
    let originalDiscount = document.querySelectorAll('.editDiscount');
    let editDiscount = prompt("수정할 할인가(%)를 입력하세요.", "");

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
            type:'PUT',
            url:'/admin/discount/'+ carNo + '/' + editDiscount,
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

// 할인 설명 수정하기

$('.description-update-btn').click(function(e) {
    let carNo = e.target.dataset.index;
    let originalDescription = document.querySelectorAll('.discountDescription');
    let editDescription = prompt("수정할 할인 설명을 입력하세요.", "");

    // 할인 설명 입력 안해줬을 경우 -> 원래대로
    if (!editDescription) {
        for (let i = 0; i < originalDescription.length; i++) {
            if (originalDescription[i].dataset.index === carNo) {
                editDescription = originalDescription[i].innerText;
            }
        }
    } else {
        $.ajax({
            type:'PUT',
            url:'/admin/description/'+ carNo + '/' + editDescription,
            dataType:'json',
            contentType : 'application/json; charset=utf-8'
        }).done(function (result) {
            if (result.result == 1) {
                alert('할인 설명이 수정되었습니다.');
            } else if (result.result == 0) {
                alert('할인 설명 수정에 문제가 생겼습니다.');
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



// 모렌으로 데이터 전달, 예약하기
$('.reservation-confirm-btn').click(function(e) {
    console.log(e.target.dataset.index);

    // 예약 정보 받기
    let idList = document.getElementsByClassName('reservationId');
    let carNoList = document.getElementsByClassName('carNo');
    let reservationNameList = document.getElementsByClassName('reservationName');
    let reservationPhoneList = document.getElementsByClassName('reservationPhone');
    let reservationDateList = document.getElementsByClassName('reservationDate');
    let reservationTimeList = document.getElementsByClassName('reservationTime');
    let addressList = document.getElementsByClassName('reservationAddress');
    let addressDetailList = document.getElementsByClassName('reservationAddressDetail');

    let id;
    let carNo;
    let reservationName;
    let reservationPhone;
    let reservationDate;
    let reservationTime;
    let address;
    let addressDetail;

    for (i=0; i < idList.length; i++) {
        if (e.target.dataset.index == idList[i].dataset.index) {
            id = idList[i].innerText;
        }
    }
    for (i=0; i < carNoList.length; i++) {
        if (e.target.dataset.index == carNoList[i].dataset.index) {
            carNo = carNoList[i].innerText;
        }
    }
    for (i=0; i < reservationNameList.length; i++) {
        if (e.target.dataset.index == reservationNameList[i].dataset.index) {
            reservationName = reservationNameList[i].innerText;
        }
    }
    for (i=0; i < reservationPhoneList.length; i++) {
        if (e.target.dataset.index == reservationPhoneList[i].dataset.index) {
            reservationPhone = reservationPhoneList[i].innerText;
        }
    }
    for (i=0; i < reservationDateList.length; i++) {
        if (e.target.dataset.index == reservationDateList[i].dataset.index) {
            reservationDate = reservationDateList[i].innerText;
        }
    }
    for (i=0; i < reservationTimeList.length; i++) {
        if (e.target.dataset.index == reservationTimeList[i].dataset.index) {
            reservationTime = reservationTimeList[i].innerText;
        }
    }
    for (i=0; i < addressList.length; i++) {
        if (e.target.dataset.index == addressList[i].dataset.index) {
            address = addressList[i].innerText;
        }
    }
    for (i=0; i < addressDetailList.length; i++) {
        if (e.target.dataset.index == addressDetailList[i].dataset.index) {
            addressDetail = addressDetailList[i].innerText;
        }
    }

    var data = {
        id : id,
        carNo: carNo,
        reservationName: reservationName,
        reservationPhone: reservationPhone,
        reservationDate: reservationDate,
        reservationTime: reservationTime,
        address: address,
        addressDetail: addressDetail,
    }

    console.log(data);

    let reserveConfirm = confirm('예약을 완료하시겠습니까?');

    if (reserveConfirm) {
        $.ajax({
            type:'POST',
            url:'/moren/reservation/apply',
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('예약이 완료되었습니다.');
            } else {
                alert('예약에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/moren/reservation/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };

})

// 모렌 예약 신청 목록에서 취소
$('.reservation-delete-btn').click(function(e) {
    const reservationId = e.target.dataset.index;
    // const trList = document.querySelectorAll('tr[data-index]');
    // let tr;
    //
    // for (i=0; i < trList.length; i++) {
    //     if (reservationId == trList[i].dataset.index) {
    //         tr = trList[i];
    //     }
    // }
    // console.log(tr);

    let deleteConfirm = confirm('취소하시겠습니까?');

    if (deleteConfirm) {
        $.ajax({
            type:'GET',
            url:'/moren/reservation/cancel/'+ reservationId,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
        }).done(function (result) {
            if (result.result == 1) {
                alert('취소 되었습니다.');
            } else if (result.result == 0) {
                alert('취소에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/moren/reservation/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
})