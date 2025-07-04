package org.example.tlias.service;

import org.example.tlias.pojo.PageResult;

public interface OperateLogService {
    PageResult page(Integer pageNum, Integer pageSize);
}
