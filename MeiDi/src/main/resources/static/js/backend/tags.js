/**
 * Created by luanpeng on 16/4/21.
 */
var myitems = $(".myitems");
var str = "";
var newStr = $("#new");


$(function(){
    $("#add").on("click", function () {
        var val = $.trim(newStr.val());
        if (!val) {
            alert("不能为空");
            return false;
        }
        if (!checkCommon()) {
            alert("已经存在该类目");
            newStr.val("");
            newStr.focus();
            return false;
        }
        addOne(val);
    })
});




function checkCommon() {
    var strong = myitems.find("strong");
    var length = strong.length;
    for (var i = 0; i < length; i++) {
        if ($.trim(strong.eq(i).html()) == $.trim(newStr.val())) {
            return false;
        }
    }
    return true;
}

function addOne(val) {
    var flag = $('#flag').val();
    $.ajax({
        url: BASE_JS_URL + "/ajax/addTag",
        type: 'POST',
        data: {
            flag: flag,
            val: val
        },
        dataType: 'json',
        success: function (data) {

            str = '<span id="tag_#{tag.id}" class="alert alert-info pr" role="alert" tipid=0>' +
                '  <button type="button" class="close" data-dismiss="alert" aria-label="Close" onclick="deleteOne(' + data.id + ');">' +
                '    <span aria-hidden="true">×</span>' +
                '  </button>' +
                '  <strong>' + val + '</strong>' +
                '</span>';
            myitems.append(str);
        }
    })
}


function deleteOne(id) {
    $.ajax({
        url: BASE_JS_URL + "/ajax/deleteTag",
        type: 'POST',
        data: {
            id: id
        },
        dataType: 'json',
        success: function (data) {
            $('#tag_' + id).remove();
        }
    })
}
