(function () {
	UpLoadimg = function (json) {
		this.backtagimgarr = [];
		this.imgarr = [];
		this.textarr = [];
		this.delUrl = "";//删除路径
		this.uploadUrl = "";//上传路径
		this.isexisttextarea = false;//默认不存在文本框
		this.isexistfirstPic = false;//默认不存在首图
		this.ismultiple = false;//默认单选
		this.sortOrder = false;//默认不排列顺序
		this.sortOrdername = "sortorder";
		this.formid = $("form").eq(0);
		this.storepicname = "storePicName";
		this.textarea = "textarea";
		this.backtag = "backTag";
		this.dbpicbox = 'dbpicbox';
		this.addAbtn = 'addAbtn';
		this.fileName = 'fileName';
		this.hasupfileimg = 'hasupfileimg';
		this.firstpic = 'firstpic';
		this.init(json);
	};
	UpLoadimg.prototype = {
		init: function (json) {
			for (var key in json) {
				this[key] = json[key];
			}
			return this.createMsgInput().initEvents();
		},
		createImg: function (strs) {
			var box = $("<div>").addClass("piclist fl pr text-center");
			var str = "";
			str += '<img src="http://pica.nipic.com/2007-11-09/2007119124413448_2.jpg" class="hasimg">';
			if (this.isexistfirstPic) {
				str += '<p>设为首图：<input type="radio" name="' + this.firstpic + '"></p>';
			}
			if(this.sortOrder){
				str+='<p>设为排列顺序：<select>';
				for(var i=1;i<11;i++){
					str+='<option value='+i+'>'+i+'</option>';
				}
				str+='</select></p>';
			}
			str += '<div class="progress">'
				+ '<div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:80%;">'
				+ '发送中</div>'
				+ '</div>'
				+ (this.isexisttextarea ? '<div class="martb10"><textarea class="form-control" name="">输入描述语句</textarea></div>' : "")
				+ '<div class="text-center">' + '</div>';


			box.html(str);
			return box;
		},
		createMsgInput: function () {
			this.formid.append($('<input id="' + this.storepicname + '" type="hidden" value=""  name="' + this.storepicname + '"/>'
				+ (this.isexisttextarea ? '<input id="' + this.textarea + '" type="hidden" value=""  name="' + this.textarea + '"/>' : "")
				+ '<input id="' + this.backtag + '" type="hidden" value=""  name="' + this.backtag + '"/>'
				+(this.sortOrder?'<input id="'+this.sortOrdername+'" type="hidden" value=""  name="'+this.sortOrdername+'"/>':"")));
			return this;
		},
		getsortOrder   :  	function(){
			if(!this.sortOrder) return this;
			this.arr = [];
			var tip = $("#"+this.dbpicbox).find("select");
			for(var i = 0;i<tip.length;i++ ){
				this.arr.push(tip.eq(i).val());
			}
			$("#"+this.sortOrdername).val(this.arr.join(","));
			return this;
		},
		getbackimgtag: function () {
			this.backtagimgarr = [];
			var tip = $("#" + this.dbpicbox).find("a");
			for (var i = 0; i < tip.length; i++) {
				this.backtagimgarr.push(tip.eq(i).attr("backtip"));
			}
			$("#" + this.backtag).val(this.backtagimgarr.join(","));
			return this;
		},
		gettext: function () {
			this.textarr = [];
			var text = $("#" + this.dbpicbox).find("textarea");
			for (var i = 0; i < text.length; i++) {
				if (text.eq(i).val() == '输入描述语句') {
					text.eq(i).val("");
				}
				this.textarr.push(text.eq(i).val() ? text.eq(i).val() : " ");
			}

			$("#" + this.textarea).val(this.textarr.join("~&"));
			return this;
		},
		getpngname: function () {
			this.imgarr = [];

			var img = $("#" + this.dbpicbox).find("img");
			for (var i = 0; i < img.length; i++) {
				var pngname = img.eq(i).attr("src").split("/");
				pngname = pngname[pngname.length - 1];
				this.imgarr.push(pngname);
			}
			//alert(this.storepicname);


			console.log(this.imgarr);
			$("#" + this.storepicname).val(this.imgarr.join(","));
			//alert(this.imgarr );


			return this;
		},
		initEvents: function () {
			var me = this;

			$("#" + this.dbpicbox).delegate(
				"input",
				"click",
				function () {    /*删除操作*/
					var backname = $(this).parent().parent().find("img").eq(0)
						.attr("src");

					var pngname = backname.split("/");
					pngname = pngname[pngname.length - 1];
					if ($(this).get(0).checked) {
						$(this).val(pngname);
					}

				});

			$("#" + this.dbpicbox).delegate(
				"a",
				"click",
				function () {    /*删除操作*/
					var that = this;
					var backname = $(this).parent().parent().find("img").eq(0)
						.attr("src");

					var pngname = backname.split("/");
					pngname = pngname[pngname.length - 1];

					$("#" + me.hasupfileimg).val(
						!$("#" + me.dbpicbox).children().length ? "" : $("#" + me.dbpicbox).children().length);

					$.ajax({
						url: me.delUrl,
						type: 'post',
						data: {
							storePicName: pngname,//传递值

							type: 'remove',//删除操作

							backTag: $(that).attr("backtip")
						},
						success: function (data) {
							$(that).parent().parent().remove();
							$("#" + me.hasupfileimg).val(
								!$("#" + me.dbpicbox).children().length ? "" : $(
									"#" + me.dbpicbox).children().length);

						}
					});
				});
			$("#" + this.addAbtn).click(function () {
				if (me.ismultiple)$("#" + me.fileName).attr("multiple", "true");
				$("#" + me.fileName).click();
				$("#" + me.fileName).on(
					"change",
					function () {
						filenum = this.files.length;
						//console.log(this.files);


						$.ajaxFileUpload({
							type: 'post',
							url: me.uploadUrl, //上传文件的服务端

							secureuri: false, //是否启用安全提交

							dataType: 'json', //数据类型

							fileElementId: [me.fileName], //表示文件域ID

							data: {
								//type : 'upload'


							},
							//提交成功后处理函数      html为返回值，status为执行的状态


							success: function (data) {
								if (data.ret == -1) {
									alert("格式错误");
									return;
								} else if (data.ret == -2) {
									alert("系统繁忙请稍后再试");
									return;
								} else {
									/*返回图片的地址*/
									for (var i = 0; i < filenum; i++) {
										$("#" + me.dbpicbox).append(me.createImg());
									}
									$("#" + me.hasupfileimg).val(!$("#" + me.dbpicbox).children().length ? "" : $("#" + me.dbpicbox).children().length);
									me.successAfter(data.imgList);
									$("#" + me.fileName).removeAttr("multiple");
								}

							},

							//提交失败处理函数


							error: function (html, status, e) {
								alert("提交失败，请重新提交");
							}
						});
					});
			});
			return this;
		},
		successAfter: function (result) {
			var img = $("#" + this.dbpicbox).find(".hasimg");
			/*找到未附SRC图片的IMG*/
			//var imgArr = result;


			for (var i = 0; i < result.length; i++) {
				result[i].replace(/'\'/, '/');
				img
					.eq(i)
					.attr("src", IMAGE_TEMPORARY_URL + "/" + result[i])
					.removeClass("hasimg")
					.parent()
					.find(".text-center").html(
					"<a class='btn btn-warning' backtip='00'>删除</a>");
				img.eq(i).parent().find(".progress").remove();
				img.eq(i).parent().find("textarea").attr("name",
					result[i].split("/")[result[i].split("/").length - 1]);
			}
			return this;
		},
		a: function () {

		}
	};
})();
