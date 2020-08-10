<template>
	<view>
		<view class="uni-list">
			 <view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(item,index) in list" :key="index">
				  <view class="uni-list-cell-navigate uni-navigate-right" @click="clickItem(index)">
					   <text>{{item.label}}</text>
					   <text style="display: flex; padding-right: 50rpx;">{{item.value}}</text>
				  </view>
			 </view>
		 </view>
		 <view style="padding-top:30rpx;">
		 	<button type="primary" @click="logOut">退出登录</button>
		 </view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list:[
					{
						id: 0,
						label:'姓名',
						value: ''
					},
					{
						id: 1,
						label:'性别',
						value: ''
					},
					{
						id: 2,
						label:'年龄',
						value: ''
					},
					{
						id: 3,
						label:'电话',
						value: ''
					},
					{
						id: 4,
						label:'修改密码',
						value: ''
					},
					{
						id: 5,
						label:'修改头像',
						value: ''
					}
					],
			}
		},
		
		onLoad() {
			
		},
		
		onShow() {
			var _this = this;
			uni.getStorage({
			    key: 'userInfo',
			    success: function (res) {
					_this.list[0].value = res.data.nickname;
					_this.list[1].value = res.data.sex==0?'男':'女';
					_this.list[2].value = res.data.age;
					_this.list[3].value = res.data.phone;
			    },
			});
		},
		
		methods: {
			logOut(){
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
				setTimeout(() => {
					uni.reLaunch({
						url: '/pages/tabBar/my/my' 
					})
				}, 500);
			},
			
			clickItem(index){
				uni.navigateTo({
					url: '/pages/user/edit/edit?index=' + index
				})
			}
		}
	}
</script>

<style>
@import '@/common/uni.css';
</style>
