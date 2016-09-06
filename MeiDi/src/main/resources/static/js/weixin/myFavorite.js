/**
 * Created by luanpeng on 16/4/3.
 */

    $(function(){
        getFavoriteList(1);
    });

var pageNumber = 1;
function getFavoriteList(num) {
    if(num == 1){
        pageNumber = num;
    }
    ZENG.msgbox.loadingAnimationPath = BASE_JS_URL + "/images/loading.gif";
    ZENG.msgbox.show(" 正在加载中，请稍后...", 6, 10000);
    $.ajax({
        url: BASE_JS_URL + '/business/getMyFavorite',
        data: {
            'pageNumber': pageNumber
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if(num == 1){
                $('.itemlistbox').html('');
            }
            pageNumber = data.pageNumber + 1;
            createTable(data.commodityList);

            if(data.commodityList == null || data.commodityList.length < data.pageSize ){
                $('.getmore').hide();
            }

            ZENG.msgbox.hide(); //隐藏加载提示
        }
    });
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
        getFavoriteList(0);
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