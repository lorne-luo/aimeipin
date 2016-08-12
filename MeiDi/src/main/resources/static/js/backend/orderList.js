/**
 * Created by luanpeng on 16/4/17.
 */


$(function () {
    getList(1);

    $('#flag').on('change', function () {
        getList(1);
    });

    $('#state').on('change', function () {
        getList(1);
    });
});

function searchOrder() {
    getList(1);
}


var pageNumber = 1;
function getList(page) {
    if (page == 1) {
        pageNumber = page;
    }


    var flag = $('#flag').val();
    var state = $('#state').val();
    var queryStr = $('.queryStr').val();


    ZENG.msgbox.loadingAnimationPath = BASE_JS_URL + "/images/loading.gif";
    $("#pagediv").myPagination({
        cssStyle: 'scott',
        currPage: pageNumber,
        ajax: {
            on: true,
            url: BASE_JS_URL + "/backend/getOrderList",
            dataType: 'json',
            param: {
                flag: flag,
                state: state,
                queryStr: queryStr
            },
            ajaxStart: function () {
                ZENG.msgbox.show(" 正在加载中，请稍后...", 6, 10000);
            },
            callback: function (data) {
                ZENG.msgbox.hide(); //隐藏加载提示
                pageNumber = data.pageNumber;
                createTable(data.orderList);
            }
        }
    });
}

function createTable(result) {
    $('#addList').html('');
    $.each(result, function (index, order) {
        var str = '';
        if (index == 0) {
            str += '<tr class="active">';
        } else if (index == 2) {
            str += '<tr class="success">';
        } else if (index == 4) {
            str += '<tr class="info">';
        } else if (index == 6) {
            str += '<tr class="warning">';
        } else if (index == 8) {
            str += '<tr class="danger">';
        } else {
            str += '<tr>';
        }

        str += '<td>' + order.order.orderCode + '</td>' +
            '<td>' + getProjectFlag(order.order) + '</td>' +
            '<td>' + order.order.commodityName + '</span></td>' +
            '<td>' + order.order.discountPrice/100 + '元</td>' +
            '<td>' + order.order.payAmount/100 + '元</td>' +
            '<td>' + order.order.username + '<br>' + order.order.mobile;
        if (order.order.weixin != null && order.order.weixin != '') {
            str += '<br>' + order.order.weixin;
        }

        str += '</td>';
        if (order.order.bookingTime != null) {
            str += '<td id="bookingTime_' + order.order.id + '">' + getTimeMMDDhhmm(order.order.bookingTime) + '</td>';
        } else {
            str += '<td>暂无</td>';
        }

        str += '<td>' + getTimeMMDDhhmm(order.order.createTime) + '</td>' +
            '<td>' + getOrderState(order.order.state);
        if (order.launch != null) {
            str += '<br>' + getLaunchState(order.launch.state);
        }
        str += '</td>' +
            '<td id="remarks_' + order.order.id + '">';
        if (order.order.remarks != null && order.order.remarks != '' && order.order.remarks.length > 0) {
            str += order.order.remarks;
        } else {
            str += "暂无";
        }

        str += '</td>' +
            '<td>';

        if (order.order.state == 3) {
            str += '<a href="javascript:integral(' + order.order.id + ');" class="btn btn-success">订单完成加积分</a>';
            str += '<a href="javascript:closeOrder(' + order.order.id + ',7);" class="btn btn-success">取消订单(过期)</a>';
        } else if (order.order.state == 5) {
            str += '<a href="javascript:closeOrder(' + order.order.id + ',6);" class="btn btn-success">退款</a>';
            str += '<a href="javascript:closeOrder(' + order.order.id + ',7);" class="btn btn-success">不退款</a>';
        }
        if (order.order.state == 2 || order.order.state == 3) {
            str += '<a href="javascript:bookingTime(' + order.order.id + ');" class="btn btn-success">预约时间</a>';
        }
        str += '<a href="javascript:remarks(' + order.order.id + ');" class="btn btn-success">备注</a>';
        str += '</td>' +
            '</tr>';
        $('#addList').append(str);
    });
}

var dialog = new Dialog({
    dialogtext: "<div class='pr bgf6'><p class='p10 tal bgWhite fs20 p555'>添加备注</p><span class='close'></span><div class='pt40 tac'><input type='text' class='myform-control w340 p10 fs20 remarks'><div class='tac pb30  mt60'><a href='javascript:submitRemarks();' class='dsavelp btn btn-success'>提交</a></div></div>",
    hasDialog: true,
    dialogClass: "erroralert",
    markColor: "rgba(0,0,0,0.5)"
});

