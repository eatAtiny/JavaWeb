package org.example.tlias.mapper;

import org.apache.ibatis.annotations.*;
import org.example.tlias.anno.LogOperation;
import org.example.tlias.pojo.Dept;

import java.util.List;

@Mapper
public interface DeptMapper {
    /*
    在数据表字段与实体类属性不一致时，需要使用@Results和@Result注解进行映射
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
            }）
     */

    @Select("select * from dept;")
    public List<Dept> findAll();

    @Select("select * from dept where id = #{id}")
    public Dept findById(int id);

    @LogOperation
    @Delete("delete from dept where id = #{id}")
    public int deleteById(int id);

    @LogOperation
    @Insert("INSERT INTO dept (name, create_time, update_time) VALUES (#{name}, #{createTime}, #{updateTime})")
    public int insert(Dept dept);

    @LogOperation
    @Update("update dept set name=#{name},update_time=#{updateTime} where id=#{id};")
    public int update(Dept dept);


}
