package com.back.mapper;

import com.back.entity.Friend;
import com.back.entity.User;
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
 * @since 2023-01-31
 */
public interface FriendMapper  extends BaseMapper<Friend> {

    @Select("select * from user where id in (select follow_id from friend where user_id = #{userId})")
    List<User> queryFollowids(@Param("userId") Integer userId);
}
