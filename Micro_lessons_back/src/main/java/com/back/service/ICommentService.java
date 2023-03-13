package com.back.service;

import com.back.entity.Comment;
import com.back.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface ICommentService extends IService<Comment> {
    Result comment( Map<String,String> comment);

    Result queryALlComment(Map<String,String> id);

    Result relpyByrootid(Map<String, String> id);
}
