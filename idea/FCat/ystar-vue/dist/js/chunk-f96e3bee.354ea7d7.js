(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-f96e3bee"],{"81dd":function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-container",{attrs:{"fill-height":"",fluid:"","grid-list-xl":""}},[a("v-layout",{attrs:{"justify-center":"",wrap:""}},[a("v-flex",[a("material-card",{attrs:{color:"green"}},[a("v-form",[a("v-container",{attrs:{"py-0":""}},[a("v-layout",{attrs:{wrap:""}},[a("v-flex",{attrs:{xs12:"",sm6:"",md4:"",lg2:"","d-flex":""}},[a("v-select",{attrs:{items:t.short,label:"多空"},on:{change:t.changeBuySell},model:{value:t.shortActive,callback:function(e){t.shortActive=e},expression:"shortActive"}})],1),a("v-flex",{attrs:{xs12:"",sm6:"",md4:"",lg2:"","d-flex":""}},[a("v-select",{attrs:{items:t.symbol,label:"币种"},on:{change:t.changeSymbol},model:{value:t.symbolActive,callback:function(e){t.symbolActive=e},expression:"symbolActive"}})],1),a("v-flex",{attrs:{xs12:"",sm6:"",md4:"",lg2:"","d-flex":""}},[a("v-select",{attrs:{items:t.contractType,label:"合约类型"},model:{value:t.contractTypeActive,callback:function(e){t.contractTypeActive=e},expression:"contractTypeActive"}})],1),a("v-flex",{attrs:{xs12:"",sm6:"",md4:"",lg2:"","d-flex":""}},[a("v-select",{attrs:{items:t.period,"item-text":t.period.text,"item-value":t.period.value,label:"时间周期"},model:{value:t.periodActive,callback:function(e){t.periodActive=e},expression:"periodActive"}})],1),a("v-flex",{attrs:{xs12:"",sm3:"",md2:"",lg1:"","d-flex":""}},[a("v-btn",{staticClass:"inquire-btn",attrs:{small:"",loading:t.btnLoading,disabled:t.btnLoading,color:"info"},on:{click:t.inquire}},[t._v("\n                    查 询\n                    "),a("v-icon",{attrs:{right:"",dark:""}})],1)],1),a("v-flex",{attrs:{xs12:"",sm3:"",md2:"",lg2:"","d-flex":""}},[a("v-btn-toggle",{attrs:{mandatory:""},on:{change:t.change},model:{value:t.btnChange,callback:function(e){t.btnChange=e},expression:"btnChange"}},[a("v-btn",{staticClass:"inquire-btn",attrs:{value:"table",color:"info",small:"",disabled:t.btnLoading,loading:t.btnLoading}},[t._v("\n\t            \t\t\t\t表格\n\t\t\t              ")]),a("v-btn",{staticClass:"inquire-btn",attrs:{value:"linkPic",color:"info",small:"",disabled:t.btnLoading,loading:t.btnLoading}},[t._v("\n\t                \t\t折线图\n\t\t\t              ")]),a("v-btn",{staticClass:"inquire-btn",attrs:{value:"klinkPic",color:"info",small:"",disabled:t.btnLoading,loading:t.btnLoading}},[t._v("\n\t\t\t                K线图\n\t\t\t              ")])],1)],1)],1)],1)],1)],1)],1),"table"==t.btnChange?a("v-flex",{attrs:{md12:"",lg12:""}},[a("material-card",{attrs:{color:"#00aec5",title:t.inCome,text:t.sumNum}},[a("v-data-table",{attrs:{headers:t.headers,items:t.items,"hide-actions":""},scopedSlots:t._u([{key:"headerCell",fn:function(e){var n=e.header;return[a("span",{staticClass:"font-weight-light text-info text--darken-3",domProps:{textContent:t._s(n.text)}})]}},{key:"items",fn:function(e){e.index;var n=e.item;return[a("td",[t._v(t._s(n.date))]),a("td",[t._v(t._s(n.open))]),a("td",[t._v(t._s(n.high))]),a("td",[t._v(t._s(n.low))]),a("td",[t._v(t._s(n.close))]),a("td",[t._v(t._s(n.gain))]),a("td",[t._v(t._s(n.volume))]),a("td",[t._v(t._s(n.buySellStatus))]),a("td",[t._v(t._s(n.incomeRate))])]}}],null,!1,3533910488)})],1)],1):t._e(),a("v-flex",{directives:[{name:"show",rawName:"v-show",value:"linkPic"==t.btnChange,expression:"btnChange=='linkPic'"}],attrs:{md12:"",sm12:""}},[a("div",{staticClass:"dateBtnBox"},[a("v-btn-toggle",{attrs:{mandatory:""},on:{change:t.dateChange},model:{value:t.dateBtnVal,callback:function(e){t.dateBtnVal=e},expression:"dateBtnVal"}},[a("v-btn",{staticClass:"inquire-btn",attrs:{value:"min",color:"info",small:"",disabled:t.btnLoading,loading:t.btnLoading}},[t._v("\n\t\t\t\t\t\t\t分\n\t          ")]),a("v-btn",{staticClass:"inquire-btn",attrs:{value:"day",color:"info",small:"",disabled:t.btnLoading,loading:t.btnLoading}},[t._v("\n\t\t\t\t\t\t\t日\n\t          ")]),a("v-btn",{staticClass:"inquire-btn",attrs:{value:"week",color:"info",small:"",disabled:t.btnLoading,loading:t.btnLoading}},[t._v("\n\t    \t\t\t\t周\n\t          ")]),a("v-btn",{staticClass:"inquire-btn",attrs:{value:"month",color:"info",small:"",disabled:t.btnLoading,loading:t.btnLoading}},[t._v("\n        \t\t\t月\n\t          ")])],1)],1),a("div",{staticStyle:{width:"100%",height:"580px"},attrs:{id:"netIncome"}})]),a("v-flex",{directives:[{name:"show",rawName:"v-show",value:"klinkPic"==t.btnChange,expression:"btnChange=='klinkPic'"}],attrs:{md12:"",sm12:""}},[a("div",{staticStyle:{width:"100%",height:"700px"},attrs:{id:"kline"}})])],1)],1)},i=[],s=(a("ac6a"),a("9fe8")),o=a("6629"),c=a("95cf"),l={data:function(){return{shortActive:"多",short:["多","空"],symbolActive:"BTC",symbol:["BTC","EOS"],contractTypeActive:"quarter",contractType:["quarter"],periodActive:"15min",period:[{text:"15分钟",value:"15min"}],upMaNum:4,downMaNum:4,btnLoading:!0,inCome:"",sumNum:"",headers:[{sortable:!1,text:"时间",value:"date"},{sortable:!1,text:"开盘",value:"open"},{sortable:!1,text:"最高点",value:"high"},{sortable:!1,text:"最低点",value:"low"},{sortable:!1,text:"收盘",value:"close"},{sortable:!1,text:"涨跌幅",value:"gain"},{sortable:!1,text:"成交量",value:"volume"},{sortable:!1,text:"买卖状态",value:"buySellStatus"},{sortable:!1,text:"收益率",value:"incomeRate"}],items:[],dailySalesChart:{data:{labels:["M","T","W","T","F","S","S"],series:[[12,17,7,17,23,18,38]]},options:{lineSmooth:this.$chartist.Interpolation.cardinal({tension:0}),low:0,high:50,chartPadding:{top:0,right:0,bottom:0,left:0}}},cumulativeIncomeMin:{},cumulativeIncomeDay:{},cumulativeIncomeWeek:{},cumulativeIncomeMonth:{},netIncomeMin:{},netIncomeDay:{},netIncomeWeek:{},netIncomeMonth:{},incomeStatus:!0,kLineData:{},KLineBuySellList:[],kLineStatus:!0,btnChange:"table",dateBtnVal:"min"}},mounted:function(){this.inquire()},methods:{change:function(t){var e=this;switch(t){case"table":break;case"linkPic":this.incomeStatus&&(setTimeout(function(){Object(o["b"])("netIncome",e.netIncomeMin,e.cumulativeIncomeMin)},10),this.incomeStatus=!this.incomeStatus);break;case"klinkPic":this.kLineStatus&&(setTimeout(function(){Object(o["a"])("kline",e.kLineData,e.upMaNum,e.downMaNum,e.KLineBuySellList)},10),this.kLineStatus=!this.kLineStatus);break}},dateChange:function(t){switch(t){case"min":Object(o["b"])("netIncome",this.netIncomeMin,this.cumulativeIncomeMin);break;case"day":Object(o["b"])("netIncome",this.netIncomeDay,this.cumulativeIncomeDay);break;case"week":Object(o["b"])("netIncome",this.netIncomeWeek,this.cumulativeIncomeWeek);break;case"month":Object(o["b"])("netIncome",this.netIncomeMonth,this.cumulativeIncomeMonth);break}},inquire:function(){var t=this;this.btnLoading=!0;this.buyTypeActive;"多"===this.shortActive?Object(s["b"])("symbol="+this.symbolActive+"&contractType="+this.contractTypeActive+"&period="+this.periodActive).then(function(e){t.initTabularData(e.data.data),t.initTableData(e),t.btnLoading=!1}).catch(function(e){t.btnLoading=!1}):Object(s["d"])("symbol="+this.symbolActive+"&contractType="+this.contractTypeActive+"&period="+this.periodActive).then(function(e){t.initTabularData(e.data.data),t.initTableData(e),t.btnLoading=!1}).catch(function(e){t.btnLoading=!1})},initTableData:function(t){this.items=t.data.data.detail,this.inCome=0==t.data.data.income?"":"总收益 : "+t.data.data.income.toFixed(4)+"%",this.sumNum=0==t.data.data.sumNum?"":"买卖次数 : "+t.data.data.sumNum,this.items.forEach(function(t){switch(t.open=t.open.toFixed(4),t.high=t.high.toFixed(4),t.low=t.low.toFixed(4),t.close=t.close.toFixed(4),t.gain=(100*t.gain).toFixed(4)+"%",t.incomeRate=0==t.incomeRate?"":(100*t.incomeRate).toFixed(4)+"%",t.buySellStatus){case"buy":t.buySellStatus="开多";break;case"sell":t.buySellStatus="平多";break;case"SELL_OPEN":t.buySellStatus="开空";break;case"BUY_CLOSE":t.buySellStatus="平空";break}})},initTabularData:function(t){if(this.netIncomeMin=Object(c["d"])(JSON.stringify(t.detail),"min"),this.netIncomeDay=Object(c["d"])(JSON.stringify(t.detail),"day"),this.netIncomeWeek=Object(c["d"])(JSON.stringify(t.detail),"week"),this.netIncomeMonth=Object(c["d"])(JSON.stringify(t.detail),"month"),this.cumulativeIncomeMin=Object(c["a"])(JSON.stringify(t.detail),"min"),this.cumulativeIncomeDay=Object(c["a"])(JSON.stringify(t.detail),"day"),this.cumulativeIncomeWeek=Object(c["a"])(JSON.stringify(t.detail),"week"),this.cumulativeIncomeMonth=Object(c["a"])(JSON.stringify(t.detail),"month"),this.kLineData=Object(c["c"])(t.kLines),this.KLineBuySellList=Object(c["b"])(t.kLines),"table"==this.btnChange)this.incomeStatus=!0,this.kLineStatus=!0;else if("linkPic"==this.btnChange)switch(this.kLineStatus=!0,this.dateBtnVal){case"min":Object(o["b"])("netIncome",this.netIncomeMin,this.cumulativeIncomeMin);break;case"day":Object(o["b"])("netIncome",this.netIncomeDay,this.cumulativeIncomeDay);break;case"week":Object(o["b"])("netIncome",this.netIncomeWeek,this.cumulativeIncomeWeek);break;case"month":Object(o["b"])("netIncome",this.netIncomeMonth,this.cumulativeIncomeMonth);break}else this.incomeStatus=!0,Object(o["a"])("kline",this.kLineData,this.upMaNum,this.downMaNum,this.KLineBuySellList)},changeBuySell:function(){this.changeSymbol(this.symbolActive)},changeSymbol:function(t){if("多"===this.shortActive)switch(t){case"BTC":this.upMaNum=4,this.downMaNum=4;break;case"EOS":this.upMaNum=20,this.downMaNum=6;break}else switch(t){case"BTC":this.upMaNum=40,this.downMaNum=29;break;case"EOS":this.upMaNum=38,this.downMaNum=28;break}}}},u=l,m=(a("c5e5"),a("2877")),r=Object(m["a"])(u,n,i,!1,null,null,null);e["default"]=r.exports},"9f4b":function(t,e,a){},c5e5:function(t,e,a){"use strict";var n=a("9f4b"),i=a.n(n);i.a}}]);
//# sourceMappingURL=chunk-f96e3bee.354ea7d7.js.map