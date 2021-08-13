const setReserve = (id) => {
    const url = `/campingcar/reservation/update/${id}`
    const idData = {
        "id":id,
    }
    let reserveConfirm = confirm(`${id}의 예약을 확정하시겠습니까?`);
    if(reserveConfirm) {
        fetch(url)
            .then(response => {
                console.log(response)
                alert(`${id}의 예약이 확정되었습니다.`)
                let idBtn = document.getElementById(id)
                idBtn.disabled = true
            }).then(() => window.location.href = '/admin/logininfo')
            .catch(err => console.error('Error: ', err))
    }

}