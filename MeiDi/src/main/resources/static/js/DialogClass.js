/**
	//
	
*/
(function(){
	Dialog = function(json){
		this.mark     =  "";//遮罩对象
		this.markId   =  "mark"+new Date().getTime();//不传入默认是Dialog加时间戳
		this.hasMark  =  true;//默认存在遮罩层
		this.markColor=  "rgba(0,0,0,.5)";//设置遮罩层颜色
		this.isfade   =  true;//显示掩藏方式

		this.dialog       =  "";
		this.dialogClass  =  "dialog";//默认CLASS
		this.dialogtext   = "";//弹出框里面内容
		this.closebtnClass= "close";//关闭按钮的CLASS
		this.surebtnClass = "sure";//确定按钮的CLASS
		this.hasDialog    =  true;//默认存在弹框
		this.topDialog    = "";
		this.ifclickcloseMark = false;//默认可以关闭
		this.state        = false;//当前是否显示，默认不显示下为false
		this.events        = {close:[],sure:[]};
		this.init(json);	
	};


	Dialog.prototype={
		init        :      	function(json){//初始化参数
								for(var key in json){
									this[key] = json[key];
								}
								return this.initMark().initDialog();
					       	},
	    initMark    :      	function(){//初始化遮罩
	    						
								return this.createMark().setMarkStyle().markEvent();
					       	},
		initDialog  :      	function(){//初始化弹框
								
								return this.createDialog().setDiaPos().dialogEvent();
					       	},
		createDialog:      	function(){//创建弹框
								this.dialog = $("<div>").attr("class",this.dialogClass);
								if(!this.hasDialog) return this;
								this.dialog.appendTo($("body")).html(this.dialogtext);
								return this;
					       	},
		createMark  :      	function(){//创建遮罩
								this.mark = $("<div>").attr("id",this.markId);
								if(!this.hasMark) return this;
								$("body").append(this.mark);
								return this;
					       	},
		markEvent   :       function(){
								return this.resizeMark().closeMark();
							},
		dialogEvent :       function(){
								return this.resizeDia().closeDialog().sureDialog();
							},
		showMark    :      	function(){
								this.mark.show();
								return this;
						   	},
		fadeShowMark:      	function(){
								this.mark.fadeIn();
								return this;
							},
		hideMark    :      	function(){
								this.mark.hide();
								return this;
							},
		fadeHideMark:      	function(){
								this.mark.fadeOut();
								return this;
							},
		showDialog  :      	function(){
								this.dialog.show();
								return this;
						   	},
		fadeShowDia :      	function(){
								this.dialog.fadeIn();
								return this;
							},
		hideDialog  :      	function(){
								this.dialog.hide();
								return this;
							},
		fadeHideDia:      	function(){
								this.dialog.fadeOut();
								return this;
							},
		getMarkName :      	function(){
								return this.markId;
							},
		getMark     :      	function(){
								return $("#"+this.markId);
							},
		getDialogL  :       function(){
								return ($(window).width()-this.dialog.width())/2;
							},
		getDialogT  :       function(){
								return ($(window).height()-this.dialog.height())/2;
							},
		setDiaText  :       function(str){
								this.dialog.html(str);
								return this;
							},
		setMarkH    :       function(){
								this.mark.height($(window).height());
								return this;
							},
		setMarkW    :       function(){
								this.mark.width($(window).width());
								return this;
							},
		setMarkStyle:       function(){//设置宽高颜色
								var that = this;
								this.mark.css({
									"position":"fixed",
									"width":"0",
									"height":"0",
									"zIndex":"9999",
									"top":"0",
									"left":"0",
									"display":"none",
									"background":that.markColor
								});
								return this.setMarkW().setMarkH();
							},
		setDiaPos   :       function(){//设置弹框的位置
								var that = this;
								this.dialog.css({
									"display":"none",
									"top":that.topDialog?that.topDialog:that.getDialogT(),
									"left":that.getDialogL()
								})

								return this;
							},

		resizeMark  :       function(){
								var that = this;
								$(window).on("resize",function(){
									that.setMarkH().setMarkW();
								})
								return this;
							},	
		resizeDia  :       function(){
								var that = this;
								$(window).on("resize",function(){
									if(!this.state) return ;
									that.setDiaPos();
									that.showDialog();
								})
								return this;
							},	
		closeMark  :        function(){
								var that = this;
								this.mark.on("click",function(){
									if(that.ifclickcloseMark)return ;
									that.close();
								})
								
								return this;
							},
		closeDialog :        function(){
								var that = this;
								this.dialog.on("click",'.'+this.closebtnClass,function(){
									that.close();
								})
								
								return this;
							},	
		sureDialog  :        function(){
								var that = this;
								this.dialog.on("click",'.'+this.surebtnClass,function(){
									that.fire("sure");
									
								})
								
								return this;
							},
		addThis     :        function(obj){
								this.obj = obj;
								return this;
							},
		close       :       function(){
								if(this.isfade){
									this.fadeHideMark();
									this.fadeHideDia();
								}else{
									this.hideMark();
									this.hideDialog();
								}
								this.state=false;
								this.fire("close");

								return this;
							},	
		show        :        function(){
								if(this.isfade){
									this.fadeShowMark();
									this.fadeShowDia();
								}else{
									this.showMark();
									this.showDialog();
								}
								this.state=true;
								return this;
							},	
		on			:   	function(name,fn,scope){
								this.events[name]&&this.events[name].push({fn:fn,scope:scope});
								return this;
							},
		fire		:   	function(name){
								var args = Array.prototype.slice.call(arguments);
								args.shift();
								for(var i=0,fnArr=this.events[name]||[],fnObj;fnObj=fnArr[i++];){
									fnObj.fn.apply(fnObj.scope||this||window,args);
								}
								return this;
							},		
		a           :      	function(){

					       	}
	};
})()