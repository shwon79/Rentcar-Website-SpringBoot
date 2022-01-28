let imgBoxList = document.getElementsByClassName('image-box');
let uploadBoxList = document.getElementsByClassName('upload-box');
let inputFileList = document.querySelectorAll('input[type="file"]');

// Array.from(uploadBoxList).forEach(function(uploadBox) {
//     uploadBox.addEventListener('dragenter', function(e) {
//         console.log('dragenter');
//     });
//
//     uploadBox.addEventListener('dragover', function(e) {
//         e.preventDefault();
//         console.log('dragover');
//
//         this.style.backgroundColor = 'lightblue';
//     });
//     uploadBox.addEventListener('dragleave', function(e) {
//         console.log('dragleave');
//
//         this.style.backgroundColor = 'white';
//     });
//     uploadBox.addEventListener('drop', function(e) {
//         e.preventDefault();
//
//         console.log('drop');
//         this.style.backgroundColor = 'white';
//         console.dir(e.dataTransfer);
//
//         var data = e.dataTransfer.files[0];
//         console.dir(data);
//     });
// });


// 사진 업로드 버튼
function uploadImage(carName, title, imageId) {
    let input = Array.from(inputFileList).filter(ele => ele.title == carName).find(ele => ele.dataset.title == title).files[0];
    let formData;

    if (input) {
        formData = new FormData();
        formData.append('data', input);
    }

    let data = {
        imageId: imageId,
        carName: carName,
        title: title,
        url: '',
        isUploaded: 1,
        file: formData
    };

    console.log(data);

    if (formData) {
        if (confirm('해당 사진을 업로드 하시겠습니까?')) {
            $.ajax({
                enctype: 'multipart/form-data',
                cache: false,
                type: 'PUT',
                url: '/admin/campingcar/image/' + imageId,
                processData:false,
                contentType: false,
                dataType: 'json',
                data: JSON.stringify(data)
            }).done(function (result) {
                if (result.result == 1) {
                    alert('업로드가 완료되었습니다.');
                } else {
                    alert('업로드에 문제가 생겼습니다.');
                };
                window.location.href = '/admin/campingcar/image/menu';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        };
    } else {
        alert('업로드할 사진을 선택해주세요.');
    }

}
// function doNotMove() {
//     window.location = "/admin/campingcar/image/menu";
// }