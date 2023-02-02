package com.back.service.impl;

import com.back.entity.Friend;
import com.back.entity.User;
import com.back.mapper.FriendMapper;
import com.back.service.IFriendService;
import com.back.util.Result;
import com.back.util.UserHolder;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
 * @since 2023-01-31
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements IFriendService {

    @Resource
    private IFriendService friendService;
    @Override
    public Result follow(Map<String, String> friend) {
        //获取 观看视频|| 点关注按钮 的人 的id
        Integer userid = UserHolder.getUser().getId();
        //如果没有登录，就返回错误，让其登录
        if ( userid == null){
            return Result.fail("请登录");
        }
        //获取 视频发布者的id
        String id = friend.get("id");
        Integer videoid = Integer.parseInt(id);
        List<Friend> list = friendService.query().eq("is_delete",0).eq("follow_id", userid).eq("user_id", videoid).list();
//        判断 list是否为空 ，空就新建
        if ( list == null){
            System.out.println("没有关注");
            //将 粉丝 和 视频作者id 封装成 Friend 实体类
            Friend rela = new Friend();
            rela.setUserId(userid);
            rela.setFollowId(videoid);
            //存入数据库friend表中
            friendService.save(rela);
            return Result.ok();
        }
        System.out.println("关注");
//        有，就代表 之前数据库有，只是逻辑删除了
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_id",videoid);
        updateWrapper.eq("follow_id",userid);
        updateWrapper.set("is_delete",1);
        friendService.update(null,updateWrapper);
        return Result.ok();
    }

    @Override
    public Result TakeOffFollow(Map<String, String> id) {
        //获取当前用户id
        Integer user = UserHolder.getUser().getId();
        //获取视频作者id
        String id1 = id.get("id");
        int videouser = Integer.parseInt(id1);
        //逻辑删除
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_id",videouser);
        updateWrapper.eq("follow_id",user);
        updateWrapper.set("is_delete",0);
        friendService.update(null,updateWrapper);
        return Result.ok();
    }

    @Override
    public Result isfollowed(Map<String, String> id) {
//        获取 当前用户id
        Integer userdid = UserHolder.getUser().getId();
        String id1 = id.get("id");
//        获取视频id
        if(id1 == null){
            return Result.fail("没有传值");
        }
        Integer videoid = Integer.parseInt(id1);
//        去数据库查询是否 包含关注 关系
        List<Friend> list = friendService.query().eq("is_delete",1).eq("follow_id", userdid).eq("user_id", videoid).list();
        if (list.isEmpty()){
            //空就是没有关注
            return Result.fail("没有关注");
        }else {
            return Result.ok();
        }


    }
}
