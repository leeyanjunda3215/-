package com.back.controller;

import com.back.entity.Video;
import com.back.service.IHistoryService;
import com.back.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

@RestController
@RequestMapping("/history")
public class HIstoryController {

    @Resource
    private IHistoryService historyService;

    @PostMapping("/addhistory")
    public Result addhistory(@RequestBody Video video){
        return historyService.addhistory(video);
    }

    @GetMapping("/queryUserHistory")
    public Result queryUserHistory() throws ParseException {
        return historyService.queryUserHistory();
    }
}
