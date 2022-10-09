// 내용 순서 변경 버튼 클릭
function displayChangeIndexBox() {
    const btn = document.getElementsByClassName('change-index-btn');
    const changeIndexBox = document.getElementById('changeIndexBox');

    btn[0].classList.toggle('opened');
    changeIndexBox.classList.toggle('opened');
}

// 차량 순서 변경 창 보이기
function displaySortingBox() {
    let boxList = document.getElementsByClassName('changeOrderBox');
    let btnList = document.getElementsByClassName('changeOrderBtn');
    [...boxList].forEach(function(box) {
        box.classList.toggle('active');
    });
    [...btnList].forEach(function(btn) {
        btn.classList.toggle('active');
    });
};

// 내용 순서 변경
function saveChangedRowIndex() {
    let boxList = document.getElementsByClassName('oneItem');

    if (confirm('순서를 변경하시겠습니까?')) {
        let tempTitle = 1;
        let imageTitleList = [];

        [...boxList].forEach(function(box) {
            let imageId = parseInt(box.dataset.id);
            let oneData = {
                imageId: imageId,
                title: tempTitle
            }
            tempTitle++;
            imageTitleList.push(oneData);
        });

        let data = {
            imageTitleList: imageTitleList
        };

        $.ajax({
            type: 'PUT',
            url: '/admin/longTerm/sequence',
            dataType: 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('순서가 변경 되었습니다.');
            } else {
                alert('순서 변경에 문제가 생겼습니다.');
            };
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    };
}

// 내용 순서 변경
function saveChangedRowIndex() {
    let boxList = document.getElementsByClassName('oneItem');

    if (confirm('순서를 변경하시겠습니까?')) {
        let tempTitle = 1;
        let imageTitleList = [];

        [...boxList].forEach(function(box) {
            let imageId = parseInt(box.dataset.id);
            let oneData = {
                imageId: imageId,
                title: tempTitle
            }
            tempTitle++;
            imageTitleList.push(oneData);
        });

        let data = {
            imageTitleList: imageTitleList
        };

        $.ajax({
            type: 'PUT',
            url: '/admin/longTerm/sequence',
            dataType: 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('순서가 변경 되었습니다.');
            } else {
                alert('순서 변경에 문제가 생겼습니다.');
            };
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    };
}
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

    if (confirm('장기렌트 차량을 등록 하시겠습니까?')) {
        // console.log(data);
        postLongTermCar(formData);
    };

    // if (requiredFields.includes('') || requiredFields.includes(undefined) || image.length == 0) {
    //     alert('필수 입력 내용을 빠짐없이 작성해주세요.');
    // } else if (!requiredFields.includes('') && !requiredFields.includes(undefined) && image.length != 0) {
    //     formData.append('carName', carName);
    //     formData.append('carNum', carNum);
    //     formData.append('carColor', carColor);
    //     formData.append('carYearModel', carYearModel);
    //     formData.append('contractPeriod', contractPeriod);
    //     formData.append('contractKm', contractKm);
    //     formData.append('contractPrice', contractPrice);
    //     formData.append('contractDeposit', contractDeposit);
    //     formData.append('contractMaintenance', contractMaintenance);
    //     formData.append('newOld', newOld);
    //     formData.append('fuel', fuel);
    //     formData.append('description', description);
    //     for (let i = 0; i < image.length; i++) {
    //         formData.append('file', image[i]);
    //     };
    //
    //     // let data = {
    //     //     carName: carName,
    //     //     carNum: carNum,
    //     //     carColor: carColor,
    //     //     carYearModel: carYearModel,
    //     //     contractPeriod: contractPeriod,
    //     //     contractKm: contractKm,
    //     //     contractPrice: contractPrice,
    //     //     contractDeposit: contractDeposit,
    //     //     contractMaintenance: contractMaintenance,
    //     //     newOld: newOld,
    //     //     image: image,
    //     //     fuel: fuel,
    //     //     description: description
    //     // };
    //
    //     if (confirm('장기렌트 차량을 등록 하시겠습니까?')) {
    //         // console.log(data);
    //         postLongTermCar(formData);
    //     };
    // };
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
                window.location.href='/admin/longTerm/main';
            } else if (result.result == 0) {
                alert('삭제에 문제가 생겼습니다.');
                location.reload();
            };
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