<template>
    <div>
        <el-row>

            <el-col :span="8">
                <el-card class="box-card">
                    <div class="user">
                        <img class="userimg" src="@/assets/images/user1.png" />
                        <div class="userinfo">
                            <p class="name">Admin</p>
                            <p class="access">超级管理员</p>
                        </div>
                    </div>

                    <div class="login-info">
                        <p>上次登录的时间：<span>2022-10-20</span></p>
                        <p>上次登录的地点：<span>荆门</span></p>
                    </div>
                </el-card>

                <el-card class="box-card" style="margin-top: 20px;">
                    <el-table :data="tableData" stripe style="width: 100%">
                        <el-table-column prop="date" label="日期" width="180">
                        </el-table-column>
                        <el-table-column prop="name" label="姓名" width="180">
                        </el-table-column>
                        <el-table-column prop="address" label="地址">
                        </el-table-column>
                    </el-table>
                </el-card>

                <el-card class="box-card" style="margin-top: 20px;">
                    <!-- 统计图2 -->
                    <div ref="echarts2" style="height: 300px;">

                    </div>
                </el-card>
            </el-col>


            <el-col :span="16" style="padding-left: 20px;">

                <el-card class="box-card system-bulletin">
                    <div slot="header" class="system-title">
                        <span>系统公告</span>
                    </div>
                    <div v-for="item in systemData" :key="item.id" class="system-context">
                        <div>
                            {{ item.title }}
                        </div>

                        <div>
                            <i :class="`el-icon-${item.icon}`">{{ item.data }}</i>
                        </div>
                    </div>
                </el-card>

                <el-card class="box-card" style="margin-top: 20px;">
                    <!-- 统计图 -->
                    <div ref="echarts1" style="height: 300px;">

                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>
<script>
import * as echarts from 'echarts';
export default {
    name: 'start',
    data() {
        return {
            tableData: [{
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-04',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1517 弄'
            }, {
                date: '2016-05-01',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1519 弄'
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄'
            }],
            systemData: [
                {
                    id: 1,
                    data: "2022-10-1 09:00",
                    title: "【内部分享】添加了xx影视",
                    icon: "timer"
                },
                {
                    id: 2,
                    data: "2022-10-1 09:00",
                    title: "【内部分享】添加了xx影视",
                    icon: "timer"
                },
                {
                    id: 3,
                    data: "2022-10-1 09:00",
                    title: "【内部分享】添加了xx影视",
                    icon: "timer"
                },
                {
                    id: 4,
                    data: "2022-10-1 09:00",
                    title: "【内部分享】添加了xx影视",
                    icon: "timer"
                },
                {
                    id: 5,
                    data: "2022-10-1 09:00",
                    title: "【内部分享】添加了xx影视",
                    icon: "timer"
                },
                {
                    id: 6,
                    data: "2022-10-1 09:00",
                    title: "【内部分享】添加了xx影视",
                    icon: "timer"
                },
            ]
        }
    },
    mounted() {
        var myecharts1 = echarts.init(this.$refs.echarts1)
        var myecharts2 = echarts.init(this.$refs.echarts2)

        var option1 = {
            title: {
                text: 'ECharts 入门示例'
            },
            tooltip: {},
            legend: {
                data: ['销量']
            },
            xAxis: {
                data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
            },
            yAxis: {},
            series: [
                {
                    name: '销量',
                    type: 'bar',
                    data: [5, 20, 36, 10, 10, 20]
                }
            ]
        };

        var option2 = {

            title: {
                text: 'Stacked Area Chart'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            legend: {
                data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine']
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: 'Email',
                    type: 'line',
                    stack: 'Total',
                    areaStyle: {},
                    emphasis: {
                        focus: 'series'
                    },
                    data: [120, 132, 101, 134, 90, 230, 210]
                },
                {
                    name: 'Union Ads',
                    type: 'line',
                    stack: 'Total',
                    areaStyle: {},
                    emphasis: {
                        focus: 'series'
                    },
                    data: [220, 182, 191, 234, 290, 330, 310]
                },
                {
                    name: 'Video Ads',
                    type: 'line',
                    stack: 'Total',
                    areaStyle: {},
                    emphasis: {
                        focus: 'series'
                    },
                    data: [150, 232, 201, 154, 190, 330, 410]
                },
                {
                    name: 'Direct',
                    type: 'line',
                    stack: 'Total',
                    areaStyle: {},
                    emphasis: {
                        focus: 'series'
                    },
                    data: [320, 332, 301, 334, 390, 330, 320]
                },
                {
                    name: 'Search Engine',
                    type: 'line',
                    stack: 'Total',
                    label: {
                        show: true,
                        position: 'top'
                    },
                    areaStyle: {},
                    emphasis: {
                        focus: 'series'
                    },
                    data: [820, 932, 901, 934, 1290, 1330, 1320]
                }
            ]
        }

        myecharts1.setOption(option2)
        myecharts2.setOption(option1)
    }
}
</script>

<style lang="less" scoped>
.user {
    padding-bottom: 20px;
    margin-bottom: 20px;
    border-bottom: 1px solid #999;
    display: flex;
    align-items: center;

    img {
        margin-left: 40px;
        width: 150px;
        height: 150px;
        border-radius: 50%;
    }

    .userinfo {
        margin-left: 40px;

        .name {
            font-size: 32px;
            margin-bottom: 10px;
        }

        .access {
            color: #999;
            margin-top: 0px;
        }
    }
}

.login-info {
    p {
        color: #999999;
        font-size: 14px;
        line-height: 28px;

        span {
            color: #666666;
            margin-left: 20px;
        }
    }
}

.system-bulletin {
    .system-title {
        font-size: 22px;
        color: #000000;
    }

    .system-context {
        padding-bottom: 10px;
        border-bottom: 1px solid #f2f2f2;
        margin-bottom: 10px;

        display: flex;
        justify-content: space-between; //两端对齐
        align-items: center;
    }
}
</style>