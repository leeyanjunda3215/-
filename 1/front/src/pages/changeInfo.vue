<template>
    <el-main>
        <div class="personInfo">
            <div class="info">
                <el-form ref="form" :model="user" label-width="80px">
                    <div style="display: flex; justify-content: center;margin-top: 20px;margin-bottom: 20px;">
                        <div class="headimg"><img :src="user.headimg" alt=""></div>
                        <div style="width: 100px; text-align: center;">
                            <el-divider direction="vertical"></el-divider>
                        </div>
                        <el-upload :http-request="uploadimg" :limit="1" accept=".png,.jpg" list-type="picture-card">
                            <i class="el-icon-plus"></i>
                            <div class="el-upload__tip" slot="tip">下次登录的时候显示</div>
                        </el-upload>
                    </div>
                    <el-form-item label="用户名">
                        <el-input v-model="user.name" style="width: 300px;"></el-input>
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input v-model="user.password" type="password" style="width: 300px;"></el-input>
                    </el-form-item>
                    <el-form-item label="电话">
                        <el-input v-model="user.phone" style="width: 300px;"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱">
                        <el-input v-model="user.email" style="width: 300px;"></el-input>
                    </el-form-item>
                    <el-form-item style="margin-left: 50vh;">
                        <el-button type="primary" @click="onSubmit"> &nbsp&nbsp&nbsp&nbsp保存&nbsp&nbsp&nbsp&nbsp </el-button>
                    </el-form-item>
                </el-form>
            </div>

        </div>
    </el-main>
</template>


<script>

export default {
    name: 'changeInfo',
    data() {
        //验证邮箱
        var reg_email = (rule, value, callback) => {
            const reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
            if (!value) {
                callback(new Error('请输入邮箱'));
            } else {
                let check = reg.test(value);
                if (!check) {
                    callback(new Error('邮箱格式错误'))
                } else {
                    return true,
                        callback()
                }
            }
        }


        return {
            rules: {
                phone: [
                    { required: true, message: '请输入手机号码', trigger: 'blur' },
                    { min: 11, max: 11, message: '请输入11位手机号码', trigger: 'blur' },
                    {
                        pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/,
                        message: '请输入正确的手机号码'
                    }
                ],
                email: [
                    { validator: reg_email, required: true }
                ],
            },
            user: [],
            // 视频封面
            formData: new FormData(),
        }
    },
    mounted() {
        this.queryUser()
    },
    methods: {
        queryUser() {
            this.$axios.get("http://localhost:8082/user/me").then(resp => {
                //保存用户
                if (resp.data.success = true) {
                    this.user = resp.data.data
                }

            })
        },
        onSubmit() {
            this.$axios.post("http://localhost:8082/user/updataUser", this.user).then(resp => {
                if (resp.data.success == true) {
                    location.href = "http://localhost:8081/#/login"
                }
            })
        },
        uploadimg(param) {
            this.formData.append("file", param.file)
            this.$axios({
                method: "post",
                url: "http://localhost:8082/user/updateUserHeadImg",
                data: this.formData
            }).then(resp => {
                this.$message.success = '图像保存成功'
            })
        },
    }
}
</script>
   
<style lang="less" scoped>
.personInfo {
    width: 100%;
    height: 100%;

    .info {
        margin-left: 20%;
        width: 60%;
        height: 100vh;
        background: #fafafa;
        border: 1px solid #e1e2e5;
    }

    // background-color: aqua;
    .headimg {
        padding: 2vh;
        width: 15vh;
        height: 15vh;


        img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
        }
    }
}

.el-form-item {
    margin-left: 40vh;
}

.el-divider--vertical {
    height: 171px !important;

}
</style>
