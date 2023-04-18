<template>
    <el-main>
        <div class="upload">
            <div class="steps">
                <el-steps :active="active" finish-status="success">
                    <el-step title="步骤 1, 视频基本信息">

                    </el-step>
                    <el-step title="步骤 2, 视频封面，上传视频">
                    </el-step>

                    <el-step title="步骤 3, 上传完成">
                    </el-step>
                </el-steps>
            </div>


            <div v-if="active == 0">
                <el-form ref="videoform" :model="videoform" label-width="80px">
                    <el-form-item label="视频名称">
                        <el-input v-model="videoform.vName"></el-input>
                    </el-form-item>
                    <el-form-item label="视频类别">
                        <el-cascader :options="options" v-model="videoform.classify" clearable></el-cascader>
                    </el-form-item>

                    <el-form-item label="视频简介">
                        <el-input type="textarea" v-model="videoform.brief"></el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">投稿</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>
            </div>

            <!-- action="http://localhost:8082/video/getVideoCover"  -->
            <div v-if="active == 1">
                <h3>上传视频封面：</h3>
                <el-upload :http-request="uploadimg" :limit="1" :on-success="uploadSuccess" accept=".png"
                    list-type="picture-card">
                    <i class="el-icon-plus"></i>
                    <div class="el-upload__tip" slot="tip">上传视频封面, 且不超过500kb</div>
                </el-upload>
                <div style="width: 20px;height: 20px;">

                </div>
                <h3>上传视频：</h3>
                <!-- <el-upload multiple class="upload-demo" drag :http-request="uploadvide" accept=".mp4">
                    <i class="el-icon-plus"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                    <div class="el-upload__tip" slot="tip">上传视频文件, 不超过2G</div>
                </el-upload> -->

                <el-upload class="upload-demo" drag :http-request="uploadvide" accept=".mp4">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                    <div class="el-upload__text">传视频文件, 大小不超过2G</div>
                </el-upload>

            </div>

            <div v-if="active == 2">
                <el-result icon="success" title="成功提示" subTitle="请根据提示进行操作">
                    <template slot="extra">
                        <el-button type="primary" size="medium" @click="Gotohome()">返回</el-button>
                    </template>
                </el-result>
            </div>

        </div>

    </el-main>
</template>
<script>
export default {
    name: 'upload',
    data() {
        return {
            // 步骤条
            active: 0,
            // 视频分类
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
                            value: "专用用途英语",
                            label: "专用用途英语"
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
            videoform: {
                // 上传的视频信息
            },
            // 视频封面
            formData: new FormData(),
            // 视频
            formvideo: new FormData(),
            arrvideo: [],
            value: 0,
            //文件上传的路径
            uploadUrl: "http://localhost:8082/video/getVideo" //文件上传的路径

        }
    },
    methods: {
        Gotohome() {
            location.href = 'http://localhost:8081/';
        },
        uploadimg(param) {
            this.arrvideo.push(this.videoform.vName)
            this.arrvideo.push(this.videoform.classify)
            this.formData.append("file", param.file)
            this.formData.append("videoform", this.arrvideo)
            this.$axios({
                method: "post",
                url: "http://localhost:8082/video/getVideoCover",
                data: this.formData
            }).then(resp => {

            })
        },
        uploadvide(param) {
            this.arrvideo.push(this.videoform.vName)
            this.arrvideo.push(this.videoform.classify)
            this.formvideo.append("file", param.file)
            console.log(this.videolist);
            this.formvideo.append("videoform", this.arrvideo)
            this.$axios({
                method: "post",
                url: "http://localhost:8082/video/getVideo",
                data: this.formvideo
            }).then(resp => {
                if (resp.data.success == true) {
                    this.active++
                }
            })
            // 区块链 上传 视频名称
            // this.$axios.get("http://192.168.21.1:8082/fisco/set?val=" + this.videoform.vName)
        },
        onSubmit() {
            this.active++
            this.$axios.post("http://localhost:8082/video/getVideoInfo", this.videoform).then(resp => {
            })
        }
    }
}

</script>

<style lang="less" scoped>
.upload {
    margin-left: 20%;
    margin-right: 40%;
    margin-top: 3%;

    .steps {
        margin-bottom: 10%;
    }
}
</style>