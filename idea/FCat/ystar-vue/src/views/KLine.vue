<template>
	<!--K线图-->
	<v-container fill-height fluid grid-list-xl>
		<v-layout justify-center wrap>
			<v-flex md12 sm12>
				<div style="width:100%; height:800px;" id="kline"></div>
			</v-flex>
		</v-layout>
	</v-container>
</template>

<script>
	import { kLinePic, aaaa } from "@/utils/echart";
	import { queryStrategy } from '@/api/demoAddress'
	import { klinkPicLogic, getBuySellList } from "@/utils/echartLogic"

	export default {
		data() {
			return {
				// 长期均线
		      	upMaNum: 0,
		      	// 短期均线
		      	downMaNum: 0,
		      	// k线数据
				KLineData: [],
				// K线买卖标识数据
				KLineBuySellList: [],
			}
		},
		mounted() {
			this.getKLine();
		},
		methods: {
			//获取K线数据
			getKLine() {
				let _this = this;
				queryStrategy().then(res => {
					_this.kLineData = klinkPicLogic(res.data.data.k);
      				_this.KLineBuySellList = getBuySellList([], res.data.data.mark);
      				_this.upMaNum = res.data.data.mas[0];
      				_this.downMaNum = res.data.data.mas[1];
      				
  					kLinePic("kline", _this.kLineData, _this.upMaNum, _this.downMaNum, _this.KLineBuySellList);
				}).catch(e => {
				});
			},
		}
	}
</script>

<style>
	
</style>