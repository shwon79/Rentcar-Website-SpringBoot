

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

        }).then(response => console.log('Success: ', response.json()))
            .then((data) => console.log(data));

/*
.then(()=> {
    if () {

    } else {

        }
})
*/
    } else alert('입력을 완료해주세요!')

}