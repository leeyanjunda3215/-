<template>
  <el-container>
    <el-header>
      <div class="header">


        <!-- 首页导航 -->
        <div class="main">
          <img @click="Gotohome()" src="../assets/sign.png" alt="头像">
          <span class="title" @click="Gotohome()">微课</span>

          <div class="homebox">
            <p id="home" class="home" @click="Gotohome()">首页</p>
          </div>

          <div class="lastbox">
            <p id="last" class="last" @click="Gotolast()">最新视频</p>
          </div>

          <div class="classifybox">
            <p id="classify" class="classify" @click="GotoClassify()">视频分类</p>
          </div>

          <div class="collectionbox">
            <p id="collection" class="collection" @click="GotoCollection()">视频合集</p>
          </div>

        </div>


        <!-- s搜索框 -->
        <div class="seach">
          <el-input placeholder="搜索" suffix-icon="el-icon-search" v-model.trim="input1"
            @keyup.enter.native="handleSelect">
          </el-input>
        </div>

        <!-- 私信 ， 投稿 -->
        <div class="message">
          <i class="el-icon-chat-dot-round" @click="Gotochat()"></i>
        </div>
        <div class="upload">
          <i class="el-icon-upload" @click="Gotoupload()"></i>
        </div>

        <!-- 用户头像 -->
        <div class="headimg">
          <el-dropdown v-if="user.length != 0">
            <span class="el-dropdown-link">
              <img class="userimg" :src="user.headimg" />
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="Gotoinfo()">个人信息</el-dropdown-item>
              <el-dropdown-item @click.native="ChangeInfo()">修改信息</el-dropdown-item>
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
import search from './search.vue'
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
    Gotolast() {
      location.href = "http://localhost:8081/#/last"
      let home = document.getElementById("home")
      let last = document.getElementById("last")
      let classify = document.getElementById("classify")
      let collection = document.getElementById("collection")
      home.style.color = "#fff"
      last.style.color = "#409EFF"
      classify.style.color = "#fff"
      collection.style.color = "#fff"
    },
    Gotochat() {
      if (this.user.length == 0) {
        this.$message({ showClose: true, message: "请登录", type: 'error' })
      } else {
        location.href = "http://localhost:8081/#/chat"
      }
    },
    GotoCollection() {
      location.href = "http://localhost:8081/#/collection"
      let home = document.getElementById("home")
      let last = document.getElementById("last")
      let classify = document.getElementById("classify")
      let collection = document.getElementById("collection")
      home.style.color = "#fff"
      last.style.color = "#fff"
      classify.style.color = "#fff"
      collection.style.color = "#409EFF"
    },
    GotoClassify() {
      location.href = "http://localhost:8081/#/classify"
      let home = document.getElementById("home")
      let last = document.getElementById("last")
      let classify = document.getElementById("classify")
      let collection = document.getElementById("collection")
      home.style.color = "#fff"
      last.style.color = "#fff"
      classify.style.color = "#409EFF"
      collection.style.color = "#fff"
    },
    Gotoupload() {
      if (this.user.length == 0) {
        // this.$message.error("请登录");
        this.$message({ showClose: true, message: "请登录", type: 'error' })
        // this.$message({ showClose: true, message: "请登录", type: error })
      } else {
        location.href = "http://localhost:8081/#/upload"
      }

      // location.href = "http://localhost:8081/#/upload"

    },
    Gotoplay(i) {
      this.$store.state.tab.videopath = i;
      location.href = "http://localhost:8081/#/play"
    },
    queryUser() {
      this.$axios.get("http://localhost:8082/user/me").then(resp => {
        //保存用户
        this.user = resp.data.data

        this.$store.state.tab.user = this.user
      })
    },
    Gologin() {
      location.href = 'http://localhost:8081/#/login';
    },
    Gotoinfo() {
      location.href = "http://localhost:8081/#/info";
    },
    ChangeInfo() {
      location.href = "http://localhost:8081/#/changeInfo";
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
    handleSelect() {
      var input = this.input1
      if (input == "") {
        this.$message({ showClose: true, message: "请输入搜索内容" })
      }
      else {
        // this.$store.state.tab.search = input
        this.$router.push({ path: '/search', query: { input: input } })
      }
    }

  }
}
</script>

<style lang="less">
p {
  margin: 0;
  padding: 0;
}

.el-header {
  padding: 0;
}

.header {
  width: auto;
  height: 8vh;
  background-color: #475669;
  display: flex;
  align-content: center;
  align-items: center;

  .main {
    margin-left: 5vh;
    display: flex;
    align-items: center;
    margin-bottom: 3vh;

    img {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      margin-right: 2vh;
      margin-left: 15vh;
    }

    img:hover {
      margin-top: 5px;
    }

    .title {
      margin-right: 2vh;
      font-size: 45px;
      font-weight: 600px;
    }

    .title:hover {
      margin-bottom: 5px;
    }

    span {
      color: #fff;
      margin-right: 50px;
    }

    .homebox {
      height: 11vh;
      width: 15vh;

      .home {
        color: #fff;
        margin-left: 5vh;
        margin-top: 5vh;
        font-size: 20px;
      }

      .home:hover {
        color: #409EFF;
      }
    }


    .lastbox {
      height: 11vh;
      width: 15vh;

      .last {
        color: #fff;
        margin-left: 4vh;
        margin-top: 5vh;
        font-size: 20px;
      }

      .last:hover {
        color: #409EFF;
      }
    }

    .classifybox {
      height: 11vh;
      width: 15vh;

      .classify {
        color: #fff;
        margin-left: 4vh;
        margin-top: 5vh;
        font-size: 20px;
      }

      .classify:hover {
        color: #409EFF;
      }
    }

    .collectionbox {
      height: 11vh;
      width: 15vh;

      .collection {
        color: #fff;
        margin-left: 4vh;
        margin-top: 5vh;
        font-size: 20px;
      }

      .collection:hover {
        color: #409EFF;
      }
    }
  }

  .seach {
    margin-left: 10vh;
    width: 20vh;
  }

  .message {
    margin-left: 15vh;

    .el-icon-chat-dot-round {
      color: #fff;
      font-size: 30px;
    }

    .el-icon-chat-dot-round:hover {
      margin-top: 5px;
      color: #409EFF;
    }

  }

  .upload {
    margin-left: 3vh;

    .el-icon-upload {
      color: #fff;
      font-size: 30px;
    }

    .el-icon-upload:hover {
      margin-top: 5px;
      color: #409EFF;
    }
  }

  .headimg {
    width: 6%;
    margin-left: 5vh;
    // margin-right: 10%;

    .userimg {
      margin-right: 20px;
      width: 40px;
      border-radius: 50%;
    }

    .userimg:hover {
      margin-top: 5%;
    }

    .unlogin {
      font-size: 20px;
      color: #fff;
      margin-right: 20px;
    }

    .unlogin:hover {
      margin-top: 5px;
      color: #409EFF;
    }
  }

}
</style>
