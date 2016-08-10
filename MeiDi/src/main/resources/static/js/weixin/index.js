/**
 * Created by luanpeng on 16/3/23.
 */
$(function () {

    $('.sliderbox').slick({
        azyLoad: 'ondemand',
        infinite: true,
        slidesToShow: 1,
        slidesToScroll: 1,
        dots: true,
        arrows: false,
        autoplay: true,
        autoplaySpeed: 2000,
        pauseOnHover: false
    });
    $(".searchbtn").on("click", function () {
        searchAction();
    });

    getProjectList(-2, 1, -1, '');


});

function searchAction() {
    var str = $('#searchinput').val();
    getProjectList(-2, 1, -1, str);
}

function getMore() {
    var str = $('#searchinput').val();
    getProjectList(-2, 0, -1, str);
}


