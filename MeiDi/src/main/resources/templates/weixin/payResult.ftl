<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="format-detection" content="telephone=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="keywords" content="北"/>
		<meta name="description" content=""/>

		<title>聚会美商城-支付结果</title>
		<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
	<#include "header.ftl"/>
	</head>
	<body style="background:#ffffff">
		<div class="wrapper pr">
				<div class="center pr bookordercenter mt50">
                    <img style="width:540px;max-height:280px;" src="${IMAGE_FORMAL_URL}/${commodity.commodityPhotoList[0].imageName}">
                    <div class="p30 tac ml30 mr30" >
						<p class="fs24 tac fb mb30">${commodity.name}</p>
						<p class="fs28 tac fb mt30" >支付成功</p>
                    </div>

					<#if order?exists && order.launchId?exists>
                        <div class="p10 tac mt30">
                            <a href="${PATH}/business/joinGroupPage/#{order.launchId}"  class=" fs28 baseBtn" style="background:#fd5380;width:500px;margin:0 auto;">分享拼团邀请好友参团</a>
                        </div>
					</#if>

					<div class="p10 tac mt20">
						<a href="${PATH}/business/myOrderPage"  class=" fs28 baseBtn" style="background:#fd5380;width:500px;margin:0 auto;">查看我的订单</a>
					</div>
			</div><!--center-->
		</div>

	</body>
</html>



