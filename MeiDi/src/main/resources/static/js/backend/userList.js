/**
 * Created by luanpeng on 16/3/26.
 */
$(function () {
    getList(1);

    $('.provinceId').on('change',function(){
        var parentId = $(this).val();
        $.ajax({
            url: BASE_JS_URL + "/ajax/getCityList",
            type:"post",
            data:{
                id:parentId
            },
            dataType:"json",
            success:function(data){
                $('.cityId').html('');
                $('.cityId').append("<option value='0'>请选择</option>");
                $.each(data.cityList,function(index, city){
                    var str = '<option value="' + city.id + '">' + city.name + '</option>';
                    $('.cityId').append(str);
                })
            }
        })
    })

    $('.cityId').on('change', function(){
        //var cityId = $(this).val();
        getList(1);
    })

});


function searchUser(){
    getList(1);
}


var pageNumber = 1;
function getList(page) {

    if (page == 1) {
        pageNumber = page;
    }
    var queryStr = $('.queryStr').val();

    var cityId = $('.cityId').val();

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
                createTable(data.userList);
            },
            url: BASE_JS_URL + "/backend/getUserList",
            dataType: 'json',
            param: {
                cityId: cityId,//不限城市
                queryStr: queryStr
            }
        }
    });
}

function createTable(result) {
    $('.addList').html('');
    $.each(result, function (index, user) {
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
        str += '<th scope="row">' + (index + 1) + '</th>' +
            '<td>' + user.nickname + '</td>' +
            '<td>' + user.integral + '</td>' +
            '<td>' + user.name + '</td>' +
            '<td>';
        if (user.gender == 1) {
            str += "男";
        } else if (user.gender == 2) {
            str += "女";
        } else {
            str += "未知";
        }
        str += '</td>' +
            '<td>' + user.mobile + '</td>' +
            '<td>';
        if (user.dicCity != null) {
            str += user.dicCity.name;
        } else {
            str += "暂无";
        }
        str += '</td>';
        str += '<td>' + user.channels + '</td>';

        str += '<td>' + user.interests + '</td>' +
            '<td>' + user.wheres + '</td>' +
            '</tr>';

        $('.addList').append(str);
    });
}
