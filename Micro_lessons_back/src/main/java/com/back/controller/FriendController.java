package com.back.controller;

import com.back.entity.User;
import com.back.service.IFriendService;
import com.back.util.Result;
import com.back.util.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 李焱军
 * @since 2023-01-31
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Resource
    private IFriendService friendService;

    @PostMapping("/follow")
    public Result follow(@RequestBody Map<String,String> friend){
        return friendService.follow(friend);
    }

    @PostMapping("/takeoff")
    public Result TakeOffFollow(@RequestBody Map<String,String> id){
        return friendService.TakeOffFollow(id);
    }

    @PostMapping("/isfollowed")
    public Result isfollowed(@RequestBody Map<String,String> id){
        return friendService.isfollowed(id);
    }

    @GetMapping("/querybyid")
    public Result queryByuserId(){
        return friendService.querybyid();
    }

}
