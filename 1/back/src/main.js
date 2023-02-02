// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import store from './store';
import axios from 'axios';


//将axios挂载到原型链上
Vue.prototype.$axios = axios

//配置
Vue.use(ElementUI)
Vue.config.productionTip = false

 // request拦截器，将用户token放入头中
 axios.interceptors.request.use(
  config => {
      if (sessionStorage.getItem("token")) config.headers['authorization'] = sessionStorage.getItem("token")
      return config
  }
)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store, //挂载store实例
  components: { App },
  template: '<App/>'
})
