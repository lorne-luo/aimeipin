/**
 * 获取年月日
 * 例如 2014-02-02
 */
function getDate(obj){
    if(obj == null || obj == ""){
        return "";
    }
    var time = JSON.parse(obj);
    time = new Date(time);
    var year = time.getFullYear();
    var month = time.getMonth() + 1;
    var day = time.getDate() ;
    if(year < 10){
        year = "0" + year;
    }
    if(month < 10){
        month = "0" + month;
    }
    if(day < 10){
        day = "0" + day;
    }
    time = year + "-" + month + "-" + day;
    return time;
}

function getDateNew(obj){
    if(obj == null || obj == ""){
        return "";
    }
    time = new Date(obj);
    var year = time.getFullYear();
    var month = time.getMonth() + 1;
    var day = time.getDate() ;
    if(year < 10){
        year = "0" + year;
    }
    if(month < 10){
        month = "0" + month;
    }
    if(day < 10){
        day = "0" + day;
    }
    time = year + "-" + month + "-" + day;
    return time;
}

//转换时间格式 例如 2014-02-02  21:22:22
function getTime(obj){
    if(obj == null || obj == ""){
        return "";
    }
    var time = JSON.parse(obj);
    time = new Date(time);
    var year = time.getFullYear();
    var month = time.getMonth() + 1;
    var day = time.getDate() ;
    var hour = time.getHours();
    var min = time.getMinutes();
    var sec = time.getSeconds();
    if(year < 10){
        year = "0" + year;
    }
    if(month < 10){
        month = "0" + month;
    }
    if(day < 10){
        day = "0" + day;
    }
    if(hour < 10){
        hour = "0" + hour;
    }
    if(min < 10){
        min = "0" + min;
    }
    if(sec < 10){
        sec = "0" + sec;
    }
    time = year + "-" + month + "-" + day
        + " " + hour +":"+ min +":"+ sec;
    return time;
}


//转换时间格式 例如 2014-02-02  21:22
function getTime2(obj){
    if(obj == null || obj == ""){
        return "";
    }
    var time = JSON.parse(obj);
    time = new Date(time);
    var year = time.getFullYear();
    var month = time.getMonth() + 1;
    var day = time.getDate() ;
    var hour = time.getHours();
    var min = time.getMinutes();
    if(year < 10){
        year = "0" + year;
    }
    if(month < 10){
        month = "0" + month;
    }
    if(day < 10){
        day = "0" + day;
    }
    if(hour < 10){
        hour = "0" + hour;
    }
    if(min < 10){
        min = "0" + min;
    }
    time = year + "-" + month + "-" + day
        + " " + hour +":"+ min;
    return time;
}

function getTimes(obj){
    var time = JSON.parse(obj);
    time = new Date(time);
    return time.getTime();
}