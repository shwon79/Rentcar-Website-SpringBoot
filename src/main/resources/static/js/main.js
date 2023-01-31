"use strict";
jQuery(document).ready(function ($) {

    $(window).load(function () {
        $(".loaded").fadeOut();
        $(".preloader").delay(1000).fadeOut("slow");
    });
    /*---------------------------------------------*
     * Mobile menu
     ---------------------------------------------*/
    $('.navbar-collapse').find('a[href*=#]:not([href=#])').click(function () {
        if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
            var target = $(this.hash);
            target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
            if (target.length) {
                $('html,body').animate({
                    scrollTop: (target.offset().top - 40)
                }, 1000);
                if ($('.navbar-toggle').css('display') != 'none') {
                    $(this).parents('.container').find(".navbar-toggle").trigger("click");
                }
                return false;
            }
        }
    });


// slick slider active 
    $(".main_home_slider").slick({
        dots: false,
        infinite: true,
        slidesToShow: 1,
        slidesToScroll: 1,
        prevArrow:"<i class='fa fa-angle-left nextprevleft'></i>",
        nextArrow:"<i class='fa fa-angle-right nextprevright'></i>"
    });

//    $(".study_slider").slick({
//        dots: true,
//        slidesToShow: 1,
//        slidesToScroll: 1
//    });
    $(".study_slider").slick({
        dots: true,
        arrows:false,
        slidesToShow: 1,
        slidesToScroll: 1
    });

    /*---------------------------------------------*
     * STICKY scroll
     ---------------------------------------------*/

    $("").localScroll();

    /*---------------------------------------------*
     * WOW
     ---------------------------------------------*/

    var wow = new WOW({
        mobile: false // trigger animations on mobile devices (default is true)
    });
    wow.init();


// magnificPopup

    $('.portfolio-img').magnificPopup({
        type: 'image',
        gallery: {
            enabled: true
        }
    });




//---------------------------------------------
// Counter 
//---------------------------------------------

    $('.statistic-counter').counterUp({
        delay: 10,
        time: 2000
    });

// main-menu-scroll

    jQuery(window).scroll(function () {
        var top = jQuery(document).scrollTop();
        var height = 300;
        //alert(batas);

        // if (top > height) {
        //     jQuery('.navbar-fixed-top').addClass('menu-scroll');
        // } else {
        //     jQuery('.navbar-fixed-top').removeClass('menu-scroll');
        // }
    });



// Portfoliowork init
//     jQuery('#portfoliowork').mixItUp({
//         selectors: {
//             target: '.tile',
//             filter: '.filter'
//                     //           sort: '.sort-btn'
//         },
//         animation: {
//             animateResizeContainer: false,
//             effects: 'fade scale'
//         }
//
//     });

// dropdown menu
    $('.dropdown-menu').click(function (e) {
        e.stopPropagation();
    });

    //End

});



$(document).on("scroll", function () {
    if ($(document).scrollTop() > 120) {
        $("nav").addClass("small");
    } else {
        $("nav").removeClass("small");
    }
});



// 캠핑카 렌트 서브 메뉴
$( ".btn4-default" ).click(function() {
    document.getElementById('btn-sub1').className += ' btn-clicked';
});

// camping calendar
function moveToCampingCalendar() {
    let date = new Date();
    let thisYear = date.getFullYear();
    let thisMonth = date.getMonth() + 1;
    window.location.href = '/camping/calendar/'+ thisYear + '/' + thisMonth;
}

function moveToCampingReservation(carType) {
    let date = new Date();
    let thisYear = date.getFullYear();
    let thisMonth = date.getMonth() + 1;
    window.location.href = '/camping/calendar/'+ carType + '_reserve/' + thisYear + '/' + thisMonth;
}