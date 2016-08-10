/**
 * Created by luanpeng on 16/6/15.
 */


function login(){
    var account = $('.account').val();
    var password = $('.password').val();
    $.ajax({
        url:BASE_JS_URL + '/backend/login',
        type:'post',
        data:{
            account:account,
            password:password
        },
        dataType:'json',
        success:function(data){
           if(data.ret == 0){
               window.location.href = BASE_JS_URL + "/backend";
           }else if(data.ret == -1){
               alert("帐号或者密码填写有误");
           }else if(data.ret == -2){
               alert("帐号与密码不匹配");
           }else if(data.ret == -3){
               alert("该账号未激活，请联系超管");
           }
        }

    })
}