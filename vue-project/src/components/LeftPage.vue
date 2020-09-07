<template>

  <el-container>
    <el-header id="header" style="text-align: right; font-size: 12px;">
      <el-dropdown @command="toLogout">
        <i class="el-icon-setting" style="margin-right: 15px"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="1">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <span>{{userInfo}}</span>
    </el-header>

    <el-container style="height: 500px; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-openeds="['1', '2', '3', '4', '5']">
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-message"></i>用户管理</template>
            <el-menu-item index="1-1"><router-link to="/user">用户列表</router-link></el-menu-item>
            <el-menu-item index="1-2"><router-link to="/dept">部门列表</router-link></el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-menu"></i>角色管理</template>
            <el-menu-item index="2-1"><router-link to="/role">角色列表</router-link></el-menu-item>
            <el-menu-item index="2-2"><router-link to="/userRole">用户角色</router-link></el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title"><i class="el-icon-menu"></i>权限管理</template>
            <el-menu-item index="3-1"><router-link to="/permission">权限列表</router-link></el-menu-item>
            <el-menu-item index="3-2"><router-link to="/rolePermission">角色权限</router-link></el-menu-item>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title"><i class="el-icon-menu"></i>工资管理</template>
            <el-menu-item index="4-1"><router-link to="/salaryManager">工资列表</router-link></el-menu-item>
            <el-menu-item index="4-1"><router-link to="/salaryChart">工资图表</router-link></el-menu-item>
          </el-submenu>
          <el-submenu index="5">
            <template slot="title"><i class="el-icon-menu"></i>工资配置</template>
            <el-menu-item index="5-1"><router-link to="/salaryConfig">基本配置</router-link></el-menu-item>
            <el-menu-item index="5-2"><router-link to="/customSalaryConfig">自定义配置</router-link></el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <router-view></router-view>
      </el-container>
    </el-container>
    <el-footer class="footer"></el-footer>
  </el-container>



</template>

<script>
  import {getLoginUser, logout} from "@/service/login";
  import {Message} from 'element-ui';

  export default {
    name: "LeftVue",
    data() {
      return {
        userInfo:"test"
      }
    },
    beforeCreate() {

    },
    created() {
      getLoginUser().then((data) => {
        console.log(data);
        if (data.code == 200) {
          this.userInfo = data.data;
        } else {
          if (data.code == 401) {
              console.log("没有登录")
          } else {
            Message({
              message: data.msg || 'Error',
              type: 'error',
              duration: 1000
            })
          }
        }
      })
    },
    mounted() {

    },
    methods: {
      toLogout(command) {
        return new Promise(resolve => {
          logout().then((data) => {
            console.log(data);
            if (data.code == 200) {
              this.$router.replace({path: '/login'});
            } else {
              Message({
                message: data.msg || 'Error',
                type: 'error',
                duration: 1000
              })
            }
          })
        });
      }
    }
  }
</script>

<style>
  #header {
    background: white;
    text-align: left;
    background: url('../assets/header.jpg') no-repeat;
    -webkit-background-size: 100% 100%;
    background-size: 100% 100%;
  }
  .el-aside {
    color: #333;
  }
  a {
    text-decoration: none;
    color: #303133;
  }
  .router-link-active {
    text-decoration: none;
  }
  .footer {
    background: white;
    text-align: left;
    background: url('../assets/footer.jpg') no-repeat;
    -webkit-background-size: 100% 100%;
    background-size: 100% 100%;
  }
</style>
