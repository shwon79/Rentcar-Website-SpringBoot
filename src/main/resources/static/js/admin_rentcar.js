// 종류 변경 시, 종류에 따른 분류 옵션 보여주기
function displayCategory2(event, makeDefaultOption) {
    const category1 = event.value;
    const category2 = document.getElementsByClassName('category2');
    const domesticCarOptions = ['경형', '준중형', '중형', '중대형', '대형'];
    const domesticVanOptions = ['소중형SUV', '중형SUV', '중대형SUV', '대형SUV', '승합'];
    const importedCarOptions = ['수입차'];
    let selectedOptions, selectedCategory2;

    for (i = 0; i < category2.length; i++) {
        if (category2[i].dataset.title == event.dataset.title) {
            selectedCategory2 = category2[i];
        }
    }

    if (category1 === '국산승용') {
        selectedOptions = domesticCarOptions;
    } else if (category1 === '국산승합•RV') {
        selectedOptions = domesticVanOptions;
    } else if (category1 === '수입차') {
        selectedOptions = importedCarOptions;
    } else if (category1 === '') {
        selectedOptions = [];
    }

    selectedCategory2.options.length = 0;

    if (makeDefaultOption) {
        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.innerText = '선택하세요';

        selectedCategory2.appendChild(defaultOption);
    }

    for (i in selectedOptions) {
        let option = document.createElement('option');
        option.value = selectedOptions[i];
        option.innerText = selectedOptions[i];

        selectedCategory2.appendChild(option);
    };
};

// 렌트카 가격 수정 메뉴 페이지 일괄 수정 버튼
function editBundleData(period, type) {
    let editedData;

    switch (type) {
        case '보증금':
            editedData = parseFloat(document.getElementById('bundleDeposit').value.replace(/,/g, ''));
            break;
        case '2500km':
            editedData = parseFloat(document.getElementById('bundle2500km').value).toFixed(15);
            break;
        case '3000km':
            editedData = parseFloat(document.getElementById('bundle3000km').value).toFixed(15);
            break;
        case '4000km':
            editedData = parseFloat(document.getElementById('bundle4000km').value).toFixed(15);
            break;
        case '21세':
            editedData = parseFloat(document.getElementById('bundleAgeLimit').value);
            break;
        case '20000km':
            editedData = parseFloat(document.getElementById('bundle20000km').value).toFixed(15);
            break;
        case '30000km':
            editedData = parseFloat(document.getElementById('bundle30000km').value).toFixed(15);
            break;
        case '40000km':
            editedData = parseFloat(document.getElementById('bundle40000km').value).toFixed(15);
            break;
    }

    if (confirm(`모든 차량의 ${type}을 일괄 수정하시겠습니까?`)) {
        $.ajax({
            type:'PUT',
            url:'/admin/rentcar/price/' + period + '/' + type + '/' + editedData,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            beforeSend : function(request){
                $("#my-spinner").show();
            },
            success: function (result) {
                $("#my-spinner").hide();
                if (result.result == 1) {
                    alert('처리되었습니다.');
                } else if (result.result == 0) {
                    alert('처리에 문제가 생겼습니다.');
                };
                location.reload();
            },
            error: function (error) {
                $("#my-spinner").hide();
                alert(JSON.stringify(error));
            }
        });
    }
}

console.log('hi');

