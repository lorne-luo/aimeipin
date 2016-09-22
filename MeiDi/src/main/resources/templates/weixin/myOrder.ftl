<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="keywords" content="北"/>
    <meta name="description" content=""/>

    <title>聚会美商城-我的订单</title>
    <meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<#include "header.ftl"/>
</head>
<body style="background:#ffffff">
<div class="wrapper pr pb50 tal">
    <div class="header pwhite bg  bor_b ">
        <a class="fs14 pa city iconfont icon-back base" href="${PATH}/my"></a>
        <p class="fs18">全部订单</p>
    </div>
    <div class="myorder p20">
        <div class="addList">

        </div>

        <ul id="getmore" class="md-news hide" style="display:none" mbsc-enhance></ul>
        <div id="getmore-loading" class="tac mbsc-btn-ic"  style="display:none" mbsc-enhance>
            <span class="getmore">加载中...</span>
        </div>
    </div>

</div>
<#include "footer.ftl"/>
</body>
</html>
<script type="text/javascript" src="${PATH}/js/weixin/myOrder.js?v=${version}"></script>