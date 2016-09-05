<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="keywords" content="北"/>
    <meta name="description" content=""/>

    <title>参团</title>
    <meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
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
            jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });

        wx.ready(function () {
            //分享朋友圈
            wx.onMenuShareTimeline({
                title: '【超值拼团】${commodity.name}', // 分享标题
                link: '${PATH}/business/joinGroupPage/#{groupLaunch.id}', // 分享链接
                imgUrl: '${PATH}/images/share.jpg', // 分享图标
                success: function () {
                    // 用户确认分享后执行的回调函数
                },
                cancel: function () {
                    // 用户取消分享后执行的回调函数
                }
            });
            //分享好友
            wx.onMenuShareAppMessage({
                title: '【超值拼团】${commodity.name}', // 分享标题
                desc: '【还差${groupLaunch.peopleNumber - userList?size}人】${commodity.name}', // 分享描述
                link: '${PATH}/business/joinGroupPage/#{groupLaunch.id}', // 分享链接
                imgUrl: '${PATH}/images/share.jpg', // 分享图标
                type: 'link', // 分享类型,music、video或link，不填默认为link
                dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                success: function () {
                    // 用户确认分享后执行的回调函数
                },
                cancel: function () {
                    // 用户取消分享后执行的回调函数
                }
            });
        })
        </#if>
    </script>
</head>
<body style="background:#ffffff">
<div class="wrapper pr pb130">
    <input type="hidden" value="#{groupLaunch.commodityId}" id="commodityId">
    <input type="hidden" value="#{groupLaunch.dicCity.id}" id="cityId">
    <input type="hidden" value="${groupLaunch.state}" id="state">

    <div class="pl20 pr20 mt10">
        <div class="itemlist shadowall ">
            <div class="focusImgs pr ">
                <div class="slider multiple-items">
                <#if commodity.commodityPhotoList?exists>
                    <#list commodity.commodityPhotoList as photo>
                        <#if photo_index == 0>
                            <div><a href="${PATH}/business/commodityDetailPage/#{groupLaunch.commodityId}"><img
                                    src="${IMAGE_FORMAL_URL}/${photo.imageName}"></a>
                            </div>
                        </#if>
                    </#list>
                </#if>
                </div>
            </div>
        </div>
        <div class="tal pt10 pb10 mt20">
            <p class="pl18"><span class="pf40000 fs28 lineblock mr10">¥#{groupLaunch.discountPrice/100}</span>
                <del class="fs20">¥#{groupLaunch.price/100}</del>
            </p>
            <p class="line30 fs24">${groupLaunch.commodityName}</p>
        </div>
    </div>

    <div class="pt10 ">
        <div class="gogroup fs20">
            <div class="clearfix ">
            <#if userList?exists>
                <#list userList as user>
                    <span class="inlineblock ml10 mr10 mt10 mb10">
                        <div class="twoimg pr">
                            <img src="<#if user.user.headimgurl?has_content>${user.user.headimgurl}<#else>/images/fog.png</#if>" class="t1">
                        </div>
                        <p class="fs16"><#if user.groupLaunchUser.flag == 1>【团长】</#if><#if user.user.headimgurl?has_content>${user.user.nickname}<#else>未知</#if></p>
                    </span>
                </#list>
            </#if>
            </div>

            <div class="mt30 mb30 fs28">
            <#if groupLaunch.state == 0>
                <#if groupLaunch.peopleNumber - userList?size gt 0>
                    <span>还差<span class="f40b0b">${groupLaunch.peopleNumber - userList?size}</span>人，你来了咱就一起美！</span>
                </#if>
            <#elseif groupLaunch.state == 1>
                <span>该拼团已成功！</span>
            <#else>
                <span>该拼团已结束！</span>
            </#if>
            </div>
        </div>
        <#if groupLaunch.state == 0>
        <div class="ml20 mr20 mt20 fs18 pr">
            <span class="line"></span>
            <span class="times p9c" id="times" endtime="#{endTime}">剩余时间：
                <span class="f40b0b">23</span><i class="f40b0b">:</i>
                <span class="f40b0b">55</span><i class="f40b0b">:</i>
                <span class="f40b0b">59</span>
            </span>
            <span class="line ml10"></span>
        </div>
        </#if>
    </div>
    <div class="tal pl20 pr20">
        <p class="fs24 pb10">猜你喜欢</p>

        <div class="pl20 pr20 moreclassify clearfix addList">

        </div>
    </div>

</div>
<div class="sharebtnbox clearfix btfix">
    <a href="${PATH}/class" class="t1">更多拼团</a>
<#if shareFlag?exists && shareFlag == 1>
    <a>已参团</a>
<#else>
    <a href="javascript:checkJoinGroup(#{groupLaunch.id});">我要参团</a>
</#if>
</div>
<#if groupLaunch.peopleNumber - userList?size gt 0 && shareFlag?exists && shareFlag == 1>
<div class="sharemark">
    <div class="rightt1">
        <img src="${PATH}/images/row.png">

        <p>发送给小伙伴参团</p>
    </div>

    <#if shareFlag?exists && shareFlag == 1>
        <p class="fs26 t2">您已经参加该团，<br/>还差${groupLaunch.peopleNumber - userList?size}人就拼团成功啦！</p>
    <#else>
        <p class="fs28 t1">还差${groupLaunch.peopleNumber - userList?size}人就拼团成功啦！</p>
    </#if>
</div>
</#if>

</body>
</html>
<script type="text/javascript" src="${PATH}/js/CountTClass.js"></script>
<script type="text/javascript" src="${PATH}/js/weixin/joinGroup.js"></script>