package com.back.service.impl;


import com.back.dto.LoginFrom;
import com.back.entity.User;
import com.back.mapper.UserMapper;
import com.back.service.IUserService;
import com.back.util.Result;
import com.back.util.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 李焱军
 * @since 2023-01-08
 */

@Service

public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    IUserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(LoginFrom loginFrom, HttpSession session) {
        //检查账号密码
        if (loginFrom.getName() == null || loginFrom.getPassword()==null){
            return Result.fail("账号或者密码为空");
        }
        //去数据库查询 密码 用户名   select * from tb_user where name="#name"
        User name = query().eq("is_delete",1).eq("name", loginFrom.getName()).one();
        if (name == null){
            return Result.fail("没有此用户");
        }
        User pass = query().eq("is_delete",1).eq("name", loginFrom.getName()).eq("password", loginFrom.getPassword()).one();
        if (pass == null){
            return Result.fail("密码错误");
        }
        // 存在就将用户查询出来
        User user = query().eq("is_delete",1).eq("name", loginFrom.getName()).eq("password", loginFrom.getPassword()).one();

        //将user对象转为Map对象
        Map<String, Object> userMap =  new HashMap<>();
        userMap.put("id",Integer.toString(user.getId()));
        userMap.put("age",Integer.toString(user.getAge()));
        userMap.put("isDelete",Integer.toString(user.getIsDelete()));
        userMap.put("password",user.getPassword());
        userMap.put("auth",user.getAuth());
        userMap.put("phone",user.getPhone());
        userMap.put("sex",user.getSex());
        userMap.put("name",user.getName());
        userMap.put("email",user.getEmail());
        userMap.put("headimg",user.getHeadimg());
        //将 用户 存入redis
        // 生成一个token
        String token = UUID.randomUUID().toString(); //16位随机数
        //将token和用户Map 存入redis
        stringRedisTemplate.opsForHash().putAll("login:token"+token,userMap);
        //设置token有效期
        stringRedisTemplate.expire("login:token"+token,30, TimeUnit.MINUTES);
        //返回token
        return Result.ok(token);
    }

    @Override
    public Result register(User user) {
        if (user.getName() == null || user.getPassword() == null || user.getEmail() == null || user.getPhone()==null){
            return Result.fail("用户名或者密码为空");
        }
        User name = userService.query().eq("is_delete",1).eq("name", user.getName()).one();
        if ( !( name == null) ){
            return Result.fail("用户已经存在");
        }

        User phone = userService.query().eq("is_delete",1).eq("phone", user.getPhone()).one();
        if ( !( phone == null) ){
            return Result.fail("手机号已经存在");
        }

        User email = userService.query().eq("is_delete",1).eq("email", user.getEmail()).one();
        if ( !( email == null) ){
            return Result.fail("邮箱已经存在");
        }
        userService.save(user); //注册 到数据库中
        return Result.ok();
    }

    @Override
    public Result logout(Map<String,String> token) {
//        System.out.println("logout的token:"+token.get("token"));
        Boolean flag = stringRedisTemplate.delete("login:token" + token.get("token"));
        return Result.ok();
    }

    @Override
    public Result queryVideoUser(Map<String, String> userid) {
        String id = userid.get("userid");
        User user = userService.query().eq("is_delete",1).eq("id", id).one();

        return Result.ok(user);
    }

    @Override
    public Result UserisVideo() {
        Integer id = UserHolder.getUser().getId();
        return Result.ok(id);
    }


}
