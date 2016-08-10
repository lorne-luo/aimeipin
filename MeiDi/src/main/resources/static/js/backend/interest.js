/**
 * Created by luanpeng on 16/4/21.
 */
var conlistbox = $(".tab-content");
var nav = $(".nav.nav-pills");
nav.on("click", ".close", function () {
    var index = $(this).parent().index();
    var allpane = conlistbox.find(".tab-pane");
    var newpane = allpane.eq(index);
    var thisparent = $(this).parent();
    var li = nav.find("li");
    if (thisparent.hasClass("active")) {
        if (allpane.length != 1) {
            if (index + 1 == allpane.length) {//最后一个
                allpane.eq(0).addClass("active in");
                li.eq(0).addClass("active");
            } else {
                allpane.eq(index + 1).addClass("active in");
                li.eq(index + 1).addClass("active");
            }

        }


    }
    thisparent.remove();
    newpane.remove();

})


var str = "";
var servicename = $("#servicename");
var bigapartname = $("#bigapartname");

function create(name, level) {
    //var val = $.trim(obj.val());
    if (!name) {
        alert("不能为空");
        return false;
    }
    //if(!checkcommon(obj)){
    //    alert("已经存在该类目");
    //    obj.val("");
    //    obj.focus();
    //    return false;
    //}
    //creatnewstr(val);
    // var data = {};
    // data[dataname] = val;
    var parentId = nav.find('li.active').attr('pid');
    if(!parentId || parentId ==''){

        if(level == 1){
            parentId = 0;
        }else{
            alert("请选择一级活着添加一级");
            return;
        }
    }
    $.ajax({
        url: BASE_JS_URL + "/backend/addInterest",
        type: 'post',
        dataType: 'json',
        data: {
            name: name,
            level: level,
            parentId: parentId
        },
        success: function (data) {
            if(level == 1){
                creatnewbigstr(name,data.id);
            }else{
                creatnewlitstr(name,data.id);
            }
        }
    })
}


$("#creatnewbig").on("click", function () {//大类别
    var name = $.trim($('#bigapartname').val());
    create(name, 1);

})

$("#creatnew").on("click", function () {//类别里的小项目
    var name = $.trim($('#servicename').val());
    create(name, 2);
})


function creatnewlitstr(val,id) {//msg
    str = '<span class="alert alert-info pr" role="alert" tipid="' + id + '" id="son_' + id + '">'
        + '<button type="button" class="close" data-dismiss="alert" aria-label="Close" onclick="deleteInterest(' + id + ',2)"><span aria-hidden="true">×</span></button>'
        + '<strong>' + val + '</strong>'
        + '</span>'
    conlistbox.find(".active").find(".myitems").append(str);
}
function creatnewbigstr(val,id) {//msg
    var idhref = "s" + "_" + new Date().getTime();
    nav.children().removeClass("active");
    conlistbox.children().removeClass("active in");
    str = '<li class="active" pid="' + id + '" id="parent_' + id + '"><a href="#' + idhref + '" data-toggle="tab" aria-expanded="false">' + val + '</a>'
        + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"  onclick="deleteInterest(' + id + ',1)"><span aria-hidden="true">×</span></button></li>'
    nav.append(str);
    str = '<div class="tab-pane fade active in" id="' + idhref + '"><h4></h4><div class=" myitems clearfix mb20"></div></div>';
    conlistbox.append(str);
    servicename.focus();
}
function checkcommon(obj) {
    var strong = obj.attr("id") == "servicename" ? conlistbox.find(".active").find(".myitems").find("strong") : nav.find("a");
    var length = strong.length;
    for (var i = 0; i < length; i++) {
        if ($.trim(strong.eq(i).html()) == $.trim(obj.val())) {
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


function deleteInterest(id,level) {
    $.ajax({
        url: BASE_JS_URL + "/backend/deleteInterest",
        type: 'POST',
        data: {
            id: id,
            level:level
        },
        dataType: 'json',
        success: function (data) {
            if(level == 1){



                $('#parent_' + id).remove();
                $('#home-pills_' + id).remove();


            }else{
                $('#son_' + id).remove();
            }
        }
    })
}
