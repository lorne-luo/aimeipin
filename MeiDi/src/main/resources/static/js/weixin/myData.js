/**
 * Created by luanpeng on 16/4/8.
 */
var nowparent = "";
var dialog = new Dialog({
    dialogtext: "<div class='popinputbox'></div><div class='clearfix twobtnbox'><a href='javascript:void(0);' class='t1btn'>取消修改</a><a href='javascript:void(0);' class='t2btn'>保存修改</a></div></div>",
    hasDialog: true,
    dialogClass: "editor",
    markColor: "rgba(0,0,0,0.5)",
    topDialog: "200px"
});
$(".itemsrowA").on("click", function () {
    $(this).find("select").mobiscroll('show');
})
$(".itemsrowB").on("click", function () {
    nowparent = $(this);
    $(this).find('.readyinput').appendTo($(".popinputbox")).show();
    dialog.show();
})
$(".editor").on('click', 'a', function () {
    //console.log($(".editor").find("input").attr("name"),$(".editor").find("input").val());
    if ($(this).hasClass('t2btn')) {
        $.ajax({
            url: BASE_JS_URL + '/business/updateMyInfo',
            type: 'post',
            dataType: 'json',
            data: {
                key: $(".editor").find("input").attr("name"),
                val: $(".editor").find("input").val()
            },
            success: function (data) {

                $(".editor").find("input").parent().hide().appendTo(nowparent);
                nowparent.find('.result').html(nowparent.find("input").val());
                dialog.close();
            }
        })
    } else {
        $(".editor").find("input").parent().hide().appendTo(nowparent);
        dialog.close();
    }
})


function updateInfo(key, val, inst) {
    $.ajax({
        url: BASE_JS_URL + '/business/updateMyInfo',
        type: 'post',
        dataType: 'json',
        data: {
            key: key,
            val: val
        },
        success: function (data) {

            $("." + key).html(inst._value.replace(/,/g, ' '));
            inst.cancel();
        }
    })
}

$('#gender').mobiscroll().select({//性别
    theme: 'ios',
    lang: 'zh',
    display: 'bottom',
    minWidth: 200,
    height: 70,
    onSelect: function (val, inst) {
        updateInfo('gender', $('#gender').val(), inst);
    },
    buttons: [
        {
            text: '确定',
            icon: '',
            cssClass: 'dwb',
            handler: function (event, inst) {
                updateInfo('gender', $('#gender').val(), inst);
            }
        },
        {
            text: '取消',
            icon: '',
            cssClass: 'dwb',
            handler: "cancel"
        }
    ]
});

function cityInit() {
    $('#city').mobiscroll().select({//所在地
        theme: 'ios',
        lang: 'zh',
        display: 'bottom',
        label: 'city',
        group: true,
        groupLabel: '',
        minWidth: 200,
        height: 70,
        onSelect: function (val, inst) {
            updateInfo('city', $('#city').val(), inst);
        },
        buttons: [

            {
                text: '确定',
                icon: '',
                cssClass: 'dwb',
                handler: function (event, inst) {
                    updateInfo('city', $('#city').val(), inst);
                }
            },
            {
                text: '取消',
                icon: '',
                cssClass: 'dwb',
                handler: "cancel"
            }
        ]
    });
}


function channelsInit() {

    $('#channels').mobiscroll().select({//渠道
        theme: 'ios',
        lang: 'zh',
        display: 'bottom',
        minWidth: 200,
        height: 70,
        onSelect: function (val, inst) {
            //updateInfo('channels', $('#channels').val(), inst);
        },
        buttons: [

            {
                text: '确定',
                icon: '',
                cssClass: 'dwb',
                handler: function (event, inst) {
                    updateInfo('channels', inst._value.replace(/,/g, ' '), inst);
                }
            },
            {
                text: '取消',
                icon: '',
                cssClass: 'dwb',
                handler: "cancel"
            }
        ]
    });
}

