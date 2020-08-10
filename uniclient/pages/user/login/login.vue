<template>
	<view>
		<view>
			<view style="margin-top: 30rpx;">
				<input v-model="account.username" type="text" focus maxlength="16" class="my-input" placeholder="请输入账号"></input>
			</view>
			<view style="margin-top: 30rpx;">
				<input v-model="account.password" type="password" displayable maxlength="10" class="my-input" placeholder="请输入密码"></input>
			</view>
			<view style="padding-top: 30rpx;">
				<button type="primary" @tap="fnLogin">登录</button>
			</view>
		</view>
		<view class="forget-password" @tap="findPassword">忘记密码?</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				account: {
					username: '',
					password: '',
				}
			}
		},
		
		onLoad() {
			var _this = this;
			uni.getStorage({
				key: 'username',
				success(res) {
					console.log(res);
					_this.account.username = res.data;
				}
			})
		},
		
		methods: {
			/**
			 * 登录按钮点击执行
			 */
			fnLogin() {
				let _this = this;
				uni.showLoading({
					mask: true,
					title: '请稍后',
				});
				let url = "/user/login";
				let params = _this.account;
				if (_this.account.username == '') {
					uni.showToast({
						title: '请输入用户名',
						icon: 'none'
					})
					return;
				}
				if (_this.account.password == '') {
					uni.showToast({
						title: '请输入密码',
						icon: 'none'
					})
					return;
				}
				
				uni.request({
					url: this.serverurl + url,
					data: params,
					success: (res) => {
						console.info(res);
						if (res.data.code == 200) {
							uni.showToast({
								title: '登录成功',
								icon: 'none'
							})
							uni.setStorage({
								key: "username",
								data: res.data.data.userInfo.username
							})
							
							uni.setStorage({
								key: "userInfo",
								data: res.data.data.userInfo
							})
							uni.setStorage({
								key: "accessToken",
								data: res.data.data.accessToken
							})
							setTimeout(() => {
								uni.reLaunch({
									url: '/pages/tabBar/my/my' 
								})
							}, 500)
						} else {
							uni.showToast({
								title: res.data.message,
								icon: 'none'
							})
						}
									
						uni.hideLoading();
					},
					
					fail: (err) => {
						console.log(err);
					}
				});
			},
			
			findPassword() {
				uni.showModal({
					title: '提示',
					content: '请联系管理员:\n电话：13655491580',
					showCancel: false,
				});
			},
		}
	}
</script>

<style>
.my-input {
	height: 50rpx;
	font-size: 36rpx;
	text-align: center;
	border-bottom: 2rpx #dedede solid;
}

.forget-password {
	margin-top: 20rpx;
	float: right;
	padding-right: 50rpx;
}
</style>
