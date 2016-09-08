/**
 * Created by luanpeng on 16/4/6.
 */


$(function () {
    getList(1);
});

var pageNumber = 1;
function getList(num) {
    if (num == 1) {
        pageNumber = num;
    }
    ZENG.msgbox.loadingAnimationPath = BASE_JS_URL + "/images/loading.gif";
    ZENG.msgbox.show(" 正在加载中，请稍后...", 6, 10000);
    $.ajax({
        url: BASE_JS_URL + '/business/getMyOrderList',
        data: {
            'pageNumber': pageNumber
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (num == 1) {
                $('.addList').html('');
            }
            pageNumber = data.pageNumber + 1;
            createTable(data.orderList);

            if(data.orderList == null || data.orderList.length < data.pageSize ){
                $('.getmore').hide();
            }

            ZENG.msgbox.hide(); //隐藏加载提示
        }
    });
}

function createTable(result) {

    $.each(result, function (index, order) {
        var str = '<div class="mb30 mt10 pb20" style="border-bottom: 1px solid #ddd;">' +
            '<span class="fs20 fb">订单号：' + order.order.orderCode + '</span>' +
            '<span class="fs20 fb fr">' + getTime2(order.order.createTime) + '</span> '+
            '<div class="clearfix mt10">' +
            '<div class="left fl">' +
            '<a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + order.commodity.id + '">';
        if (order.commodity.commodityPhotoList != null) {
            str += '<img src="' + IMAGE_FORMAL_URL + '/' + order.commodity.commodityPhotoList[0].imageName + '">';
        } else {
            str += '<img src="no img">';
        }

        str += '</a>' +
            '</div>' +
            '<div class="ml30 right fl fs18 pl20 mt10 line24">' +
            '<p>类别：' + getProjectFlag(order.order.flag, order.order.aloneFlag) + '  </p>' +
            '<p>金额：¥' + order.order.price/100 + '  </p>' +
            '<p>实付：¥' + order.order.payAmount/100 + '</p>' +
            '<p>状态：' + getOrderState(order.order.state) + '</p>' +
            '</div>' +
            '</div>';
            str += '<p class="fs20 mt15">' + order.order.commodityName + '</p>';
        str += '<div class="fs20 mt20 clearfix">';

        if (order.order.state < 3) {//订单正常
            if(order.launch!=null){
                if (order.launch.state == 1 || order.launch.state == 2){ //拼团进行中,可以退款
                    str += '<a href="javascript:alert(\'本次拼团已成功，请拨打400-605-6662联系客服取消订单！\');"  class="cancelorder fr ">取消订单</a>';
                }
            }else{
                str += '<a href="javascript:cancelOrder(' + order.order.id + ');"  class="cancelorder fr ">取消订单</a>';
            }
        }

        if (order.order.state == 1) {//未支付
            if(order.order.flag == 1){//拼团的订单
                str += '<a href="javascript:goPay(' + order.order + ');" class="gopay fr">去支付</a>';
            }else{
                str += '<a href="' + BASE_JS_URL + '/pay/orderPage/' + order.order.id + '" class="gopay fr">去支付</a>';
            }

        }
        if(order.order.flag == 1 && order.order.state == 2 &&  order.launch != null &&  order.launch.state == 0){//拼团订单 并且还未成功
            str += '<a href="' + BASE_JS_URL + '/business/joinGroupPage/ '+ order.launch.id + '" class="gopay fr">找人拼团</a>';

        }


        str += '</div>' +
            '</div>';
        $('.addList').append(str);
    });
}


function cancelOrder(id) {
    if (confirm("确认取消订单？", "确认", "取消")) {
        $.ajax({
            url: BASE_JS_URL + '/business/cancelOrder',
            data: {
                'orderId': id
            },
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.ret == 0) {
                    alert("您已取消订单，如果已付款，请拨打400-605-6662联系客服确认退款事宜！");
                    window.location.href = BASE_JS_URL + "/business/myOrderPage";
                } else if (data.ret == -1) {
                    alert("订单已取消");
                } else if (data.ret == -2) {
                    alert("服务器异常，请联系管理员！");
                } else if (data.ret == -3) {
                    alert("该订单拼团已成功 无法取消！");
                }
            }
        });
    }
}

function goPay(order){
    $.ajax({
        url: BASE_JS_URL + '/business/checkJoinGroup',
        data: {
            'launchId': order.launchId
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.ret == 0) {
                window.location.href = BASE_JS_URL + "/pay/orderPage/" + orderId;
            } else if (data.ret == -1) {
                //错误信息
                //alert("您已在此团中，不可重复参团！");
            } else if (data.ret == -2 || data.ret == -4) {
                alert("该拼团已结束！");
            } else if (data.ret == -3) {
                alert("该拼团人数已满！");
            }

        }
    })
}

function getProjectFlag(flag, aloneFlag) {
    switch (flag) {
        case 1:
            if (aloneFlag == 1) {
                return "拼团(一人)";
            }
            return "拼团";
        case 2:
            return "福袋";
        case 3:
            return "特惠";
        case 4:
            return "咨询";
    }
}

function getOrderState(state) {
    switch (state) {
        case 1:
            return "未支付";
        case 2:
            return "已支付";
        case 3:
            return "已预约";
        case 4:
            return "已完成";
        case 5:
            return "取消中";
        case 6:
            return "已取消(已退款)";
        case 7:
            return "已取消(不退款)";
        case 8:
            return "已取消";
        case 9:
            return "拼团失败(已退款)";
    }
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
        getList(0);
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
