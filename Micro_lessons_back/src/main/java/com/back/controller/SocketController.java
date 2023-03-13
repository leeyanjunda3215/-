package com.back.controller;

import com.back.config.WebSocketConfig;
import com.back.dto.Message;
import com.back.entity.User;
import com.back.util.Result;
import com.back.util.UserHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Component
@ServerEndpoint(value = "/chat/{userId}/{sendTo}")
public class SocketController {
    /**用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String,SocketController> webSocketMap = new ConcurrentHashMap<>();
//    与客户端的连接会话，用来给客户端发送数据
    private Session session;

    /**接收userId*/
    private String userId="";

    /**
     * 连接成功后自动调用
     * @param session
     * @return
     */
    @OnOpen
    public void OnOpen(Session session, @PathParam("userId") String userId){
        this.session =session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
        }else{
            webSocketMap.put(userId,this);
        }
        System.out.println("当前用户id"+userId);
        System.out.println("websocket连接成功");
    }


    /**
     * 收到客户端发送的数据时调用
     * @param message
     * @param session
     */
    @OnMessage
    public void OnMessage(String message,Session session){
        System.out.println("收到的消息："+message);
      try{
          //将message转化成Message对象
          ObjectMapper mapper= new ObjectMapper();
          Message mess = mapper.readValue(message, Message.class);
//          获取要将数据发送给的用户
          String toId = mess.getToId();
//          获取消息数据
          String data = mess.getMessage();
          System.out.println("用户："+this.userId+"->发送给用户："+toId+"=>消息是："+data);
//         获取推送给指定用户的消息格式的数据
          webSocketMap.get(toId).session.getBasicRemote().sendText(data);
//          sendInfo(message,this.userId,toId);
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    /**
     * 连接关闭时调用
     * @param session
     */
    @OnClose
    public void OnClose(Session session){
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
        }
    }

    /**
     * 发送自定义消息
     * @param message
     * @param userId
     * @throws IOException
     */
    public static void sendInfo(String message,@PathParam("userId") String userId,@PathParam("sendTo") String sendTo) throws IOException {
        webSocketMap.get(userId).sendMessage(message);
    }

    /**
     * 实现服务器主动推送
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


}
