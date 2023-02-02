<template>
    <div class="re_container">

        <div class="re-form">
            <p>用户注册</p>
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                class="demo-ruleForm">

                <el-form-item label="用户名" prop="name">
                    <el-input v-model="ruleForm.name"></el-input>
                </el-form-item>

                <el-form-item label="年龄" prop="age">
                    <el-input v-model.number="ruleForm.age"></el-input>
                </el-form-item>

                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="ruleForm.phone"></el-input>
                </el-form-item>

                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="ruleForm.email"></el-input>
                </el-form-item>

                <el-form-item label="性别" prop="sex">
                    <el-radio-group v-model="ruleForm.sex">
                        <el-radio label="男"></el-radio>
                        <el-radio label="女"></el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="确认密码" prop="checkPass">
                    <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </div>

    </div>
</template>
<script>
export default {
    name: 'login',
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
        //验证年龄
        var checkAge = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('年龄不能为空'));
            }
            setTimeout(() => {
                if (!Number.isInteger(value)) {
                    callback(new Error('请输入数字值'));
                }
            }, 1000);
        };
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.ruleForm.checkPass !== '') {
                    this.$refs.ruleForm.validateField('checkPass');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.ruleForm.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };

        return {
            ruleForm: {},
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
                age: [
                    { validator: checkAge, required: true }
                ],
                password: [
                    { validator: validatePass, trigger: 'blur' }
                ],
                checkPass: [
                    { validator: validatePass2, required: true, trigger: 'blur' }
                ],
            }
        }
    },
    methods: {
        submitForm() {
            console.log(this.ruleForm)
            var thus = this
            this.$axios.post("http://localhost:8082/user/register", thus.ruleForm).then(resp => {
                console.log(resp.data)
                if (resp.data.success == true) {
                    this.$message.success('注册成功');
                    location.href = "http://localhost:8081/#/login"
                }else{
                    this.$message.error(resp.data.errorMsg);
                }

            })
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    }
}

</script>

<style lang="less" scoped>
.re_container {
    width: 100%;
    height: 100vh;
    background-color: rgb(242, 242, 242, 1);
    background-image: url(../assets/login_bg.png);
    background-position: center bottom;
    background-repeat: no-repeat;
    background-attachment: scroll;
    background-size: 1278px 559px;
    background-origin: border-box;
    border: none;
    border-radius: 0px;
    display: flex;
    justify-content: center;
    align-items: center;

    .re-form {
        width: 510px;
        margin: 0px auto;
        padding: 0 55px 15px 35px;
        background-color: #fff;
        border: none;
        border-radius: 5px;
        box-shadow: 0 0 25px #cac6c6;

        p {
            font-family: "微软雅黑 Bold", "微软雅黑";
            font-weight: 700;
            text-decoration: none;
            color: rgb(0, 121, 254);
            font-size: 32px;
            margin-top: 50px;
            margin-bottom: 30px;
            text-align: center;
        }

    }
}
</style>