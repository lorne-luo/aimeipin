/**
 * Created by luanpeng on 16/3/26.
 */
$(function () {
    getList(1);

    $('#flag').on("change",function(){
        getList(1);
    });
    $('#state').on("change",function(){
        getList(1);
    });
    $('#province').on("change",function(){
        getList(1);
    });
    $('#category').on("change",function(){
        getList(1);
    });
});

function searchCommodity(){
    getList(1);
}


var pageNumber = 1;
function getList(page) {

    if(page == 1){
        pageNumber = page;
    }

    var flag = $('#flag').val();
    var state = $('#state').val();
    var province = $('#province').val();
    var category = $('#category').val();

    var queryStr = $('.queryStr').val();
    ZENG.msgbox.loadingAnimationPath = BASE_JS_URL + "/images/loading.gif";
    $("#pagediv").myPagination({
        cssStyle: 'scott',
        currPage: pageNumber,
        ajax: {
            on: true,
            ajaxStart: function () {
                ZENG.msgbox.show(" 正在加载中，请稍后...", 6, 10000);
            },
            callback: function (data) {
                ZENG.msgbox.hide(); //隐藏加载提示
                pageNumber = data.pageNumber;
                createTable(data.commodityList);
            },
            url: BASE_JS_URL + "/backend/getCommodityList",
            dataType: 'json',
            param: {
                flag: flag,
                state: state,
                provinceId: province,
                categoryId: category,
                queryStr: queryStr
            }
        }
    });
}

function createTable(result) {
    $('.addList').html('');
    $.each(result, function (index, commodity) {
        var str = '';
        if (index == 0) {
            str += '<tr class="active">';
        } else if (index == 2) {
            str += '<tr class="success">';
        } else if (index == 4) {
            str += '<tr class="info">';
        } else if (index == 6) {
            str += '<tr class="warning">';
        } else if (index == 8) {
            str += '<tr class="danger">';
        } else {
            str += '<tr>';
        }

        var category_name = commodity.category.name == null?'':commodity.category.name;

        str += '<th scope="row">' + commodity.id + '</th>' +
            //'<td>' + project.projectCode + '</td>' +
            '<td>' + getProjectFlag(commodity.flag) + '</td>' +
            '<td>' + category_name + '</td>' +
            '<td class="tal" style="max-width: 300px;">' + commodity.name + '</span></td>'+
            '<td>' + commodity.priceDouble + '</td>' +
            '<td>' + commodity.discount + '</td>' +
            '<td>' + commodity.discountPriceDouble + '</td>';
        if (commodity.state == 0) {
            str += '<th class="cinfo">未上架</th>';
        } else if (commodity.state == 1) {
            str += '<th class="cprimary">已上架</th>';
        }

        str +='<td>' + commodity.dicCity.name + '</td>' +
            '<td>' + getDate(commodity.createTime) + '</td>' +
            '<td><a href="javascript:weight(' + commodity.id + ');" class="btn btn-success" target="_blank">' + commodity.weight + '</a></td>' +
            '<td>'+
            '<a href="/backend/editCommodityPage/' + commodity.id + '" class="btn btn-success" target="_blank">编辑</a> ';

        if (commodity.state == 0) {
            str += '<a href="javascript:upProject(' + commodity.id + ');" class="btn btn-warning">上架</a> ';
        } else if (commodity.state == 1) {
            str += '<a href="javascript:downProject(' + commodity.id + ');" class="btn btn-warning">下架</a> ';
        }
        str += '<a href="javascript:deleteProject(' + commodity.id + ');" class="btn btn-warning">删除</a>';
        str += '</td>' +
            '</tr>';

        $('.addList').append(str);
    });
}

function upProject(id) {
    if (confirm('确认上架？', '确定', '取消')) {
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: BASE_JS_URL + '/backend/upCommodity',
            data: {
                id: id
            },
            success: function (data) {
                alert("上架成功！");
                getList(pageNumber);
            }
        });
    }
}

function downProject(id) {
    if (confirm('确认下架？', '确定', '取消')) {
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: BASE_JS_URL + '/backend/downCommodity',
            data: {
                id: id
            },
            success: function (data) {
                alert("下架成功！");
                getList(pageNumber);
            }
        });
    }
}

function deleteProject(id){
    if (confirm('确认删除？', '确定', '取消')) {
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: BASE_JS_URL + '/backend/deleteCommodity',
            data: {
                id: id
            },
            success: function (data) {
                alert("删除成功！");
                getList(pageNumber);
            }
        });
    }
}

var dialog = new Dialog({
    dialogtext: "<div class='pr bgf6'><p class='p10 tal bgWhite fs20 p555'>修改权重</p><span class='close'></span><div class='pt40 tac'><input type='text' class='myform-control w340 p10 fs20 weight'><div class='tac pb30  mt60'><a href='javascript:updateWeight();' class='btn btn-success'>提交</a></div></div>",
    hasDialog: true,
    dialogClass: "erroralert",
    markColor: "rgba(0,0,0,0.5)"
});

var commodityId ;
function weight(id){
    commodityId = id;
    dialog.show();
}

function updateWeight(){
    var weight = $('.weight').val();
    if (weight == '') {
        dialog.close();
        return;
    }
    $.ajax({
        url: BASE_JS_URL + '/backend/updateWeight',
        data: {
            'weight': weight,
            'commodityId':commodityId
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            dialog.close();
            $('.weight').val('');
            getList(pageNumber);

        }
    });
}

function getProjectFlag(flag) {
    switch (flag) {
        case 1:
            return "拼团";
        case 2:
            return "福袋";
        case 3:
            return "特惠";
        case 4:
            return "咨询";
    }
}