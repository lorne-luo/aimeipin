/**
 * Created by luanpeng on 16/3/25.
 */
/**
 * 上传图片
 * @type {UpLoadimg}
 */
var uploadimg = new UpLoadimg({
    delUrl: BASE_JS_URL + "/ajax/deleteOthersImage",   //删除路径
    uploadUrl: BASE_JS_URL + "/ajax/uploadImage",      //上传路径
    isexisttextarea: false, //默认存在文本框
    ismultiple: true,//多选
    formid: $("#addForm"),
    storepicname: "imageName",
    backtag: "backFlag",
    dbpicbox: "dbpicbox",
    addAbtn: "addAbtn",
    fileName: "fileName",
    hasupfileimg: "hasupfileimg",
    firstpic: "firstPhoto"
});

function saveOthers() {
    uploadimg.getpngname();
    uploadimg.getbackimgtag();

    var imageName = $('#imageName').val();
    var backFlag = $('#backFlag').val();
    var flag = $('#flag').val();
    $.ajax({
        url: BASE_JS_URL + '/backend/addOthers',
        data: {
            'imageName': imageName,
            'backFlag': backFlag,
            'flag': flag
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {

            window.location.href = BASE_JS_URL + "/backend/others/" + flag;
        }
    })

}


