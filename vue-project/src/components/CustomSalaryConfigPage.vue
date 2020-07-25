<template>
  <el-container>
    <el-header style="text-align: left; font-size: 12px">
      <span>自定义工资配置列表</span>
      <div style="float: right; cursor: pointer;">
        <el-tag @click="handleAddClick" style="">新增自定义工资</el-tag>
      </div>
    </el-header>

    <el-main>
      <el-table :data="tableData">
        <el-table-column prop="customSalary" label="工资" width="200">
        </el-table-column>
        <el-table-column prop="name" label="说明" width="200">
        </el-table-column>
        <el-table-column prop="userName" label="用户名" width="200">
        </el-table-column>
        <el-table-column prop="deptName" label="部门名称" width="200">
        </el-table-column>
        <el-table-column prop="createTime" label="工资创建日期" width="300">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作">
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

    <el-dialog title="新增自定义工资" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="基本工资" :label-width="formLabelWidth">
          <el-input v-model="form.salary" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="说明" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户" :label-width="formLabelWidth">
          <el-select v-model="form.userId" style="width: 100%" placeholder="请选择用户">
            <el-option
              v-for="item in userOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="部门" :label-width="formLabelWidth">
          <el-select v-model="form.deptId" style="width: 100%" placeholder="请选择部门">
            <el-option
              v-for="item in deptOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
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
  import {findList, addConfig, deleteConfig} from "@/service/customSalaryConfig";
  import {findUserOption} from "@/service/user";
  import {findDeptOption} from "@/service/dept";
  import {Message} from 'element-ui';

  export default {
    name: "CustomSalaryConfigVue",
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
          name: '',
          userId: '',
          deptId: ''
        },
        userOptions: [],
        deptOptions: [],
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
        this.getUserOptions();
        this.getDeptOptions();
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
      getUserOptions() {
        return new Promise(resolve => {
          findUserOption().then((data) => {
            this.userOptions = data.data;
          });
        });

      },
      getDeptOptions() {
        return new Promise(resolve => {
          findDeptOption().then((data) => {
            this.deptOptions = data.data;
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
