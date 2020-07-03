import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import test from '@/components/test'
import test1 from '@/components/test1'
import test2 from '@/components/test2'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/test',
      name: 'test',
      redirect: '/test2',
      component: test,
      children: [
        {
          path: '/test1',
          component: test1

        },
        {
          path: '/test2',
          component: test2
        }
        ]
    }
  ]
})
