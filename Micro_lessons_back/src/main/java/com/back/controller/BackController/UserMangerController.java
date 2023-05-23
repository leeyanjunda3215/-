package com.back.controller.BackController;

import com.back.entity.User;
import com.back.entity.Video;
import com.back.service.IUserService;
import com.back.service.IVideoService;
import com.back.util.Result;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/back")
public class UserMangerController {
    @Autowired
    private IUserService userService;

    @GetMapping("/GetALL")
    public Result getAlllUser() {
        return Result.ok(userService.query().eq("is_delete", 1).list());
    }


    @PostMapping("/deleteUser")
    public Result DelUser(@RequestBody User user) {
        System.out.println(user.getId());

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", user.getId());
        updateWrapper.set("is_delete", 0);
        userService.update(null, updateWrapper);
        return Result.ok();
    }

    @PostMapping("/EditUser")
    public Result EdUser(@RequestBody User user) {
//        获取用户的id
        System.out.println("修改的用户： " + user);
        Integer userId = user.getId();
        UpdateWrapper wrapper = new UpdateWrapper<>();
        wrapper.eq("id", userId);

        String phone = user.getPhone();
        wrapper.set("phone", phone);

        String name = user.getName();
        wrapper.set("name", name);

        String password = user.getPassword();
        wrapper.set("password", password);

        String email = user.getEmail();
        wrapper.eq("id", userId);

        Integer age = user.getAge();
        wrapper.set("age", age);

        String auth = user.getAuth();
        wrapper.set("auth", auth);

        String sex = user.getSex();
        wrapper.set("sex", sex);

        userService.update(wrapper);
        return Result.ok();
    }

    @Resource
    private IVideoService videoService;
    @GetMapping("/getAllvideo")
    public Result getAllvideo(){
        List<Video> videos = videoService.query().eq("is_delete", 1).list();
        return Result.ok(videos);
    }

    @PostMapping("/deleteVideo")
    public Result deleteVideo(@RequestBody Video video){
        Integer vid = video.getvId();
        System.out.println("视频的id:"+vid);
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.eq("v_id",vid);
        wrapper.set("is_delete",0);
        videoService.update(wrapper);
        return Result.ok();
    }

    @PostMapping("/searchUser")
    public Result searchUser(@RequestBody Map<String,String> search){
//        System.out.println("搜索: "+search);
        String content = search.get("search");
        System.out.println("搜索: "+content);
        List<User> userList = userService.query().eq("is_delete",1).eq("name", content).list();
        return Result.ok(userList);
    }

    @PostMapping("/searchVideo")
    public Result searchVideo(@RequestBody Map<String,String> search){
//        System.out.println("搜索: "+search);
        String content = search.get("search");
        System.out.println("搜索: "+content);
        List<Video> videoList = videoService.query().eq("is_delete",1).eq("v_name", content).list();
        return Result.ok(videoList);
    }

}
