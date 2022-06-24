function erase_after(target) {
    if (target === 'category1') {
        document.getElementById('select-category1').value = "";
        document.getElementById('select-category2').value = "";
        document.getElementById('select-car-name').value = "";
        setSelectBoxByText('select-category2', "차 분류를 선택해주세요");
        setSelectBoxByText('select-car-name', "차명을 선택해주세요");
        setSelectBoxByText('select-mileage', "주행거리 선택해주세요");
    } else if (target === 'category2') {
        document.getElementById('select-category2').value = "";
        document.getElementById('select-car-name').value = "";
        setSelectBoxByText('select-car-name', "차명을 선택해주세요");
        setSelectBoxByText('select-mileage', "주행거리 선택해주세요");
    } else if (target === 'carname') {
        document.getElementById('select-car-name').value = "";
        setSelectBoxByText('select-mileage', "주행거리 선택해주세요");
    }

    document.getElementById('select-mileage').value = "";
    document.getElementById("carPrice").innerText = "원";
    document.getElementById("carVat").innerText = "원";
    document.getElementById("carDeposit").innerText = "원";
    document.getElementById("carTotal").innerText = "원";
}


function check_all_box(){
    const total_agree =  document.getElementById("agree4");
    const agrees = document.getElementsByClassName("agree");

    if(total_agree.checked == true) {
        for (var i = 0; i < agrees.length; i++) {
            var item = agrees.item(i);
            item.checked = true;
        }
    } else {
        for (var i = 0; i < agrees.length; i++) {
            var item = agrees.item(i);
            item.checked = false;
        }
    }
}

