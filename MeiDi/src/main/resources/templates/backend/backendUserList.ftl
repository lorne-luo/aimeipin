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
                    <h4 class="page-header">管理员列表</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class=" bor">
                <div>
                    <table class="table table-hover tac">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>帐号</th>
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
<script src="${PATH}/js/backend/backendUserList.js"></script>
</html>
