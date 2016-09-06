/**
 * Created by luanpeng on 16/3/23.
 */

var flag = 1;
var page = 1;
var cityId = -1;
$(function () {

    getProjectList(1, 1,-1, '');

});

/**
 *
 * @param categoryId 项目分类 －1 查所有
 * @param pageNumber 页码 1 表示第一页 0 表示继续查询
 * @param city 地区 －1 查所有, －2 查非韩国所有
 */
function setProjectFlagAndGetProject(categoryId, city, pageNumber, tag) {
    $('.t' + tag).parent().find('.active').removeClass('active');
    $('.t' + tag).addClass('active');

    cityId = city;
    getProjectList(categoryId, pageNumber, cityId,'');

    $('.getmore').show();

}

function getMore(){
    getProjectList(flag, 0,cityId, '');
}


// mobiscroll 底端下拉加载
$.mobiscroll.setDefaults({
    theme: 'mobiscroll'
});

$(function () {

    var i,
        li,
        inst,
        entry,
        timer,
        feed,
        $wnd = $(window),
        $getmore = $('#getmore'),
        $loadingCont = $('#getmore-loading'),
        $loadingIc = $loadingCont.find('.mbsc-btn-ic'),
        isLoading = false,
        feedNum = 10;
    function getFeed() {
        $loadingIc.show();
        getMore();
        isLoading = false;
        $loadingIc.hide();
    }

    function shouldLoad() {
        return $loadingCont.offset().top + $loadingCont.outerHeight() - 1 < $wnd.scrollTop() + $wnd.outerHeight();
    }

    inst = $getmore.mobiscroll().listview({
        altRow: true,
        animation: false,
        swipe: false,
        theme: 'mobiscroll'
    }).mobiscroll('getInst');

    $wnd.on('scroll touchmove', function () {
        clearTimeout(timer);
        timer = setTimeout(function () {
            if (!isLoading && shouldLoad()) {
                isLoading = true;
                feedNum += 10;
                getFeed();
            }
        }, 250);
    });

    // Make links work inside listview on touchscreens
    $getmore.on('touchstart', 'a', function (ev) {
        ev.stopPropagation()
    });
});