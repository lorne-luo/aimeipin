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

    // 置顶tab导航条
    var top=$(".tabClick").offset().top;
    $(window).scroll(function () {
        if ($(window).scrollTop() >= top) {
            $(".tabClick").attr("style", "position:fixed;top:0;left:0;z-index:10;width:100%");
        } else {
            $(".tabClick").attr("style", "");
        }
    }).trigger("scroll");

    getGroupLaunch();

    youLike();
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

    if (result.length>0){
        $('.addList').append('<p class="tal fs14">本项目有下列拼团正在进行中, 点击“去参团”参加:</p>');
    }

    $.each(result, function (index, launch) {
        var str = '<div class="pt20 pb20 goutuanbox mt10">' +
            '<div class="clearfix gogroup">' +
            '<div class="fl msgbox">' +
            ' <div class="twoimg pr">' +
            ' <img src="' + BASE_JS_URL + '/images/fog.png" class="t1">' +
            ' <img src="' + launch.headimgurl + '" class="t2 pa">' +
            ' </div>' +
            ' <p class="headimg">' + launch.nickname + '</p>' +
            ' </div>' +

            '<div class="fl mr10 tar fs14 line20">' +
            ' <p>' +
            ' 正在发起拼团，还差<span>' + (launch.groupLaunch.peopleNumber - launch.groupLaunch.groupLaunchUserList.length) + '</span>人成团' +
            ' </p>' +
            ' <p class="times" id="times_' + index + '" endtime="' + launch.groupLaunch.endTime +'">剩余时间：' +
            '<span class="f40b0b">23</span><i class="f40b0b">:</i>' +
            '   <span class="f40b0b">55</span><i class="f40b0b">:</i>' +
            '   <span class="f40b0b">59</span>' +
            '  </p>' +
            '  </div>' +
            '   <div class="fl">' +
            '   <a href="javascript:joinGroup(' + launch.groupLaunch.id +');"></a>' +
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

function redirectLogin() {
    window.location ='/business/login';
}


//收藏动作
function favoriteAction(id) {
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


//获取猜你喜欢的数据
//同地区下同类别项目
function youLike() {
    var commodityId = $('.commodityId').val();
    var cityId = $('.cityId').val();
    $.ajax({
        url: BASE_JS_URL + '/business/getYouLikeCommodity',
        data: {
            'cityId': cityId,
            'commodityId': commodityId
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.commodityList == null || data.commodityList.length <= 0) {
                $('#youlike').parent().hide();
                return;
            }
            createTable_youlike(data.commodityList);
        }
    });
}

function createTable_youlike(commodityList) {

    $.each(commodityList, function (index, commodity) {
        // only show first 5
        if (index > 4){
            return;
        }

        var str = '';
        if (commodity.flag == 1) {//拼团项目
            str += '<div class="itemlist shadowall mb20">'+
                '  <div class="focusImgs pr ">' +
                '    <div class="slider multiple-items">' +
                '      <div>' +
                '        <a href="javascript:;">';
            if (commodity.commodityPhotoList != null && commodity.commodityPhotoList.length > 0) {
                str += '          <img src=' + IMAGE_FORMAL_URL + '/' + commodity.commodityPhotoList[0].imageName + '>';
            }
            str += '        </a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '  <div class="tal">' +
                '    <p class="pl16 line24 fs20 pr16 mt4">' + commodity.name + '</p>' +
                '    <div class=" pr price">' +
                '      <div class="lefticon "></div>' +
                '      <div class="righticon cleafix pr ">' +
                '        <span class=" ml50 mr10 fl fs12">原价：<del>' + commodity.price / 100 + '</del></span>' +
                '        <span class="fs16 fl">' + commodity.peopleNumber + '人团：¥' + commodity.discountPrice / 100 + '</span>' +
                '        <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '"></a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '</div>';
        } else if (commodity.flag == 2) {//福袋
            str += '<div class="itemlist shadowall mb20">'+
                '  <div class="focusImgs pr ">' +
                '    <div class="slider multiple-items">' +
                '      <div>' +
                '        <a href="javascript:;">';
            if (commodity.commodityPhotoList != null && commodity.commodityPhotoList.length > 0) {
                str += '          <img src=' + IMAGE_FORMAL_URL + '/' + commodity.commodityPhotoList[0].imageName + '>';
            }
            str += '        </a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '  <div class="tal">' +
                '    <p class="pl16 line24 fs20 pr16 mt4">' + commodity.name + '</p>' +
                '  <div class=" pr price">' +
                '    <div class="lefticon fu"></div>' +
                '      <div class="righticon cleafix pr qiang">' +
                '        <span class=" ml50 mr10 fl fs12">原价：<del>' + commodity.price / 100 + '</del></span>' +
                '        <span class="fs16 fl">现价：¥' + commodity.discountPrice / 100 + '</span>' +
                '        <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '"></a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '</div>';
        } else if (commodity.flag == 3) {//特惠
            str += '<div class="itemlist shadowall mb20">'+
                '  <div class="focusImgs pr ">' +
                '    <div class="slider multiple-items">' +
                '      <div>' +
                '        <a href="javascript:;">';

            if (commodity.commodityPhotoList != null && commodity.commodityPhotoList.length > 0) {
                str += '          <img src=' + IMAGE_FORMAL_URL + '/' + commodity.commodityPhotoList[0].imageName + '>';
            }

            str += '        </a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '  <div class="tal">' +
                '    <p class="pl16 pr16 line24 fs20 mt4">' + commodity.name + '</p>' +
                '    <div class=" pr price">' +
                '      <div class="lefticon sale"></div>' +
                '      <div class="righticon cleafix pr qiang">' +
                '        <span class=" ml50 mr10 fl fs12">原价：<del>' + commodity.price / 100 + '</del></span>' +
                '        <span class="fs16 fl">现价：¥' + commodity.discountPrice / 100 + '</span>' +
                '        <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '"></a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '</div>';
        }

        $('.youlikeList').append(str);
    });

}
