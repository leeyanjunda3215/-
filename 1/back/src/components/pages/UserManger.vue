<template>
    <div>
        <div class="tabletop">
            <el-button @click="clearFilter">重置</el-button>
            <el-input class="seach" v-model="search" size="mini" placeholder="输入关键字搜索"
                @keyup.enter.native="handleSelect()" />
        </div>


        <el-table ref="filterTable" :data="tableData" style="width: 100%">
            <el-table-column prop="id" label="id" width="180">
            </el-table-column>

            <el-table-column label="用户头像">
                <template slot-scope="scope">
                    <img :src="scope.row.headimg" alt="" style="width: 5vh;height: 5vh;border-radius: 50%;">
                </template>
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

                    <el-dialog title="个人信息" :visible.sync="dialogFormVisible">
                        <el-form :model="form">

                            <el-form-item label="用户id:">
                                <el-input v-model="form.id" :placeholder="form.id" autocomplete="off"></el-input>
                            </el-form-item>

                            <el-form-item label="用户名:">
                                <el-input v-model="form.name" :placeholder="form.name" autocomplete="off"></el-input>
                            </el-form-item>

                            <el-form-item label="密 码:">
                                <el-input v-model="form.password" :placeholder="form.password"
                                    autocomplete="off"></el-input>
                            </el-form-item>

                            <el-form-item label="年 龄:">
                                <el-input v-model="form.age" :placeholder="form.age" autocomplete="off"></el-input>
                            </el-form-item>

                            <el-form-item label="性 别:">
                                <el-input v-model="form.sex" :placeholder="form.sex" autocomplete="off"></el-input>
                            </el-form-item>

                            <el-form-item label="手机号:">
                                <el-input v-model="form.phone" :placeholder="form.phone" autocomplete="off"></el-input>
                            </el-form-item>

                            <el-form-item label="邮 箱:">
                                <el-input v-model="form.email" :placeholder="form.email" autocomplete="off"></el-input>
                            </el-form-item>

                            <el-form-item label="权 限:">
                                <el-input v-model="form.auth" :placeholder="form.auth" autocomplete="off"></el-input>
                            </el-form-item>

                            <!-- <el-button @click="dialogFormVisible = false">取 消</el-button>
                            <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button> -->

                        </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button @click="dialogFormVisible = false">取 消</el-button>
                            <el-button type="primary" @click="handelUserEdit()">确 定</el-button>
                        </div>
                    </el-dialog>

                    <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>



            <el-table-column prop="auth" label="标签" width="100"
                :filters="[{ text: '用户', value: 'normal' }, { text: '管理员', value: 'admin' }]" :filter-method="filterTag">
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
            form: {}
        }
    },
    mounted() {
        this.queryAll()
    },
    methods: {
        handleSelect() {
            let search = this.search
            this.$axios.post("http://localhost:8082/back/searchUser", { search }).then(resp => {
                if (resp.data.success == true) {
                    this.tableData = resp.data.data
                }
            })
        },
        handelUserEdit() {
            // console.log(i);
            this.dialogFormVisible = false
            console.log(this.form);
            this.$axios.post("http://localhost:8082/back/EditUser", this.form).then(resp => {
                if (resp.data.success == true) {
                    this.queryAll()
                }
            })
        },
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
            this.form = row

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