package org.example.tlias.service;

import org.example.tlias.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    /**
     * 统计各个职位的员工人数
     * @return 职位选项对象，包含职位列表和对应员工人数列表
     */
    JobOption getEmpJobData();
    /**
     * 统计员工性别信息
     * @return 性别信息列表，每个元素是一个Map，包含性别和对应的员工数量
     */
    List<Map> getEmpGenderData();
}