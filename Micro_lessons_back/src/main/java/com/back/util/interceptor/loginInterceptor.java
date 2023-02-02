package com.back.util.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.back.entity.User;
import com.back.util.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class loginInterceptor implements HandlerInterceptor {


    private StringRedisTemplate stringRedisTemplate;

    //使用构造函数注入
    public loginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 获取请求头中的token
        String token = request.getHeader("authorization");
//        System.out.println("拦截器取到的的token: "+token);
        if (token == null || token.equals("")){
            //不存在，拦截 ,401 是未授权
            response.setStatus(401);
            return false;
        }
        //基于token 获取redis的用户
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries("login:token" + token);
        //3.判断用户是否存在
        if (userMap.isEmpty()){
            //不存在，拦截 ,401 是未授权
            response.setStatus(401);
            return false;
        }

        //将查询到的Hash数据，转化为user对象
        User user = JSONObject.parseObject(JSONObject.toJSONString(userMap),User.class);
//        System.out.println(user);

        //存在，保存到Threadlocal
        UserHolder.saveUser((User) user);
        //刷新token有效期
        stringRedisTemplate.expire("login:token"+token,30, TimeUnit.MINUTES);

        //方行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除用户
       UserHolder.removeUser();
    }
}
