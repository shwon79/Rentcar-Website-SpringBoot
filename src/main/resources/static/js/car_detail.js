let modal = document.getElementById('pickupPriceModal');
let btn = document.getElementById("pickupPriceBtn");
let span = document.getElementsByClassName("modal_close_btn")[0];

btn.addEventListener('click', () => {
    modal.style.display = "block";
})
span.addEventListener('click', () => {
    modal.style.display = "none";
})
window.addEventListener('click', (e) => {
    if(e.target==modal) {
        modal.style.display = 'none';
    }
});