var orderId = 0;
function remarks(id) {
    var str = $('#remarks_' + id).text();
    if (str != '暂无') {
        $('.remarks').val(str);
    }

    dialog.show();
    orderId = id;
}

function submitRemarks() {
    var remarks = $('.remarks').val();
    $.ajax({
        url: BASE_JS_URL + "/backend/remark",
        type: "post",
        data: {
            orderId: orderId,
            remarks: remarks
        },
        dataType: "json",
        success: function (data) {
            dialog.close();
            $('#remarks_' + orderId).text(remarks);
            $('.remarks').val('');
        }
    })
}

var dialog2 = new Dialog({
    dialogtext: "<div class='pr bgf6'><p class='p10 tal bgWhite fs20 p555'>预约时间</p><span class='close'></span><div class='pt40 tac'><input type='text' class='myform-control w340 p10 fs20 bookingTime'><div class='tac pb30  mt60'><a href='javascript:submitBookingTime();' class='dsavelp btn btn-success'>提交</a></div></div>",
    hasDialog: true,
    dialogClass: "erroralert",
    markColor: "rgba(0,0,0,0.5)"
});

function bookingTime(id) {
    var str = $('#bookingTime_' + id).text();
    if (str != '暂无') {
        $('.bookingTime').val(str);
    }
    dialog2.show();
    orderId = id;
    $('.bookingTime').mobiscroll().calendar({
        theme: 'default',
        lang: 'zh',
        display: 'bubble',
        animate: 'flip',
        minDate: new Date(),
        dateFormat: "yy-mm-dd",
        controls: ['calendar', 'time'],
        mode: 'mixed'
    });
}

function submitBookingTime() {
    var bookingTimeStr = $('.bookingTime').val();
    if (bookingTimeStr == '') {
        dialog2.close();
        return;
    }
    $.ajax({
        url: BASE_JS_URL + "/backend/bookingTime",
        type: "post",
        data: {
            orderId: orderId,
            bookingTimeStr: bookingTimeStr
        },
        dataType: "json",
        success: function (data) {
            dialog2.close();
            getList(pageNumber);
            $('.bookingTime').val('');
            //$('#bookingTime_' + orderId).text(getTime(data.bookingTime));
        }
    })
}


function closeOrder(orderId, state) {
    $.ajax({
        url: BASE_JS_URL + '/backend/closeOrder',
        data: {
            'orderId': orderId,
            'state': state
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.ret == 0) {
                alert("操作成功！");
                getList(pageNumber);
            } else {
                alert("操作失败，请稍后再试！");
            }
        }
    });
}


var dialog3 = new Dialog({
    dialogtext: "<div class='pr bgf6'><p class='p10 tal bgWhite fs20 p555'>加积分</p><span class='close'></span><div class='pt40 tac'><input type='text' class='myform-control w340 p10 fs20 integral'><div class='tac pb30  mt60'><a href='javascript:addIntegral();' class='dsavelp btn btn-success'>提交</a></div></div>",
    hasDialog: true,
    dialogClass: "erroralert",
    markColor: "rgba(0,0,0,0.5)"
});

function integral(id) {
    orderId = id;
    dialog3.show();
}

function addIntegral() {
    var integral = $('.integral').val();
    if (integral == '') {
        dialog3.close();
        return;
    }
    $.ajax({
        url: BASE_JS_URL + '/backend/addIntegral',
        data: {
            'integral': integral,
            'orderId': orderId
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            dialog3.close();
            $('.integral').val('');
            getList(pageNumber);

        }
    });
}


function getProjectFlag(order) {
    var flag = order.flag;
    var id = order.launchId;
    if(id == null){
        id = 0;
    }
    switch (flag) {
        case 1:
            return "拼团(" + id+ ")";
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
            return "待支付";
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
            return "已取消(未付款)";
        case 9:
            return "已取消(已退款)";
    }
}

function getLaunchState(state) {
    switch (state) {
        case 0:
            return "拼团中";
        case 1:
            return "拼团成功";
        case 2:
            return "拼团失败";
        case 3:
            return "拼团失败";
    }
}
