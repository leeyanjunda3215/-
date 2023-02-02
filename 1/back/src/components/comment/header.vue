<template>
    <div class="header-container">
        <div class="left-context">
            <el-button size="mini" icon="el-icon-menu" @click="handlerMenu"></el-button>
            <!-- <span class="left-2"> 首页</span> -->
            <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 10px;">
                <el-breadcrumb-item v-for="item in tags" :key="item.path" :to="{path: item.path }" >{{ item.label }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="right-context">
            <el-dropdown>
                <span class="el-dropdown-link">
                    <img class="userimg" src="@/assets/images/user1.png" />
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>个人信息</el-dropdown-item>
                    <el-dropdown-item>退出</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
    name: 'header',
    data() {
        return {

        }
    },
    methods: {
        handlerMenu() {
            //对菜单进行collapse的操作
            this.$store.commit('collapseChange')
        }
    },
    computed:{
        ...mapState({
            tags:state => state.tab.tabsList
        })
    }
}
</script>

<style lang="less" scoped>
.header-container {
    height: 60px;
    background-color: #242f42;
    display: flex; //两个div水平居中
    justify-content: space-between;
    align-items: center;

    .left-context {
        margin-left: 20px;
        display: flex;
        align-items: center;
        /deep/.el-breadcrumb__item{
            .el-breadcrumb__inner{
                font-weight: normal;
                &.is-link{
                color: #666;
                }
            }            
            &:last-child{
                .el-breadcrumb__inner{
                    color: #fff;
                }
            }
        }
    }

    .left-2 {
        color: #fff;
        font-size: 16px;
        margin-left: 20px;
        cursor: pointer;
    }

    .right-context {
        margin-right: 20px;

        .userimg {
            width: 40px;
            height: 40px;
            border-radius: 50%;
        }
    }
}
</style>