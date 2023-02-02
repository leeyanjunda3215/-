import Vue from 'vue'
import Router from 'vue-router'
import login from '@/pages/login'
import register from '@/pages/register'
import index from '@/pages/index'
import play from '@/pages/play'
import home from '@/pages/home'
import info from '@/pages/info'
import search from '@/pages/search'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/register',
      name: 'register',
      component: register
    },
 
    {
      path: '/',
      name: 'index',
      component:index,
      children:[
        {
          path: '/play',
          name: 'play',
          component: play
        },
        {
          path: '/',
          name: 'home',
          component: home
        },
        {
          path: '/info',
          name: 'info',
          component: info
        },
        {
          path: '/search',
          name: 'search',
          component: search
        }
      ]
    }
  ]
})
