// 종류 변경 시, 종류에 따른 분류 옵션 보여주기
function displayCategory2(event) {
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
    }

    selectedCategory2.options.length = 0;

    for (i in selectedOptions) {
        let option = document.createElement('option');
        option.value = selectedOptions[i];
        option.innerText = selectedOptions[i];

        selectedCategory2.appendChild(option);
    };
};

console.log('hi');
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
        formData.append('file', img_input.files[0])

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

        for (let key of formData.keys()) {
            console.log(key);
        }
        for (let value of formData.values()) {
            console.log(value);
        }
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