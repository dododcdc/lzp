<template>

  <div>

    <el-row>
      <el-col>
        <h3>
          同言异性统计
        </h3>
        <p>
          在样本数据内有且只有两条相同评论且为不同性别的人发出
        </p>
      </el-col>
    </el-row>


    <el-row>
      <el-col>
        <el-table :data="page.data"
                  table-layout="fixed"
                  style="width: 100%">
          <el-table-column prop="name" label="评论"  >
            <template #default="scope">

              <el-row>
                <el-col>
                  <span v-html="scope.row.name"></span>
                </el-col>
              </el-row>

            </template>

          </el-table-column>
          <el-table-column prop="cmName1" label="人"  >
            <template #default="scope">
<!--              <img :src="scope.row.avatar1" width="40px" height="40px" />-->
              <el-avatar :size="100" :src="scope.row.avatar1" />
              <a :href="scope.row.url1">{{scope.row.cmName1}}</a>

            </template>

          </el-table-column>
          <el-table-column prop="cmName2" label="人" >
            <template #default="scope">
              <!--              <img :src="scope.row.avatar1" width="40px" height="40px" />-->
              <el-avatar :size="100" :src="scope.row.avatar2" />
              <a :href="scope.row.url2">{{scope.row.cmName2}}</a>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>

</template>

<script>
import {tgCm} from "@/api/analysis";
export default {
  name: "TgCm",
  data() {
    return {

      page:{
        data:[],
        currentPage:1,
        pageSize:5,
        totalRow:100,
        totalPages:20
      }

    }
  },
  mounted() {
    this.get(2)
  },
  methods:{

    get(currentPage) {
      tgCm(currentPage).then(res => {
        this.page.data = res.data.data
        console.log(this.page.data)
        this.page.currentPage = res.data.currentPage
        this.page.pageSize = res.data.pageSize
        this.page.totalRow = res.data.totalRow
        this.page.totalPages = res.data.totalPages
      })

    }

  }
}
</script>

<style scoped>

</style>