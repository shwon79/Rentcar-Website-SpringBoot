const setReserve = (id) => {
    const url = `/campingcar/reservation/update/${id}`
    const idData = {
        "id":id,
    }
    fetch(url)
        .then(response => console.log(response))
        .catch(err => console.error('Error: ', err))
}