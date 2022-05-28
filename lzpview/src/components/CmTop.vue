<template>
<div id="cm-tom">

</div>
</template>

<script>
import {cmTop} from "@/api/analysis";
import * as echarts from "echarts";
export default {
  name: "CmTop",
  data() {
    return {

    }
  },
  created() {
  },
  mounted() {
    this.initChart()
  },
  methods: {
    initChart() {

      var chart = echarts.init(document.getElementById('cm-tom'))

      cmTop().then(res => {

        var option = {
          title: {
            text: 'World Population'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {},
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
          },
          yAxis: {
            type: 'category',
            data: ['Brazil', 'Indonesia', 'USA', 'India', 'China', 'World']
          },
          series: [
            {

              type: 'bar',
              data: [18203, 23489, 29034, 104970, 131744, 630230]
            }

          ]
        };

        // for(var i in res.data) {
        //
        //   console.log(res.data[i].name,res.data[i].value)
        //   option.yAxis.data.push(res.data[i].name)
        //   option.series[0].data.push(res.data[i].value)
        //
        // }
        console.log(option)
        chart.setOption(option)

      })

      // 窗口大小发生变化时(window.onresize) 图表尺寸适应变化(chart.resize)
      window.onresize = chart.resize;

    }
  }
}
</script>

<style scoped>

#cm-tom{
  width: 900px;
  height: 500px;

}

</style>