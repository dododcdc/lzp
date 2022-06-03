<template>

  <div id="tf">

  </div>

</template>

<script>
import {tfNums} from "@/api/analysis";
import * as echarts from "echarts";
export default {
  name: "TfNums",
  data () {
    return {

    }
  },
  mounted() {
    this.initChart()
  },
  created() {
  },
  methods: {

    initChart() {
      var chart = echarts.init(document.getElementById('tf'))
      tfNums().then(res => {
        var option = {
          title: {
            text: '铁粉非铁粉评论数量统计',
            // subtext: '基于45709条评论',
            left: 'center',
            textStyle: {
              color:'#ff2052'
            }
          },
          // color:['#45C2E0', '#C1EBDD', '#FFC851','#5A5476','#1869A0','#FF9393'],
          color:['#1869A0','#FF9393'],
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left',
            textStyle: {
              color:'#111110'
            }
          },
          series: [
            {
              name: '评论数',
              type: 'pie',
              radius: '50%',
              data: res.data,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        };
        chart.setOption(option);

      })

      window.onresize = chart.resize;

    }

  }
}
</script>

<style scoped>
#tf{
  width: 900px;
  height: 500px;

}
</style>