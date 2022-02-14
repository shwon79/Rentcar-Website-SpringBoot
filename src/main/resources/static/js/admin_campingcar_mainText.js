let imgBoxList = document.getElementsByClassName('image-box');
let uploadBoxList = document.getElementsByClassName('upload-box');
let inputFileList = document.querySelectorAll('input[type="file"]');

// 메인&추가 이미지 등록 및 삭제
function uploadImage(behavior, carName, title, imageId) {
    let formData = new FormData();

    if (behavior == 'upload') {
        let input = Array.from(inputFileList).filter(ele => ele.title == carName).find(ele => ele.dataset.title == imageId).files[0];

        if (input) {
            formData.append('file', input);
            formData.append('carName', carName);
            formData.append('title', 20);
            formData.append('url', '');
            formData.append('isUploaded', '1');
            if (confirm('해당 사진을 본문 이미지로 등록 하시겠습니까?')) {
                postData();
                sessionStorage.setItem('campingMainTextTab', carName);
            };
        } else {
            alert('업로드할 사진을 선택해주세요.');
        };
    } else if (behavior == 'delete') {
        if (confirm('이미지를 삭제 하시겠습니까?')) {
            deleteData();
            sessionStorage.setItem('campingMainTextTab', carName);
        };
    };

    function postData() {
        $.ajax({
            enctype: 'multipart/form-data',
            cache: false,
            type: 'POST',
            url: '/admin/campingcar/mainText',
            processData:false,
            contentType: false,
            data: formData
        }).done(function () {
            alert('업로드가 완료되었습니다.');
            window.location.href = '/admin/campingcar/mainText/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };

    function deleteData() {
        $.ajax({
            type:'DELETE',
            url:'/admin/campingcar/mainText/'+ imageId,
            dataType:'text',
            contentType : 'application/json; charset=utf-8'
        }).done(function () {
            alert('삭제가 완료되었습니다.');
            window.location.href = '/admin/campingcar/mainText/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    };
}

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
    if (confirm('이미지 순서를 변경하시겠습니까?')) {
        let tempTitle = 1;
        let campingCarMainTextTitleList = [];

        [...boxList].forEach(function(box) {
            let imageId = parseInt(box.dataset.imageid);
            let oneData = {
                imageId: imageId,
                title: tempTitle
            }
            tempTitle++;
            campingCarMainTextTitleList.push(oneData);
        });

        let data = {
            campingCarMainTextTitleList: campingCarMainTextTitleList
        }

        editData(data);
        sessionStorage.setItem('campingMainTextTab', carName);
    };

    function editData(data) {
        $.ajax({
            type: 'PUT',
            url: '/admin/campingcar/mainText/title',
            dataType: 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (result) {
            if (result.result == 1) {
                alert('이미지 순서가 변경 되었습니다.');
            } else {
                alert('이미지 순서 변경에 문제가 생겼습니다.');
            };
            window.location.href = '/admin/campingcar/mainText/menu';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    };
};

// active tab 확인
function checkCampingMainTextTab() {
    let tabStatus = sessionStorage.getItem('campingMainTextTab');
    let navLinkList = document.getElementsByClassName('nav-link');
    let tabPaneList = document.getElementsByClassName('tab-pane');

    [...navLinkList].forEach((navLink) => {
        if (tabStatus && tabStatus === navLink.dataset.title) {
            navLink.classList.add('active');
            navLink.classList.add('show');
        } else {
            navLink.classList.remove('active');
            navLink.classList.remove('show');
        }
    });
    [...tabPaneList].forEach((tabpane) => {
        if (tabStatus && tabStatus === tabpane.id) {
            tabpane.classList.add('show');
            tabpane.classList.add('active');
        } else {
            tabpane.classList.remove('show');
            tabpane.classList.remove('active');
        }
    })

    if (!tabStatus) {
        navLinkList[0].classList.add('active');
        navLinkList[0].classList.add('show');
        tabPaneList[0].classList.add('active');
        tabPaneList[0].classList.add('show');
    }
}
window.onload = checkCampingMainTextTab();