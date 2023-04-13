package com.back.service;

import com.back.entity.Message;
import com.back.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 李焱军
 * @since 2023-04-11
 */
@Service
public interface IMessageService  extends IService<Message> {
    Result getchatlist();

    void saveMessage(Message messageList);

    Result getHistoryMessage(Integer id);
}
