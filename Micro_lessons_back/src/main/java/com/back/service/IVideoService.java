package com.back.service;

import com.back.dto.VideoForm;
import com.back.entity.Video;
import com.back.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 李焱军
 * @since 2023-01-18
 */
public interface IVideoService extends IService<Video> {

    Result getCarousel();

    Result getAll();

    Result search(Map<String,String> search);

    Result addTimes(Video video);


    Result queryclassify();

    Result getVideoCover(MultipartFile pic, String videoform) throws IOException;

    Result getVideo(MultipartFile video, String videoform) throws IOException;

    Result getVideoInfo(VideoForm videoinfo);

    Result getLastByPage(Integer currentPage, Integer pageSize);
}
