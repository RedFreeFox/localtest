//净收益
export function totalRevenue(e, item, item2){

	var myChart = echarts.init(document.getElementById(e));
	var app = {};
	var option = null;
	option = {
		backgroundColor: '#fff',
		title: {
			text: '收益情况',
			left: '1%',
			textStyle: {
				color: '#495057'
			}
		},
		legend: {
	        data:['净收益','累计收益']
	    },
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				lineStyle: {
					color: '#f9d797'
				}
			},
		},
		grid: {
			x: 60,
			x2: 40,
			y: 65,
			y2: 80,
		},
		xAxis: [{
			type: 'category',
			boundaryGap: false,
			axisLine: {
				lineStyle: {
					color: '#575d64'
				}
			},
			axisLabel: {
				textStyle: {
					color: '#575d64'
				},
				rotate: 40,
				fontSize: 12,
				align: 'center',
				margin: 30
			},
			axisTick: {
				show: false
			},
//			data: ['13:00', '13:05', '13:10', '13:15', '13:20', '13:25', '13:30', '13:35', '13:40', '13:45', '13:50', '13:55']
			data: item.date
		}],
		yAxis: [{
			type: 'value',
			name: '收益率（%）',
			axisTick: {
				show: false
			},
			axisLine: {
				lineStyle: {
					color: '#575d64'
				}
			},
			axisLabel: {
				margin: 10,
				textStyle: {
					fontSize: 14,
					color: '#575d64'
				}
			},
			splitLine: {
				show: false,
				lineStyle: {
					color: 'red'
				}
			}
		}],
		series: [{
			name: '净收益',
			type: 'line',
			symbol: 'circle',
			symbolSize: 5,
			showSymbol: true,
			lineStyle: {
				normal: {
					width: 1,
					color: {
						type: 'linear',
						x: 0,
						y: 0,
						x2: 1,
						y2: 0,
						colorStops: [{
							offset: 0,
							color: '#e0abab' // 0% 处的颜色
						}, {
							offset: 1,
							color: '#e0abab' // 100% 处的颜色
						}],
						globalCoord: false // 缺省为 false
					},
					opacity: 0.9
				}
			},
//			itemStyle: {
//				normal: {
//					color: 'rgba(224, 171, 171, 0.95);',
//					borderColor: 'rgba(224, 171, 171, 0.27)',
//					borderWidth: 5
//	
//				}
//			},
//			data: [220, 182, 191, 134, 250, 120, 110, 125, 145, 122, 165, 122]
			data: item.incomeRate
		},{
			name: '累计收益',
			type: 'line',
			symbol: 'circle',
			symbolSize: 5,
			showSymbol: true,
			lineStyle: {
				normal: {
					width: 1,
					color: {
						type: 'linear',
						x: 0,
						y: 0,
						x2: 1,
						y2: 0,
						colorStops: [{
							offset: 0,
							color: '#00aec5' // 0% 处的颜色
						}, {
							offset: 1,
							color: '#00aec5' // 100% 处的颜色
						}],
						globalCoord: false // 缺省为 false
					},
					opacity: 0.9
				}
			},
			areaStyle: {
				normal: {
					color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						offset: 0,
						color: '#00aec5'
					}, {
						offset: 0.8,
						color: 'white'
					}], false),
					shadowColor: 'rgba(0, 0, 0, 0.1)',
					shadowBlur: 10
				}
			},
//			itemStyle: {
//				normal: {
//					color: 'rgba(0, 174, 197, 0.95);',
//					borderColor: 'rgba(0, 174, 197, 0.27)',
//					borderWidth: 5
//	
//				}
//			},
//			data: [220, 182, 191, 134, 250, 120, 110, 125, 145, 122, 165, 122]
			data: item2.incomeRate
		}]
	};

	if(option && typeof option === "object") {
		myChart.setOption(option, true);
	};
	
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}

