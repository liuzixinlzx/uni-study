<template>
	<view class="phone-fit">
		<view class="uni-list" style="padding-top: 30rpx;">
			<view class="uni-list-cell">
				<view class="uni-list-cell-left oneline">
					选择日期
				</view>
				<view class="uni-list-cell-db l-padding">
					<picker mode="date" :value="date" :start="startDate" :end="endDate" @change="bindDateChange">
						<view class="uni-input">{{date}}</view>
					</picker>
				</view>
			</view>
		</view>
		<view style="padding-top: 30rpx;">
			<t-table @change="change">
				<t-tr>
					<t-th>课程名称</t-th>
					<t-th>上课时间</t-th>
					<t-th>下课时间</t-th>
				</t-tr>
				<t-tr v-for="item in tableList" :key="item.id">
					<t-th>{{ item.name }}</t-th>
					<t-td>{{ item.begintime.substring(0, 5)}}</t-td>
					<t-td>{{ item.endtime.substring(0, 5) }}</t-td>
				</t-tr>
			</t-table>
		</view>
	</view>
</template>

<script>
	import tTable from '@/components/t-table/t-table.vue';
	import tTh from '@/components/t-table/t-th.vue';
	import tTr from '@/components/t-table/t-tr.vue';
	import tTd from '@/components/t-table/t-td.vue';
	export default {
		components: {
		            tTable,
		            tTh,
		            tTr,
		            tTd
		},
		data() {
			const currentDate = this.getDate({
				format: true
			})
			return {
				date: currentDate,
				tableList: [],
			}
		},
		computed: {
			startDate() {
				return this.getDate('start');
			},
			endDate() {
				return this.getDate('end');
			}
		},
		onLoad() {
			this.getList();
		},
		methods: {
			getList(){
				console.log("date:" + this.date);
				uni.request({
					url: this.serverurl + '/course/list', //仅为示例，并非真实接口地址。
					method: 'GET',
					data: {
						date: this.date,
					},
					success: (res) => {
						console.log("/course/list result: " + JSON.stringify(res));
						this.tableList = res.data;
					}
				});
			},
			
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
	
				if (type === 'start') {
					year = year - 60;
				} else if (type === 'end') {
					year = year + 2;
				}
				month = month > 9 ? month : '0' + month;;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			},
			
			bindDateChange: function(e) {
				this.date = e.target.value
				this.getList();
			},
					
			change(e) {
				console.log(e.detail);
			},
		}
	}
</script>

<style>
.phone-fit {
	/* padding-top:var(--status-bar-height); */
}

.oneline {
	float: left;
}

.l-padding {
	padding-left: 200rpx;
}

.date-input {
	border-bottom: 2rpx #dedede solid;
}
</style>
