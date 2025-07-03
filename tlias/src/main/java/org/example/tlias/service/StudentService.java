package org.example.tlias.service;

import org.example.tlias.pojo.PageResult;
import org.example.tlias.pojo.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {

    /**
     * 根据id查询学生信息
     * @param id 学生id
     * @return 学生对象
     */
    Student getStudentById(String id);

    /**
     * 分页查询
     * @param page 页码
     * @param pageSize 每页显示的记录数
     * @param name 名称
     * @param degree 学历
     * @param classId 班级id
     * @return PageResult对象，包含总记录数和当前页的数据列表
     */
    PageResult page(Integer page, Integer pageSize, String name, Integer degree, Integer classId);

    /**
     * 批量删除
     * @param ids 要删除的id列表
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 新增
     * @param student 要新增的学生对象
     */
    void add(Student student);

    /**
     * 修改
     * @param student 要修改的学生对象
     */
    void update(Student student);

    /**
     * 违纪处理
     * @param id 学生id
     * @param score 违纪分数
     */
    void violation(Integer id,Integer score);


}
