import { getWeekOfYear } from './common'


//净收益
export function netIncomeLogic(item, dataVal) {
	item = JSON.parse(item)
	item.reverse();//数组倒排
	//echart数据
	let netIncomeData = {
		date: [],
		incomeRate: []
	};
	let index = 0;
	//根据所选时间处理echart数据
	switch (dataVal) {
		//分
		case 'min':
			item.forEach(data => {
				netIncomeData.date[index] = data.date.slice(0, 16);
				netIncomeData.incomeRate[index] = Number((data.incomeRate * 100).toFixed(4))
				index = index + 1;
			});
			break;
		//日
		case 'day':
			item.forEach(data => {
				if (index == 0) {
					netIncomeData.date[index] = data.date.slice(0, 10);
					netIncomeData.incomeRate[index] = Number((data.incomeRate * 100).toFixed(4));
					index = index + 1;
				} else {
					if (netIncomeData.date[index - 1] == data.date.slice(0, 10)) {
						netIncomeData.incomeRate[index - 1] = (Number((data.incomeRate * 100).toFixed(4)) + Number(netIncomeData.incomeRate[index - 1])).toFixed(4);
					} else {
						netIncomeData.date[index] = data.date.slice(0, 10);
						netIncomeData.incomeRate[index] = Number((data.incomeRate * 100).toFixed(4))
						index = index + 1;
					}
				};
			});
			break;
		//周
		case 'week':
			let WeekOfYear = ''
			item.forEach(data => {
				// 第0直接新增
				if (index == 0) {
					netIncomeData.date[index] = data.date.slice(0, 10)
					// 保存周
					WeekOfYear = getWeekOfYear(data.date)
					netIncomeData.incomeRate[index] = Number((data.incomeRate * 100))
					index = index + 1;
				} else {
					// 判断重复 累加/新增
					if (WeekOfYear == getWeekOfYear(data.date) ) {
						netIncomeData.incomeRate[index - 1] = Number(data.incomeRate * 100) + Number(netIncomeData.incomeRate[index - 1]);
					} else {
						netIncomeData.date[index] = data.date.slice(0, 10)
						netIncomeData.incomeRate[index] = Number(data.incomeRate * 100)
						index = index + 1;
						// 保存周
						WeekOfYear = getWeekOfYear(data.date) 
					}
				}
			});
			// netIncomeData
			// 保留小数点后四位
			netIncomeData.incomeRate.forEach((items, i) => {
				netIncomeData.incomeRate[i] = Number(items).toFixed(4)
			})
			break;
		//月
		case 'month':
			item.forEach(data => {
				// 第0直接新增
				if (index == 0) {
					netIncomeData.date[index] = data.date.slice(0, 7);
					netIncomeData.incomeRate[index] = Number((data.incomeRate * 100));
					index = index + 1;
				} else {
					// 判断重复 累加/新增
					if (netIncomeData.date[index - 1] == data.date.slice(0, 7)) {
						netIncomeData.incomeRate[index - 1] = Number(data.incomeRate * 100) + Number(netIncomeData.incomeRate[index - 1]);
					} else {
						netIncomeData.date[index] = data.date.slice(0, 7)
						netIncomeData.incomeRate[index] = Number(data.incomeRate * 100)
						index = index + 1;
					}
				}
			});
			// 保留小数点后四位
			netIncomeData.incomeRate.forEach((items, i) => {
				netIncomeData.incomeRate[i] = Number(items).toFixed(4)
			})
			break;
	};

	return netIncomeData
}

