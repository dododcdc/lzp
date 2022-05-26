<template>
  <div id="main" >

  </div>
</template>

<script>

import * as echarts from 'echarts';
import 'echarts-wordcloud';
import {regNums} from "@/api/analysis";
export default {
  name: "RegNums",

  data () {
    return {
      a:"good",

    }
  },
  // 在模板渲染成html后调用
  mounted() {
    this.initChart()
  },
  // 在模板渲染成html前调用
  created() {


  },
  methods: {
    initChart() {
      var chart = echarts.init(document.getElementById('main'))
      regNums().then(res => {

        var option = {
          title: {
            text: '评论区人员分布统计',
            // subtext: '基于45709条评论',
            // subtextStyle:{color:'#0000FF'},
            left: 'center',
            textStyle: {
              color:'#ff2052'
            }
          },
          tooltip: {},
          series: [ {
            type: 'wordCloud',
            gridSize: 10,
            sizeRange: [12, 50],
            rotationRange: [-90, 90],
            shape: 'pentagon',
            width: 1000,
            height: 800,
            drawOutOfBound: true,
            textStyle: {
              color: function () {
                return 'rgb(' + [
                  Math.round(Math.random() * 140),
                  Math.round(Math.random() * 140),
                  Math.round(Math.random() * 140)
                ].join(',') + ')';
              }
            },
            emphasis: {
              textStyle: {
                shadowBlur: 10,
                shadowColor: '#333'
              }
            },
            data:res.data
          } ]
        };
        chart.setOption(option);
      })

      // 窗口大小发生变化时(window.onresize) 图表尺寸适应变化(chart.resize)
      window.onresize = chart.resize;

    }

  }
}
</script>

<style scoped>
html, body, #main {
  width: 900px;
  height: 500px;
  margin: 0;

}
</style>