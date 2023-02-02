package com.back.mapper;

import com.back.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 李焱军
 * @since 2023-01-18
 */

public interface VideoMapper extends BaseMapper<Video> {

        List<Video> queryVideoCover();

        List<Video> querySearch(@Param("name") String search);

        void addTimes(@Param("id") Integer id);

}