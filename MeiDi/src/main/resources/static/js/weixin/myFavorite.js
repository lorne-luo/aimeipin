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
