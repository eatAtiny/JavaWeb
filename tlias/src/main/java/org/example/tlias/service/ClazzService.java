package org.example.tlias.service;

import org.example.tlias.pojo.Clazz;
import org.example.tlias.pojo.PageResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

public interface ClazzService {

    /**
     *  查询所有班级信息
     */
    List<Clazz> findAll();
    /**
     * 分页查询
     * @param page 页码
     * @param pageSize 每页显示的记录数
     * @param name 名称
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return PageResult对象，包含总记录数和当前页的数据列表
     */
    PageResult page(Integer page, Integer pageSize, String name, LocalDate beginDate, LocalDate endDate);

    /**
     * 根据id删除
     * @param id
     */
    void delete(Integer id);

    /**
     * 新增班级
     * @param clazz
     */
    void save(Clazz clazz);

    /**
     * 根据id查询
     * @param id
     * @return Clazz对象
     */
    Clazz findById(Integer id);

    /**
     * 根据id更新
     * @param clazz
     */
    void update(Clazz clazz);
}
