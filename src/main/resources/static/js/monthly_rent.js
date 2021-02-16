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
    console.log(fr1)
    console.log(fr2)

    $.ajax({
        type: 'GET',
        url: '/rent/month/' + fr1 + '/' + fr2,
        contentType: "application/json; charset=euc-kr",
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
        url: '/rent/month/' + fr1 + '/' + fr2 + '/' + fr3,
        contentType: "application/json; charset=euc-kr",
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

function get_mileage(fr1, detailedSelect) {
    console.log(fr1)

    $.ajax({
        type: 'GET',
        url: '/rent/month/' + fr1 + '/mileage',
        contentType: "application/json; charset=euc-kr",
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