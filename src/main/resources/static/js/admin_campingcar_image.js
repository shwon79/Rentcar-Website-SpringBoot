let sec9 = document.querySelector('#europe');
let imgUploadBtn = sec9.querySelector('.imgUploadBtn');
let inputFile = sec9.querySelector('input[type="file"]');
let uploadBox = sec9.querySelector('.upload-box');

/* 박스 안에 Drag 들어왔을 때 */
uploadBox.addEventListener('dragenter', function(e) {
    console.log('dragenter');
});

/* 박스 안에 Drag를 하고 있을 때 */
uploadBox.addEventListener('dragover', function(e) {
    e.preventDefault();
    console.log('dragover');

    this.style.backgroundColor = 'lightblue';
});

/* 박스 밖으로 Drag가 나갈 때 */
uploadBox.addEventListener('dragleave', function(e) {
    console.log('dragleave');

    this.style.backgroundColor = 'white';
});

/* 박스 안에서 Drag를 Drop했을 때 */
uploadBox.addEventListener('drop', function(e) {
    e.preventDefault();

    console.log('drop');
    this.style.backgroundColor = 'white';
});