/**
 * Created by luanpeng on 16/3/26.
 */
$(function () {
    $('.flag').change(function () {
        var flag = $(this).val();
        window.location.href = BASE_JS_URL + "/backend/buyNotice/" + flag;

    });

});

function submit(){
    var flag = $('.flag').val();
    var description = "";
    ue.ready(function () {
         description = ue.getContent();
    });

    var paymentNote = "";
    ue_payment.ready(function () {
        paymentNote = ue_payment.getContent();
    });

    $.ajax({
        type: 'post',
        dataType: 'json',
        url: BASE_JS_URL + '/backend/updateBuyNotice',
        data: {
            flag: flag,
            description:description,
            paymentNote:paymentNote
        },
        success: function (data) {
           alert("保存成功");
        }
    });
}


