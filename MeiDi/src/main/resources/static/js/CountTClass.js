/**
	//
	
*/
(function(){
	Countdown = function(json){
	  		this.timeBox ="";
	  		this.timeHtml = "";
	  		this.goingHtml = "剩余时间："
	  		this.endHtml = "团购已结束";
	  		this.endTime = new Date("2016/3/25,12:20:12");
	  		this.timer = "";
	  		this.leftsecond =0;
	  		this.init(json);
	  	}
	  	Countdown.prototype={
	  		init        :   function(json){//初始化参数
								for(var key in json){
									this[key] = json[key];
								}
								return this._fresh().startCount();
					       	},
			startCount  :   function(){
								var me = this;
								this.timer=setInterval(function(){
									me._fresh();
								},1000); 
								return this;
							},
			setHtml    :   	function(h,m,s){
								this.timeBox.html(this.goingHtml+'<span class="pfd5380">'+h+'</span><i class="pfd5380">:</i><span class="pfd5380">'+m+'</span><i class="pfd5380">:</i><span class="pfd5380">'+s+'</span>'); 
								return this;
							}, 
			getD        :   function(){
								var __d=parseInt(this.leftsecond/3600/24);
								return __d;
							},
			getH        :   function(){
								var __h=parseInt((this.leftsecond/3600)) ;
								if(__h<10){
								 	__h= '0'+__h;
								 }
								return __h;
							},
			getM        :   function(){
								var __m=parseInt((this.leftsecond/60)%60);
								if(__m<10){
								 	__m= '0'+__m;
								 }
								return __m;
							},
			getS        :   function(){
								var __s=parseInt(this.leftsecond%60);
								if(__s<10){
								 	__s= '0'+__s;
								 }
								return __s;
							},
			_fresh		:   function(){
								 this.leftsecond=parseInt((this.endTime-(new Date().getTime()))/1000);
								 this.setHtml(this.getH(),this.getM(),this.getS());
								 if(this.leftsecond<=0){   
									this.timeBox.html(this.endHtml);   
								 	clearInterval(this.timer);   
								 } 
								 return this;  
							}  
	  	} 
})()