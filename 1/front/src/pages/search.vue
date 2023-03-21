<template>
    <el-main>
        <div class="mainbody">
            <div>
                <div class="cover" v-for="item in video">
                    <div class="showAndText">
                        <img class="show" style="cursor: pointer;" :src="item.cover" @click="Gotoplay(item)" />
                        <p>{{ item.vName }}</p>
                    </div>
                </div>
            </div>
            <div class="block" v-if="!(total == 0)">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page="currentPage" :page-sizes="[5, 10, 15]" :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
            </div>
            <el-empty v-if="total == 0" description="没有内容"></el-empty>
        </div>


    </el-main>
</template>


<script>

export default {
    name: 'search',
    data() {
        return {
            input: "",
            video: [],
            pageSize: 10,
            total: 0,
            currentPage: 1,
        }
    },
    mounted() {
        this.getInput()
        this.handelSearch()
    },
    // activated() {
    //     console.log('路由跳转触发')
    // },
    watch: {
        '$route'(to, from) {
            //执行数据更新查询
            this.getInput()
            this.handelSearch()
        }
    },
    methods: {
        getInput() {
            // this.input = this.$store.state.tab.search;
            this.input = this.$route.query.input
            console.log("search的input:", this.input);
        },
        handelSearch() {
            // console.log(this.input);
            // http://localhost:8082/video/search
            var params = new URLSearchParams();
            params.append("currentPage", this.currentPage)
            params.append("pageSize", this.pageSize)
            params.append("search", this.input)
            this.$axios.post("http://localhost:8082/video/search", params).then(resp => {
                if (resp.data.success == true) {
                    this.total = resp.data.data.totalCount
                    this.video = resp.data.data.rows
                }
            })

        },
        Gotoplay(i) {
            console.log(i);
            this.$store.state.tab.videopath = i;
            location.href = "http://localhost:8081/#/play"
        },
        handleSizeChange(val) {
            // console.log(`每页 ${val} 条`);
            this.pageSize = val
            this.handelSearch()
        },
        handleCurrentChange(val) {
            // console.log(`当前页: ${val}`);
            this.currentPage = val
            this.handelSearch()
        }
    }
}
</script>

<style lang="less">
.mainbody {
    margin-left: 20vh;
    width: 150vh;
    height: 90vh;
    background: #fafafa;
    border: 1px solid #e1e2e5;

    .cover {
        margin-top: 2vh;
        margin-left: 3vh;
        display: inline-block;
        align-content: center;
        align-items: center;

        .showAndText {
            text-align: center;
        }

        .show {
            margin-right: 5%;
            width: 255px;
            height: 150px;
            padding: 10px;
            padding-left: 0;
            padding-right: 20px;
            object-fit: fill;
        }
    }

    .block {
        margin-left: 60vh;
    }
}
</style>
