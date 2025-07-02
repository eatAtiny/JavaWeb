package org.example.tlias.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.tlias.mapper.EmpExprMapper;
import org.example.tlias.mapper.EmpLogMapper;
import org.example.tlias.mapper.EmpMapper;
import org.example.tlias.pojo.Emp;
import org.example.tlias.pojo.EmpExpr;
import org.example.tlias.pojo.EmpLog;
import org.example.tlias.pojo.PageResult;
import org.example.tlias.service.EmpLogService;
import org.example.tlias.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;

    @Override
    public PageResult page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end){
        // 使用传统方式需要先查询总记录数，再查询当前页的数据，要进行两次查询
//        Long total = empMapper.count();
//        Integer start = (page - 1) * pageSize;
//        List<Emp> empList = empMapper.list(start, pageSize);
//        return new PageResult(total, empList);

        // 使用pageHelper插件可以减少查询次数，只需要一次查询
        // 注意：在使用pageHelper插件时会先执行查询总记录数的sql，再执行查询当前页的数据的sql
        // 所以在查询当前页的数据的sql中不要使用limit关键字，pageHelper插件会自动添加limit关键字
        // 但是如果在查询总记录数的sql中使用了limit关键字，pageHelper插件就不会添加limit关键字
        // 而且sql语句也不要加分号，因为会影响pageHelper插件的执行
        PageHelper.startPage(page,pageSize);
        List<Emp> empList = empMapper.list(name, gender, begin, end);
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult(p.getTotal(),p.getResult());

    }
    /*
    @Transactional是用于声明事务的注解，它可以应用于方法或类上。
    可以通过rollbackFor属性指定事务回滚的异常类型，
        rollbackFor = Exception.class表示任何异常都会回滚。
        rollbackFor = RuntimeException.class表示运行时异常会回滚。
        如果没有指定rollbackFor属性，默认情况下只有RuntimeException及其子类才会回滚。
        如果还需要回滚指定类型的异常，可以在rollbackFor属性中添加多个异常类型，例如：
            rollbackFor = {Exception.class, SQLException.class}
    第二个参数propagation是事务的传播行为，它指定了事务的传播方式。
        常用的传播行为有：
            REQUIRED：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
            REQUIRES_NEW：创建一个新的事务，并暂停当前事务。
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Emp emp) {
        try {
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);

            //3. 保存员工的工作经历信息 - 批量
            Integer empId = emp.getId();
            List<EmpExpr> exprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(exprList)) {
                exprList.forEach(empExpr -> empExpr.setEmpId(empId));
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //4. 保存员工的日志信息
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), emp.toString());
            empLogService.insertLog(empLog);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        //1. 删除员工信息
        empMapper.deleteByIds(ids);
        //2. 删除员工的工作经历信息
        empExprMapper.deleteByEmpIds(ids);
    }
    @Override
    public Emp findById(Integer id) {
        return empMapper.findById(id);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        //1. 更新员工信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
        //2. 更新员工的工作经历信息
        empExprMapper.deleteByEmpIds(List.of(emp.getId()));
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }
}
