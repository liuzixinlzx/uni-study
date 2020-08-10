<template>
	<view>
		<view class="uni-list">
			 <view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(item,index) in messageList" :key="index">
				  <view class="uni-list-cell-navigate uni-navigate-right split-line">
					   <view class="item-title">{{item.title}}</view>
					   <view>{{item.content}}</view>
					   <view class="item-time">{{item.createTime.split(' ')[0]}}</view>
				  </view>
			 </view>
		 </view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				messageList: [],
			}
		},
		
		onLoad() {
			this.getMessageList();
		},
		
		methods: {
			getMessageList() {
				var _this = this;
				uni.request({
					url: this.serverurl + "/push-message/list",
					success(res) {
						_this.messageList = res.data; 
					},
					
					fail() {
						
					}
				})
			}
		}
	}
</script>

<style>
.split-line {
	border-bottom: 2rpx #dedede solid;
}

.item-title {	
	display: flex;
	flex-direction: column;
	align-items: center;
	font-size: 50rpx;
	/* justify-content: center; */
}

.item-time {
	display: flex;
	flex-direction: column;
	align-items: flex-end;
	/* justify-content: flex-end; */
}
</style>
