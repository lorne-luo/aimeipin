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

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">修改密码</h3>
                </div>
                <div class="panel-body">
                    <form role="form">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control password" placeholder="密码" name="password" type="password" value="">
                            </div>
                            <div class="form-group">
                                <input class="form-control rePassword" placeholder="重复密码" name="rePassword" type="password" value="">
                            </div>
                            <a href="javascript:edit();" class="btn btn-lg btn-success btn-block">确定</a>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script src="${PATH}/js/backend/editPassword.js"></script>
</html>
