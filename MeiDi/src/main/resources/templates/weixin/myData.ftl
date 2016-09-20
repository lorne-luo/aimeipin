<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="keywords" content="北"/>
    <meta name="description" content=""/>

    <title>我的资料</title>
    <meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<#include "header.ftl"/>
</head>
<body style="background:#ffffff">
<div class="wrapper pr ">
    <div class="header pwhite bg  bor_b ">
        <a class="fs14 pa iconfont icon-back base" href="${PATH}/my"></a>

        <p class="fs18">聚会美商城-我的资料</p>
    </div>
    <div class="pt10 pl20 tal">
        <div class="mt10 pr20">
            <div class="itemsrow  bgWhite  tal itemsrowB">
                <a href="#">
                    <div class="">
                        <div class=" t1 pr bor_b">
                            <img src="${PATH}/images/my/z1.png" class="rowimg">
                            <span class="fs28 hl36 ml10 ">姓名</span>
                            <span class="result"><#if userProfile.name?exists>${userProfile.name}</#if></span>

                            <div class='fs24 p20 hide readyinput'>姓名：
                                <input type='text' name='name' class='userinput  commoninput' placeholder=''></div>
                            <span class="iconfont icon-more p555 pa"></span>
                        </div>
                    </div>
                </a>
            </div>
            <div class="itemsrow  bgWhite  tal itemsrowB">
                <a href="#">
                    <div class=" ">
                        <div class=" t1 pr bor_b">
                            <img src="${PATH}/images/my/z2.png" class="rowimg4">
                            <span class="fs28 hl36 ml10 ">手机号</span>
                            <span class="result"><#if userProfile.mobile?exists>${userProfile.mobile}</#if></span>

                            <div class='fs24 p20 hide readyinput'>手机号：
                                <input type='text' name='mobile' class='userinput  commoninput' placeholder=''></div>
                            <span class="iconfont icon-more p555 pa"></span>
                        </div>
                    </div>
                </a>
            </div>
            <div class="itemsrow  bgWhite  tal itemsrowB">
                <a href="#">
                    <div class=" ">
                        <div class=" t1 pr bor_b">
                            <img src="${PATH}/images/my/z3.png" class="rowimg3">
                            <span class="fs28 hl36 ml10 ">微信号</span>
                            <span class="result"><#if userProfile.wxNumber?exists>${userProfile.wxNumber}</#if></span>

                            <div class='fs24 p20 hide readyinput'>微信号：
                                <input type='text' name='wx_number' class='userinput  commoninput' placeholder=''></div>
                            <span class="iconfont icon-more p555 pa"></span>
                        </div>
                    </div>
                </a>
            </div>
            <div class="itemsrow  bgWhite  tal itemsrowA">
                <a href="#">
                    <div class=" ">
                        <div class=" t1 pr bor_b">
                            <img src="${PATH}/images/my/z4.png" class="rowimg3">
                            <span class="fs28 hl36 ml10 ">性别</span>
                            <span class="result gender"><#if userProfile.gender?exists><#if userProfile.gender == 1>男<#elseif userProfile.gender == 2 >女</#if></#if></span>

                            <div class="databox hide">
                                <select id="gender">
                                    <option value="1">男</option>
                                    <option value="2">女</option>
                                </select>
                            </div>
                            <span class="iconfont icon-more p555 pa"></span>
                        </div>
                    </div>
                </a>
            </div>
            <div class="itemsrow  bgWhite  tal itemsrowA">
                <a href="#">
                    <div class=" ">
                        <div class=" t1 pr bor_b">
                            <img src="${PATH}/images/my/z5.png" class="rowimg4">
                            <span class="fs28 hl36 ml10 ">所在地</span>
                            <span class="result city"><#if userProfile.dicCity?exists>${userProfile.dicCity.name}</#if></span>

                            <div class="databox hide">
                                <select id="city">

                                </select>
                            </div>
                            <span class="iconfont icon-more p555 pa"></span>
                        </div>
                    </div>
                </a>
            </div>
            <div class="itemsrow  bgWhite  tal itemsrowA">
                <a href="#">
                    <div class=" ">
                        <div class=" t1 pr bor_b">
                            <img src="${PATH}/images/my/z6.png" class="rowimg5">
                            <span class="fs28 hl36 ml10 ">从何种渠道认识聚会美</span>
                            <span class="result channels"><#if userProfile.channels?exists>${userProfile.channels}</#if></span>

                            <div class="databox hide">
                                <select id="channels" multiple>
                                </select>
                            </div>
                            <span class="iconfont icon-more p555 pa"></span>
                        </div>
                    </div>
                </a>
            </div>
            <div class="itemsrow  bgWhite  tal itemsrowA">
                <a href="#">
                    <div class=" ">
                        <div class=" t1 pr bor_b">
                            <img src="${PATH}/images/my/z7.png" class="rowimg5">
                            <span class="fs28 hl36 ml10 ">感兴趣的医美项目</span>
                            <span class="result interests"><#if userProfile.interests?exists>${userProfile.interests}</#if></span>

                            <div class="databox hide">
                                <select id="interests" multiple>

                                </select>
                            </div>
                            <span class="iconfont icon-more p555 pa"></span>
                        </div>
                    </div>
                </a>
            </div>
            <div class="itemsrow  bgWhite  tal itemsrowA">
                <a href="#">
                    <div class=" ">
                        <div class=" t1 pr bor_b">
                            <img src="${PATH}/images/my/z8.png" class="rowimg5">
                            <span class="fs28 hl36 ml10 ">会选择去哪里做项目</span>
                            <span class="result wheres" multiple><#if userProfile.wheres?exists>${userProfile.wheres}</#if></span>

                            <div class="databox hide">
                                <input type="hidden" value="<#if userProfile.wheres?exists>${userProfile.wheres}</#if>" id="wheresinp">
                                <select id="wheres" multiple>
                                    <option value="韩国">韩国</option>
                                    <option value="中国">中国</option>
                                    <option value="其他">其他</option>
                                </select>
                            </div>
                            <span class="iconfont icon-more p555 pa"></span>
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>

</div>

</body>
</html>
<script type="text/javascript" src="${PATH}/js/weixin/myData.js?v=${version}"></script>


