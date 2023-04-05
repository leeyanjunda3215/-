package com.back.service;

import com.back.entity.Friend;
import com.back.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 李焱军
 * @since 2023-01-31
 */
public interface IFriendService  extends IService<Friend> {

    Result follow(Map<String, String> friend);

    Result TakeOffFollow(Map<String, String> id);

    Result isfollowed(Map<String, String> id);

    Result querybyid();

    Result queryFollowById();
}
