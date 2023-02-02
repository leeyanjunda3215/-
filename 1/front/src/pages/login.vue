<template>
    <div class="login_container">

        <div class="loginform">
            <p>登录</p>
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                class="demo-ruleForm">
                <el-form-item label="账号" prop="name">
                    <el-input v-model.number="ruleForm.name"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
                </el-form-item>
      
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                    <el-button @click="Gotoregister()">注册</el-button>
                </el-form-item>
            </el-form>
        </div>

    </div>
</template>
<script>
import store from '../store/tab'
export default {
    name: 'login',
    data() {
        var checkName = (rule, value, callback) => {
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

        return {
            ruleForm: {},
            rules: {
                pass: [
                    { validator: validatePass, trigger: 'blur' }
                ],
        
                name: [
                    { validator: checkName, trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        submitForm() {
            var thus = this
            this.$axios.post("http://localhost:8082/user/login", thus.ruleForm)
            .then(resp => {
                if (resp.data.success == true) {
                    this.$message.success('登录成功');
                    // console.log(resp.data.data)
                    //对token的处理
                    sessionStorage.setItem("token",resp.data.data)
                    location.href = "http://localhost:8081"
                }else{
                    this.$message.error(resp.data.errorMsg);
                }

            })
        },
        Gotoregister(){
            location.href = 'http://localhost:8081/#/register';
        }
    }
}

</script>

<style lang="less" scoped>
.login_container {
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