// 렌트카 가격 수정 메뉴 페이지 가격 수정 버튼
function editRentPriceMenu(id, period) {
    let category1 = [...document.getElementsByClassName('category1')].find(item => item.dataset.title == id);
    let category2 = [...document.getElementsByClassName('category2')].find(item => item.dataset.title == id);
    let name = [...document.getElementsByClassName('name')].find(item => item.dataset.title == id);
    let deposit = [...document.getElementsByClassName('deposit')].find(item => item.dataset.title == id);
    let cost_for_others = [...document.getElementsByClassName('cost_for_others')].find(item => item.dataset.title == id);
    let age_limit = [...document.getElementsByClassName('age_limit')].find(item => item.dataset.title == id);
    let cost_per_km = [...document.getElementsByClassName('cost_per_km')].find(item => item.dataset.title == id);
    let nameMoren = [...document.getElementsByClassName('nameMoren')].find(item => item.dataset.title == id);
    let start = [...document.getElementsByClassName('start')].find(item => item.dataset.title == id);
    let end = [...document.getElementsByClassName('end')].find(item => item.dataset.title == id);
    let credit = [...document.getElementsByClassName('credit')].find(item => item.dataset.title == id);
    let img_url = [...document.getElementsByClassName('img_url')].find(item => item.dataset.title == id);
    let img_input = [...document.getElementsByClassName('img_input')].find(item => item.dataset.title == id);

    // 새로운 이미지 파일을 선택하지 않았을 때
    if (img_input === undefined || img_input.files[0] === undefined) {
        let data = {
            id: id,
            category1: category1.value || category1.innerText,
            category2: category2.value || category2.innerText,
            name: name.value || name.innerText,
            deposit: deposit.value.replace(/,/g, ''),
            cost_for_others: cost_for_others.value || cost_for_others.innerText,
            age_limit: age_limit.value || age_limit.innerText,
            cost_per_km: cost_per_km.value,
            nameMoren: nameMoren.value || nameMoren.innerText,
            start: parseInt(start.value) || parseInt(start.innerText),
            end: parseInt(end.value) || parseInt(end.innerText),
            credit: credit.value,
            img_url: img_url.innerText
        };

        if (period === 'monthly') {
            let cost_for_2k = [...document.getElementsByClassName('cost_for_2k')].find(item => item.dataset.title == id);
            let cost_for_2_5k = [...document.getElementsByClassName('cost_for_2_5k')].find(item => item.dataset.title == id);
            let cost_for_3k = [...document.getElementsByClassName('cost_for_3k')].find(item => item.dataset.title == id);
            let cost_for_4k = [...document.getElementsByClassName('cost_for_4k')].find(item => item.dataset.title == id);

            let cost_for_2_5k_price = (Math.round((parseFloat(cost_for_2k.value.replace(/,/g, ''))*parseFloat(cost_for_2_5k.value).toFixed(15))/1000)*1000).toFixed(15);
            let cost_for_3k_price = (Math.round((parseFloat(cost_for_2k.value.replace(/,/g, ''))*parseFloat(cost_for_3k.value).toFixed(15))/1000)*1000).toFixed(15);
            let cost_for_4k_price = (Math.round((parseFloat(cost_for_2k.value.replace(/,/g, ''))*parseFloat(cost_for_4k.value).toFixed(15))/1000)*1000).toFixed(15);

            data['cost_for_2k'] = parseFloat(cost_for_2k.value.replace(/,/g, ''));
            data['cost_for_2_5k'] = parseFloat(cost_for_2_5k.value).toFixed(15);
            data['cost_for_3k'] = parseFloat(cost_for_3k.value).toFixed(15);
            data['cost_for_4k'] = parseFloat(cost_for_4k.value).toFixed(15);
            data['cost_for_2_5k_price'] = cost_for_2_5k_price;
            data['cost_for_3k_price'] = cost_for_3k_price;
            data['cost_for_4k_price'] = cost_for_4k_price;

            // console.log(data);
            postStringData(period, id, data);
        } else if (period === 'yearly') {
            let cost_for_20k = [...document.getElementsByClassName('cost_for_20k')].find(item => item.dataset.title == id);
            let cost_for_30k = [...document.getElementsByClassName('cost_for_30k')].find(item => item.dataset.title == id);
            let cost_for_40k = [...document.getElementsByClassName('cost_for_40k')].find(item => item.dataset.title == id);

            data['cost_for_20k'] = parseFloat(cost_for_20k.value).toFixed(15);
            data['cost_for_30k'] = parseFloat(cost_for_30k.value).toFixed(15);
            data['cost_for_40k'] = parseFloat(cost_for_40k.value).toFixed(15);

            // console.log(data);
            postStringData(period, id, data);
        } else if (period === 'twoYearly') {
            let cost_for_20Tk = [...document.getElementsByClassName('cost_for_20Tk')].find(item => item.dataset.title == id);
            let cost_for_30Tk = [...document.getElementsByClassName('cost_for_30Tk')].find(item => item.dataset.title == id);
            let cost_for_40Tk = [...document.getElementsByClassName('cost_for_40Tk')].find(item => item.dataset.title == id);

            data['cost_for_20Tk'] = parseFloat(cost_for_20Tk.value).toFixed(15);
            data['cost_for_30Tk'] = parseFloat(cost_for_30Tk.value).toFixed(15);
            data['cost_for_40Tk'] = parseFloat(cost_for_40Tk.value).toFixed(15);

            // console.log(data);
            postStringData(period, id, data);
        }
    } else {
        // 새로운 이미지를 추가하려고 선택했을 때
        let formData = new FormData();

        formData.append('category1', category1.value);
        formData.append('category2', category2.value);
        formData.append('name', name.value);
        formData.append('deposit', deposit.value.replace(/,/g, ''));
        formData.append('cost_for_others', cost_for_others.value);
        formData.append('age_limit', age_limit.value.replace(/,/g, ''));
        formData.append('cost_per_km', cost_per_km.value);
        formData.append('nameMoren', nameMoren.value);
        formData.append('start', parseInt(start.value));
        formData.append('end', parseInt(end.value));
        formData.append('credit', credit.value);
        formData.append('img_url', '');
        formData.append('file', img_input.files[0]);

        if (period === 'monthly') {
            let cost_for_2k = [...document.getElementsByClassName('cost_for_2k')].find(item => item.dataset.title == id);
            let cost_for_2_5k = [...document.getElementsByClassName('cost_for_2_5k')].find(item => item.dataset.title == id);
            let cost_for_3k = [...document.getElementsByClassName('cost_for_3k')].find(item => item.dataset.title == id);
            let cost_for_4k = [...document.getElementsByClassName('cost_for_4k')].find(item => item.dataset.title == id);

            let cost_for_2_5k_price = (Math.round((parseFloat(cost_for_2k.value.replace(/,/g, ''))*parseFloat(cost_for_2_5k.value).toFixed(15))/1000)*1000).toFixed(15);
            let cost_for_3k_price = (Math.round((parseFloat(cost_for_2k.value.replace(/,/g, ''))*parseFloat(cost_for_3k.value).toFixed(15))/1000)*1000).toFixed(15);
            let cost_for_4k_price = (Math.round((parseFloat(cost_for_2k.value.replace(/,/g, ''))*parseFloat(cost_for_4k.value).toFixed(15))/1000)*1000).toFixed(15);

            formData.append('cost_for_2k', parseFloat(cost_for_2k.value.replace(/,/g, '')).toFixed(15));
            formData.append('cost_for_2_5k', parseFloat(cost_for_2_5k.value).toFixed(15));
            formData.append('cost_for_3k', parseFloat(cost_for_3k.value).toFixed(15));
            formData.append('cost_for_4k', parseFloat(cost_for_4k.value).toFixed(15));
            formData.append('cost_for_2_5k_price', cost_for_2_5k_price.toFixed(15));
            formData.append('cost_for_3k_price', cost_for_3k_price.toFixed(15));
            formData.append('cost_for_4k_price', cost_for_4k_price.toFixed(15));

            postFormData(id, formData, period);
        }
    }
}

