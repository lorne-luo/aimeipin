<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="keywords" content="北"/>
    <meta name="description" content=""/>

    <title>我的</title>
    <meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<#include "header.ftl"/>
</head>
<body style="background:#ffffff">
<div class="wrapper pr pb180">
    <div class="pt20 pl30 pr30 mypage">
        <div class="t1">
            <div class="myimgbox"><img src="${user.headimgurl}"></div>
            <p class="fs28 pt20">${user.nickname}</p>
        </div>
        <div class="itemsrow  bgWhite  tal">
            <a href="${PATH}/business/myOrderPage">
                <div class=" ">
                    <div class=" t1 pr bor_b">
                        <img src="${PATH}/images/my/order.png" class="rowimg1">
                        <span class="fs28 hl36 ml10 ">我的订单</span>
                        <span class="iconfont icon-more p555 pa"></span>
                    </div>
                </div>
            </a>
        </div>
        <div class="itemsrow  bgWhite  tal">
            <a href="${PATH}/business/myFavoritePage">
                <div class=" ">
                    <div class=" t1 pr bor_b">
                        <img src="${PATH}/images/my/c.png" class="rowimg">
                        <span class="fs28 hl36 ml10 ">我的收藏</span>
                        <span class="iconfont icon-more p555 pa"></span>
                    </div>
                </div>
            </a>
        </div>
        <div class="itemsrow  bgWhite  tal">
            <a href="${PATH}/business/myDataPage">
                <div class=" ">
                    <div class=" t1 pr bor_b">
                        <img src="${PATH}/images/my/file.png" class="rowimg3">
                        <span class="fs28 hl36 ml10 ">我的资料</span>
                        <span class="iconfont icon-more p555 pa"></span>
                    </div>
                </div>
            </a>
        </div>
        <div class="itemsrow  bgWhite  tal">
            <a href="${PATH}/business/myIntegral">
                <div class=" ">
                    <div class=" t1 pr bor_b">
                        <img src="${PATH}/images/my/jf.png" class="rowimg">
                        <span class="fs28 hl36 ml10 ">我的积分</span>
                        <span class="iconfont icon-more p555 pa"></span>
                    </div>
                </div>
            </a>
        </div>
        <div class="itemsrow  bgWhite  tal">
            <a href="javascript:alert('敬请期待');">
                <div class=" ">
                    <div class=" t1 pr bor_b">
                        <img src="${PATH}/images/my/sc.png" class="rowimg2">
                        <span class="fs28 hl36 ml10 ">积分商城</span>
                        <span class="iconfont icon-more p555 pa"></span>
                    </div>
                </div>
            </a>
        </div>
        <div class="itemsrow  bgWhite  tal">
            <a href="${PATH}/business/commonProblem">
                <div class=" ">
                    <div class=" t1 pr bor_b">
                        <img src="${PATH}/images/my/q.png" class="rowimg">
                        <span class="fs28 hl36 ml10 ">常见问题</span>
                        <span class="iconfont icon-more p555 pa"></span>
                    </div>
                </div>
            </a>
        </div>
        <div class="tal">
            <div class="aboutustip">

            </div>
            <div class="afterimg">
            </div>
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
<#include "footer.ftl"/>

</body>
</html>

