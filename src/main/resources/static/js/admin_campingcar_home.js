// 내용 순서 변경 버튼 클릭_admin/campingcar/home/menu
function displayChangeIndexBox() {
    const btn = document.getElementsByClassName('change-index-btn');
    const changeIndexBox = document.getElementById('changeIndexBox');

    btn[0].classList.toggle('opened');
    changeIndexBox.classList.toggle('opened');
}

// 내용 추가_admin/campingcar/home/register
function addCampingCarHome() {
    const title = document.getElementById('title').value;
    const description = document.getElementById('description').value;
    const columnNum = parseInt(document.getElementById('columnNum').value);
    const sequence = parseInt(document.getElementById('sequence').innerText);
    const images = document.getElementById('image').files;

    if (title == '' || description == '' || images.length === 0) {
        alert('모든 내용을 입력해주세요.')
    } else {
        if (confirm('새로운 내용을 등록하시겠습니까?')) {
            let formData = new FormData();

            formData.append('title', title);
            formData.append('description', description);
            formData.append('columnNum', columnNum);
            formData.append('sequence', sequence + 1)
            for (let i = 0; i < images.length; i++) {
                formData.append('file', images[i]);
            };

            $.ajax({
                enctype: 'multipart/form-data',
                cache: false,
                type: 'POST',
                url: '/admin/campingcar/home',
                processData:false,
                contentType: false,
                data: formData
            }).done(function () {
                alert('새로운 내용이 등록되었습니다.');
                window.location.href = '/admin/campingcar/home/menu'
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        }
    }
};

// 내용 순서 변경_admin/campingcar/home/menu
function saveChangedRowIndex() {
    let boxList = document.getElementsByClassName('oneItem');

    if (confirm('내용 순서를 변경하시겠습니까?')) {
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
            url: '/admin/campingcar/home/sequence',
            dataType: 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('내용 순서가 변경 되었습니다.');
            } else {
                alert('내용 순서 변경에 문제가 생겼습니다.');
            };
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    };
}

// 내용 삭제_admin/campingcar/home/menu, detail
function deleteCampingCarHome(id, pageType) {
    if (confirm('이 내용을 삭제하시겠습니까?')) {
        $.ajax({
            type:'DELETE',
            url:'/admin/campingcar/home/'+ id,
            dataType:'text',
            contentType : 'application/json; charset=utf-8'
        }).done(function (result) {
            if (result.result == 1) {
                alert('삭제되었습니다.');
            } else if (result.result == 0) {
                alert('삭제에 문제가 생겼습니다.');
            };

            if (pageType == 'menu') {
                location.reload();
            } else if (pageType == 'detail') {
                window.location.href='/admin/campingcar/home/menu';
            };
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
}

// 텍스트 내용 수정_admin/campingcar/home/detail
function editCampingHomeText(id) {
    const title = document.getElementById('title').value;
    const description = document.getElementById('description').value;
    const textSequence = document.getElementById('textSequence').innerText;
    const columnNum = document.getElementById('columnNum').value;

    const data = {
        title: title,
        description: description,
        columnNum: parseInt(columnNum),
        sequence: parseInt(textSequence)
    };

    if (confirm('텍스트 내용을 수정하시겠습니까?')) {
        $.ajax({
            type:'PUT',
            url: '/admin/campingcar/home/' + id,
            dataType:'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('처리되었습니다.');
            } else if (result.result == 0) {
                alert('처리에 문제가 생겼습니다.');
            };
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
}

// 이미지 순서 변경_admin/campingcar/home/detail
function saveChangedImageIndex() {
    let boxList = document.getElementsByClassName('oneItem');

    if (confirm('이미지 순서를 변경하시겠습니까?')) {
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
            url: '/admin/campingcar/home/image/sequence',
            dataType: 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('이미지 순서가 변경 되었습니다.');
            } else {
                alert('이미지 순서 변경에 문제가 생겼습니다.');
            };
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    };
}

// 새 이미지 추가_admin/campingcar/home/detail
function uploadImage(id) {
    let newImage = document.getElementById('newImage').files;
    let sequence = document.getElementById('newImageSequence').innerText;

    if (newImage.length == 0) {
        alert('추가할 이미지를 선택해주세요.')
    } else {
        let formData = new FormData();

        formData.append('homeId', id);
        formData.append('sequence', sequence);
        formData.append('file', newImage[0]);

        if (confirm('이미지를 추가하시겠습니까?')) {
            $.ajax({
                enctype: 'multipart/form-data',
                cache: false,
                type: 'POST',
                url: '/admin/campingcar/home/image',
                processData: false,
                contentType: false,
                data: formData
            }).done(function () {
                alert('업로드가 완료되었습니다.');
                location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        }
    }
};

// 이미지 한장씩 삭제_admin/campingcar/home/detail
function deleteImage(imageId) {
    if (confirm('이미지를 삭제하시겠습니까?')) {
        $.ajax({
            type:'DELETE',
            url:'/admin/campingcar/home/image/'+ imageId,
            dataType:'text',
            contentType : 'application/json; charset=utf-8'
        }).done(function (result) {
            if (result.result == 1) {
                alert('이미지가 삭제되었습니다.');
            } else if (result.result == 0) {
                alert('이미지가 삭제에 문제가 생겼습니다.');
            };
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
};