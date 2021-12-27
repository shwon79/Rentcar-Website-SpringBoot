function erase_after_catergory1(){

    document.getElementById('select-category1').value = "";
    document.getElementById('select-category2').value = "";
    document.getElementById('select-car-name').value = "";
    document.getElementById('select-mileage').value = "";
    document.getElementById("carPrice").innerText = "원";
    document.getElementById("carVat").innerText = "원";
    document.getElementById("carDeposit").innerText = "원";
    document.getElementById("carTotal").innerText = "원";
    setSelectBoxByText('select-category2', "차 분류를 선택해주세요");
    setSelectBoxByText('select-car-name', "차명을 선택해주세요");
    setSelectBoxByText('select-mileage', "주행거리 선택해주세요");

}

function erase_after_catergory2(){

    document.getElementById('select-category2').value = "";
    document.getElementById('select-car-name').value = "";
    document.getElementById('select-mileage').value = "";
    document.getElementById("carPrice").innerText = "원";
    document.getElementById("carVat").innerText = "원";
    document.getElementById("carDeposit").innerText = "원";
    document.getElementById("carTotal").innerText = "원";
    setSelectBoxByText('select-car-name', "차명을 선택해주세요");
    setSelectBoxByText('select-mileage', "주행거리 선택해주세요");
}

function erase_after_carname(){

    document.getElementById('select-car-name').value = "";
    document.getElementById('select-mileage').value = "";
    document.getElementById("carPrice").innerText = "원";
    document.getElementById("carVat").innerText = "원";
    document.getElementById("carDeposit").innerText = "원";
    document.getElementById("carTotal").innerText = "원";
    setSelectBoxByText('select-mileage', "주행거리 선택해주세요");
}

function erase_after_milege(){

    document.getElementById('select-mileage').value = "";
    document.getElementById("carPrice").innerText = "원";
    document.getElementById("carVat").innerText = "원";
    document.getElementById("carDeposit").innerText = "원";
    document.getElementById("carTotal").innerText = "원";
}

