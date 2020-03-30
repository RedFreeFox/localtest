<template>
  <v-container
    fill-height
    fluid
    grid-list-xl
  >

    <v-layout justify-center wrap>
      <v-flex>
        <material-card
          color="green">
          <v-form>
            <v-container py-0>
              <v-layout wrap>
                <!-- 多空 -->
                <v-flex xs12 sm6 md4 lg2 d-flex >
                  <v-select
                    v-model="shortActive"
                    :items="short"
                    label="多空"
                    @change="changeSymbol"
                  ></v-select>
                </v-flex>
                <!-- 币种 -->
                <v-flex xs12 sm6 md4 lg2 d-flex >
                    <v-select
                      v-model="symbolActive"
                      :items="symbol"
                      label="币种"
                      @change="changeSymbol"
                    ></v-select>
                </v-flex>
                <!-- 合约类型 -->
                <v-flex xs12 sm6 md4 lg2 d-flex >
                    <v-select
                      v-model="contractTypeActive"
                      :items="contractType"
                      label="合约类型"
                    ></v-select>
                </v-flex>
                <!-- 时间周期 -->
                <v-flex xs12 sm6 md4 lg2 d-flex >
                    <v-select
                      v-model="periodActive"
                      :items="period"
                      :item-text="period.text"
                      :item-value="period.value"
                      label="时间周期"
                    ></v-select>
                </v-flex>
                
                <!-- 查询按钮 -->
                <v-flex xs12 sm3 md2 lg1 d-flex >
                   <v-btn small
                    :loading="btnLoading"
                    :disabled="btnLoading"
                    color="info"
                    class="inquire-btn"
                    @click="inquire"
                  >
                    查 询
                    <v-icon right dark></v-icon>
                  </v-btn>
                   <!-- <v-btn color="info" small>查 询</v-btn> -->
                </v-flex>
              </v-layout>
            </v-container>
          </v-form>
        </material-card>
      </v-flex>
      <div class=""></div>
       <!-- 矩形box -->
      <v-flex>
        <v-card>
          <v-responsive :aspect-ratio="16/9" class="matrix-r">
          <!-- 设置16比9的高度 -->
            <div ref="slide" :style="'height:'+slideHeight / 16 * 9+'px;'" class="matrix-slide">
              <div class="matrix-box" draggable="true">
                <div class="matrix-line" v-for="item in matrixData" :key="item.index">
                    <div class="matrix-single" :class="items.style" v-for="items in item" :key="items.index">
                      <div v-if="items.value != 0">
                         <p>{{items.value}}% </p>
                         <p>({{ items.m }},{{items.n}}) </p>
                      </div>
                     
                    </div>
                </div>
              </div>
            </div>
          </v-responsive>
        </v-card>
      </v-flex>

    </v-layout>
  </v-container>
</template>

<script>
import { twoMaStrategyBest ,twoMaStrategySellBest} from '@/api/demoAddress'
import { setTimeout } from 'timers';

