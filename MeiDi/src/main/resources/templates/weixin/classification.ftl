<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="keywords" content="北"/>
    <meta name="description" content=""/>

    <title>分类</title>
    <meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<#include "header.ftl"/>
</head>
<body style="background:#ffffff">
<div class="wrapper pr pb130">
    <div class="pl20 pr20" style="margin-bottom: -20px">
        <div class="classifybox">
            <a href="javascript:setProjectFlagAndGetProject(1,-1,1,1);" class="t1 active"></a>
            <a href="javascript:setProjectFlagAndGetProject(2,-1,1,2);" class="t2"></a>
            <a href="javascript:setProjectFlagAndGetProject(3,-1,1,3);" class="t3"></a>
            <a href="javascript:setProjectFlagAndGetProject(4,-1,1,4);" class="t4"></a>
        </div>
        <div class="itemlistbox">

        </div>
    </div>

    <ul id="getmore" class="md-news hide" style="display:none" mbsc-enhance></ul>
    <div id="getmore-loading" class="mbsc-btn-ic"  style="display:none" mbsc-enhance>
        <span href="javascript:getMore();" class="getmore">加载中...</span>
    </div>
</div>
<#include "footer.ftl"/>
</body>
</html>
<script type="text/javascript" src="${PATH}/js/weixin/common-ajax.js"></script>
<script type="text/javascript" src="${PATH}/js/weixin/classification.js"></script>