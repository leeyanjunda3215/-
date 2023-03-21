<template>
    <el-main>
        <div class="last">
            <div class="mainbody">
                <div class="cover" v-for="item in video">
                    <div class="showAndText">
                        <img class="show" style="cursor: pointer;" :src="item.cover" @click="Gotoplay(item)" />
                        <p>{{ item.vName }}</p>
                    </div>
                </div>
            </div>
            <div class="block">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page="currentPage" :page-sizes="[5, 10, 15]" :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
            </div>
        </div>
    </el-main>
</template>


<script>

export default {
    name: 'last',
    data() {
        return {
            pageSize: 10,
            total: 100,
            currentPage: 1,
            video: []
        }
    },
    created() {
        this.gelastByPage()
    },
    methods: {
        gelastByPage() {
            this.$axios.get("http://localhost:8082/video/getlastbypage?currentPage=" + this.currentPage + "&pageSize=" + this.pageSize).then(resp => {
                // console.log(resp.data.data); 
                this.total = resp.data.data.totalCount
                this.video = resp.data.data.rows
            })
        },
        handleSizeChange(val) {
            // console.log(`每页 ${val} 条`);
            this.pageSize = val
            this.gelastByPage()
        },
        handleCurrentChange(val) {
            // console.log(`当前页: ${val}`);
            this.currentPage = val
            this.gelastByPage()
        }
    }
}
</script>
   
<style lang="less" scoped>
.last {
    overflow: hidden;
    margin-left: 20vh;
    width: 150vh;
    height: 90vh;
    background: #fafafa;
    border: 1px solid #e1e2e5;

    .mainbody::-webkit-scrollbar {
        display: none;
    }

    .mainbody {
        height: 80vh;
        overflow: scroll;

        .cover {
            margin-top: 2vh;
            margin-left: 6.5vh;
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
    }


    .block {
        margin-left: 60vh;
    }
}
</style>
