package com.back.service.impl;

import com.back.entity.Comment;
import com.back.entity.User;
import com.back.mapper.CommentMapper;
import com.back.service.ICommentService;
import com.back.service.IUserService;
import com.back.util.Result;
import com.back.util.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private ICommentService commentService;

    @Resource
    private IUserService userService;

    @Override
    public Result comment(Map<String, String> comment) {
        //获取评论用户id
        String userId = comment.get("userId");

        if (userId == null || userId.equals("")) {
            return Result.fail("请登录后在发表评论");
        }
        //获取用户名称和用户头像
        User user = userService.query().select("name", "headimg").eq("id", userId).one();
//        用户名称
        String name = user.getName();
//        头像
        String headimg = user.getHeadimg();
        //获取输入的评论内容
        String content = comment.get("content");
        if (content == null || content.equals("")) {
            return Result.fail("评论内容不允许为空");
        }
        //获取视频id
        String videoId = comment.get("videoId");
//        获取评论时间
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = Timestamp.valueOf(dateFormat.format(date)).toString().substring(0, 19);

        //存入对象
        Comment c1 = new Comment();
        c1.setContent(content);
        c1.setCreateTime(createTime);
        c1.setUserId(Integer.parseInt(userId));
        c1.setVideoId(Integer.parseInt(videoId));
        c1.setUsername(name);
        c1.setHeadimg(headimg);
        commentService.save(c1);
        return Result.ok();
    }

    @Override
    public Result queryALlComment(Map<String, String> id) {
//        获取视频id
        String videoid = id.get("videoid");
        List<Comment> commentList = commentService.query().eq("video_id", videoid).list();
//                .orderByDesc("tocomment").list()

        return Result.ok(commentList);
    }

    @Override
    public Result relpyByrootid(Map<String, String> id) {
//        System.out.println(id);
//        得到回复的root评论id
        String commentid = id.get("commentid");
//        获取回复的内容
        String content = id.get("content");
//        获取回复所在的视频id
        String videoId = id.get("videoId");
//        获取回复的人id
        String userId = id.get("userId");
        //获取用户名称和用户头像
        User user = userService.query().select("name", "headimg").eq("id", userId).one();
        if (user == null){
            return Result.fail("请登录");
        }
//        用户名称
        String name = user.getName();
//        头像
        String headimg = user.getHeadimg();
        //        获取评论时间
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = Timestamp.valueOf(dateFormat.format(date)).toString().substring(0, 19);

//       封装到实体类
        Comment comment = new Comment();
        comment.setHeadimg(headimg);
        comment.setUsername(name);
        comment.setVideoId(Integer.parseInt(videoId));
        comment.setUserId(Integer.parseInt(userId));
        comment.setRootcomment(Integer.parseInt(commentid));
        comment.setContent(content);
        comment.setCreateTime(createTime);
//        将这个评论设置为子评论
        comment.setTocomment(1);

        System.out.println(comment);
        commentService.save(comment);
        return Result.ok();
    }
}
