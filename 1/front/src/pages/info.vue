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

          <div v-if="!(uservideo.length == 0)">
            <div>
              <!-- {{ uservideo }} -->
              <!-- v-for="i in uservideo" -->
              <!-- 视频作品的展示 -->
              <el-table :data="uservideo" style="width: 100%" :default-sort="{ prop: 'date', order: 'descending' }">
                <el-table-column prop="vId" label="视频ID" sortable width="180">
                </el-table-column>

                <el-table-column label="视频封面">
                  <template slot-scope="scope">
                    <img :src="scope.row.cover" alt="" style="width: 200px; height: 100px;">
                  </template>
                </el-table-column>

                <el-table-column label="视频名称" width="180">
                  <template slot-scope="scope">
                    <div v-if="isEdit">
                      <p @dblclick="HandleUpdateName(scope.row.vName)">{{ scope.row.vName }}</p>
                    </div>

                    <div v-else>
                      <div v-if="updateRowName == scope.row.vName">
                        <el-input v-model="updateName" :placeholder="scope.row.vName" type="text"
                          @keyup.enter.native="HandleUpdateNameInput(scope.row)"></el-input>
                      </div>
                    </div>

                  </template>

                </el-table-column>

                <el-table-column prop="times" label="视频播放量" sortable width="180">
                </el-table-column>

                <el-table-column label="操作">
                  <template slot="header" slot-scope="scope" style="display: flex;">
                    <el-input style="width: 100px;" v-model="search" size="mini" placeholder="输入关键字"
                      @keyup.enter.native="SearchKeyWord()" />
                    <el-button size="mini" @click="queryVideoByUser()">复原</el-button>

                  </template>
                  <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>

            </div>
          </div>
          <el-empty v-else description="暂无作品"></el-empty>

        </el-tab-pane>

        <el-tab-pane label="喜欢" name="second">
          <!-- {{ likes }} -->
          <div v-if="!(likes.length == 0)">
            <div class="cover" v-for="item in likes">
              <img :src="item.cover" alt="">
              <p>{{ item.vName }}</p>
            </div>
          </div>
          <el-empty v-else description="暂无作品"></el-empty>

        </el-tab-pane>

        <el-tab-pane label="收藏" name="third">
          <!-- {{ collectiones }} -->
          <div v-if="!(collectiones.length == 0)">
            <div class="cover" v-for="item in collectiones">
              <img :src="item.cover" alt="">
              <p>{{ item.vName }}</p>
            </div>
          </div>

          <el-empty v-else description="暂无作品"></el-empty>
        </el-tab-pane>

        <el-tab-pane label="观看历史" name="fourth">
          <el-empty description="暂无观看" v-if="historyvideo.length == 0"></el-empty>
          <div v-else>
            <div class="cover" v-for="item in historyvideo">
              <img :src="item.cover" alt="">
              <p>{{ item.vName }}</p>
            </div>
          </div>
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
      // 当前用户的视频作品
      uservideo: [],
      // 关注的数量和粉丝的数量
      follow: [],
      // 点赞的内容
      likes: [],
      // 收藏的内容
      collectiones: [],
      // 作品表单的搜索
      search: "",
      // 是否在修改
      isEdit: true,
      // 修改的行的名字
      updateRowName: "",
      updateName: "",
      // 观看历史列表
      historyvideo: []
    }
  },
  mounted() {
    this.queryUser()
    this.queryfollowAndfan()
    this.querylikes()
    this.queryCollections()
    this.queryVideoByUser()
    this.queryUserHistory()
  },
  methods: {
    // 查询 当前用户的观看历史
    queryUserHistory() {
      this.$axios.get("http://localhost:8082/history/queryUserHistory").then(resp => {
        if (resp.data.success = true) {
          console.log(resp.data.data);
          this.historyvideo = resp.data.data
        }
      })
    },
    // 查询当前登录用户
    queryUser() {
      this.$axios.get("http://localhost:8082/user/me").then(resp => {
        //保存用户
        if (resp.data.success = true) {
          this.user = resp.data.data
        }
      })
    },
    // 更改用户作品的名字
    HandleUpdateNameInput(i) {
      this.isEdit = !this.isEdit
      // console.log(this.updateName);
      let currentName = i.vId
      let updateName = this.updateName

      if (updateName == "") {
        this.$message({
          type: 'info',
          message: '无法更改空值'
        });
      } else {
        let params = new URLSearchParams();
        params.append("currentName", currentName)
        params.append("updateName", updateName)
        this.$axios.post("http://localhost:8082/video/UpdateVideoName", params).then(resp => {
          if (resp.data.success = true) {
            // this.uservideo = resp.data.data
            this.queryVideoByUser()
            this.updateName = ""
          }
        })
      }

    },
    // 用户双击后显示 用户作品名称 的更改 和输入框
    HandleUpdateName(i) {
      this.isEdit = !this.isEdit
      this.updateRowName = i
    },
    // 用户删除用户作品
    handleDelete(index, row) {
      // index是表格的序列
      // row是表行数据
      this.$confirm('确认删除 ' + row.vName + ' 吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {

        this.$axios.post("http://localhost:8082/video/DeleteVideo", row).then(resp => {
          if (resp.data.success = true) {
            // this.uservideo = resp.data.data
            this.queryVideoByUser()
          }
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 用户根据 关键字 搜索 相关视频
    SearchKeyWord() {
      let search = this.search
      if (search == "") {
        this.$message({ showClose: true, message: "请输入搜索内容" })
      } else {
        this.$axios.post("http://localhost:8082/video/SearchKeyWord", { search }).then(resp => {
          if (resp.data.success = true) {
            this.uservideo = resp.data.data
          }
        })
      }

    },
    // 查询到用户后 进行的相应是查询
    queryfollowAndfan() {
      let userid = this.user.id
      this.$axios.get("http://localhost:8082/friend/querybyid").then(resp => {
        if (resp.data.success = true) {
          this.follow = resp.data.data
        }
      })
    },
    // 查找喜欢视频
    querylikes() {
      let userid = this.user.id
      this.$axios.get("http://localhost:8082/like/getlikes").then(resp => {
        if (resp.data.success = true) {
          this.likes = resp.data.data
        }
      })
    },
    // 查询用户收藏的视频
    queryCollections() {
      let userid = this.user.id
      this.$axios.get("http://localhost:8082/like/queryCollections").then(resp => {
        if (resp.data.success = true) {
          this.collectiones = resp.data.data
        }
      })
    },
    // 查询 用户 的 视频 作品
    queryVideoByUser() {
      let userid = this.user.id
      this.search = ""
      this.$axios.get("http://localhost:8082/video/getVideoByUser").then(resp => {
        if (resp.data.success = true) {
          this.uservideo = resp.data.data
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
