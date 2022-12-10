
// 구독 새로운 차량 등록 버튼 클릭_admin/subscribe/register
function clickRegister() {
    let carName = document.getElementById('carName').value;
    let carNum = document.getElementById('carNum').value.replace(/(\s*)/g, '');
    let carColor = document.getElementById('carColor').value;
    let carYearModel = document.getElementById('carYearModel').value;
    let contractPeriod = document.getElementById('contractPeriod').value + '개월';
    let contractKm = document.getElementById('contractKm').value + 'km';
    let contractPrice = document.getElementById('contractPrice').value;
    let contractDeposit = document.getElementById('contractDeposit').value;
    let contractMaintenance = document.getElementById('contractMaintenance').value;
    let newOld = document.getElementById('newOld').value;
    let fuel = document.getElementById('fuel').value;
    let description = document.getElementById('description').value;
    let image = document.getElementById('image').files;

    const requiredFields = [carName, carNum, carColor, carYearModel, contractPeriod, contractKm, contractMaintenance, contractPrice, contractDeposit, newOld];

    let formData = new FormData();

    formData.append('carName', carName);
    formData.append('carNum', carNum);
    formData.append('carColor', carColor);
    formData.append('carYearModel', carYearModel);
    formData.append('contractPeriod', contractPeriod);
    formData.append('contractKm', contractKm);
    formData.append('contractPrice', contractPrice);
    formData.append('contractDeposit', contractDeposit);
    formData.append('contractMaintenance', contractMaintenance);
    formData.append('newOld', newOld);
    formData.append('fuel', fuel);
    formData.append('description', description);
    for (let i = 0; i < image.length; i++) {
        formData.append('file', image[i]);
    };

    if (confirm('구독 차량을 등록 하시겠습니까?')) {
        // console.log(data);
        postLongTermCar(formData);
    };
};

// 새로운 구독 차량 등록 기능_admin/longTerm/register
function postLongTermCar(data) {
    $.ajax({
        enctype: 'multipart/form-data',
        cache: false,
        type: 'POST',
        url: '/admin/subscribe',
        processData:false,
        contentType: false,
        data: data
    }).done(function () {
        alert('구독 차량이 등록되었습니다.');
        location.reload();
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
};


// 구독 현황에서 수정하기 버튼 클릭_admin/subscribe/main
function clickEdit(e) {
    const id = e.dataset.id;

    let carName = document.getElementById('carName').value;
    let carNum = document.getElementById('carNum').value.replace(/(\s*)/g, '');
    let carColor = document.getElementById('carColor').value;
    let carYearModel = document.getElementById('carYearModel').value;
    let contractPeriod = document.getElementById('contractPeriod').value;
    let contractKm = document.getElementById('contractKm').value;
    let contractPrice = document.getElementById('contractPrice').value;
    let contractDeposit = document.getElementById('contractDeposit').value;
    let contractMaintenance = document.getElementById('contractMaintenance').value;
    let newOld = document.getElementById('newOld').value;
    let fuel = document.getElementById('fuel').value;
    let description = document.getElementById('description').value;

    let data = {
        carName: carName,
        carNum: carNum,
        carColor: carColor,
        carYearModel: carYearModel,
        contractPeriod: contractPeriod,
        contractKm: contractKm,
        contractPrice: contractPrice,
        contractDeposit: contractDeposit,
        contractMaintenance: contractMaintenance,
        newOld: newOld,
        fuel: fuel,
        description: description
    }
    // console.log(data);

    editSubscribe(id, data);
};

// 구독 차량 수정 기능_admin/subscribe/menu
function editSubscribe(id, data) {
    if (confirm('수정하시겠습니까?')) {
        $.ajax({
            type:'PUT',
            url:'/admin/subscribe/' + id,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('수정되었습니다.');
            } else {
                alert('수정에 문제가 생겼습니다.');
            };
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };
};

// 구독 현황에서 삭제하기 버튼 클릭_admin/subscribe/main
function clickDelete(e) {
    const id = e.dataset.id;

    deleteLongTermCar(id);
};

// 구독 차량 삭제하기 버튼 클릭_admin/subscribe/main
function deleteLongTermCar(id) {
    if (confirm('차량을 삭제하시겠습니까?')) {
        $.ajax({
            type:'DELETE',
            url:'/admin/subscribe/'+ id,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
        }).done(function (result) {
            if (result.result == 1) {
                alert('삭제 되었습니다.');
                window.location.href='/admin/subscribe/main';
            } else if (result.result == 0) {
                alert('삭제에 문제가 생겼습니다.');
                location.reload();
            };
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
};

// 이미지 추가 클릭_admin/subscribe/detail
function clickAddImage(e) {
    let subscribeId = e.dataset.id;
    const imageFiles = document.getElementById('imageFile').files;

    // 이미지 있는 지 확인 필요!!!!!!!1
    let formData = new FormData();

    if (imageFiles.length == 0) {
        alert('추가할 이미지를 선택하세요');
    } else {
        formData.append('subscribeId', subscribeId);
        for (let i = 0; i < imageFiles.length; i++) {
            formData.append('file', imageFiles[i]);
        };
    };

    addImage(formData);
};

// 이미지 추가 기능_admin/subscribe/detail
function addImage(data) {
    if ('이미지를 추가하시겠습니까?') {
        $.ajax({
            enctype: 'multipart/form-data',
            cache: false,
            type: 'POST',
            url: '/admin/subscribe/image',
            processData:false,
            contentType: false,
            data: data
        }).done(function () {
            alert('이미지가 추가 되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };
};

// 이미지 삭제 클릭_admin/subscribe/detail
function clickDeleteImage(e) {
    let id = e.dataset.id;

    deleteImage(id);
}

// 이미지 삭제 기능_admin/subscribe/detail
function deleteImage(id) {
    $.ajax({
        type:'DELETE',
        url:'/admin/subscribe/image/'+ id,
        dataType:'text',
        contentType : 'application/json; charset=utf-8'
    }).done(function () {
        alert('삭제가 완료되었습니다.');
        location.reload();
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}