package org.example.tlias.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.tlias.pojo.EmpExpr;

import java.util.List;

@Mapper
public interface EmpExprMapper {

    /**
     * 批量插入
     * @param exprList 员工工作经历集合
     */
    public void insertBatch(List<EmpExpr> exprList);
    /**
     * 批量删除
     * @param ids 员工id集合
     */
    void deleteByEmpIds(List<Integer> ids);
}
