package com.back.service.impl;

import com.back.entity.History;
import com.back.entity.Video;
import com.back.mapper.HistoryMapper;
import com.back.service.IHistoryService;
import com.back.service.IVideoService;
import com.back.util.Result;
import com.back.util.UserHolder;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 李焱军
 * @since 2023-03-21
 */

@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements IHistoryService {

    @Resource
    private IHistoryService historyService;
    @Override
    public Result addhistory(Video video) {
//        获取当前用户id
        Integer uid = UserHolder.getUser().getId();
        if (uid == null){
            return Result.fail("用户没有登录");
        }
//        获取视频id
        Integer vId = video.getvId();
//        获取当前时间
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = Timestamp.valueOf(dateFormat.format(date)).toString().substring(0, 19);

        History history = new History();
        history.setCreatetime(createTime);
        history.setvId(vId);
        history.setuId(uid);
        history.setIsDelete(1);
        System.out.println("观看历史："+history);
        historyService.save(history);
        return Result.ok();
    }

    @Resource
    private IVideoService videoService;
    @Override
    public Result queryUserHistory() throws ParseException {
//        获取当前用户id
        Integer id = UserHolder.getUser().getId();
        if (id==null){
            return Result.fail("用户没有登录");
        }
//        查询对应用户的观看历史（主要是看开始时间）
        List<History> historyTime = historyService.query().eq("is_delete", 1).eq("u_id", id).list();
//        获取现在的时间
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = Timestamp.valueOf(dateFormat.format(date)).toString().substring(0, 19);
        Date date1 = dateFormat.parse(nowTime);
        for (History i:historyTime) {
            int i1 = i.getvId();
            int i2 = i.gethId();
            String createtime = i.getCreatetime();
            Date date2 = dateFormat.parse(createtime);
//             时间计算
            long diff = date1.getTime() - date2.getTime();
//             对结果时间进行转化
            long days = diff / (1000 * 60 * 60 * 24);
            if (days >=7){
                UpdateWrapper wrapper = new UpdateWrapper();
                wrapper.eq("v_id",i1);
                wrapper.eq("h_id",i2);
                wrapper.set("is_delete",0);
                historyService.update(wrapper);
            }
        }

//        查询 用户 对应的 观看历史
        List<History> listHistory = historyService.query().select("v_id").eq("is_delete", 1).eq("u_id", id).list();
//       视频id集合
        List<Video> vIdList = new ArrayList<>();
        for (History i:listHistory) {
            int i1 = i.getvId();
            Video video = videoService.query().eq("is_delete", 1).eq("v_id", i1).one();
            vIdList.add(video);
            System.out.println("用户："+vIdList);
        }
        return Result.ok(vIdList);
    }
}
