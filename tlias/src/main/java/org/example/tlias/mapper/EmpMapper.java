package org.example.tlias.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.tlias.pojo.Emp;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {

    /**
     * 查询所有员工信息
     * @return 返回所有员工信息列表
     */
    @Select("select e.*, d.name as dept_name from emp e left join dept d on e.dept_id = d.id")
    List<Emp> findAll();

    /**
     * 根据id查询员工信息
     * @return 返回员工记录数量
     */
    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
    Long count();

//    @Select("select e.*, d.name as dept_name from emp e left join dept d on e.dept_id = d.id limit #{start},#{pageSize}")
    List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);

}
