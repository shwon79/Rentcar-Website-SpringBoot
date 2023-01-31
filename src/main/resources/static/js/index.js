// scroll Up
$(window).scroll(function () {
    if ($(this).scrollTop() > 600) {
        $('.scrollup').fadeIn('slow');
    } else {
        $('.scrollup').fadeOut('slow');
    }
});

$('.scrollup').click(function () {
    $("html, body").animate({scrollTop: 0}, 1000);
    return false;
});


// scrool Down
$('.scrooldown a').bind('click', function () {
    $('html , body').stop().animate({
        scrollTop: $($(this).attr('href')).offset().top - 160
    }, 1500, 'easeInOutExpo');
    event.preventDefault();
});