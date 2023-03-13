package com.back.controller;

import com.alibaba.druid.sql.visitor.SQLEvalVisitorUtils;
import com.back.service.ILikeService;
import com.back.service.impl.LikeServiceImpl;
import com.back.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Resource
    private ILikeService likeService;

    @PostMapping("/like")
    public Result liked(@RequestBody Map<String,String> id){
        return likeService.like(id);
    }

    @PostMapping("/isliked")
    public Result isliked(@RequestBody Map<String,String> id){
        return likeService.isliked(id);
    }
    @PostMapping("/offlike")
    public Result offlike(@RequestBody Map<String,String> id){
        return likeService.offlike(id);
    }

    @PostMapping("/iscollctiond")
    public Result iscollctiond(@RequestBody Map<String,String> id){
        return likeService.iscollctiond(id);
    }

    @PostMapping("/collctiond")
    public Result collctiond(@RequestBody Map<String,String> id){
        return likeService.collctiond(id);
    }

    @PostMapping("/collctiondoff")
    public Result collctiondoff(@RequestBody Map<String,String> id){
        return likeService.collctiondoff(id);
    }

    @GetMapping("/getlikes")
    public Result getlikes(){
        return likeService.getlikes();
    }

    @GetMapping("/queryCollections")
    public Result queryCollections(){
        return likeService.queryCollections();
    }


}
