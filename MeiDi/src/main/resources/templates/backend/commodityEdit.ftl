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
<#--<script src="${PATH}/ueditor/ueditor.parse.js"></script>-->
    <script>
        window.UEDITOR_HOME_URL = BASE_JS_URL + "/ueditor/";
        <#--uParse("#description",{-->
        <#--rootPath:'${PATH}/ueditor'-->
        <#--});-->
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
                    <input type="hidden" name="id" value="#{commodity.id}">

                    <div class="form-group">
                        <label><span class="star mr6">*</span>商品类型：</label>
                        <select name="flag" class="flag" disabled>
                            <option value="1" <#if commodity.flag == 1>selected</#if>>拼团</option>
                            <option value="2" <#if commodity.flag == 2>selected</#if>>福袋</option>
                            <option value="3" <#if commodity.flag == 3>selected</#if>>特惠</option>
                            <option value="4" <#if commodity.flag == 4>selected</#if>>咨询</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6">*</span>分类类型：</label>
                        <select name="category.id" class="category">
                            <option value="">------</option>
                        <#list categoryList as category>
                            <option value="#{category.id}"
                                    <#if commodity.category?exists && commodity.category.id == category.id>selected</#if>>${category.name}</option>
                        </#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6">*</span>商品名称：</label>
                        <input class="form-control" placeholder="例如：为你打造美丽" name="name" value="${commodity.name}"/>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6">*</span>商品关键词：</label>
                        <input class="form-control" placeholder="例如：为你打造美丽" name="keyword"
                               value="${commodity.keyword}"/>
                    </div>
                    <div class="form-group">
                        <div id="dbpicbox" class="dbpicbox  clearfix ">
                        <#list commodity.commodityPhotoList as photo>
                            <div class="piclist fl pr text-center">
                                <img class="" src="${IMAGE_FORMAL_URL}/${photo.imageName}">

                                <div class="text-center">
                                    <a class="btn btn-warning" backtip="#{photo.id}">删除</a>
                                </div>
                            </div>
                        </#list>
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
                            <option value="#{province.id}"
                                    <#if commodity.dicProvince.id == province.id>selected</#if>>${province.name}</option>
                        </#list>
                        </select>
                        <select name="dicCity.id" class="city">
                        <#list cityList as city>
                            <option value="#{city.id}"
                                    <#if commodity.dicCity.id == city.id>selected</#if>>${city.name}</option>
                        </#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6">*</span>原价：</label>
                        <input class="form-control price" placeholder="0" value="<#if commodity.priceDouble?exists && commodity.priceDouble != 0>#{commodity.priceDouble}<#else>#{commodity.price}</#if>" name="priceDouble"/>
                        <span class="inlineblock ml10">元</span>
                        <input class="form-control unit" placeholder="单位 例如：ml" value="<#if commodity.unit?exists>${commodity.unit}</#if>" name="unit"/>
                    </div>

                    <div class="form-group">
                        <label><span class="star mr6">*</span>折扣：</label>
                        <input class="form-control discount" placeholder="例如：9.8" value="${commodity.discount}"
                               name="discount"/>
                    </div>
                    <div id="cmbox">
                    <#if commodity.flag == 2 || commodity.flag == 3 || commodity.flag == 4>
                        <div class="form-group">
                            <label><span class="star mr6">*</span>现价：</label>
                            <input class="form-control discountPrice" value="<#if commodity.discountPriceDouble?exists && commodity.discountPriceDouble != 0>#{commodity.discountPriceDouble}<#else>#{commodity.discountPrice}</#if>"
                                   name="discountPriceDouble"/>
                            <span class="inlineblock ml10">元</span>
                            <input class="form-control discountUnit" placeholder="单位 例如：ml" value="<#if commodity.discountUnit?exists>${commodity.discountUnit}</#if>" name="discountUnit"/>
                        </div>
                    </#if>
                    </div>
                    <div id="ptbox">
                    <#if commodity.flag == 1>
                        <div class="form-group">
                            <label><span class="star mr6">*</span>团购价：</label>
                            <input class="form-control discountPrice" value="<#if commodity.discountPriceDouble?exists && commodity.discountPriceDouble != 0 >#{commodity.discountPriceDouble}<#else>#{commodity.discountPrice}</#if>"
                                   name="discountPriceDouble"/>
                            <span class="inlineblock ml10">元</span>
                            <input class="form-control discountUnit" placeholder="单位 例如：ml" value="<#if commodity.discountUnit?exists>${commodity.discountUnit}</#if>" name="discountUnit"/>
                        </div>
                        <div class="form-group">
                            <label><span class="star mr6">*</span>拼团人数：</label>
                            <input class="form-control peopleNumber" placeholder="0" value="${commodity.peopleNumber}"
                                   name="peopleNumber"/>
                        </div>
                        <div class="form-group">
                            <label><span class="star mr6">*</span>单人价格：</label>
                            <input class="form-control alonePrice" placeholder="0" value="<#if commodity.alonePriceDouble?exists && commodity.alonePriceDouble != 0>#{commodity.alonePriceDouble}<#else>#{commodity.alonePrice}</#if>"
                                   name="alonePriceDouble"/>
                        </div>
                    </#if>
                    </div>
                    <div id="djbox">
                        <#if commodity.flag == 1 || commodity.flag == 2 || commodity.flag == 3>
                            <div class="form-group">
                                <label><span class="star mr6">*</span>订金：</label>
                                <input class="form-control deposit" name="depositDouble" value="<#if commodity.depositDouble?exists && commodity.depositDouble != 0>#{commodity.depositDouble}<#else>#{commodity.deposit}</#if>"/>
                            </div>
                        </#if>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6"></span>活动时间：</label>
                        <input class="form-control startDate" name="startDate"
                               value="<#if commodity.startDate?exists>${commodity.startDate}</#if>"/>
                        <input class="form-control endDate" name="endDate"
                               value="<#if commodity.endDate?exists>${commodity.endDate}</#if>"/>
                        <a class="btn btn-success" id="cleardate" href="javascript:;">清除</a>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6"></span>限量：</label>
                        <input class="form-control deposit" name="commodityNumber"
                               value="${commodity.commodityNumber}"/>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6"></span>已售：</label>
                        <input class="form-control deposit" readonly name="sold" value="${commodity.sold}"/>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6"></span>自定义已售：</label>
                        <input class="form-control deposit" name="customSold" value="${commodity.customSold}"/>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6">*</span>商品标签：</label>

                        <div class="checkbox checkdiv">
                        <#list tagList as tag>
                            <label>
                                <input type="checkbox" tagId="#{tag.id}" tagName="${tag.name}">${tag.name}
                            </label>
                        </#list>
                            <input type="text" name="tags" class="tags" value="${commodity.tags}"
                                   style="width:0px;height:0px;line-height:0px;overflow:hidden;border:none">
                        </div>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6"></span>自定义标签：</label>
                        <input type="radio" <#if commodity.labelFlag?exists && commodity.labelFlag == 1>checked</#if>
                               name="labelFlag" value="1"/>最新
                        <input type="radio" <#if commodity.labelFlag?exists && commodity.labelFlag == 2>checked</#if>
                               name="labelFlag" value="2"/>最热
                        <input type="radio" <#if commodity.labelFlag?exists && commodity.labelFlag == 3>checked</#if>
                               name="labelFlag" value="3"/>限时
                        <input type="radio" <#if commodity.labelFlag?exists && commodity.labelFlag == 4>checked</#if>
                               name="labelFlag" value="4"/>限量
                        <input type="radio" <#if commodity.labelFlag?exists && commodity.labelFlag == 0>checked</#if>
                               name="labelFlag" value="0"/>无
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6"></span>案例分析：</label>
                        <input class="form-control" name="caseUrl"
                               value="<#if commodity.caseUrl?exists>${commodity.caseUrl}</#if>"/>
                    </div>
                    <div class="form-group">
                        <label><span class="star mr6"></span>分享摘要：</label>
                        <div>
                            <textarea rows="3" style="width:60%" class="form-control" name="sharingSummary"><#if commodity.sharingSummary?exists>${commodity.sharingSummary}</#if></textarea>
                        </div>
                    </div>
                    <div class="form-group" id="remarks">
                        <input type="hidden" name="remarks" class="remarks">
                        <label><span class="star mr6"></span>支付说明：</label>
                        <!-- 加载编辑器的容器 --><!--这里写你的初始化内容-->
                        <script id="container_remarks" name="content_remarks" type="text/plain">

                        </script>
                        <!-- 配置文件 -->
                        <script type="text/javascript" src="${PATH}/ueditor/ueditor.config.js"></script>
                        <!-- 编辑器源码文件 -->
                        <script type="text/javascript" src="${PATH}/ueditor/ueditor.all.js"></script>
                        <!-- 实例化编辑器 -->
                        <script type="text/javascript">
                            var ue_remarks = UE.getEditor('container_remarks');
                            ue_remarks.ready(function () {
                                ue_remarks.setContent('<#if commodity.remarks?exists>${commodity.remarks}</#if>');
                            });
                        </script>
                    </div>
                    <div class="form-group" id="description">
                        <input type="hidden" name="description" class="description">
                        <label><span class="star mr6"></span>商品描述：</label>
                        <!-- 加载编辑器的容器 --><!--这里写你的初始化内容-->
                        <script id="container" name="content" type="text/plain">

                        </script>

                        <!-- 实例化编辑器 -->
                        <script type="text/javascript">
                            var ue = UE.getEditor('container');
                            ue.ready(function () {
                                ue.setContent('${commodity.description}');
                            });
                            //                            var config = UE.getOpt('config');
                        </script>
                    </div>
                    <div class="text-center col-lg-10 mt20">
                        <button type="button" class="btn btn-primary btn-lg" id="fusave" onclick="submitCommodity(0);">
                            保存
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
<script type="text/javascript" src="${PATH}/js/backend/commodityEdit.js"></script>