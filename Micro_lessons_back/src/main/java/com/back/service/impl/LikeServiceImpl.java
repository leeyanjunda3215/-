package com.back.service.impl;

import com.back.entity.Liked;
import com.back.entity.User;
import com.back.entity.Video;
import com.back.mapper.LikeMapper;
import com.back.mapper.VideoMapper;
import com.back.service.ILikeService;
import com.back.service.IVideoService;
import com.back.util.Result;
import com.back.util.UserHolder;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Liked> implements ILikeService {

    @Resource
    ILikeService likeService;

    @Resource
    private LikeMapper likeMapper;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private IVideoService videoService;

    @Override
    public Result like(Map<String, String> id) {
        //获取被点赞的视频id
        String vId = id.get("id");
        int vedioId = Integer.parseInt(vId);
        //获取登录的用户
        User user = UserHolder.getUser();
        if (user == null) {
            return Result.fail("请登录后点赞");
        }
        Integer userid = user.getId();

        List<Liked> list = likeService.query()
                .eq("v_id", vedioId)
                .eq("u_id", userid)
                .eq("liked", 0)
                .list();
        //查不到，就创建一个新的
        if (list.isEmpty()) {
            // 将结果封装到实体类
            Liked like = new Liked();
            like.setvId(vedioId);
            like.setuId(userid);
            like.setLiked(1);
            //确认无误，将点赞信息存入数据库
            likeService.save(like);
            return Result.ok();
        }
        //不然就修改，将 liked 和 is_delete 改为 1
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("v_id", vedioId);
        updateWrapper.eq("u_id", userid);
        updateWrapper.set("liked", 1);
        likeService.update(updateWrapper);
        return Result.ok();
    }

    @Override
    public Result isliked(Map<String, String> id) {
        //获取视频id
        String vId = id.get("id");
        int vedioId = Integer.parseInt(vId);
        //获取当前用户id
        User user = UserHolder.getUser();
        if (user == null) {
            return Result.fail("请登录后点赞");
        }
        Integer userid = user.getId();

        //在数据库中查找，没有被逻辑删除，用户id和视频id管理的
        List<Liked> list = likeService.query()
                .eq("v_id", vedioId)
                .eq("u_id", userid)
                .eq("liked", 1)
                .list();
        if (!(list.isEmpty())) {
            return Result.fail("没有点赞");
        } else {
            return Result.ok();
        }
    }

    @Override
    public Result offlike(Map<String, String> id) {
        //获取被点赞的视频id
        String vId = id.get("id");
        int vedioId = Integer.parseInt(vId);
        //获取登录的用户
        User user = UserHolder.getUser();
        if (user == null) {
            return Result.fail("请登录后点赞");
        }
        Integer userid = user.getId();

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("v_id", vedioId);
        updateWrapper.eq("u_id", userid);
        updateWrapper.set("liked", 0);
        likeService.update(updateWrapper);
        return Result.ok();
    }

    @Override
    public Result iscollctiond(Map<String, String> id) {
        //获取被点赞的视频id
        String vId = id.get("id");
        int vedioId = Integer.parseInt(vId);
        //获取登录的用户
        User user = UserHolder.getUser();
        if (user == null) {
            return Result.fail("请登录后点赞");
        }
        Integer userid = user.getId();

        //在数据库中查找，没有被逻辑删除，用户id和视频id管理的 且collectiond 为1的
        List<Liked> list = likeService.query()
                .eq("v_id", vedioId)
                .eq("u_id", userid)
                .eq("collectioned", 1)
                .list();
        if (!(list.isEmpty())) {
            return Result.fail("没有收藏");
        } else {
            return Result.ok();
        }
    }

    @Override
    public Result collctiond(Map<String, String> id) {
        //获取被点赞的视频id
        String vId = id.get("id");
        int vedioId = Integer.parseInt(vId);
        //获取登录的用户
        User user = UserHolder.getUser();
        if (user == null) {
            return Result.fail("请登录后点赞");
        }
        Integer userid = user.getId();

        List<Liked> list = likeService.query()
                .eq("v_id", vedioId)
                .eq("u_id", userid)
                .eq("collectioned", 0)
                .list();
        if (list.isEmpty()) {
            // 将结果封装到实体类
            Liked like = new Liked();
            like.setvId(vedioId);
            like.setuId(userid);
            like.setCollectioned(1);
            //确认无误，将点赞信息存入数据库
            likeService.save(like);
            return Result.ok();
        }
        //不然就修改，将 liked 和 is_delete 改为 1
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("v_id", vedioId);
        updateWrapper.eq("u_id", userid);
        updateWrapper.set("collectioned", 1);
        likeService.update(updateWrapper);
        return Result.ok();
    }

    @Override
    public Result collctiondoff(Map<String, String> id) {
        //获取被点赞的视频id
        String vId = id.get("id");
        int vedioId = Integer.parseInt(vId);
        //获取登录的用户
        User user = UserHolder.getUser();
        if (user == null) {
            return Result.fail("请登录后点赞");
        }
        Integer userid = user.getId();

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("v_id", vedioId);
        updateWrapper.eq("u_id", userid);
        updateWrapper.set("collectioned", 0);
        likeService.update(updateWrapper);
        return Result.ok();

    }

    @Override
    public Result getlikes() {
//        获取当前用户id
        User user = UserHolder.getUser();
//       判断是否登录
        if (user==null){
            return Result.fail("请登录");
        }
        Integer id = user.getId();
//        查询，这个用户id对应的 点赞过的视频id
//        List<Liked> list = likeService.query().select("v_id").eq("liked", 1).eq("u_id", id).list();
        List<Integer> videoids = likeMapper.querylikes(id, 1);
        System.out.println(videoids);
        //        根据对应的视频id查出对应的视频

        List<Video> videos = new ArrayList<>();
        for (int i:videoids) {
            Video v = videoService.query().eq("v_id", i).one();
            videos.add(v);
        }
        System.out.println(videos);

        return Result.ok(videos);
    }

    @Override
    public Result queryCollections() {
        //        获取当前用户id
        User user = UserHolder.getUser();
//       判断是否登录
        if (user==null){
            return Result.fail("请登录");
        }
        Integer id = user.getId();
//        查询，这个用户id对应的 收藏过的视频id
//        List<Liked> list = likeService.query().select("v_id").eq("liked", 1).eq("u_id", id).list();
        List<Integer> videoids = likeMapper.querycollectioned(id, 1);
        System.out.println(videoids);
        //        根据对应的视频id查出对应的视频

        List<Video> videos = new ArrayList<>();
        for (int i:videoids) {
            Video v = videoService.query().eq("v_id", i).one();
            videos.add(v);
        }
        System.out.println(videos);

        return Result.ok(videos);
    }
}
