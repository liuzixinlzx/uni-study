<template>
	<view class="phone-fit"> 
		<view class="my-navigator">
			<image class="img-style" mode="scaleToFill" :src="imgsrc" @tap="clickHead" @error="imageError">
			</image>
			<view class="name-title">
				<text >欢迎您\n</text>
				<text >{{nickName}}</text>
			</view>
		</view>	
		<view class="uni-list">
			 <view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(item,index) in list" :key="index">
				  <view class="uni-list-cell-navigate uni-navigate-right" @click="clickItem(index)" v-if='index !=0 || (index == 0 && isAdmin)'>
					{{item}}
				  </view>
			 </view>
		 </view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				imgsrc: '/static/logo.png',
				list:['消息推送', '视频教程','版本'],
				isAdmin: false,
				nickName: '请点击头像登陆',
			}
		},
		
		onLoad() {
			
		},
		
		onShow() {
			var _this = this;
			uni.getStorage({
			    key: 'userInfo',
			    success: function (res) {
			        console.log("storage userInfo:" + JSON.stringify(res));
					_this.nickName = res.data.nickname; 
					if(res.data.headurl != null && res.data.headurl.length>0){
						_this.imgsrc = res.data.headurl;
					}
					
					if(res.data.username == 'admin'){
						_this.isAdmin = true;
					} 
			    },
			});
		},
		
		methods: {
			clickItem(index) {
				console.log("index=" + index);
				uni.navigateTo({
					url: '/pages/other/other?index=' + index,
				})
			},
			
			clickHead(){
				uni.getStorage({
				    key: 'userInfo',
				    success: function (res) {
						uni.navigateTo({
							url: "/pages/user/info/info"
						})
				    },
					fail: function (e) {
						console.log(e);
						uni.navigateTo({
							url: "/pages/user/login/login"
						})
					}
				});
			},
			
			imageError: function(e) {
				console.error('image发生error事件，携带值为' + e.detail.errMsg);
			}
		}
	}
</script>

<style>
@import '@/common/uni.css';
.phone-fit {
	padding-top:var(--status-bar-height);
}
.my-navigator {
	height: 150rpx;
	width: 100%;
	background-color: #007AFF;
	display:flex;
}

.img-style {
	width: 120rpx;
	height: 120rpx;
	align-self:center;
	padding-left: 20rpx;
}

.name-title {
	padding-left: 20rpx;
	align-self:center;
}
</style>
