/**
 * Created by luanpeng on 16/4/3.
 */

function cancelOrder(id){
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
                    alert("您已取消订单，如果已付款，请拨打010-84466106联系客服确认退款事宜！");
                    window.location.href =BASE_JS_URL + "/pay/orderPage/" + id;
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


