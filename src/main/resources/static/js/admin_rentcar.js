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

//월렌트 메뉴 페이지 가격 수정 버튼
function editMonthlyPriceMenu(id) {
    let category1 = [...document.getElementsByClassName('category1')].find(item => item.dataset.title == id);
    let category2 = [...document.getElementsByClassName('category2')].find(item => item.dataset.title == id);
    let name = [...document.getElementsByClassName('name')].find(item => item.dataset.title == id);
    let deposit = [...document.getElementsByClassName('deposit')].find(item => item.dataset.title == id);
    let cost_for_2k = [...document.getElementsByClassName('cost_for_2k')].find(item => item.dataset.title == id);
    let cost_for_2_5k = [...document.getElementsByClassName('cost_for_2_5k')].find(item => item.dataset.title == id);
    let cost_for_3k = [...document.getElementsByClassName('cost_for_3k')].find(item => item.dataset.title == id);
    let cost_for_4k = [...document.getElementsByClassName('cost_for_4k')].find(item => item.dataset.title == id);
    let cost_for_others = [...document.getElementsByClassName('cost_for_others')].find(item => item.dataset.title == id);
    let age_limit = [...document.getElementsByClassName('age_limit')].find(item => item.dataset.title == id);
    let cost_per_km = [...document.getElementsByClassName('cost_per_km')].find(item => item.dataset.title == id);
    let nameMoren = [...document.getElementsByClassName('nameMoren')].find(item => item.dataset.title == id);
    let start = [...document.getElementsByClassName('start')].find(item => item.dataset.title == id);
    let end = [...document.getElementsByClassName('end')].find(item => item.dataset.title == id);
    let credit = [...document.getElementsByClassName('credit')].find(item => item.dataset.title == id);
    let img_url = [...document.getElementsByClassName('img_url')].find(item => item.dataset.title == id);

    let data = {
        id: id,
        category1: category1.value,
        category2: category2.value,
        name: name.value,
        deposit: deposit.value.replace(/,/g, ''),
        cost_for_2k: cost_for_2k.value.replace(/,/g, ''),
        cost_for_2_5k: cost_for_2_5k.value.replace(/,/g, ''),
        cost_for_3k: cost_for_3k.value.replace(/,/g, ''),
        cost_for_4k: cost_for_4k.value.replace(/,/g, ''),
        cost_for_others: cost_for_others.value,
        age_limit: age_limit.innerText,
        cost_per_km: cost_per_km.innerText,
        nameMoren: nameMoren.innerText,
        start: parseInt(start.innerText),
        end: parseInt(end.innerText),
        credit: credit.innerText,
        img_url: img_url.innerText
    }
    console.log(data);
}

//월렌트 상세 페이지 가격 수정 버튼
function editMonthlyPriceDetail(id) {
    const category1 = document.getElementById('category1');
    const category2 = document.getElementById('category2');
    const name = document.getElementById('name');
    let deposit = document.getElementById('category1');
    let cost_for_2k = document.getElementById('cost_for_2k');
    let cost_for_2_5k = document.getElementById('cost_for_2_5k');
    let cost_for_3k = document.getElementById('cost_for_3k');
    let cost_for_4k = document.getElementById('cost_for_4k');
    const cost_for_others = document.getElementById('cost_for_others');
    let age_limit = document.getElementById('age_limit');
    const cost_per_km = document.getElementById('cost_per_km');
    const nameMoren = document.getElementById('nameMoren');
    const start = document.getElementById('start');
    const end = document.getElementById('end');
    const credit = document.getElementById('credit');
    const img_url = document.getElementById('img_url');

    let data = {
        id: id,
        category1: category1.value,
        category2: category2.value,
        name: name.value,
        deposit: deposit.value.replace(/,/g, ''),
        cost_for_2k: cost_for_2k.value.replace(/,/g, ''),
        cost_for_2_5k: cost_for_2_5k.value.replace(/,/g, ''),
        cost_for_3k: cost_for_3k.value.replace(/,/g, ''),
        cost_for_4k: cost_for_4k.value.replace(/,/g, ''),
        cost_for_others: cost_for_others.value,
        age_limit: age_limit.value.replace(/,/g, ''),
        cost_per_km: cost_per_km.value,
        nameMoren: nameMoren.value,
        start: parseInt(start.value),
        end: parseInt(end.value),
        credit: credit.value,
        img_url: img_url.src
    }
    console.log(data);
}
