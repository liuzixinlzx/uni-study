<template>
	<view>
		<view class="page-body">
			<view class="page-section page-section-gap m-border">
				<map style="width: 100%; height: 999px;" :class="heightClass" :latitude="latitude" :longitude="longitude" :markers="covers">
				</map>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				mHeight: '800px',
				heightClass: {
					height: this.mHeight + 'px'
				},
				longitude: 35.02172,
				latitude: 118.273375,
				covers: [{
						latitude: 35.02172,
						longitude: 118.273375,
						iconPath: '../../static/dw.png'
					},
				],
			}
		},
		
		onLoad(options){
			console.log(options.longitude + ',' + options.latitude);
			this.longitude = options.longitude;
			this.latitude = options.latitude;
			this.covers[0].longitude = options.longitude;
			this.covers[0].latitude = options.latitude;
			
			var _this = this
			uni.getSystemInfo({
			  success: function (res) {
				  console.log('height=' + res.windowHeight);
				  _this.mHeight = res.windowHeight + 'px';
			  }
			})
		},
		
		methods: {
			
		}
	}
</script>

<style>
</style>
