package com.back.service;

import com.back.dto.LoginFrom;
import com.back.entity.User;
import com.back.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 李焱军
 * @since 2023-01-08
 */
public interface IUserService extends IService<User> {

    Result login(LoginFrom loginFrom, HttpSession session);

    Result register(User user);

    Result logout( Map<String,String> token);

    Result queryVideoUser(Map<String, String> userid);

    Result UserisVideo();

}
