/**
 * Created by luanpeng on 16/3/31.
 */


var dialog = new Dialog({
    dialogtext: "<p class='pwhite errorp close fs28 p20'>该账户或密码不正确，<br/>请重新输入！</p>",
    hasDialog: true,
    dialogClass: "erroralert",
    markColor: "rgba(0,0,0,0)",
    topDialog: "458px"
});

$(".cgtotalinput").data("val", 1);

var submitFlag = 0;
function submitOrder(flag) {
    if(submitFlag == 1){
        return;
    }else{
        submitFlag = 1;
    }
    var commodityId = $('.commodityId').val();
    var groupLaunchId = $('.groupLaunchId').val();
    var username = $('#username').val();
    var mobile = $('#mobile').val();
    var reservationCount = $('.reservationCount').val();
    if(reservationCount == null || reservationCount == '' || !reservationCount){
        reservationCount = 0;
    }
    var remarks = $('#remarks').val();

    $.ajax({
        url: BASE_JS_URL + '/business/createOrder',
        data: {
            'commodityId': commodityId,
            'username': username,
            'mobile': mobile,
            'reservationCount': reservationCount,
            'flag': flag,
            'launchId': groupLaunchId,
            'remarks': remarks,
            'weixin':''
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {

            if (data.ret == 0) {
                window.location.href = BASE_JS_URL + "/pay/orderPage/" + data.orderId;
            }  else if(data.ret == -2){
                alert("姓名错误");
                submitFlag = 0;
            } else if(data.ret == -3){
                alert("手机号错误");
                submitFlag = 0;
            } else if(data.ret == -4){
                alert("数量错误");
                submitFlag = 0;
            } else if(data.ret == -5){
                alert("该商品库存不足");
                submitFlag = 0;
                //错误信息
            }else{

            }
        }
    });
}

