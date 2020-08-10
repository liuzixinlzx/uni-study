<template>
	<view>
		<view>
			<view style="margin-top: 30rpx;">
				<input v-model="message.title" type="text" focus maxlength="16" class="my-input" placeholder="请输入消息标题"></input>
			</view>
			<view class="uni-textarea" style="margin-top: 30rpx;">
				<textarea v-model="message.content" class="my-textarea" placeholder="请输入消息内容"/>
			</view>
			<view style="margin-top: 30rpx;">
				<input v-model="message.time" type="number" focus maxlength="16" class="my-input" placeholder="请输入过期时间(秒)"></input>
			</view>
			<view style="padding-top: 30rpx;">
				<button type="primary" @tap="fnSubmit">提交</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				message: {
					title: '',
					content: '',
					time: undefined,
				},
				
				token: null,
			}
		},
		
		mounted() {
			var _this = this;
			uni.getStorage({
				key: 'accessToken',
				success: function(res) {
					_this.token = res.data;
					console.log("token="+ _this.token)
				},
			});
		},
		
		methods: {
			fnSubmit () {
				var _this = this;
				
				if (_this.message.title.length < 1){
					uni.showModal({
						title: '提示',
						content: '请输入标题！',
						showCancel: false,
					})
					return;
				}
				
				if (_this.message.content.length < 1){
					uni.showModal({
						title: '提示',
						content: '请输入内容！',
						showCancel: false,
					})
					return;
				}
				
				if (!_this.message.time || _this.message.time.length < 1){
					uni.showModal({
						title: '提示',
						content: '请输入超时时间！',
						showCancel: false,
					})
					return;
				}
				
				
				uni.request({
					url: _this.serverurl + '/push-message/push',
					data: _this.message,
					header: {
						'accessToken': _this.token
					},
					method: "POST",
					success(res) {
						uni.showModal({
							title: '提示',
							content: '消息推送成功！',
							showCancel: false,
							success() {
								uni.navigateBack({
									delta: 1,
								})
							}
						})
					},
					
					fail(err) {
						uni.showModal({
							title: '提示',
							content: err.message,
							showCancel: false,
						})
					}
				})
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
.my-textarea {
	width: 100%;
	font-size: 36rpx;
	border-bottom: 2rpx #dedede solid;
}
</style>
