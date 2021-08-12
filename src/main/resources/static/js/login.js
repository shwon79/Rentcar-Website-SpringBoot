

let idData = '';
const inputId = document.getElementById('input_id');
inputId.addEventListener('change',
    function () {
        idData = inputId.value;
        console.log(inputId.value);
    })

let pwdData = '';
const inputPwd = document.getElementById('input_pwd');
inputPwd.addEventListener('change',
    function () {
        pwdData = inputPwd.value;
        console.log(inputPwd.value);
    })

const logIn = () => {

    if(idData != '' && pwdData != '') {
        const url = '/logininfo'
        const loginData = {
            "id": idData,
            "password": pwdData,
        }

        fetch(url, {
            method: 'POST',
            headers:{
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(loginData),

        }).then(response => response.json())
            .then(result => {
                let resultBool = result[0];
                if (resultBool == 'false') {
                    alert('잘못된 접근입니다.')
                } else if (resultBool == 'true') {
                    window.location.href = '/admin/main'
                }
            })
            // .then((data) => console.log(data));

/*
.then(()=> {
    if () {

    } else {

        }
})
*/
    } else alert('입력을 완료해주세요!')

}