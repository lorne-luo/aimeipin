<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="keywords" content="北"/>
    <meta name="description" content=""/>

    <title>${commodity.name}</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<#include "header.ftl"/>
    <script src="${PATH}/ueditor/ueditor.parse.js"></script>
    <script>
        uParse("#description", {
            rootPath: '${PATH}/ueditor'
        });
        uParse("#buyNotice", {
            rootPath: '${PATH}/ueditor'
        });
    </script>
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
                title: '${commodity.name}', // 分享标题
                link: '${PATH}/business/commodityDetailPage/#{commodity.id}', // 分享链接
                imgUrl: '${PATH}/images/share.jpg', // 分享图标
                success: function () {
                    // 用户确认分享后执行的回调函数
                },
                cancel: function () {
                    // 用户取消分享后执行的回调函数
                }
            });

            <#if commodity.sharingSummary?exists && commodity.sharingSummary!='' >
                var sharingSummary='${commodity.sharingSummary}';
            <#else>
                var sharingSummary ='为您挑选中韩优质医美机构及顶级专家，推荐高性价比明星项目。';
            </#if>
            //分享好友
            wx.onMenuShareAppMessage({
                title: '${commodity.name}', // 分享标题
                desc: sharingSummary, // 分享描述
                link: '${PATH}/business/commodityDetailPage/#{commodity.id}', // 分享链接
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
        });
        </#if>
    </script>
    <script type='text/javascript'>
        <#if commodity.dicProvince.id == 35>
        (function (m, ei, q, i, a, j, s) {
            m[a] = m[a] || function () {
                        (m[a].a = m[a].a || []).push(arguments)
                    };
            j = ei.createElement(q),
                    s = ei.getElementsByTagName(q)[0];
            j.async = true;
            j.charset = 'UTF-8';
            j.src = i + '?v=' + new Date().getUTCDate();
            s.parentNode.insertBefore(j, s);

        })(window, document, 'script', '//static.meiqia.com/dist/meiqia.js', '_MEIQIA');
        _MEIQIA('entId', 2247);

        _MEIQIA('assign', {
            agentToken: 'b912e50bf17568e02289b5f9a0491ceb'
        });

        //无按钮模式
        _MEIQIA('withoutBtn', true);
        <#else>
        (function (m, ei, q, i, a, j, s) {
            m[a] = m[a] || function () {
                        (m[a].a = m[a].a || []).push(arguments)
                    };
            j = ei.createElement(q),
                    s = ei.getElementsByTagName(q)[0];
            j.async = true;
            j.charset = 'UTF-8';
            j.src = i + '?v=' + new Date().getUTCDate();
            s.parentNode.insertBefore(j, s);

        })(window, document, 'script', '//static.meiqia.com/dist/meiqia.js', '_MEIQIA');
        _MEIQIA('entId', 2247);
        
        _MEIQIA('assign', {
            agentToken: 'f63c019c0610f177cdacc757c3acdc3e'
        });
        //无按钮模式
        _MEIQIA('withoutBtn', true);
        </#if>
    </script>
    <style>
        .tabClick{ background: #f3f3f3; overflow: hidden;margin-top: 10px;border-bottom:2px solid #ff4d7d;}
        .tabClick li{ height:40px; line-height: 40px; width: 33%; float: left; text-align: center;font-size: 16px;border-right:0px solid #ff4d7d;}
        .tabClick li.active{ color: #099; transition: 0.1s; font-weight: bold}
        .tabClick li:not(:last-child){ border-right:1px solid #ddd;}
    </style>
</head>

<body style="background:#ffffff">
<div class="wrapper pr pb80 zx">
    <input type="hidden" value="#{commodity.id}" class="commodityId">
    <input type="hidden" value="${commodity.flag}" class="commodityFlag">
    <input type="hidden" value="#{commodity.dicCity.id}" class="cityId">

    <div class="pl12 pr12 mt20">
        <div class="itemlist shadowall ">
            <div class="focusImgs pr ">
                <div class="slider multiple-items">

                <#if commodity.commodityPhotoList?exists>
                    <#list commodity.commodityPhotoList as photo>
                        <div><a href="javascript:void(0);">
                            <img src="${IMAGE_FORMAL_URL}/${photo.imageName}"></a>
                        </div>
                    </#list>
                </#if>
                </div>
            </div>
            <div class="water">
            <#if commodity.flag == 1>
                <p class="zhe1">${commodity.discount} 折</p>

                <p class="tuan">${commodity.peopleNumber}人团</p>
            <#else>
                <p class="zhe">${commodity.discount} 折</p>
            </#if>
            </div>
            <#if commodity.labelFlag == 1>
                <div class="new"></div>
            <#elseif commodity.labelFlag == 2>
                <div class="hot"></div>
            <#elseif commodity.labelFlag == 3>
                <div class="lt"></div>
            <#elseif commodity.labelFlag == 4>
                <div class="ll"></div>
            </#if>

            <div class="tal pt10 pb10">
                <p class="pl18"><span
                        class="pf40000 fs18 lineblock mr10">¥#{commodity.discountPrice/100}<#if commodity.discountUnit?exists>${commodity.discountUnit}</#if></span>
                    <del>¥#{commodity.price/100}<#if commodity.unit?exists>${commodity.unit}</#if></del>
                </p>
                <p class="pl16 line24 fs18 menutitle">${commodity.name}</p>
            </div>
        </div>
    </div>
<#if commodity.flag != 4>
    <div class="detailtip pf40000 mt10 fs16">
        订金¥#{commodity.deposit/100},到院支付全款,订金全部退还
    </div>
</#if>
<#if commodity.tags?exists && commodity.tags !="">
    <div class="virtue mt10 fs16">
        <div class="t1">
            <#list commodity.tags?split(',') as tag>
                <span class="pf40000">${tag}</span>
            </#list>
        </div>
    </div>
</#if>
    <div class="addList mt20">

    </div>

    <ul class="tabClick">
        <li><a href="#description">项目详情</a></li>
        <li><a href="#buyNotice">订购须知</a></li>
        <li><a href="#youlike">猜你喜欢</a></li>
    </ul>

    <div class="casebox pl20 pr20 clearfix tal">
    <#if commodity.caseUrl?exists>
        <div class="casebtn"><a href="${commodity.caseUrl}"></a></div>
    </#if>

        <div id="description" class="line18">${commodity.description}</div>
    </div>

    <div id="buyNotice" class=" mb10 tal pl10 pr10 pt10 pb10 line18 buyNotice">
        <section class="fs18" style="display: inline-block; height: 2em; max-width: 100%; line-height: 1em;box-sizing: border-box; border-top: 1.1em solid #ff4d7d; border-bottom: 1.1em solid #ff4d7d; border-right: 1em solid transparent;">
            <section style="height: 2em; margin-top: -1em; color: white; padding: 0.5em 1em; max-width: 100%; white-space: nowrap;text-overflow: ellipsis;">订购须知</section>
        </section>
    <#if buyNotice?exists && buyNotice.description?exists>
        ${buyNotice.description}
    </#if>
    </div>

    <div id="youlike" class="tal pl10 pr10 moreclassify clearfix youlike">
        <section class="fs18" style="display: inline-block; height: 2em; max-width: 100%; line-height: 1em;box-sizing: border-box; border-top: 1.1em solid #ff4d7d; border-bottom: 1.1em solid #ff4d7d; border-right: 1em solid transparent;">
            <section style="height: 2em; margin-top: -1em; color: white; padding: 0.5em 1em; max-width: 100%; white-space: nowrap;text-overflow: ellipsis;">猜你喜欢</section>
        </section>
        <div class="youlikeList pt10">

        </div>
    </div>

    <div class="detailnav clearfix btfix">
        <div class="t1  tt2 fs12">
            <a class="borr fl pt8" href="${PATH}/index">
                <img src="${PATH}/images/detail/indicon.png">

                <p class="mt2">首页</p>
            </a>
            <a class="borr fl pt8" href="javascript:<#if wx_openid?has_content>favoriteAction(#{commodity.id})<#else>redirectLogin()</#if>;">
                <img src="${PATH}/images/detail/sc.png" class="<#if favoriteFlag == 1>hide</#if> favorite1">
                <img src="${PATH}/images/detail/noc.png" class="<#if favoriteFlag == 2>hide</#if> favorite2">

                <p class="mt2">收藏</p>
            </a>
            <a class=" fl pt8" href="javascript:void(0);" onclick="_MEIQIA._SHOWPANEL()">
                <img src="${PATH}/images/detail/zx.png">

                <p class="mt2">咨询</p>
            </a>
        </div>

    <#if commodity.state == 1 >
        <#if commodity.flag ==1 >
            <#-- 1 拼团 -->
            <a class="t2  bgfe91b0" href="${PATH}/business/bookingPage/#{commodity.id}/2">
                <div class="mt8"><span>¥#{commodity.alonePrice/100}</span></div>
                <p class="fs16 pwhite fb">单独预订</p>
            </a>
            <a class="t2 bgff4d7d" href="${PATH}/business/bookingPage/#{commodity.id}/1">
                <div class="mt8"><span>¥#{commodity.discountPrice/100}</span></div>
                <p class='fs16 pwhite fb'>发起拼团</p>
            </a>
        <#else>
            <#-- 2 福袋, 3 特惠, 4 咨询 -->
            <a class="t4 bgff4d7d" href="${PATH}/business/bookingPage/#{commodity.id}/3">
                <p class="fs20 mt16 priceorder">
                    <span>¥#{commodity.discountPrice/100}</span>
                    <i class="pwhite inlineblock ml4">我要预订</i>
                </p>
            </a>
        </#if>
    <#else>
        <a class="t4 ended">
            <p class="fs20 mt16 priceorder">
                <i class="pwhite inlineblock ml4">项目已结束</i>
            </p>
        </a>
    </#if>
    </div>

</div>


</body>
</html>
<script type="text/javascript" src="${PATH}/js/CountTClass.js"></script>
<script type="text/javascript" src="${PATH}/js/weixin/commodityDetail.js"></script>