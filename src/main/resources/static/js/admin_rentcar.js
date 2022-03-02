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

//월렌트 메뉴 페이지 가격 수정 버튼
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

            data['cost_for_2k'] = parseInt(cost_for_2k.value.replace(/,/g, '')).toFixed(2);
            data['cost_for_2_5k'] = parseInt(cost_for_2_5k.value).toFixed(2);
            data['cost_for_3k'] = parseInt(cost_for_3k.value).toFixed(2);
            data['cost_for_4k'] = parseInt(cost_for_4k.value).toFixed(2);

            // console.log(data);
            postStringData(period, id, data);
        } else if (period === 'yearly') {
            let cost_for_20k = [...document.getElementsByClassName('cost_for_20k')].find(item => item.dataset.title == id);
            let cost_for_30k = [...document.getElementsByClassName('cost_for_30k')].find(item => item.dataset.title == id);
            let cost_for_40k = [...document.getElementsByClassName('cost_for_40k')].find(item => item.dataset.title == id);

            data['cost_for_20k'] = parseInt(cost_for_20k.value).toFixed(2);
            data['cost_for_30k'] = parseInt(cost_for_30k.value).toFixed(2);
            data['cost_for_40k'] = parseInt(cost_for_40k.value).toFixed(2);

            // console.log(data);
            postStringData(period, id, data);
        } else if (period === 'twoYearly') {
            let cost_for_20Tk = [...document.getElementsByClassName('cost_for_20Tk')].find(item => item.dataset.title == id);
            let cost_for_30Tk = [...document.getElementsByClassName('cost_for_30Tk')].find(item => item.dataset.title == id);
            let cost_for_40Tk = [...document.getElementsByClassName('cost_for_40Tk')].find(item => item.dataset.title == id);

            data['cost_for_20Tk'] = parseInt(cost_for_20Tk.value).toFixed(2);
            data['cost_for_30Tk'] = parseInt(cost_for_30Tk.value).toFixed(2);
            data['cost_for_40Tk'] = parseInt(cost_for_40Tk.value).toFixed(2);

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

            formData.append('cost_for_2k', parseInt(cost_for_2k.value).toFixed(2));
            formData.append('cost_for_2_5k', parseInt(cost_for_2_5k.value).toFixed(2));
            formData.append('cost_for_3k', parseInt(cost_for_3k.value).toFixed(2));
            formData.append('cost_for_4k', parseInt(cost_for_4k.value).toFixed(2));

            postFormData(id, formData, period);
        }

        // for (let key of formData.keys()) {
        //     console.log(key);
        // }
        // for (let value of formData.values()) {
        //     console.log(value);
        // }
    }
}

// 이미지 없이 데이터 보낼 때
function postStringData(period, id, data) {
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

// 새로운 이미지 추가할 때
function postFormData(id, formData, period) {
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
};

// 렌트카 등록 12/24개월 등록 버튼 누를 때
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
        cost_for_20Tk = parseInt(cost_for_20Tk).toFixed(2);
        cost_for_30Tk = parseInt(cost_for_30Tk).toFixed(2);
        cost_for_40Tk = parseInt(cost_for_40Tk).toFixed(2);
    } else {
        isTwoYearExist = 0;
        depositTwoYearly = null;
        cost_per_kmTwoYearly = null;
        creditTwoYearly = null;
        cost_for_20Tk = null;
        cost_for_30Tk = null;
        cost_for_40Tk = null;
    }

    // cost_for_others float 아닌지??
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
    formData.append('cost_for_2k', parseInt(cost_for_2k).toFixed(2));
    formData.append('cost_for_2_5k', parseInt(cost_for_2_5k).toFixed(2));
    formData.append('cost_for_3k', parseInt(cost_for_3k).toFixed(2));
    formData.append('cost_for_4k', parseInt(cost_for_4k).toFixed(2));

    // yearly
    formData.append('cost_for_20k', parseInt(cost_for_20k).toFixed(2));
    formData.append('cost_for_30k', parseInt(cost_for_30k).toFixed(2));
    formData.append('cost_for_40k', parseInt(cost_for_40k).toFixed(2));

    // twoYearly
    formData.append('cost_for_20Tk', cost_for_20Tk);
    formData.append('cost_for_30Tk', cost_for_30Tk);
    formData.append('cost_for_40Tk', cost_for_40Tk);

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

    }

    const data = {
        category1: category1,
        category2: category2,
        name: name,
        deposit: deposit,
        cost_for_2k: parseInt(cost_for_2k).toFixed(2),
        cost_for_2_5k: parseInt(cost_for_2_5k).toFixed(2),
        cost_for_3k: parseInt(cost_for_3k).toFixed(2),
        cost_for_4k: parseInt(cost_for_4k).toFixed(2),
        cost_for_others: cost_for_others,
        age_limit: age_limit,
        cost_per_km: cost_per_km,
        nameMoren: nameMoren,
        start: start,
        end: end,
        credit: credit,
        img_input: img_input,
        depositYearly: depositYearly,
        cost_for_20k: parseInt(cost_for_20k).toFixed(2),
        cost_for_30k: parseInt(cost_for_30k).toFixed(2),
        cost_for_40k: parseInt(cost_for_40k).toFixed(2),
        cost_per_kmYearly: cost_per_kmYearly,
        creditYearly: creditYearly,
        depositTwoYearly: depositTwoYearly,
        cost_for_20Tk: cost_for_20Tk,
        cost_for_30Tk: cost_for_30Tk,
        cost_for_40Tk: cost_for_40Tk,
        cost_per_kmTwoYearly: cost_per_kmTwoYearly,
        creditTwoYearly: creditTwoYearly,
    }

    console.log(data);
}

// 렌트카 등록 시 배수 계산
function calculatePrice(type) {
    const cost_for_2k = parseInt(document.getElementById('cost_for_2k').value).toFixed(2);
    console.log(cost_for_2k);

    // input number 처리 필요

    // let value = parseFloat(document.getElementById(type).value).toFixed(2);
    // console.log(document.getElementById(type).value);
    // console.log(parseFloat(document.getElementById(type).value));
    // console.log(value);
    // // number로 바꾸기
    // let target = document.getElementById(`display_${type}`);
    // target.innerText = cost_for_2k * value;
}