/**
 * Created by luanpeng on 16/3/30.
 */
var sh;
$(function () {
    $('.multiple-items').slick({
        azyLoad: 'ondemand',
        infinite: true,
        slidesToShow: 1,
        slidesToScroll: 1,
        dots: true,
        arrows: false,
        autoplay: true,
        autoplaySpeed: 2000,
        pauseOnHover: false
    });

    getGroupLaunch();

});

/**
 * 获取正在被发起的拼团
 */
function getGroupLaunch() {
    var flag = $('.commodityFlag').val();
    if(flag != 1){
        return;
    }

    var  id = $('.commodityId').val();
    $.ajax({
        url: BASE_JS_URL + '/business/getGroupLaunch',
        data: {
            'commodityId': id
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            createTable(data.result);
        }
    })

}

function createTable(result) {
    $.each(result, function (index, launch) {
        var str = '<div class="pt20 pb20 goutuanbox mt16">' +
            '<div class="clearfix gogroup fs18">' +
            '<div class="fl msgbox">' +
            ' <div class="twoimg pr">' +
            ' <img src="' + BASE_JS_URL + '/images/fog.png" class="t1">' +
            ' <img src="' + launch.headimgurl + '" class="t2 pa">' +
            ' </div>' +
            ' <p class="headimg">' + launch.nickname + '</p>' +
            ' </div>' +

            '<div class="fl  mr10 tar">' +
            ' <p class="mt20">' +
            ' 正在发起拼团，还差<span>' + (launch.groupLaunch.peopleNumber - launch.groupLaunch.groupLaunchUserList.length) + '</span>人成团' +
            ' </p>' +
            ' <p class="times" id="times_' + index + '" endtime="' + launch.groupLaunch.endTime +'">剩余时间：' +
            '<span class="f40b0b">23</span><i class="f40b0b">:</i>' +
            '   <span class="f40b0b">55</span><i class="f40b0b">:</i>' +
            '   <span class="f40b0b">59</span>' +
            '  </p>' +
            '  </div>' +
            '   <div class="fl">' +
            '   <a href="javascript:joinGroup(' + launch.groupLaunch.id +');" class="mt20"></a>' +
            '  </div>' +

            '  </div>' +
            '  </div>';
        $('.addList').append(str);

        new Countdown({
                    timeBox: $('#times_' + index),
                    endTime: launch.groupLaunch.endTime
                });
    });
}


//收藏动作
function favoriteAction(id, openid) {
    if (!openid){
        // not login, redirect to login entrypoint
        window.location ='/business/login';
    }

    $.ajax({
        url: BASE_JS_URL + '/business/favoriteAction',
        data: {
            'id': id
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if ($('.favorite2').hasClass('hide')) {
                $('.favorite2').removeClass("hide");
                $('.favorite1').addClass("hide");
            } else {
                $('.favorite2').addClass("hide");
                $('.favorite1').removeClass("hide");
            }
        }
    });
}

function joinGroup(id) {
    $.ajax({
        url: BASE_JS_URL + '/business/checkJoinGroup',
        data: {
            'launchId': id
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.ret == 0) {
                window.location.href = BASE_JS_URL + "/business/joinGroupPage/" + id;
            } else if (data.ret == -1) {
                //错误信息
                alert("您已在此团中，不可重复参团！");
            } else if(data.ret == -2 || data.ret == -4){
                alert("该拼团已结束！");
            } else if(data.ret == -3){
                alert("该拼团人数已满！");
            }
        }
    });
}

