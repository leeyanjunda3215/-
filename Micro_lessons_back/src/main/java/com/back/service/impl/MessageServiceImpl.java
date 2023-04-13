package com.back.service.impl;

import com.back.entity.Message;
import com.back.entity.User;
import com.back.entity.messageHistory;
import com.back.mapper.MessageMapper;
import com.back.service.IMessageService;
import com.back.util.Result;
import com.back.util.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 李焱军
 * @since 2023-4-11
 */

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService{

    @Resource
    private IMessageService messageService;

    @Resource
    private MessageMapper messageMapper;

    @Override
    public Result getchatlist() {
        // 获取当前用户id
        Integer id = UserHolder.getUser().getId();
        // 根据id 查了聊天列表
        List<User> userList = messageMapper.queryMessageUserList(id);
        return Result.ok(userList);
    }

    @Override
    public void saveMessage(Message messageList) {
        messageService.save(messageList);
    }

    @Override
    public Result getHistoryMessage(Integer id) {
//        获取当前用户
        Integer userid = UserHolder.getUser().getId();
//        查询对应的聊天记录
        List<Message> meMessageList = messageService.query().select("message")
                .eq("is_delete", 1)
                .eq("u_id", userid)
                .eq("to_id", id)
                .ne("message","")
                .list();
        List<Message> otherMessageList = messageService.query().select("message")
                .eq("is_delete", 1)
                .eq("u_id", id)
                .eq("to_id", userid)
                .ne("message","").list();

        //        转为前端对应的格式
        List<messageHistory> messageHistoryList = new ArrayList<>();

        for (Message i:meMessageList) {
            messageHistory meHistory = new messageHistory();
            String message = i.getMessage();
            meHistory.setType("me");
            meHistory.setMessage(message);
            messageHistoryList.add(meHistory);
        }

        for (Message i:otherMessageList) {
            messageHistory otherHistory = new messageHistory();
            String message = i.getMessage();
            otherHistory.setType("other");
            otherHistory.setMessage(message);
            messageHistoryList.add(otherHistory);
        }
        System.out.println(messageHistoryList);
        return Result.ok(messageHistoryList);
    }
}
