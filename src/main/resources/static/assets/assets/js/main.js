$('.slider-wrapper').owlCarousel({
    loop: true,
    margin: 0,
    nav: false,
    dots: false,
    autoplay: true,
    autoplayTimeout: 5000,
    animateOut: 'fadeOut',
    responsive: {
        0: {
            items: 1
        },
        600: {
            items: 1
        },
        1000: {
            items: 1
        }
    }
});


// for date in header 
var NowMoment = moment();
var eDisplayMoment = document.getElementById('displayDate');
eDisplayMoment.innerHTML = NowMoment.format('D-MMMM-YYYY');