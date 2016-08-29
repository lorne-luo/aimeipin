/**
 * Created by luanpeng on 16/3/23.
 */

var flag = 1;
var page = 1;
var cityId = -1;
$(function () {

    getProjectList(1, 1,-1, '');

});

/**
 *
 * @param categoryId 项目分类 －1 查所有
 * @param pageNumber 页码 1 表示第一页 0 表示继续查询
 * @param city 地区 －1 查所有
 */
function setProjectFlagAndGetProject(categoryId, city, pageNumber, tag) {
    $('.t' + tag).parent().find('.active').removeClass('active');
    $('.t' + tag).addClass('active');

    cityId = city;
    getProjectList(categoryId, pageNumber, cityId,'');

    $('.getmore').show();

}

function getMore(){
    getProjectList(flag, 0,cityId, '');
}

