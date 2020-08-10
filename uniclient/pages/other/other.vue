<template>
	<view>
		{{contents}}
	</view>
</template>

<script>
	export default {
		data() {
			return {
				contents:'',
			}
		},
		onLoad(options) {
			console.log(options);
			switch (options.index){
				case '0':
					this.contents = "我的姓名";
					break;
				case '1':
					this.contents = "我的电话";
					break;
				case '2':
					this.contents = "我的备注";
					break;
				case '3':
					this.getVersion();
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
