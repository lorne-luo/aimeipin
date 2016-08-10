/**
 * Created by luanpeng on 16/4/22.
 */
/**
 * 上传图片
 * @type {UpLoadimg}
 */
var uploadimg = new UpLoadimg({
    delUrl: BASE_JS_URL + "/ajax/deleteIndexImage",   //删除路径
    uploadUrl: BASE_JS_URL + "/ajax/uploadImage",      //上传路径
    isexisttextarea: true, //默认存在文本框
    ismultiple: true,//多选
    sortOrder:true,//图片权重
    formid: $("#addIndexImage"),
    storepicname: "imageName",
    backtag: "backFlag",
    dbpicbox: "dbpicbox",
    addAbtn: "addAbtn",
    fileName: "fileName",
    hasupfileimg: "hasupfileimg",
    firstpic: "firstPhoto",
    sortOrdername:"weightStr"
});


function submitImage() {
    uploadimg.getpngname();
    uploadimg.getbackimgtag();
    uploadimg.gettext();
    uploadimg.getsortOrder();

    var imageName = $('#imageName').val();
    var backFlag = $('#backFlag').val();
    var textarea = $('#textarea').val();
    var weightStr = $('#weightStr').val();
    $.ajax({
        type: 'post',
        dataType: 'json',
        url: BASE_JS_URL + '/ajax/saveIndexImage',
        data: {
            imageName: imageName,
            backFlag: backFlag,
            textarea:textarea,
            weightStr:weightStr
        },
        success: function (data) {
            if (data.ret == 0) {
                window.location.href = BASE_JS_URL + "/backend/indexImage";
            } else {

            }
        }
    });
}