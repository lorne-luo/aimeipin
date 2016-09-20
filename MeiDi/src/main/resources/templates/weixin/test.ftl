<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="keywords" content="北"/>
    <meta name="description" content=""/>

    <title>首页</title>
    <meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">


    <link rel="stylesheet" type="text/css" href="${PATH}/css/mobiscroll.custom/mobiscroll.custom-2.17.1.min.css"/>
    <link rel="stylesheet" type="text/css" href="${PATH}/js/msgbox/msgbox.css"/>
    <link rel="stylesheet" type="text/css" href="${PATH}/slick/slick.css"/>
    <link rel="stylesheet" type="text/css" href="${PATH}/slick/slick-theme.css"/>
    <link rel="stylesheet" type="text/css" href="${PATH}/css/common.css"/>

    <script type="text/javascript" src="${PATH}/js/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="${PATH}/js/common.js"></script>
    <script type="text/javascript" src="${PATH}/js/DialogClass.js"></script>
    <script type="text/javascript" src="${PATH}/js/jquery.lazyload.js"></script>
    <script type="text/javascript" src="${PATH}/slick/slick.min.js"></script>
    <script type="text/javascript" src="${PATH}/js/constants.js"></script>
    <script type="text/javascript" src="${PATH}/js/msgbox/msgbox.js"></script>
    <script type="text/javascript" src="${PATH}/js/mobiscroll.custom-2.17.1.min.js"></script>
    <script type="text/javascript" src="${PATH}/js/weixin/common-ajax.js"></script>

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
                title: '聚会美商城', // 分享标题
                link: '${PATH}', // 分享链接
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
                title: '聚会美商城', // 分享标题
                desc: '为您挑选中韩优质医美机构及顶级专家，推荐高性价比明星项目。', // 分享描述
                link: '${PATH}', // 分享链接
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
<div class="wrapper pr pb130" id="wrapper">
    <div class="focusImg pr  topimg shadowall">
        <div class="slider multiple-items sliderbox">
        <#if images?exists>
            <#list images as image>
                <div><a href="<#if image.url?exists>${image.url}?from_slider=${image?index}<#else>javascript:void(0);</#if>"><img
                        src="${IMAGE_FORMAL_URL}/${image.imageName}"></a></div>
            </#list>
        </#if>
        </div>
    </div>
    <div class="pl20 pr20" style="margin-bottom: -20px">

        <div class="searchdiv pr">
            <img src="${PATH}/images/index/flower.png" class="flower">


            <form action="javascript:searchAction();" id="searchform">
                <div class="searchbox pa">
                    <img src="${PATH}/images/index/search.png" class="searchimg">
                    <input type="search" id="searchinput">
                    <a href="javascript:void(0);" class="searchbtn"></a>
                </div>
            </form>
        </div>
        <div class="itemlistbox">

        </div>

    </div>

    <ul id="getmore" class="md-news hide" style="display:none" mbsc-enhance></ul>
    <div id="getmore-loading" class="mbsc-btn-ic"  style="display:none" mbsc-enhance>
        <span class="getmore">加载中...</span>
    </div>

</div>
<#include "footer.ftl"/>
</body>
</html>

<script type="text/javascript" src="${PATH}/js/weixin/index.js"></script>
