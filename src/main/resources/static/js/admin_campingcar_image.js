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
function uploadImage(behavior, carName, title, imageId) {
    let formData = new FormData();

    if (behavior == 'mainUpload') {
        let input = Array.from(inputFileList).filter(ele => ele.title == carName).find(ele => ele.dataset.title == '0').files[0];
        if (input) {
            formData.append('file', input);
            formData.append('imageId', imageId);
            formData.append('carName', carName);
            formData.append('title', title);
            formData.append('url', '');
            formData.append('isUploaded', '1');
            formData.append('isMain', '1');
            if (confirm('해당 사진을 대표 이미지로 등록 하시겠습니까?')) {
                console.log(formData.getAll('file'));
                console.log(formData.getAll('imageId'));
                console.log(formData.getAll('carName'));
                console.log(formData.getAll('title'));
                console.log(formData.getAll('url'));
                console.log(formData.getAll('isUploaded'));
                console.log(formData.getAll('isMain'));
                $.ajax({
                    enctype: 'multipart/form-data',
                    cache: false,
                    type: 'POST',
                    url: '/admin/campingcar/image/',
                    processData:false,
                    contentType: false,
                    data: formData
                }).done(function (result) {
                    if (result.result == 1) {
                        alert('업로드가 완료되었습니다.');
                        window.location.href = '/admin/campingcar/image/menu';
                    } else {
                        alert('업로드에 문제가 생겼습니다.');
                    };
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                })
            };
        } else {
            alert('업로드할 사진을 선택해주세요.');
        };
    } else if (behavior == 'mainDelete') {
        if (confirm('대표 이미지를 삭제 하시겠습니까?')) {
            $.ajax({
                type:'DELETE',
                url:'/admin/campingcar/image/'+ imageId,
                dataType:'json',
                contentType : 'application/json; charset=utf-8'
            }).done(function (result) {
                if (result.result == 1) {
                    alert('삭제가 완료되었습니다.');
                    window.location.href = '/admin/campingcar/image/menu';
                } else {
                    alert('삭제에 문제가 생겼습니다.');
                };
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        };
    };

    // if (behavior == 'upload') {
    //     let input = Array.from(inputFileList).filter(ele => ele.title == carName).find(ele => ele.dataset.title == title).files[0];
    //
    //     if (input) {
    //         formData.append('file', input);
    //         formData.append('imageId', imageId);
    //         formData.append('carName', carName);
    //         formData.append('title', title);
    //         formData.append('url', '');
    //         formData.append('isUploaded', '1');
    //         if (confirm('해당 사진을 업로드 하시겠습니까?')) {
    //             sendingData();
    //         };
    //     } else {
    //         alert('업로드할 사진을 선택해주세요.');
    //     };
    //
    // } else if (behavior == 'delete') {
    //     formData.append('file', '');
    //     formData.append('imageId', imageId);
    //     formData.append('carName', carName);
    //     formData.append('title', title);
    //     formData.append('url', '');
    //     formData.append('isUploaded', '0');
    //     if (confirm('해당 사진을 삭제 하시겠습니까?')) {
    //         console.log(formData.get('file'));
    //         console.log(formData.get('imageId'));
    //         console.log(formData.get('carName'));
    //         console.log(formData.get('title'));
    //         console.log(formData.get('url'));
    //         console.log(formData.get('isUploaded'));
    //         sendingData();
    //     };
    // }

    // console.log(input);
    // console.log(formData.getAll('data'));
    //
    // let data = {
    //     imageId: imageId,
    //     carName: carName,
    //     title: title.toString(),
    //     url: '',
    //     isUploaded: '1',
    //     file: formData
    // };


    // if (formData) {
    //
    // } else {
    // }

    function sendingData() {
        $.ajax({
            enctype: 'multipart/form-data',
            cache: false,
            type: 'PUT',
            url: '/admin/campingcar/image/' + imageId,
            processData:false,
            contentType: false,
            data: formData
        }).done(function (result) {
            if (result.result == 1) {
                alert('업로드가 완료되었습니다.');
                window.location.href = '/admin/campingcar/image/menu';
            } else {
                alert('업로드에 문제가 생겼습니다.');
            };
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };


}
// function doNotMove() {
//     window.location = "/admin/campingcar/image/menu";
// }