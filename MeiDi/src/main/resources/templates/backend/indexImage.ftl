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
                    <h4 class="page-header">添加商品</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="col-lg-6">
                <form class="createVenueForm" role="form" method="post" id="addIndexImage">
                    <div class="form-group ">
                        <div id="dbpicbox" class="dbpicbox  clearfix ">
                        <#list images as img>
                            <div class="piclist fl pr text-center">
                                <img class="" src="${IMAGE_FORMAL_URL}/${img.imageName}">
                                <p>
                                    <select>
                                        <option value="1" <#if img.weight?exists && img.weight == 1>selected</#if>>1</option>
                                        <option value="2" <#if img.weight?exists && img.weight == 2>selected</#if>>2</option>
                                        <option value="3" <#if img.weight?exists && img.weight == 3>selected</#if>>3</option>
                                        <option value="4" <#if img.weight?exists && img.weight == 4>selected</#if>>4</option>
                                        <option value="5" <#if img.weight?exists && img.weight == 5>selected</#if>>5</option>
                                        <option value="6" <#if img.weight?exists && img.weight == 6>selected</#if>>6</option>
                                        <option value="7" <#if img.weight?exists && img.weight == 7>selected</#if>>7</option>
                                        <option value="8" <#if img.weight?exists && img.weight == 8>selected</#if>>8</option>
                                        <option value="9" <#if img.weight?exists && img.weight == 9>selected</#if>>9</option>
                                        <option value="10" <#if img.weight?exists && img.weight == 10>selected</#if>>10</option>
                                    </select>
                                </p>
                                <div class="martb10">
                                    <textarea class="form-control" name=""><#if img.url?exists>${img.url}</#if></textarea>
                                </div>
                                <div class="text-center">
                                    <a class="btn btn-warning" backtip="#{img.id}">删除</a>
                                </div>
                            </div>
                        </#list>
                        </div>
                        <div class="pr mart10">
                            <span class="dbstar  pv10s0"></span>
                            <label>商品图片：</label>
                            <input type="file" name="fileName" id="fileName" style="display: none;">
                            <a href="javascript:;" class="btn btn-success" id="addAbtn">+添加照片</a>
                        </div>
                    </div>
                    <div class="text-center col-lg-10 mt20">
                        <button type="button" class="btn btn-primary btn-lg" onclick="submitImage();">保存</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

</body>
<script src="${PATH}/js/backend/indexImage.js"></script>
</html>