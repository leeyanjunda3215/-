import Vue from 'vue'
import Router from 'vue-router'
import login from '@/pages/login'
import register from '@/pages/register'
import index from '@/pages/index'
import play from '@/pages/play'
import home from '@/pages/home'
import info from '@/pages/info'
import search from '@/pages/search'
import upload from '@/pages/upload'
import chat from '@/pages/chat'
import last from '@/pages/last'
import classify from '@/pages/classify'
import collection from '@/pages/collection'
import changeInfo from '@/pages/changeInfo'

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
      component: index,
      children: [
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
        },
        {
          path: '/upload',
          name: 'upload',
          component: upload
        },
        {
          path: '/chat',
          name: 'chat',
          component: chat
        },
        {
          path: '/last',
          name: 'last',
          component: last
        },
        {
          path: '/collection',
          name: 'collection',
          component: collection
        },
        {
          path: '/classify',
          name: 'classify',
          component: classify
        },
        {
          path: '/changeInfo',
          name: 'changeInfo',
          component: changeInfo
        }
      ]
    }
  ]
})
