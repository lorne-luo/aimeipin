function autosave(form_name) {
    if (!window.localStorage) {
        console.log('您的浏览器不支持 localStorage 技术!');
    } else {
        // var spanObj = $("span");
        // alert("spanObj" + spanObj);
        var saveTimer = setInterval(
            function () {
                var str = "";
                if (document.all) {/* IE */
                    str = document.frames[1].document.body.innerHTML;
                } else {/* Chrome,ff */
                    // str =
                    // document.getElementById("container").contentDocument.body.innerHTML;
                    var ue = UE.getEditor(form_name);
                    str = ue.getContent();
                }
                if (str.length > 5) {
                    // alert(str);
                    localStorage.setItem(form_name, str);
                    var d = new Date();
                    var YMDHMS = d.getFullYear() + "-"
                        + (d.getMonth() + 1) + "-"
                        + d.getDate() + " "
                        + d.getHours() + ":"
                        + d.getMinutes() + ":"
                        + d.getSeconds();
                    // alert(YMDHMS);
                    // spanObj.in   nerHTML = '（数据保存于: ' +
                    // YMDHMS
                    // + '）';
                    // var text = $("span").text = '（数据保存于: '
                    // + YMDHMS + '）';
                    // $("#span").text('（数据保存于: ' + YMDHMS + '）');

                    // alert(text);
                    // setTimeout(function() {
                    // spanObj.innerText = '';
                    // }, 5000);
                }
            }, 5000); // 每隔N秒保存一次
    }
}

function clearDraft(form_name) {
    localStorage.setItem(form_name, '');
}

function showlocs(form_name) {
    var html = localStorage.getItem(form_name);
    editor.setContent(html);
    alert(localStorage.getItem(form_name));
}