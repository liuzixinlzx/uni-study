<template>
	<view>
		<view v-if="index == '1'">
			<push-message> </push-message>
		</view>
		<view v-else>
			{{contents}}
		</view>
	</view>
</template>

<script>
	import PushMessage from '@/pages/other/PushMessage/PushMessage'
	export default {
		components:{
			PushMessage
		},
		data() {
			return {
				index: '0',
				contents:'',
			}
		},
		onLoad(options) {
			console.log(options);
			this.index = options.index;
			switch (options.index){
				case '0':
					this.getVersion();
					break;
				case '1':
					break;
				default:
					this.contents = "好像出错啦";
					break;
			}
		},
		methods: {
			getVersion() {
				var _this = this;
				// #ifdef APP-PLUS
				plus.runtime.getProperty(plus.runtime.appid, function(widgetInfo) {
					_this.contents = widgetInfo.version;
					}
				);
				// #endif
			}
		}
	}
</script>

<style>
</style>
