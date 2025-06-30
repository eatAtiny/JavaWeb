package org.example.tlias.service;

import org.example.tlias.pojo.PageResult;

import java.time.LocalDate;

public interface EmpService {
    /**
     * 分页查询
     * @param page 页码
     * @param pageSize 每页显示的记录数
     * @return PageResult 分页查询结果
     */
    PageResult page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);
}
