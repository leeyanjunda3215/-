package com.back.service;

import com.back.entity.History;
import com.back.entity.Video;
import com.back.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;

public interface IHistoryService extends IService<History> {
    Result addhistory(Video video);

    Result queryUserHistory() throws ParseException;
}
