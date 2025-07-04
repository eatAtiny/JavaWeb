package org.example.tlias.controller;

import org.example.tlias.pojo.PageResult;
import org.example.tlias.pojo.Result;
import org.example.tlias.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/log")
@RestController
public class OperateLogController {
    @Autowired
    private OperateLogService operateLogService;

    @GetMapping("/page")
    public Result page(Integer page, Integer pageSize){
        PageResult pageResult = operateLogService.page(page, pageSize);
        return Result.success(pageResult);
    }


}
