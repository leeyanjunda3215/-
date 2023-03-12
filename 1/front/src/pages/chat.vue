<template>
    <el-main>

        <div class="box-card" :body-style="{ padding: '0px' }">

            <div class="out-box-right">
                <div class="box-right ">
                    <div v-for="i in chatuser">
                        <div id="person" class="person" @click="choose(i.id)">

                            <img :src="i.headimg" alt="">
                            <div>
                                <p>{{ i.name }}</p>
                            </div>

                        </div>
                    </div>

                </div>
            </div>




            <div class="box-left">
                <div class="chatwindows">
                    <div></div>
                    <div>
                        <div class="me" v-for="i in me">
                            <div class="me_content">
                                <p>{{ i }}</p>
                            </div>
                            <div class="me_img">
                                <img :src="user.headimg" alt="">
                            </div>
                            <div>

                            </div>
                        </div>
                        <div class="other" v-for="i in getmessage">
                            <div class="other_img">
                                <img :src="chatuser.headimg" alt="">
                            </div>
                            <div class="other_content">
                                <p>{{ i }}</p>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="chatinput">
                    <el-input v-model="input" placeholder="请输入内容" @keyup.enter.native="sendMessage()">
                    </el-input>
                    <div class="btn_send">
                        <el-button type="primary" icon="el-icon-message" circle @click="sendMessage()">发送</el-button>
                    </div>

                </div>
            </div>


        </div>

    </el-main>
</template>
  
<script>


export default {
    name: "chat",
    data() {
        return {
            // 聊天用户列表
            chatuser: [],
            //  用户
            user: [],
            // 输入的内容
            input: "",
            // 收到来自服务器的信息
            getmessage: [],
            // 当前用户发送的信息
            me: [],
            // 发送用户的id
            sendto: [],
            // 连接路径
            path: "ws://localhost:8082/chat/",
            // wbsocket 实例
            socket: "",
        };
    },
    created() {
        var temp = this.$store.state.tab.user
        if (!(temp.length == 0)) {
            this.getuser()
        }
        if (temp.length == 0) {
            this.user = JSON.parse(sessionStorage.getItem("user"))
        }
        this.getchatwith()
        this.initWebsocket()

    },
    methods: {
        getchatwith() {
            // var user =  this.$store.state.tab.chatuser
            var user = JSON.parse(localStorage.getItem("chatwith"))
            // console.log(user);
            this.chatuser.push(user)
            if (user.id == this.user.id) {
                this.chatuser.pop(user)
            }

        },
        getuser() {
            this.user = this.$store.state.tab.user
            sessionStorage.setItem("user", JSON.stringify(this.user))
        }
        ,
        initWebsocket() {
            // var user = this.$store.state.tab.user
            var sendto = this.chatuser[0]
            // console.log(sendto);
            // console.log(sendto.id);
            var path = this.path + this.user.id+"/"+sendto.id
            console.log(path);

            this.socket = new WebSocket(path)
            // 监听websocket的事件
            // websocket连接成功的时候触发
            this.socket.onopen = this.open

            // 当websocket连接关闭的时候触发
            this.socket.onclose = this.onclose

            // 当websocket连接发生错误的时候触发
            this.socket.onerror = this.onerror

            // 收到来自服务器的消息时触发
            this.socket.onmessage = this.onmessage
        },
        // websocket连接成功的时候触发
        open() {
            console.log("websocket连接成功");
            
            // 建立联系后 显示聊天的对象
            // console.log(this.chatuser);
            // 获取用户
            // console.log(this.user);
        },
        // 收到来自服务器的消息时触发
        onmessage(msg) {
            // console.log(msg);
            
            this.getmessage.push(msg.data)
            console.log("服务端", msg.data)
        },
        // 当websocket连接关闭的时候触发
        onclose() {
            console.log("websocket关闭");
        },
        // 当websocket连接发生错误的时候触发
        onerror() {
            console.log("websocket连接发生错误");

        },
        // 向服务器发送消息
        sendMessage() {
            // 发送消息的用户
            // var user = this.user
            // 被发送消息的用户
            var sendto = this.chatuser[0]
            // console.log(user.id);
            // console.log(sendto.id);
            if (this.input == "") {
                this.$message.error("请不要输入空值");
            } else {
                // console.log(sendto.id);
                // 获取输入的内容
                var message = this.input
                 // 发送的消息格式
                var json = {"toId":sendto.id,"message":message};
                // 发送内容到服务端
                this.socket.send(JSON.stringify(json))
                // 将内容循环到页面
                this.me.push(message)
                // 重置内容
                this.input = ""
             
            }

        },
        // 点击用户框，选择私信用户
        choose(item) {
            this.sendto = item;
            var btn = document.getElementById("person")
            btn.style.backgroundColor = "#E6A23C"
        }


    }
}
</script>
  
<style lang="less" scoped>
.box-card {
    margin-top: 1%;
    margin-left: 10%;
    width: 160vh;
    height: 80vh;
    border: 1px solid #000;
    display: flex;
    overflow: hidden;

    // justify-content: space-between;
    .out-box-right {
        .box-right::-webkit-scrollbar {
            display: none;
        }

        .box-right {
            width: 30vh;
            height: 80vh;
            padding: 0px;
            overflow: auto;

            .person {
                margin-top: 1vh;
                margin-left: 1vh;
                width: 25vh;
                height: 10vh;
                border: 1px solid #000;
                border-radius: 5%;
                display: flex;

                img {
                    margin-top: 2vh;
                    margin-left: 2vh;
                    width: 6vh;
                    height: 6vh;
                    border-radius: 50%;
                }

                p {
                    margin-top: 3vh;
                    margin-left: 2vh;
                    font-size: 2vh;
                    font-weight: 500;
                }
            }

            .person:hover {
                background-color: #409EFF;
            }
        }
    }


    .box-left {
        // margin-left: 2vh;
        width: 130vh;
        height: 80vh;
        border-left: 1px solid #000;

        .chatwindows::-webkit-scrollbar {
            display: none;
        }

        // border: 1px solid #000;
        .chatwindows {
            width: 130vh;
            height: 70vh;
            // background-color: aqua;
            overflow: auto;

            .me {
                margin-top: 2vh;
                margin-left: 87vh;
                width: 40vh;
                height: auto;
                display: flex;

                // background-color:#409EFF;
                .me_content {
                    width: 40vh;
                    height: auto;
                    p {
                        float: right;
                        font-size: 27px;
                    }
                }

                .me_img {

                    // background-color: #000;
                    img {
                        margin-left: 3vh;
                        width: 6vh;
                        height: 6vh;
                        border-radius: 50%;
                    }

                }
            }

            .other {
                margin-top: 2vh;
                margin-left: 2vh;
                width: 40vh;
                height: auto;
                display: flex;

                .other_content {
                    margin-left: 1vh;
                    width: 30vh;
                    height: auto;

                    p {
                        font-size: 27px;
                        word-wrap: break-word;
                        word-break: break-all;
                    }
                }

                .other_img {
                    img {
                        width: 6vh;
                        height: 6vh;
                        border-radius: 50%;
                    }

                }
            }
        }

        .chatinput {
            margin-left: 25vh;
            width: 80vh;
            height: 30vh;
            display: flex;
            border-radius: 5%;

            .btn_send {
                margin-left: 2vh;
            }

            el-input {
                height: 25vh;


            }
        }
    }
}</style>