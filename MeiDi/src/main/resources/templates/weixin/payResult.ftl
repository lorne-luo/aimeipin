<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="format-detection" content="telephone=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="keywords" content="北"/>
		<meta name="description" content=""/>

		<title>支付结果</title>
		<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
	<#include "header.ftl"/>
	</head>
	<body style="background:#ffffff">
		<div class="wrapper pr">
				<div class="center pr bookordercenter mt20">
                    <img style="width:540px;max-height:360px;" src="${IMAGE_FORMAL_URL}/${commodity.commodityPhotoList[0].imageName}">
                    <div class="p20 tac ml30 mr30" >
						<p class="fs24 tac fb mb30">${commodity.name}</p>
						<p class="fs28 tac fb mt30" >
						<#if order.flag == 5>已参加<#else>支付成功</#if>
						</p>
                    </div>

					<#if order.flag == 5>
                    <div class="p10 tal ml50 mr50 fs16 line24" >
                        <p>下单时间：${order.createTime}</p>
                        <p>姓  名：${order.username}</p>
                        <p>电  话：${order.mobile}</p>
                        <p>微  信：${order.weixin}</p>
                        <p>备  注：${order.remarks}</p>
                        <p>订单编号：${order.orderCode}</p>
						<br/>
						<p>${order.remarks}</p>
					</div>
					</#if>

					<#if order?exists && order.launchId?exists && order.flag != 5>
                        <div class="p10 tac mt30">
                            <a href="${PATH}/business/joinGroupPage/#{order.launchId}"  class=" fs28 baseBtn" style="background:#fd5380;width:500px;margin:0 auto;">分享拼团邀请好友参团</a>
                        </div>
					</#if>

					<#if order.flag != 5>
					<div class="p10 tac mt20">
						<a href="${PATH}/business/myOrderPage"  class=" fs28 baseBtn" style="background:#fd5380;width:500px;margin:0 auto;">查看我的订单</a>
					</div>
					<#else>
					<div class="p10 tac ml30 mr30 fs16 line24" >
                        <p>我们将立即与您取得联系并邀请您入群,请留意微信好友申请。</p>
						<p>长按识别二维码,添加客服微信</p>
						<img class="mt10" width="100%" src="/images/daka/service.png">
                    </div>
					</#if>
			</div><!--center-->
		</div>

	</body>
</html>



