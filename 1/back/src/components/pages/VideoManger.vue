<template>
    <div>
        <div class="tabletop">
            <el-button @click="clearFilter">重置</el-button>
            <el-input class="seach" v-model="search" size="mini" placeholder="输入关键字搜索" @keyup.enter.native="handleSelect()"
                style="width: 20vh;" />
        </div>


        <el-table ref="filterTable" :data="video" style="width: 100%">
            <el-table-column prop="vId" label="视频ID" width="180">
            </el-table-column>

            <el-table-column prop="vName" label="视频名称" width="180">
            </el-table-column>

            <el-table-column prop="userId" label="视频作者ID" width="180">
            </el-table-column>

            <el-table-column label="视频封面" width="180">
                <template slot-scope="scope">
                    <img :src="scope.row.cover" alt="" style="width: 15vh;height: 10vh;">
                </template>
            </el-table-column>

            <el-table-column prop="tag" label="视频标签" width="180">
            </el-table-column>

            <el-table-column prop="times" label="视频观看次数" width="180">
            </el-table-column>


            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>



            <el-table-column prop="classify" label="标签" width="100" :filters="[{ text: '计算机', value: '计算机' },
            { text: '外语', value: '外语' },
            { text: '理工农', value: '理工农' },
            { text: '经济管理', value: '经济管理' },
            { text: '文史哲法', value: '文史哲法' },
            { text: '教育教学', value: '教育教学' },
            { text: '音乐艺术', value: '音乐艺术' }]" :filter-method="filterTag">
                <template slot-scope="scope">
                    <el-tag disable-transitions>{{
                        scope.row.classify
                    }}</el-tag>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>
<script>
export default {
    name: 'VideoManger',
    data() {
        return {
            search: "",
            video: {}
        }
    },
    mounted() {
        this.queryAllVideo()
    },
    methods: {
        handleSelect() {
            let search = this.search
            this.$axios.post("http://localhost:8082/back/searchUser", { search }).then(resp => {
                if (resp.data.success == true) {
                    this.video = resp.data.data
                }
            })
        },
        handleDelete(i, row) {
            console.log("删除");
            console.log(row);
            this.$axios.post("http://localhost:8082/back/deleteVideo", row).then(resp => {
                // this.queryAllVideo()
                if (resp.data.success == true) {
                    this.queryAllVideo()
                }
            })

        },
        clearFilter() {
            this.$refs.filterTable.clearFilter();
        },
        filterTag(value, row) {
            return row.auth === value;
        },
        queryAllVideo() {
            this.$axios.get("http://localhost:8082/back/getAllvideo").then(resp => {
                if (resp.data.success == true) {
                    this.video = resp.data.data

                }
            })
        }
    }
}
</script>