//累计收益
export function cumulativeIncomeLogic(item, dataVal) {

	item = JSON.parse(item)
	item.reverse();//数组倒排

	//echart数据
	let cumulativeIncomeData = {
		date: [],
		incomeRate: []
	};
	let index = 0;

	//根据所选时间处理echart数据
	switch (dataVal) {
		case 'min':
			item.forEach(data => {
				if (index == 0) {
					cumulativeIncomeData.date[index] = data.date.slice(0, 16);
					cumulativeIncomeData.incomeRate[index] = Number((data.incomeRate * 100).toFixed(4));
					index = index + 1;
				} else {
					cumulativeIncomeData.date[index] = data.date;
					cumulativeIncomeData.incomeRate[index] = (Number((data.incomeRate * 100).toFixed(4)) + Number(cumulativeIncomeData.incomeRate[index - 1])).toFixed(4);
					index = index + 1;
				};
			});
			break;
		case 'day':
			item.forEach(data => {
				if (index == 0) {
					cumulativeIncomeData.date[index] = data.date.slice(0, 10);
					cumulativeIncomeData.incomeRate[index] = Number((data.incomeRate * 100).toFixed(4));
					index = index + 1;
				} else {
					if (cumulativeIncomeData.date[index - 1] == data.date.slice(0, 10)) {
						cumulativeIncomeData.incomeRate[index - 1] = (Number((data.incomeRate * 100).toFixed(4)) + Number(cumulativeIncomeData.incomeRate[index - 1])).toFixed(4);
					} else {
						cumulativeIncomeData.date[index] = data.date.slice(0, 10);
						cumulativeIncomeData.incomeRate[index] = (Number((data.incomeRate * 100).toFixed(4)) + Number(cumulativeIncomeData.incomeRate[index - 1])).toFixed(4);
						index = index + 1;
					}
				};
			});
			break;
		case 'week':
			let WeekOfYear = ''
			item.forEach(data => {
				// 第0直接新增
				if (index == 0) {
					cumulativeIncomeData.date[index] = data.date.slice(0, 10)
					// 保存周
					WeekOfYear = getWeekOfYear(data.date) 
					cumulativeIncomeData.incomeRate[index] = Number((data.incomeRate * 100))
					index = index + 1;
				} else {
					// 判断重复 累加/新增
					if (WeekOfYear == getWeekOfYear(data.date)) {
						cumulativeIncomeData.incomeRate[index - 1] = Number(data.incomeRate * 100) + Number(cumulativeIncomeData.incomeRate[index - 1])
					} else {
						cumulativeIncomeData.date[index] = data.date.slice(0, 10) 
						// 保存周
						WeekOfYear = getWeekOfYear(data.date)
						cumulativeIncomeData.incomeRate[index] = Number(data.incomeRate * 100) + Number(cumulativeIncomeData.incomeRate[index - 1])
						index = index + 1
					}
				}
			});
			// netIncomeData
			// 保留小数点后四位
			cumulativeIncomeData.incomeRate.forEach((items, i) => {
				cumulativeIncomeData.incomeRate[i] = Number(items).toFixed(4)
			})

			break;
		case 'month':

				item.forEach(data => {
					// 第0直接新增
					if (index == 0) {
						cumulativeIncomeData.date[index] = data.date.slice(0, 7)
						cumulativeIncomeData.incomeRate[index] = Number((data.incomeRate * 100))
						index = index + 1
					} else {
						// 判断重复 累加/新增
						if (cumulativeIncomeData.date[index - 1] == data.date.slice(0, 7)) {
							cumulativeIncomeData.incomeRate[index - 1] = Number(data.incomeRate * 100) + Number(cumulativeIncomeData.incomeRate[index - 1])
						} else {
							cumulativeIncomeData.date[index] = data.date.slice(0, 7)
							cumulativeIncomeData.incomeRate[index] = Number(data.incomeRate * 100) + Number(cumulativeIncomeData.incomeRate[index - 1])
							index = index + 1
						}
					}
				})
				// 保留小数点后四位
				cumulativeIncomeData.incomeRate.forEach((items, i) => {
					cumulativeIncomeData.incomeRate[i] = Number(items).toFixed(4)
				})
			break;
	};

	return cumulativeIncomeData
}

//K线图
export function klinkPicLogic(item) {
	//echart数据
	let kLinesData = [];
	
	//开盘、收盘、最低价、最高价、买卖数、涨幅、上引线跌幅、下引线涨幅、量比
	item.forEach((data, index) => {
		let dayData = [];
		dayData[0] = data.date.slice(0, 16);
		dayData[1] = data.open;
		dayData[2] = data.close;
		dayData[3] = data.low;
		dayData[4] = data.high;
		dayData[5] = data.volume;
		dayData[6] = (data.gain * 100).toFixed(2);
		dayData[7] = (data.upLeadGain * 100).toFixed(2);
		dayData[8] = (data.downLeadGain * 100).toFixed(2);
		dayData[9] = (data.volRate).toFixed(2);
		if(data.remark != undefined && data.remark.length > 0){
			dayData[10] = data.remark;
		};
		kLinesData[index] = dayData;
	});
	return kLinesData
}

//K线图 - 买入卖出标记数组
export function getBuySellList(item, marker) {
	var buySellList = [];
	var buySellStatus = '';
	
	if(item != undefined && item.length > 0){
		item.forEach(data => {
			if (data.buySellStatus != "") {
				switch (data.buySellStatus) {
					case 'sell':
						buySellStatus = '卖';
						break;
					case 'buy':
						buySellStatus = '买';
						break;
					case "SELL_OPEN":
						buySellStatus = "开空";
						break;
					case "BUY_CLOSE":
						buySellStatus = "平空";
						break;
				};
				if (buySellStatus == '买' || buySellStatus == '平空') {
					var buySell = {
						value: buySellStatus,
						xAxis: data.date.slice(0, 16),
						yAxis: (data.high) * 1.0007,
						itemStyle: {
							color: '#00da3c'
						}
					};
				} else {
					var buySell = {
						value: buySellStatus,
						xAxis: data.date.slice(0, 16),
						yAxis: (data.low) * 0.9993,
						symbolRotate: 180,
						label: {
							offset: [0, 10]
						},
						itemStyle: {
							color: '#ec0000'
						}
					};
				};
				buySellList.push(buySell);
			};
		});
	};
	
	if(marker != undefined && marker.length > 0){
		marker.forEach(mark=>{
			buySellList.push(mark);
		});
	};
	
	return buySellList
}