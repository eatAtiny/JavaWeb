package org.example.tlias.service.impl;

import org.example.tlias.mapper.DeptMapper;
import org.example.tlias.pojo.Dept;
import org.example.tlias.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.findAll();
    }

    @Override
    public Dept getById(Integer id) {
        return deptMapper.findById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return deptMapper.deleteById(id);
    }

    @Override
    public int save(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        return deptMapper.insert(dept);
    }

    @Override
    public int update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        return deptMapper.update(dept);
    }
}
