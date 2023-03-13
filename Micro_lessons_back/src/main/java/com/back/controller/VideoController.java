package com.back.controller;

import javax.annotation.Resource;

import com.back.dto.VideoForm;
import com.back.entity.Video;
import com.back.service.IVideoService;
import com.back.util.Result;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
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
    public Result getAll() {
        return videoService.getAll();
//        return Result.ok(videoService.list());
    }


    @GetMapping("/getCarousel")
    public Result getCarousel() {
        return videoService.getCarousel();
    }

    @PostMapping("/search")
    public Result search(@RequestBody Map<String, String> search) {
        return videoService.search(search);
    }

    @PostMapping("/addTimes")
    public Result addTimes(@RequestBody Video video) {
        return videoService.addTimes(video);
    }

    @GetMapping("/queryclassify")
    public Result queryclassify() {
        return videoService.queryclassify();
    }

//    @RequestParam("file") MultipartFile pic
    @PostMapping("/getVideoCover")
    public Result getVideoCover(@RequestParam("file") MultipartFile pic, String videoform) throws IOException {
        return videoService.getVideoCover(pic,videoform);
    }

    @PostMapping("/getVideo")
    public Result getVideo(@RequestParam("file") MultipartFile video,String videoform) throws Exception {
        return videoService.getVideo(video,videoform);
    }

    @PostMapping("/getVideoInfo")
    public Result getVideoInfo(@RequestBody VideoForm videoinfo) {
        return videoService.getVideoInfo(videoinfo);
    }

    @GetMapping("/getlastbypage")
    public Result getLastByPage(@RequestParam("currentPage") Integer currentPage,@RequestParam("pageSize") Integer pageSize){
        return videoService.getLastByPage(currentPage,pageSize);
    }
}
