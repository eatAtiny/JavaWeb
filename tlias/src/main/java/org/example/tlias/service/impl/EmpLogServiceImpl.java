package org.example.tlias.service.impl;

import org.example.tlias.mapper.EmpLogMapper;
import org.example.tlias.pojo.EmpLog;
import org.example.tlias.service.EmpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpLogServiceImpl implements EmpLogService {
    @Autowired
    private EmpLogMapper empLogMapper;

    /*
     * 在Transactional注解中，REQUIRES_NEW：创建一个新的事务，并且暂停当前事务（如果存在）。
     * 这样无论父类事务是否成功，都会创建一个新的事务，并且在新的事务中执行插入操作。
     * 这样可以保证插入操作的事务与父类事务无关，不会影响父类事务的提交或回滚。
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void insertLog(EmpLog empLog) {
        empLogMapper.insert(empLog);
    }
}
