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
        <el-table-column prop="customSalaryName" label="配置名称" width="150">
        </el-table-column>
        <el-table-column prop="typeStr" label="类型" width="100">
        </el-table-column>
        <el-table-column prop="remark" label="说明" width="200">
        </el-table-column>
        <el-table-column prop="createTime" label="创建日期" width="200">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row, 1)" type="text" size="small">删除</el-button>
            <el-button @click="handleClick(scope.row, 2)" type="text" size="small">用户列表</el-button>
            <el-button @click="handleClick(scope.row, 3)" type="text" size="small">增加用户</el-button>
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

    <el-dialog title="新增自定义工资配置" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="配置名称" :label-width="formLabelWidth">
          <el-input v-model="form.customSalaryName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="说明" :label-width="formLabelWidth">
          <el-input v-model="form.remark" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型" :label-width="formLabelWidth">
          <el-select v-model="form.type" style="width: 100%" placeholder="请选择类型">
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

    <el-dialog title="新增用户自定义工资" :visible.sync="dialogUserFormVisible">
      <el-form :model="userForm">
        <el-form-item label="日期" :label-width="formLabelWidth">
          <el-input v-model="userForm.dt" autocomplete="off" placeholder="只能输入未来日期的每个月的1号"></el-input>
        </el-form-item>
        <el-form-item label="说明" :label-width="formLabelWidth">
          <el-input v-model="userForm.remark" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户" :label-width="formLabelWidth">
          <el-select v-model="userForm.userId" style="width: 100%" placeholder="请选择用户">
            <el-option
              v-for="item in userOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="自定义工资" :label-width="formLabelWidth">
          <el-input v-model="userForm.customSalary" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogUserFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitAddUser">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="用户自定义工资列表" :visible.sync="dialogUserListFormVisible">
      <el-table :data="userListFormData">
        <el-table-column prop="userName" label="用户名称" width="200">
        </el-table-column>
        <el-table-column prop="nickname" label="用户昵称" width="200">
        </el-table-column>
        <el-table-column prop="dt" label="日期" width="200">
        </el-table-column>
        <el-table-column prop="customSalary" label="自定义工资" width="200">
        </el-table-column>
        <el-table-column prop="remark" label="说明" width="200">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作">
          <template slot-scope="scope">
            <el-button @click="deleteUserClick(scope.row, 1)" type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogUserListFormVisible = false">关闭</el-button>
      </div>
    </el-dialog>

  </el-container>
</template>
<script>
  import {findList, addConfig, deleteConfig, addUserCustomSalary, deleteUserCustomSalary, findUserCustomSalaryList} from "@/service/customSalaryConfig";
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
        dialogUserFormVisible: false,
        dialogUserListFormVisible: false,
        form: {
          customSalaryName: '',
          remark: '',
          type: ''
        },
        userForm: {
          userId:"",
          customSalaryConfigId:"",
          dt:"",
          customSalary:"",
          customSalaryName: '',
          remark: '',
          type: ''
        },
        userListFormData: [],
        userOptions: [],
        deptOptions: [{
          value: '2',
          label: '补贴'
        }, {
          value: '3',
          label: '扣款'
        }],
        formLabelWidth: '120px',
        id: "",
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
        // this.getDeptOptions();
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
        } else if (type == 2) {
          this.id = val.id;
          this.findUserList();
          this.dialogUserListFormVisible = true;
        } else if (type == 3) {
          this.userForm.customSalaryConfigId = val.id;
          this.dialogUserFormVisible = true;
        }
      },
      deleteUserClick(val, type) {
        console.log(val, type);
        if (type == 1) {
          deleteUserCustomSalary(val).then((data) => {
            if (data.code == 200) {
              Message({
                message: '删除成功',
                type: 'success',
                duration: 1000
              });
              this.findUserList();
          }});
        }
      },
      handleAddClick() {
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
      },
      findUserList() {
        findUserCustomSalaryList({id:this.id}).then((data) => {
          if (data.code == 200) {
            this.userListFormData = data.data;
          }
        });
      },
      submitAddUser() {
        addUserCustomSalary(this.userForm).then((data) => {
          if (data.code == 200) {
            Message({
              message: data.msg,
              type: 'success',
              duration: 1000,
              customClass:'zZindex'
            });
            this.dialogUserFormVisible = false;
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
