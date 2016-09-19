<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="keywords" content="北"/>
    <meta name="description" content=""/>

    <title>聚会美商城-我的积分</title>
    <meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<#include "header.ftl"/>
</head>
<body style="background:#ffffff">
<div class="wrapper pr pb50 tal">
    <div class="header pwhite bg  bor_b ">
        <p class="fs18">我的积分</p>
    </div>
    <div class="myjf p20">
        <div class="fs20">
            <p class="p878787 line30 mt10">该积分，等同您在聚会美预约项目并消费的实际金额，可用于享受多种积分福利。详情请移步“积分商城”</p>
        </div>
        <div class="fs28 mt10" style="margin-bottom: 60px">
            <p>总积分：<span class="pf40000">#{user.integral}分</span></p>

            <table class="jfbox fs20">
                <thead>
                    <th class="tac" style="width:85px">日期</th>
                    <th class="tac">事件</th>
                    <th class="tac" style="width:85px">积分增减</th>
                </thead>
                <tbody class="tableList">

                </tbody>
            </table>
            <ul id="getmore" class="md-news hide" style="display:none" mbsc-enhance></ul>
            <div id="getmore-loading" class="tac mbsc-btn-ic"  style="display:none" mbsc-enhance>
                <span class="getmore">加载中...</span>
            </div>
        </div>
    </div>

</div>

<#include "footer.ftl"/>

</body>
<script src="${PATH}/js/weixin/myIntegral.js"></script>
</html>