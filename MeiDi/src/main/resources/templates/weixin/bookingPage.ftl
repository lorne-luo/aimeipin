<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="keywords" content="北"/>
    <meta name="description" content=""/>

    <title>购买</title>
    <meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<#include "header.ftl"/>


</head>
<body style="background:#ffffff">
<div class="wrapper pr pt10">

    <div class="cancelboximg borall ">
    <#if commodity.commodityPhotoList?exists>
        <#list commodity.commodityPhotoList as photo>
            <img src="${IMAGE_FORMAL_URL}/${photo.imageName}">
        </#list>
    </#if>
    </div>
    <div class="pl24  pr24 tal mt10">
        <p class="pf40000 fs28 ml4">¥#{commodity.discountPrice/100}</p>

        <p class="fs20 mlf6 pt10">${commodity.name}</p>
    </div>
    <form action="" id="addOrder" method="post">
        <input type="hidden" value="#{commodity.id}" name="commodityId" class="commodityId">

        <div class="tal  pt10 pb10 pl24 pr24 sign fs24">
            <div class="clearfix mt10">
                <span class="fl t1 mt10">姓名：</span>
                <input type="text" placeholder="请填写真实姓名" name="username" id="username">
            </div>
            <div class="clearfix mt10">
                <span class="fl mt10">手机号码：</span>
                <input type="tel" placeholder="请务必填写正确的手机号码" name="mobile" id="mobile">
            </div>
            <div class="clearfix mt10">
                <span class="fl mt10">微信号：</span>
                <input type="text" placeholder="选填" name="weixin" id="weixin">
            </div>
        <#if commodity.flag == 4>

        <#else>
            <div class="clearfix mt10">
                <span class="fl mt20">预定数量：</span>

                <p class="mpbox clearfix">
                    <span class="cgminus  fs20 fl "></span>
                    <span class=" fs14 tac fl">
                        <input type="text" value="1" name="reservationCount" readonly
                               class="cgtotalinput nobg tac p999 fs20 reservationCount">
                    </span>
                    <span class="cgplus can  fs18 fl"></span>
                </p>
            </div>
            <div class="clearfix mt10">
                <span class="fl mt10">支付订金：</span>
                <span class="fr mt10 pf40000 fs24 totalDeposit" style="height:41px"
                      value="#{commodity.deposit/100}">¥#{commodity.deposit/100}</span>
            </div>
        </#if>
            <div class="clearfix mt10">
                <span class="fl t1 mt10">备注：</span>
                <input type="text" placeholder="备注留言" name="remarks" id="remarks">
            </div>
        </div>
        <div class="goupaybox">
            <a href="javascript:submitOrder(${flag});" id="goupay"></a>
        </div>
    </form>

    <div class="earnestills tal fs16 line30 p555">
        <b class="fs20">支付说明：</b>
    <#if commodity.remarks?exists && commodity.remarks!='' >
        ${commodity.remarks}
    <#else>
        <p class="pt10">·预约项目到院支付全款后，系统退还订金；</p>

        <p>·福袋可随时取消订单，系统退还订金；</p>

        <p>·拼团成功前，可随时取消订单，系统退还订金；</p>

        <p>·拼团成功后，因个人原因取消订单，订金不予退还；</p>

        <p>·已接受聚会美线下服务（面诊、接送机等），但未到院消费，订金不予退还。</p>
    </#if>
    </div>
</div>

</body>
</html>
<script type="text/javascript" src="${PATH}/js/weixin/bookingPage.js"></script>
