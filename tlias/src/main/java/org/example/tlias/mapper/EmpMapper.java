package org.example.tlias.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
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


    /**
     * 新增员工数据
     * @param emp 员工对象
     * 添加了@Options注解，useGeneratedKeys = true表示使用数据库自动生成的主键，keyProperty = "id"表示将生成的主键值赋值给Emp对象的id属性。
     * 这样，在执行插入操作后，Emp对象的id属性就会被自动赋值为生成的主键值。
     * 注意：@Options注解只能用于插入操作，不能用于更新操作。
     * 另外，使用@Options注解时，需要确保数据库表中对应的字段支持自动生成主键。
     * 例如，MySQL中的自增字段可以支持自动生成主键，而Oracle中的序列字段也可以支持自动生成主键。
     * 具体的支持情况需要根据数据库的类型和配置来确定。
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);
}
