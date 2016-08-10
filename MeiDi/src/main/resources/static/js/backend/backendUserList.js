/**
 * Created by luanpeng on 16/3/26.
 */
$(function () {
    getList(1);


});


var pageNumber = 1;
function getList(page) {

    if (page == 1) {
        pageNumber = page;
    }

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
            url: BASE_JS_URL + "/backend/getBackendUserList",
            dataType: 'json',
            param: {}
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
            '<td>' + user.account + '</td>' +
            '<td>';
        if (user.state == 0) {
            str += '<a href="javascript:activation(' + user.id + ');" class="btn btn-success" >激活</a>';
        } else {
            str += "已激活";
        }

        if(user.flag == 0 && user.state == 1){
            str += '<a href="javascript:disableUser(' + user.id + ');" class="btn btn-success" >禁用</a>';
        }
        str += '</td>' +
            '</tr>';

        $('.addList').append(str);
    });
}

function activation(id) {
    if (confirm('确认激活？', '确定', '取消')) {
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: BASE_JS_URL + '/backend/activationUser',
            data: {
                id: id
            },
            success: function (data) {
                alert("激活成功！");
                getList(pageNumber);
            }
        });
    }
}

function disableUser(id) {
    if (confirm('确认禁用？', '确定', '取消')) {
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: BASE_JS_URL + '/backend/disableUser',
            data: {
                id: id
            },
            success: function (data) {
                alert("禁用成功！");
                getList(pageNumber);
            }
        });
    }
}
