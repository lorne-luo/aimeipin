/**
 * Created by luanpeng on 16/6/15.
 */


function edit(){
    var password = $('.password').val();
    var rePassword = $('.rePassword').val();
    $.ajax({
        url:BASE_JS_URL + '/backend/editPassword',
        type:'post',
        data:{
            password:password,
            rePassword:rePassword
        },
        dataType:'json',
        success:function(data){
           if(data.ret == 0){
               alert("修改成功");
               window.location.href = BASE_JS_URL + "/backend/loginPage";
           }else if(data.ret == -1){
               alert("密码有误");
           }else if(date.ret == -2){
               alert("帐号异常请重新登录");
           }
        }

    })
}