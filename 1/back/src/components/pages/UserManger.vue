<template>
    <div>
        <div class="tabletop">
            <el-button @click="clearFilter">重置</el-button>
            <el-input class="seach" v-model="search" size="mini" placeholder="输入关键字搜索" />
        </div>


        <el-table ref="filterTable" :data="tableData" style="width: 100%">
            <el-table-column prop="id" label="id" width="180">
            </el-table-column>

            <el-table-column prop="name" label="姓名" width="180">
            </el-table-column>

            <el-table-column prop="password" label="密码" width="180">
            </el-table-column>

            <el-table-column prop="age" label="年龄" width="180">
            </el-table-column>

            <el-table-column prop="sex" label="性别" width="180">
            </el-table-column>

            <el-table-column prop="phone" label="手机号" width="180">
            </el-table-column>

            <el-table-column prop="email" label="邮箱" width="230">
            </el-table-column>

            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="mini"
                        @click="handleEdit(scope.$index, scope.row), dialogFormVisible = true">编辑</el-button>
                    <!-- 修改用户信息的表单 -->
                    <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
                        <el-form :model="form">

                            <span>设计中，待定</span>
                            <el-form-item label="用户名" :label-width="formLabelWidth">
                                <el-input v-model="form.name" autocomplete="off" style="width: 200px;"></el-input>
                            </el-form-item>
                       
                        </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button @click="dialogFormVisible = false">取 消</el-button>
                            <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
                        </div>
                    </el-dialog>

                    <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>



            <el-table-column prop="auth" label="标签" width="100"
                :filters="[{ text: '用户', value: 'normal' }, { text: '管理员', value: 'admin' }]"
                :filter-method="filterTag">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.auth === 'normal' ? 'primary' : 'success'" disable-transitions>{{
                        scope.row.auth
                    }}</el-tag>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>

export default {
    name: 'UserManager',
    data() {
        return {
            dialogFormVisible: false,
            tableData: [],
            search: "",
            form:{}
        }
    },
    mounted() {
        this.queryAll()
    },
    methods: {
        clearFilter() {
            this.$refs.filterTable.clearFilter();
        },
        queryAll() {
            this.$axios.get("http://localhost:8082/back/GetALL").then(resp => {
                console.log(resp.data.data);
                this.tableData = resp.data.data
            })
        },
        filterTag(value, row) {
            return row.auth === value;
        },
        handleEdit(index, row) {
            // console.log(row.id);

        },
        handleDelete(index, row) {
            // console.log(index, row);
            console.log(typeof (row.id));
            this.$axios.post("http://localhost:8082/back/deleteUser", row).then(resp => {
                if (resp.data.success == true) {
                    this.$message.success('删除成功');
                    this.queryAll()
                }
            })
        },
    }

}
</script>

<style lang="less" scoped>
.tabletop {
    display: flex;
    align-items: center;
    align-content: center;
    justify-content: space-between;

    .seach {
        width: 400px;
    }
}
</style>