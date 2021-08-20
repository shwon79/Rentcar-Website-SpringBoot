//jQuery to collapse the navbar on scroll
    $(window).scroll(function() {
        if ($("#navigation").offset().top > 160) {
            $(".navbar-fixed-top").addClass("header-collapse");
            // $(".navbar-fixed-top").removeClass("menu-scroll");

        } else {
            $(".navbar-fixed-top").removeClass("header-collapse");
        }
    });