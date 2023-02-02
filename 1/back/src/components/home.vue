<template>
    <div class="hello">
     
  <el-container>
      <el-aside width="auto">
       <Aside></Aside>
      </el-aside>
      <el-container>
      <el-header>
        <Header></Header>
      </el-header>
      <el-main>
         <!-- 路由的出口 -->
        <router-view></router-view>

      </el-main>
  </el-container>
</el-container>
    
    </div>
  </template>
  
  <script>
import { RouterLink } from 'vue-router';
import Aside from "./comment/aside.vue";
import Header from "./comment/header.vue";

  export default {
    name: "home",
    data() {
        return {
            msg: ""
        };
    },
    methods: {

    },
    components: { RouterLink,Aside,Header }
}
  </script>
  
  <!-- Add "scoped" attribute to limit CSS to this component only -->
  <style scoped>
  .el-header{
    padding: 0 0;
  }
  
  </style>
  