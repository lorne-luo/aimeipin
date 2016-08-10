/**
 * Created by luanpeng on 16/6/15.
 */


function register(){
    var account = $('.account').val();
    var password = $('.password').val();
    var rePassword = $('.rePassword').val();
    $.ajax({
        url:BASE_JS_URL + '/backend/register',
        type:'post',
        data:{
            account:account,
            password:password,
            rePassword:rePassword
        },
        dataType:'json',
        success:function(data){
           if(data.ret == 0){
               alert("注册成功");
           }else if(data.ret == -1){
               alert("帐号或者密码填写有误");
           }else if(date.ret == -2){
               alert("该帐号已存在");
           }
        }

    })
}