<template>
  <el-container>
    <el-header style="text-align: left; font-size: 12px">
      <span>工资列表</span>
    </el-header>

    <el-main>
      <el-form :inline="true" :model="listQuery" class="demo-form-inline" style="margin-right: 200px;">
        <el-form-item label="用户名">
          <el-input v-model="listQuery.userName" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="日期">
          <el-select v-model="listQuery.dt" placeholder="日期">
            <el-option
              v-for="item in dtOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData">
        <el-table-column prop="userName" label="用户名称" width="200">
        </el-table-column>
        <el-table-column prop="deptName" label="部门名称" width="200">
        </el-table-column>
        <el-table-column prop="dt" label="日期">
        </el-table-column>
        <el-table-column prop="salary" label="工资">
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
  import {findList, findDtList} from "@/service/salaryManager";
  import {Message} from 'element-ui';

  export default {
    name: "SalaryManagerVue",
    data() {
      return {
        listQuery: {
          pageSize: 10,
          page: 1,
          userName:null,
          dt:null
        },
        tableData: [],
        dtOptions: [],
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
        this.getDtList();
      },
      getList() {
        if (!this.listQuery.dt) {
          this.listQuery.dt = null;
        }
        if (!this.listQuery.userName) {
          this.listQuery.userName = null;
        }
        let obj = JSON.parse(JSON.stringify(this.listQuery));
        return new Promise(resolve => {
          findList(obj).then((data) => {
            this.tableData = data.data;
            this.total = data.total;
          });
        });

      },
      getDtList() {
        return new Promise(resolve => {
          let param = {num:12};
          findDtList(param).then((data) => {
            this.dtOptions = data.data;
          });
        });
      },
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getList();
      },
      onSubmit() {
        this.listQuery.page = 1;
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
