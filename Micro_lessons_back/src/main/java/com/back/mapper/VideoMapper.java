package com.back.mapper;

import com.back.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

        //查询最新视频 和 分页
        @Select("select * from video order by v_id DESC limit #{begin},#{size} ")
        List<Video> getlastByPage(@Param("begin") int begin,@Param("size") int size);

        //查询分类视频 和 分页
        @Select("select * from video where classify=#{classify} and tag=#{tag} limit #{begin},#{size}")
        List<Video> getClassifyByPage(@Param("begin") int begin,@Param("size") int size,@Param("classify") String classify,
                          @Param("tag") String tag);

//        查询分类视频的总数
@Select("select count(*) from video where classify=#{classify} and tag=#{tag} limit #{begin},#{size}")
int getClassifyByPageTotal(@Param("begin") int begin,@Param("size") int size,@Param("classify") String classify,
                              @Param("tag") String tag);

//        查询总记录数
        @Select("select count(*) from video")
        int selectTotalCount();
}
