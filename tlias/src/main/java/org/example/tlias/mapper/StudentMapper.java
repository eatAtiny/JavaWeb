package org.example.tlias.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.tlias.pojo.Student;

import java.util.List;

@Mapper
public interface StudentMapper {

    List<Student> findAll(String name, Integer degree, Integer clazzId);

    void deleteByIds(List<Integer> ids);

    void insert(Student student);
}
