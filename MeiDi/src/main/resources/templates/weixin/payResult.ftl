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
				<div class="center  pr bookordercenter mt50">
				
					<p class="pfd5380 fs28 tac fb" >支付成功</p>

					<#if order?exists && order.launchId?exists>
                        <div class="mt20 p10 tac mt30">
                            <a href="${PATH}/business/joinGroupPage/#{order.launchId}"  class=" fs28 baseBtn" style="background:#fd5380;width:500px;margin:0 auto;">分享拼团邀请好友参团</a>
                        </div>
					</#if>

					<div class="mt20 p10 tac mt30">
						<a href="${PATH}/business/myOrderPage"  class=" fs28 baseBtn" style="background:#fd5380;width:500px;margin:0 auto;">查看我的订单</a>
					</div>
			</div><!--center-->
		</div>

	</body>
</html>



