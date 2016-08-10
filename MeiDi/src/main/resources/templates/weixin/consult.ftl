<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="keywords" content="北"/>
    <meta name="description" content=""/>

    <title>定制咨询</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<#include "header.ftl"/>

</head>
<body style="background:#ffffff">
<script type='text/javascript'>
    (function(m, ei, q, i, a, j, s) {
        m[a] = m[a] || function() {
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
    //无按钮模式
    _MEIQIA('withoutBtn', true);
</script>
<div class="wrapper pr pb130 zx">
    <div class="zxdivbox">
        <div class="zxbox">
            <a href="javascript:void(0);" class="dz"></a><a href="javascript:void(0)" onclick="_MEIQIA._SHOWPANEL()"
                                                            class="mf"></a>
        </div>
        <div class="itemlistbox">


        </div>
        <a href="javascript:getMore();" class="getmore">点击加载更多...</a>
    </div>
    <div class=" zx btfix  clearfix itemsbtnbox">
        <a href="${PATH}/index" class="fl <#if pageActive == 'index'>active</#if>">
            <span class="shouyeicon"></span>

            <p>首页</p>
        </a>
        <a href="${PATH}/class" class="fl <#if pageActive == 'class'>active</#if>">
            <span class="fenleicon"></span>

            <p>分类</p>
        </a>
        <a href="${PATH}/consult" class="fl <#if pageActive == 'consult'>active</#if>">
            <span class="zixunicon"></span>

            <p>咨询</p>
        </a>
        <a href="${PATH}/my" class="fl <#if pageActive == 'my'>active</#if>">
            <span class="myicon"></span>

            <p>我的</p>
        </a>
    </div>
</div>


</body>
</html>
<script type="text/javascript" src="${PATH}/js/weixin/common-ajax.js"></script>
<script type="text/javascript" src="${PATH}/js/weixin/consult.js"></script>