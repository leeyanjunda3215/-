package com.back.service.impl;

import com.back.entity.Video;
import com.back.mapper.VideoMapper;
import com.back.service.IVideoService;
import com.back.util.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
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
        return Result.ok(videoService.query().eq("is_delete",1).list());
    }

    @Override
    public Result search(Map<String,String> search) {
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
}
