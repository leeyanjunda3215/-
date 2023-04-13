package com.back.controller;

import com.alibaba.fastjson2.JSONObject;
import com.back.dto.MessageDto;
import com.back.entity.Message;
import com.back.service.IMessageService;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.concurrent.ConcurrentHashMap;


@Component
@ServerEndpoint(value = "/chat/{userId}/{sendTo}")
public class SocketController {
    //    注入消息服务类
    @Resource
    private IMessageService messageService;

    /**
     * 用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, SocketController> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 线程安全的Set 用于存储还没登录的用户的消息
     */
    private static ConcurrentHashMap<String, String> messageMap = new ConcurrentHashMap<>();
    //    与客户端的连接会话，用来给客户端发送数据
    private Session session;

    /**
     * 接收userId
     */
    private String userId = "";


    /**
     * 连接成功后自动调用
     *
     * @param session
     * @return
     */
    @OnOpen
    public void OnOpen(Session session, @PathParam("userId") String userId) throws IOException {
        this.session = session;
        this.userId = userId;

        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
        } else {
            webSocketMap.put(userId, this);
        }
//      登录后将 其他用户发送给他的消息 发送给这个用户
        if (messageMap.containsKey(userId)) {
//      对应的消息
            String data = messageMap.get(userId);
            webSocketMap.get(userId).session.getBasicRemote().sendText(data);
        }
        System.out.println("当前用户id" + userId);
        System.out.println("websocket连接成功");
    }


    /**
     * 收到客户端发送的数据时调用
     *
     * @param message
     * @param session
     */

    @OnMessage
    public void OnMessage(String message, Session session) {
        System.out.println("收到的消息：" + message);
        try {
            //将message转化成Message对象
            ObjectMapper mapper = new ObjectMapper();
            MessageDto mess = mapper.readValue(message, MessageDto.class);
//          获取要将数据发送给的用户
            String toId = mess.getToId();
            //获取消息数据
            String data = mess.getMessage();
//            将发送消息存入数据库
//            加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
//          获取数据连接
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/microlessonsback", "root", "liyanjun");
//            操作数据库，实现增
//            执行sql
            String sql = "insert into message(u_id,to_id,message) values(?,?,?)";
           PreparedStatement stmt = conn.prepareStatement(sql);
//           传参
            stmt.setString(1, this.userId);
            stmt.setString(2, toId);
            stmt.setString(3, data);
            stmt.execute();
            System.out.println(sql);
//            将消息转为JSON格式
            String messageJSON = JSONObject.toJSONString(mess);
            System.out.println("用户：" + this.userId + "->发送给用户：" + toId + "=>消息是：" + data);
//          判断 接收的用户 是否登录
//          如果 接收的用户 没有登录
            if (!webSocketMap.containsKey(toId)) {
//                    将消息存入消息队列 rabbitmq
//                System.out.println("存消息："+data+"用户是："+toId);
                messageMap.put(toId, messageJSON);
            } else {
                //          当 接收的用户 登录
//         获取推送给指定用户的消息格式的数据
                webSocketMap.get(toId).session.getBasicRemote().sendText(messageJSON);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭时调用
     *
     * @param session
     */
    @OnClose
    public void OnClose(Session session) {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
        }
    }

    /**
     * 发送自定义消息
     *
     * @param message
     * @param userId
     * @throws IOException
     */
    public static void sendInfo(String message, @PathParam("userId") String userId, @PathParam("sendTo") String sendTo) throws IOException {
        webSocketMap.get(userId).sendMessage(message);
    }

    /**
     * 实现服务器主动推送
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


}
