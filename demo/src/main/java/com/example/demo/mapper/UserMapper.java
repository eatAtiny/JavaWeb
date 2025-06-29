package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user;")
    public List<User> findAll();

    @Insert("INSERT INTO user(username, password, name, age) VALUES(#{username}, #{password}, #{name}, #{age});")
    public Integer addUser(User user);

    @Delete("DELETE FROM user WHERE id = #{id};")
    public Integer deleteUser(Integer id);

    @Update("update user set username = #{username}, password = #{password}, name = #{name}, age = #{age} where id = #{id};")
    public Integer updateUser(User user);

    /*
     * @param 注解的作用是给参数起别名，在 SQL 语句中可以使用别名来引用参数。
     */
//    @Select("select * from user where id = #{id} and password = #{password};")
    public User findUserByIdAndPassword(@Param("id") Integer id, @Param("password") String password);
}
