const tabClick = (name) => {
    let tabBtns = document.getElementsByClassName('tab_buttons');
    for (const tabBtn of tabBtns) {
        tabBtn.classList.remove('current')
    }

    let theBtn = document.getElementsByName(name)
    console.log(theBtn)
    theBtn[0].classList.add('current');


    console.log('pass1')
    let tabContents = document.getElementsByClassName('tab_content')
    for (const tabContent of tabContents) {
        tabContent.classList.remove('current')
    }
    console.log('pass2')

    let target = document.getElementById(name);
    target.classList.add('current')
    console.log('pass3')
}

function toggleImg(id) {
    let before = document.getElementById("headerImg").src;
    let tmp = document.getElementById(id).src;
    document.getElementById("headerImg").src = tmp;
    document.getElementById(id).src = before;
}