<template>

  <el-main>
    <div class="head">
      <div class="img">
        <img :src="user.headimg" alt="头像">
      </div>
      <div class="userinfo">
        <span>{{ user.name }}</span>
        <p class="u-id">uid: {{ user.id }}</p>
        <div class="follow">
          <p>关注: {{ follow.follow }}</p>
          <p>粉丝: {{ follow.fan }}</p>
        </div>
      </div>
    </div>

    <!-- main -->
    <div class="mian">
      <el-tabs v-model="activeName">
        <el-tab-pane label="作品" name="first">

          <el-empty description="暂无作品"></el-empty>

        </el-tab-pane>

        <el-tab-pane label="喜欢" name="second">
          <!-- {{ likes }} -->
          <div v-if=likes>
            <div class="cover" v-for="item in likes">
              <img :src="item.cover" alt="">
              <p>{{ item.vName }}</p>
            </div>
          </div>
          <el-empty v-else description="暂无作品"></el-empty>

        </el-tab-pane>

        <el-tab-pane label="收藏" name="third">
          <!-- {{ collectiones }} -->
          <div v-if=collectiones>
            <div class="cover" v-for="item in collectiones">
              <img :src="item.cover" alt="">
              <p>{{ item.vName }}</p>
            </div>
          </div>

          <el-empty v-else description="暂无作品"></el-empty>
        </el-tab-pane>

        <el-tab-pane label="观看历史" name="fourth">
          <el-empty description="暂无观看"></el-empty>
        </el-tab-pane>

      </el-tabs>
    </div>

  </el-main>
</template>


<script>

export default {
  name: 'info',
  data() {
    return {
      activeName: 'first',
      // 当前用户
      user: [],
      // 关注的数量和粉丝的数量
      follow: [],
      // 点赞的内容
      likes: [],
      // 收藏的内容
      collectiones: [],
    }
  },
  created() {
    this.queryUser()
    this.queryfollowAndfan()
    this.querylikes()
    this.queryCollections()
  },
  methods: {
    queryUser() {
      this.$axios.get("http://localhost:8082/user/me").then(resp => {
        //保存用户
        if (resp.data.success = true) {
          this.user = resp.data.data
        }

      })
    },
    queryfollowAndfan() {
      this.$axios.get("http://localhost:8082/friend/querybyid").then(resp => {
        if (resp.data.success = true) {
          this.follow = resp.data.data
        }
      })
    },
    querylikes() {
      this.$axios.get("http://localhost:8082/like/getlikes").then(resp => {
        if (resp.data.success = true) {
          this.likes = resp.data.data
        }
      })
    },
    queryCollections() {
      this.$axios.get("http://localhost:8082/like/queryCollections").then(resp => {
        if (resp.data.success = true) {
          this.collectiones = resp.data.data
        }
      })
    }
  }

}
</script>

<style lang="less">
.head {
  display: flex;
  align-items: center;
  align-content: center;
  margin-bottom: 20px;

  .img {
    margin-top: 5%;
    margin-left: 10%;

    img {
      width: 112px;
      border-radius: 50%;
    }
  }

  .userinfo {
    margin-left: 40px;
    margin-top: 7%;

    span {
      font-weight: 500;
      font-size: 30px;
    }

    .u-id {
      margin-left: 15px;
      font-size: 15px;
    }

    .follow {
      display: flex;
      align-items: center;
      align-content: center;
      margin-top: 20px;
      margin-bottom: 0px;
      padding-bottom: 0px;

      p {
        padding-right: 30px;
      }
    }
  }
}

.mian {
  margin-left: 10%;
  margin-right: 10%;
}

.cover {
  display: flex;
  align-items: center;
  img {
    width: 150px;
    height: 100px;
    margin-bottom: 2%;
    margin-right: 4%;
  }
}
</style>