// 이미지 없이 데이터 보낼 때
function postStringData(period, id, data) {
    if (confirm('수정하시겠습니까?')) {
        $.ajax({
            type:'PUT',
            url:'/admin/rentcar/price/' + period + '/' + id,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('처리되었습니다.');
            } else {
                alert('처리에 문제가 생겼습니다.');
            };
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
}

// 새로운 이미지 추가할 때
function postFormData(id, formData, period) {
    if (confirm('수정하시겠습니까?')) {
        $.ajax({
            enctype: 'multipart/form-data',
            cache: false,
            type: 'PUT',
            url: '/admin/rentcar/price/' + period + '/image/' + id,
            processData:false,
            contentType: false,
            data: formData
        }).done(function () {
            alert('처리되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
};

// 월렌트에서 차량 삭제
function deleteRentCar(id){
    if (confirm('차량을 삭제하시겠습니까?')) {
        $.ajax({
            type:'DELETE',
            url:'/admin/rentcar/price/'+ id,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
        }).done(function (result) {
            if (result.result == 1) {
                alert('삭제 되었습니다.');
            } else if (result.result == 0) {
                alert('삭제에 문제가 생겼습니다.');
            };
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
}

// 렌트카 등록 24개월 등록 버튼 누를 때
function openForm(event, type) {
    const target = document.getElementById(`${type}Form`);

    target.classList.toggle('openForm');

    if (target.classList.contains('openForm')) {
        event.innerText = '등록 안함';
    } else {
        event.innerText = '등록';
    }
}

// 렌트카 등록
function registerRentCar() {
    const category1 = document.getElementById('category1').value;
    const category2 = document.getElementById('category2').value;
    const name = document.getElementById('name').value;
    const deposit = document.getElementById('deposit').value;
    const cost_for_2k = document.getElementById('cost_for_2k').value;
    const cost_for_2_5k = document.getElementById('cost_for_2_5k').value;
    const cost_for_3k = document.getElementById('cost_for_3k').value;
    const cost_for_4k = document.getElementById('cost_for_4k').value;
    const cost_for_others = document.getElementById('cost_for_others').value;
    const age_limit = document.getElementById('age_limit').value;
    const cost_per_km = document.getElementById('cost_per_km').value;
    const nameMoren = document.getElementById('nameMoren').value;
    const start = parseInt(document.getElementById('start').value);
    const end = parseInt(document.getElementById('end').value);
    const credit = document.getElementById('credit').value;
    const img_input = document.getElementById('img_input').files[0];

    const requiredFields = [category1, category2, name, deposit, cost_for_2k, cost_for_2_5k, cost_for_3k, cost_for_4k, cost_for_others, age_limit, cost_per_km, nameMoren, start, end, credit, img_input];

    const depositYearly = document.getElementById('depositYearly').value;
    const cost_for_20k = document.getElementById('cost_for_20k').value;
    const cost_for_30k = document.getElementById('cost_for_30k').value;
    const cost_for_40k = document.getElementById('cost_for_40k').value;
    const cost_per_kmYearly = document.getElementById('cost_per_kmYearly').value;
    const creditYearly = document.getElementById('creditYearly').value;

    let depositTwoYearly = document.getElementById('depositTwoYearly').value;
    let cost_for_20Tk = document.getElementById('cost_for_20Tk').value;
    let cost_for_30Tk = document.getElementById('cost_for_30Tk').value;
    let cost_for_40Tk = document.getElementById('cost_for_40Tk').value;
    let cost_per_kmTwoYearly = document.getElementById('cost_per_kmTwoYearly').value;
    let creditTwoYearly = document.getElementById('creditTwoYearly').value;

    let isTwoYearExist;

    const yearlyForm = document.getElementById('yearlyForm');
    const twoYearlyForm = document.getElementById('twoYearlyForm');

    // 24개월 값 없으면
    if (twoYearlyForm.classList.contains('openForm')) {
        isTwoYearExist = 1;
        cost_for_20Tk = parseFloat(cost_for_20Tk).toFixed(15);
        cost_for_30Tk = parseFloat(cost_for_30Tk).toFixed(15);
        cost_for_40Tk = parseFloat(cost_for_40Tk).toFixed(15);
    } else {
        isTwoYearExist = 0;
        depositTwoYearly = '';
        cost_per_kmTwoYearly = '';
        creditTwoYearly = '';
        cost_for_20Tk = 0;
        cost_for_30Tk = 0;
        cost_for_40Tk = 0;
    }

    let formData = new FormData();

    // 공통
    formData.append('category1', category1);
    formData.append('category2', category2);
    formData.append('name', name);
    formData.append('cost_for_others', cost_for_others);
    formData.append('age_limit', age_limit);
    formData.append('nameMoren', nameMoren);
    formData.append('start', start);
    formData.append('end', end);
    formData.append('file', img_input);
    formData.append('isTwoYearExist', isTwoYearExist);

    // 다른 것
    formData.append('deposit_monthly', deposit);
    formData.append('deposit_yearly', depositYearly);
    formData.append('deposit_twoYearly', depositTwoYearly);
    formData.append('cost_per_km_monthly', cost_per_km);
    formData.append('cost_per_km_yearly', cost_per_kmYearly);
    formData.append('cost_per_km_twoYearly', cost_per_kmTwoYearly);
    formData.append('credit_monthly', credit);
    formData.append('credit_yearly', creditYearly);
    formData.append('credit_twoYearly', creditTwoYearly);

    // monthly
    formData.append('cost_for_2k', parseFloat(cost_for_2k).toFixed(4));
    formData.append('cost_for_2_5k', parseFloat(cost_for_2_5k).toFixed(4));
    formData.append('cost_for_3k', parseFloat(cost_for_3k).toFixed(4));
    formData.append('cost_for_4k', parseFloat(cost_for_4k).toFixed(4));

    // yearly
    formData.append('cost_for_20k', parseFloat(cost_for_20k).toFixed(4));
    formData.append('cost_for_30k', parseFloat(cost_for_30k).toFixed(4));
    formData.append('cost_for_40k', parseFloat(cost_for_40k).toFixed(4));

    // twoYearly
    formData.append('cost_for_20Tk', parseFloat(cost_for_20Tk).toFixed(4));
    formData.append('cost_for_30Tk', parseFloat(cost_for_30Tk).toFixed(4));
    formData.append('cost_for_40Tk', parseFloat(cost_for_40Tk).toFixed(4));

    // for (let key of formData.keys()) {
    //     console.log(key);
    // }
    // for (let value of formData.values()) {
    //     console.log(value);
    // }

    if (img_input === undefined) {
        alert('이미지를 첨부해주세요.');
    } else if (requiredFields.includes('') || requiredFields.includes(undefined)) {
        alert('캠핑카 등록에 필요한 필수 정보들을 작성해주세요.')
    } else if (!requiredFields.includes('') && !requiredFields.includes(undefined)) {
        postData(formData);
    }

    function postData(formData) {
        $.ajax({
            enctype: 'multipart/form-data',
            cache: false,
            type: 'POST',
            url: '/admin/rentcar/price/',
            processData:false,
            contentType: false,
            data: formData
        }).done(function () {
            alert('업로드가 완료되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };
}

// 렌트카 등록 시 배수 계산
function calculatePrice(type) {
    const cost_for_2k = parseFloat(document.getElementById('cost_for_2k').value);
    const cost_for_3k_float = parseFloat(document.getElementById('cost_for_3k').value).toFixed(15);
    const cost_for_4k_float = parseFloat(document.getElementById('cost_for_4k').value).toFixed(15);
    const cost_for_3k = Math.round(cost_for_2k * cost_for_3k_float / 1000) *1000;
    const cost_for_4k = Math.round(cost_for_2k * cost_for_4k_float / 1000) * 1000;

    const relate_cost_for_2k = ['cost_for_2_5k', 'cost_for_3k', 'cost_for_4k', 'cost_for_20k', 'cost_for_20Tk'];
    const relate_cost_for_3k = ['cost_for_30k', 'cost_for_30Tk'];
    const relate_cost_for_4k = ['cost_for_40k', 'cost_for_40Tk'];

    let standard;

    if (relate_cost_for_2k.includes(type)) {
        standard = cost_for_2k;
    } else if (relate_cost_for_3k.includes(type)) {
        standard = cost_for_3k;
    } else if (relate_cost_for_4k.includes(type)) {
        standard = cost_for_4k;
    }

    if (type != 'cost_for_2k') {
        calculate(type, standard);
    } else {
        calculate('cost_for_2_5k', cost_for_2k);
        calculate('cost_for_3k', cost_for_2k);
        calculate('cost_for_4k', cost_for_2k);
        calculate('cost_for_20k', cost_for_2k);
        calculate('cost_for_20Tk', cost_for_2k);
        calculate('cost_for_30k', cost_for_3k);
        calculate('cost_for_30Tk', cost_for_3k);
        calculate('cost_for_40k', cost_for_4k);
        calculate('cost_for_40Tk', cost_for_4k);
    };

    if (type === 'cost_for_3k') {
        calculate('cost_for_30k', cost_for_3k);
        calculate('cost_for_30Tk', cost_for_3k);
    };

    if (type === 'cost_for_4k') {
        calculate('cost_for_40k', cost_for_4k);
        calculate('cost_for_40Tk', cost_for_4k);
    };

    function calculate(type, standard) {
        let value = parseFloat(document.getElementById(type).value).toFixed(15);
        let target = document.getElementById(`display_${type}`);
        let result;
        result = Math.round(standard * value / 1000 ) * 1000 || '';
        target.innerText = result;
    }
}

// 렌트카 가격수정페이지 자동계산
function calculatePriceOnMenu(event, period, type) {
    if (period === 'monthly') {
        const cost_for_2k = parseInt([...document.getElementsByClassName('cost_for_2k')].find(item => item.dataset.title == event.dataset.title).value.replace(/,/g, ''));

        if (type != 'cost_for_2k') {
            calculate(type, cost_for_2k);
        } else {
            calculate('cost_for_2_5k', cost_for_2k);
            calculate('cost_for_3k', cost_for_2k);
            calculate('cost_for_4k', cost_for_2k);
        }

    } else {
        const cost_for_2k = parseInt([...document.getElementsByClassName('cost_for_2k')].find(item => item.dataset.title == event.dataset.title).innerText);
        const cost_for_3k_float = parseFloat([...document.getElementsByClassName('cost_for_3k')].find(item => item.dataset.title == event.dataset.title).innerText).toFixed(15);
        const cost_for_4k_float = parseFloat([...document.getElementsByClassName('cost_for_4k')].find(item => item.dataset.title == event.dataset.title).innerText).toFixed(15);
        const cost_for_3k = Math.round(cost_for_2k * cost_for_3k_float / 1000) *1000;
        const cost_for_4k = Math.round(cost_for_2k * cost_for_4k_float / 1000) * 1000;

        const relate_cost_for_2k = ['cost_for_2_5k', 'cost_for_3k', 'cost_for_4k', 'cost_for_20k', 'cost_for_20Tk'];
        const relate_cost_for_3k = ['cost_for_30k', 'cost_for_30Tk'];
        const relate_cost_for_4k = ['cost_for_40k', 'cost_for_40Tk'];

        let standard;

        if (relate_cost_for_2k.includes(type)) {
            standard = cost_for_2k;
        } else if (relate_cost_for_3k.includes(type)) {
            standard = cost_for_3k;
        } else if (relate_cost_for_4k.includes(type)) {
            standard = cost_for_4k;
        }

        calculate(type, standard);
    }

    function calculate(type, standard) {
        let value = parseFloat([...document.getElementsByClassName(type)].find(item => item.dataset.title == event.dataset.title).value).toFixed(15);
        let target = [...document.getElementsByClassName(`display_${type}`)].find(item => item.dataset.title == event.dataset.title);
        let result;
        result = Math.round(standard * value / 1000 ) * 1000 || '';
        target.innerText = result;
    }
}

// 보증금 숨기기 버튼
// function hideDeposit() {
//     const depositColumns = [...document.getElementsByClassName('depositColumn')];
//     depositColumns.forEach(column => {
//         column.classList.toggle('displayNone');
//     });
// }