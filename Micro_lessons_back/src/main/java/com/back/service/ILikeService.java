package com.back.service;

import com.back.entity.Liked;
import com.back.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface ILikeService extends IService<Liked> {
    Result like(Map<String, String> id);

    Result isliked(Map<String, String> id);

    Result offlike(Map<String, String> id);

    Result iscollctiond(Map<String, String> id);

    Result collctiond(Map<String, String> id);

    Result collctiondoff(Map<String, String> id);

    Result getlikes();

    Result queryCollections();
}
