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
    <meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<#include "../header.ftl"/>


</head>
<body style="background:#ffffff">
<div class="wrapper pr pt10">

    <div class="cancelboximg borall ">
        <img src="http://s.luotao.net/static/aimeipin/index/1.jpg">
    </div>
    <div class="pl24  pr24 tal mt10">
        <p class="pf40000 fs28 ml4">¥#{ask.discountPrice}</p>

        <p class="fs20 mlf6 pt10">${ask.description}</p>
    </div>
    <form action=""  id="addOrder" method="post">
        <input type="hidden" value="#{ask.id}" name="projectId" class="askId">
        <div class="tal  pt10 pb10 pl24 pr24 sign fs24">
            <div class="clearfix mt10">
                <span class="fl t1 mt10">姓名：</span>
                <input type="text" placeholder="请填写真实姓名" name="username" id="username">
            </div>
            <div class="clearfix mt10">
                <span class="fl mt10">手机号码：</span>
                <input type="tel" placeholder="请务必填写正确的手机号码" name="mobile" id="mobile">
            </div>
            <#--<div class="clearfix mt10">-->
                <#--<span class="fl mt20">预定数量：</span>-->

                <#--<p class="mpbox clearfix">-->
                    <#--<span class="cgminus  fs20 fl "></span>-->
                    <#--<span class=" fs14 tac fl">-->
                        <#--<input type="text" value="1" name="projectNumber" readonly class="cgtotalinput nobg tac p999 fs20 projectNumber">-->
                    <#--</span>-->
                    <#--<span class="cgplus can  fs18 fl"></span>-->
                <#--</p>-->
            <#--</div>-->
            <div class="clearfix mt10">
                <span class="fl mt10">支付金额：</span>
                <span class="fr mt10 pf40000 fs24 totalDeposit" style="height:41px"
                      value="#{ask.discountPrice}">¥#{ask.discountPrice}</span>
            </div>
            <div class="clearfix mt10">
                <span class="fl t1 mt10">备注：</span>
                <input type="text" placeholder="备注留言" name="remarks" id="remarks">
            </div>
        </div>
        <div class="goupaybox">
            <a href="javascript:submitOrder();" id="goupay"></a>
        </div>
    </form>
    <div class="earnestills tal fs16 line30 p555">

    </div>
</div>

</body>
</html>
<script type="text/javascript" src="${PATH}/js/weixin/askPayment.js?v=${version}"></script>