// 간편상담 요청
function make_easy_reservation () {

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

    var product;
    if (document.getElementById("long-rent-product1") != null) {

        if (document.getElementById("long-rent-product1").checked) {
            product = document.getElementById("long-rent-product1").getAttribute("value")
        } else if ((document.getElementById("long-rent-product2").checked)){
            product = document.getElementById("long-rent-product2").getAttribute("value")
        } else {
            product = document.getElementById("long-rent-product3").getAttribute("value")
        }

    } else if (document.getElementById("rent-product1") != null) {

        var month1 = document.getElementById("rent-product1")
        var yearly = document.getElementById("rent-product2")

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


    var deposit;
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



    var price;
    if (document.getElementById("carTotal") != null) {
        price = document.getElementById("carTotal").innerText;
    }


    var age_limit;
    if (document.getElementById("age_limit").checked){
        age_limit = "21세 이상 O"
    } else {
        age_limit = "21세 이상 X"
    }

    var data = {
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
            window.location.href = '/europe';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    } else{
        alert("개인정보 수집 및 이용에 동의해주세요.");
    }
}

//차종 구하기
const get_category1 = (fr, detailedSelect) => {
  fetch(`/rent/estimate/${fr}`)
      .then(response => response.json())
      .then(result => {
          for (let i = 0; i < result.length; i++) {
              detailedSelect.options[i+1] = new Option(result[i], result[i]);
          }
      }).catch(err => console.log(err))
}

/*
function get_category1(fr, detailedSelect) {
    $.ajax({
        type: 'GET',
        url: '/rent/month/' + fr,
        contentType: "application/json; charset=UTF-8",
        dataType: 'json',
        success: function set_c1(result) {
            for (i = 0; i < result.length; i++) {
                detailedSelect.options[i+1] = new Option(result[i], result[i]);
            }
        }
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}
 */

//차 분류 구하기
const get_category2 = (fr1, fr2, detailedSelect) => {

    fetch(`/rent/estimate/${fr1}/${fr2}`)
        .then(response => response.json())
        .then(result => {
            detailedSelect.length = 1;
            console.log(result)
            for (let i = 0; i < result.length; i++) {
                detailedSelect.options[i+1] = new Option(result[i], result[i]);
            }
        }).catch(err => console.log(err))

    /*
    $.ajax({
        type: 'GET',
        url: '/rent/month/' + fr1 + '/' + fr2,
        contentType: "application/json; charset=UTF-8",
        dataType: 'json',
        success: function set_c2(result) {

            detailedSelect.options.length = 1;
            for (i = 0; i < result.length; i++) {
                detailedSelect.options[i+1] = new Option(result[i], result[i]);
            }
        }
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
     */
}
// 차명 구하기
const get_car_name = (fr1, fr2, fr3, detailedSelect) => {

    fetch(`/rent/estimate/${fr1}/name/${fr2}/${fr3}`)
        .then(response => response.json())
        .then(result => {
            detailedSelect.options.length = 1;
            console.log(result.length);
            for (let i = 0; i < result.length; i++) {
                detailedSelect.options[i+1] = new Option(result[i], result[i]);
                console.log(detailedSelect.options[i+1]);
            }
        }).catch(err=>console.log(err))

    /*
    $.ajax({
        type: 'GET',
        url: '/rent/month/' + fr1 + "/name/" + fr2 + '/' + fr3,
        contentType: "application/json; charset=UTF-8",
        dataType: 'json',
        success: function set_n(result) {
            detailedSelect.options.length = 1;
            for (i = 0; i < result.length; i++) {
                detailedSelect.options[i+1] = new Option(result[i], result[i]);
            }
        }
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
     */
}
//주행거리 구하기
const get_mileage = (fr1, detailedSelect) => {
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
const get_price = (fr1, fr2, fr3, detailedSelect) => {

    fetch(`/rent/estimate/${fr1}/price/${fr2}/${fr3}`)
        .then((response) => response.json())
        .then(result => {
            let price = result[0];

            if (price==='상담') {
                let vat = price
                let deposit = result[1];
                let total = vat;

                document.getElementById("carPrice").innerText = price;
                document.getElementById("carVat").innerText = vat;
                document.getElementById("carDeposit").innerText = deposit +"원";
                document.getElementById("carTotal").innerText =  total;

            } else {
                let vat = price.replace(/,/gi, "");
                vat = parseInt(vat) * 0.1;
                let deposit = result[1];
                let total = parseInt(price.replace(/,/gi, "")) + vat;

                price = int_to_price(price.toString());
                vat = int_to_price(vat.toString());
                total = int_to_price(total.toString());

                document.getElementById("carPrice").innerText = price +"원";
                document.getElementById("carVat").innerText = vat +"원";
                document.getElementById("carDeposit").innerText = deposit +"원";
                document.getElementById("carTotal").innerText =  total +"원";
            }
        }).catch(err => {console.log(err)})

    /*
    $.ajax({
        type: 'GET',
        url: '/rent/month/' + fr1 + '/price/' + fr2 + '/' + fr3,
        contentType: "application/json; charset=euc-kr",
        dataType: 'json',
        success: function set_p(result) {
            var price = result[0];
            var age_limit = result[2];

            if(price == '상담') {
                var vat = price
                var deposit = result[1];
                var total = vat;

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

                // var price_no_comma = price.replace(/,/gi, "");
                vat = price * 0.1;
                var total = price + vat;
                var deposit = result[1];

                price = int_to_price(price.toString());
                vat = int_to_price(vat.toString());
                total = int_to_price(total.toString());

                document.getElementById("carPrice").innerText = price +"원";
                document.getElementById("carVat").innerText = vat +"원";
                document.getElementById("carDeposit").innerText = deposit +"원";
                document.getElementById("carTotal").innerText =  total +"원";
            }


        }
    }).fail(function (error) {
        // alert(JSON.stringify(error));
    })
    */
}

function setSelectBoxByText(eid, etxt) {
    var eid = document.getElementById(eid);

    for (var i = 0; i < (eid.options.length); ++i) {
        if (eid.options[i].innerText == etxt) {
            eid.options[i].selected = true;
            break
        }
    }
}