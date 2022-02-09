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