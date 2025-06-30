package org.example.tlias;

import org.example.tlias.mapper.DeptMapper;
import org.example.tlias.mapper.EmpMapper;
import org.example.tlias.pojo.Dept;
import org.example.tlias.pojo.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TliasEmpMapperTests {
    @Autowired
    private EmpMapper empMapper;

    @Test
    public void testFindById(){
        List<Emp> emps = empMapper.findAll();
        emps.forEach(System.out::println);
    }
}
