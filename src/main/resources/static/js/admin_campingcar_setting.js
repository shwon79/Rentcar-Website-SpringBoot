// setting에서 캠핑카 내용 수정 버튼
let settingSaveBtn = document.getElementById('settingSaveBtn');
settingSaveBtn.addEventListener('click', () => {
    let editedCamperPrice = document.getElementById('editedCamperPrice');
    let editedRentPolicy = document.getElementById('editedRentPolicy');
    let editedRentInsurance = document.getElementById('editedRentInsurance');
    let editedDriverLicense = document.getElementById('editedDriverLicense');
    let editedRentRule = document.getElementById('editedRentRule');
    let editedRefundPolicy = document.getElementById('editedRefundPolicy');
    let editedEuropeBasicOption = document.getElementById('editedEuropeBasicOption');
    let editedEuropeFacility = document.getElementById('editedEuropeFacility');
    let editedLimousineBasicOption = document.getElementById('editedLimousineBasicOption');
    let editedLimousineFacility = document.getElementById('editedLimousineFacility');
    let editedTravelBasicOption = document.getElementById('editedTravelBasicOption');
    let editedTravelFacility = document.getElementById('editedTravelFacility');
    

    let data = {
        camper_price: editedCamperPrice.value,
        rent_policy: editedRentPolicy.value,
        rent_insurance: editedRentInsurance.value,
        driver_license: editedDriverLicense.value,
        rent_rule: editedRentRule.value,
        refund_policy: editedRefundPolicy.value,
        europe_basic_option: editedEuropeBasicOption.value,
        limousine_basic_option: editedLimousineBasicOption.value,
        travel_basic_option: editedTravelBasicOption.value,
        europe_facility: editedEuropeFacility.value,
        limousine_facility: editedLimousineFacility.value,
        travel_facility: editedTravelFacility.value
    }
    // console.log(data);

    $.ajax({
        type:'POST',
        url:'/admin/setting',
        dataType:'json',
        contentType : 'application/json; charset=utf-8',
        data : JSON.stringify(data)
    }).done(function (result) {
        if (result.result == 1) {
            alert('완료되었습니다.');
            window.location.href = '/admin/setting/menu';
        } else {
            alert('문제가 생겼습니다.');
        };
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
})