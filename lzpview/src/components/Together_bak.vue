<template>
<div id="main">

</div>
</template>

<script>
import {together} from "@/api/analysis";
import * as echarts from "echarts";

export default {
  name: "Together",
  data () {
    return {
      a:"good",
      option:{
        title: {
          text: 'Referer of a Website',
          subtext: 'Fake Data',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
    }
  },
  created() {
    together().then(res => {
      this.option.series[0].data = res.data
      console.log(this.option.series[0].data)

      // console.log(res.data)
      // this.seriesData = [
      //   { value: 1048, name: 'Search Engine' },
      //   { value: 735, name: 'Direct' },
      //   { value: 580, name: 'Email' },
      //   { value: 484, name: 'Union Ads' },
      //   { value: 300, name: 'Video Ads' }
      // ]
      this.initChart()
    })

  },
  methods: {
    initChart() {
      var chart = echarts.init(document.getElementById('main'));
      chart.setOption(this.option);
      // 窗口大小发生变化时(window.onresize) 图表尺寸适应变化(chart.resize)
      window.onresize = chart.resize;
    }

  }

}
</script>

<style scoped>
html, body, #main {
  width: 500px;
  height: 400px;
  margin: 0;
}
</style>