function displayCheapestPrice() {
    console.log('hi');
    let euroObj, limoObj, travelObj;

    let europePrice = document.getElementById('europePrice');
    let limousinePrice = document.getElementById('limousinePrice');
    let travelPrice = document.getElementById('travelPrice');


    fetch(`/camping/calendar/europe/getprice/0`)
        .then(res => res.json())
        .then(result => {
            euroObj = result;
            europePrice.innerText = euroObj['onedays'].toLocaleString();
        })

    fetch(`/camping/calendar/limousine/getprice/0`)
        .then(res => res.json())
        .then(result => {
            limoObj = result;
            limousinePrice.innerText = limoObj['onedays'].toLocaleString();
        })

    fetch(`/camping/calendar/travel/getprice/0`)
        .then(res => res.json())
        .then(result => {
            travelObj = result;
            travelPrice.innerText = travelObj['onedays'].toLocaleString();
        })

}