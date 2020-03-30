<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex>
        <material-card color="green">
          <v-form>
            <v-container py-0>
              <v-layout wrap>
                <!-- 多空 -->
                <v-flex xs12 sm6 md4 lg2 d-flex>
                  <v-select v-model="shortActive" :items="short" label="多空" @change="changeBuySell"></v-select>
                </v-flex>
                <!-- 币种 -->
                <v-flex xs12 sm6 md4 lg2 d-flex>
                  <v-select
                    v-model="symbolActive"
                    :items="symbol"
                    label="币种"
                    @change="changeSymbol"
                  ></v-select>
                </v-flex>
                <!-- 合约类型 -->
                <v-flex xs12 sm6 md4 lg2 d-flex>
                  <v-select v-model="contractTypeActive" :items="contractType" label="合约类型"></v-select>
                </v-flex>
                <!-- 时间周期 -->
                <v-flex xs12 sm6 md4 lg2 d-flex>
                  <v-select
                    v-model="periodActive"
                    :items="period"
                    :item-text="period.text"
                    :item-value="period.value"
                    label="时间周期"
                  ></v-select>
                </v-flex>
                 
                <!-- 查询按钮 -->
                <v-flex xs12 sm3 md2 lg1 d-flex>
                  <v-btn
                    small
                    :loading="btnLoading"
                    :disabled="btnLoading"
                    color="info"
                    class="inquire-btn"
                    @click="inquire"
                  >
                    查 询
                    <v-icon right dark></v-icon>
                  </v-btn>
                </v-flex>
                <v-flex xs12 sm3 md2 lg2 d-flex>
	                <v-btn-toggle @change="change" v-model="btnChange" mandatory>
			              <v-btn 
			              	value="table"
			              	color="info" 
			              	class="inquire-btn" 
			              	small
			              	:disabled="btnLoading"
                    	:loading="btnLoading"	
		              	>
	            				表格
			              </v-btn>
			              <v-btn 
			              	value="linkPic"
			              	color="info" 
			              	class="inquire-btn" 
			              	small
			              	:disabled="btnLoading"
                    	:loading="btnLoading"
			              >
	                		折线图
			              </v-btn>
			              <v-btn 
			              	value="klinkPic"
			              	color="info" 
			              	class="inquire-btn" 
			              	small
			              	:disabled="btnLoading"
                    	:loading="btnLoading"
			              >
			                K线图
			              </v-btn>
			            </v-btn-toggle>
		            </v-flex>
                <!--<v-flex>
                  <v-switch
                    v-model="chartSwitch"
                    label="图表"
                    color="warning"
                    value="warning"
                    hide-details
                    :disabled="btnLoading"
                    :loading="btnLoading"
                    @change="change"
                  ></v-switch>
                </v-flex>-->
              </v-layout>
            </v-container>
          </v-form>
        </material-card>
      </v-flex>

      <!--数据表格-->
      <v-flex v-if="btnChange=='table'" md12 lg12>
        <material-card color="#00aec5" :title="inCome" :text="sumNum">
          <v-data-table :headers="headers" :items="items" hide-actions>
            <template slot="headerCell" slot-scope="{ header }">
              <span class="font-weight-light text-info text--darken-3" v-text="header.text" />
            </template>
            <template slot="items" slot-scope="{ index, item }">
              <td>{{ item.date }}</td>
              <td>{{ item.open }}</td>
              <td>{{ item.high }}</td>
              <td>{{ item.low }}</td>
              <td>{{ item.close }}</td>
              <td>{{ item.gain }}</td>
              <td>{{ item.volume }}</td>
              <td>{{ item.buySellStatus }}</td>
              <td>{{ item.incomeRate }}</td>
            </template>
          </v-data-table>
        </material-card>
      </v-flex>
      <!-- 图表 -->
      <!-- 累计收益 -->

      <v-flex v-show="btnChange=='linkPic'" md12 sm12>
      	<div class="dateBtnBox">
      		<v-btn-toggle @change="dateChange" v-model="dateBtnVal" mandatory>
      			<v-btn 
	          	value="min"
	          	color="info" 
	          	class="inquire-btn" 
	          	small
	          	:disabled="btnLoading"
	    				:loading="btnLoading"	
	      		>
							分
	          </v-btn>
	          <v-btn 
	          	value="day"
	          	color="info" 
	          	class="inquire-btn" 
	          	small
	          	:disabled="btnLoading"
	    				:loading="btnLoading"	
	      		>
							日
	          </v-btn>
	          <v-btn 
	          	value="week"
	          	color="info" 
	          	class="inquire-btn" 
	          	small
	          	:disabled="btnLoading"
	    				:loading="btnLoading"
	          >
	    				周
	          </v-btn>
	          <v-btn 
	          	value="month"
	          	color="info" 
	          	class="inquire-btn" 
	          	small
	          	:disabled="btnLoading"
	    				:loading="btnLoading"
	          >
        			月
	          </v-btn>
	        </v-btn-toggle>
      	</div>
        <div style="width:100%; height:580px;" id="netIncome"></div>
      </v-flex>
      
      <!--K线图-->
      <v-flex v-show="btnChange=='klinkPic'" md12 sm12>
        <div style="width:100%; height:700px;" id="kline"></div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { twoMaStrategy, twoMaStrategySell } from "@/api/demoAddress";
