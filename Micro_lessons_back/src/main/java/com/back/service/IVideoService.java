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

    Result search(int currentPage,int pageSize,String search);

    Result addTimes(Video video);


    Result queryclassify();

    Result getVideoCover(MultipartFile pic, String videoform) throws IOException;

    Result getVideo(MultipartFile video, String videoform) throws IOException;

    Result getVideoInfo(VideoForm videoinfo);

    Result getLastByPage(Integer currentPage, Integer pageSize);

    Result getClassifyByPage(Integer currentPage, Integer pageSize, String classify, String tag);

    Result getAllByPage(Integer currentPage, Integer pageSize);

    Result getVideoByUser();

    Result SearchKeyWord(Map<String, String> keyWord);

    Result DeleteVideo(Video video);


    Result UpdateVideoName(String currentName, String updateName);

    Result getSystemrecommend();
}
