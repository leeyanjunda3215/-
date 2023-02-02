<template>
  <el-container>
    <el-header>
      <div class="header">


        <div class="main">
          <el-button  @click="Gotohome()"  circle>首页</el-button>
        </div>


        <!-- s搜索框 -->
        <div class="seach">
          <el-input placeholder="搜索" suffix-icon="el-icon-search" v-model.trim="input1" @keyup.enter.native="handleSelect">
          </el-input>
        </div>
        <!-- 用户头像 -->
        <div class="headimg">
          <el-dropdown v-if="user.length != 0">
            <span class="el-dropdown-link">
              <img class="userimg" :src="user.headimg" />
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="Gotoinfo()">个人信息</el-dropdown-item>
              <el-dropdown-item @click.native="Logout()">退出</el-dropdown-item>
            </el-dropdown-menu> 
          </el-dropdown>

          <span class="unlogin" @click="Gologin()" v-else>请登录</span>
        </div>
      </div>
    </el-header>

    <router-view />

  </el-container>

</template>


<script>

export default {
  name: 'index',
  data() {
    return {
      user: [],//取到的用户信息
      input1: "",
    }
  },
  created() {
    this.queryUser()
  },
  methods: {
    Gotoplay(i) {
      this.$store.state.tab.videopath = i;
      location.href = "http://localhost:8081/#/play"
    },
    queryUser() {
      this.$axios.get("http://localhost:8082/user/me").then(resp => {
        //保存用户
        this.user = resp.data.data
      })
    },
    Gologin() {
      location.href = 'http://localhost:8081/#/login';
    },
    Gotoinfo() {
      location.href = "http://localhost:8081/#/info";
    },
    Logout() {
      // 登出
      var token = sessionStorage.getItem("token")

      this.$axios.post("http://localhost:8082/user/logout", { token }).then(resp => {
        if (resp.data.success == true) {
          location.href = 'http://localhost:8081/';
        }
      })
    },
    Gotohome() {
      location.href = 'http://localhost:8081/';
    },
    handleSelect(){
        var input = this.input1
        this.$axios.post("http://localhost:8082/video/search",{ input }).then(resp => {
          if (resp.data.success == true) {
            // console.log(resp.data.data);
            this.$store.state.tab.search = resp.data.data;
            location.href = "http://localhost:8081/#/search";
          }
        })
    }
  }
}
</script>

<style lang="less">
html,
body,
p {
  margin: 0;
  padding: 0;
}

.el-header {
  padding: 0;
}

.header {
  height: 80px;
  background-color: #475669;
  display: flex;
  align-content: center;
  align-items: center;
  justify-content: space-between;

  .main {
    margin-left: 0.7%;
    margin-bottom: 15px;
  }

  .seach {
    // margin-left: 30%;
    width: 300px;
  }

  .headimg {
    .userimg {
      margin-right: 20px;
      width: 40px;
      border-radius: 50%;
    }

    .unlogin {
      color: #fff;
      margin-right: 20px;
    }
  }

}
</style>
