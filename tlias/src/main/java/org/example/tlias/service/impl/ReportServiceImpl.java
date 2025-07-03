package org.example.tlias.service.impl;

import org.example.tlias.mapper.EmpMapper;
import org.example.tlias.mapper.StudentMapper;
import org.example.tlias.pojo.ClazzOption;
import org.example.tlias.pojo.JobOption;
import org.example.tlias.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;
        
    @Override
    public JobOption getEmpJobData() {
        List<Map<String,Object>> list = empMapper.countEmpJobData();
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("total")).toList();
        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    @Override
    public List<Map> studentDegreeData() {
        return studentMapper.studentDegreeData();
    }

    @Override
    public ClazzOption studentClazzData() {
        List<Map> studentClassData = studentMapper.studentClazzData();
        List<Object> clazzList = studentClassData.stream().map(dataMap -> dataMap.get("name")).toList();
        List<Object> dataList = studentClassData.stream().map(dataMap -> dataMap.get("value")).toList();
        return new ClazzOption(clazzList, dataList);
    }
}