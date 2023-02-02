<template>

  <el-main>
    <div class="head">
      <div class="img">
        <img src="../assets/images/user1.png" alt="头像">
      </div>
      <div class="userinfo">
        <span>Username</span>
        <p class="u-id">uid: 1212121</p>
        <div class="follow">
          <p>关注: 0</p>
          <p>粉丝: 0</p>
        </div>
      </div>
    </div>

    <!-- main -->
    <div class="mian">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="作品" name="first">作品</el-tab-pane>
        <el-tab-pane label="喜欢" name="second">喜欢</el-tab-pane>
        <el-tab-pane label="收藏" name="third">收藏</el-tab-pane>
        <el-tab-pane label="观看历史" name="fourth">观看历史</el-tab-pane>
      </el-tabs>
    </div>

  </el-main>
</template>


<script>

export default {
  name: 'info',
  data() {
    return {
      activeName: 'first'
    }
  },
  created() {

  },
  methods: {

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
.mian{
  margin-left: 10%;
  margin-right: 10%;
}
</style>
