package com.back.mapper;

import com.back.entity.Liked;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LikeMapper extends BaseMapper<Liked> {

    List<Integer> querylikes(@Param("uid") Integer uid, @Param("liked") Integer liked);

    List<Integer> querycollectioned(@Param("uid") Integer uid, @Param("collectioned") Integer collectioned);
}
