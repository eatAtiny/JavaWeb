package org.example.tlias.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.tlias.mapper.EmpMapper;
import org.example.tlias.pojo.Emp;
import org.example.tlias.pojo.PageResult;
import org.example.tlias.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageResult page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end){
        // 使用传统方式需要先查询总记录数，再查询当前页的数据，要进行两次查询
//        Long total = empMapper.count();
//        Integer start = (page - 1) * pageSize;
//        List<Emp> empList = empMapper.list(start, pageSize);
//        return new PageResult(total, empList);

        // 使用pageHelper插件可以减少查询次数，只需要一次查询
        // 注意：在使用pageHelper插件时会先执行查询总记录数的sql，再执行查询当前页的数据的sql
        // 所以在查询当前页的数据的sql中不要使用limit关键字，pageHelper插件会自动添加limit关键字
        // 但是如果在查询总记录数的sql中使用了limit关键字，pageHelper插件就不会添加limit关键字
        // 而且sql语句也不要加分号，因为会影响pageHelper插件的执行
        PageHelper.startPage(page,pageSize);
        List<Emp> empList = empMapper.list(name, gender, begin, end);
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult(p.getTotal(),p.getResult());

    }
}
