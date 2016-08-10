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
    })
    $.ajax({
        type: 'post',
        dataType: 'json',
        url: BASE_JS_URL + '/backend/updateBuyNotice',
        data: {
            flag: flag,
            description:description
        },
        success: function (data) {
           alert("保存成功");
        }
    });
}


