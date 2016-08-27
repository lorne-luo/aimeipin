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
        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-12">
                    <h4 class="page-header">项目列表</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="p10 form-inline">
                <label><span class="star mr6"></span>请输入：</label>
                <input type="text" class="queryStr  form-control"/>
                <a href="javascript:searchCommodity();" class="btn btn-success">搜索</a>
            </div>
            <div class=" bor">
                <div>
                    <table class="table table-hover tac">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>
                                <select class="" id="flag">
                                    <option value="-1">类型</option>
                                    <option value="1">拼团</option>
                                    <option value="2">福袋</option>
                                    <option value="3">特惠</option>
                                    <option value="4">咨询</option>
                                </select>
                            </th>
                            <th>
                                <select id="category">
                                    <option value="-1">所有</option>
                                    <#list categoryList as category>
                                        <option value="#{category.id}"><#if category.name?has_content>${category.name}<#else>------</#if></option>
                                    </#list>
                                </select>
                            </th>
                            <th style="max-width: 300px;min-width: 200px;">名称</th>
                            <th>原价</th>
                            <th>折扣</th>
                            <th>现价</th>
                            <th>
                                <select class="" id="state">
                                    <option value="-1">状态</option>
                                    <option value="0">未上架</option>
                                    <option value="1">已上架</option>
                                </select>
                            </th>
                            <th>
                                <select class="" id="province">
                                    <option value="-1">地区</option>
                                    <#list provinceList as province>
                                        <option value="#{province.id}">${province.name}</option>
                                    </#list>
                                </select>
                            </th>
                            <th>时间</th>
                            <th>权重</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody class="addList">

                        </tbody>
                    </table>
                </div>
                <div id="pagediv"></div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${PATH}/js/backend/commodityList.js"></script>
</html>
