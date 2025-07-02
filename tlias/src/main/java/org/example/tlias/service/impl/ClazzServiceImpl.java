package org.example.tlias.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.tlias.mapper.ClazzMapper;
import org.example.tlias.pojo.Clazz;
import org.example.tlias.pojo.Emp;
import org.example.tlias.pojo.PageResult;
import org.example.tlias.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public List<Clazz> findAll() {
        return clazzMapper.findAll(null,null,null);
    }

    @Override
    public PageResult page(Integer page, Integer pageSize, String name, LocalDate beginDate, LocalDate endDate) {
        PageHelper.startPage(page,pageSize);
        List<Clazz> clazzList = clazzMapper.findAll(name,beginDate,endDate);
        Page<Clazz> p = (Page<Clazz>) clazzList;
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public void delete(Integer id) {
        clazzMapper.delete(id);
    }

    @Override
    public void save(Clazz clazz) {
        // 补全属性值
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }

    @Override
    public Clazz findById(Integer id) {
        return clazzMapper.findById(id);
    }

    @Override
    public void update(Clazz clazz) {
        // 补全属性值
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.update(clazz);
    }
}
