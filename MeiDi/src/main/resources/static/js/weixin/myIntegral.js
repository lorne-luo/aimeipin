/**
 * Created by luanpeng on 16/5/17.
 */
$(function () {
    getList(1);
})

var pageNumber = 1;
function getList(page) {
    if (page == 1) {
        pageNumber = page;
    }
    ZENG.msgbox.loadingAnimationPath = BASE_JS_URL + "/images/loading.gif";
    ZENG.msgbox.show(" 正在加载中，请稍后...", 6, 10000);
    $.ajax({
        url: BASE_JS_URL + "/business/getMyIntegralList",
        type: "post",
        dataType: "json",
        data: {
            pageNumber: pageNumber
        },
        success: function (data) {

            pageNumber = data.pageNumber + 1;
            createTable(data.result);

            if (data.result == null || data.result.length < data.pageSize) {
                $('.getmore').hide();
            }

            ZENG.msgbox.hide(); //隐藏加载提示
        }

    })
}

function createTable(result) {
    $.each(result, function (index, integral) {
        var integral_change = integral.userIntegral.integral > 0 ? "+" + integral.userIntegral.integral : integral.userIntegral.integral;
        var table_content = '<tr class="line24">' +
            '<td class="tac">' + getDateYYMMDD(integral.userIntegral.createTime) + '</td>' +
            '<td>' + integral.commodityName + '</td>' +
            '<td class="pf40000 tac">' + integral_change + '</td>' +
            '</tr>';

        $('.tableList').append(table_content);
    })

}