//累计收益
export function cumulativeIncome(e, item){
	var myChart = echarts.init(document.getElementById(e));
	var app = {};
	var option = null;
	option = {
		backgroundColor: '#fff',
		title: {
			text: '累计收益',
			left: '1%',
			top: '3%',
			textStyle: {
				color: '#495057'
			}
		},
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				lineStyle: {
					color: '#f9d797'
				}
			},
			formatter: '{b}</br>{a} : {c}%'
		},
		grid: {
			x: 60,
			x2: 40,
			y: 80,
			y2: 80,
		},
		xAxis: [{
			type: 'category',
			boundaryGap: false,
			axisLine: {
				lineStyle: {
					color: '#575d64'
				}
			},
			axisLabel: {
				textStyle: {
					color: '#575d64'
				},
				rotate: 40,
				fontSize: 12,
				align: 'center',
				margin: 40
			},
			axisTick: {
				show: false
			},
//			data: ['13:00', '13:05', '13:10', '13:15', '13:20', '13:25', '13:30', '13:35', '13:40', '13:45', '13:50', '13:55']
			data: item.date
		}],
		yAxis: [{
			type: 'value',
			name: '收益率（%）',
			axisTick: {
				show: false
			},
			axisLine: {
				lineStyle: {
					color: '#575d64'
				}
			},
			axisLabel: {
				margin: 10,
				textStyle: {
					fontSize: 14,
					color: '#575d64'
				}
			},
			splitLine: {
				show: false,
				lineStyle: {
					color: 'red'
				}
			}
		}],
		series: [{
			name: '收益率',
			type: 'line',
			symbol: 'circle',
			symbolSize: 3,
			showSymbol: true,
			lineStyle: {
				normal: {
					width: 1,
					color: {
						type: 'linear',
						x: 0,
						y: 0,
						x2: 1,
						y2: 0,
						colorStops: [{
							offset: 0,
							color: '#00aec5' // 0% 处的颜色
						}, {
							offset: 1,
							color: '#00aec5' // 100% 处的颜色
						}],
						globalCoord: false // 缺省为 false
					},
					opacity: 0.9
				}
			},
			areaStyle: {
				normal: {
					color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						offset: 0,
						color: '#00aec5'
					}, {
						offset: 0.8,
						color: 'white'
					}], false),
					shadowColor: 'rgba(0, 0, 0, 0.1)',
					shadowBlur: 10
				}
			},
			itemStyle: {
				normal: {
					color: 'rgba(0, 174, 197, 0.95);',
					borderColor: 'rgba(0, 174, 197, 0.27)',
					borderWidth: 5
				}
			},
//			data: [220, 182, 191, 134, 250, 120, 110, 125, 145, 122, 165, 122]
			data: item.incomeRate
		}]
	};

	if(option && typeof option === "object") {
		myChart.setOption(option, true);
	};
	
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}

