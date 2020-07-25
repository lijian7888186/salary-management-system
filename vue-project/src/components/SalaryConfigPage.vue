<template>
  <el-container>
    <el-header style="text-align: left; font-size: 12px">
      <span>基本工资配置列表</span>
      <div style="float: right; cursor: pointer;">
        <el-tag @click="handleAddClick" style="">新增基本工资</el-tag>
      </div>
    </el-header>

    <el-main>
      <el-table :data="tableData">
        <el-table-column prop="baseSalary" label="工资" width="200">
        </el-table-column>
        <el-table-column prop="name" label="说明" width="200">
        </el-table-column>
        <el-table-column prop="createTime" label="工资创建日期">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="200">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row, 1)" type="text" size="small">删除</el-button>
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

    <el-dialog title="新增基本工资" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="基本工资" :label-width="formLabelWidth">
          <el-input v-model="form.salary" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="说明" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitAddRole">确 定</el-button>
      </div>
    </el-dialog>

  </el-container>
</template>
<script>
  import {findList, addConfig, deleteConfig} from "@/service/salaryConfig";
  import {Message} from 'element-ui';

  export default {
    name: "SalaryConfigVue",
    data() {
      return {
        listQuery: {
          pageSize: 10,
          page: 1
        },
        tableData: [],
        total: 0,
        dialogFormVisible: false,
        form: {
          salary: '',
          name: ''
        },
        formLabelWidth: '120px',
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
      },
      handleClick(val, type) {
        console.log(val, type);
        if (type == 1) {
          deleteConfig(val).then((data) => {
            if (data.code == 200) {
              Message({
                message: '删除成功',
                type: 'success',
                duration: 1000
              });
              this.handleCurrentChange(1);
          }});
        }
      },
      handleAddClick() {
        console.log("新增部门");
        this.dialogFormVisible = true;
      },
      submitAddRole() {
        addConfig(this.form).then((data) => {
          if (data.code == 200) {
            Message({
              message: data.msg,
              type: 'success',
              duration: 1000,
              customClass:'zZindex'
            });
            this.dialogFormVisible = false;
            this.handleCurrentChange(1);
          }
        });
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
