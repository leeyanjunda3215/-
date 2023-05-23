<template>
  <div class="asideContianer">

    <el-menu default-active="1" class="el-menu-vertical-demo" background-color="rgb(50,65,87)" text-color="#fff"
      active-text-color="#ffd04b" :collapse="isCollapse">
      <h3>{{ isCollapse ? "影视" : "影视管理系统" }}</h3>
      <!-- 没有二级菜单 -->
      <el-menu-item :index="item.name" v-for="item in noChidren" :key="item.name" @click="clickMenu(item)">
        <i :class="`el-icon-${item.icon}`"></i>
        <span slot="title">{{ item.label }}</span>
      </el-menu-item>
      <!-- 有二级菜单 -->
      <el-submenu v-for=" item in hasChildren" :key="item.name" :index="item.name">
        <template slot="title">
          <i :class="`el-icon-${item.icon}`"></i>
          <span slot="title">{{ item.label }}</span>
        </template>

        <el-menu-item-group v-for="subitem in item.children" :key="subitem.name">
          <el-menu-item :index="subitem.name" @click="clickMenu(subitem)">{{ subitem.label }}</el-menu-item>
        </el-menu-item-group>

      </el-submenu>

    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside",
  data() {
    return {
      // isCollapse:false
      menuData: [
        // {
        //   path: '/start',
        //   label: '首页',
        //   name: 'coustmer',
        //   url: 'Home/start',
        //   icon: 'house'
        // },
        {
          label: "用户管理",
          name: 'Cusomer',
          icon: 'user',
          children: [
            {
              path: '/UserManger',
              label: '用户列表',
              name: 'UserManger',
              url: 'Cusomer/UserManger'
            }
          ]
        },
        {
          label: '视频管理',
          name: 'AdminManger',
          icon: 'folder',
          children: [
            {
              path: '/VideoManger',
              label: '视频列表',
              name: 'AdminManger',
              icon: 'folder',
              url: 'Cusomer/VideoManger'
            }
          ]
        }
      ]
    };
  },
  methods: {
    clickMenu(item) {
      if (this.$router.path != item.path && !(this.$router.path == '/start' && item.path === '/')) {
        this.$router.push(item.path)
      }
      //触发面包屑数据的更新
      this.$store.commit("menuChange", item)
    }
  },
  computed: {
    isCollapse() {
      return this.$store.state.tab.isCollapse
    },
    hasChildren() {
      return this.menuData.filter(item => item.children)
    },
    noChidren() {
      return this.menuData.filter(item => !item.children)
    }
  }
}

</script>

<style lang="less" scoped>
.el-menu {
  /*铺满整个屏幕  */
  height: 100vh;
  border-right: 0;

  h3 {
    color: aliceblue; //字体颜色
    text-align: center; //字体居中
    line-height: 48px; //行号
    font-size: 22px; //字体大小
    font-weight: 400;
    padding: 0 20px;
  }

}
</style>