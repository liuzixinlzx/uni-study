<template>
	<view class="phone-fit">
		<view class="content">
	<!-- 		<image class="logo" src="/static/logo.png"></image>
			<view class="text-area">
				<text class="title">{{title}}</text>
			</view>
	-->
			<view class="title-area">
				<text class="title">{{trainingData.name}}</text>
			</view>
			<view class="text-area">
				<text class="notitle">{{trainingData.descript}}</text>
			</view>
			<view style="width: 100%;">
				<swiper class="screen-swiper swiper-height" :indicator-dots="indicatorDots" :autoplay="autoplay" :circular="true" :interval="interval" :duration="duration">
					<swiper-item v-for="(item,index) in swiperList" :key="index">
						<image :src="item.url" :mode="item.mode" v-if="item.type=='image'" class='swipper-img'></image>
						<video :src="item.url" autoplay loop muted :show-play-btn="false" :controls="false" objectFit="cover" v-if="item.type=='video'"></video>
					</swiper-item>
				</swiper>
			</view>
			<view class="bottom-area">
				<view class="text-area">
					<text class="notitle oneline">地址：{{trainingData.address}}</text>
					<image src="@/static/dw.gif" mode='aspectFit' style="width: 25rpx; height:50rpx; padding-left: 60rpx;" @tap="openMap"> </image>
				</view>
				<view class="text-area">
					<text class="notitle">电话：{{trainingData.phone}}</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: 'Hello',
				
				indicatorDots: true,
				autoplay: true,
				interval: 4000,
				duration: 500,
				
				trainingData: {
					name: '状元教育',
					descript: "  状元教育，xxxxxxxxxxx。\r\n  搞活动啦，优惠100元！！！\r\n  状元教育，xxxxxxxxxxx。\r\n  搞活动啦，优惠100元！！！",
					address: '科技大道和沂河路交汇，南100米',
					phone: '13655491580',
					lon: 118.273375,
					lat: 35.02172,
				},
				
				swiperList: [{
					id: 0,
					type: 'image',
					mode: 'scaleToFill',
					url: '/static/img/1.jpg'
				}, {
					id: 1,
					type: 'image',
					mode: 'scaleToFill',
					url: '/static/img/2.jpg'
				}, {
					id: 2,
					type: 'image',
					mode: 'scaleToFill',
					url: '/static/img/3.jpg'
				}, {
					id: 3,
					type: 'image',
					mode: 'scaleToFill',
					url: '/static/img/4.jpg'
				}, {
					id: 4,
					type: 'image',
					mode: 'scaleToFill',
					url: '/static/img/5.jpg'
				},{
					id: 5,
					type: 'image',
					mode: 'scaleToFill',
					url: 'https://ossweb-img.qq.com/images/lol/web201310/skin/big25011.jpg'
				}],
			}
		},
		onLoad() {
			this.getrainingData();
		},
		methods: {			
			getrainingData() {
				uni.request({
					url: this.serverurl + '/training/list',
					success: (res) => {
						console.log("/training/list result: " + JSON.stringify(res));
						this.trainingData = res.data[0];
					}
				})
			},
			
			openMap() {
				uni.navigateTo({
					url: '/pages/map/map?longitude=' + this.trainingData.lon + '&latitude=' + this.trainingData.lat
				})
			},
		}
	}
</script>

<style>
.phone-fit {
	padding-top:var(--status-bar-height);
}

.content {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
}

.logo {
	height: 200rpx;
	width: 200rpx;
	margin-top: 200rpx;
	margin-left: auto;
	margin-right: auto;
	margin-bottom: 50rpx;
}

.title-area {
	display: flex;
	justify-content: center;
}

.text-area {
	display: flex;
	justify-content: left;
}

.title {
	font-size: 60rpx;
	color: #8f8f94;
}

.notitle {
	font-size: 36rpx;
	color: #8f8f94;
	padding-left: 10rpx;
}

.bottom-area {
	position: absolute;
	bottom: 0rpx;
	width: 100%;
}

.oneline {
	float: left;
}

.swiper-height {
	height: 500rpx;
}
.swipper-img {
	width: 100%;
	height: 500rpx;
}
</style>
