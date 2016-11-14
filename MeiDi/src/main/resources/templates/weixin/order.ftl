<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="keywords" content="北"/>
    <meta name="description" content=""/>

    <title>聚会美商城</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<#include "header.ftl"/>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script>
        <#if signature?exists>
        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: '${signature.appid}', // 必填，公众号的唯一标识
            timestamp: '${signature.timestamp}', // 必填，生成签名的时间戳
            nonceStr: '${signature.noncestr}', // 必填，生成签名的随机串
            signature: '${signature.signature}',// 必填，签名，见附录1
            jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
        </#if>
        function goPay(launchId) {

            if (launchId != 0) {
                $.ajax({
                    url: BASE_JS_URL + '/business/checkJoinGroup',
                    data: {
                        'launchId': launchId
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if (data.ret == 0) {
                            goPayOK();
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
            } else {
                goPayOK();
            }

        }
        function goPayOK() {
        <#if wxPaySignature?exists>
            // 调微信支付接口
            wx.chooseWXPay({
                timestamp: '${wxPaySignature.timestamp}', // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                nonceStr: '${wxPaySignature.nonceStr}', // 支付签名随机串，不长于 32 位
                package: '${wxPaySignature.package}', // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
                signType: '${wxPaySignature.signType}', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
                paySign: '${wxPaySignature.paySign}', // 支付签名
                success: function (res) {
                    // 支付成功后的回调函数

                    window.location.href = BASE_JS_URL + "/pay/payResult/${order.orderCode}";

                }
            });
        </#if>
        }

        <#--function goPay() {//本地测试-->

        <#--$.ajax({-->
        <#--url: BASE_JS_URL + '/payTest/notifyUrlTest',-->
        <#--data: {-->
        <#--'orderCode': '${order.orderCode}'-->
        <#--},-->
        <#--type: 'post',-->
        <#--dataType: 'json',-->
        <#--success: function (data) {-->

        <#--window.location.href = BASE_JS_URL + "/payTest/payResultTest/${order.orderCode}";-->

        <#--}-->
        <#--});-->
        <#--}-->
    </script>
</head>
<body style="background:#ffffff">
<script type='text/javascript'>
    (function (m, ei, q, i, a, j, s) {
        m[a] = m[a] || function () {
                    (m[a].a = m[a].a || []).push(arguments)
                };
        j = ei.createElement(q),
                s = ei.getElementsByTagName(q)[0];
        j.async = true;
        j.charset = 'UTF-8';
        j.src = i + '?v=' + new Date().getUTCDate();
        s.parentNode.insertBefore(j, s);

    })(window, document, 'script', '//static.meiqia.com/dist/meiqia.js', '_MEIQIA');
    _MEIQIA('entId', 2247);
    //无按钮模式
    _MEIQIA('withoutBtn', true);
</script>
<div class="wrapper pr pb80">
    <div class="clearfix cancelboxtop">
        <div class="fl">
            <img src="http://s.luotao.net/static/aimeipin/cancel/s.png">
        </div>
        <div class="fr pf40000 fs14 mt20">
        <#if order.state == 1>
            <p>订单已生成,待支付...</p>
        <#elseif order.state == 2>
            <p>订单已支付...</p>
        <#elseif order.state == 3>
            <p>订单已预约...</p>
        <#elseif order.state == 4>
            <p>订单已完成...</p>
        <#elseif order.state == 5>
            <p>订单取消中...</p>
        <#elseif order.state == 6 || order.state == 7 || order.state == 8 || order.state == 9>
            <p>订单已取消...</p>
        </#if>

        </div>
    </div>
<#if commodity?exists && commodity.commodityPhotoList?exists>
    <div class="pl10 pr10">
        <div class="cancelboximg borall">
            <#list commodity.commodityPhotoList as photo>
                <#if photo_index == 0>
                    <img src="${IMAGE_FORMAL_URL}/${photo.imageName}">
                </#if>
            </#list>
        </div>
    </div>
<#elseif ask?exists && ask.imageName?exists>
    <div class="pl10 pr10">
        <div class="cancelboximg borall">
            <img src="${IMAGE_FORMAL_URL}/${ask.imageName}">
        </div>
    </div>
</#if>

    <div class="pl10 pr24 tal mt6">
        <p class="pf40000 fs16 ml10">
            <#if order.flag == 5>
                ¥#{order.price/100}
            <#else>
                ¥#{order.discountPrice/100}
            </#if>
        </p>
        <p class="fs18 ml10 pt6">${order.commodityName}</p>
    </div>
    <div class="tal orderdetail pt10 pb10 mt10 mb10 ml20 mr20">
        <div class="pl24 line24 fs14">
            <p>订单编号：${order.orderCode}</p>
            <p>支付方式：微信支付</p>
            <p>姓  名：${order.username}</p>
            <p>电  话：${order.mobile}</p>
            <p>微  信：${order.weixin}</p>
            <p>备  注：${order.remarks}</p>
            <p>下单时间：${order.createTime}</p>
        </div>
    </div>
    <div class="pr28 fs14 clearfix mr30">
    <#if order.flag == 4>
        <p class="fr">订单合计:<span class="pf40000">#{order.payAmount/100}</span></p>
    <#else>
        <p class="fr">共<span class="pf40000">#{order.commodityNumber}</span>件，订金合计:<span
                class="pf40000"><#if order.flag==4>#{order}</#if>#{order.payAmount/100}</span></p>
    </#if>

    </div>
    <div class="chatbox tac fs14">
        <a href="javascript:void(0);" class="mr20" onclick="_MEIQIA._SHOWPANEL()"><img
                src="http://s.luotao.net/static/aimeipin/cancel/h.png">在线咨询</a>
        <a href="tel:4006056662"><img src="http://s.luotao.net/static/aimeipin/cancel/t.png">拨打电话</a>
    </div>

    <div class=" btfix  clearfix cancelbtnbox">
    <#if order.state ==1 >
        <#if order.launchId?exists>
            <a onclick="return goPay(#{order.launchId});return false;" class="fr gopay">去付款></a>
        <#else>
            <a onclick="return goPay(0);return false;" class="fr gopay">去付款></a>
        </#if>

    </#if>
        <a href="${PATH}/index" class="fr cancel">返回首页</a>
    <#if order.state == 1 || order.state == 2 || order.state == 3>
        <a href="javascript:cancelOrder(#{order.id});" class="fr cancel">取消订单</a>
    <#else>
        <a href="${PATH}/business/myOrderPage" class="fr cancel">我的订单</a>
    </#if>

    </div>
</div>

</body>
<script src="${PATH}/js/weixin/order.js?v=${version}"></script>
</html>
