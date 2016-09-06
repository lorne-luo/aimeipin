/**
 * Created by luanpeng on 16/4/17.
 */


$(function () {
    getList(1);

    $('#flag').on('change', function () {
        if ($('#flag').val()>0)
            addFilterCondition('flag', $('#flag option:selected').text());
        else
            removeFilterCondition('flag', false);
        getList(1);
    });

    $('#state').on('change', function () {
        if ($('#state').val()>0)
            addFilterCondition('state', $('#state option:selected').text());
        else
            removeFilterCondition('state', false);
        getList(1);
    });

    $("input.queryStr").keyup(function (e) {
        if ($("input.queryStr:focus") &&e.keyCode == 13) {
            searchOrder();
        }
    });

});

function searchOrder() {
    addFilterCondition('search','搜索: '+$('.queryStr').val());
    removeFilterCondition('launch_id', false);
    removeFilterCondition('commodity_id', false);
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
    var launchID = $('#search_launch_id').val();
    var commodityID = $('#search_commodity_id').val();

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
                launchID:launchID,
                commodityID:commodityID,
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

function getListByCommodity(commodityID,commodityName) {
    $('#search_commodity_id').val(commodityID);
    addFilterCondition('commodity_id',commodityName);
    removeFilterCondition('launch_id', false);
    removeFilterCondition('search', false);

    getList(1);
}

function getListByLaunch(launchID) {
    $('#search_launch_id').val(launchID);
    addFilterCondition('launch_id','拼团('+launchID+')');
    removeFilterCondition('commodity_id', false);
    removeFilterCondition('search', false);

    getList(1);
}

function removeFilterCondition(type, refresh) {
    $('span.'+type,'#filter-panel').remove();
    if(type=='launch_id')
        $('#search_launch_id').val('0');
    else if(type=='commodity_id')
        $('#search_commodity_id').val('0');
    else if(type=='search')
        $('#queryStr').val('');
    else if(type=='flag')
        $('#flag').val('-1');
    else if(type=='state')
        $('#state').val('-1');
    if(refresh)
        getList(1);
}

function addFilterCondition(type,text) {
    $('span.'+type,'#filter-panel').remove();
    var item='<span class="filter-item '+type+'">'+text+' <a href="javascript:removeFilterCondition(\''+type+'\',true);"><i class="fa fa-times-circle" aria-hidden="true"></i></a></span>';
    $('#filter-panel').append(item);
}


function createTable(result) {
    $('#addList').html('');
    $.each(result, function (index, order) {
        var str = index % 2 ? '<tr class="active">' : '<tr>';

        var nickname = '';
        if (order.user && order.user.nickname) {
            nickname = order.user.nickname
        }

        str += '<td>' + order.order.orderCode + '</td>' +
            '<td>' + getProjectFlag(order.order);
        if (order.launch != null) {
            str += '<br>' + getLaunchState(order.launch.state);
        }
        str += '</td>' +
            '<td class="tal">' + order.order.commodityName + ' <a href="javascript:getListByCommodity('+order.order.commodityId+', \''+order.order.commodityName+'\');"><i class="fa fa-filter" aria-hidden="true"></i></a></td>' +
            '<td>' + order.order.discountPrice/100 + '元</td>' +
            '<td>' + order.order.payAmount/100 + '元</td>' +
            '<td>' + nickname;
        if (order.order.weixin != null && order.order.weixin != '') {
            str += '<br>@' + order.order.weixin;
        }
        str += '</td>'+
               '<td>' + order.order.username + '<br>' + order.order.mobile+ '</td>';

        str += '<td>' + getTimeMMDDhhmm(order.order.createTime) + '</td>' +
            '<td>' + getOrderState(order.order.state);

        str += '</td>' +
            '<td style="max-width:130px;word-wrap: break-word;" id="remarks_' + order.order.id + '">';
        if (order.order.remarks != null && order.order.remarks != '' && order.order.remarks.length > 0) {
            str += '<div class="text-left">'+order.order.remarks;
            str += ' <a href="javascript:remarks(' + order.order.id + ');" class="text-primary"><i class="fa fa-pencil-square" aria-hidden="true"></i></a></div>';
        } else {
            str += '<a href="javascript:remarks(' + order.order.id + ');" class="text-primary"><i class="fa fa-plus-circle fa-2x" aria-hidden="true"></i></a>';
        }

        // str += '</td><td>';
        //
        // if (order.order.state == 1) {
        //     str += '<a href="javascript:closeOrder(' + order.order.id + ',6);" class="btn btn-warning">取消</a>';
        // // } else if (order.order.state == 3) {
        // //     str += '<a href="javascript:integral(' + order.order.id + ');" class="btn btn-success">订单完成加积分</a>';
        // //     str += '<a href="javascript:closeOrder(' + order.order.id + ',7);" class="btn btn-success">取消订单(过期)</a>';
        // } else if (order.order.state == 5) {
        //     str += '<a href="javascript:closeOrder(' + order.order.id + ',6);" class="btn btn-danger">退款</a>';
        //     str += ' <a href="javascript:closeOrder(' + order.order.id + ',7);" class="btn btn-danger">不退款</a>';
        // }else if (order.order.state == 2) {
        //     str += '<a href="javascript:integral(' + order.order.id + ');" class="btn btn-success">完成</a>';
        //     str += ' <a href="javascript:closeOrder(' + order.order.id + ',6);" class="btn btn-danger">退款</a>';
        // }
        // str += '</td></tr>';
        str += '</td>' +
            '<td>';

        if (order.order.state == 3) {
            str += '<a href="javascript:integral(' + order.order.id + ');" class="btn btn-success">订单完成加积分</a> ';
            str += '<a href="javascript:closeOrder(' + order.order.id + ',7);" class="btn btn-success">取消订单(过期)</a> ';
        } else if (order.order.state == 5) {
            str += '<a href="javascript:closeOrder(' + order.order.id + ',6);" class="btn btn-success">退款</a> ';
            str += '<a href="javascript:closeOrder(' + order.order.id + ',7);" class="btn btn-success">不退款</a> ';
        }
        if (order.order.state == 2 || order.order.state == 3) {
            str += '<a href="javascript:integral(' + order.order.id + ');" class="btn btn-success">完成</a> ';
            str += ' <a href="javascript:confirmCloseOrder(' + order.order.id + ',6);" class="btn btn-danger">取消</a> ';
        }
        str += '</td>' +
            '</tr>';
        $('#addList').append(str);
    });
}

function confirmCloseOrder(orderId,state){
    var result = confirm('确定取消此订单?');
    if(result){
        closeOrder(orderId,state);
    }
}

var dialog = new Dialog({
    dialogtext: "<div class='pr bgf6'><p class='p10 tal bgWhite fs20 p555'>添加备注</p><span class='close'></span><div class='pt40 tac'><input type='text' class='myform-control w340 p10 fs20 remarks'><div class='tac pb30  mt60'><a href='javascript:submitRemarks();' class='btn btn-success'>提交</a></div></div>",
    hasDialog: true,
    dialogClass: "erroralert",
    markColor: "rgba(0,0,0,0.5)"
});

var orderId = 0;
function remarks(id) {
    var str = $('#remarks_' + id).text();
    if (str != '暂无') {
        $('.remarks').val($.trim(str));
    }

    dialog.show();
    $('input.remarks').focus();
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
            if (remarks){
                console.log(remarks);
                $('#remarks_' + orderId).html('<div class="text-left">'+remarks+' <a href="javascript:remarks(' + orderId + ');" class="text-primary"><i class="fa fa-pencil-square" aria-hidden="true"></i></a></div>');
            }else{
                console.log('null');
                $('#remarks_' + orderId).html('<a href="javascript:remarks(' + orderId + ');" class="text-primary"><i class="fa fa-plus-circle fa-2x" aria-hidden="true"></i></a>');
            }
            $('.remarks').val('');
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
    dialogtext: "<div class='pr bgf6'><p class='p10 tal bgWhite fs20 p555'>加积分</p><span class='close'></span><div class='pt40 tac'><input type='text' class='myform-control w340 p10 fs20 integral'><div class='tac pb30  mt60'><a href='javascript:addIntegral();' class='btn btn-success'>提交</a></div></div>",
    hasDialog: true,
    dialogClass: "erroralert",
    markColor: "rgba(0,0,0,0.5)"
});

function integral(id) {
    orderId = id;
    dialog3.show();
    $('input.integral').focus();
}

function addIntegral() {
    var integral = $('.integral').val();
    if (integral == '') {
        dialog3.close();
        return;
    }
    //order.state将变更为4已完成
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
    var groupName=order.bookingFlag==1?'团长':'拼团';
    
    switch (flag) {
        case 1:
            return groupName+"(" + id+ ")"+' <a href="javascript:getListByLaunch('+id+');"><i class="fa fa-filter" aria-hidden="true"></i></a>';
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
            return "支付失败";
        case 4:
            return "已完成";
        case 5:
            return "取消中(待退款)";
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
