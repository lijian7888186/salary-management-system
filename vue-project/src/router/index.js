import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Index from '@/components/LeftPage'
import Right from '@/components/RightPage'
import User from '@/components/UserPage'
import Dept from '@/components/DeptPage'
import Role from '@/components/RolePage'
import UserRole from '@/components/UserRolePage'
import Permission from '@/components/PermissionPage'
import RolePermission from '@/components/RolePermissionPage'
import SalaryManager from '@/components/SalaryManagerPage'
import SalaryChart from '@/components/SalaryChartPage'
import SalaryConfig from '@/components/SalaryConfigPage'
import CustomSalaryConfig from '@/components/CustomSalaryConfigPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      redirect: '/choosePage',
      component: Index
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/index',
      name: 'LeftPage',
      redirect: '/choosePage',
      component: Index,
      children: [
        {
          path: '/choosePage',
          component: Right

        },
        {
          path: '/user',
          component: User
        },
        {
          path: '/dept',
          component: Dept
        },
        {
          path: '/role',
          component: Role
        },
        {
          path: '/userRole',
          component: UserRole
        },
        {
          path: '/permission',
          component: Permission
        },
        {
          path: '/rolePermission',
          component: RolePermission
        },
        {
          path: '/salaryManager',
          component: SalaryManager
        },
        {
          path: '/salaryChart',
          component: SalaryChart
        },
        {
          path: '/salaryConfig',
          component: SalaryConfig
        },
        {
          path: '/customSalaryConfig',
          component: CustomSalaryConfig
        }
        ]
    }
  ]
})
