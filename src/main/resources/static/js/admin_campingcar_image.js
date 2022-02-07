let imgBoxList = document.getElementsByClassName('image-box');
let uploadBoxList = document.getElementsByClassName('upload-box');
let inputFileList = document.querySelectorAll('input[type="file"]');

// 메인&추가 이미지 등록 및 삭제
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
                postData();
            };
        } else {
            alert('업로드할 사진을 선택해주세요.');
        };
    } else if (behavior == 'mainDelete') {
        if (confirm('대표 이미지를 삭제 하시겠습니까?')) {
            deleteData();
        };
    } else if (behavior == 'extraUpload') {
        let input = Array.from(inputFileList).filter(ele => ele.title == carName).find(ele => ele.dataset.title == imageId).files[0];

        if (input) {
            formData.append('file', input);
            formData.append('imageId', imageId);
            formData.append('carName', carName);
            formData.append('title', title);
            formData.append('url', '');
            formData.append('isUploaded', '1');
            formData.append('isMain', '0');

            if (confirm('해당 이미지를 등록 하시겠습니까?')) {
                postData();
            };
        } else {
            alert('등록할 사진을 선택해주세요.');
        };
    } else if (behavior == 'extraDelete') {
        if (confirm('이 이미지를 삭제 하시겠습니까?')) {
            deleteData();
        };
    };

    function postData() {
        $.ajax({
            enctype: 'multipart/form-data',
            cache: false,
            type: 'POST',
            url: '/admin/campingcar/image/',
            processData:false,
            contentType: false,
            data: formData
        }).done(function () {
            alert('업로드가 완료되었습니다.');
            window.location.href = '/admin/campingcar/image/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };

    function deleteData() {
        $.ajax({
            type:'DELETE',
            url:'/admin/campingcar/image/'+ imageId,
            dataType:'text',
            contentType : 'application/json; charset=utf-8'
        }).done(function () {
            alert('삭제가 완료되었습니다.');
            window.location.href = '/admin/campingcar/image/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };
}

// $.ajax({
//             enctype: 'multipart/form-data',
//             cache: false,
//             type: 'PUT',
//             url: '/admin/campingcar/image/' + imageId,
//             processData:false,
//             contentType: false,
//             data: formData

//datatype: text
//         }).done(function (result) {
//             if (result.result == 1) {
//                 alert('업로드가 완료되었습니다.');
//                 window.location.href = '/admin/campingcar/image/menu';
//             } else {
//                 alert('업로드에 문제가 생겼습니다.');
//             };
//         }).fail(function (error) {
//             alert(JSON.stringify(error));
//         })

// 이미지 순서 변경 창 보이기
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

// 이미지 순서 변경 후 저장
function saveChangedData(carName) {
    let boxList = document.getElementsByClassName(carName+'Item');
    if (confirm('추가 이미지 순서를 변경하시겠습니까?')) {
        let tempTitle = 1;
        let imageTitleList = [];

        [...boxList].forEach(function(box) {
            let imageId = parseInt(box.dataset.imageid);
            let oneData = {
                imageId: imageId,
                title: tempTitle
            }
            tempTitle++;
            imageTitleList.push(oneData);
        });

        let data = {
            imageTitleList: imageTitleList
        }

        editData(data);
    };

    function editData(data) {
        $.ajax({
            type: 'PUT',
            url: '/admin/campingcar/image/title',
            dataType: 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('이미지 순서가 변경 되었습니다.');
            } else {
                alert('이미지 순서 변경에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/campingcar/image/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    };
}

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