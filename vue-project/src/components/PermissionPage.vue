<template>
  <el-container>
    <el-header style="text-align: left; font-size: 12px">
      <span>权限列表</span>
    </el-header>

    <el-main>
      <el-table :data="tableData">
        <el-table-column prop="url" label="权限url" width="510">
        </el-table-column>
        <el-table-column prop="name" label="权限名称">
        </el-table-column>
      </el-table>
    </el-main>
    <el-pagination
      background
      layout="prev, pager, next, jumper"
      @current-change="handleCurrentChange"
      :current-page="listQuery.page"
      :total="total">
    </el-pagination>

  </el-container>
</template>
<script>
  import {findList} from "@/service/permission";
  import {Message} from 'element-ui';

  export default {
    name: "PermissionVue",
    data() {
      return {
        listQuery: {
          pageSize: 10,
          page: 1
        },
        tableData: [],
        total: 0
      };
    },
    beforeCreate() {
    },
    created() {
      console.log("test");
      this.init();
    },
    mounted() {

    },
    activated() {

    },
    methods: {
      init() {
        this.getList();
      },
      getList() {
        let obj = JSON.parse(JSON.stringify(this.listQuery));
        return new Promise(resolve => {
          findList(obj).then((data) => {
            this.tableData = data.data;
            this.total = data.total;
          });
        });

      },
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getList();
      }
    }
  }
</script>
<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }

  .zZindex {
    z-index:3000 !important;
  }
</style>
