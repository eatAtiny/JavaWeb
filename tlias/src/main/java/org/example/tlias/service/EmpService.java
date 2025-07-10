package org.example.tlias.service;

import org.apache.ibatis.annotations.MapKey;
import org.example.tlias.pojo.Emp;
import org.example.tlias.pojo.LoginInfo;
import org.example.tlias.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EmpService {
    /**
     * 分页查询
     * @param page 页码
     * @param pageSize 每页显示的记录数
     * @return PageResult 分页查询结果
     */
    PageResult page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);

    /**
     * 新增员工
     * @param emp 员工对象
     */
    void save(Emp emp);

    /**
     * 根据id删除员工
     * @param ids 员工id
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据id查询员工
     * @param id 员工id
     * @return Emp 员工对象
     * */
    Emp findById(Integer id);

    /**
     * 更新员工
     * @param emp 员工对象
     */
    void update(Emp emp);
    /**
     * 登录
     * @return LoginInfo 登录信息
     */
    LoginInfo login(Emp emp);

    /**
     * 获取班主任列表
     */
    List<Map> getMasterList();

}