// 간편상담 요청
function make_easy_reservation () {
    if (document.getElementById("reservation-detail-name").value == ""){
        alert('성함을 입력해주세요.');
        return;
    }

    if (document.getElementById("reservation-detail-phone").value == ""){
        alert('전화번호를 입력해주세요.');
        return;
    }


    let data = {
        name : $("#reservation-detail-name").val(),
        phoneNo : $("#reservation-detail-phone").val(),
        detail : $("#reservation-detail-details").val(),
        title : "간편상담신청",
        car_name : $("#reservation-detail-carname").val(),
        region : $("#reservation-detail-region").val(),
        resDate : $("#reservation-detail-resdate").val()
    };

    let checkbox = document.getElementById("agree");

    if(checkbox.checked) {
        $.ajax({
            type : 'POST',
            url : '/reservation/apply',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('예약이 완료되었습니다.');
            window.location.href = '/index';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    } else{
        alert("개인정보 수집 및 이용에 동의해주세요.");
    }
}


// 에약 요청
function make_reservation () {

    if (document.getElementById("reservation-detail-name").value == ""){
        alert('(아래)상담 신청하기에서 성함을 입력해주세요.')
        return
    }

    if (document.getElementById("reservation-detail-phone").value == ""){
        alert('전화번호를 입력해주세요.')
        return
    }

    let product;
    if (document.getElementById("long-rent-product1") != null) {

        if (document.getElementById("long-rent-product1").checked) {
            product = document.getElementById("long-rent-product1").getAttribute("value")
        } else if ((document.getElementById("long-rent-product2").checked)){
            product = document.getElementById("long-rent-product2").getAttribute("value")
        } else {
            product = document.getElementById("long-rent-product3").getAttribute("value")
        }

    } else if (document.getElementById("rent-product1") != null) {

        let month1 = document.getElementById("rent-product1")
        let yearly = document.getElementById("rent-product2")

        if (month1 && month1.checked) {
            product = document.getElementById("rent-product1").getAttribute("value")
        } else if(yearly && yearly.checked) {
            product = document.getElementById("rent-product2").getAttribute("value")
        } else {
            product = document.getElementById("rent-product3").getAttribute("value")
        }

        if (product == "rentMonth") {
            product = "월렌트"
        } else if (product == "rentYear") {
            product = "12개월 렌트";
        } else if (product == "rent2Year") {
            product = "24개월 렌트";
        }
    } else if (document.getElementById("rentProduct") != null){
        product = document.getElementById("rentProduct").getAttribute("value")
    } else {
        product = "";
    }


    let deposit;
    // 누구나 장기렌트
    if (document.getElementById("deposit-10") != null) {
        if (document.getElementById("deposit-10").checked){
            deposit = $("#deposit-10").val();
        } else if (document.getElementById("deposit-30").checked){
            deposit = $("#deposit-30").val();
        } else {
            deposit = $("#deposit-0").val();
        }
        // 월렌트
    } else {
        deposit = document.getElementById("carDeposit").innerText;
    }



    let price;
    if (document.getElementById("carTotal") != null) {
        price = document.getElementById("carTotal").innerText;
    }


    let age_limit;
    if (document.getElementById("age_limit").checked){
        age_limit = "만 21세 이상~만 26세 미만"
    } else {
        age_limit = "만 26세 이상"
    }

    let data = {
        name : $("#reservation-detail-name").val(),
        phoneNo : $("#reservation-detail-phone").val(),
        detail : $("#reservation-detail-details").val(),
        title : document.getElementById("rent-title").getAttribute("value"),
        product : product,
        category1 : $("#select-category1").val(),
        category2 : $("#select-category2").val(),
        car_name : $("#select-car-name").val(),
        mileage : $("#select-mileage").val(),
        deposit : deposit,
        age_limit : age_limit,
        option : $("#select-car-option").val(),
        price : price
    };

    // console.log(data);

    let checkbox = document.getElementById("agree")
    if(checkbox.checked) {
        $.ajax({
            type : 'POST',
            url : '/reservation/apply',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('상담 신청이 완료되었습니다.');
            window.location.href = '/rent/estimate';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    } else{
        alert("개인정보 수집 및 이용에 동의해주세요.");
    }
}

//차종 구하기
function get_category1(fr, detailedSelect) {
    fetch(`/rent/estimate/${fr}`)
        .then(response => response.json())
        .then(result => {
            for (let i = 0; i < result.length; i++) {
                detailedSelect.options[i+1] = new Option(result[i], result[i]);
            }
        }).catch(err => console.log(err))
};

//차 분류 구하기
function get_category2(fr1, fr2, detailedSelect) {
    fetch(`/rent/estimate/${fr1}/${fr2}`)
        .then(response => response.json())
        .then(result => {
            detailedSelect.length = 1;
            for (let i = 0; i < result.length; i++) {
                detailedSelect.options[i+1] = new Option(result[i], result[i]);
            }
        }).catch(err => console.log(err));
}

// 차명 구하기
function get_car_name(fr1, fr2, fr3, detailedSelect) {
    fetch(`/rent/estimate/${fr1}/name/${fr2}/${fr3}`)
        .then(response => response.json())
        .then(result => {
            detailedSelect.options.length = 1;
            for (let i = 0; i < result.length; i++) {
                detailedSelect.options[i+1] = new Option(result[i], result[i]);
            }
        }).catch(err=>console.log(err));
};

//주행거리 구하기
function get_mileage(fr1, detailedSelect) {
    let mileage_options;
    detailedSelect.length = 1;
    if (fr1 == "rentMonth") {
        mileage_options = [2000, 2500, 3000, 4000, "기타주행거리"];
    } else {
        mileage_options = [20000, 30000, 40000, "기타주행거리"];
    }
    for (let i = 0; i < mileage_options.length; i++) {
        detailedSelect.options[i+1] = new Option(mileage_options[i], mileage_options[i]);
    }
}

// 가격 파싱해서 천단위마다 , 로 끊기
const int_to_price = (price) => {
    let len = price.length;
    let result = "";

    for (let i=len ; i>0 ; i-=3) {
        if (result == ""){
            result = price.slice(i-3, i)
        } else {
            result = price.slice(i-3, i) + ',' + result
        }
    }
    result = price.slice(0, len%3) + result;
    return result;
}

// 요청한 값들에 따라 가격 구하기
function get_price(fr1, fr2, fr3, detailedSelect) {

    fetch(`/rent/estimate/${fr1}/price/${fr2}/${fr3}`)
        .then((response) => response.json())
        .then(result => {
            let price = result[0];
            let age_limit = result[2];

            if (price==='상담') {
                let vat = price
                let deposit = result[1];
                let total = vat;

                document.getElementById("carPrice").innerText = price;
                document.getElementById("carVat").innerText = vat;
                document.getElementById("carDeposit").innerText = deposit +"원";
                document.getElementById("carTotal").innerText =  total;

            } else {

                if(document.getElementById("age_limit").checked) {
                    price = parseInt(price) + parseInt(age_limit);
                } else {
                    price = parseInt(price)
                }

                let vat = price * 0.1;
                let deposit = parseInt(result[1]).toLocaleString();
                let total = price + vat;

                price = int_to_price(price.toString());
                vat = int_to_price(vat.toString());
                total = int_to_price(total.toString());

                document.getElementById("carPrice").innerText = price +"원";
                document.getElementById("carVat").innerText = vat +"원";
                document.getElementById("carDeposit").innerText = deposit +"원";
                document.getElementById("carTotal").innerText =  total +"원";
            }
        }).catch(err => {console.log(err)});
}

function setSelectBoxByText(eid, eTxt) {
    let target = document.getElementById(eid);

    for (let i = 0; i < target.options.length; i++) {
        if (target.options[i].innerText == eTxt) {
            target.options[i].selected = true;
            break;
        }
    };
}