/**
 * Created by luanpeng on 16/3/26.
 */
var discount = $(".discount");//折扣系数
var discountPrice = $(".discountPrice");//折扣价格
var price = $(".price");//价格
$(function () {
    priceKeyup();


    $('.province').change(function () {
        var id = $(this).val();
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: BASE_JS_URL + '/ajax/getCityList',
            data: {
                id: id
            },
            success: function (data) {
                $('.city').html('');
                $.each(data.cityList, function (index, city) {
                    var str = '<option value="' + city.id + '">' + city.name + '</option>';
                    $('.city').append(str);
                });

            }
        });
    });

    $('.flag').change(function () {
        var flag = $(this).val();
        if (flag == 1) {//拼团
            $('#ptbox').html('');
            $('#cmbox').html('');
            var str = '<div class="form-group">' +
                ' <label><span class="star mr6">*</span>团购价：</label>' +
                '<input class="form-control discountPrice" name="discountPriceDouble"/>' +
                '<span class="inlineblock ml10">元</span>' +
                '<input class="form-control discountUnit" placeholder="单位 例如：ml" value="" name="discountUnit"/>' +
                '</div>' +
                '<div class="form-group">' +
                '<label><span class="star mr6">*</span>拼团人数：</label>' +
                ' <input class="form-control peopleNumber" placeholder="0"  name="peopleNumber"/>' +
                ' </div>' +
                ' <div class="form-group">' +
                ' <label><span class="star mr6">*</span>单人价格：</label>' +
                '<input class="form-control alonePrice" placeholder="0" name="alonePriceDouble"/>' +
                '</div>';
            $('#ptbox').append(str);
            $('#djbox').html('');
            str = '<div class="form-group">' +
                '<label><span class="star mr6">*</span>订金：</label>' +
                '<input class="form-control deposit" name="depositDouble"/>' +
                '</div>';
            $('#djbox').append(str);
        } else if (flag == 4) {//咨询
            $('#ptbox').html('');
            $('#cmbox').html('');
            var str = '<div class="form-group">' +
                ' <label><span class="star mr6">*</span>现价：</label>' +
                '<input class="form-control discountPrice" name="discountPriceDouble"/>' +
                '<span class="inlineblock ml10">元</span>' +
                '<input class="form-control discountUnit" placeholder="单位 例如：ml" value="" name="discountUnit"/>' +
                '</div>';
            $('#cmbox').append(str);
            $('#djbox').html('');


        } else {
            $('#ptbox').html('');
            $('#cmbox').html('');
            var str = '<div class="form-group">' +
                ' <label><span class="star mr6">*</span>现价：</label>' +
                '<input class="form-control discountPrice" name="discountPriceDouble"/>' +
                '<span class="inlineblock ml10">元</span>' +
                '<input class="form-control discountUnit" placeholder="单位 例如：ml" value="" name="discountUnit"/>' +
                '</div>';
            $('#cmbox').append(str);
            $('#djbox').html('');
            str = '<div class="form-group">' +
                '<label><span class="star mr6">*</span>订金：</label>' +
                '<input class="form-control deposit" name="depositDouble"/>' +
                '</div>';
            $('#djbox').append(str);

        }

        discount = $(".discount");//折扣系数
        discountPrice = $(".discountPrice");//折扣价格
        price = $(".price");//价格

        priceKeyup();
    });


    var startDate = $('.startDate').mobiscroll().calendar({
        theme: 'default',
        lang: 'zh',
        display: 'bubble',
        animate: 'flip',
        minDate: new Date(),
        dateFormat: "yy-mm-dd"
    });

    var endDate = $('.endDate').mobiscroll().calendar({
        theme: 'default',
        lang: 'zh',
        display: 'bubble',
        animate: 'flip',
        minDate: new Date(),
        dateFormat: "yy-mm-dd"
    });
    $("#cleardate").click(function () {
        startDate.val("");
        endDate.val("");
    })


});

function priceKeyup() {
    price.keyup(function () {
        var str = $.trim(price.val());
        if (!str) {//原价为0与空情况下
            alert("原价必须大于0");
            return;
        }
        if (!$.trim(discount.val()) && !$.trim(discountPrice.val())) {
            return;
        }
        if ($.trim(discount.val()) != "0") {
            calu();
            return;
        }
        if ($.trim(discountPrice.val()) != "0") {
            calu1();
            return;
        }

    });

    discount.keyup(function () {
        if ($.trim($(this).val()) > 10) {
            alert("折扣系数不能大于10");
            $(this).val("10")
        }
        calu();
    });

    discountPrice.keyup(function () {
        if (!$.trim(price.val())) {//原价为0与空情况下
            alert("原价必须大于0");
            return;
        }
        if (!$.trim($(this).val())) {//
            alert("折扣价格必须大于0");
            return;
        }
        calu1();
    });
}


function calu() {
    if (!$.trim(price.val())) {//原价为0与空情况下
        return;
    }
    var s = parseFloat(price.val() * 100) * (discount.val() / 10) / 100;
    discountPrice.val(s.toFixed(2));
}

