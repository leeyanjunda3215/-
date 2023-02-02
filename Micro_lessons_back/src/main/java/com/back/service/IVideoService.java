package com.back.service;

import com.back.entity.Video;
import com.back.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
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
}
