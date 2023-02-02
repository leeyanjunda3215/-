import Vue from 'vue'
import Router from 'vue-router'


import home from '@/components/home'


import start from '@/components/pages/start'

import login from '@/components/comment/login'

import tv from '@/components/pages/tv'
import movie from '@/components/pages/movie'
import animation from '@/components/pages/animation'

import UserManger from '@/components/pages/UserManger'
import VideoManger from '@/components/pages/VideoManger'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/', //主路由
      name: 'home',
      component: home,
      children:[
         //嵌套子路由
         {
          path: '/start',
          name: 'start',
          component: start
        },
        {
          path: '/tv',
          name: 'tv',
          component: tv
        },
        {
          path: '/movie',
          name: 'movie',
          component: movie
        },
        {
          path: '/animation',
          name: 'animation',
          component: animation
        },
        {
          path: '/UserManger',
          name: 'UserManger',
          component: UserManger
        },
        {
          path: '/VideoManger',
          name: 'VideoManger',
          component: VideoManger
        },
      ]
    },
    {
      path: '/login', //主路由
      name: 'login',
      component: login,
    }
    
  ]
})
