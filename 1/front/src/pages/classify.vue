<template>
    <el-main>
        <div class="mainbody">
            <div class="select">
                <div class="select1">
                    <span>专业分类：</span>
                    <el-select v-model="select1" placeholder="请选择">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </div>
                <div class="select2">
                    <div v-for="i in options">
                        <div v-if="i.label == select1">
                            <span>课程分类：</span>
                            <el-select v-model="select2" placeholder="请选择">
                                <el-option v-for="item in i.children" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </div>
                    </div>
                </div>
                <div class="select_btn">
                    <el-button type="primary" icon="el-icon-search" @click="seach()">搜索</el-button>
                </div>

            </div>
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
    name: 'classify',
    data() {
        return {
            options: [
                {
                    value: "计算机",
                    label: "计算机",
                    children: [
                        {
                            value: "大数据与人工智能",
                            label: "大数据与人工智能"
                        }, {
                            value: "程序设计开发",
                            label: " 程序设计开发"
                        }, {
                            value: "计算机基础与应用",
                            label: "计算机基础与应用"
                        }, {
                            value: "软件工程",
                            label: "软件工程"
                        }, {
                            value: "网络与安全技术",
                            label: "网络与安全技术"
                        }, {
                            value: "硬软件系统及原理",
                            label: "硬软件系统及原理"
                        }, {
                            value: "前端开发",
                            label: "前端开发"
                        }, {
                            value: "后端开发",
                            label: "后端开发"
                        }
                    ]
                }, {
                    value: "外语",
                    label: "外语",
                    children: [
                        {
                            value: "语法/阅读",
                            label: "语法/阅读"
                        }, {
                            value: "写作/翻译",
                            label: "写作/翻译"
                        }, {
                            value: "文学与语言学",
                            label: "文学与语言学"
                        }, {
                            value: "专用途英语",
                            label: "专用途英语"
                        }, {
                            value: "四六级",
                            label: "四六级"
                        }, {
                            value: "雅思",
                            label: "雅思"
                        }, {
                            value: "考研英语",
                            label: "考研英语"
                        }, {
                            value: "专四专八",
                            label: "专四专八"
                        }
                    ]
                }, {
                    value: "理工农",
                    label: "理工农",
                    children: [
                        {
                            value: "数学",
                            label: "数学"
                        }, {
                            value: "物理",
                            label: "物理"
                        }, {
                            value: "天文学",
                            label: "天文学"
                        }, {
                            value: "地理科学",
                            label: "地理科学"
                        }, {
                            value: "生物科学",
                            label: "生物科学"
                        }, {
                            value: "电气信息",
                            label: "电气信息"
                        }, {
                            value: "机械工程",
                            label: "机械工程"
                        }, {
                            value: "大气与海洋",
                            label: "大气与海洋"
                        }, {
                            value: "农科化学",
                            label: "农科化学"
                        }, {
                            value: "土建水利",
                            label: "土建水利"
                        }, {
                            value: "力学",
                            label: "力学"
                        }, {
                            value: "材料",
                            label: "材料"
                        }, {
                            value: "交通运输",
                            label: "交通运输"
                        }, {
                            value: "化工与生物制药",
                            label: "化工与生物制药"
                        }, {
                            value: "能源矿业",
                            label: "能源矿业"
                        }, {
                            value: "轻纺食品",
                            label: "轻纺食品"
                        }, {
                            value: "航空航天",
                            label: "航空航天"
                        }, {
                            value: "农业环境",
                            label: "农业环境"
                        }
                    ]
                }, {
                    value: "经济管理",
                    label: "经济管理",
                    children: [
                        {
                            value: "经济",
                            label: "经济"
                        }, {
                            value: "金融",
                            label: "金融"
                        }, {
                            value: "电商贸易",
                            label: "电商贸易"
                        }, {
                            value: "会计",
                            label: "会计"
                        }, {
                            value: "管理",
                            label: "管理"
                        }
                    ]
                }, {
                    value: "文史哲法",
                    label: "文史哲法",
                    children: [
                        {
                            value: "文学文化",
                            label: "文学文化"
                        }, {
                            value: "新闻传播",
                            label: "新闻传播"
                        }, {
                            value: "哲学",
                            label: "哲学"
                        }, {
                            value: "历史",
                            label: "历史"
                        }, {
                            value: "法学",
                            label: "法学"
                        }, {
                            value: "思政",
                            label: "思政"
                        }, {
                            value: "社会",
                            label: "社会"
                        }
                    ]
                }, {
                    value: "教育教学",
                    label: "教育教学",
                    children: [
                        // 教学方法，教学能力，信息化教学，职业素养，学科教学，素质教育，体育教育，学前教育
                        {
                            value: "教学方法",
                            label: "教学方法"
                        }, {
                            value: "教学能力",
                            label: "教学能力"
                        }, {
                            value: "信息化教学",
                            label: "信息化教学"
                        }, {
                            value: "职业素养",
                            label: "职业素养"
                        }, {
                            value: "学科教学",
                            label: "学科教学"
                        }, {
                            value: "素质教育",
                            label: "素质教育"
                        }, {
                            value: "体育教育",
                            label: "体育教育"
                        }, {
                            value: "学前教育",
                            label: "学前教育"
                        }
                    ]
                }, {
                    value: "音乐艺术",
                    label: "音乐艺术",
                    children: [
                        // 设计/创作，艺术学，美术学，戏剧与影视，设计学，音乐与舞蹈
                        {
                            label: "设计/创作",
                            value: "设计/创作"
                        }, {
                            value: "艺术学",
                            label: "艺术学"
                        }, {
                            value: "美术学",
                            label: "美术学"
                        }, {
                            value: "戏剧与影视",
                            label: "戏剧与影视"
                        }, {
                            value: "设计学",
                            label: "设计学"
                        }, {
                            value: "音乐与舞蹈",
                            label: "音乐与舞蹈"
                        }
                    ]
                }

            ],
            select1: "计算机",
            select2: "",
            pageSize: 10,
            total: 0,
            currentPage: 1,
            video: []
        }
    },
    created() {
    },
    methods: {
        Gotoplay(i) {
            // 将跳转前的视频信息添加到历史记录中
            this.$axios.post("http://localhost:8082/history/addhistory", i).then(resp => {

            })

            this.$store.state.tab.videopath = i;
            location.href = "http://localhost:8081/#/play"
        },
        seach() {
            // console.log(this.select1);
            // console.log(this.select2);
            var params = new URLSearchParams();
            params.append("currentPage", this.currentPage)
            params.append("pageSize", this.pageSize)
            params.append("classify", this.select1)
            params.append("tag", this.select2)
            this.$axios.post("http://localhost:8082/video/getClassifyByPage", params).then(resp => {
                console.log(resp.data.data);
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
   
<style lang="less">
.mainbody {
    margin-left: 20vh;
    width: 150vh;
    height: 90vh;
    background: #fafafa;
    border: 1px solid #e1e2e5;

    .select {
        display: flex;
        margin-top: 2vh;
        margin-left: 20vh;

        .select2 {
            margin-left: 5vh;
        }

        .select_btn {
            margin-left: 5vh;
        }
    }

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
        margin-left: 50vh;
    }
}
</style>