export default {
  data () {
    return {
      // 币种
      symbolActive:'BTC',
      symbol:['BTC','EOS'],
      // 合约类型
      contractTypeActive:'quarter',
      contractType:['quarter'],
      // 时间周期
      periodActive:'15min',
      period: [{
      	text: '15分钟',
      	value: '15min'
      }],
       
      // 多做做空
      shortActive:'多',
      short:['多','空'],
      // 加载
      btnLoading: true,
      // 矩阵数据
      matrixData:[],
      slideHeight:'',

    }
  },
  mounted(){
    this.inquire()

  },
  methods: {
    inquire(){
        this.btnLoading = true
        let _this = this


        let buyType = this.buyTypeActive === '等额' ? 0:1
        // 多空判断   调取不同接口
        if(this.shortActive == '多'){
          twoMaStrategyBest('symbol='+ this.symbolActive +
                      '&contractType='+ this.contractTypeActive +
                      '&period=' + this.periodActive).then( res =>{
                      // console.log(res)
                    let data = res.data,
                        list = data.data
                    this.btnLoading = false
                    if(data.code == 0){

                      // 上颜色
                      for(var i = 0; i<list.length; i++){
                        if(i < 20 &&  list[i].value > 0){
                          list[i].style = 'color'+ i
                        }else if(list[i].value > 0){
                          list[i].style = 'colorn'
                        }else if(list[i].value <= 0){
                          list[i].style = 'not'
                        }
                      }
                      this.matrixData =  this.matrixAlgorithm(list);

                      setTimeout(()=>{
                        _this.slideHeight = this.$refs.slide.offsetWidth
                        
                      },500)

                      // console.log('matrixData',JSON.stringify(this.matrixData));
                    }else{
                      console.log(data.msg)
                    }
          }).catch(e=>{ 
            this.btnLoading = false
          })
        }else{
            twoMaStrategySellBest('symbol='+ this.symbolActive +
                      '&contractType='+ this.contractTypeActive +
                      '&period=' + this.periodActive).then( res =>{
                      // console.log(res)
                    let data = res.data,
                        list = data.data
                    this.btnLoading = false
                    if(data.code == 0){

                      // 上颜色
                      for(var i = 0; i<list.length; i++){
                        if(i < 20 &&  list[i].value > 0){
                          list[i].style = 'color'+ i
                        }else if(list[i].value > 0){
                          list[i].style = 'colorn'
                        }else if(list[i].value <= 0){
                          list[i].style = 'not'
                        }
                      }
                      this.matrixData =  this.matrixAlgorithm(list);
                      setTimeout(()=>{
                        _this.slideHeight = this.$refs.slide.offsetWidth
                      },500)

                      // console.log('matrixData',JSON.stringify(this.matrixData));
                    }else{
                      console.log(data.msg)
                    }
          }).catch(e=>{
            this.btnLoading = false
          })

        }
    },
    // 矩阵算法
    matrixAlgorithm(data){

      let matris = [],
          len = 1
        // 形成矩阵
        algorithm()
        function algorithm(){
          //过滤m轴
          for(var i = 0; i < data.length; i++){
            if(data[i].m === len){
              let line = []
              //获取二维数组
              for(var j = 0;j<data.length;j++){
                let item = data[j]
                if(item.m == len){
                    line.push(item)
                }                  
              }
              // 添加到数组
              matris.push(line)
              len = len + 1
              algorithm()
              break;
            }
          }
        }

        // 二维数组排序

        matris.forEach(item=>{
          for(var i = 0;i<item.length-1;i++) {
            for(var j = 0;j<item.length-i-1;j++){
                if(item[j].n > item[j+1].n){
                    //把大的数字放到后面
                    var swap = item[j];
                    item[j] = item[j+1];
                    item[j+1] = swap;
                }
            }
          }
        })
       return matris
    },
    // 下拉改变
    changeSymbol(){
       
    }
  }
}
</script>


<style lang="scss">
  .matrix-slide{
    overflow: auto;
  }
  .matrix-box{
    width: 100%;
    height: 100%;
    display: grid;
  }
  .matrix-line{
    width: 100%;
    display: flex;
    .color0{
      background-color: #B71C1C;
      color: #ffffff
    }
    .color1{
      background-color: #C62828;
      color: #ffffff

    }
    .color2{
      background-color: #D32F2F;
      color: #ffffff

    }
    .color3{
      background-color: #E53935;
      color: #ffffff

    }
    .color4{
      background-color: #EF5350;
      color: #ffffff

    }
    .color5{
      background-color: #E57373
    }
    .color6{
      background-color: #EF9A9A
    }
    .color7{
      background-color: #FFCDD2
    }
    .color8{
      background-color: #FFCDD2
    }
    .color9{
      background-color: #FFCDD2
    }
    .color10{
      background-color: #FFCDD2
      
    }
    .color11{
      background-color: #FFCDD2
      
    }
    .color12{
      background-color: #FFCDD2
     
    }
     .color13{
      background-color: #FFCDD2
     
    }
     .color14{
      background-color: #FFCDD2
     
    }
     .color15{
      background-color: #FFCDD2
     
    }
     .color16{
      background-color: #FFCDD2
     
    }
     .color17{
      background-color: #FFCDD2
     
    }
     .color18{
      background-color: #FFCDD2
     
    }
    .color19{
      background-color: #FFCDD2
     
    }
   .color20{
      background-color: #FFCDD2
     
    }
    .colorn{
      background-color: #FFEBEE
    }
    .not{
      background-color: #E0E0E0;
      color:#424242
    }
  




  }
  .matrix-r .v-responsive__content{
    width: 100%;
  //   overflow: auto;
    
  //   // position: absolute;
  //   // top: 0;
  //   // left: 0;
  //   // width: 100%;
  //   // bottom: 0;
  }
  .matrix-single{
    text-align: center;
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 1px;
    padding: 1px;
    min-width: 45px;
    p{
     margin: 0;
    }
  }

</style>
