package org.example.tlias.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.tlias.pojo.OperateLog;

import java.util.List;

@Mapper
public interface OperateLogMapper {
    
    //插入日志数据
    @Insert("insert into operate_log (operate_emp_id, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateEmpId}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime});")
    public void insert(OperateLog log);

    // 查询所有日志记录
    @Select("select o.*, e.name operateEmpName from operate_log o left join emp e on o.operate_emp_id = e.id")
    public List<OperateLog> selectAll();
    
}
