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
            <form class=" form-inline" role="form">

                <div class="col-lg-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="form-group input-group">
                                <input type="text" class="form-control" id="bigapartname" name="bigapartname">
                                    <span class="input-group-btn">
                                        <button class="btn btn-default" type="button" id="creatnewbig">添加一级
                                        </button>
                                    </span>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <!-- Nav tabs -->
                            <ul class="nav nav-pills">
                            <#if interest?exists>
                                <#list interest as inte>
                                    <li class="<#if inte_index == 0>active</#if>" pid="#{inte.id}" id="parent_#{inte.id}">
                                        <a href="#home-pills_#{inte.id}" data-toggle="tab" aria-expanded="false">${inte.name}</a>
                                        <button onclick="deleteInterest(#{inte.id},1);" type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">×</span>
                                        </button>
                                    </li>
                                </#list>
                            </#if>
                            </ul>

                            <!-- Tab panes -->
                            <div class="tab-content">
                            <#if interest?exists>
                                <#list interest as inte>
                                    <div class="tab-pane fade <#if inte_index ==0>active in</#if>" id="home-pills_#{inte.id}">
                                        <h4></h4>
                                        <div class=" myitems clearfix mb20">
                                            <#if inte.interestCommoditySons?exists>
                                                <#list inte.interestCommoditySons as son>
                                                    <span class="alert alert-info pr" role="alert" tipid="#{son.id}" id="son_#{son.id}">
                                                      <button type="button" class="close" data-dismiss="alert" onclick="deleteInterest(#{son.id},2)"
                                                              aria-label="Close">
                                                          <span aria-hidden="true" >×</span>
                                                      </button>
                                                      <strong>${son.name}</strong>
                                                    </span>
                                                </#list>
                                            </#if>
                                        </div>
                                    </div>
                                </#list>
                            </#if>



                                <div class="tab-pane fade" id="settings-pills">
                                    <h4></h4>

                                    <div class=" myitems clearfix mb20">


                                    </div>
                                </div>
                            </div>
                            <div class="form-group input-group">
                                <input type="text" class="form-control " name="servicename" id="servicename">
                                    <span class="input-group-btn">
                                        <button class="btn btn-default" type="button" id="creatnew">添加二级
                                        </button>
                                    </span>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>

            </form>
        </div>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

</body>

</html>
<script type="text/javascript" src="${PATH}/js/backend/interest.js"></script>