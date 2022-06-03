<template>
  <div id="fm">

  </div>

</template>

<script>
import {fmProportion} from "@/api/analysis";
import * as echarts from "echarts";
export default {
  name: "FmProportion",
  data () {
    return{

    }
  },
  mounted() {
    this.initChart()
  },
  methods: {

    initChart() {
      var chart = echarts.init(document.getElementById('fm'))
      fmProportion().then(res => {
        var option = {
          // backgroundColor: '#2c343c',
          // color:['#45C2E0', '#C1EBDD', '#FFC851','#5A5476','#1869A0','#FF9393'],
          color:['#FFC851'
            ,'#5A5476'],
          title: {
            text: '男女比例统计',
            // subtext: '基于45709条评论',
            left: 'center',
            textStyle: {
              color:'#ff2052'
            }
          },
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
              name: '男女',
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
#fm{
  width: 900px;
  height: 500px;

}
</style>