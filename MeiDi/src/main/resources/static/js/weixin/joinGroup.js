/**
 * Created by luanpeng on 16/4/9.
 */
$(function () {
    var state = $('#state').val();
    if (state == 0) {
        new Countdown({
            timeBox: $('#times'),
            endTime: $('#times').attr('endtime')
        });
    }
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


    $('.sharemark').on('click', function () {
        $(this).hide();
    })

    youLike();

});

function checkJoinGroup(launchId) {
    $.ajax({
        url: BASE_JS_URL + '/business/checkJoinGroup',
        data: {
            'launchId': launchId
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.ret == 0) {
                window.location.href = BASE_JS_URL + "/business/bookingPage/" + launchId + "/4";
            } else if (data.ret == -1) {
                //错误信息
                alert("您已在此团中，不可重复参团！");
            } else if (data.ret == -2 || data.ret == -4) {
                alert("该拼团已结束！");
            } else if (data.ret == -3) {
                alert("该拼团人数已满！");
            }
        }
    });
}

//获取猜你喜欢的数据
//同地区下同类别项目
function youLike() {
    var commodityId = $('#commodityId').val();
    var cityId = $('#cityId').val();
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
                $('.addList').parent().hide();
                return;
            }
            createTable(data.commodityList);
        }
    });
}

function createTable(commodityList) {


    $.each(commodityList, function (index, commodity) {
        var str = '';
        if (commodity.flag == 1) {//拼团项目
            str += '<div class="itemlist shadowall mb40">'+
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
                '        <span class=" ml50 mr10 fl fs16">原价：<del>' + commodity.price / 100 + '</del></span>' +
                '        <span class="fs24 fl">' + commodity.peopleNumber + '人团：¥' + commodity.discountPrice / 100 + '</span>' +
                '        <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '"></a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '</div>';
        } else if (commodity.flag == 2) {//福袋
            str += '<div class="itemlist shadowall mb40">'+
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
                '        <span class=" ml50 mr10 fl fs16">原价：<del>' + commodity.price / 100 + '</del></span>' +
                '        <span class="fs24 fl">现价：¥' + commodity.discountPrice / 100 + '</span>' +
                '        <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '"></a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '</div>';
        } else if (commodity.flag == 3) {//特惠
            str += '<div class="itemlist shadowall mb40">'+
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
                '        <span class=" ml50 mr10 fl fs16">原价：<del>' + commodity.price / 100 + '</del></span>' +
                '        <span class="fs24 fl">现价：¥' + commodity.discountPrice / 100 + '</span>' +
                '        <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '"></a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '</div>';
        }

        $('.addList').append(str);
    });

}
