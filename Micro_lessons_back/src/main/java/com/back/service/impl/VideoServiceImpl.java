package com.back.service.impl;

import com.back.dto.PageBean;
import com.back.dto.VideoForm;
import com.back.entity.Video;
import com.back.mapper.VideoMapper;
import com.back.service.IVideoService;
import com.back.util.Result;
import com.back.util.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 李焱军
 * @since 2023-01-18
 */

@Service

public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {
    @Resource
    private IVideoService videoService;

    @Resource
    VideoMapper videoMapper;

    @Override
    public Result getCarousel() {
        List<Video> cover = videoMapper.queryVideoCover();
        return Result.ok(cover);
    }

    @Override
    public Result getAll() {
        return Result.ok(videoService.query().eq("is_delete", 1).list());
    }

    @Override
    public Result search(Map<String, String> search) {
        String input = search.get("input");
        List<Video> videos = videoMapper.querySearch(input);
        return Result.ok(videos);
    }

    @Override
    public Result addTimes(Video video) {
        Integer id = video.getvId();
        videoMapper.addTimes(id);

        return Result.ok();
    }

    @Override
    public Result queryclassify() {
        List<Video> classify = videoService.query().select("distinct classify").list();
        return Result.ok(classify);
    }


    @Override
    public Result getVideoCover(MultipartFile pic, String videoform) throws IOException {
//        获取视频分类和视频名
        String[] split = videoform.split(",");
//        视频的分类
        String classify = split[1];
//        视频名字
        String name = split[0];
        File file = new File("D:\\liyanjun\\video\\" + classify + "\\" + name + "\\" + "1.png");
        if (!file.exists()) {
            file.mkdirs();
        }
        pic.transferTo(file);

        return Result.ok();
    }


    @Override
    public Result getVideo(MultipartFile video, String videoform) throws IOException {
        //        获取视频分类和视频名
        String[] split = videoform.split(",");
//        视频的分类
        String classify = split[1];
//        视频名字
        String name = split[0];
        System.out.println(video.getOriginalFilename());
        File file = new File("D:\\liyanjun\\video\\" + classify + "\\" + name + "\\1.mp4");
        if (!file.exists()) {
            file.mkdirs();
        }
        video.transferTo(file);
        return Result.ok();
    }


    @Override
    public Result getVideoInfo(VideoForm videoinfo) {
        //      获取视频的基本信息
        String name = videoinfo.getvName();
        List classify = videoinfo.getClassify();
        String brief = videoinfo.getBrief();
//  获取当前的用户id
        Integer id = UserHolder.getUser().getId();
//       封面图片地址
        String coverpath = "http://127.0.0.1:9999/" + classify.get(0) + "/" + name + "/1.png";
//        视频地址
        String videopath = "http://127.0.0.1:9999/" + classify.get(0) + "/" + name;
//      获取tag
        String tag = (String) classify.get(1);
//        封装实体类
        Video video = new Video();
        video.setClassify((String) classify.get(0));
        video.setBrief(brief);
        video.setvName(name);
        video.setUserId(id);
        video.setVideopath(videopath);
        video.setCover(coverpath);
        video.setTag(tag);
        videoService.save(video);
//        System.out.println("步骤1完成");
        return Result.ok();
    }

    @Override
    public Result getLastByPage(Integer currentPage, Integer pageSize) {
//      计算开始索引
        int begin = (currentPage - 1) * pageSize;
//     计算查询条目数
        int size = pageSize;
//     查询当前页面数据
        List<Video> videos = videoMapper.getlastByPage(begin, size);
//      查询总记录数
        int totalCount = videoMapper.selectTotalCount();

        PageBean<Video> pageBean = new PageBean<>();
        pageBean.setRows(videos);
        pageBean.setTotalCount(totalCount);
        return Result.ok(pageBean);
    }

    @Override
    public Result getClassifyByPage(Integer currentPage, Integer pageSize, String classify, String tag) {
//        System.out.println(classify+" : "+tag+" : "+" : "+currentPage+" : "+pageSize);
        //      计算开始索引
        int begin = (currentPage - 1) * pageSize;
//     计算查询条目数
        int size = pageSize;
//     查询当前页面数据
        List<Video> videos = videoMapper.getClassifyByPage(begin, size,classify,tag);

        //      查询总记录数
        int totalCount = videoMapper.getClassifyByPageTotal(begin, size,classify,tag);

        PageBean<Video> pageBean = new PageBean<>();
        pageBean.setRows(videos);
        pageBean.setTotalCount(totalCount);
        return Result.ok(pageBean);
    }


}
