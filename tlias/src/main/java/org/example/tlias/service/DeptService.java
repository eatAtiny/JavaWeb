package org.example.tlias.service;

import org.apache.ibatis.annotations.Param;
import org.example.tlias.pojo.Dept;

import java.util.List;

public interface DeptService {
    public List<Dept> list();
    public Dept getById(Integer id);
    public int deleteById(Integer id);
    public int save(Dept dept);
    public int update(Dept dept);
}
