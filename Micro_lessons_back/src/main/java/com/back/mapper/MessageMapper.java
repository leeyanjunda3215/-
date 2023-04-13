package com.back.mapper;

import com.back.entity.Message;
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
 * @since 2023-04-11
 */


public interface MessageMapper  extends BaseMapper<Message> {

    @Select("select * from user where id in (select to_id from message where u_id = #{id} )")
   List<User> queryMessageUserList(@Param("id") Integer id);
}
