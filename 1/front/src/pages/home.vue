<template>

    <el-main>
        <div class="main">
            <!-- 轮播图 -->
            <div class="carousel">
                <el-carousel :interval="2000" type="card" height="200px">
                    <el-carousel-item v-for="item in carousel" :key="item.id">
                        <img :src="item.cover" @click="Gotoplay(item)" alt="轮播">
                    </el-carousel-item>
                </el-carousel>
            </div>


            <div class="center">
                <!-- java 的展示 -->
                <div class="title">
                    <p>JAVA</p>
                </div>
                <el-divider class="line"></el-divider>
                <!-- 展示 视频的封面 -->
                <div class="cover" v-for="item in path" :key="item.vId" :index="item.classify"
                    v-if="item.classify == 'JAVA'">
                    <div>
                        <img class="show" style="cursor: pointer;" :src="item.cover" @click="Gotoplay(item)" />
                        <div class="vname">
                            <p>{{ item.vName }}</p>
                        </div>
                    </div>
                </div>

                <!-- python 的展示 -->
                <div class="title">
                    <p>Python</p>
                </div>
                <el-divider class="line"></el-divider>
                <!-- 展示 视频的封面 -->
                <div class="cover" v-for="item in path" :key="item.vId" :index="item.classify"
                    v-if="item.classify == 'Python'">
                    <div>
                        <img class="show" style="cursor: pointer;" :src="item.cover" @click="Gotoplay(item)" />
                        <div class="vname">
                            <p>{{ item.vName }}</p>
                        </div>
                    </div>
                </div>

                <!-- 动漫 的展示 -->
                <div class="title">
                    <p>动漫</p>
                </div>
                <el-divider class="line"></el-divider>
                <!-- 展示 视频的封面 -->
                <div class="cover" v-for="item in path" :key="item.vId" :index="item.classify"
                    v-if="item.classify == '动漫'">
                    <div>
                        <img class="show" style="cursor: pointer;" :src="item.cover" @click="Gotoplay(item)" />
                        <div class="vname">
                            <p>{{ item.vName }}</p>
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

        }
    },
    created() {
        this.querypath()
        this.queryimg()

    },
    methods: {
        Gotoplay(i) {
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
                    // console.log(this.carousel);


                }
            })
        }
    }
}
</script>

<style lang="less">
.main {
    margin-top: 20px;

    .carousel {
        width: 50%;
        margin-left: 26%;

        img {
            width: 350px;
            height: 200px;
            padding: 10px;
            padding-left: 0;
            padding-right: 20px;
            object-fit: fill;
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
            margin-left: 10%;
            width: 80%;
        }

        .cover {
            margin-left: 10%;
            display: inline-block;
            align-content: center;
            align-items: center;

            .show {
                width: 150px;
                height: 255px;
                padding: 10px;
                padding-left: 0;
                padding-right: 20px;
                object-fit: fill;
            }
        }

        .vname {
            margin-left: 10%;
        }
    }


}
</style>