//K线图
export function kLinePic(e, item, upMA, downMA, buySellList){
	
	var upColor = '#00da3c';
	var downColor = '#ec0000';
	
	function splitData(rawData) {
	    var categoryData = [];
	    var values = [];
	    var volumes = [];
	    for (var i = 0; i < rawData.length; i++) {
	        categoryData.push(rawData[i].splice(0, 1)[0]);
	        values.push(rawData[i]);
	        volumes.push([i, rawData[i][4], rawData[i][0] > rawData[i][1] ? 1 : -1]);
	    }
	
	    return {
	        categoryData: categoryData,
	        values: values,
	        volumes: volumes
	    };
	}
	
	function calculateMA(dayCount, data) {
	    var result = [];
	    for (var i = 0, len = data.values.length; i < len; i++) {
	        if (i < dayCount) {
	            result.push('-');
	            continue;
	        }
	        var sum = 0;
	        for (var j = 0; j < dayCount; j++) {
	            sum += data.values[i - j][1];
	        }
	        result.push(+(sum / dayCount).toFixed(3));
	    }
	    return result;
	}
	
	var kLineData = splitData(item);
	
	var myChart = echarts.init(document.getElementById(e));
	var app = {};
	var option = null;
	option = {
        animation: false,
        legend: {
        	top: 'top',
            left: 'right',
            data: ['蜡烛K线', 'MA'+upMA, 'MA'+downMA]
        },
        tooltip: {
//          trigger: 'item',
			trigger: 'axis',
            axisPointer: {
                type: 'cross'
            },
            backgroundColor: '#eeeeee',
            borderWidth: 0,
            borderColor: '#ccc',
            padding: 0,
            textStyle: {
                color: '#000000'
            },
//          position: function (pos, params, el, elRect, size) {
//              var obj = {top: 10};
//              obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
//              return obj;
//          },
			position: [70,0],
			formatter: function(params) {
				var res = '';
//				res = params[3].name + '</br>' + params[3].seriesName + ' : ' + params[3].value[1];
//				
//				res += '</br></br>' + params[0].name + '</br>' + params[0].seriesName + 
//				' :</br>开 : ' + params[0].value[1] +
//				'</br>收 : ' + params[0].value[2] + 
//				'</br>低 : ' + params[0].value[3] +
//				'</br>高 : ' + params[0].value[4] +
//				'</br>涨幅  : ' + params[0].value[6] + '%' +
//				'</br>上引线跌幅 : ' + params[0].value[7] + '%' +
//				'</br>下引线涨幅 : ' + params[0].value[8] + '%' +
//				'</br>量比 : ' + params[0].value[9] + 
//				'</br>' + params[1].seriesName + ' : ' + params[1].value + 
//				'</br>' + params[2].seriesName + ' : ' + params[2].value;
				
				if(params[0].componentSubType == "candlestick"){ //K线 数据
					res = 
					params[params.length-1].name + '&nbsp&nbsp&nbsp&nbsp&nbsp' + params[params.length-1].seriesName + ' : ' + params[params.length-1].value[1] + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
					'开 : ' + params[0].value[1] + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
					'收 : ' + params[0].value[2] + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
					'低 : ' + params[0].value[3] + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
					'高 : ' + params[0].value[4] + '&nbsp&nbsp&nbsp&nbsp&nbsp';
					
					if(Number(params[0].value[6]) > 0){
						res += '涨幅 : <span style="color:' + '#00da3c' + '">' + params[0].value[6] + '%</span>' + '</br>';
					}else {
						res += '涨幅 : <span style="color:' + '#ec0000' + '">' + params[0].value[6] + '%</span>' + '</br>';
					};
					
					res += '上引线跌幅 : ' + params[0].value[7] + '%' + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
					'下引线涨幅 : ' + params[0].value[8] + '%' + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
					'量比 : ' + params[0].value[9] + '&nbsp&nbsp&nbsp&nbsp&nbsp';
					
					if(params[1] != undefined){
						res += params[1].seriesName + ' : ' + params[1].value + '&nbsp&nbsp&nbsp&nbsp&nbsp';
					}
					
					if(params[2] != undefined){
						res += params[2].seriesName + ' : ' + params[2].value + '&nbsp&nbsp&nbsp&nbsp&nbsp';
					}
					
					if(params[0].data[10] != undefined && params[0].data[10].length > 0){
						params[0].data[10].forEach(remark=>{
							res += remark.k  + ' : ' + remark.V + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						});
					};
				}else if(params[0].componentSubType == "bar"){ //柱状图  数据
					if(params[1] == undefined){
						res =
						params[0].name + '&nbsp&nbsp&nbsp&nbsp&nbsp' + params[0].seriesName + ' : ' + params[0].value[1] + '&nbsp&nbsp&nbsp&nbsp&nbsp';
					}else if(params[1].componentSubType == "line"){
						res =
						params[0].name + '&nbsp&nbsp&nbsp&nbsp&nbsp' + params[0].seriesName + ' : ' + params[0].value[1] + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						
						if(params[1] != undefined){
							res += params[1].seriesName + ' : ' + params[1].value + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						}
						
						if(params[2] != undefined){
							res += params[2].seriesName + ' : ' + params[2].value + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						}
					}else {
						res =
						params[0].name + '&nbsp&nbsp&nbsp&nbsp&nbsp' + params[0].seriesName + ' : ' + params[0].value[1] + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						'开 : ' + params[1].value[1] + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						'收 : ' + params[1].value[2] + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						'低 : ' + params[1].value[3] + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						'高 : ' + params[1].value[4] + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						
						if(Number(params[1].value[6]) > 0){
							res += '涨幅 : <span style="color:' + '#00da3c' + '">' + params[1].value[6] + '%</span>' + '</br>';
						}else {
							res += '涨幅 : <span style="color:' + '#ec0000' + '">' + params[1].value[6] + '%</span>' + '</br>';
						};
						
						res +=
						'上引线跌幅 : ' + params[1].value[7] + '%' + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						'下引线涨幅 : ' + params[1].value[8] + '%' + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						'量比 : ' + params[1].value[9] + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						
						if(params[2] != undefined){
							res += params[2].seriesName + ' : ' + params[2].value + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						}
						
						if(params[3] != undefined){
							res += params[3].seriesName + ' : ' + params[3].value + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						}
						
						if(params[1].data[10] != undefined && params[1].data[10].length > 0){
							params[1].data[10].forEach(remark=>{
								res += remark.k  + ' : ' + remark.V + '&nbsp&nbsp&nbsp&nbsp&nbsp';
							});
						};
					};
				}else if(params[0].componentSubType == "line"){ //MA 折线数据
					if(params[1].componentSubType == "bar"){
						res = 
						params[1].name + '&nbsp&nbsp&nbsp&nbsp&nbsp' + params[1].seriesName + ' : ' + params[1].value[1] + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						
						if(params[0] != undefined){
							res += params[0].seriesName + ' : ' + params[0].value + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						}
					}else if(params[2].componentSubType == "bar"){
						res = 
						params[2].name + '&nbsp&nbsp&nbsp&nbsp&nbsp' + params[2].seriesName + ' : ' + params[2].value[1] + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						
						if(params[0] != undefined){
							res += params[0].seriesName + ' : ' + params[0].value + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						}
						if(params[1] != undefined){
							res += params[1].seriesName + ' : ' + params[1].value + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						}
					}else if(params[2].componentSubType == "candlestick"){
						res = 
						params[3].name + '&nbsp&nbsp&nbsp&nbsp&nbsp' + params[3].seriesName + ' : ' + params[3].value[1] + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						'开 : ' + params[2].value[1] + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						'收 : ' + params[2].value[2] + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						'低 : ' + params[2].value[3] + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						'高 : ' + params[2].value[4] + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						
						if(Number(params[2].value[6]) > 0){
							res += '涨幅 : <span style="color:' + '#00da3c' + '">' + params[2].value[6] + '%</span>' + '</br>';
						}else {
							res += '涨幅 : <span style="color:' + '#ec0000' + '">' + params[2].value[6] + '%</span>' + '</br>';
						};
						
						res +=
						'上引线跌幅 : ' + params[2].value[7] + '%' + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						'下引线涨幅 : ' + params[2].value[8] + '%' + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						'量比 : ' + params[2].value[9] + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						params[0].seriesName + ' : ' + params[0].value + '&nbsp&nbsp&nbsp&nbsp&nbsp' +
						params[1].seriesName + ' : ' + params[1].value + '&nbsp&nbsp&nbsp&nbsp&nbsp';
						
						if(params[2].data[10] != undefined && params[2].data[10].length > 0){
							params[2].data[10].forEach(remark=>{
								res += remark.k  + ' : ' + remark.V + '&nbsp&nbsp&nbsp&nbsp&nbsp';
							});
						};
					};
				};
				return res
			}
        },
        axisPointer: {
            link: {xAxisIndex: 'all'},
            label: {
                backgroundColor: '#777'
            }
        },
        visualMap: {
            show: false,
            seriesIndex: 5,
            dimension: 2,
            pieces: [{
                value: 1,
                color: downColor
            }, {
                value: -1,
                color: upColor
            }]
        },
        grid: [
            {
                left: '5%',
                right: '1%',
                top: '10%',
                height: '64%'
            },
            {
                left: '5%',
                right: '1%',
                top: '79%',
                height: '12%'
            }
        ],
        xAxis: [
            {
                type: 'category',
                data: kLineData.categoryData,
                scale: true,
                boundaryGap : false,
                axisLine: { lineStyle: { color: '#8392A5' } },
                splitLine: {show: false},
                splitNumber: 20,
                min: 'dataMin',
                max: 'dataMax',
                axisPointer: {
                    z: 100
                }
            },
            {
                type: 'category',
                gridIndex: 1,
                data: kLineData.categoryData,
                scale: true,
                boundaryGap : false,
                axisLine: {onZero: false},
                axisTick: {show: false},
                splitLine: {show: false},
                axisLabel: {show: false},
                splitNumber: 20,
                min: 'dataMin',
                max: 'dataMax'
            }
        ],
        yAxis: [
            {
                scale: true,
                axisLine: { lineStyle: { color: '#8392A5' } },
            },
            {
                scale: true,
                gridIndex: 1,
                splitNumber: 2,
                axisLabel: {show: false},
                axisLine: {show: false},
                axisTick: {show: false},
                splitLine: {show: false}
            }
        ],
        dataZoom: [
            {
                type: 'inside',
                xAxisIndex: [0, 1],
                start: 98,
                end: 100,
                textStyle: {
                    color: '#8392A5'
                },
                dataBackground: {
                    areaStyle: {
                        color: '#8392A5'
                    },
                    lineStyle: {
                        opacity: 0.8,
                        color: '#8392A5'
                    }
                },
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            },
            {
                show: true,
                xAxisIndex: [0, 1],
                type: 'slider',
                top: '94%',
                start: 98,
                end: 100
            }
        ],
        series: [
            {
                name: '蜡烛K线',
                type: 'candlestick',
                data: kLineData.values,
                itemStyle: {
                    normal: {
                        color: upColor,
                        color0: downColor,
                        borderColor: null,
                        borderColor0: null
                    }
                },
                markPoint: {
	                data: buySellList,
	                symbolSize: [45,45],
	                silent: true,
	            },
//	            tooltip: {
//	            	position: function (pos, params, el, elRect, size) {
//		                var obj = {top: 10};
//		                obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
//		                return obj;
//		            },
//	            	formatter: function(params) {
//						var res = '';
//						
//						res = params.name + '</br>量  : ' + params.value[5] + '</br></br>' +
//						params.seriesName + ' : </br>开 : ' + params.value[1] +
//						'</br>收 : ' + params.value[2] + 
//						'</br>低 : ' + params.value[3] +
//						'</br>高 : ' + params.value[4] +
//						'</br>涨幅 : ' + params.value[6] + '%' +
//						'</br>上引线跌幅 : ' + params.value[7] + '%' +
//						'</br>下引线涨幅 : ' + params.value[8] + '%' +
//						'</br>量比 : ' + params.value[9] 
//						
//						return res
//					}
//	            }
            },
            {
                name: 'MA' + upMA,
                type: 'line',
                showSymbol: false,
                data: calculateMA(upMA, kLineData),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                },
//              tooltip: {
//	            	formatter: '{b}</br>{a} : {c}'
//	            }
            },
            {
                name: 'MA' + downMA,
                type: 'line',
                showSymbol: false,
                data: calculateMA(downMA, kLineData),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                },
//              tooltip: {
//	            	formatter: '{b}</br>{a} : {c}'
//          	}
            },
            {
                name: 'MA',
                type: 'line',
            },
            {
                name: 'MA',
                type: 'line'
            },
            {
                name: '量',
                type: 'bar',
                xAxisIndex: 1,
                yAxisIndex: 1,
                data: kLineData.volumes,
//              tooltip: {
//	            	formatter: function(params) {
//	            		var res = '';
//	            		res = params.name + '</br>量 : ' + params.value[1]; 
//	            		return res
//	            	}
//	            }
            }
        ]
	};

	if(option && typeof option === "object") {
		myChart.setOption(option, true);
	};
	
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}
