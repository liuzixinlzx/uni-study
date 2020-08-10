<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
			// #ifdef APP-PLUS  
			this.checkUpdate();
			this.noticeMsg(); 
			this.checkToken();
			// #endif
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
		
		methods: {
			//检查更新
			checkUpdate: function() {
				var _this = this;
				plus.runtime.getProperty(plus.runtime.appid, function(widgetInfo) {
					console.log(widgetInfo);
					console.log("url:" + _this.serverurl + '/version/list');
				   uni.request({  
					   url: _this.serverurl + '/version/list',
					   // data: {  
					   //     version: widgetInfo.version,  
					   //     name: widgetInfo.name  
					   // },  
					   success: (result) => {
						   console.log("/version/list result: " + JSON.stringify(result));
						   if(result.data.length <= 0) {
							   return;
						   }
								
						   var data = result.data[0];  
						   var needUpdate = (data.version.trim() !=  widgetInfo.version.trim());
						   console.log("new_version:" + data.version + ",oldversion:"+widgetInfo.version);
						   console.log("needUpdate:"+needUpdate+",updateUrl:" +data.updateUrl);
						   if (needUpdate && data.updateUrl) {						   
							   if (data.type == '1'){
								   /* 热更新 */
								   uni.downloadFile({  
									   url: data.updateUrl,  
									   success: (downloadResult) => {  
										   if (downloadResult.statusCode === 200) {									   
											   plus.runtime.install(downloadResult.tempFilePath, {  
												   force: false  
											   }, function() {  
												   console.log('install success...');  
												   plus.runtime.restart();  
											   }, function(e) {  
												   console.error('install fail...');  
											   });   
										   }  
									   }  
								   }); 
							   }else{
								   /* 整包升级 */
								   uni.showModal({ //提醒用户更新  
									title: "更新提示",  
									content: "有新的升级包，确认升级?",  
									success: (res) => {  
										if (res.confirm) {  
											plus.runtime.openURL(data.updateUrl);  
										}  
									}  
								   })
							   }
						   }  
					   },
					   
					   fail(e){
						   console.log(e);
					   }
				   });  
				}); 
			},
			
			//通知
			noticeMsg: function() {
				var _this = this;
				var info = plus.push.getClientInfo();
				console.log( JSON.stringify( info ) );
				 /* 5+  push 消息推送 ps:使用:H5+的方式监听，实现推送*/  
				plus.push.addEventListener("click", function(msg) {  
						console.log("click:"+JSON.stringify(msg));  
						uni.showToast({
						 	title: "click:"+JSON.stringify(msg),
						 	duration: 5000
						})
						//这里可以写跳转业务代码
				}, false);  
					// 监听在线消息事件    
				plus.push.addEventListener("receive", function(msg) {  
					// plus.ui.alert(2);  
					//这里可以写跳转业务代码
					console.log("recevice:"+JSON.stringify(msg))
					
					 uni.showToast({
						title: "recevice:"+JSON.stringify(msg),
						duration: 5000
					})
				}, false);  
			},
			
			checkToken: function() {
				var _this = this;
				uni.getStorage({
					key: 'accessToken',
					success: function(res) {
						var token = res.data;
						console.log("url="+ _this.serverurl + "/token/check");
						uni.request({
							url: _this.serverurl + "/token/check",
							header: {
								'accessToken': token
							},
							method: "GET",
							success: (res) => {
								console.log("/token/check result:" + JSON.stringify(res));
								if(res.statusCode == 401){
									//uni.clearStorage();
									uni.removeStorage({
									    key: 'accessToken',
									    success: function (res) {
									    }
									});
									uni.removeStorage({
									    key: 'userInfo',
									    success: function (res) {
									    }
									});
								}
							},
							
							fail: (err) => {
								console.log(err);
							}
						})
					},
				});
			}
		}
		
	}
</script>

<style>
	/*每个页面公共css */
</style>
