<template>
  <el-container>
    <el-header style="text-align: left; font-size: 12px">
      <span>用户角色列表</span>
      <div style="float: right; cursor: pointer;">
        <el-tag @click="handleAddClick" style="">新增用户角色</el-tag>
      </div>
    </el-header>

    <el-main>
      <el-table :data="tableData">
        <el-table-column prop="userName" label="用户名" width="530">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row, 1)" type="text" size="small">查看角色</el-button>
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

    <el-dialog title="新增用户角色" :visible.sync="dialogFormVisible">
      <el-form :model="form">
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
        <el-form-item label="角色" :label-width="formLabelWidth">
          <el-select v-model="form.roleId" style="width: 100%" placeholder="请选择角色">
            <el-option
              v-for="item in roleOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitAddUserRole">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="用户角色列表" :visible.sync="dialogTableVisible">
      <el-table :data="userRoleData">
        <el-table-column property="roleName" label="角色名称" width="150"></el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="200">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row, 2)" type="text" size="small">删除角色</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>


  </el-container>
</template>
<script>
  import {findList, findUserRoles, addUserRole, deleteUserRole} from "@/service/userRole";
  import {findRoleOption} from "@/service/role";
  import {findUserOption} from "@/service/user";
  import {Message} from 'element-ui';

  export default {
    name: "UserRoleVue",
    data() {
      return {
        listQuery: {
          pageSize: 10,
          page: 1
        },
        tableData: [],
        userRoleData: [],
        total: 0,
        dialogFormVisible: false,
        dialogTableVisible: false,
        form: {
          userId: '',
          roleId: ''
        },
        formLabelWidth: '120px',
        roleOptions: [],
        userOptions: [],
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
        this.getRoleOptions();
        this.getUserOptions();
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
      getRoleOptions() {
        return new Promise(resolve => {
          findRoleOption().then((data) => {
            this.roleOptions = data.data;
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
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getList();
      },
      handleClick(val, type) {
        console.log(val, type);
        if (type == 1) {
          findUserRoles(val).then((data) => {
            if (data.code == 200) {
              this.userRoleData = data.data;
              this.dialogTableVisible=true;
          }});
        } else if (type == 2) {
          deleteUserRole(val).then((data) => {
            if (data.code == 200) {
              Message({
                message: data.msg,
                type: 'success',
                duration: 1000,
                customClass:'zZindex'
              });
              this.dialogTableVisible = false;
              this.handleCurrentChange(1);
            }
          });
        }
      },
      handleAddClick() {
        console.log("新增用户角色");
        this.dialogFormVisible = true;
      },
      submitAddUserRole() {
        addUserRole(this.form).then((data) => {
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