import { totalRevenue, kLinePic } from "@/utils/echart";
//echart图数据算法
// 净收益,累计收益
import { netIncomeLogic, cumulativeIncomeLogic, klinkPicLogic, getBuySellList } from "@/utils/echartLogic"

export default {
  data() {
    return {
      // 多做做空
      shortActive: "多",
      short: ["多", "空"],
      // 币种
      symbolActive: "BTC",
      symbol: ["BTC", "EOS"],
      // 合约类型
      contractTypeActive: "quarter",
      contractType: ["quarter"],
      // 时间周期
      periodActive: "15min",
      period: [
        {
          text: "15分钟",
          value: "15min"
        }
      ],
      // 长期均线
      upMaNum: 4,
      // 短期均线
      downMaNum: 4,
      // 按钮 - 加载
      btnLoading: true,
      // 总收益
      inCome: "",
      // 买卖次数
      sumNum: "",
      // 表格头部label
      headers: [
        {
          sortable: false,
          text: "时间",
          value: "date"
        },
        {
          sortable: false,
          text: "开盘",
          value: "open"
        },
        {
          sortable: false,
          text: "最高点",
          value: "high"
        },
        {
          sortable: false,
          text: "最低点",
          value: "low"
        },
        {
          sortable: false,
          text: "收盘",
          value: "close"
        },
        {
          sortable: false,
          text: "涨跌幅",
          value: "gain"
        },
        {
          sortable: false,
          text: "成交量",
          value: "volume"
        },
        {
          sortable: false,
          text: "买卖状态",
          value: "buySellStatus"
        },
        {
          sortable: false,
          text: "收益率",
          value: "incomeRate"
        }
      ],
      // 表格数据
      items: [],
      // 递增总收益曲线图
      dailySalesChart: {
        data: {
          labels: ["M", "T", "W", "T", "F", "S", "S"],
          series: [[12, 17, 7, 17, 23, 18, 38]]
        },
        options: {
          lineSmooth: this.$chartist.Interpolation.cardinal({
            tension: 0
          }),
          low: 0,
          high: 50, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
          chartPadding: {
            top: 0,
            right: 0,
            bottom: 0,
            left: 0
          }
        }
      },
      //累计收益(分、日、周、月)
      cumulativeIncomeMin:{},
      cumulativeIncomeDay:{},
      cumulativeIncomeWeek:{},
      cumulativeIncomeMonth:{},
      //净收益(分、日、周、月)
      netIncomeMin: {},
      netIncomeDay: {},
      netIncomeWeek: {},
      netIncomeMonth: {},
      //状态
      incomeStatus:true,
      //K线图
      kLineData: {},
      KLineBuySellList: [],
      //状态
      kLineStatus: true,
      btnChange: 'table',
      dateBtnVal: 'min',
    };
  },
  mounted() {
    this.inquire();
  },
  methods: {
  	//数据展示类型切换
  	change(val){
			switch (val){
				case 'table':
					break;
				case 'linkPic':
          if(this.incomeStatus){
            setTimeout(()=>{
              totalRevenue("netIncome",this.netIncomeMin,this.cumulativeIncomeMin);
            },10);
            this.incomeStatus = !this.incomeStatus
          }
					break;
				case 'klinkPic':
          if(this.kLineStatus){
            setTimeout(()=>{
              kLinePic("kline",this.kLineData,this.upMaNum,this.downMaNum,this.KLineBuySellList);
            },10);
            this.kLineStatus = !this.kLineStatus
          }
					break;
			}
  	},
  	//折线图 - 展示时间切换
  	dateChange(val){
  		let _this = this;
  		switch (val){
  			case 'min':
  				totalRevenue("netIncome",this.netIncomeMin,this.cumulativeIncomeMin);
  				break;
  			case 'day':
  				totalRevenue("netIncome",this.netIncomeDay,this.cumulativeIncomeDay);
  				break;
  			case 'week':
  				totalRevenue("netIncome",this.netIncomeWeek,this.cumulativeIncomeWeek);
  				break;
  			case 'month':
  				totalRevenue("netIncome",this.netIncomeMonth,this.cumulativeIncomeMonth);
  				break;
  		}
  	},
  	//初始化
    inquire() {
      this.btnLoading = true;

      let buyType = this.buyTypeActive === "等额" ? 0 : 1;

      if (this.shortActive === "多") {
        twoMaStrategy(
          "symbol=" +
            this.symbolActive +
            "&contractType=" +
            this.contractTypeActive +
            "&period=" +
            this.periodActive
        )
          .then(res => {
            this.initTabularData(res.data.data)
            this.initTableData(res);
            this.btnLoading = false;
          })
          .catch(e => {
            this.btnLoading = false;
          });
      } else {
        twoMaStrategySell(
          "symbol=" +
            this.symbolActive +
            "&contractType=" +
            this.contractTypeActive +
            "&period=" +
            this.periodActive
        )
          .then(res => {
            this.initTabularData(res.data.data)
            this.initTableData(res);
            this.btnLoading = false;
          })
          .catch(e => {
            this.btnLoading = false;
          });
      }
    },
    // 初始化表格数据
    initTableData(res) {
			this.items = res.data.data.detail;
			
      this.inCome =
        res.data.data.income == 0
          ? ""
          : "总收益 : " + res.data.data.income.toFixed(4) + "%";
      this.sumNum =
        res.data.data.sumNum == 0 ? "" : "买卖次数 : " + res.data.data.sumNum;
      this.items.forEach(item => {
        // 开盘
        item.open = item.open.toFixed(4);
        // 最高点
        item.high = item.high.toFixed(4);
        // 最低点
        item.low = item.low.toFixed(4);
        // 收盘
        item.close = item.close.toFixed(4);
        // 涨跌幅
        item.gain = (item.gain * 100).toFixed(4) + "%";
        // 收益率
        item.incomeRate =
          item.incomeRate == 0 ? "" : (item.incomeRate * 100).toFixed(4) + "%";

        // 买卖状态
        switch (item.buySellStatus) {
          case "buy":
            item.buySellStatus = "开多";
            break;
          case "sell":
            item.buySellStatus = "平多";
            break;
          case "SELL_OPEN":
            item.buySellStatus = "开空";
            break;
          case "BUY_CLOSE":
            item.buySellStatus = "平空";
            break;
        }
      });
    },
    // 初始化表格数据
    initTabularData(data){
      //净收益(分、日、周、月) /累计收益(分、日、周、月) /k线数据 /k线买卖 
      this.netIncomeMin = netIncomeLogic(JSON.stringify(data.detail), 'min');
      this.netIncomeDay = netIncomeLogic(JSON.stringify(data.detail), 'day');
      this.netIncomeWeek = netIncomeLogic(JSON.stringify(data.detail), 'week');
      this.netIncomeMonth = netIncomeLogic(JSON.stringify(data.detail), 'month');
      
      this.cumulativeIncomeMin = cumulativeIncomeLogic(JSON.stringify(data.detail), 'min');
      this.cumulativeIncomeDay = cumulativeIncomeLogic(JSON.stringify(data.detail), 'day');
      this.cumulativeIncomeWeek = cumulativeIncomeLogic(JSON.stringify(data.detail), 'week');
      this.cumulativeIncomeMonth = cumulativeIncomeLogic(JSON.stringify(data.detail), 'month');
      
      this.kLineData = klinkPicLogic(data.kLines);
      this.KLineBuySellList = getBuySellList(data.kLines);

      //按钮切换
      if(this.btnChange == 'table'){
        // 设置为从新渲染
        this.incomeStatus = true
        this.kLineStatus = true
      }else if(this.btnChange == 'linkPic'){
        this.kLineStatus = true
        switch (this.dateBtnVal){
        	case 'min':
	  				totalRevenue("netIncome",this.netIncomeMin,this.cumulativeIncomeMin);
	  				break;
	  			case 'day':
	  				totalRevenue("netIncome",this.netIncomeDay,this.cumulativeIncomeDay);
	  				break;
	  			case 'week':
	  				totalRevenue("netIncome",this.netIncomeWeek,this.cumulativeIncomeWeek);
	  				break;
	  			case 'month':
	  				totalRevenue("netIncome",this.netIncomeMonth,this.cumulativeIncomeMonth);
	  				break;
        };
      }else {
        this.incomeStatus = true
        kLinePic("kline",this.kLineData,this.upMaNum,this.downMaNum,this.KLineBuySellList);
      };
    },
    // 多空切换
    changeBuySell() {
      this.changeSymbol(this.symbolActive);
    },
    //监听币种下拉框
    changeSymbol(symbolActive) {
      if (this.shortActive === "多") {
        switch (symbolActive) {
          case "BTC":
            this.upMaNum = 4;
            this.downMaNum = 4;
            break;
          case "EOS":
            this.upMaNum = 20;
            this.downMaNum = 6;
            break;
        }
      } else {
        switch (symbolActive) {
          case "BTC":
            this.upMaNum = 40;
            this.downMaNum = 29;
            break;
          case "EOS":
            this.upMaNum = 38;
            this.downMaNum = 28;
            break;
        }
      }
    }
  }
};
</script>

<style>
	.mar-24 {
		margin-top: 24px;
	}
	
	.v-btn-toggle--selected {
		box-shadow: none;
	}
	
	.theme--light.v-btn-toggle {
		padding: 10px;
	}
	
	.dateBtnBox {
		width: 100%;
		background: #FFF;
		text-align: right;
	}
</style>