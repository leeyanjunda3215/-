package com.back.controller;


import com.back.service.IMessageService;
import com.back.util.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 李焱军
 * @since 2023-04-11
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private IMessageService messageService;

    @GetMapping("/getchatlist")
    public Result getchatlist(){
        return messageService.getchatlist();
    }
    @PostMapping("/getHistoryMessage")
    public Result getHistoryMessage(@Param("id") Integer id){
        return messageService.getHistoryMessage(id);
    }
}