function interestsInit() {
    $('#interests').mobiscroll().select({//感兴趣
        theme: 'ios',
        lang: 'zh',
        display: 'bottom',
        minWidth: 200,
        rows:5,
        height: 70,
        onSelect: function (val, inst) {
            //updateInfo('interests', $('#interests').val(), inst);
        },
        buttons: [
            {
                text: '确定',
                icon: '',
                cssClass: 'dwb',
                handler: function (event, inst) {
                    updateInfo('interests', inst._value.replace(/,/g, ' '), inst);

                }
            },
            {
                text: '取消',
                icon: '',
                cssClass: 'dwb',
                handler: "cancel"
            }
        ]
    })
}





$(function () {
    var wheresStr = $("#wheresinp").val();
    var wheres = $("#wheres").children();
    if(wheresStr){
        var arr = wheresStr.split(" ");
        for(var i=0;i<arr.length;i++){
            for(var j =0;j<wheres.length;j++){
                if(arr[i] == wheres.eq(j).attr("value")){
                    wheres.eq(j).attr("selected",true);
                    continue;
                }
            }
        }
    }


    getProvinceAndCity();
    getChannels();
    getInterests();



    $('#wheres').mobiscroll().select({//去哪
        theme: 'ios',
        lang: 'zh',
        display: 'bottom',
        minWidth: 200,
        height: 70,
        onSelect: function (val, inst) {
            //updateInfo('wheres', $('#wheres').val(), inst);
        },
        buttons: [
            {
                text: '确定',
                icon: '',
                cssClass: 'dwb',
                handler: function (event, inst) {
                    updateInfo('wheres', inst._value.replace(/,/g, ' '), inst);

                }
            },
            {
                text: '取消',
                icon: '',
                cssClass: 'dwb',
                handler: "cancel"
            }
        ]
    });
})


function getProvinceAndCity() {
    $.ajax({
        url: BASE_JS_URL + '/ajax/getProvinceAndCity',
        data: {},
        type: 'post',
        dataType: 'json',
        success: function (data) {
            createTable(data.resultList);

            cityInit();
        }
    });
}

function getChannels() {
    $.ajax({
        url: BASE_JS_URL + '/ajax/getTags',
        data: {
            flag: 2
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {

            var strs = $('.channels').text().split(' ');
            $.each(data.tagList, function (index, tag) {
                var str = '<option value="' + tag.name + '">' + tag.name + '</option>';
                for (var i = 0; i < strs.length; i++) {
                    if (tag.name == strs[i]) {
                        str = '<option value="' + tag.name + '" selected>' + tag.name + '</option>';
                        break;
                    }
                }

                $('#channels').append(str);
            });

            channelsInit();
        }
    });
}

function getInterests() {
    $.ajax({
        url: BASE_JS_URL + '/ajax/getInterest',
        data: {
            flag: 3
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            var strs = $('.interests').text().split(' ');
            $.each(data.inteList, function (index, inte) {
                var str = '<optgroup label="' + inte.name + '">';
                if (inte.interestCommoditySons != null) {
                    $.each(inte.interestCommoditySons, function (index, son) {
                       var strSon = '<option value="' + son.name + '">' + son.name + '</option>';
                        for(var i=0;i<strs.length;i++){
                            if(strs[i] == son.name){
                                strSon = '<option value="' + son.name + '" selected>' + son.name + '</option>';
                                break;
                            }
                        }
                        str += strSon;
                    });
                }
                str += ' </optgroup>';

                $('#interests').append(str);
            });

            interestsInit();
        }
    });
}


function createTable(list) {
    $.each(list, function (index, result) {
        var str = '<optgroup label="' + result.province.name + '">';
        if (result.cityList != null) {
            $.each(result.cityList, function (index, city) {
                str += '<option value="' + city.id + '">' + city.name + '</option>';
            });
        }
        str += ' </optgroup>';

        $('#city').append(str);
    });
}
