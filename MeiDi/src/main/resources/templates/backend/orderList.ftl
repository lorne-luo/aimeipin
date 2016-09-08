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
                    <h4 class="page-header">订单列表</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="p10 form-inline clearfix">
                <div class="pull-left fs14" id="filter-panel" style="line-height:34px;">
                </div>

                <div class="pull-right">
                    <label>搜索：</label>
                    <input type="text" id="queryStr" class="queryStr  form-control"/>
                    <a href="javascript:searchOrder();" class="btn btn-success">搜索</a>
                </div>
            </div>

            <input type="hidden" name="search_launch_id" id="search_launch_id" value="0">
            <input type="hidden" name="search_commodity_id" id="search_commodity_id" value="0">

            <div class=" bor">
                <div class="table-responsive">
                    <table class="table tac">
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
                            <th style="width:300px">名称</th>
                            <th>价格</th>
                            <th>订金</th>
                            <th>微信</th>
                            <th>联系人</th>
                            <th>时间</th>
                            <th>
                                <select class="" id="state">
                                    <option value="-1">状态</option>
                                    <option value="1">待支付</option>
                                    <option value="2">已支付</option>
                                    <option value="3">已预约</option>
                                    <option value="4">已完成</option>
                                    <option value="5">取消中</option>
                                    <option value="6">已取消</option>
                                    <option value="100">已删除</option>
                                </select>
                            </th>
                            <th style="max-width:130px">备注</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="addList">

                        </tbody>
                    </table>
                </div>
                <div id="pagediv"></div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${PATH}/js/backend/orderList.js"></script>
</html>
