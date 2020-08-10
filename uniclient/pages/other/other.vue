<template>
	<view>
		<view v-if="index == '0'">
			<push-message> </push-message>
		</view>
		<view v-if="index == '1'">
			<course-video> </course-video>
		</view>
		<view v-if="index == '2'">
			{{version}}
		</view>
	</view>
</template>

<script>
	import PushMessage from '@/pages/other/PushMessage/PushMessage'
	import courseVideo from '@/pages/other/courseVideo/courseVideo'
	export default {
		components:{
			PushMessage,
			courseVideo
		},
		data() {
			return {
				index: '0',
				version:'',
			}
		},
		onLoad(options) {
			console.log(options);
			this.index = options.index;
			switch (options.index){
				case '0':
					uni.setNavigationBarTitle({
						title: "消息推送",
					});
					break;
				case '1':
					uni.setNavigationBarTitle({
						title: "视频教程",
					});
					break;
				case '2':
					this.getVersion();
					uni.setNavigationBarTitle({
						title: "应用版本",
					});
					break;
				default:
					break;
			}
		},
		methods: {
			getVersion() {
				var _this = this;
				// #ifdef APP-PLUS
				plus.runtime.getProperty(plus.runtime.appid, function(widgetInfo) {
					_this.version = widgetInfo.version;
					}
				);
				// #endif
			}
		}
	}
</script>

<style>
</style>
