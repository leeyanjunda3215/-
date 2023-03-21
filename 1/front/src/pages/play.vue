<template>
  <el-main>
    <div class="user">
      <img :src="user.headimg" alt="头像" @click="Gotoinfo(user.id)">
      <div class="userinfo">
        <p>{{ user.name }}</p>
        <div ref="followbtn" class="tools_btn">
          <el-button size="mini" type="info" icon="el-icon-plus" @click="follow()" v-if=isfollow>已关注</el-button>
          <el-button size="mini" type="primary" icon="el-icon-plus" @click="follow()" v-else>关注</el-button>
          <el-button size="mini" type="primary" icon="el-icon-plus" @click="gotochat()">私 信</el-button>
        </div>
      </div>
    </div>

    <div class="video1">
      <!-- <video width="1080px" height="620px" controls :src="ep" type="video/mp4"></video> -->
      <video width="70%" height="40%" controls :src="ep" type="video/mp4"></video>
      <!-- <a :href="video.videopath">点击播放</a> -->
      <div class="episodes">
        <div class="block" v-for="i in video.episodes">
          <button @click="ChangeEP(i)">{{ i }}</button>
        </div>
      </div>
    </div>

    <!--  点赞，收藏 -->
    <div class="tools">
      <img @click="handlelike" v-if=islike src="../assets/icon/like1.png" alt="点赞">
      <img @click="handlelike" v-else src="../assets/icon/like0.png" alt="点赞">

      <img @click="handlecollction" v-if=isccollection src="../assets/icon/collection1 .png" alt="收藏">
      <img @click="handlecollction" v-else src="../assets/icon/collection0.png" alt="收藏">
    </div>

    <el-divider class="d1"></el-divider>

    <!-- 视频简介 -->
    <div class="brief">
      <p>{{ video.brief }}</p>
      <p></p>
    </div>

    <el-divider class="d1"></el-divider>

    <!-- 写评论 -->
    <div class="writecomment">

      <div class="writer">
        <img v-if="userNow.headimg == null" src="../assets/images/user0.png" alt="">
        <img v-else :src="userNow.headimg" alt="">
      </div>

      <div class="input">
        <el-input type="textarea" autosize placeholder="请输入内容" v-model="textarea">
        </el-input>
      </div>

      <div class="btn"><el-button type="primary" @click="rootcomment()">发布</el-button></div>
    </div>

    <!-- root 评论 -->
    <div class="root-comment" v-for="item in comment">

      <div v-if="item.tocomment == 0">
        <div class="userinfo">
          <img :src="item.headimg" alt="">
          <p>{{ item.username }}</p>
          <P>|</P>
          <p>{{ item.createTime }}</p>
          <!-- <img v-if=isheart class="heart" src="../assets/icon/heart1.png" alt="">
          <img v-else class="heart" src="../assets/icon/heart0.png" alt=""> -->
          <a @click="toreply(item.cId)">回复</a>
        </div>

        <div class="content">
          <p>
            {{ item.content }}
          </p>
        </div>
        <el-divider class="d2"></el-divider>
      </div>


      <!-- 弹出 回复 输入框 -->
      <div class="reply-input" v-if="isreply && item.cId == tocId">
        <div class="writer">
          <img v-if="userNow.headimg == null" src="../assets/images/user0.png" alt="">
          <img v-else :src="userNow.headimg" alt="">
        </div>

        <div class="input">
          <el-input type="textarea" autosize placeholder="请输入内容" v-model="replyarea">
          </el-input>
        </div>

        <div class="btn"><el-button type="primary" @click="replytoroot(item.cId)">发送</el-button></div>
      </div>

      <!-- 如果有回复，显示回复 -->
      <!--  reply to root  -->
      <!-- v-if="item.tocomment == 1 || item.rootcomment == item.cId" -->
      <div v-for="i in comment">
        <div class="reply-root" v-if="i.tocomment == 1 && i.rootcomment == item.cId">
          <div class="reply-userinfo">
            <img :src="i.headimg" alt="">
            <p>{{ i.username }}</p>
            <P>|</P>
            <p>{{ i.createTime }}</p>
            <!-- <img v-if=isheart class="heart" src="../assets/icon/heart1.png" alt="">
            <img v-else class="heart" src="../assets/icon/heart0.png" alt=""> -->
            <!-- <a @click="toreply(item.cId)">回复</a> -->
          </div>
          <div class="content">
            <p>
              {{ i.content }}
            </p>
          </div>
          <el-divider class="d2"></el-divider>
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
      //是否展示用户的信息框
      isshow: false,

      //关注 false是没有关注，true 是关注
      isfollow: false,

      //视频点赞
      islike: false,
      // 收藏
      isccollection: false,
      // 评论点赞
      isheart: false,
      //弹出回复框
      isreply: false,
      userNow: "",
      // 评论
      textarea: "",
      // 回复
      replyarea: "",
      // 回复的评论id
      tocId: "",
      comment: []

    }
  },
  created() {
    var temp = this.$store.state.tab.videopath
    if (!(temp.length == 0)) {
      this.getvideo()
    }
    if (temp.length == 0) {
      this.video = JSON.parse(sessionStorage.getItem("video"))
    }
    this.queryallcomments()
    this.queryVideo()
    this.queryVideoUser()
    this.isfollowed()
    this.isliked()
    this.iscollctiond()
  },
  methods: {
    // 跳转到用户信息
    Gotoinfo(i) {
      console.log(1);
      this.$router.push({ path: '/info', query: { id: i } })
      console.log('跳转结束');
    },
    // 跳转到私聊界面
    gotochat() {
      // this.$store.state.tab.chatuser = this.user
      localStorage.setItem("chatwith", JSON.stringify(this.user))
      location.href = "http://localhost:8081/#/chat"
    },
    // 展示视频作者详情，私聊等人
    showUserHoverInfo() {
      this.isshow = !this.isshow
    },
    // 防止页面刷新
    getvideo() {
      this.video = this.$store.state.tab.videopath
      sessionStorage.setItem("video", JSON.stringify(this.video))
    },

    // 点赞
    handlelike() {
      var id = this.video.vId
      //取点赞
      if (this.islike == true) {
        this.$axios.post("http://localhost:8082/like/offlike", { id }).then(resp => {
          if (resp.data.success == true) {
            this.islike = !this.islike
          }
          if (resp.data.success == false) {
            this.$message.error(resp.data.errorMsg)
          }
        })
      }
      //点赞
      if (this.islike == false) {
        this.$axios.post("http://localhost:8082/like/like", { id }).then(resp => {
          if (resp.data.success == true) {
            this.islike = !this.islike
          }
          if (resp.data.success == false) {
            this.$message.error(resp.data.errorMsg)
          }
        })
      }
    },
    //判断是否点过赞了
    isliked() {
      var id = this.video.vId
      this.$axios.post("http://localhost:8082/like/isliked", { id }).then(resp => {
        if (resp.data.success == true) {
          this.islike = false
        }
        if (resp.data.success == false) {
          this.islike = true
        }
      })
    },
    // 收藏
    handlecollction() {
      // this.isccollection = !this.isccollection
      var id = this.video.vId
      //收藏
      if (this.isccollection == false) {
        this.$axios.post("http://localhost:8082/like/collctiond", { id }).then(resp => {
          if (resp.data.success == true) {
            this.isccollection = !this.isccollection
          }
          if (resp.data.success == false) {
            this.$message.error(resp.data.errorMsg)
          }
        })
      }
      //取消收藏
      if (this.isccollection == true) {
        this.$axios.post("http://localhost:8082/like/collctiondoff", { id }).then(resp => {
          if (resp.data.success == true) {
            this.isccollection = !this.isccollection
          }
          if (resp.data.success == false) {
            this.$message.error(resp.data.errorMsg)
          }
        })
      }

    },
    //判断是否收藏过了
    iscollctiond() {
      var id = this.video.vId
      this.$axios.post("http://localhost:8082/like/iscollctiond", { id }).then(resp => {
        if (resp.data.success == true) {
          this.isccollection = false
        }
        if (resp.data.success == false) {
          this.isccollection = true
        }
      })
    },
    // 查询所有评论
    queryallcomments() {
      var videoid = this.video.vId
      this.$axios.post("http://localhost:8082/comment/allcomment", { videoid }).then(resp => {
        if (resp.data.success == true) {
          this.comment = resp.data.data
          // console.log(this.comment);
        }
      })
    },
    // 评论
    rootcomment() {
      // console.log(this.textarea);
      // console.log("视频id", this.video.vId);
      // console.log("观看的人的id", this.userNow.id);

      this.$axios.post("http://localhost:8082/comment/rootcomment", {
        "content": this.textarea,
        "videoId": this.video.vId,
        "userId": this.userNow.id
      }).then(resp => {
        if (resp.data.success == false) {
          this.$message.error(resp.data.errorMsg)
        }
        if (resp.data.success == true) {
          this.queryallcomments()
        }
      })
    },
    // 弹出回复框
    toreply(item) {
      if (this.tocId == item) {
        this.isreply = !this.isreply
      } else {
        this.tocId = item
      }
    },
    //进行回复
    replytoroot(item) {
      var cId = item;
      console.log(this.replyarea);
      this.$axios.post("http://localhost:8082/comment/reply", {
        "content": this.replyarea,
        "videoId": this.video.vId,
        "userId": this.userNow.id,
        "commentid": cId
      }).then(resp => {
        if (resp.data.success == false) {
          this.$message.error(resp.data.errorMsg)
        }
        if (resp.data.success == true) {
          this.isreply = !this.isreply
          this.queryallcomments()
        }
      })
    },

    // 判断用户是否关注
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
    // 判断用户是否是视频作者
    UserisVideo() {
      this.$axios.get("http://localhost:8082/user/UserisVideo").then(resp => {
        if (resp.data.success == true) {
          // console.log(   resp.data.data);
          this.userNow = resp.data.data
          if (this.userNow.id == this.video.userId) {
            this.$refs.followbtn.style.display = "none";
          }
        }
      })
    },
    // 点击关注 和 取消关注
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

        if (this.userNow.length == 0) {
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
    //查询响应的视频，并分集
    queryVideo() {
      //增加视频的观看量
      this.$axios.post("http://localhost:8082/video/addTimes", this.video).then(resp => { })

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
    // 换集
    ChangeEP(i) {
      this.ep = this.videoplay[i]
    }
  }

}
</script>

<style lang="less">
template {
  width: 100%;
  height: 100%;
}

.user {
  margin-left: 85%;
  margin-top: 1%;
  display: flex;
  align-items: center;
  align-content: center;

  .userHoverInfo {
    margin-top: 1%;
    position: absolute;
    display: flex;
    width: 230px;
  }

  img {
    width: 60px;
    border-radius: 50%;
  }

  img:hover {
    width: 55px;
  }

  .tools_btn {
    display: flex;
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
  margin-top: 1%;
  margin-left: 15%;
  // margin-right: 15%;
  display: flex;

  .episodes {
    margin-left: 2%;
    margin-right: 8%;
    height: auto;
    background-color: #f1f2f3;

    .block {
      display: inline-block;

      button {
        margin-top: 5px;
        margin-left: 10px;
        margin-bottom: 10px;
        width: 68px;
        height: 36px;
        // border: box-sizing;
      }
    }
  }
}

// 点赞，收藏
.tools {
  margin-top: 1%;
  margin-bottom: 30px;
  margin-left: 15%;

  img {
    width: 40px;
    height: 40px;
  }


}

.d1 {
  width: 1080px;
  margin-left: 15%;
}

// 简介
.brief {
  margin-top: 10px;
  margin-left: 15%;

  p {
    width: 300px;
    word-break: break-all;
    word-wrap: break-word;
  }
}

// 写评论
.writecomment {
  display: flex;
  align-items: center;
  margin-top: 2%;
  margin-left: 15%;

  .writer {
    margin-right: 2%;
    display: flex;
    align-items: center;
    align-content: center;

    img {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      margin-right: 1%;
    }
  }

  .input {
    width: 700px;
  }

  .btn {
    margin-left: 3%;
  }

}

.d2 {
  width: 1080px;
  // margin-left: 15%;
}

// root评论
.root-comment {
  margin-top: 2%;
  margin-left: 15%;

  .userinfo {
    display: flex;
    align-items: center;

    img {
      width: 4%;
      height: 4%;
      border-radius: 50%;
      margin-right: 2%;
    }

    p {
      margin-right: 2%;
    }

    .heart {
      width: 3%;
      height: 3%;
    }

    a:hover {
      color: blue;
    }
  }

  .content {
    margin-top: 1%;
    margin-left: 6%;
    width: 60%;
    height: auto;

    p {
      word-wrap: break-word;
      word-break: break-all;
    }
  }

  .reply-input {
    display: flex;
    align-items: center;
    margin-top: 2%;
    margin-left: 5%;

    .writer {
      margin-right: 2%;
      display: flex;
      align-items: center;
      align-content: center;

      img {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        margin-right: 1%;
      }
    }

    .input {
      width: 700px;
    }

    .btn {
      margin-left: 3%;
    }
  }
}

.reply-root {
  margin-top: 2%;
  margin-left: 5%;

  .reply-userinfo {
    display: flex;
    align-items: center;

    img {
      width: 4%;
      height: 4%;
      border-radius: 50%;
      margin-right: 2%;
    }

    p {
      margin-right: 2%;
    }

    .heart {
      width: 3%;
      height: 3%;
    }
  }

  .content {
    margin-top: 1%;
    margin-left: 6%;
    width: 60%;
    height: auto;

    p {
      word-wrap: break-word;
      word-break: break-all;
    }
  }
}
</style>
