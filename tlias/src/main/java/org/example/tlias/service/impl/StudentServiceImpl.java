package org.example.tlias.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.tlias.mapper.StudentMapper;
import org.example.tlias.pojo.PageResult;
import org.example.tlias.pojo.Student;
import org.example.tlias.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult page(Integer page, Integer pageSize, String name, Integer degree, Integer classId) {
        PageHelper.startPage(page, pageSize);
        List<Student> students = studentMapper.findAll(name, degree, classId);
        Page<Student> p = (Page<Student>) students;
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        studentMapper.deleteByIds(ids);
    }

    @Override
    public void add(Student student) {
        student.setViolationCount((short) 0);
        student.setViolationScore((short) 0);
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.insert(student);
    }
}
