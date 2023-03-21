package com.back.mapper;

import com.back.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 李焱军
 * @since 2023-01-18
 */

public interface VideoMapper extends BaseMapper<Video> {

    List<Video> queryVideoCover();

    List<Video> querySearch(@Param("name") String search);

    void addTimes(@Param("id") Integer id);

    @Select("select * from video where is_delete = 1 and v_name like  #{search} order by times DESC limit #{begin},#{size}")
    List<Video> searchbyPage(@Param("begin") int begin, @Param("size") int size, @Param("search") String search);

    @Select("select count(*)  from video where is_delete = 1 and v_name like  #{search} order by times DESC limit #{begin},#{size}")
    int searchbyPageTotal(@Param("begin") int begin, @Param("size") int size, @Param("search") String search);

    //查询最新视频 和 分页
    @Select("select * from video where is_delete = 1 order by v_id DESC limit #{begin},#{size} ")
    List<Video> getlastByPage(@Param("begin") int begin, @Param("size") int size);

    //查询分类视频 和 分页
    @Select("select * from video where is_delete = 1 and classify=#{classify} and tag=#{tag} limit #{begin},#{size}")
    List<Video> getClassifyByPage(@Param("begin") int begin, @Param("size") int size, @Param("classify") String classify,
                                  @Param("tag") String tag);

    //        查询分类视频的总数
    @Select("select count(*) from video where is_delete = 1 and classify=#{classify} and tag=#{tag} limit #{begin},#{size}")
    int getClassifyByPageTotal(@Param("begin") int begin, @Param("size") int size, @Param("classify") String classify,
                               @Param("tag") String tag);

    //        查询总记录数
    @Select("select count(*) from video where is_delete = 1")
    int selectTotalCount();

    //    查询全部视频的 分页
    @Select("select  * from video  where is_delete = 1 limit #{begin},#{size}")
    List<Video> getAllByPage(@Param("begin") int begin, @Param("size") int size);

    //查询全部视频总数
    @Select("select count(*) from video where is_delete = 1  ")
    int getAllByPageTotalCount();

//    通过 关键字 查询作品 进行管理
    @Select("select  * from video where is_delete = 1 and v_name like #{word}")
List<Video> getVideoByKeyWord(@Param("word") String word);
}
