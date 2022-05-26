<template>
<div id="together">
  <el-row>
    <el-col>
      <el-tag class="ml-2" type="danger"><h2>你和我统计</h2></el-tag>
    </el-col>
    <el-col><div style="color: lightseagreen">一个地区恰好只有两个人，且为异性</div></el-col>
  </el-row>
  <div style="height: 20px"></div>
  <el-row>
    <el-col>
      <el-table :data="list" style="width: 100%">
        <el-table-column prop="area" label="区域" width="180" />
        <el-table-column label="-">
          <template #default="scope" >
            <!--        <a :href="scope.row.p2">{{scope.row.p1}}</a>-->
            <el-row>
              <el-col :span="12">
                {{scope.row.p1}}
              </el-col>
              <el-col :span="12" >
                <el-button type="primary" @click="go(scope.row.p2)">微博地址</el-button>
              </el-col>
            </el-row>
            <!--        <p v-for="(item,index) in scope.row" :key="index" class="scopeItem">{{item}}</p>-->
          </template>
        </el-table-column>
        <el-table-column label="-">
          <template #default="scope" >
            <el-row>
              <el-col :span="12">
                {{scope.row.p3}}
              </el-col>
              <el-col :span="12">
                <el-button type="primary" @click="go(scope.row.p4)">微博地址</el-button>
              </el-col>
            </el-row>
            <!--        <p v-for="(item,index) in scope.row" :key="index" class="scopeItem">{{item}}</p>-->
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>


</div>
</template>

<script>
import {together} from "@/api/analysis";
import * as echarts from "echarts";
export default {
  name: "Together",
  data() {
    return{
      list:[{
        date: '2016-05-03',
        name: 'Tom',
        address: 'https://www.baidu.com',
      },
        {
          date: '2016-05-02',
          name: 'Tom',
          address: 'https://www.baidu.com',
        },
        {
          date: '2016-05-04',
          name: 'Tom',
          address: 'https://www.baidu.com',
        },
        {
          date: '2016-05-01',
          name: 'Tom',
          address: 'https://www.baidu.com',
        }]
    }
  },
  mounted() {
  },
  created() {
    this.init()
  },
  methods:{

    init() {
      together().then(res => {

        var sd = res.data
        console.log(sd)
        var list = []

        for (var i in sd) {
          console.log(sd[i])

          const area = sd[i].name
          const p1 = sd[i].value.split("||")[0].split("332067")[0]
          const p2 = sd[i].value.split("||")[0].split("332067")[1]
          const p3 = sd[i].value.split("||")[1].split("332067")[0]
          const p4 = sd[i].value.split("||")[1].split("332067")[1]
          console.log(p1,p2)
          console.log(p3,p4)

          list.push({
            "area":area,
            "p1":p1,
            "p2":p2,
            "p3":p3,
            "p4":p4
          })

        }

        console.log(list)
        this.list = list


      })

    },

    go(url) {
      window.location.href= url;
    }
  }
}
</script>

<style scoped>

</style>