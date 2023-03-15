package com.back.controller;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.back.dto.LoginFrom;
import com.back.entity.User;
import com.back.service.IUserService;
import com.back.service.IVideoService;
import com.back.util.Result;
import com.back.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 李焱军
 * @since 2023-01-08
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 用户登录
     *
     * @param loginFrom
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginFrom loginFrom, HttpSession session) {
        return userService.login(loginFrom, session);
    }

    @GetMapping("/me")
    public Result me() {
        User user = UserHolder.getUser();
//        System.out.println("接口/user/me:  "+user);
        return Result.ok(user);
    }

    @PostMapping("/logout")
    public Result Logout(@RequestBody Map<String, String> token) {
        return userService.logout(token);
    }


    @PostMapping("/videoUser")
    public Result queryVideoUser(@RequestBody Map<String, String> userid) {
        return userService.queryVideoUser(userid);
    }


    @GetMapping("/UserisVideo")
    public Result UserisVideo() {
        return userService.UserisVideo();
    }

    @PostMapping("/updateUserHeadImg")
    public Result updateUserHeadImg(@RequestParam("file") MultipartFile file) throws IOException {
        return userService.updateUserHeadImg(file);
    }

    @PostMapping("/updataUser")
    public Result updateUser(@RequestBody User user) {
        return userService.updataUser(user);
    }


}
