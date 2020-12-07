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
        <el-table-column
          fixed="right"
          label="操作">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row, 1)" type="text" size="small">查看详情</el-button>
          </template>
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


    <el-dialog title="用户工资详情" :visible.sync="dialogFormVisible">
      <el-table :data="infoForm">
        <el-table-column prop="typeStr" label="类型" width="200">
        </el-table-column>
        <el-table-column prop="configName" label="项目名称" width="200">
        </el-table-column>
        <el-table-column prop="customSalary" label="金额" width="200">
        </el-table-column>
        <el-table-column prop="remark" label="说明" width="200">
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">关闭</el-button>
      </div>
    </el-dialog>

  </el-container>
</template>
<script>
  import {findList, findDtList, findUserSalaryInfo} from "@/service/salaryManager";
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
        total: 0,
        dialogFormVisible: false,
        infoForm: [],
        userId:''
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
      },
      handleClick(val, type) {
        console.log(val, type);
        if (type == 1) {
          this.userId = val.userId
          this.infoForm = [];
          this.findUserList(val);
          this.dialogFormVisible = true;
        }
      },
      findUserList(val) {
        findUserSalaryInfo(val).then((data) => {
          if (data.code == 200) {
            this.infoForm = data.data;
          }
        });
      },
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
