<template>
  <el-container>
    <el-header style="text-align: left; font-size: 12px">
      <span>用户列表</span>
      <div style="float: right; cursor: pointer;">
        <el-tag @click="handleAddClick" style="">新增用户</el-tag>
      </div>
    </el-header>

    <el-main>
      <el-table :data="tableData">
        <el-table-column prop="userName" label="用户名" width="200">
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="150">
        </el-table-column>
        <el-table-column prop="createTime" label="入职日期">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="200">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row, 1)" type="text" size="small">删除</el-button>
            <el-button @click="handleClick(scope.row, 2)" type="text" size="small">查看/编辑</el-button>
            <el-button @click="handleClick(scope.row, 3)" type="text" size="small">修改密码</el-button>
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

    <el-dialog title="新增用户" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="用户名称" :label-width="formLabelWidth">
          <el-input v-model="form.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="所属部门" :label-width="formLabelWidth">
          <el-select v-model="form.deptId" style="width: 100%" placeholder="请选择所属部门">
            <el-option
              v-for="item in addUserOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职位" :label-width="formLabelWidth">
          <el-select v-model="form.level" style="width: 100%" placeholder="请选择职位">
            <el-option
              v-for="item in levelOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitAddUser">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改/编辑用户" :visible.sync="dialogEditVisible">
      <el-form :model="editForm">
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="editForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="所属部门" :label-width="formLabelWidth">
          <el-select v-model="editForm.deptId" style="width: 100%" placeholder="请选择所属部门">
            <el-option
              v-for="item in addUserOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职位" :label-width="formLabelWidth">
          <el-select v-model="editForm.level" style="width: 100%" placeholder="请选择职位">
            <el-option
              v-for="item in levelOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitUpdateUser">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改密码" :visible.sync="dialogPasswordVisible">
      <el-form :model="passwordForm">
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input type="password" v-model="passwordForm.password" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogPasswordVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitPassword">确 定</el-button>
      </div>
    </el-dialog>

  </el-container>
</template>
<script>
  import {findList, findUserLevelList, findUserById, addUser, deleteUser, updateUser, updatePassword} from "@/service/user";
  import {findDeptOption} from "@/service/dept";
  import {Message} from 'element-ui';

  export default {
    name: "UserVue",
    data() {
      return {
        listQuery: {
          pageSize: 10,
          page: 1
        },
        tableData: [],
        total: 0,
        dialogFormVisible: false,
        dialogEditVisible: false,
        dialogPasswordVisible: false,
        form: {
          userName: '',
          phone: '',
          deptId: '',
          level: ''
        },
        editForm: {
          id:'',
          userName: '',
          phone: '',
          deptId: '',
          level: ''
        },
        passwordForm: {
          id:'',
          password:''
        },
        formLabelWidth: '120px',
        addUserOptions: [],
        levelOptions: [],
        editUserOptions: [],
        editLevelOptions: [],
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
        this.getDeptOptions();
        this.getLevelOptions();
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
      getDeptOptions() {
        return new Promise(resolve => {
          findDeptOption().then((data) => {
            this.addUserOptions = data.data;
          });
        });

      },
      getLevelOptions() {
        return new Promise(resolve => {
          findUserLevelList().then((data) => {
            this.levelOptions = data.data;
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
          deleteUser(val).then((data) => {
            if (data.code == 200) {
              Message({
                message: '删除成功',
                type: 'success',
                duration: 1000
              });
              this.handleCurrentChange(1);
          }});
        } else if (type == 2) {
          this.editForm.id=val.id;
          findUserById(val).then((data) => {
            this.editForm = data.data;
            this.editForm.deptId=data.data.deptId;
            this.editForm.level=data.data.level;
          });
          this.handleEditClick();

        } else if (type == 3) {
          this.passwordForm.id=val.id;
          this.handlePasswordClick();
        }
      },
      handleAddClick() {
        console.log("新增用户");
        this.dialogFormVisible = true;
      },
      handleEditClick() {
        console.log("修改用户");
        this.dialogEditVisible = true;
      },
      handlePasswordClick() {
        console.log("修改密码");
        this.dialogPasswordVisible = true;
      },
      submitAddUser() {
        addUser(this.form).then((data) => {
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
      submitUpdateUser() {
        updateUser(this.editForm).then((data) => {
          if (data.code == 200) {
            Message({
              message: data.msg,
              type: 'success',
              duration: 1000,
              customClass:'zZindex'
            });
            this.dialogEditVisible = false;
            this.handleCurrentChange(1);
          }
        });
      },
      submitPassword() {
        updatePassword(this.passwordForm).then((data) => {
          if (data.code == 200) {
            Message({
              message: data.msg,
              type: 'success',
              duration: 1000,
              customClass:'zZindex'
            });
            this.dialogPasswordVisible = false;
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
