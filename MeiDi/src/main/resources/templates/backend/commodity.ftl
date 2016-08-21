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
    <script>
        window.UEDITOR_HOME_URL = BASE_JS_URL + "/ueditor/";
    </script>

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
            <div class="col-lg-12">
                <form class="createVenueForm" role="form" method="post" id="addCommodity" name="addCommodity">
                    <input type="hidden" name="state" class="state" value="0">

                    <div class="form-group">
                        <label><span class="star mr6">*</span>商品类型：</label>
                        <select name="flag" class="flag">
                            <option value="1">拼团</option>
                            <option value="2">福袋</option>
                            <option value="3">特惠</option>
                            <option value="4">咨询</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6">*</span>商品名称：</label>
                        <input class="form-control" placeholder="例如：为你打造美丽" name="name"/>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6">*</span>商品关键词：</label>
                        <input class="form-control" placeholder="例如：为你打造美丽" name="keyword"/>
                    </div>
                    <div class="form-group">
                        <div id="dbpicbox" class="dbpicbox  clearfix ">

                        </div>
                        <div class="pr mart10">
                            <label><span class="star mr6 pv10s0">*</span>商品封面：</label>
                            <input name="fileName" id="fileName" style="display: none;" type="file">
                            <a href="javascript:;" class="btn btn-success" id="addAbtn">+添加照片</a>
                        </div>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6">*</span>城市地区：</label>
                        <select name="dicProvince.id" class="province">
                        <#list provinceList as province>
                            <option value="#{province.id}">${province.name}</option>
                        </#list>
                        </select>
                        <select name="dicCity.id" class="city">
                        <#list cityList as city>
                            <option value="#{city.id}">${city.name}</option>
                        </#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6">*</span>原价：</label>
                        <input class="form-control price" placeholder="0" value="" name="priceDouble"/>
                        <span class="inlineblock ml10">元</span>
                        <input class="form-control unit" placeholder="单位 例如：ml" value="" name="unit"/>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6">*</span>折扣：</label>
                        <input class="form-control discount" placeholder="例如：9.8" name="discount"/>
                    </div>
                    <div id="cmbox">

                    </div>
                    <div id="ptbox">
                        <div class="form-group">
                            <label><span class="star mr6">*</span>团购价：</label>
                            <input class="form-control discountPrice" name="discountPriceDouble"/>
                            <span class="inlineblock ml10">元</span>
                            <input class="form-control discountUnit" placeholder="单位 例如：ml" value="" name="discountUnit"/>
                        </div>
                        <div class="form-group">
                            <label><span class="star mr6">*</span>拼团人数：</label>
                            <input class="form-control peopleNumber" placeholder="0" name="peopleNumber"/>
                        </div>

                        <div class="form-group">
                            <label><span class="star mr6">*</span>单人价格：</label>
                            <input class="form-control alonePrice" placeholder="0" name="alonePriceDouble"/>
                        </div>
                    </div>
                    <div id="djbox">
                        <div class="form-group">
                            <label><span class="star mr6">*</span>订金：</label>
                            <input class="form-control deposit" name="depositDouble"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6"></span>活动时间：</label>
                        <input class="form-control startDate" name="startDate"/>
                        <input class="form-control endDate" name="endDate"/>
                        <a class="btn btn-success" id="cleardate" href="javascript:;">清除</a>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6"></span>限量：</label>
                        <input class="form-control deposit" name="commodityNumber" value="0"/>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6"></span>已售：</label>
                        <input class="form-control deposit" readonly name="sold" value="0"/>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6"></span>自定义已售：</label>
                        <input class="form-control deposit" name="customSold" value="0"/>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6">*</span>商品标签：</label>

                        <div class="checkbox checkdiv">
                        <#list tagList as tag>
                            <label>
                                <input type="checkbox" tagId="#{tag.id}" tagName="${tag.name}">${tag.name}
                            </label>
                        </#list>
                            <input type="text" name="tags" class="tags" value=""
                                   style="width:0px;height:0px;line-height:0px;overflow:hidden;border:none">
                        </div>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6"></span>自定义标签：</label>
                        <input type="radio" class="" name="labelFlag" value="1"/>最新
                        <input type="radio" class="" name="labelFlag" value="2"/>最热
                        <input type="radio" class="" name="labelFlag" value="3"/>限时
                        <input type="radio" class="" name="labelFlag" value="4"/>限量
                        <input type="radio" class="" name="labelFlag" value="0"/>无
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6"></span>案例分析：</label>
                        <input class="form-control" name="caseUrl" value=""/>
                    </div>
                    <div class="form-group">
                        <input type="hidden" name="notice" class="notice">
                        <label><span class="star mr6"></span>注意事项：</label>
                        <!-- 加载编辑器的容器 --><!--这里写你的初始化内容-->
                        <script id="container_notice" name="content_notice" type="text/plain">

                        </script>
                        <!-- 配置文件 -->
                        <script type="text/javascript" src="${PATH}/ueditor/ueditor.config.js"></script>
                        <!-- 编辑器源码文件 -->
                        <script type="text/javascript" src="${PATH}/ueditor/ueditor.all.js"></script>
                        <!-- 实例化编辑器 -->
                        <script type="text/javascript">
                            var ue_notice = UE.getEditor('container_notice');
                        </script>
                    </div>
                    <div class="form-group">
                        <input type="hidden" name="description" class="description">
                        <label><span class="star mr6"></span>商品描述：</label>
                        <!-- 加载编辑器的容器 --><!--这里写你的初始化内容-->
                        <script id="container" name="content" type="text/plain">

                        </script>

                        <!-- 实例化编辑器 -->
                        <script type="text/javascript">
                            var ue = UE.getEditor('container');
                            //                            var config = UE.getOpt('config');
                        </script>
                    </div>
                    <div class="text-center col-lg-10 mt20">
                        <button type="button" class="btn btn-primary btn-lg" id="fusave" onclick="submitCommodity(0);">
                            保存
                        </button>
                        <button type="button" class="btn btn-primary btn-lg ml20" onclick="submitCommodity(1);">保存并上线
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- /#page-wrapper -->

</div>

</body>

</html>
<script type="text/javascript" src="${PATH}/js/backend/commodity.js"></script>