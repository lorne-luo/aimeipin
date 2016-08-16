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
                    <h4 class="page-header">用户列表</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="p10 form-inline">
                <label><span class="star mr6"></span>请输入：</label>
                <input type="text" placeholder="如：名称，电话等" class="queryStr  form-control"/>
                <a href="javascript:searchUser();" class="btn btn-success">搜索</a>
            </div>
            <div class="p10 form-inline">
                <label><span class="star mr6"></span>所在地：</label>
                <select class="provinceId">
                    <option value="0">请选择</option>
                    <#list provinceList as province>
                        <option value="#{province.id}">${province.name}</option>
                    </#list>
                </select>
                <select class="cityId">
                    <option value="0">请选择</option>
                </select>
            </div>
            <div class=" bor">
                <div>
                    <table class="table table-hover tac">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>昵称</th>
                            <th>积分</th>
                            <th>姓名</th>
                            <th>性别</th>
                            <th>电话</th>
                            <th>所在地</th>
                            <th>渠道</th>
                            <th>感兴趣</th>
                            <th>去哪做</th>
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
<script src="${PATH}/js/backend/userList.js"></script>
</html>
