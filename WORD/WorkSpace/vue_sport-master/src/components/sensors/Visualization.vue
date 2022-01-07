<template>
  <div class="body">
    <header>
      
        <h1 >数据展示</h1>
        <div class="showTime">当前时间：2022年1月1日-0时0分0秒</div>
    </header>
    <div class="mainbox">
        <div class="panel" >
          <div id="chart" style="height: 500px;width: 500px;padding-top:20px ;"></div>
        </div>
          <div class="panel1" >
            <div id="chart1" style="height: 500px;width: 500px;padding-top:20px ;"></div>
        </div>
    </div>
  </div>
</template>

<script>
        var t = null;
        t = setTimeout(time, 1000); //開始运行
        function time() {
          clearTimeout(t); //清除定时器
          var dt = new Date();
          var y = dt.getFullYear();
          var mt = dt.getMonth() + 1;
          var day = dt.getDate();
          var h = dt.getHours(); //获取时
          var m = dt.getMinutes(); //获取分
          var s = dt.getSeconds(); //获取秒
          document.querySelector(".showTime").innerHTML =
            "当前时间：" +
            y +
            "年" +
            mt +
            "月" +
            day +
            "-" +
            h +
            "时" +
            m +
            "分" +
            s +
            "秒";
          t = setTimeout(time, 1000); //设定定时器，循环运行
        }
const axios = require('axios');
export default {
  data(){
    return{
        list: [
                {},
            ]
      } 
  },
  mounted() {
    console.log(document.getElementById('chart'));
    this.myEcharts();  //调用组件的时候自动调用这个方法
    console.log(document.getElementById('chart1'));
    this.myEcharts1();
  },
  methods: {
    
    myEcharts1(){
      var sum = 0;
      let echarts = require('echarts');
        var myChart1 = echarts.init(document.getElementById('chart1'));
              function randomData() {
                var now = new Date();
                value = value + Math.random() * 21 - 10;
                return {
                    value: [
                        [now.getHours(), now.getMinutes(), now.getSeconds()].join(':'),
                        sum
                    ]
                }
            }

            var data = [];
            var date = [];
            var value = Math.random() ;
            for (var i = 0; i < 7; i++) {
                data.push(randomData());
                date.push('')
            }

            var option1 = {
              
                title: {
                    text: '温度折线变化',
                    x: 'center',
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: function (params) {
                        params = params[0];
                        return params.value[0] + ' / ' + params.value[1];
                    },
                    axisPointer: {
                        animation: true
                    }
                },
                xAxis: {
                    type: 'category',
                    splitLine: {
                        show: false
                    },
                    data: date
                },
                yAxis: {
                    type: 'value',
                    boundaryGap: [0, '100%'],
                    splitLine: {
                        show: false
                    }
                },
                series: [{
                    name: '模拟数据',
                    type: 'line',
                    showSymbol: true,
                    data: sum,
                    label:{
                    show:true,
                    symbolSize:0.2,

                    formatter:function(data){
                        return data.value[1]+' ℃';
                    }
                }
                }]
            };

            setInterval(function () {
            axios.get('up').then(function (resp) {
            sum = resp.data["temp"];
          })
                var _data = randomData();
                data.shift();
                date.shift();
                data.push(_data);
                date.push(_data.value[0])

                myChart1.setOption({
                    xAxis: {
                      data: date  
                    },
                    series: [{
                        data: data
                    }]
                });
            }, 1000);
              option1 && myChart1.setOption(option1);
            },
    myEcharts() {
        let echarts = require('echarts');
        var myChart = echarts.init(document.getElementById('chart'));
        var option = {
          title: {
                    text: '温度传感器',
                    x: 'center',
                },
            series: [
              {
                type: 'gauge',
                center: ['50%', '60%'],
                startAngle: 200,
                endAngle: -20,
                min: -80,
                max: 80,
                splitNumber: 8,
                itemStyle: {
                  color: '#FFAB91'
                },
                progress: {
                  show: true,
                  width: 30
                },
                pointer: {
                  show: false
                },
                axisLine: {
                  lineStyle: {
                    width: 30
                  }
                },
                axisTick: {
                  distance: -45,
                  splitNumber: 8,
                  lineStyle: {
                    width: 2,
                    color: '#999'
                  }
                },
                splitLine: {
                  distance: -52,
                  length: 14,
                  lineStyle: {
                    width: 3,
                    color: '#999'
                  }
                },
                axisLabel: {
                  distance: -50,
                  color: '#999',
                  fontSize: 20
                },
                anchor: {
                  show: false
                },
                title: {
                  show: false
                },
                detail: {
                  valueAnimation: true,
                  width: '60%',
                  lineHeight: 40,
                  borderRadius: 8,
                  offsetCenter: [0, '-15%'],
                  fontSize: 60,
                  fontWeight: 'bolder',
                  formatter: '{value} °C',
                  color: 'auto'
                },
                data: [
                  {
                    value: -40
                  }
                ]
              },
              {
                type: 'gauge',
                center: ['50%', '60%'],
                startAngle: 200,
                endAngle: -20,
                min: -80,
                max: 80,
                itemStyle: {
                  color: '#FD7347'
                },
                progress: {
                  show: true,
                  width: 8
                },
                pointer: {
                  show: true
                },
                axisLine: {
                  show: false
                },
                axisTick: {
                  show: false
                },
                splitLine: {
                  show: false
                },
                axisLabel: {
                  show: false
                },
                detail: {
                  show: false
                },
                data: [
                  {
                    value: -40
                  }
                ]
              }
            ]
          };
           var sum = 0;
          setInterval(function () {
          axios.get('up').then(function (resp) {
            sum = resp.data["temp"];
          })
            const random = +(Math.random() * 60).toFixed(2);
            myChart.setOption({
              series: [
                {
                  data: [
                    {
                      value: sum
                    }
                  ]
                },
                {
                  data: [
                    {
                      value: sum
                    }
                  ]
                }
              ]
            });
          }, 1000);
          option && myChart.setOption(option);

              },
            },
          };
       
</script>

<style lang="less" scoped>
.body{
    height:100%;
  }
 header {
    height: 120px;
    background: url(../../assets/images/head_bg.png) no-repeat top center;
    background-size: 100% 100%;
  }
  header h1 {
    padding-top: 30px;
    font-size: 30px;
    color: #fff;
    text-align: center;
    line-height: 20px;
  }
  .showTime {
    position: absolute;
    right: 40%;
    font-size: 15px;
    color: black;
  }
  
  .panel h2 {
    text-align: center;
    color: black;
    font-size: 20px;
    font-weight: 400;
  }
  .panel {
    width: 500px;
    height:500px;
     //border: 1px solid rgba(25, 186, 139, 0.17);
     //background: rgba(255, 255, 255, 0.04) url(../../assets/images/line.png);
  }
  .panel1 {
    width: 500px;
    height:500px;
     border: 1px solid rgba(25, 186, 139, 0.17);
     background: rgba(255, 255, 255, 0.04) url(../../assets/images/line.png);
  }
  .mainbox{
    display: flex;
    flex-direction: row;
    justify-content:space-around;
  }
</style>