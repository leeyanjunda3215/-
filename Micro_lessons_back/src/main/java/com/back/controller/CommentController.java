package com.back.controller;

import com.alibaba.fastjson.JSON;
import com.back.service.ICommentService;
import com.back.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private ICommentService commentService;

    @PostMapping("/rootcomment")
    public Result comment(@RequestBody Map<String,String> comment){
        return commentService.comment(comment);
    }

    @PostMapping("/allcomment")
    public Result queryALlComment(@RequestBody Map<String,String> id){
        return commentService.queryALlComment(id);
    }

    @PostMapping("/reply")
    public Result relpyByrootid(@RequestBody Map<String,String> id){
        return commentService.relpyByrootid(id);
    }

}
