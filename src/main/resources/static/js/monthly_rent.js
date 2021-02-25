function make_reservation () {
    var product;
    if (document.getElementById("rentProduct") != null) {
        product = document.getElementById("rentProduct").getAttribute("value")
    } else if (document.getElementById("rent-product1") != null) {
        if (document.getElementById("rent-product1").checked) {
            product = $("#rent-product1").val();
        } else if (document.getElementById("rent-product2").checked) {
            product = $("#rent-product2").val();
        }

        if (product == "rentMonth") {
            product = "월렌트"
        } else if (product == "rentYear") {
            product = "12개월 렌트";
        }
    } else {
        product = "";
    }

    var deposit;
    if (document.getElementById("rentMonth") != null) {
        if (document.getElementById("rentMonth").checked){
            deposit = $("#rentMonth").val();
        } else {
            deposit = $("#rentYear").val();
        }
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
        option : $("#select-car-option").val(),
        price : $("#carTotal").val()
    };
    console.log(data)

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

function get_category1(fr, detailedSelect) {
    $.ajax({
        type: 'GET',
        url: '/rent/month/' + fr,
        contentType: "application/json; charset=UTF-8",
        dataType: 'json',
        success: function set_c1(result) {
            console.log(result)
            for (i = 0; i < result.length; i++) {
                detailedSelect.options[i+1] = new Option(result[i], result[i]);
            }
        }
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}

function get_category2(fr1, fr2, detailedSelect) {

    $.ajax({
        type: 'GET',
        url: '/rent/month/' + fr1 + '/' + fr2,
        contentType: "application/json; charset=UTF-8",
        dataType: 'json',
        success: function set_c2(result) {
            console.log(result)
            for (i = 0; i < result.length; i++) {
                detailedSelect.options[i+1] = new Option(result[i], result[i]);
            }
        }
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}

function get_car_name(fr1, fr2, fr3, detailedSelect) {
    $.ajax({
        type: 'GET',
        url: '/rent/month/' + fr1 + "/name/" + fr2 + '/' + fr3,
        contentType: "application/json; charset=UTF-8",
        dataType: 'json',
        success: function set_n(result) {
            console.log(result)
            for (i = 0; i < result.length; i++) {
                detailedSelect.options[i+1] = new Option(result[i], result[i]);
            }
        }
    }).fail(function (error) {
        console.log('/rent/month/' + fr1 + '/name/' + fr2 + '/' + fr3);
        alert(JSON.stringify(error));
    })
}

function get_mileage(fr1, detailedSelect) {
    if (fr1 == "rentMonth") {
        mileage_options = [2000, 2500, 3000, 4000];
    } else {
        mileage_options = [20000, 30000, 40000];
    }
    for (i = 0; i < mileage_options.length; i++) {
        detailedSelect.options[i+1] = new Option(mileage_options[i], mileage_options[i]);
    }
}

function int_to_price(price) {
    var len = price.length;
    var result = "";

    for (var i=len ; i>0 ; i-=3) {
        if (result == ""){
            result = price.slice(i-3, i)
        } else {
            result = price.slice(i-3, i) + ',' + result
        }
        console.log(result)
    }
    result = price.slice(0, len%3) + result;
    return result;
}

function get_price(fr1, fr2, fr3, detailedSelect) {
    $.ajax({
        type: 'GET',
        url: '/rent/month/' + fr1 + '/price/' + fr2 + '/' + fr3,
        contentType: "application/json; charset=euc-kr",
        dataType: 'json',
        success: function set_p(result) {
            var price = result[0];
            var vat = price.replace(/,/gi, "");
            vat = parseInt(vat) * 0.1;
            var deposit = result[1];
            var total = parseInt(price.replace(/,/gi, "")) + vat;

            vat = int_to_price(vat.toString());
            total = int_to_price(total.toString());

            document.getElementById("carPrice").innerText = price +"원";
            document.getElementById("carVat").innerText = vat +"원";
            document.getElementById("carDeposit").innerText = deposit +"원";
            document.getElementById("carTotal").innerText =  total +"원";

        }
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}