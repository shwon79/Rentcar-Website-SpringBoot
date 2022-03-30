// 내용 순서 변경 버튼 클릭_admin/campingcar/home/menu
function displayChangeIndexBox() {
    const btn = document.getElementsByClassName('change-index-btn');
    const changeIndexBox = document.getElementById('changeIndexBox');

    btn[0].classList.toggle('opened');
    changeIndexBox.classList.toggle('opened');
}

// 내용 순서 변경_admin/campingcar/home/menu
function saveChangedRowIndex() {
    console.log(`Not yet`);
}

// 텍스트 내용 수정_admin/campingcar/home/detail
function editCampingHomeText(id) {
    const title = document.getElementById('title').value;
    const description = document.getElementById('description').value;
    const textSequence = document.getElementById('textSequence').innerText;

    const data = {
        title: title,
        description: description,
        sequence: parseInt(textSequence)
    }

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