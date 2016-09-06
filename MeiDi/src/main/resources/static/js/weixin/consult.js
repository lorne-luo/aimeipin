/**
 * Created by luanpeng on 16/4/23.
 */
$(function () {
    getProjectList(4,1,-1,'');
})

function getMore(){
    getProjectList(4, 0,-1, '');
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
