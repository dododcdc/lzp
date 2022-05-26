<template>
<div >

  <el-row>
    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>
    <el-col :span="12"><div class="grid-content bg-purple" />
      <h1>
        李壮平微博评论区分析研究中心
      </h1>



    </el-col>

    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>
  <br /><br /><br /><br /><br />
  </el-row>

  <el-row>
    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>
    <el-col :span="12"><div class="grid-content bg-purple" />
      <RegNums />
    </el-col>

    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>

  </el-row>


  <el-row>
    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>
    <el-col :span="12"><div class="grid-content bg-purple" />
      <TfNums />
    </el-col>

    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>

  </el-row>

  <el-row>
    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>
    <el-col :span="12"><div class="grid-content bg-purple" />
      <FmProportion />
    </el-col>

    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>

  </el-row>

  <el-row>
    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>
    <el-col :span="12"><div class="grid-content bg-purple" />
      <Together />
    </el-col>

    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>

  </el-row>

  <div style="height: 50px">



  </div>
  <el-row>
    <el-col :span="8"></el-col>
    <el-col :span="8">
      <p style="color: aqua">
        本次采集评论的时间范围为{{this.min}}至{{this.max}}
      </p>
      <p>
        <b style="color: antiquewhite">TODO</b>
      </p>
      <ul style="color: lightseagreen;text-align: left">
        <li>地区人员活跃情况</li>
        <li>发送相同内容的两人，且正好是异性</li>
        <li>哪个时间段评论最多</li>
        <li>哪个地区铁粉最多</li>
        <li>00:00 后评论数量占比</li>
        <li>00:00后发布评论次数统计前十名</li>
      </ul>

    </el-col>
    <el-col :span="8"></el-col>
  </el-row>




</div>
</template>

<script>

import RegNums from "@/components/RegNums";
import TfNums from "@/components/TfNums";
import FmProportion from "@/components/FmProportion";
import Together from "@/components/Together";

import {getPeriod} from "@/api/analysis";

export default {
  name: "Analysis",
  components: {
    RegNums,TfNums,FmProportion,Together
  },
  data() {
    return {
      max:undefined,
      min:undefined

    }
  },
  created() {

    getPeriod().then(res => {
      this.min = res.data[0]
      this.max = res.data[1]
    })

  },mounted() {
  },
  methods: {

  }
}
</script>

<style >
body{
  /*background: url("D:\\project\\mine\\lzp\\lzpview\\src\\assets\\bg.png") ;*/
  /*background: url("../assets/bg2.jpg") ;*/
  background: url("../assets/bg.png") ;
  background-position: center ;
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: cover;
  background-color: #CCCCCC;

}

</style>