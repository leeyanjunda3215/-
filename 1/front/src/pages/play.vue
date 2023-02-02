<template>
  <el-main>
    <div class="user">
      <img :src="user.headimg" alt="头像">
      <div class="userinfo">
        <p>{{ user.name }}</p>
        <div ref="followbtn">
          <el-button size="mini" type="info" icon="el-icon-plus" @click="follow()" v-if=isfollow>已关注</el-button>
          <el-button size="mini" type="primary" icon="el-icon-plus" @click="follow()" v-else>关注</el-button>
        </div>




      </div>

    </div>
    <div class="video1">
      <video width="1080" height="620" controls :src="ep" type="video/mp4"></video>
      <!-- <a :href="video.videopath">点击播放</a> -->
      <div class="episodes">
        <div class="block" v-for="i in video.episodes">
          <button @click="ChangeEP(i)">{{ i }}</button>
        </div>
      </div>
    </div>
  </el-main>
</template>


<script>

export default {
  name: 'play',
  data() {
    return {
      video: [],
      videoplay: [],
      ep: "",
      user: [],
      // false是没有关注，true 是关注
      isfollow: false,
      userNow: ""

    }
  },
  created() {
    var temp = this.$store.state.tab.videopath
    if(!(temp.length == 0)){
      this.getvideo()
    }
    if(temp.length == 0){
      this.video =JSON.parse(sessionStorage.getItem("video")) 
    }

    this.queryVideo()
    this.queryVideoUser()
    this.isfollowed()

  
  },
  methods: {
    getvideo(){
      this.video = this.$store.state.tab.videopath
      sessionStorage.setItem("video",JSON.stringify(this.video))
    },
    isfollowed() {
      this.UserisVideo()
      var id = this.video.userId
      this.$axios.post("http://localhost:8082/friend/isfollowed", { id }).then(resp => {
        if (resp.data.success == true) {
          this.isfollow = true
        } 
        if (resp.data.success == false) {
          this.isfollow = false
        }
      })

    },
    UserisVideo() {
      this.$axios.get("http://localhost:8082/user/UserisVideo").then(resp => {
        if (resp.data.success == true) {
          // console.log(   resp.data.data);
          this.userNow = resp.data.data
          if (this.userNow == this.video.userId) {
            this.$refs.followbtn.style.display = "none";
          }
        }
      })
    },
    follow() {
      // console.log("视频作者的user_id", this.video.userId);
      // console.log("观看的人的id", this.userNow);

       //取关
      if (this.isfollow == true) {
        var id = this.video.userId
        this.$axios.post("http://localhost:8082/friend/takeoff", { id }).then(resp => {
          if (resp.data.success == false) {
            this.$message.error(resp.data.errorMsg);
          }
          this.isfollow = !this.isfollow
        })
      }
      //关注
      if (this.isfollow == false) {
        var id = this.video.userId

        if(this.userNow.length==0){
          this.$message.error("请登录");
        }

        this.$axios.post("http://localhost:8082/friend/follow", { id }).then(resp => {
          if (resp.data.success == false) {
            this.$message.error(resp.data.errorMsg);
          }
          this.isfollow = !this.isfollow
        })
      }
 
    },
    queryVideo() {
  
      //增加视频的观看量
      this.$axios.post("http://localhost:8082/video/addTimes", this.video).then(resp => {})

      //将视频的剧集分开
      for (let i = 1; i <= this.video.episodes; i++) {
        this.videoplay.push(this.video.videopath + "/" + i + ".mp4")
      }
      this.ep = this.videoplay[0]
    },
    queryVideoUser() {
      var userid = this.video.userId;

      this.$axios.post("http://localhost:8082/user/videoUser", { userid }).then(resp => {
        if (resp.data.success == true) {
          this.user = resp.data.data
        }
      }
      )

    },
    ChangeEP(i) {
      this.ep = this.videoplay[i]
    }
  }

}
</script>

<style lang="less">
.user {
  margin-left: 75%;
  margin-top: 10px;
  display: flex;
  align-items: center;
  align-content: center;

  img {
    width: 60px;
    border-radius: 50%;
  }

  .userinfo {
    margin-top: 10px;
    margin-left: 20px;

    p {
      font-size: large;
    }
  }
}

.video1 {
  margin-top: 50px;
  margin-left: 15%;
  margin-right: 15%;
  display: flex;
  align-items: center;
  align-content: center;

  .episodes {
    margin-left: 20px;
    height: 600px;
    width: 400px;
    background-color: #f1f2f3;

    .block {
      display: inline-block;

      button {
        margin-top: 5px;
        margin-left: 10px;
        margin-bottom: 10px;
        width: 68px;
        height: 36px;
        border: box-sizing;
      }
    }
  }

}
</style>
