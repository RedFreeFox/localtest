(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d213107"],{aaa2:function(t,a,e){"use strict";e.r(a);var n=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("v-container",{attrs:{"fill-height":"",fluid:"","grid-list-xl":""}},[e("v-layout",{attrs:{"justify-center":"",wrap:""}},[e("v-flex",{attrs:{md12:"",sm12:""}},[e("div",{staticStyle:{width:"100%",height:"800px"},attrs:{id:"kline"}})])],1)],1)},i=[],u=e("6629"),c=e("9fe8"),l=e("95cf"),d={data:function(){return{upMaNum:0,downMaNum:0,KLineData:[],KLineBuySellList:[]}},mounted:function(){this.getKLine()},methods:{getKLine:function(){var t=this;Object(c["a"])().then(function(a){t.kLineData=Object(l["c"])(a.data.data.k),t.KLineBuySellList=Object(l["b"])([],a.data.data.mark),t.upMaNum=a.data.data.mas[0],t.downMaNum=a.data.data.mas[1],Object(u["a"])("kline",t.kLineData,t.upMaNum,t.downMaNum,t.KLineBuySellList)}).catch(function(t){})}}},s=d,r=e("2877"),o=Object(r["a"])(s,n,i,!1,null,null,null);a["default"]=o.exports}}]);
//# sourceMappingURL=chunk-2d213107.fb7dcff2.js.map