<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>后台管理系统</title>
<#include "common.ftl"/>

</head>

<body>

<div id="wrapper">
<#include "header.ftl"/>
    <div id="page-wrapper" class="clearfix">

        <div class="col-lg-10">
            <div class="row">
                <div class="col-lg-12">
                    <h4 class="page-header"><#if flag ==1>自定义关于我们<#elseif flag == 2>自定义常见问题</#if></h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="col-lg-12" id="addForm">
                <input type="hidden" value="${flag}" id="flag">

                <div class=" myitems clearfix">
                    <div id="dbpicbox" class="dbpicbox  clearfix ">
                    <#if othersList?exists>
                        <#list othersList as others>
                            <div class="piclist fl pr text-center">
                                <img class="" src="${IMAGE_FORMAL_URL}/${others.imageName}">

                                <div class="text-center">
                                    <a class="btn btn-warning" backtip="#{others.id}">删除</a>
                                </div>
                            </div>
                        </#list>
                    </#if>
                    </div>
                    <div class="pr mart10 tac">
                        <input name="fileName" id="fileName" style="display: none;" type="file">
                        <a href="javascript:;" class="btn btn-success" id="addAbtn">+添加照片</a>
                    </div>
                </div>

                <div class="text-center col-lg-10 mt20">

                    <button type="button" onclick="saveOthers();" class="btn btn-primary btn-lg ml20" id="creatnew">保存设置
                    </button>
                </div>
            </div>
        </div>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

</body>

</html>
<script type="text/javascript" src="${PATH}/js/backend/others.js"></script>