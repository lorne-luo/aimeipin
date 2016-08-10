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
    <div id="page-wrapper">

        <div class="col-lg-10">
            <div class="row">
                <div class="col-lg-12">
                    <h4 class="page-header"><#if flag == 1>自定义项目标签<#elseif flag == 2>自定义用户渠道</#if></h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="col-lg-10">
                <form class=" form-inline" role="form">
                    <input type="hidden" value="${flag}" id="flag"／>
                    <div class=" myitems clearfix">
                    <#if tagList?exists>
                        <#list tagList as tag>
                            <span id="tag_#{tag.id}" class="alert alert-info pr" role="alert" tipid=0>
                              <button type="button" class="close" data-dismiss="alert" aria-label="Close" onclick="deleteOne(#{tag.id});">
                                  <span aria-hidden="true">×</span>
                              </button>
                              <strong>${tag.name}</strong>
                            </span>
                        </#list>
                    </#if>
                    </div>
                    <div class="text-center col-lg-10 mt20">
                        <input class="form-control"  id="new"/>
                        <button type="button" class="btn btn-primary btn-lg ml20" id="add">添加</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

</body>

</html>
<script type="text/javascript" src="${PATH}/js/backend/tags.js"></script>