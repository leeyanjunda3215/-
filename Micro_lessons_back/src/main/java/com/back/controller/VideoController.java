package com.back.controller;
import javax.annotation.Resource;

import com.back.entity.Video;
import com.back.service.IVideoService;
import com.back.util.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 李焱军
 * @since 2023-01-18
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    @Resource
    private IVideoService videoService;

    @GetMapping("/all")
    public Result getAll(){
        return videoService.getAll();
//        return Result.ok(videoService.list());
    }


    @GetMapping("/getCarousel")
    public Result getCarousel(){
        return videoService.getCarousel();
    }

    @PostMapping("/search")
    public Result search(@RequestBody Map<String,String> search){
        return videoService.search(search);
    }

    @PostMapping("/addTimes")
    public Result addTimes(@RequestBody Video video){
        return videoService.addTimes(video);
    }

}