function calu1() {
    if (!$.trim(price.val())) {//原价为0与空情况下
        return;
    }
    if (parseFloat($.trim(price.val())) < parseFloat(discountPrice.val())) {//原价为0与空情况下
        alert("折扣价格必须小于等于原价");
        discountPrice.val(parseFloat($.trim(price.val())));
        discount.val("10");
        return;
    }
    var s = parseFloat(discountPrice.val() * 100) / parseFloat(price.val() * 100);
    discount.val((s * 10).toFixed(1));
}


function submitCommodity(num) {

    ue.ready(function () {
        var html = ue.getContent();
        $('.description').val(html);
    });

    ue_remarks.ready(function () {
        var remarks_html = ue_remarks.getContent();
        $('.remarks').val(remarks_html);
    });

    $('.startDate').removeAttr('readonly');
    $('.endDate').removeAttr('readonly');


    $('.state').val(num);
    $('#addCommodity').submit();
}

var checkarr = [];

$(".checkdiv input").on('click', function () {
    var reg = $(this).attr("tagName");
    if ($(this).prop("checked")) {
        checkarr.push(reg);
    } else {
        checkSame(checkarr, reg);
    }
    var idStr = checkarr.join(",");
    $(".tags").val(idStr);
});

function checkSame(arr, reg) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == reg) {
            arr.splice(i, 1);
            return;
        }
    }
}


/**
 * 上传图片
 * @type {UpLoadimg}
 */
var uploadimg = new UpLoadimg({
    delUrl: BASE_JS_URL + "/ajax/deleteCommodityImage",   //删除路径
    uploadUrl: BASE_JS_URL + "/ajax/uploadImage",      //上传路径
    isexisttextarea: false, //默认存在文本框
    ismultiple: true,//多选
    formid: $("#addCommodity"),
    storepicname: "imageName",
    backtag: "backFlag",
    dbpicbox: "dbpicbox",
    addAbtn: "addAbtn",
    fileName: "fileName",
    hasupfileimg: "hasupfileimg",
    firstpic: "firstPhoto"
});

/**
 * 验证
 */

jQuery.validator.addMethod("isMobile", function (value, element) {
    var length = value.length;
    var mobile = /^0{0,1}1(3|5|8|7|4)[0-9]{9}$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写您的手机号码");
jQuery.validator.addMethod("isTelephone", function (value, element) {
    var length = value.length;
    var telephone = /^(0[0-9]{2,4}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
    return this.optional(element) || (length <= 19 && length > 10 && telephone.test(value));
}, "请正确填写您的电话号码");
$("#addCommodity").validate({
    rules: {
        "flag": true,
        "name": true,
        "keyword": true,
        "priceDouble": {
            required: true,
            number: true,
            min: 0.1
        },
        "unit": true,
        "discountPrice": {
            required: true,
            number: true,
            min: 0.1
        },
        "discount": {
            required: true,
            number: true,
            max: 10,
            min: 0.1
        },
        "peopleNumber": {
            required: true,
            digits: true,
            max: 100,
            min: 2
        },
        "alonePriceDouble": {
            required: true,
            number: true,
            min: 0.1
        },
        "depositDouble": {
            required: true,
            number: true,
            min: 0.1
        },
        //"startDate": "required",
        //"endDate": "required",
        "tags": true
    },
    messages: {
        "flag": "*请选择商品类型",
        "name": "*请填写名称",
        "keyword": "*请填写关键词",
        "priceDouble": {
            required: "*请填写价格",
            number: "*必须是数字",
            min: "*最少为0.1"
        },
        "unit": "*请填写单位",
        "discountPriceDouble": {
            required: "*请填写折后价",
            number: "*请填写数字",
            min: "*最少为0.1"
        },
        "discount": {
            required: "*请填写折扣",
            number: "*必须是数字",
            max: "*折扣不能大于10",
            min: "*折扣不能小于0.1"
        },
        "peopleNumber": {
            required: "*请填写人数",
            digits: "*请填写数字",
            max: "*最多不能超过100人",
            min: "*最少1人"
        },
        "alonePriceDouble": {
            required: "*请填写单人价格",
            number: "*必须是数字",
            min: "*最小为0.1"
        },
        "depositDouble": {
            required: "*请填写订金",
            number: "*必须是数字",
            min: "*最小为0.1"
        },
        //"startDate": "*请选择开始日期",
        //"endDate": "*请选择结束日期",
        "tags": "*请选择标签"
    },
    errorPlacement: function (error, element) {
        $('.startDate').attr('readonly', true);
        $('.endDate').attr('readonly', true);
        error.css("color", 'red').appendTo(element.parents('.form-group'));

    },
    submitHandler: function () {

        uploadimg.getpngname();
        uploadimg.getbackimgtag();
        //var addCommodity = $('#addCommodity');
        addCommodity.action = BASE_JS_URL + "/backend/addCommodity";
        addCommodity.submit();
    }
});
