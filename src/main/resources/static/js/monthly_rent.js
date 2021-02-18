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

function get_price(fr1, fr2, fr3, detailedSelect) {
    $.ajax({
        type: 'GET',
        url: '/rent/month/' + fr1 + '/price/' + fr2 + '/' + fr3,
        contentType: "application/json; charset=euc-kr",
        dataType: 'json',
        success: function set_p(result) {
            var price = result[0];
            var vat = price * 0.1;
            var deposit = result[1];
            var total = parseInt(price) + vat;
            console.log(price, vat, deposit, total);
            console.log(document.getElementById("carPrice"))
            console.log(document.getElementById("carPrice").innerText)


            document.getElementById("carPrice").innerText = price +"원";
            document.getElementById("carVat").innerText = vat +"원";
            document.getElementById("carDeposit").innerText = deposit +"원";
            document.getElementById("carTotal").innerText = total +"원";

            console.log(document.getElementById("carPrice"))
            console.log(document.getElementById("carPrice").innerText)

        }
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}