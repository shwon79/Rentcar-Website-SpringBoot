// 장기렌트 새로운 차량 등록 버튼 클릭_admin/longTerm/register
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
    let image = document.getElementById('image').files;

    const requiredFields = [carName, carNum, carColor, carYearModel, contractPeriod, contractKm, contractMaintenance, contractPrice, contractDeposit, newOld];

    let formData = new FormData();

    // if (image.length > 10) {
    //     // 사진 최대 갯수 10개
    //     alert('이미지 첨부는 최대 10장까지 가능합니다.');
    // } else

    if (requiredFields.includes('') || requiredFields.includes(undefined) || image.length == 0) {
        alert('필수 입력 내용을 빠짐없이 작성해주세요.');
    } else if (!requiredFields.includes('') && !requiredFields.includes(undefined) && image.length != 0) {
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
        for (let i = 0; i < image.length; i++) {
            formData.append('file', image[i]);
        };

        // let data = {
        //     carName: carName,
        //     carNum: carNum,
        //     carColor: carColor,
        //     carYearModel: carYearModel,
        //     contractPeriod: contractPeriod,
        //     contractKm: contractKm,
        //     contractPrice: contractPrice,
        //     contractDeposit: contractDeposit,
        //     contractMaintenance: contractMaintenance,
        //     newOld: newOld,
        //     image: image,
        // };

        if (confirm('장기렌트 차량을 등록 하시겠습니까?')) {
            // console.log(data);
            postLongTermCar(formData);
        };
    };
};

// 새로운 장기렌트 차량 등록 기능_admin/longTerm/register
function postLongTermCar(data) {
    $.ajax({
        enctype: 'multipart/form-data',
        cache: false,
        type: 'POST',
        url: '/admin/longTerm',
        processData:false,
        contentType: false,
        data: data
    }).done(function () {
        alert('장기렌트 차량이 등록되었습니다.');
        location.reload();
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
};

// 장기렌트 현황에서 수정하기 버튼 클릭_admin/longTerm/main
function clickEdit(e) {
    const id = e.dataset.id;

    let carName = [...document.getElementsByClassName('carName')].find(ele => ele.dataset.id == id).value;
    let carNum = [...document.getElementsByClassName('carNum')].find(ele => ele.dataset.id == id).value.replace(/(\s*)/g, '');
    let carColor = [...document.getElementsByClassName('carColor')].find(ele => ele.dataset.id == id).value;
    let carYearModel = [...document.getElementsByClassName('carYearModel')].find(ele => ele.dataset.id == id).value;
    let contractPeriod = [...document.getElementsByClassName('contractPeriod')].find(ele => ele.dataset.id == id).value;
    let contractKm = [...document.getElementsByClassName('contractKm')].find(ele => ele.dataset.id == id).value;
    let contractPrice = [...document.getElementsByClassName('contractPrice')].find(ele => ele.dataset.id == id).value;
    let contractDeposit = [...document.getElementsByClassName('contractDeposit')].find(ele => ele.dataset.id == id).value;
    let contractMaintenance = [...document.getElementsByClassName('contractMaintenance')].find(ele => ele.dataset.id == id).value;
    let newOld = [...document.getElementsByClassName('newOld')].find(ele => ele.dataset.id == id).value;
    let fuel = [...document.getElementsByClassName('fuel')].find(ele => ele.dataset.id == id).value;

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
    }

    editLongTermCar(id, data);
};

// 장기렌트 차량 수정 기능_admin/longTerm/menu
function editLongTermCar(id, data) {
    if (confirm('수정하시겠습니까?')) {
        $.ajax({
            type:'PUT',
            url:'/admin/longTerm/' + id,
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

// 장기렌트 현황에서 삭제하기 버튼 클릭_admin/longTerm/main
function clickDelete(e) {
    const id = e.dataset.id;

    deleteLongTermCar(id);
};

// 장기렌트 차량 삭제하기 버튼 클릭_admin/longTerm/main
function deleteLongTermCar(id) {
    if (confirm('차량을 삭제하시겠습니까?')) {
        $.ajax({
            type:'DELETE',
            url:'/admin/longTerm/'+ id,
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
};

// 이미지 추가 클릭_admin/longTerm/detail
function clickAddImage(e) {
    let longTermRentId = e.dataset.id;
    const imageFiles = document.getElementById('imageFile').files;

    // 이미지 있는 지 확인 필요!!!!!!!1
    let formData = new FormData();

    if (imageFiles.length == 0) {
        alert('추가할 이미지를 선택하세요');
    } else {
        formData.append('longTermRentId', longTermRentId);
        for (let i = 0; i < imageFiles.length; i++) {
            formData.append('file', imageFiles[i]);
        };
    };

    addImage(formData);
};

// 이미지 추가 기능_admin/longTerm/detail
function addImage(data) {
    if ('이미지를 추가하시겠습니까?') {
        $.ajax({
            enctype: 'multipart/form-data',
            cache: false,
            type: 'POST',
            url: '/admin/longTerm/image',
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

// 이미지 삭제 클릭_admin/longTerm/detail
function clickDeleteImage(e) {
    let id = e.dataset.id;

    deleteImage(id);
}

// 이미지 삭제 기능_admin/longTerm/detail
function deleteImage(id) {
    $.ajax({
        type:'DELETE',
        url:'/admin/longTerm/image/'+ id,
        dataType:'text',
        contentType : 'application/json; charset=utf-8'
    }).done(function () {
        alert('삭제가 완료되었습니다.');
        location.reload();
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}