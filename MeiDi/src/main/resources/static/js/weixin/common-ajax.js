/**
 * Created by luanpeng on 16/3/24.
 */
var page = 1;
function getProjectList(categoryId, pageNumber, cityId, queryStr) {
    if (pageNumber == 1) {
        page = pageNumber;
    }
    ZENG.msgbox.loadingAnimationPath = BASE_JS_URL + "/images/loading.gif";
    ZENG.msgbox.show(" 正在加载中，请稍后...", 6, 10000);
    $.ajax({
        url: BASE_JS_URL + '/business/getCommodityList',
        data: {
            'categoryId': categoryId,
            'page': page,
            'state': 1,
            'cityId': cityId,
            'queryStr': queryStr
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (page == 1) {
                $('.itemlistbox').html('');
            }

            page = data.pageNumber + 1;

            if (data.commodityList == null || data.commodityList.length < data.pageSize) {
                $('.getmore').hide();
            }

            createTable(data.commodityList);
            ZENG.msgbox.hide(); //隐藏加载提示
        }
    });
}

function createTable(commodityList) {
    var str = '';
    $.each(commodityList, function (index, commodity) {
        //if(page == 1 && index){
        //
        //}
        str += '<div class="itemlist shadowall mb40">';
        if (commodity.flag == 1) {//拼团项目
            str += '  <div class="focusImgs pr ">' +
                '    <div class="slider multiple-items">' +
                '      <div>' +
                '        <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '">';
            if (commodity.commodityPhotoList != null && commodity.commodityPhotoList.length > 0) {
                str += '          <img src=' + IMAGE_FORMAL_URL + '/' + commodity.commodityPhotoList[0].imageName + '>';
            }
            str += '        </a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '  <div class="water">' +
                '    <p class="fs20 mt15 ml18">' + commodity.discount + '折</p>' +
                '    <p class="fs20 ml18">' + commodity.peopleNumber + '人团</p>' +
                '  </div>';

            if (commodity.labelFlag == 1) {
                str += '<div class="new"></div>';
            } else if (commodity.labelFlag == 2) {
                str += '<div class="hot"></div>';
            } else if (commodity.labelFlag == 3) {
                str += '<div class="lt"></div>';
            } else if (commodity.labelFlag == 4) {
                str += '<div class="ll"></div>';
            }

            str += '  <div class="tal">' +
                '    <p class="pl16 line40 fs26 pr16 mt8">' + commodity.name + '</p>' +
                '    <div class=" pr price">' +
                '      <div class="lefticon "></div>' +
                '      <div class="righticon cleafix pr ">' +
                '        <span class=" ml50 mr10 fl fs19">原价：<del>' + commodity.price/100;
            //if (commodity.unit != null && commodity.unit != '') {
            //    str += '／' + commodity.unit;
            //}
            str += '</del></span>' +
                '        <span class="fs25 fl">' + commodity.peopleNumber + '人团：¥' + commodity.discountPrice/100;
            //if (commodity.discountUnit != null && commodity.discountUnit != '') {
            //    str += '／' + commodity.discountUnit;
            //}
            str += '</span>' +
                '        <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '"></a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '</div>';
        } else if (commodity.flag == 2) {//福袋
            str += '  <div class="focusImgs pr ">' +
                '    <div class="slider multiple-items">' +
                '      <div>' +
                '        <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '">';
            if (commodity.commodityPhotoList != null && commodity.commodityPhotoList.length > 0) {
                str += '          <img src=' + IMAGE_FORMAL_URL + '/' + commodity.commodityPhotoList[0].imageName + '>';
            }
            str += '        </a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '  <div class="water">' +
                '    <p class="fs20 mt28 ml17">' + commodity.discount + ' 折</p>' +
                '  </div>';
            if (commodity.labelFlag == 1) {
                str += '<div class="new"></div>';
            } else if (commodity.labelFlag == 2) {
                str += '<div class="hot"></div>';
            } else if (commodity.labelFlag == 3) {
                str += '<div class="lt"></div>';
            } else if (commodity.labelFlag == 4) {
                str += '<div class="ll"></div>';
            }
            str += '  <div class="tal">' +
                '    <p class="pl16 line40 fs26 pr16 mt8">' + commodity.name + '</p>' +
                '  <div class=" pr price">' +
                '    <div class="lefticon fu"></div>' +
                '      <div class="righticon cleafix pr qiang">' +
                '        <span class=" ml50 mr10 fl fs19">原价：<del>' + commodity.price/100;
            //if (commodity.unit != null && commodity.unit != '') {
            //    str += '／' + commodity.unit;
            //}
            str += '</del></span>' +
                '        <span class="fs25 fl">现价：¥' + commodity.discountPrice/100;
            //if (commodity.discountUnit != null && commodity.discountUnit != '') {
            //    str += '／' + commodity.discountUnit;
            //}
            str += '</span>' +
                '        <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '"></a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '</div>';
        } else if (commodity.flag == 3) {//特惠
            str += '  <div class="focusImgs pr ">' +
                '    <div class="slider multiple-items">' +
                '      <div>' +
                '        <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '">';
            if (commodity.commodityPhotoList != null && commodity.commodityPhotoList.length > 0) {
                str += '          <img src=' + IMAGE_FORMAL_URL + '/' + commodity.commodityPhotoList[0].imageName + '>';
            }
            str += '        </a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '  <div class="water">' +
                '    <p class="fs20 mt28 ml17">' + commodity.discount + ' 折</p>' +
                '  </div>';
            if (commodity.labelFlag == 1) {
                str += '<div class="new"></div>';
            } else if (commodity.labelFlag == 2) {
                str += '<div class="hot"></div>';
            } else if (commodity.labelFlag == 3) {
                str += '<div class="lt"></div>';
            } else if (commodity.labelFlag == 4) {
                str += '<div class="ll"></div>';
            }
            str += '  <div class="tal">' +
                '    <p class="pl16 pr16 line40 fs26 mt8">' + commodity.name + '</p>' +
                '    <div class=" pr price">' +
                '      <div class="lefticon sale"></div>' +
                '      <div class="righticon cleafix pr qiang">' +
                '        <span class=" ml50 mr10 fl fs19">原价：<del>' + commodity.price/100;
            //if (commodity.unit != null && commodity.unit != '') {
            //    str += '／' + commodity.unit;
            //}
            str += '</del></span>' +
                '        <span class="fs25 fl">现价：¥' + commodity.discountPrice/100;
            //if (commodity.discountUnit != null && commodity.discountUnit != '') {
            //    str += '／' + commodity.discountUnit;
            //}
            str += '</span>' +
                '        <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '"></a>' +
                '      </div>' +
                '    </div>' +
                '  </div>' +
                '</div>';
        } else if (commodity.flag == 4) {//咨询
            str += '<div class="focusImgs pr ">' +
                '  <div class="slider multiple-items">' +
                '    <div>' +
                '        <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '">';
            if (commodity.commodityPhotoList != null && commodity.commodityPhotoList.length > 0) {
                str += '          <img src=' + IMAGE_FORMAL_URL + '/' + commodity.commodityPhotoList[0].imageName + '>';
            }
            str += '        </a>' +
                '    </div>' +
                '  </div>' +
                '</div>' +
                '<div class="tal">' +
                '  <p class="pl16 line24 fs20 pr18 mt6">' + commodity.name + '</p>' +
                '  <div class=" pr price askpic">' +
                '    <div class="lefticon dzf"></div>' +
                '    <div class="righticon cleafix pr askdz">' +
                '      <span class="yuanj  fl ">原价:<del>' + commodity.price/100 + '</del></span>' +
                '      <span class="ml30 mr10 fl fs19">现价:' + commodity.discountPrice/100 + '</span>' +
                '      <a href="' + BASE_JS_URL + '/business/commodityDetailPage/' + commodity.id + '"></a>' +
                '    </div>' +
                '  </div>' +
                '  </div>' +
                '</div>';
        }
    });
    $('.itemlistbox').append(str);
}
