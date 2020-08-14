<template>
	<view>
		<view v-if="index == 0" >
			<view>
				<input v-model="nickname" type="text" maxlength="16" class="my-input" placeholder="请输入新的用户名"></input>
			</view>
		</view>
		<view class="uni-list" v-if="index == 1">
			<view class="uni-list-cell">
				<view class="uni-list-cell-db">
					<picker @change="bindPickerChangeSex" :value="sexIndex" :range="sexArray">
						<view class="uni-input">{{sexArray[sexIndex]}}</view>
					</picker>
				</view>
			</view>
		</view>
		<view v-if="index == 2">
			<view class="one-line" >
				<button type="primary"  @click="subAge">-</button>
				<input class="my-input" v-model="age" type="text" maxlength="16" disabled="disabled" placeholder="请修改年龄"></input>
				<button type="primary"  @click="addAge">+</button>
			</view>
		</view>
		<view v-if="index == 3">
			<input v-model="phone" type="text" maxlength="16" class="my-input" placeholder="请输入电话号码"></input>
		</view>
		<view v-if="index == 4">
			<view class="one-line">
				原密码： <input v-model="oldPassword" type="password" maxlength="10" displayable class="my-input" placeholder="请输入原密码"></input>
			</view>
			<view class="one-line">
				新密码： <input v-model="newPassword" type="password" maxlength="10" displayable class="my-input" placeholder="请输入新密码"></input>
			</view>
			<view class="one-line">
				新密码：<input v-model="newPassword2" type="password" maxlength="10" displayable class="my-input" placeholder="请再次输入新密码"></input>
			</view>
		</view>
		<view v-if="index == 5" class="img-view">
			<image :src="headPath" mode="scaleToFill" class="img-style" @tap="pickPicture"> </image>
		</view>
		<view style="padding-top: 30rpx;"> 
			<button type="primary" @click='onEdit'>确认修改</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: '',
				
				index: 0,
				
				nickname: '',
				
				sexArray: ['男', '女'],
				sexIndex: 0,
				
				age: 0,
				phone: '',
				
				oldPassword: '',
				newPassword: '',
				newPassword2: '',
				
				headPath: '/static/head.png',
				
				token: null,
			}
		},
		
		onLoad(options) {
			var _this = this;
			uni.getStorage({
			    key: 'userInfo',
			    success: function (res) {
					_this.nickname = res.data.nickname;
					_this.sexIndex = res.data.sex;
					_this.age = res.data.age;
					_this.phone = res.data.phone;
					if (res.data.headurl != null && res.data.headurl.length > 0){
						_this.headPath = res.data.headurl;
						plus.io.resolveLocalFileSystemURL(_this.headPath,
						function(entry){
							
						}, function(e){ //本地图片不存在,使用后台图片
							var fileName = _this.headPath.substring(_this.headPath.lastIndexOf("/") + 1);
							_this.headPath = _this.serverurl + "/image/" + res.data.id + "/" + fileName;
							// console.log("XXXX:" + _this.headPath);
						})
					}
			    },
			});
			
			uni.getStorage({
				key: 'accessToken',
				success: function(res) {
					_this.token = res.data;
					console.log("token="+ _this.token)
				},
			});
			
			this.index = options.index;
			switch(this.index){
				case '0':
					this.title = '修改用户姓名'
					break;
				case '1':
					this.title = '修改用户性别'
					break;
				case '2':
					this.title = '修改用户年龄'
					break;
				case '3':
					this.title = '修改用户电话'
					break;
				case '4':
					this.title = '修改用户密码'
					break;
				case '5':
					this.title = '修改用户头像'
					break;			
			}
			
			uni.setNavigationBarTitle({
				title:this.title
			})
		},
		
		methods: {	
			subAge(){
				if(this.age > 0){
					this.age--;
				}
			},
			
			addAge() {
				if(this.age < 99) {
					this.age++;
				}
			},
			
			pickPicture(){
				var _this = this;
				uni.chooseImage({
				    count: 1, //默认9
				    sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
				    sourceType: ['album', 'camera'], //从相册选择, 启动摄像头
				    success: function (res) {
				        console.log(res.tempFilePaths[0]);
						_this.headPath = res.tempFilePaths[0];
						uni.uploadFile({
							url: _this.serverurl + '/user/uploadHead',
							filePath: res.tempFilePaths[0],
							name: 'file',
							header: {
								'accessToken': _this.token
							},
							success(res) {
								console.log(res);
							}
						});
				    },
					fail: function (err) {
						console.log(err);
					}
				});
			},
			
			bindPickerChangeSex(e){
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.sexIndex = e.target.value
			},
			
			onEdit() {
				var _this = this;
				
				var data = {};
				switch(this.index) {
					case '0':
						data = {'nickname': this.nickname};
						break;
					case '1':
						data = {'sex': this.sexIndex};
						break;
					case '2':
						data = {'age': this.age};
						break;
					case '3':
						data = {'phone': this.phone};
						break;			
					case '4':
						//密码需要特殊处理
						_this.editPassword();
						return;
					case '5':
						data = {'headurl': this.headPath};
						break;
				}

				uni.request({
					url: this.serverurl + '/user/edit',
					data: data,
					header: {
						'accessToken': _this.token
					},
					method: "POST",
					success: (res) => {
						console.log("/user/edit result: " + JSON.stringify(res));
						uni.setStorage({
							key: "userInfo",
							data: res.data.data.userInfo
						});
						
						setTimeout(()=>{							
							uni.navigateBack({
								delta: 1
							})
						}, 500);
					},
					
					fail: (err) => {
						console.log(err);
					}
				});
			},
			
			editPassword() {
				var _this = this;
				if (this.oldPassword.length < 1 || this.newPassword.length < 1 || this.newPassword2.length<1){
					uni.showModal({
						title: '提示',
						content: '请输入正确的密码',
						showCancel: false,
					});
					return;
				}
				
				if (this.newPassword != this.newPassword2){
					uni.showModal({
						title: '提示',
						content: '两次输入的新密码不一致',
						showCancel: false,
					});
					return;
				}
				uni.request({
					url: this.serverurl + '/user/editPassword',
					data: {
						oldPassword: _this.oldPassword,
						newPassword: _this.newPassword
					},
					header: {
						'accessToken': _this.token
					},
					method: "POST",
					success: (res) => {
						console.log('/user/editPassword' + JSON.stringify(res));
						uni.showModal({
							title: '提示',
							content: '密码修改成功！',
							showCancel: false,
							success(res) {
								uni.navigateBack({
									delta: 1,
								})
							}
						})
					},
					
					fail: (err) => {
						console.log(err);
					}
				});
			}
		}
	}
</script>

<style>
.my-input {
	height: 50rpx;
	font-size: 36rpx;
	text-align:center;
	border-bottom: 2rpx #dedede solid;
}

.one-line {
	/* 定义flex容器 */
			display: flex;
	         /*设置容器内部容器的排列方向*/	
			flex-direction: row;
			flex-wrap:nowrap;
			margin-top: 30rpx;
}

.img-view {
	display:flex;
	justify-content: center;
	width: 100%;
	height: 200rpx;
}
.img-style {
	width: 150rpx;
	height: 150rpx;
	align-self:center;
}
</style>
