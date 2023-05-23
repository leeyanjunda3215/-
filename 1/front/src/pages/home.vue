<template>
    <el-main>
        <div class="main">
            <!-- 轮播图 -->
            <div class="head">
                <div class="carousel">
                    <el-carousel>
                        <el-carousel-item v-for="item in carousel" :key="item.id">
                            <div class="allcarousel">
                                <img :src="item.cover" @click="Gotoplay(item)" alt="轮播">
                                <!-- <p>{{ item.vName }}</p> -->
                            </div>
                        </el-carousel-item>
                    </el-carousel>
                </div>


                <div class="recommend">
                    <p>今日推荐</p>
                    <div class="cover" v-for="(item, index) in recommend">
                        <div class="showAndText">
                            <!-- <img class="show" style="" :src="item.cover"  /> -->
                            <video id="video" class="show" muted :src="item.videopath + '/1.mp4'" :poster="item.cover"
                                @click="Gotoplay(item)"></video>
                            <p>{{ item.vName }}</p>
                        </div>
                    </div>
                </div>
            </div>



            <div v-for="i in classify">
                <div class="center">
                    <!-- 分类 的展示 -->
                    <div class="title">
                        <p>{{ i.classify }}</p>
                    </div>

                    <el-divider class="line"></el-divider>
                    <!-- 展示 视频的封面 -->

                    <div class="towall">
                        <div class="cover" v-for="(item, index) in path" :key="item.vId" :index="item.classify"
                            v-if="item.classify == i.classify">
                            <div class="showAndText">
                                <!-- <img class="show" style="" :src="item.cover"  /> -->
                                <video id="video" class="show" muted :src="item.videopath + '/1.mp4'" :poster="item.cover"
                                    @click="Gotoplay(item)"></video>
                                <p>{{ item.vName }}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </el-main>
</template>

<script>
export default {
    name: 'home',
    data() {
        return {
            path: [],
            carousel: [],
            classify: [],
            // 推荐的视频（首页视频）
            recommend: []
        }
    },
    created() {
        this.queryclassify()
        this.querypath()
        this.queryimg()
        this.querySystemrecommend()
    },
    methods: {

        // 视频暂停
        // VideoPuse() {
        //     var video = document.getElementById("video")
        //     video.pause()
        // },
        // 鼠标移入观看
        // WatchVideoByHover(item) {
        //     var video = document.getElementById("video")
        //     video.play()
        // },

        querySystemrecommend() {
            this.$axios.get("http://localhost:8082/video/recommend").then(resp => {
                if (resp.data.success == true) {
                    console.log(resp.data.data);
                    this.recommend = resp.data.data
                }
            })
        },


        // 查询所有分类
        queryclassify() {
            this.$axios.get("http://localhost:8082/video/queryclassify").then(resp => {
                if (resp.data.success == true) {
                    this.classify = resp.data.data
                }
            })
        },
        Gotoplay(i) {
            // 将跳转前的视频信息添加到历史记录中
            this.$axios.post("http://localhost:8082/history/addhistory", i).then(resp => {

            })

            this.$store.state.tab.videopath = i;
            location.href = "http://localhost:8081/#/play"
        },
        querypath() {
            this.$axios.get("http://localhost:8082/video/all").then(resp => {
                //保存用户
                this.path = resp.data.data
            })
        },
        queryimg() {
            this.$axios.get("http://localhost:8082/video/getCarousel").then(resp => {
                if (resp.data.success == true) {
                    this.carousel = resp.data.data
                    console.log(this.carousel);
                }
            })
        }
    }
}
</script>

<style lang="less">
.main {
    margin-top: 20px;

    .head {
        display: flex;

        .carousel {
            margin-top: 50px;
            width: 590px;
            height: 300px;

            // width: 255px;
            // height: 150px;
            margin-left: 10%;

            .allcarousel {
                width: 590px;
                height: 300px;
                position: relative;

                img {
                    width: 100%;
                    height: 100%;
                    padding: 10px;
                    padding-left: 0;
                    padding-right: 20px;
                    // object-fit: fill;
                }

                p {
                    color: white;
                    position: absolute;
                    left: 1rem;
                    bottom: 2rem;
                }
            }


        }

        .recommend {
            margin-left: 40px;
            width: 1240px;
            height: 420px;
            overflow: hidden;

            .cover {
                margin-right: 2%;
                display: inline-block;
                align-content: center;
                align-items: center;

                .showAndText {
                    text-align: center;
                }

                .show {
                    cursor: pointer;
                    margin-right: 5%;
                    width: 255px;
                    height: 150px;
                    padding: 10px;
                    padding-left: 0;
                    padding-right: 20px;
                    object-fit: fill;
                }
            }
        }
    }




    .center {

        .title {
            margin-top: 20px;
            margin-left: 10%;
            margin-right: 10%;
            font-weight: 1000;
            font-size: large;
            display: flex;
            align-content: center;
            align-items: center;
            justify-content: space-between;
        }

        .line {
            // max-height: 20px;
            margin-left: 10%;
            width: 80%;
        }

        .towall {
            margin-left: 10%;
            max-height: 400px;
            overflow: hidden;

            .cover {

                margin-right: 2%;
                display: inline-block;
                align-content: center;
                align-items: center;

                .showAndText {
                    text-align: center;
                }

                .show {
                    cursor: pointer;
                    margin-right: 5%;
                    width: 255px;
                    height: 150px;
                    padding: 10px;
                    padding-left: 0;
                    padding-right: 20px;
                    object-fit: fill;
                }


            }
        }

    }


}
</style>