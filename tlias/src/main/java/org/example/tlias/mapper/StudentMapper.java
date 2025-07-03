package org.example.tlias.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.example.tlias.pojo.Student;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    Student getStudentById(String id);

    List<Student> findAll(String name, Integer degree, Integer clazzId);

    void deleteByIds(List<Integer> ids);

    void insert(Student student);

    void update(Student student);

    void violation(Integer id,Integer score);

    @MapKey("name")
    List<Map> studentDegreeData();

    @MapKey("name")
    List<Map> studentClazzData();
}
