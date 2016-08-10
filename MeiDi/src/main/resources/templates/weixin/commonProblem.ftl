<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="format-detection" content="telephone=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="keywords" content="北"/>
		<meta name="description" content=""/>

		<title>常见问题解答</title>
		<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
	<#include "header.ftl"/>
	</head>
	<body style="background:#ffffff">
		<div class="wrapper pr pb50">
            <div class="header pwhite bg  bor_b ">
                <a class="fs14 pa city iconfont icon-back base" href="${PATH}/my"></a>
                <p class="fs18">常见问题解答</p>
            </div>
			<div class="pt10 pl20 pr20 tal">
				<div class="mt30 qalistbox fs24 line40">

					<#if othersList?exists>
					    <#list othersList as others>
                            <div class="w580">
                                <img src="${IMAGE_FORMAL_URL}/${others.imageName}">
                            </div>
					    </#list>
					</#if>
				</div>
			</div>
			
		</div>
		
	</body>
</html>
