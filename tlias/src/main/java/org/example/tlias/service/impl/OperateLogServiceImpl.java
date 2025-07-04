package org.example.tlias.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.tlias.mapper.OperateLogMapper;
import org.example.tlias.pojo.OperateLog;
import org.example.tlias.pojo.PageResult;
import org.example.tlias.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperateLogServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public PageResult page(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OperateLog> operateLogs = operateLogMapper.selectAll();
        Page<OperateLog> p = (Page<OperateLog>) operateLogs;
        return new PageResult(p.getTotal(),p.getResult());
    }

}
