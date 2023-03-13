package com.back.config;


import com.back.util.interceptor.loginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resources;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new loginInterceptor(stringRedisTemplate))
               .excludePathPatterns(
                       //排除不需要拦截的路径
                        "/user/login",
                       "/user/register",
                       "/user/videoUser",
                       "/back/*",
                       "/user/path",
                       "/video/all",
                       "/video/getCarousel",
                       "/video/search",
                       "/video/addTimes",
                       "/video/queryclassify",
                       "/video/getVideoCover",
                       "/video/getVideo",
                       "/video/getlastbypage",
                       "/comment/*"
               );
    }
}
