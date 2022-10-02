let mainText = document.querySelector(".main-text");

window.addEventListener('scroll', function() {
    let value = window.scrollY;
    console.log("scrollY: ", value);

    if(value > 555) {
        mainText.style.animation="disappear-slide 1s ease-out forwards";
    } else {
        mainText.style.animation="appear-slide 1s ease-out";
    }
});