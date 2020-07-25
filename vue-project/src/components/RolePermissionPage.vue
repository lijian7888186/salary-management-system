<template>
  <el-container>
    <el-header style="text-align: left; font-size: 12px">
      <span>角色权限列表</span>
      <div style="float: right; cursor: pointer;">
        <el-tag @click="handleAddClick" style="">新增角色权限</el-tag>
      </div>
    </el-header>

    <el-main>
      <el-table :data="tableData">
        <el-table-column prop="roleName" label="角色名称" width="530">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row, 1)" type="text" size="small">查看权限</el-button>
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

    <el-dialog title="新增角色权限" :visible.sync="dialogFormVisible">
      <el-form :model="form">
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
        <el-form-item label="权限" :label-width="formLabelWidth">
          <el-select v-model="form.permissionUrl" style="width: 100%" placeholder="请选择权限">
            <el-option
              v-for="item in permissionOptions"
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

    <el-dialog title="角色权限列表" :visible.sync="dialogTableVisible">
      <el-table :data="rolePermissionData">
        <el-table-column property="permissionUrl" label="权限url" width="150"></el-table-column>
        <el-table-column property="permissionName" label="权限名称" width="300"></el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="70">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row, 2)" type="text" size="small">删除权限</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>


  </el-container>
</template>
<script>
  import {findList, findRolePermissions, addRolePermission, deleteRolePermission} from "@/service/rolePermission";
  import {findRoleOption} from "@/service/role";
  import {findPermissionOption} from "@/service/permission";
  import {Message} from 'element-ui';

  export default {
    name: "RolePermissionVue",
    data() {
      return {
        listQuery: {
          pageSize: 10,
          page: 1
        },
        tableData: [],
        rolePermissionData: [],
        total: 0,
        dialogFormVisible: false,
        dialogTableVisible: false,
        form: {
          roleId: '',
          permissionUrl: ''
        },
        formLabelWidth: '120px',
        roleOptions: [],
        permissionOptions: [],
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
        // this.getRoleOptions();
        // this.getUserOptions();
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
          findPermissionOption().then((data) => {
            this.permissionOptions = data.data;
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
          findRolePermissions(val).then((data) => {
            if (data.code == 200) {
              this.rolePermissionData = data.data;
              this.dialogTableVisible=true;
          }});
        } else if (type == 2) {
          deleteRolePermission(val).then((data) => {
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
        console.log("新增角色权限");
        this.dialogFormVisible = true;
        this.getRoleOptions();
        this.getUserOptions();
      },
      submitAddUserRole() {
        addRolePermission(this.form).then((data) => {
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
