<template>
<div>

<!--  <el-button type="primary" @click="start()" v-if="true">start</el-button>-->
  <el-button type="primary" @click="playMusic()" v-if="true">play</el-button>
    <audio  controls loop ref="audio-player" style="display: none">
      <source src="https://goodbin.cn/music/xx.mp3" />
    </audio>
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
  <div  style="height: 20px"></div>

<!--  地区人数统计-->
  <el-row>
    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>
    <el-col :span="12"><div class="grid-content bg-purple" />
      <RegNums />
    </el-col>

    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>

  </el-row>
  <div  style="height: 20px"></div>
  <!--  男女占比-->
  <el-row>
    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>
    <el-col :span="12"><div class="grid-content bg-purple" />
      <FmProportion />
    </el-col>

    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>

  </el-row>

  <div  style="height: 20px"></div>
<!--  评论数前十-->
  <el-row>
    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>
    <el-col :span="12"><div class="grid-content bg-purple" />
      <CmTop />
    </el-col>

    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>

  </el-row>
  <div  style="height: 20px"></div>
  <!--铁粉-->
  <el-row>
    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>
    <el-col :span="12"><div class="grid-content bg-purple" />
      <TfNums />
    </el-col>

    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>
  </el-row>
  <div  style="height: 20px"></div>
  <el-row>
    <el-col :span="6">

    </el-col>
    <el-col :span="12">
      <TgCm />
    </el-col>
    <el-col :span="6">

    </el-col>
  </el-row>
<!--  在一起统计-->
<!--  <el-row>-->
<!--    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>-->
<!--    <el-col :span="12"><div class="grid-content bg-purple" />-->
<!--      <Together />-->
<!--    </el-col>-->

<!--    <el-col :span="6"><div class="grid-content bg-purple" /></el-col>-->

<!--  </el-row>-->

  <div style="height: 40px">

  </div>
  <el-row>
    <el-col :span="8"></el-col>
    <el-col :span="8">
      <p style="color: black;text-align: left">
        本次采集评论的时间范围为{{this.min}}至{{this.max}}
      </p>
      <p style="text-align: left">
        <b style="color: deepskyblue">TODO</b>
      </p>
      <ul style="color: lightseagreen;text-align: left">
        <li>地区人员活跃情况</li>
        <li>同一地区只有两人且为异性</li>
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
import CmTop from "@/components/CmTop";

import TgCm from "@/components/TgCm";

import {getPeriod} from "@/api/analysis";
import { get,demo1 } from "@/api/demo";

export default {
  name: "Analysis",
  components: {
    RegNums,TfNums,FmProportion,Together,CmTop,TgCm
  },
  data() {
    return {
      max:undefined,
      min:undefined,
      box:undefined

    }
  },

  // 在模板渲染成html前调用
  created() {

    getPeriod().then(res => {
      this.min = res.data[0]
      this.max = res.data[1]
    })



  },

  mounted() {
    // this.playMusic()

  },
  methods: {

    start() {
      demo1().then(res => {
        console.log("ok")
      })
    },
    playMusic() {
      this.box = this.$refs["audio-player"];
      // this.box.pause()
      this.box.play();

    }

  }
}
</script>

<style >
body{
  /*background: url("D:\\project\\mine\\lzp\\lzpview\\src\\assets\\bg.png") ;*/
  /*background: url("../assets/bg2.jpg") ;*/
  /*background: url("../assets/bg.png") ;*/
  background-color: bisque;
  background-position: center ;
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: cover;


}

</style>