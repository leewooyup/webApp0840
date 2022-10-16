let mainText = document.querySelector(".main-text");

window.addEventListener('scroll', function() {
    let value = window.scrollY;
    console.log("scrollY: ", value);

    if(value > 395) {
        mainText.style.animation="disappear-slide 1s ease-out forwards";
    } else {
        mainText.style.animation="appear-slide 1s ease-out";
    }
});

let dateText = document.querySelector(".current-date");

window.addEventListener('scroll', function() {
    let value = window.scrollY;

    if(value > 786) {
        dateText.style.animation="appear-right-slide 1s ease-out";
    } else {
        dateText.style.animation="disappear-right-slide 1s ease-out forwards";
    }
});


let minimumText = document.querySelector(".minimum-wage");

window.addEventListener('scroll', function() {
    let value = window.scrollY;

    if(value > 880) {
        minimumText.style.animation="appear-right-slide2 1s ease-out";
    } else {
        minimumText.style.animation="disappear-right-slide2 1s ease-out forwards";
    }
});