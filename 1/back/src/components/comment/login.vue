<template>
    <div class="login_container">

        <div class="loginform">
            <p>微课后台</p>
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                class="demo-ruleForm">
                <el-form-item label="账号" prop="age">
                    <el-input v-model.number="ruleForm.age"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="pass">
                    <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
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
        var checkAge = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('账号不能为空'));
            }
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
            } else if (value !== this.ruleForm.pass) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            ruleForm: {
                pass: '',
                checkPass: '',
                age: ''
            },
            rules: {
                pass: [
                    { validator: validatePass, trigger: 'blur' }
                ],
                checkPass: [
                    { validator: validatePass2, trigger: 'blur' }
                ],
                age: [
                    { validator: checkAge, trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    alert('submit!');
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    }
}

</script>

<style lang="less" scoped>
.login_container {
    width: 100%;
    height: 100vh;
    background-color: rgb(242, 242, 242, 1);
    background-image: url(../../assets/login_bg.png);
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
    .loginform {
        width: 510px;
        margin: 0px auto;
        padding: 0 55px 15px 35px;
        background-color: #fff;
        border: none;
        border-radius: 5px;
        box-shadow: 0 0 25px #cac6c6;

        p{
            font-family: "微软雅黑 Bold","微软雅黑";
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