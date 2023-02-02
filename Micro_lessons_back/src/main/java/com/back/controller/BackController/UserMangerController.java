package com.back.controller.BackController;

import com.back.entity.User;
import com.back.service.IUserService;
import com.back.util.Result;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/back")
public class UserMangerController {
    @Autowired
    private IUserService userService;

    @GetMapping("/GetALL")
    public Result getAlllUser(){
        return Result.ok(userService.query().eq("is_delete",1).list());
    }


    @PostMapping("/deleteUser")
    public Result DelUser(@RequestBody User user){
        System.out.println(user.getId());

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id",user.getId());
        updateWrapper.set("is_delete",0);
        userService.update(null,updateWrapper);
        return Result.ok();
    }

    @PostMapping("/EditUser")
    public Result EdUser(){

        return Result.ok();
    }


}
